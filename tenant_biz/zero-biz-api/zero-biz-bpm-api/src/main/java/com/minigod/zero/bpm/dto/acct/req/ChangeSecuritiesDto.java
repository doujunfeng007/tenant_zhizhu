package com.minigod.zero.bpm.dto.acct.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChangeSecuritiesDto implements Serializable {

	private Long id;

	private Integer userId;

	private String info;

	private Integer type;

	private String imgList;

	private Integer step;
}
