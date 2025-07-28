package com.minigod.zero.bpmn.module.edda.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.bpmn.module.edda.vo.EddaCustInfoVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/10 19:46
 * @Version: 1.0
 */
@Data
public class EddaCustInfoVO {

	@ApiModelProperty(value = "名字")
	private String givenName;

	@ApiModelProperty(value = "证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]")
	private String idKind;

	@ApiModelProperty(value = "证件号码")
	private String idCard;



}
