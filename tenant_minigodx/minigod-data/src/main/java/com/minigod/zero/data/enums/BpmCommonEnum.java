package com.minigod.zero.data.enums;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;

public class BpmCommonEnum {

    private static final Map<Integer, CodeType> map = Maps.newHashMap();

    /**
     * 公共模块相关
     */
    public enum CodeType {

        OK(0, "调用成功"),
        ERROR(-1, "error"),
        NONE_DATA(201, "无满足条件的数据"),
        EXIST_ERROR(300, "有重复值存在"),
        PARAMETER_DISMATCH(301, "参数不匹配"),
        PARAMETER_ERROR(400, "参数错误"),
        //PARAMS_PARAMETER_ERROR(401, "params参数错误"), // 未登录跳转状态
        SING_PARAMETER_ERROR(402, "签名参数SIGN错误"),
        KEY_PARAMETER_ERROR(403, "签名参数KEY错误"),
        SESSION_PARAMETER_ERROR(405, "参数SESSION_ID错误"),
        SOCKET_ERROR(404, "网络异常"),
        INTERNAL_ERROR(500, "请求异常，请重试"),
        UNBIND_WECHAT_ACC(600, "未绑定微信账号"),
        SIGN_ERROR(889, "签名错误"),
        ERROR_UNKNOWN(9999, "未知错误"),
        SESSION_INVALID(-9999, "未登录或者session已失效"),
        WEB_SUCCESS(0, "ok"),
        WEB_ERROR(-1, "error"),
        WEB_DUPLICATE(-2, "exist"),
        NEED_BIND_BANK_ACCOUNT(900, "若未绑定银行卡，请先在【银行卡管理】绑定银行卡"),
        REDIS_LOCK_ERROR(999, "获取redis锁异常"),
        ;

        private int code;
        private String message;

        CodeType(int code, String message) {
            this.code = code;
            this.message = message;
            map.put(code, this);
        }

        public int getCode() {
            return code;
        }

        public String code() {
            return String.valueOf(code);
        }

        public String getMessage() {
            return message;
        }

        public static String getMessage(int code) {
            CodeType codeType = map.get(code);
            if (codeType == null) {
                return ERROR_UNKNOWN.getMessage();
            }
            return codeType.getMessage();
        }
    }

    /**
     * 流程状态
     */
    public enum ActStauts {
        /**
         * 草稿
         */
        DRAFT(1),
        /**
         * 审批中
         */
        APPROVAL(2),
        /**
         * 结束
         */
        END(3);

        private Integer value;

