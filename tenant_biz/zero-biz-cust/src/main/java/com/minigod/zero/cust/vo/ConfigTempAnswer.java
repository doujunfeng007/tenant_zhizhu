package com.minigod.zero.cust.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ConfigTempAnswer implements Serializable {

	@ApiModelProperty(value = "键")
	private Integer keyName;
	@ApiModelProperty(value = "值")
	private String keyValue;
	@ApiModelProperty(value = "选择")
	private Integer option;
}
