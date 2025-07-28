package com.minigod.zero.bpmn.module.exchange.vo;

import com.minigod.zero.bpmn.module.common.entity.FileUploadInfoEntity;
import com.minigod.zero.bpmn.module.exchange.entity.CustomerCurrencyExchangeApplication;
import com.minigod.zero.bpmn.module.exchange.entity.CustomerCurrencyExchangeInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author chen
 * @ClassName CustomerCurrencyExchangeInfoVO.java
 * @Description TODO
 * @createTime 2024年03月16日 18:16:00
 */
@Data
public class CustomerCurrencyExchangeInfoVO extends CustomerCurrencyExchangeInfo {

	private CustomerCurrencyExchangeApplication customerCurrencyExchangeApplication;

	private List<FileUploadInfoEntity> fileList;

	private Date openAccountTime;

	private String givenNameSpell;

	private String idKind;

	private String idCard;

	private String phoneNumber;


	@ApiModelProperty(value = "柜台处理状态名称")
	private String processingStatusName;

	@ApiModelProperty(value = "数据业务审核状态")
	private String appStatusName;

	@ApiModelProperty(value = "预约申请状态名称")
	private String applicationStatusName;


}
