/**
 * @Title: EF01110180VO.java
 * @Description: TODO
 * @author yanghu.luo
 * @date 2022年8月12日
 */

package com.minigod.zero.trade.vo.resp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: EF01110180VO
 * @Description: TODO
 * @author yanghu.luo
 * @date 2022年8月12日
*/
@Data
public class EF01110180VO extends FundJourRecord implements Serializable{


	/**
	 * 保证金比例
	 */
	@JSONField(name="mv_ratio")
	private String mvRatio;
	/**
	 * 追加保证金
	 */
	@JSONField(name="margin_call")
	private String marginCall;

	/**
	 * 警示值
	 */
	private String warn;
	/**
	 * 追收值
	 */
	private String recovery;
	/**
	 * 平仓值
	 */
	private String closePosition;
}
