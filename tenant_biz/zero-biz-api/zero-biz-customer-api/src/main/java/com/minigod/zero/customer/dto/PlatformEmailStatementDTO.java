package com.minigod.zero.customer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.platform.dto.platformEmailStatementDTO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/12/5 10:32
 * @Version: 1.0
 */
@Data
public class PlatformEmailStatementDTO {
	@ApiModelProperty(value = "唯一ID，外部系统调用发送消息传入")
	private String sendId;
	@ApiModelProperty(value = "推送状态(0-未发送，1-已发送 2-发送失败 3-发送中)")
	private Integer sendStatus;
	@ApiModelProperty(value = "错误信息")
	private String errorMsg;
}
