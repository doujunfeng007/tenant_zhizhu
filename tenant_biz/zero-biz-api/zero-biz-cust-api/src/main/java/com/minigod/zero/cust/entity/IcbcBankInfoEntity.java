package com.minigod.zero.cust.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.minigod.zero.core.mp.base.AppEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 客户银行账户信息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-03-28
 */
@Data
@TableName("icbc_bank_info")
@ApiModel(value = "CustBankInfo对象", description = "客户银行账户信息表")
@EqualsAndHashCode(callSuper = true)
public class IcbcBankInfoEntity extends AppEntity {

    /**
     * 资金账号ID
     */
    @ApiModelProperty(value = "资金账号")
    private String capitalAccount;
    /**
     * 银行编号
     */
    @ApiModelProperty(value = "银行编号")
    private String bankNo;
    /**
     * 银行卡账号
     */
    @ApiModelProperty(value = "银行卡账号")
    private String bankAccount;
    /**
     * 结算币种[1港币 2 美元 3 人民币]
     */
    @ApiModelProperty(value = "结算币种[1港币 2 美元 3 人民币]")
    private Integer moneyType;
	/**
	 * 状态：0-无效/禁用 1-有效/启用
	 */
	@ApiModelProperty(value = "状态：0-无效/禁用 1-有效/启用")
	private Integer bankAcctStatus;

	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty("开户时间")
	private Date bindTime;

	/**
	 * 银行卡类型[0-香港银行卡 1-大陆银行卡 2-其他]
	 */
	@ApiModelProperty(value = "银行卡类型[0-香港银行卡 1-大陆银行卡 2-其他]")
	private Integer bankType;

}
