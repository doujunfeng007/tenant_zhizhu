/**
 * @Title: EF01110180VO.java
 * @Description: TODO
 * @author yanghu.luo
 * @date 2022年8月12日
 */

package com.minigod.zero.trade.hs.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @ClassName: EF01110180VO
 * @Description: TODO
 * @author yanghu.luo
 * @date 2022年8月12日
*/
@Data
public class EF01110180VO extends FundJourRecord implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 保证金比例
	 */
	@JSONField(name="mv_ratio")
	private BigDecimal mvRatio;
	/**
	 * 追加保证金
	 */
	@JSONField(name="margin_call")
	private BigDecimal marginCall;

	/**
	 * 风险水平
	 * A.【安全】：保证金比例<警示值，警示值由恒生柜台配置，该值可能会发生变动，因此要求该数值可灵活配置。
	 * 当前警示值设为：80%
	 * B.【警示】：警示值<-保证金比例<追收值，追收值由恒生柜台配置，该值可能会发生调整，因此要求该数值可灵活配置。
	 * 当前追收值设为：100%
	 * C.【危险】：追收值＜-保证金比例<平仓值，平仓值由恒生柜台配置，该值可能会发生调整，因此要求该数值可灵活配置。
	 * 当前平仓值设为：120%
	 * D.【平仓】：保证金比例>=平仓值。
	 */
	private String riskLevel;
}
