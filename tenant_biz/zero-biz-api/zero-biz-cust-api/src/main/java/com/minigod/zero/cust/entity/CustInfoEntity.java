package com.minigod.zero.cust.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 客户信息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Data
@TableName("cust_info")
@ApiModel(value = "CustInfo对象", description = "客户信息表")
@EqualsAndHashCode(callSuper = true)
public class CustInfoEntity extends BaseEntity {

    /**
     * 客户昵称
     */
    @ApiModelProperty(value = "客户昵称")
    private String nickName;

	/**
	 * 租户id
	 */
	@ApiModelProperty(value = "租户id")
	private String tenantId;
    /**
     * 客户签名
     */
    @ApiModelProperty(value = "客户签名")
    private String signature;
    /**
     * 客户性别(1男，0女)
     */
    @ApiModelProperty(value = "客户性别(1男，0女)")
    private Integer gender;
    /**
     * 客户头像
     */
    @ApiModelProperty(value = "客户头像")
    private String custIcon;
    /**
     * 推荐渠道
     */
    @ApiModelProperty(value = "推荐渠道")
    private String custChannel;
    /**
     * 推荐人ID,邀请该客户的客户ID
     */
    @ApiModelProperty(value = "推荐人ID,邀请该客户的客户ID")
    private Long invCustId;
    /**
     * 客户职业
     */
    @ApiModelProperty(value = "客户职业")
    private Integer vocationId;
    /**
     * 客户类型：0-游客 1-普通个户 2-认证投顾 3-官方账号 4-公司授权人 5-存量ESOP客户
     */
    @ApiModelProperty(value = "客户类型：0-游客 1-普通个户 2-认证投顾 3-官方账号 4-公司授权人 5-存量ESOP客户")
    private Integer custType;

    /**
     * 客户密码
     */
    @ApiModelProperty(value = "客户密码")
    private String password;
    /**
     * 隐私开关设置 每一位都用Y/N表示是否开通：第1位：是否允许将我引荐给别人 第2位：是否允许通过手机号搜索到我（控制手机号搜索、通讯录匹配） 第3位：是否接收资讯推送 第4位：加我为联系人时需要验证 第5位:是否开启自选股排序 第6位:是否开启交易通知提醒; 第7位：新股认购弹窗；第8位：交易股票是否添加到自选；第9位：是否同意隐私协议
     */
    @ApiModelProperty(value = "隐私开关设置 每一位都用Y/N表示是否开通：第1位：是否允许将我引荐给别人 第2位：是否允许通过手机号搜索到我（控制手机号搜索、通讯录匹配） 第3位：是否接收资讯推送 第4位：加我为联系人时需要验证 第5位:是否开启自选股排序 第6位:是否开启交易通知提醒; 第7位：新股认购弹窗；第8位：交易股票是否添加到自选；第9位：是否同意隐私协议")
    private String privacy;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;
    /**
     * 最后登录IP地址
     */
    @ApiModelProperty(value = "最后登录IP地址")
    private String lastLoginIp;
    /**
     * 最后登录的城市ID
     */
    @ApiModelProperty(value = "最后登录的城市ID")
    private Integer lastCityId;
    /**
     * 总登录次数
     */
    @ApiModelProperty(value = "总登录次数")
    private Long loginCount;
	/**
	 * 微信开发平台授权ID
	 */
	private String wechatId;
    /**
     * 第三方IM平台的ID
     */
    @ApiModelProperty(value = "第三方IM平台的ID")
    private String imId;
    /**
     * 第三方IM平台的密码
     */
    @ApiModelProperty(value = "第三方IM平台的密码")
    private String imPwd;
    /**
     * 客户密码停用的时间
     */
    @ApiModelProperty(value = "客户密码停用的时间")
    private Date lockTime;
    /**
     * 乐观锁版本号
     */
    @ApiModelProperty(value = "乐观锁版本号")
    private Integer lockVersion;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String cellPhone;
    /**
     * 国际区号
     */
    @ApiModelProperty(value = "国际区号")
    private String areaCode;

    /**
     * 手势密码
     */
    @ApiModelProperty(value = "手势密码")
    private String gesturePwd;
    /**
     * 从后台切换到前台需要输入手势密码的时长限制
     */
    @ApiModelProperty(value = "从后台切换到前台需要输入手势密码的时长限制")
    private Integer getstureShowTime;
    /**
     * 注册来源：1-web 2-ios 3-aos 4-pc 5-h5
     */
    @ApiModelProperty(value = "注册来源")
    private Integer regSourceType;
    /**
     * 注册渠道：0=未知 1=互联网 2=线下开户 3-公司授权
     */
    @ApiModelProperty(value = "注册渠道")
    private Integer regChannel;
    /**
     * 0:未自动关注 1:已自动关注
     */
    @ApiModelProperty(value = "0:未自动关注 1:已自动关注")
    private Integer robotAttentionStatus;
    /**
     * 是否写入手机号
     */
    @ApiModelProperty(value = "是否写入手机号")
    private Integer isWritePhone;
    /**
     * 客户是否接受协议 0:不接受，1:接受
     */
    @ApiModelProperty(value = "客户是否接受协议 0:不接受，1:接受")
    private Integer acceptAgreement;

    /**
     * 注册邮箱
     */
    @ApiModelProperty(value = "注册邮箱")
    private String cellEmail;
    /**
     * 是否同意
     */
    @ApiModelProperty(value = "是否同意")
    private Integer isAgree;
    /**
     * facebook/intagram
     */
    @ApiModelProperty(value = "facebook/intagram")
    private String facebookName;

	@ApiModelProperty(value = "关联客户ID")
	private Long bindCust;

	@ApiModelProperty(value = "注销时间")
	private Date cancelTime;

	@ApiModelProperty(value = "公司授权人ID")
	private String authorId;

	@ApiModelProperty(value = "公司授权人证件号")
	private String authorIdno;

	@ApiModelProperty(value = "公司授权人注册类型：1-手机，2-邮箱")
	private Integer registType;

	@ApiModelProperty(value = "公司授权人交易密码")
	private String tradePwd;

	@ApiModelProperty(value = "状态(客户状态 0-停用,1-正常,2-锁定,3-注销)")
	private Integer status;
}
