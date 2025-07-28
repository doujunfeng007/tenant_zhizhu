package com.minigod.zero.ca.bo;

import feign.form.FormProperty;
import lombok.Data;


/**
 * @ClassName: unionpayVerifyFourfactorBo
 * @Description:
 * @Author chenyu
 * @Date 2022/7/28
 * @Version 1.0
 */
@Data
public class UnionpayVerifyFourfactorBo {

    private static final long serialVersionUID = 2077419640840378253L;
    @FormProperty("idcard_name")
    private String idcardName;
    @FormProperty("idcard_number")
    private String idcardNumber;
    @FormProperty("unionpay_cardnumber")
    private String unionpayCardnumber;
    @FormProperty("phone_number")
    private String phoneNumber;


}
