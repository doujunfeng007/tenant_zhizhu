package com.minigod.zero.bpmn.module.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.time.LocalDateTime;


/**
 *  实体类
 *
 * @author Chill
 */
@Data
@TableName("open_account_risk_disclosure")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountRiskDisclosure对象", description = "")
public class AccountRiskDisclosureEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 预约流水号
	 */
	@ApiModelProperty(value = "预约流水号")
	private String applicationId;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private String userId;
	/**
	 * 交易账号
	 */
	@ApiModelProperty(value = "交易账号")
	private String clientId;
	/**
	 * 损失范围[ 1-最低损失，保证本金 2-中等损失，定期收益 3-高等损失，高收益 4-高等损失，资本增值 5-极高损失，极高收益 ]
	 */
	@ApiModelProperty(value = "损失范围[ 1-最低损失，保证本金 2-中等损失，定期收益 3-高等损失，高收益 4-高等损失，资本增值 5-极高损失，极高收益 ]")
	private Integer acceptableRange;
	/**
	 * 波动范围[ 1=-5%~+5% 2=-20%~+20% 3=-40%~+40% 4=-70%~+70% 5=-100%~+100% ]
	 */
	@ApiModelProperty(value = "波动范围[ 1=-5%~+5% 2=-20%~+20% 3=-40%~+40% 4=-70%~+70% 5=-100%~+100% ]")
	private Integer fluctuationRange;
	/**
	 * 投资过的产品[ 1-没有 2-存款证／定期存款/ 政府机构发行债券／外币 3-股票 (或包括美股、沪深港通股) 4- 孖展／期货／认股权证／窝轮 5-其他结构性产品 (e.g. 累计期权)) ]
	 */
	@ApiModelProperty(value = "投资过的产品[ 1-没有 2-存款证／定期存款/ 政府机构发行债券／外币 3-股票 (或包括美股、沪深港通股) 4- 孖展／期货／认股权证／窝轮 5-其他结构性产品 (e.g. 累计期权)) ]")
	private String productsInvested;
	/**
	 * 投资年期 [ 1、<1年  2、1-3年  3、4-5年  4、6-10年 5、>10年 ]
	 */
	@ApiModelProperty(value = "投资年期 [ 1、<1年  2、1-3年  3、4-5年  4、6-10年 5、>10年 ]")
	private Integer investmentHorizon;
	/**
	 * 应对措施[ 1-全面减持 2-选择性减持 3-继续维持目前组合 4-选择性增持 5-全面增持]
	 */
	@ApiModelProperty(value = "应对措施[ 1-全面减持 2-选择性减持 3-继续维持目前组合 4-选择性增持 5-全面增持]")
	private Integer solutions;
	/**
	 * 关注频率[ 1-从不 2-很少 3-有时 4-定时 5-经常]
	 */
	@ApiModelProperty(value = "关注频率[ 1-从不 2-很少 3-有时 4-定时 5-经常]")
	private Integer attentionFrequency;
	/**
	 * 总资产负债率[ 1、>=200% 2、100.1%-200% 3、50.1%-100% 4、20.1%-50% 5、<=20%]
	 */
	@ApiModelProperty(value = "总资产负债率[ 1、>=200% 2、100.1%-200% 3、50.1%-100% 4、20.1%-50% 5、<=20%] ")
	private Integer totalGearingRatio;
	/**
	 * 支撑年限[ 1、<2年 2、2-6年 3、6-10年 4、10-15年 5、>15年]
	 */
	@ApiModelProperty(value = "支撑年限[ 1、<2年 2、2-6年 3、6-10年 4、10-15年 5、>15年]")
	private String supportYears;
	/**
	 * 风险总分
	 */
	@ApiModelProperty(value = "风险总分")
	private String totalRiskCore;

}
