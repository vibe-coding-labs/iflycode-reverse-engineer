#!/usr/bin/env python3
"""
批量地为 vibe-coding-labs 下所有有 docs/ 的仓库安装 VitePress 文档站。
每个仓库独立部署到 GitHub Pages: https://vibe-coding-labs.github.io/{repo-name}/

用法:
  python3 batch-setup-vitepress.py [--dry-run] [--skip-npm]
"""

import os, sys, json, subprocess, re, shutil

BASE_DIR = "/home/cc11001100/github/vibe-coding-labs"
ORG = "vibe-coding-labs"
WORKFLOW_CONTENT = """name: Deploy VitePress site to Pages

on:
  push:
    branches: [main, master]
  workflow_dispatch:

permissions:
  contents: read
  pages: write
  id-token: write

concurrency:
  group: pages
  cancel-in-progress: false

jobs:
  build-and-deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with:
          node-version: 20
      - name: Install dependencies
        run: npm install --ignore-scripts
      - name: Build VitePress
        run: npm run docs:build
      - uses: actions/configure-pages@v4
      - uses: actions/upload-pages-artifact@v3
        with:
          path: docs/.vitepress/dist
      - name: Deploy to GitHub Pages
        id: deployment
        uses: actions/deploy-pages@v4
"""

GITIGNORE_APPEND = """
# VitePress
node_modules/
docs/.vitepress/dist/
docs/.vitepress/cache/
"""

DRY_RUN = "--dry-run" in sys.argv
SKIP_NPM = "--skip-npm" in sys.argv

def run(cmd, cwd=None, check=True):
    """Run shell command and return output."""
    print(f"  $ {cmd}")
    if DRY_RUN:
        return ""
    result = subprocess.run(cmd, shell=True, cwd=cwd, capture_output=True, text=True)
    if check and result.returncode != 0:
        print(f"  ⚠️  Command failed (exit {result.returncode}): {result.stderr[:200]}")
    return result.stdout.strip()

def get_title_from_readme(repo_dir):
    """Extract a good title from README.md."""
    readme_path = os.path.join(repo_dir, "README.md")
    if os.path.exists(readme_path):
        with open(readme_path) as f:
            for line in f:
                line = line.strip()
                if line.startswith("# ") and not line.startswith("# https://"):
                    title = line.lstrip("# ").strip()
                    # Limit length
                    if len(title) > 60:
                        title = title[:57] + "..."
                    return title
    return repo_name  # fallback

def scan_docs(repo_dir, repo_name):
    """Scan docs/ directory for markdown files and generate sidebar config."""
    docs_dir = os.path.join(repo_dir, "docs")
    if not os.path.isdir(docs_dir):
        return None, None, None

    # Collect all .md files (flat or nested)
    md_files = []
    for root, dirs, files in os.walk(docs_dir):
        # Skip .vitepress
        if ".vitepress" in root.split(os.sep):
            continue
        for fn in files:
            if fn.endswith(".md") and fn != "index.md":
                rel = os.path.relpath(os.path.join(root, fn), docs_dir)
                md_files.append(rel)

    md_files.sort()

    # Generate sidebar from md files
    sidebar_items = []
    for mf in md_files:
        # Read first heading for display name
        fpath = os.path.join(docs_dir, mf)
        title = os.path.splitext(os.path.basename(mf))[0]
        with open(fpath) as f:
            for line in f:
                line = line.strip()
                if line.startswith("# ") or line.startswith("## "):
                    title = line.lstrip("#").strip()
                    break
                if line.startswith("---"):  # skip YAML frontmatter
                    break
        link = "/" + mf.replace("\\", "/").replace(".md", "")
        sidebar_items.append({'text': title, 'link': link})

    # Generate index.md if not exists
    index_path = os.path.join(docs_dir, "index.md")
    if not os.path.exists(index_path):
        # Check if README.md exists in docs
        readme_path = os.path.join(docs_dir, "README.md")
        if os.path.exists(readme_path):
            # Use it as index
            os.rename(readme_path, index_path)
        else:
            repo_title = get_title_from_readme(repo_dir)
            with open(index_path, "w") as f:
                f.write(f"# {repo_title}\n\n")
                f.write("## 文档列表\n\n")
                for item in sidebar_items:
                    f.write(f"- [{item['text']}]({item['link']})\n")

    return md_files, sidebar_items, index_path

