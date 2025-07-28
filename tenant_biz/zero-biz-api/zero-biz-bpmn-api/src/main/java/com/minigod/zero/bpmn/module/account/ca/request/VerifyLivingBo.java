package com.minigod.zero.bpmn.module.account.ca.request;

import com.minigod.zero.bpmn.module.account.constants.RegexpConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @ClassName: VerifyLivingBo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/12
 * @Version 1.0
 */
@Data
public class VerifyLivingBo{
    @Pattern(regexp = RegexpConstants.IS_VIDEO_URL,message = "非法URL")
    @NotBlank(message = "视频地址不能为空")
    @ApiModelProperty("url不能为空")
    private String url;
    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("证件号")
    @NotBlank(message = "证件号不能为空")
    private String idNo;
}
