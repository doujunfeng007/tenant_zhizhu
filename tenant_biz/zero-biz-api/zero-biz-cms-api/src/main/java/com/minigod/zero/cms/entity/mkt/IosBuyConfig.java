package com.minigod.zero.cms.entity.mkt;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

/**
 * ios购买配置对象 mkt_ios_buy_config
 *
 * @author bpmx
 * @date 2022-03-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("mkt_ios_buy_config")
@Alias("cmsIosBuyConfig")
public class IosBuyConfig extends BaseEntity {
	private static final long serialVersionUID = 1L;


	/**
	 * 状态 0不可支付 1 可支付
	 */
	@ExcelProperty("状态 0不可支付 1 可支付 ")
	private Integer iosStatus;


}
