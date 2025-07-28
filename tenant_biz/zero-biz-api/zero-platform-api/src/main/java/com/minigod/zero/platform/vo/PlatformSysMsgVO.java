package com.minigod.zero.platform.vo;

import com.minigod.zero.platform.entity.PlatformSysMsgEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlatformSysMsgVO extends PlatformSysMsgEntity {

	/**
	 * 语言参数
	 */
	@ApiModelProperty(value = "语言参数")
	private String lang;

	/**
	 * 带参数的消息模板中的参数列表
	 */
	@ApiModelProperty(value = "带参数的消息模板中的参数列表")
	private List<String> params = new ArrayList<>();
}
