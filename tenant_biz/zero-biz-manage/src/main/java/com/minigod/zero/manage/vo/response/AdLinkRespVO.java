package com.minigod.zero.manage.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AdLinkRespVO implements Serializable{
	private static final long serialVersionUID = 1L;

	private List<AdLinkRespVOData> posCodes;

	@Data
	public static class AdLinkRespVOData{
		private Integer posCode;
		private Long adId;
		private String url;
		private String img;
		private String desc;
		private Boolean isNeedHeader;
		private Boolean bottomTab;
		private Boolean isRefresh;
		private String subTag;
		private Integer priority;
		private String adDesc;
	}


}
