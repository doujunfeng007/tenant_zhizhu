package com.minigod.zero.bpmn.module.account.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minigod.zero.bpmn.module.account.api.TaxItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: com.minigod.zero.bpmn.module.account.dto.PreviewW8AgreementDTO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/12/23 18:12
 * @Version: 1.0
 */
@Data
public class PreviewW8AgreementDTO {
	@ApiModelProperty(value = "类型 2:w8协议    3:个人证明协议")
	private Integer type;

	@ApiModelProperty(value = "预约流水号")
	private String applicationId;

	/**
	 * 住宅地址的国家
	 */
	@ApiModelProperty(value = "住宅地址的国家")
	private String familyRepublicName;
	/**
	 * 住宅地址的省份
	 */
	@ApiModelProperty(value = "住宅地址的省份")
	private String familyProvinceName;
	/**
	 * 住宅地址的城市
	 */
	@ApiModelProperty(value = "住宅地址的城市")
	private String familyCityName;
	/**
	 * 住宅地址的区域
	 */
	@ApiModelProperty(value = "住宅地址的区域")
	private String familyCountyName;
	/**
	 * 住宅地址的详细地址
	 */
	@ApiModelProperty(value = "住宅地址的详细地址")
	private String familyDetailAddress;

	/**
	 * 通讯地址的国家
	 */
	@ApiModelProperty(value = "通讯地址的国家")
	private String contactRepublicName;
	/**
	 * 联系地址的省份
	 */
	@ApiModelProperty(value = "联系地址的省份")
	private String contactProvinceName;
	/**
	 * 联系地址的城市
	 */
	@ApiModelProperty(value = "联系地址的城市")
	private String contactCityName;
	/**
	 * 联系地址的区域
	 */
	@ApiModelProperty(value = "联系地址的区域")
	private String contactCountyName;
	/**
	 * 联系地址的详细地址
	 */
	@ApiModelProperty(value = "联系地址的详细地址")
	private String contactDetailAddress;

	/**
	 * 税务信息
	 */
	@JSONField(name = "taxInfoList")
	@JsonProperty(value = "taxInfoList")
	private List<TaxItem> taxInfoList;


}
