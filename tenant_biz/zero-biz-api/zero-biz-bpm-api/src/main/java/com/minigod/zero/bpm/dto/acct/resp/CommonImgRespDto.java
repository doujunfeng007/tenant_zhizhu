package com.minigod.zero.bpm.dto.acct.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommonImgRespDto implements Serializable {

	@JSONField(serializeUsing = ToStringSerializer.class)
	private Long id;

	private String imgBase64;
}
