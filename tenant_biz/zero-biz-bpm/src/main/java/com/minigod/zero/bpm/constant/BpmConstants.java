package com.minigod.zero.bpm.constant;

public class BpmConstants {

    public final static String NO_OPEN_CHECK = "0";

    public static final String GDCA_TX_KEY = "MINIGODGDCA";

    // 修改资料申请未提交
    public static final int SEC_USER_INFO_CHANGE_CHECK_STATUS_UNCOMMITTED = 0;
    // 修改资料申请已提交
    public static final int SEC_USER_INFO_CHANGE_CHECK_STATUS_COMMITTED = 1;
    // 修改资料申请已通过
    public static final int SEC_USER_INFO_CHANGE_CHECK_STATUS_SUCCESS = 2;
    // 修改资料已拒绝
    public static final int SEC_USER_INFO_CHANGE_CHECK_STATUS_FAIL = 3;
    // 修改姓名
    public static final int STEP_1 = 1;
    // 修改地址
    public static final int STEP_2 = 2;
    // 修改财务信息
    public static final int STEP_3 = 3;
    // 修改投资目标
    public static final int STEP_4 = 4;
    // 修改税务信息
    public static final int STEP_5 = 5;
    // 修改身份资料申报
    public static final int STEP_6 = 6;
    // 现时地址
    public static final int STEP_7 = 7;
    // 通讯地址
    public static final int STEP_8 = 8;


    public enum RealCheckTypeEnum {
        TYP_1("1", "证件号校验"),
        TYP_2("2", "公安两要素验证"),
        TYP_3("3", "四要素验证"),
        TYP_4("4", "手机号校验"),
        TYP_5("5", "邮箱校验"),
        TYP_6("6", "集体户地址校验");
        public String value;
        public String description;

        RealCheckTypeEnum(String value, String description) {
            this.value = value;
            this.description = description;
        }
    }

    public enum FileTypeEnum {
        TYP_1("1", "图片"),
        TYP_2("2", "视频");
        public String value;
        public String description;

        FileTypeEnum(String value, String description) {
            this.value = value;
            this.description = description;
        }
    }

    public enum DownloadImgTypeEnum {
        OPEN("open", "开户"),
        CHANGE("change", "线上修改资料"),
        CASH("cash", "资金存取");
        public String value;
        public String description;

        DownloadImgTypeEnum(String value, String description) {
            this.value = value;
            this.description = description;
        }
    }

    public enum QuestionTypeEnum {
        TYP_1(1, "个人户题库-基金"),
        TYP_2(2, "公司户题库-基金"),
        TYP_3(3, "PI个人户题库-PI");

        public int value;
        public String description;

        QuestionTypeEnum(int value, String description) {
            this.value = value;
            this.description = description;
        }
    }

    public enum ImgLocationTypeEnum {
        TYP_0("0", "未知"),
        TYP_101("101", "身份证正面"),
        TYP_102("102", "身份证反面"),
        TYP_103("103", "香港身份证"),
        TYP_104("104", "护照"),
        TYP_105("105", "补充身份证明"),
        TYP_106("106", "港澳通行证"),
        TYP_107("107", "澳门身份证"),
        TYP_108("108", "台湾身份证"),
        TYP_201("201", "银行卡"),
        TYP_301("301", "签名"),
        TYP_302("302", "签名信息"),
        TYP_401("401", "手持身份证正面照"),
        TYP_402("402", "正面照"),
        TYP_403("403", "公司注册证明书编号"),
        TYP_404("404", "商业登记证号码"),
        TYP_501("501", "1根手指"),
        TYP_502("502", "2根手指"),
        TYP_503("503", "3根手指"),
        TYP_504("504", "4根手指"),
        TYP_505("505", "5根手指"),
        TYP_506("506", "眨眼睛"),
        TYP_507("507", "张嘴巴"),
        TYP_508("508", "点头"),
        TYP_509("509", "摇头"),
        TYP_601("601", "接收衍生产品的方式凭证图"),
        TYP_602("602", "金融机构工作经验凭证图"),
        TYP_603("603", "衍生产品的交易凭证图"),
        TYP_604("604", "资产凭证图"),
        TYP_701("701", "广东CA认证视频"),
        TYP_801("801", "地址证明"),
        TYP_901("901", "补充广东CA认证视频");
        public String value;
        public String description;

        ImgLocationTypeEnum(String value, String description) {
            this.value = value;
            this.description = description;
        }
    }

    public enum ImgLocationEnum {
        TYP_1("1", "身份证"),
        TYP_2("2", "银行卡"),
        TYP_3("3", "签名照"),
        TYP_4("4", "正面照"),
        TYP_5("5", "活体"),
        TYP_6("6", "金融凭证"),
        TYP_7("7", "广东CA认证视频"),
        TYP_8("8", "地址证明"),
        TYP_9("9", "补充广东CA认证视频");
        public String value;
        public String description;

        ImgLocationEnum(String value, String description) {
            this.value = value;
            this.description = description;
        }
    }

    public enum BcanStatusEnum {
        TYP_0("0", "未开通"),
        TYP_1("1", "申请中"),
        TYP_2("2", "已认证"),
        TYP_3("3", "已拒绝"),
        TYP_4("4", "已撤销");
        public String value;
        public String description;

        BcanStatusEnum(String value, String description) {
            this.value = value;
            this.description = description;
        }
    }

    public enum CountryCodeEnum {
        CHN("CHN", "CHN"),
        HKG("HKG", "HKG");
        public String value;
        public String description;

        CountryCodeEnum(String value, String description) {
            this.value = value;
            this.description = description;
        }
    }

    public enum RiskTypeEnum {
        TYP_1(1, 10),
        TYP_2(2, 30),
        TYP_3(3, 50),
        TYP_4(4, 70),
        TYP_5(5, 90);
        public int typ;
        public int score;

        RiskTypeEnum(int typ, int score) {
            this.typ = typ;
            this.score = score;
        }

        public static int getScore(Integer type) {
            if (null == type) return 0;

            for (RiskTypeEnum riskTypeEnum : RiskTypeEnum.values()) {
                if (riskTypeEnum.typ == type.intValue()) {
                    return riskTypeEnum.score;
                }
            }
            return 0;
        }
    }

}
