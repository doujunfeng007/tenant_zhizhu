package com.minigod.zero.bpm.dto.acct.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CommonImgDto implements Serializable {

	private List<Long> ids;

	private String type;

	private String imgBase64;
}
