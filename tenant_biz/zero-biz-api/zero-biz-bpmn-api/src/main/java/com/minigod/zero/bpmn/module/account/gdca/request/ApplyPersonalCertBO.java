package com.minigod.zero.bpmn.module.account.gdca.request;

import lombok.Data;
import org.apache.commons.lang3.Validate;

/**
 * 申请数字证书，以 FormData 的方式提交参数
 *
 * @author eric
 * @since 2024-05-16 14:06:09
 */
@Data
public class ApplyPersonalCertBO {
    /**
     * 活体检测ID
     */
    private String authenticationId;
    /**
     * 银行卡关联对比
     */
    private String bankAuthId;
    /**
     * 证书申请 CSR
     */
    private String p10;

    public void checkValidate() {
        Validate.notBlank(authenticationId, "活体检测ID不能为空！");
        Validate.notBlank(bankAuthId, "银行卡关联对比不能为空！");
        Validate.notBlank(p10, "证书申请 CSR P10不能为空！");
    }
}
