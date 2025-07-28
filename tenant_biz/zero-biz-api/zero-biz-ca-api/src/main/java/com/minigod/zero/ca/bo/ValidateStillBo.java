package com.minigod.zero.ca.bo;

import feign.form.FormProperty;
import lombok.Data;

import java.io.File;
import java.io.Serializable;

/**
 * @ClassName: ValidateStillBo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/12
 * @Version 1.0
 */
@Data
public class ValidateStillBo implements Serializable {
    private static final long serialVersionUID = -683665619971998723L;
    @FormProperty("idcard_name")
    private String idCardName;

    @FormProperty("idcard_number")
    private String idCardNumber;
    @FormProperty("video")
    private File video;
    @FormProperty("Return_image")
    private Integer returnImage = 1;

}
