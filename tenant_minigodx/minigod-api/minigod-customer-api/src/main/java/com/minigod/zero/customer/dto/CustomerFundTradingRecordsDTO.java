package com.minigod.zero.customer.dto;

import com.minigod.zero.customer.entity.CustomerFundTradingRecordsEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 客户交易流水汇总表 数据传输对象实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerFundTradingRecordsDTO extends CustomerFundTradingRecordsEntity {
	private static final long serialVersionUID = 1L;

}
