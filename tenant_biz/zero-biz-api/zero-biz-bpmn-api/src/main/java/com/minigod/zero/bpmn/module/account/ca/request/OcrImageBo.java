package com.minigod.zero.bpmn.module.account.ca.request;

import com.minigod.zero.bpmn.module.account.constants.RegexpConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @ClassName: OcrImageBo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/29
 * @Version 1.0
 */
@Data
public class OcrImageBo {
    @Pattern(regexp = RegexpConstants.IS_IMAGE_URL,message = "非法URL")
    @NotBlank(message = "图片地址不能为空")
    @ApiModelProperty("图片地址")
    private String url;
}
