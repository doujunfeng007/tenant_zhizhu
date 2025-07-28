package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.io.Serializable;

/**
 * 基金账户信息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("bpm_fund_acct_info")
@ApiModel(value = "BpmFundAcctInfo对象", description = "基金账户信息表")
public class BpmFundAcctInfoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
	@TableId(
		value = "id",
		type = IdType.ASSIGN_ID
	)
    private Long id;
    /**
     * 交易账户
     */
    @ApiModelProperty(value = "交易账户")
    private String tradeAccount;
    /**
     * 基金主账户
     */
    @ApiModelProperty(value = "基金主账户")
    private String fundAccountMain;
    /**
     * 基金子账户
     */
    @ApiModelProperty(value = "基金子账户")
    private String fundAccount;
	/**
	 * 基金账户类型[0-个人户 1-机构户]
	 */
	@ApiModelProperty(value = "基金账户类型[0-个人户 1-机构户]")
	private Integer fundAccountType;
    /**
     * 基金账户权限：0-不可申购和赎回 1-可申购可赎回 2-可申购不可赎回 3-不可申购可赎回
     */
    @ApiModelProperty(value = "基金账户权限：0-不可申购和赎回 1-可申购可赎回 2-可申购不可赎回 3-不可申购可赎回")
    private Integer fundOperType;
    /**
     * 基金账户类型 0:正常
     */
    @ApiModelProperty(value = "基金账户类型 0:正常")
    private Integer accountStatus;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
