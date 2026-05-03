/*
 * Decompiled with CFR 0.152.
 */
package com.aicode.agent.dto;

import com.aicode.agent.dto.chat.CodeInfoDto;
import java.util.List;

public class CodeCheckOriginDto {
    private String path;
    private String name;
    private List<ErrListDTO> errList;

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ErrListDTO> getErrList() {
        return this.errList;
    }

    public void setErrList(List<ErrListDTO> errList) {
        this.errList = errList;
    }

    public static class ErrListDTO {
        private String codeFragment;
        private String errorType;
        private String errorMessage;
        private List<CodeInfoDto.RangeDTO> range;

        public String getCodeFragment() {
            return this.codeFragment;
        }

        public void setCodeFragment(String codeFragment) {
            this.codeFragment = codeFragment;
        }

        public String getErrorType() {
            return this.errorType;
        }

        public void setErrorType(String errorType) {
            this.errorType = errorType;
        }

        public String getErrorMessage() {
            return this.errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public List<CodeInfoDto.RangeDTO> getRange() {
            return this.range;
        }

        public void setRange(List<CodeInfoDto.RangeDTO> range) {
            this.range = range;
        }
    }
}
