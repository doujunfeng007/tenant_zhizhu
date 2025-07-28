package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.vo.stock.CustomerStockInfo;
import io.swagger.annotations.ApiModelProperty;
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
public class AppCustomerDetailVO implements Serializable {
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
	 * 债券账号
	 */
	private String debentureTradingAccount;
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
	/**
	 * 是否修改登录密码
	 */
	private Boolean hasLoginPass;
	/**
	 * 客户风险等级
	 */
	private Integer riskType;
	/**
	 * 活利得风险等级
	 */
	private Integer hldRiskType;
	/**
	 * 债券易风险等级
	 */
	private Integer bondRiskType;

	/**
	 * 0不校验pi等级可见活利得，1校验是否开通PI
	 */
	private Integer hldPermission;
	/**
	 * 是否修改过交易密码 0 未修改，1修改
	 */
	private Integer isUpdateTradePwd;
	/**
	 * 是否需要做2fa ture 需要做，false不需要
	 */
	private Boolean need2fa;
	/***
	 * 用户类型，0普通用户·1机构用户
	 */
	private Integer customerType;

	/**
	 * 客户名称，默认取中文名字，没有取英文名字
	 */
	private String customerName;
	/**
	 * 英文名
	 */
	private String customerEnName;
	/**
	 * 是否做过衍生品调查1做过，0为做过
	 */
	private Integer derivative;
	/**
	 * 5预批账号，0正常账号
	 */
	private Integer financingAccountStatus;

	/**
	 * {@link com.minigod.zero.customer.emuns.CustomerStatus}
	 */
	private Integer status;
	/**
	 * 风险测评过期时间
	 */
	private Date riskExpiryDate;
	/**
	 * 实名认证 证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]
	 */
	private String idKind;
	/**
	 * 实名认证 证件号码
	 */
	private String idCard;

	/**
	 * 开户时间
	 */
	private Date openAccountTime;
	/**
	 * 是否開戶
	 */
	private boolean isOpenAccount = false;

	private CustomerStockInfo customerStockInfo;

}
