package com.minigod.zero.bpmn.module.account.enums;

public enum AgreementStatus {
        NOT_SIGNED(0, "未签署"),
        SIGNED(1, "已签署");

        private final int code;
        private final String description;

        AgreementStatus(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }
