package com.minigod.zero.ca.bo.sz.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @ClassName: CertDN
 * @Description:
 * @Author chenyu
 * @Date 2022/7/30
 * @Version 1.0
 */
@Data
public class CertDN {
    //用戶身份证号码
    @JSONField(name = "idNo")
    private String idNo;
    //用户名称
    @JSONField(name = "userName")
    private String userName;
}
