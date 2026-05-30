package com.aicode.dto;

/* loaded from: instrumented-iFlyCode-3.4.2-222.jar:com/aicode/dto/GitResponseDTO.class */
public class GitResponseDTO {
    private Integer status;
    private String repoUrl;
    private String repoId;
    private String branch;
    private String command;
    private String repoName;
    private String code;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GitResponseDTO)) {
            return false;
        }
        GitResponseDTO other = (GitResponseDTO) o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$status = getStatus();
        Object other$status = other.getStatus();
        if (this$status == null) {
            if (other$status != null) {
                return false;
            }
        } else if (!this$status.equals(other$status)) {
            return false;
        }
        Object this$repoUrl = getRepoUrl();
        Object other$repoUrl = other.getRepoUrl();
        if (this$repoUrl == null) {
            if (other$repoUrl != null) {
                return false;
            }
        } else if (!this$repoUrl.equals(other$repoUrl)) {
            return false;
        }
        Object this$repoId = getRepoId();
        Object other$repoId = other.getRepoId();
        if (this$repoId == null) {
            if (other$repoId != null) {
                return false;
            }
        } else if (!this$repoId.equals(other$repoId)) {
            return false;
        }
        Object this$branch = getBranch();
        Object other$branch = other.getBranch();
        if (this$branch == null) {
            if (other$branch != null) {
                return false;
            }
        } else if (!this$branch.equals(other$branch)) {
            return false;
        }
        Object this$command = getCommand();
        Object other$command = other.getCommand();
        if (this$command == null) {
            if (other$command != null) {
                return false;
            }
        } else if (!this$command.equals(other$command)) {
            return false;
        }
        Object this$repoName = getRepoName();
        Object other$repoName = other.getRepoName();
        if (this$repoName == null) {
            if (other$repoName != null) {
                return false;
            }
        } else if (!this$repoName.equals(other$repoName)) {
            return false;
        }
        Object this$code = getCode();
        Object other$code = other.getCode();
        return this$code == null ? other$code == null : this$code.equals(other$code);
    }

    protected boolean canEqual(Object other) {
        return other instanceof GitResponseDTO;
    }

    public int hashCode() {
        Object $status = getStatus();
        int result = (1 * 59) + ($status == null ? 43 : $status.hashCode());
        Object $repoUrl = getRepoUrl();
        int result2 = (result * 59) + ($repoUrl == null ? 43 : $repoUrl.hashCode());
        Object $repoId = getRepoId();
        int result3 = (result2 * 59) + ($repoId == null ? 43 : $repoId.hashCode());
        Object $branch = getBranch();
        int result4 = (result3 * 59) + ($branch == null ? 43 : $branch.hashCode());
        Object $command = getCommand();
        int result5 = (result4 * 59) + ($command == null ? 43 : $command.hashCode());
        Object $repoName = getRepoName();
        int result6 = (result5 * 59) + ($repoName == null ? 43 : $repoName.hashCode());
        Object $code = getCode();
        return (result6 * 59) + ($code == null ? 43 : $code.hashCode());
    }

    public String toString() {
        return "GitResponseDTO(status=" + getStatus() + ", repoUrl=" + getRepoUrl() + ", repoId=" + getRepoId() + ", branch=" + getBranch() + ", command=" + getCommand() + ", repoName=" + getRepoName() + ", code=" + getCode() + ")";
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getRepoUrl() {
        return this.repoUrl;
    }

    public String getRepoId() {
        return this.repoId;
    }

    public String getBranch() {
        return this.branch;
    }

    public String getCommand() {
        return this.command;
    }

    public String getRepoName() {
        return this.repoName;
    }

    public String getCode() {
        return this.code;
    }
}
