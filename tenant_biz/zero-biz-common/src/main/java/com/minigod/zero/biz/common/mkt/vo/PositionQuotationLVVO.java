package com.minigod.zero.biz.common.mkt.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author:yanghu.luo
 * @create: 2023-06-16 17:39
 * @Description: 根据行情权限获取持仓中的证券行情
 */
@Data
public class PositionQuotationLVVO {
	@ApiModelProperty(value = "用户uin账号")
	private Long custId;

	@ApiModelProperty(value = "租户号")
	private String tenantId;

	@ApiModelProperty(value = "应用ID")
	private String clientId;

	@ApiModelProperty(value = "所属终端：(0-全平台 1-web 2-APP 3-PC 4-非展示 5-其他) 字典 mkt_varieties_device")
	private Integer terminalId;

	@ApiModelProperty(value = "IP")
	private String ip;

	@ApiModelProperty(value = "证券列表")
	List<String> assetIds;

	private String deviceInfo;

	private String deviceCode;
	@ApiModelProperty(value = "请求页面")
	private String reqPage;
}