def generate_config(repo_dir, repo_name, desc):
    config_path = os.path.join(repo_dir, "docs", ".vitepress", "config.mts")
    md_files, sidebar_items, _ = scan_docs(repo_dir, repo_name)

    # Sidebar title = repo description or name
    sidebar_title = desc or f"{repo_name} 文档"
    if len(sidebar_title) > 40:
        sidebar_title = sidebar_title[:37] + "..."

    if not sidebar_items:
        # Minimal config — just show index
        items = '[{ text: "首页", link: "/" }]'
    else:
        items = '[\n'
        items += f'          {{ text: "{sidebar_title}", items: [\n'
        items += '            { text: "首页", link: "/" },\n'
        for item in sidebar_items:
            text = item['text'].replace('"', '\\"')
            items += f'            {{ text: "{text}", link: "{item["link"]}" }},\n'
        items += '          ] }\n'
        items += '        ]'

    config = f"""import {{ defineConfig }} from 'vitepress'

export default defineConfig({{
  base: '/{repo_name}/',
  title: '{desc or repo_name}',
  description: '{desc or ""}',
  lang: 'zh-CN',
  lastUpdated: true,
  ignoreDeadLinks: true,
  markdown: {{
    lineNumbers: true,
  }},
  themeConfig: {{
    search: {{
      provider: 'local',
    }},
    nav: [
      {{ text: '文档首页', link: '/' }},
      {{ text: 'GitHub', link: 'https://github.com/{ORG}/{repo_name}' }},
    ],
    sidebar: {items},
    socialLinks: [
      {{ icon: 'github', link: 'https://github.com/{ORG}/{repo_name}' }},
    ],
    footer: {{
      message: '基于 VitePress 构建',
    }},
  }},
}})
"""
    return config

def process_repo(repo_dir, repo_name):
    """Install VitePress on a single repo."""
    print(f"\n{'='*60}")
    print(f"📦 {repo_name}")
    print(f"{'='*60}")

    # Get repo description
    desc = run(f"gh api repos/{ORG}/{repo_name} --jq '.description'", check=False)

    docs_dir = os.path.join(repo_dir, "docs")
    vitepress_dir = os.path.join(docs_dir, ".vitepress")
    workflow_dir = os.path.join(repo_dir, ".github", "workflows")

    # 1. Create .vitepress/config.mts
    os.makedirs(vitepress_dir, exist_ok=True)
    config_content = generate_config(repo_dir, repo_name, desc)
    config_path = os.path.join(vitepress_dir, "config.mts")
    if not DRY_RUN:
        with open(config_path, "w") as f:
            f.write(config_content)
    print(f"  ✅ Created {config_path}")

    # 2. Create workflow
    os.makedirs(workflow_dir, exist_ok=True)
    workflow_path = os.path.join(workflow_dir, "deploy-docs.yml")
    if not DRY_RUN:
        with open(workflow_path, "w") as f:
            f.write(WORKFLOW_CONTENT)
    print(f"  ✅ Created {workflow_path}")

    # 3. Update .gitignore
    gitignore_path = os.path.join(repo_dir, ".gitignore")
    existing = ""
    if os.path.exists(gitignore_path):
        with open(gitignore_path) as f:
            existing = f.read()
    if "node_modules/" not in existing:
        if not DRY_RUN:
            with open(gitignore_path, "a") as f:
                f.write(GITIGNORE_APPEND)
        print(f"  ✅ Updated .gitignore")

    # 4. Create/update package.json
    pkg_path = os.path.join(repo_dir, "package.json")
    pkg = {}
    if os.path.exists(pkg_path):
        with open(pkg_path) as f:
            try:
                pkg = json.load(f)
            except:
                pkg = {}

    pkg["name"] = repo_name
    pkg.setdefault("private", True)
    pkg.setdefault("scripts", {})
    pkg["scripts"]["docs:dev"] = "vitepress dev docs"
    pkg["scripts"]["docs:build"] = "vitepress build docs"
    pkg["scripts"]["docs:preview"] = "vitepress preview docs"
    pkg.setdefault("devDependencies", {})
    pkg["devDependencies"]["vitepress"] = "^1.6.0"

    if not DRY_RUN:
        with open(pkg_path, "w") as f:
            json.dump(pkg, f, indent=2)
            f.write("\n")
    print(f"  ✅ Updated package.json")

    # 5. npm install
    if not SKIP_NPM and not DRY_RUN:
        result = subprocess.run("npm install --ignore-scripts 2>&1 | tail -3", shell=True, cwd=repo_dir, capture_output=True, text=True)
        if result.returncode == 0:
            print(f"  ✅ npm install done")
        else:
            print(f"  ⚠️  npm install: {result.stderr[:100]}")

    # 6. git add & commit
    if not DRY_RUN:
        run("git add -A", cwd=repo_dir, check=False)
        result = run('git diff --cached --quiet || git commit -m "feat: add VitePress docs site with GitHub Actions deployment"', cwd=repo_dir, check=False)
        if "nothing" in result or "no changes" in result or result == "":
            print(f"  ⏭️  No changes to commit")
        else:
            print(f"  ✅ Committed")

    return True

