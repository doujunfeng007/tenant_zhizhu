package com.minigod.zero.bpmn.module.account.gdca.request;

import lombok.Data;
import org.apache.commons.lang3.Validate;

/**
 * 银行卡信息比对请求参数
 *
 * @author eric
 * @since 2024-05-12 22:10:12
 */
@Data
public class UnionPayVerifyBo {
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号码
     */
    private String idCode;
    /**
     * 银行卡号
     */
    private String bankNo;
	/**
	 * 银行卡预留的手机号区号
	 */
	private String bankMobileArea;
    /**
     * 银行卡预留的手机号
     */
    private String bankMobile;

    public void checkValidate() {
        Validate.notNull(name, "姓名不能为空");
        Validate.notNull(idCode, "身份证号码不能为空");
        Validate.notNull(bankNo, "银行卡号不能为空");
        Validate.notNull(bankMobile, "银行卡预留的手机号不能为空");
    }
}
