package com.minigod.zero.customer.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.customer.dto.JobDetailDTO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/7 15:14
 * @Version: 1.0
 */
@Data
public class JobDetailDTO {
	@ApiModelProperty(value = "运行时间")
	private String handleTime;

	@ApiModelProperty(value = "数据变更条数")
	private String dataNum;
}
