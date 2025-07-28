package com.minigod.zero.bpm.vo.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.minigod.zero.bpm.vo.OpenAccountImageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OpenAccResultCallbackProto implements Serializable {

	@JSONField(name = "openAccountAccessWay")
	private Integer openAccountAccessWay;

	@JSONField(name = "openStatus")
	private Integer openStatus;

	@JSONField(name = "userId")
	private Integer userId;

	@JSONField(name = "clientId")
	private String clientId;

	@JSONField(name = "openDate")
	private String openDate;

	@JSONField(name = "openAccountFileUrl")
	private String openAccountFileUrl;

	@JSONField(name = "errorInfo")
	private String errorInfo;

	@JSONField(name = "errorInfoStr")
	private String errorInfoStr;

	@JSONField(name = "errorImages")
	private List<OpenAccountImageInfo> errorImages;

	@JSONField(name = "channelId")
	private String channelId;
}
