package com.minigod.zero.customer.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.apivo.resp.UserTailVO
 * @Date: 2023年02月18日 17:36
 * @Description:
 */
@Data
public class CustDetailVO implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "用户昵称")
	private String nickName;

	@ApiModelProperty(value = "用户头像")
	private String userIcon;

	@ApiModelProperty(value = "用户Id")
	private Long custId;

	@ApiModelProperty(value = "客户签名")
	private String signature;

	@ApiModelProperty(value = "性别")
	private Integer gender;

	@ApiModelProperty(value = "注册手机号")
	private String phoneNum;

	@ApiModelProperty(value = "注册手机区号")
	private String areaCode;

	@ApiModelProperty(value = "开户手机号")
	private String openPhoneNum;

	@ApiModelProperty(value = "开户手机区号")
	private String openAreaCode;

	@ApiModelProperty(value = "登录邮箱")
	private String custEmail;

	@ApiModelProperty(value = "开户邮箱")
	private String openCustEmail;

	@ApiModelProperty(value = "推荐人ID,邀请该用户的用户ID")
	private Long invCustId;

	@ApiModelProperty(value = "推荐渠道")
	private String custChannel;

	@ApiModelProperty(value = "交易帐号")
	private String tradeAccount;

	@ApiModelProperty(value = "美股账户开通状态")
	private Integer usAccountState;

	@ApiModelProperty(value = "衍生品开通状态")
	private Integer derivativeState;

	@ApiModelProperty(value = "期货账户")
	private String futuresAccount;

	@ApiModelProperty(value = "是否已激活期货账户[0-未激活 1-已激活]")
	private Integer enabledFutures;

	@ApiModelProperty(value = "资金账号")
	private List<CapitalAccountVO> capitalAccounts;

	@ApiModelProperty(value = "基金账号")
	private String fundAccount;

	@ApiModelProperty(value = "是否需要弹出美股声明")
	private boolean investorStmt;

	@ApiModelProperty(value = "是否专业投资者")
	private boolean investor;

	@ApiModelProperty(value = "中华通码")
	private String bcan;

	@ApiModelProperty(value = "中华通状态：0.未开通，1.申请中，2.已认证，3.已拒绝，4.已撤销")
	private String bStatus;

	@ApiModelProperty(value = "隐私开关")
	private String privacy;

	@ApiModelProperty(value = "是否已设置登录密码")
	private boolean hasLoginPass;

	@ApiModelProperty(value = "是否显示中华通")
	private boolean hideA;

	@ApiModelProperty(value = "基金是否可以开户")
	private boolean wealthCanOpen;

	@ApiModelProperty(value = "是否基金单边户")
	private boolean wealthOnlyFlag;

	@ApiModelProperty(value = "是否为大陆：0 非大陆 1 大陆")
	private boolean mainland;

	@ApiModelProperty(value = "账户等级[0-未知 1-预批账户 2-非标准账户 3-标准账户]")
	private Integer accountLevel;


	// 以下字段赋值逻辑待实现
	@ApiModelProperty(value = "是否绑定掌上令牌 on:绑定, off:解绑")
	private String isBindType = "off";

	//@ApiModelProperty(value = "是否启用 1:表示启用,0:禁用")
	//private Integer isEnabledType = 0;

	@ApiModelProperty(value = "是否常用设备 1:是,0:不是")
	private Integer isLongTimeDevice = 1;

	@ApiModelProperty(value = "绑定设备型号")
	private String bindDeviceModel;

	@ApiModelProperty(value = "是否超过7天 1:是,0:不是")
	private Integer isOver7Day = 0;

	@ApiModelProperty(value = "认证头衔 1.认证用户返回认证头衔 2.非认证用户不返回")
	private String vTitle;

	@ApiModelProperty(value = "基金评测等级：1.保守型 2.稳健型 3.平衡型,4.增长型,5.进取型")
	private Integer riskType;

	private String issueCountry;

	@ApiModelProperty(value = "账号类型：1-个人 2-联名 3-公司 4-ESOP")
	private Integer acctType;

	@ApiModelProperty(value = "0-不可绑定，不可切换  1-可绑定个人户或ESOP户  2-可进行个人/ESOP账号切换")
	private Integer esopStatus;

	@ApiModelProperty(value = "注册时间")
	private Date createTime;

	@ApiModelProperty(value = "是否同意暗盘协议，0-否，1-同意")
	private String isAgreeGreyTradeProtocol;

	@ApiModelProperty(value = "股票转出标识: true-是, false-否")
	private boolean outStock;
}
