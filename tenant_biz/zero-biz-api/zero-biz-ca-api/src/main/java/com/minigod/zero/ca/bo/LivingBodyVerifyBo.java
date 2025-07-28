package com.minigod.zero.ca.bo;

import feign.form.FormProperty;
import lombok.Data;

import java.io.File;
import java.io.Serializable;

/**
 * @ClassName: unionpayVerifyFourfactorBo
 * @Description:
 * @Author chenyu
 * @Date 2022/7/28
 * @Version 1.0
 */
@Data
public class LivingBodyVerifyBo implements Serializable {

    @FormProperty("idcard_name")
    private String idCardName;

    @FormProperty("idcard_number")
    private String idCardNumber;
    @FormProperty("orderno")
    private String orderNo;
    @FormProperty("video")
    private File video;
    @FormProperty("return_image_best")
    private Integer returnImageBest;
    @FormProperty("phone_number")
    private String phoneNumber;

}
