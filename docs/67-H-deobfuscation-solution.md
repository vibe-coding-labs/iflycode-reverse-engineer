# H() String Deobfuscation — Complete Solution

> Date: 2026-05-12 | Status: SOLVED | Updated: 2026-05-12

## Algorithm

```
output[i] = input[i] XOR v[(len-i-1) % 106 + 1]
```

Where:
- `input` = obfuscated string (argument to H())
- `output` = decoded plaintext string
- `len` = length of the input string
- `v[]` = per-class periodic key sequence with period 106
- H() is **self-inverse**: H(H(x)) = x

## Key Properties

1. **Position-independent**: changing input[i] only affects output[i]
2. **Content-independent**: the XOR key does not depend on the input content
3. **Length-dependent**: the key at position i depends on (len - i), applied in reverse order
4. **Per-class**: each H() definition class has its own unique v[] sequence
5. **Period 106**: v[n] = v[((n-1) % 106) + 1] for all n > 106
6. **Not stack-trace-based**: contrary to bytecode appearance, the key is NOT derived from stackTrace[1]

## Why Static Analysis Failed

The .class file bytecode contains **deliberately invalid opcodes**:
- `fconst_2` (0x0D) and `fconst_1` (0x0C) at offsets 41-42 in integer context
- `iinc 511` and `iinc 255` with operands outside legal range (-128~127)
- These would cause `VerifyError` in a standard JVM

The actual runtime bytecode is **transformed by a custom ClassLoader** before execution. The transformed bytecode implements the simple reverse-indexed XOR algorithm, not the complex ishl/ixor chain visible in the .class file.

This also means Python bytecode scanning produces incorrect ldc+invokestatic pairings due to offset misalignment from invalid opcodes.

## Extracted v[] Sequences

All 33 H() definition classes have been extracted via Java runtime analysis:

| # | Class | v[1..5] | Period |
|---|-------|---------|--------|
| 1 | AICodeStringUtil | 49,58,44,39,48 | 106 |
| 2 | GenericUtils | 91,83,85,93,81 | 106 |
| 3 | NewFileUtils | 121,43,119,37,115 | 106 |
| 4 | PropertyUtils | 7,80,26,77,6 | 106 |
| 5 | FontKt | 78,82,64,92,68 | 106 |
| 6 | HandleCacheUtil | 72,23,85,10,73 | 106 |
| 7 | IndentLineUtil | 55,104,42,117,54 | 106 |
| 8 | EditorUtils | 90,1,71,28,91 | 106 |
| 9 | RequestCancelException | 83,52,93,58,89 | 106 |
| 10 | Maps | 88,100,86,106,82 | 106 |
| 11 | CodeCompleteService | 12,41,2,39,6 | 106 |
| 12 | RequestResultList | 52,6,41,27,53 | 106 |
| 13 | JComponentKt | 111,73,97,71,101 | 106 |
| 14 | CancelRequestTip | 97,97,111,111,107 | 106 |
| 15 | FileExtensionLanguageDetails | 0,18,29,15,1 | 106 |
| 16 | MethodGeneratorConfig | 95,81,81,95,85 | 106 |
| 17 | OpenTelemetryUtil | 87,19,74,14,86 | 106 |
| 18 | AICodeUtils | 19,29,14,0,18 | 106 |
| 19 | InlineChatStatusServiceKt | 127,90,98,71,126 | 106 |
| 20 | AICodeLanguageInfo | 126,79,112,65,116 | 106 |
| 21 | GitReviewService | 122,65,116,79,112 | 106 |
| 22 | PositionUtil | 73,57,71,55,67 | 106 |
| 23 | FileService | 81,67,76,94,80 | 106 |
| 24 | Application | 17,18,12,15,16 | 106 |
| 25 | IdeAction | 51,120,46,101,50 | 106 |
| 26 | GeneratorConfig | 111,121,114,100,110 | 106 |
| 27 | ConditionalActionConfiguration | 125,120,115,118,119 | 106 |
| 28 | RequestTimeoutException | 102,71,123,90,103 | 106 |
| 29 | ChatInputController | 126,114,112,124,116 | 106 |
| 30 | OverlayUtils | 119,90,106,71,118 | 106 |
| 31 | LanguageFileExtensionDetails | 127,18,98,15,126 | 106 |
| 32 | ActionButton | 105,115,116,110,104 | 106 |
| 33 | FileInfo | 0,105,29,116,1 | 106 |

Full sequences are in `tools/h_deobfuscator_final.py` (V_MAP dict).

## Decoder Tool

**File**: `tools/h_deobfuscator_final.py`

**Usage**:
```bash
python3 tools/h_deobfuscator_final.py [base_dir] [output.json]
```

Scans all .class files for `ldc + invokestatic H` patterns, decodes using per-class v[] keys.

**Current results** (with all 33 v[] keys):
- 566 class files scanned
- 279 classes with H() calls
- 4628 total H() calls found
- 33 v[] keys available (all H() definition classes)
- 91.5% decode rate (high+medium quality)
- 0 calls without v[] key

**Note on decode quality**: Many decoded strings contain non-ASCII characters (Chinese text, special symbols). The algorithm is correct (verified by H(H(x)) = x). The .class file bytecode contains deliberately invalid opcodes that cause Python's bytecode scanner to misalign ldc+invokestatic pairings, so some decoded strings may be incorrectly matched to the wrong H() target class.

## How v[] Was Extracted

1. Loaded each H() class via `URLClassLoader` with IntelliJ's full classpath
2. Called H() with known inputs of lengths 1..106
3. Derived v[n] = input[0] XOR output[0] for input of length n
4. Verified: decoded H(H(x)) == x for test strings
5. Confirmed: key is independent of input content and caller method

## Runtime Verification

```java
// Symmetry test
String plaintext = "https://api-legacy.example.com";
String obfuscated = H(plaintext);
String decoded = H(obfuscated);
assert decoded.equals(plaintext);  // TRUE

// Key independence test
H("AAAA") key == H("BBBB") key  // TRUE - same key regardless of input
H("test") from main() == H("test") from otherMethod()  // TRUE - same key regardless of caller
```

## Known-Plaintext Attack

Since H() is self-inverse, we can encode known plaintext strings and search for them in class constant pools. This allows us to:
1. Take a known string (e.g., from properties files, doc analysis)
2. Encode it with each H() method
3. Search all class constant pools for the encoded version
4. When found, we know the exact plaintext↔obfuscated mapping

This approach is more reliable than bytecode scanning but requires knowing the plaintext strings in advance.