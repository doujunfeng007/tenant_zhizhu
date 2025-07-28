package com.minigod.zero.ca.bo.sz.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.minigod.zero.ca.bo.sz.GetPDF;
import lombok.Data;


/**
 * @ClassName: Request
 * @Description:
 * @Author chenyu
 * @Date 2022/7/29
 * @Version 1.0
 */
@Data
public class Request {
    @JSONField(name = "getCertDN")
    CertDN certDN;
    @JSONField(name = "sign")
    Sign sign;
    @JSONField(name = "apply")
    Apply apply;
    @JSONField(name = "getPdf")
    GetPDF getPDF;
}