        ActStauts(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 银行卡登记流程
     */
    public enum ClientBankCardApplicationStatus {
        /**
         * 提交
         */
        CLIENT_BANK_CARD_APPLY_STATUS_UNKONW(6, 6, "新建"),
        /**
         * 申请中
         */
        CLIENT_BANK_CARD_APPLY_STATUS_APPLY(7, 7, "申请"),
        /**
         * 待上传银行凭证
         */
        CLIENT_BANK_CARD_APPLY_STATUS_UPLOAD(0, 0, "待上传银行凭证"),
        /**
         * 待系统核实
         */
        CLIENT_BANK_CARD_APPLY_STATUS_PROGRESS(1, 1, "待系统核实"),
        /**
         * 已审核通过
         */
        CLIENT_BANK_CARD_APPLY_STATUS_SUCCESS(2, 2, "已审核通过"),
        /**
         * 无需核实
         */
        CLIENT_BANK_CARD_APPLY_STATUS_NOAUDIT(3, 3, "无需核实"),
        /**
         * 核实未通过
         */
        CLIENT_BANK_CARD_APPLY_STATUS_FAILURE(4, 4, "核实未通过"),
        /**
         * 已取消
         */
        CLIENT_BANK_CARD_APPLY_STATUS_CANCEL(5, 5, "已取消");

        public static final int CLIENT_BANK_CARD_APPLY_STATUS_UNKONW_VALUE = 6;
        public static final int CLIENT_BANK_CARD_APPLY_STATUS_APPLY_VALUE = 7;
        public static final int CLIENT_BANK_CARD_APPLY_STATUS_UPLOAD_VALUE = 0;
        public static final int CLIENT_BANK_CARD_APPLY_STATUS_PROGRESS_VALUE = 1;
        public static final int CLIENT_BANK_CARD_APPLY_STATUS_SUCCESS_VALUE = 2;
        public static final int CLIENT_BANK_CARD_APPLY_STATUS_NOAUDIT_VALUE = 3;
        public static final int CLIENT_BANK_CARD_APPLY_STATUS_FAILURE_VALUE = 4;
        public static final int CLIENT_BANK_CARD_APPLY_STATUS_CANCEL_VALUE = 5;

        public final int getNumber() {
            return value;
        }

        public static ClientBankCardApplicationStatus valueOf(int value) {
            switch (value) {
                case 0:
                    return CLIENT_BANK_CARD_APPLY_STATUS_UPLOAD;
                case 1:
                    return CLIENT_BANK_CARD_APPLY_STATUS_PROGRESS;
                case 2:
                    return CLIENT_BANK_CARD_APPLY_STATUS_SUCCESS;
                case 3:
                    return CLIENT_BANK_CARD_APPLY_STATUS_NOAUDIT;
                case 4:
                    return CLIENT_BANK_CARD_APPLY_STATUS_FAILURE;
                case 5:
                    return CLIENT_BANK_CARD_APPLY_STATUS_CANCEL;
                case 6:
                    return CLIENT_BANK_CARD_APPLY_STATUS_UNKONW;
                case 7:
                    return CLIENT_BANK_CARD_APPLY_STATUS_APPLY;
                default:
                    return null;
            }
        }

        @Getter
        private final int value;
        @Getter
        private final String name;

        private ClientBankCardApplicationStatus(int index, int value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    /**
     * 出金退款申请状态
     */
    public enum FundRefundApplyStatus {
        /**
         * 开始节点
         */
        FUND_REFUND_APPLY_STATUS_NEW(0, 0, " 开始"),
        /**
         * 审核节点
         */
        FUND_REFUND_APPLY_STATUS_AUDIT(1, 1, "审核"),
        /**
         * 退款节点
         */
        FUND_REFUND_APPLY_STATUS_REFUND(2, 2, " 退款"),
        /**
         * 结束节点
         */
        FUND_REFUND_APPLY_STATUS_FINISH(99, 99, "完成"),
        ;


        public static final int FUND_REFUND_APPLY_STATUS_NEW_VALUE = 0;
        public static final int FUND_REFUND_APPLY_STATUS_COMMIT_VALUE = 1;
        public static final int FUND_REFUND_APPLY_STATUS_REFUND_VALUE = 2;
        public static final int FUND_REFUND_APPLY_STATUS_FINISH_VALUE = 99;

        public final int getNumber() {
            return value;
        }

        public static FundRefundApplyStatus valueOf(int value) {
            return Arrays.stream(values()).filter(item -> item.value == value).findFirst().orElse(null);
        }

        private final int value;
        private final String name;

        private FundRefundApplyStatus(int index, int value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    /**
     * 流程状态
     */
    public enum FundRefundStatus {
        /**
         * 新建
         */
        FUND_REFUND_STATUS_NEW(0, "审核中"),
        FUND_REFUND_STATUS_REFUND(1, "退款中"),
        FUND_REFUND_STATUS_SUCCESS(2, "退款成功"),
        FUND_REFUND_STATUS_FAIL(3, "退款失败"),
        FUND_REFUND_STATUS_CANCEL(4, "已取消"),
        FUND_REFUND_STATUS_REJECT(5, "已拒绝"),
        ;


        public static final int FUND_REFUND_STATUS_NEW_VALUE = FUND_REFUND_STATUS_NEW.getValue();
        public static final int FUND_REFUND_STATUS_REFUND_VALUE = FUND_REFUND_STATUS_REFUND.getValue();
        public static final int FUND_REFUND_STATUS_SUCCESS_VALUE = FUND_REFUND_STATUS_SUCCESS.getValue();
        public static final int FUND_REFUND_STATUS_FAIL_VALUE = FUND_REFUND_STATUS_FAIL.getValue();
        public static final int FUND_REFUND_STATUS_CANCEL_VALUE = FUND_REFUND_STATUS_CANCEL.getValue();
        public static final int FUND_REFUND_STATUS_REJECT_VALUE = FUND_REFUND_STATUS_REJECT.getValue();

        public final int getValue() {
            return value;
        }

        public static FundRefundStatus valueOf(int value) {
            return Arrays.stream(FundRefundStatus.values()).filter(e -> e.value == value).findFirst().orElse(null);
        }

        private final int value;
        private final String name;

        FundRefundStatus(int value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    /**
     * 出金申请状态
     */
    public enum FundWithdrawApplicationStatus {
        /**
         * 新建
         */
        FUND_WITHDRAW_APPLY_STATUS_NEW(-1, -1, "新建"),
        /**
         * 内部拒绝
         */
        FUND_WITHDRAW_APPLY_INNER_REJECT_AUDIT(0, 0, "内部拒绝"),
        /**
         * 已提交
         */
        FUND_WITHDRAW_APPLY_STATUS_COMMIT(1, 1, "已提交"),
        /**
         * 已受理
         */
        FUND_WITHDRAW_APPLY_ACCEPT_AUDIT(2, 2, "已受理"),
        /**
         * 已退回
         */
        FUND_WITHDRAW_APPLY_BACK_AUDIT(3, 3, "已退回"),
        /**
         * 已完成
         */
        FUND_WITHDRAW_APPLY_STATUS_PASS(4, 4, "已完成"),
        /**
         * 已取消
         */
        FUND_WITHDRAW_APPLY_APPROVAL_CANCEL(5, 5, "已取消"),
        /**
         * 已拒绝
         */
        FUND_WITHDRAW_APPLY_REJECT_AUDIT(6, 6, "已拒绝"),
        /**
         * 已审核 电汇审核
         */
        FUND_WITHDRAW_APPLY_STATUS_INITIAL_AUDIT(7, 7, "已审核"),
        /**
         * 申请退款
         */
        FUND_WITHDRAW_REFUND_APPLY_STATUS_COMMIT(8, 8, "申请退款"),
        ///**
        // * 退款待入账
        // */
        //FUND_WITHDRAW_APPLY_REFUND_PROGRESS(15, 15, "退款待入账"),
        /**
         * 退款完成
         */
        FUND_WITHDRAW_REFUND_APPLY_STATUS_SUCCESS(9, 9, "退款完成"),
        /**
         * 已复核  人工后台录入审核
         */
        FUND_WITHDRAW_APPLY_RECHECK_AUDIT(13, 13, "已复核"),

        /**
         * DBS待打款
         */
        FUND_WITHDRAW_APPLY_PAY(10, 10, "DBS待打款"),
        /**
         * DBS打款中
         */
        FUND_WITHDRAW_APPLY_PAYING(11, 11, "DBS打款中"),
        /**
         * DBS打款失败
         */
        FUND_WITHDRAW_APPLY_PAYING_FAILURE(12, 12, "DBS打款失败"),
        /**
         * 柜台失败
         */
        FUND_WITHDRAW_APPLY_FAILURE(14, 14, "失败");


        public static final int FUND_WITHDRAW_APPLY_STATUS_NEW_VALUE = 0; //开始
        public static final int FUND_WITHDRAW_APPLY_STATUS_COMMIT_VALUE = 1; //申请
        public static final int FUND_WITHDRAW_APPLY_ACCEPT_AUDIT_VALUE = 2; //复核
        public static final int FUND_WITHDRAW_APPLY_CONFIRM_VALUE = 3; //确认
        public static final int FUND_WITHDRAW_APPLY_AUDIT_VALUE = 4; //审核
        public static final int FUND_WITHDRAW_APPLY_COUNTER_WITHDRAW_VALUE = 5; //出账
        public static final int FUND_WITHDRAW_APPLY_BANK_WITHDRAW_VALUE = 6; //汇款
        public static final int FUND_WITHDRAW_APPLY_REFUND_VALUE = 7; // 退款
        public static final int FUND_WITHDRAW_APPLY_END_VALUE = 99; //  结束

        public final int getNumber() {
            return value;
        }

        public static FundWithdrawApplicationStatus valueOf(int value) {
            switch (value) {
                case -1:
                    return FUND_WITHDRAW_APPLY_STATUS_NEW;
                case 0:
                    return FUND_WITHDRAW_APPLY_INNER_REJECT_AUDIT;
                case 1:
                    return FUND_WITHDRAW_APPLY_STATUS_COMMIT;
                case 2:
                    return FUND_WITHDRAW_APPLY_ACCEPT_AUDIT;
                case 3:
                    return FUND_WITHDRAW_APPLY_BACK_AUDIT;
                case 4:
                    return FUND_WITHDRAW_APPLY_STATUS_PASS;
                case 5:
                    return FUND_WITHDRAW_APPLY_APPROVAL_CANCEL;
                case 6:
                    return FUND_WITHDRAW_APPLY_REJECT_AUDIT;
                case 7:
                    return FUND_WITHDRAW_APPLY_STATUS_INITIAL_AUDIT;
                case 8:
                    return FUND_WITHDRAW_REFUND_APPLY_STATUS_COMMIT;
                case 9:
                    return FUND_WITHDRAW_REFUND_APPLY_STATUS_SUCCESS;
                case 10:
                    return FUND_WITHDRAW_APPLY_PAY;
                case 11:
                    return FUND_WITHDRAW_APPLY_PAYING;
                case 12:
                    return FUND_WITHDRAW_APPLY_PAYING_FAILURE;
                case 13:
                    return FUND_WITHDRAW_APPLY_RECHECK_AUDIT;
                case 14:
                    return FUND_WITHDRAW_APPLY_FAILURE;
                default:
                    return null;
            }
        }

        @Getter
        private final int value;
        @Getter
        private final String name;

        private FundWithdrawApplicationStatus(int index, int value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    public enum FundWithdrawStatus {
        // 状态 10审批中 11拒绝 12取消、 20出账中、 21 出账成功、22 出账失败、 30 汇款中 、31 汇款成功、 32 汇款失败 、40 退款中、41 退款成功、 42 退款失败
        AUDIT(10, "审核中"),
        REJECT(11, "已拒绝"),
        CANCEL(12, "已取消"),
        COUNTER_WITHDRAW(20, "待出账"),
        COUNTER_SUCCESS(21, "出账成功"),
        COUNTER_FAIL(22, "出账失败"),
        BANK_WITHDRAW(30, "待汇款"),
        BANK_SUCCESS(31, "汇款成功"),
        BANK_FAIL(32, "汇款失败"),
        REFUND(40, " 待退款"),
        REFUND_SUCCESS(41, " 退款成功"),
        REFUND_FAIL(42, " 退款失败"),

        ;
        @Getter
        private Integer status;
        @Getter
        private String desc;

        public static FundWithdrawStatus valueOf(int value) {
            for (FundWithdrawStatus status : FundWithdrawStatus.values()) {
                if (status.getStatus().equals(value)) {
                    return status;
                }
            }
            return null;
        }

        FundWithdrawStatus(Integer status, String desc) {
            this.status = status;
            this.desc = desc;
        }
    }

    public enum FundDepositStatus {
        // 状态 1 凭证处理中 2 流水核对中 3入账中 4 入账成功 5 入账失败 6 拒绝 7取消
        PROOF_AUDIT( 1, "凭证处理中"),
        COLLATE_AUDIT(2, "流水核对中"),
        DEPOSIT_PENDING(3, "入账中"),
        DEPOSIT_SUCCESS(4, "入账成功"),
        DEPOSIT_FAIL(5, "入账失败"),
        REJECT(6, "已拒绝"),
        CANCEL(7, "已取消"),

        ;
        @Getter
        private Integer status;
        @Getter
        private String desc;

        public static FundDepositStatus valueOf(int value) {
            for (FundDepositStatus status : FundDepositStatus.values()) {
                if (status.getStatus().equals(value)) {
                    return status;
                }
            }
            return null;
        }

        FundDepositStatus(Integer status, String desc) {
            this.status = status;
            this.desc = desc;
        }
    }

    /**
     * 入金申请状态
     */
    public enum FundDepositApplicationStatus {
        /**
         * 未知
         */
        FUND_DEPOSIT_APPLY_STATUS_UNKNOW(0, 0),
        /**
         * 待处理
         */
        FUND_DEPOSIT_APPLY_STATUS_WAIT(1, 1),
        /**
         * 初审中，作废
         */
        FUND_DEPOSIT_APPLY_STATUS_DEAL(2, 2),
        /**
         * 审核中
         */
        FUND_DEPOSIT_APPLY_STATUS_CHECK(3, 3),
        /**
         * 待入账
         */
        FUND_DEPOSIT_APPLY_STATUS_ENTRY_WAIT(4, 4),
        /**
         * 入账中
         */
        FUND_DEPOSIT_APPLY_STATUS_ENTRY(5, 5),
        /**
         * 已入账
         */
        FUND_DEPOSIT_APPLY_STATUS_SUCCESS(6, 6),
        /**
         * 退回客服,作废
         */
        FUND_DEPOSIT_APPLY_STATUS_BACK(7, 7),
        /**
         * 退回客户
         */
        FUND_DEPOSIT_APPLY_STATUS_REJECT(8, 8),
        /**
         * 入账失败
         */
        FUND_DEPOSIT_APPLY_STATUS_FAIL(9, 9),

        /**
         * 已忽略
         */
        FUND_DEPOSIT_APPLY_STATUS_IGNORE(10, 10),
        FUND_DEPOSIT_APPLY_STATUS_ENTRY_BACK(11, 11);

        // [0-未知 1-待处理  3-审核中 4-待入账 5-入账中 6-已入账 7-退回客服 8-退回客户 9-入账失败 10-已忽略 11-等待扫回]
        public static final int FUND_DEPOSIT_APPLY_STATUS_UNKNOW_VALUE = 0;
        public static final int FUND_DEPOSIT_APPLY_STATUS_WAIT_VALUE = 1;
        public static final int FUND_DEPOSIT_APPLY_STATUS_DEAL_VALUE = 2;
        public static final int FUND_DEPOSIT_APPLY_STATUS_CHECK_VALUE = 3;
        public static final int FUND_DEPOSIT_APPLY_STATUS_ENTRY_WAIT_VALUE = 4;
        public static final int FUND_DEPOSIT_APPLY_STATUS_ENTRY_VALUE = 5;
        public static final int FUND_DEPOSIT_APPLY_STATUS_SUCCESS_VALUE = 6;
        public static final int FUND_DEPOSIT_APPLY_STATUS_BACK_VALUE = 7;
        public static final int FUND_DEPOSIT_APPLY_STATUS_REJECT_VALUE = 8;
        public static final int FUND_DEPOSIT_APPLY_STATUS_FAIL_VALUE = 9;
        public static final int FUND_DEPOSIT_APPLY_STATUS_IGNORE_VALUE = 10;
        public static final int FUND_DEPOSIT_APPLY_STATUS_ENTRY_BACK_VALUE = 11;

        public final int getNumber() {
            return value;
        }

        public static FundDepositApplicationStatus valueOf(int value) {
            switch (value) {
                case 0:
                    return FUND_DEPOSIT_APPLY_STATUS_UNKNOW;
                case 1:
                    return FUND_DEPOSIT_APPLY_STATUS_WAIT;
                case 2:
                    return FUND_DEPOSIT_APPLY_STATUS_DEAL;
                case 3:
                    return FUND_DEPOSIT_APPLY_STATUS_CHECK;
                case 4:
                    return FUND_DEPOSIT_APPLY_STATUS_ENTRY_WAIT;
                case 5:
                    return FUND_DEPOSIT_APPLY_STATUS_ENTRY;
                case 6:
                    return FUND_DEPOSIT_APPLY_STATUS_SUCCESS;
                case 7:
                    return FUND_DEPOSIT_APPLY_STATUS_BACK;
                case 8:
                    return FUND_DEPOSIT_APPLY_STATUS_REJECT;
                case 9:
                    return FUND_DEPOSIT_APPLY_STATUS_FAIL;
                case 10:
                    return FUND_DEPOSIT_APPLY_STATUS_IGNORE;
                case 11:
                    return FUND_DEPOSIT_APPLY_STATUS_ENTRY_BACK;
                default:
                    return null;
            }
        }

        private final int value;

        private FundDepositApplicationStatus(int index, int value) {
            this.value = value;
        }
    }


    /**
     * 银行卡登记流程节点
     */
    public enum BankCardActNode {
        /**
         * 申请
         */
        APPLY(1),
        /**
         * 审核
         */
        RECHECK(2),
        /**
         * 结束
         */
        END(3);

        private Integer value;

        BankCardActNode(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 取款申请流程节点
     */
    public enum FundWithdrawActNode {
        /**
         * 申请
         */
        APPLY(1),
        /**
         * 复核
         */
        RECHECK(2),
        /**
         * 确认
         */
        CONFIRM(3),
        /**
         * 受理
         */
        ACCEPT(4),
        /**
         * 汇款
         */
        PAY(5),
        /**
         * 结束
         */
        END(6);

        private Integer value;

        FundWithdrawActNode(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 退款流程节点
     */
    public enum FundRefundActNode {
        /**
         * 申请
         */
        APPLY(1),
        /**
         * 审核
         */
        RECHECK(2),
        /**
         * 结束
         */
        END(3);

        private Integer value;

        FundRefundActNode(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }


}
