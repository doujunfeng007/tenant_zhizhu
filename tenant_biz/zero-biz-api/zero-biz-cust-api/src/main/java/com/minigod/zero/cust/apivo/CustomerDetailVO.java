package com.minigod.zero.cust.apivo;


import com.minigod.zero.cust.apivo.resp.CapitalAccountVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/23 19:19
 * @description：
 */
@Data
public class CustomerDetailVO implements Serializable {
	private static final long serialVersionUID = -1L;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 头像
	 */
	private String userIcon;
	/**
	 * 用户id
	 */
	private Long custId;
	/**
	 * 用户签名
	 */
	private String signature;
	/**
	 * 性别
	 */
	private Integer gender;
	/**
	 * 手机号
	 */
	private String phoneNum;
	/**
	 * 区号
	 */
	private String areaCode;
	/**
	 * 开户手机号
	 */
	private String openPhoneNum;
	/**
	 * 区号
	 */
	private String openAreaCode;
	/**
	 * 电子邮箱
	 */
	private String custEmail;
	/**
	 * 邀请人id
	 */
	private Long invCustId;
	/**
	 * 邀请渠道
	 */
	private String custChannel;

	/**
	 * 是否开通美股
	 */
	private Integer usAccountState;
	/**
	 * 同一账号
	 */
	private String accountId;
	/**
	 * 基金交易账号
	 */
	private String tradeAccount;
	/**
	 * 基金资金账号
	 */
	private List<CapitalAccountVO> capitalAccounts;
	/**
	 * 活利得交易账号
	 */
	private String hldTradeAccount;
	/**
	 * 活利得资金账号
	 */
	private List<CapitalAccountVO> hldCapitalAccounts;
	/**
	 * 债券易交易账号
	 */
	private String bondTradeAccount;
	/**
	 * 债券易资金账号
	 */
	private List<CapitalAccountVO> bondCapitalAccounts;
	/**
	 *隐私开关
	 */
	private String privacy;

	/**
	 * 是否为大陆：0 非大陆 1 大陆
	 */
	private boolean mainland;

	/**
	 * 注册时间
	 */
	private Date createTime;
	/**
	 * pi等级
	 */
	private Integer piLevel;

	private Integer accountType;

	private Integer accountLevel;

	private Boolean hasLoginPass;

	private Integer riskType;

	private Integer hldRiskType;

	private Integer bondRiskType;

	/**
	 * 0不校验pi等级可见活利得，1校验是否开通PI
	 */
	private String hldPermission;
	/**
	 * 是否修改过交易密码 0 未修改，1修改
	 */
	private Integer isUpdateTradePwd;
	/**
	 * 是否需要做2fa ture 需要做，false不需要
	 */
	private Boolean need2fa;
}
