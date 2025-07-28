package com.minigod.zero.manage.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ReqInterfaceUpdateRespVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<UpdInfo> updInfo;

	@Data
	public static class UpdInfo implements Serializable {

		private static final long serialVersionUID = 1L;

		private String name;
		private String value;
		private Long count;
		private String lastMsg;
		private Date lastMsgTime;
		private Long maxMsgVersion = 0l;

		private List<UserIdUnReadNum> sec ;
	}


	@Data
	public static class UserIdUnReadNum implements Serializable {

		private static final long serialVersionUID = 1L;

		@ApiModelProperty(value = "用户ID")
		private Long userId; // 用户ID
		@ApiModelProperty(value = "未读数")
		private Long count; // 未读数
	}

}
