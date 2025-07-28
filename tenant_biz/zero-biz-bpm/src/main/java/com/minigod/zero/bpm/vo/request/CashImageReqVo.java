package com.minigod.zero.bpm.vo.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class CashImageReqVo implements Serializable {
	private static final long serialVersionUID = -1943522245276105966L;

	private String imgBase64;
	private Integer serviceType; // 1:存入资金 2:转入股票 3:资产凭证 4:补充凭证
	private String type;
	private String imgIds; //图片id

}
