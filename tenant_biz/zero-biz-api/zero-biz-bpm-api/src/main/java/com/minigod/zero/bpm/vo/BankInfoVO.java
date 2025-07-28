package com.minigod.zero.bpm.vo;

import com.minigod.zero.bpm.entity.BpmOtherBankInfoEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 区域入金银行列表 视图实体类
 *
 * @author wengzejie
 * @since 2023-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BankInfoVO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "银行列表")
	private List<BpmOtherBankInfoEntity> items;
}
