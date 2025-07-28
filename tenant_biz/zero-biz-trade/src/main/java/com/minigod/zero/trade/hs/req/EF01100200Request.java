package com.minigod.zero.trade.hs.req;

import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by sunline
 * Date:4/9/16
 * Time:10:12 PM
 * 交易登录请求对象
 */
@Data
public class EF01100200Request extends GrmRequestVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * 分支代码
	 */
	@NotBlank
    private String branchNo;

	/**operator
	 * 委托方式
	 */
	@NotBlank
	private String entrustWay;

	/**
	 * 站点/电话
	 */
	@NotBlank
    private String opStation;

	/**
	 * 交易账号/资金账号，input_content=1资金账号，input_content=6交易账号
	 */
	@NotBlank
    private String clientId;

	/**
	 * 交易密码
	 */
	@NotBlank
    private String password;
}
