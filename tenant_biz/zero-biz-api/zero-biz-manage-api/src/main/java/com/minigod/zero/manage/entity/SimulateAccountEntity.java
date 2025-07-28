package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 模拟资产账户
 */
@Data
@TableName("simulate_account")
@ApiModel(value = "SimulateAccount对象", description = "模拟资产账户表")
public class SimulateAccountEntity {
	private Long id;//自增主键
	private Integer userId;//用户ID
	private Double initFund = 0.00000d;//初始资金
	private Double totalFund = 0.00000d;//总资金
	private Double enableFund = 0.00000d;//可用资金
	private String currency;//币种
	private String market;//市场
	private Integer openState = 2;//1:未开户，2:开户成功
	private Date openDate;//开户日期
	private Integer accessWay;//1:H5开户 2:APP开户
	private Integer type = 1;//账户类型 1 2 3 4 5...默认类型：1
	private Integer state = 1;// 1:正常 2:禁用
	private Integer version = 1;//版本号
	private Date createTime;//创建时间
	private Date updateTime;//更新时间

	private String accountTypeName;// 切换的账户名称
	private Integer rank;// 排行名次

	private Integer tradeCount;// 可交易次数
}
