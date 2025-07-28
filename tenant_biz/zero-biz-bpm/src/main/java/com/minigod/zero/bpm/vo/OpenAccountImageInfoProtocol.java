package com.minigod.zero.bpm.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class OpenAccountImageInfoProtocol implements Serializable {

	@JSONField(name = "imageLocation")
	private Integer imageLocation;

	@JSONField(name = "imageLocationType")
	private Integer imageLocationType;

	@JSONField(name = "imageUrl")
	private String imageUrl;
}