def enable_pages(repo_name, is_private):
    """Enable GitHub Pages for a repo."""
    print(f"\n  📄 Enabling Pages for {repo_name}...")

    # Check if Pages already enabled
    result = run(f"gh api repos/{ORG}/{repo_name}/pages --jq '.status' 2>/dev/null", check=False)
    if result and result != "null":
        print(f"  ⏭️  Pages already enabled for {repo_name}")
        return

    # Enable Pages
    if not is_private:
        run(
            f'gh api -X POST repos/{ORG}/{repo_name}/pages --input - <<\'EOF\'\n'
            f'{{"build_type":"workflow","source":{{"branch":"main","path":"/"}}}}\n'
            f'EOF',
            check=False
        )
        print(f"  ✅ Pages enabled for {repo_name}")

        # Trigger workflow
        run(f"gh workflow run deploy-docs.yml --repo {ORG}/{repo_name}", check=False)
        print(f"  ✅ Deploy triggered for {repo_name}")
    else:
        print(f"  ⏭️  {repo_name} is private, can't enable Pages")

# === MAIN ===
print("="*60)
print("🚀 Batch VitePress Setup for all vibe-coding-labs repos")
print(f"DRY_RUN={DRY_RUN}, SKIP_NPM={SKIP_NPM}")
print("="*60)

# Skip repos that already have VitePress (including iflycode which is done)
skip_repos = {"iflycode-reverse-engineer", "kimi-cli-src", "zed-reverse-engineer"}

repos = []
for d in sorted(os.listdir(BASE_DIR)):
    repo_dir = os.path.join(BASE_DIR, d)
    if not os.path.isdir(repo_dir) or not os.path.isdir(os.path.join(repo_dir, ".git")):
        continue
    docs_dir = os.path.join(repo_dir, "docs")
    if not os.path.isdir(docs_dir):
        continue
    if d in skip_repos:
        print(f"\n⏭️  Skipping {d} (already has VitePress or is excluded)")
        continue

    # Check if already has vitepress
    if os.path.isdir(os.path.join(docs_dir, ".vitepress")):
        print(f"\n⏭️  Skipping {d} (already has .vitepress)")
        continue

    repos.append((repo_dir, d))

print(f"\nFound {len(repos)} repos to process")

# Phase 1: Install VitePress config
for repo_dir, repo_name in repos:
    process_repo(repo_dir, repo_name)

# Phase 2: Push to remote
print(f"\n{'='*60}")
print("📤 Phase 2: Push to remote")
print(f"{'='*60}")

for repo_dir, repo_name in repos:
    print(f"\n📤 Pushing {repo_name}...")
    if not DRY_RUN:
        run("git push origin main 2>&1 | tail -3", cwd=repo_dir, check=False)

# Phase 3: Enable GitHub Pages
print(f"\n{'='*60}")
print("🌐 Phase 3: Enable GitHub Pages")
print(f"{'='*60}")

for repo_dir, repo_name in repos:
    is_private = run(f"gh api repos/{ORG}/{repo_name} --jq '.private'", check=False)
    enable_pages(repo_name, is_private == "true")

print(f"\n{'='*60}")
print("✅ All done!")
print(f"{'='*60}")