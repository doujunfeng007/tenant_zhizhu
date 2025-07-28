package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 开户申请信息表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Data
@TableName("acct_open_info")
@ApiModel(value = "AcctOpenInfo对象", description = "开户申请信息表")
public class AcctOpenInfoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long custId;
    /**
     * 邀请人
     */
    @ApiModelProperty(value = "邀请人")
    private Integer inviter;
    /**
     * 客户信息
     */
    @ApiModelProperty(value = "客户信息")
    private String jsonInfo;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    /**
     * 推送是否接收正常
     */
    @ApiModelProperty(value = "推送是否接收正常")
    private String pushRecved;
    /**
     * 推送返回信息
     */
    @ApiModelProperty(value = "推送返回信息")
    private String returnCode;
    /**
     * 开户返回账户ID
     */
    @ApiModelProperty(value = "开户返回账户ID")
    private String openAccountAcceptId;
    /**
     * 0:开户成功，1:开户中，2:开户失败,3:基本资料错误，4:影像资料错误，5:基本资料和影像资料错误
     */
    @ApiModelProperty(value = "0:开户成功，1:开户中，2:开户失败,3:基本资料错误，4:影像资料错误，5:基本资料和影像资料错误")
    private String openStatus;
    /**
     * 开户日期
     */
    @ApiModelProperty(value = "开户日期")
    private Date openDate;
    /**
     * 交易账号
     */
    @ApiModelProperty(value = "交易账号")
    private String tradeAccount;
    /**
     * 是否领取奖励
     */
    @ApiModelProperty(value = "是否领取奖励")
    private Integer isReward;
    /**
     * 是否发放双12奖励
     */
    @ApiModelProperty(value = "是否发放双12奖励")
    private Integer isSendActivityReward;
    /**
     * 是否发放商务渠道活动奖励
     */
    @ApiModelProperty(value = "是否发放商务渠道活动奖励")
    private Integer isSendBusinessActivityReward;
    /**
     * 是否发放翻牌活动奖励
     */
    @ApiModelProperty(value = "是否发放翻牌活动奖励")
    private Integer isSendFlopActivityReward;
    /**
     * 失败次数
     */
    @ApiModelProperty(value = "失败次数")
    private Integer errCnt;
    /**
     * 是否发送消息
     */
    @ApiModelProperty(value = "是否发送消息")
    private Integer isSend;
    /**
     * 1:H5开户 2:APP开户
     */
    @ApiModelProperty(value = "1:H5开户 2:APP开户")
    private Integer openAccountAccessWay;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    private String idNo;
    /**
     * 开户结果描述
     */
    @ApiModelProperty(value = "开户结果描述")
    private String openResult;
    /**
     * 是否允许提交(控制前端按钮重复提交) 0:否 1:是
     */
    @ApiModelProperty(value = "是否允许提交(控制前端按钮重复提交) 0:否 1:是")
    private Integer isApply;
    /**
     * 活动ID
     */
    @ApiModelProperty(value = "活动ID")
    private Integer actId;
    /**
     * 开户方式：1、线上预约开户，2、线下（开户宝）3、香港预约开户
     */
    @ApiModelProperty(value = "开户方式：1、线上预约开户，2、线下（开户宝）3、香港预约开户")
    private Integer openType;
    /**
     * CA认证PIN码
     */
    @ApiModelProperty(value = "CA认证PIN码")
    private String pinNo;
    /**
     * 开户PDF文件地址
     */
    @ApiModelProperty(value = "开户PDF文件地址")
    private String openAccountPdfUrl;
    /**
     * CA认证状态回调任务状态 0否 1是
     */
    @ApiModelProperty(value = "CA认证状态回调任务状态 0否 1是")
    private Integer caPushRecved;
    /**
     * CA认证状态
     */
    @ApiModelProperty(value = "CA认证状态")
    private Integer caVerifyStatus;
    /**
     * CA认证状态描述
     */
    @ApiModelProperty(value = "CA认证状态描述")
    private String caVerifyMsg;
    /**
     * CA认证次数
     */
    @ApiModelProperty(value = "CA认证次数")
    private Integer caVerifyCnt;
    /**
     * CA认证文件HASH值
     */
    @ApiModelProperty(value = "CA认证文件HASH值")
    private String caVerifyFileHash;
    /**
     * 修改邮箱
     */
    @ApiModelProperty(value = "修改邮箱")
    private String upEmail;
    /**
     * CA认证签署文件路径
     */
    @ApiModelProperty(value = "CA认证签署文件路径")
    private String caVerifyFileUrl;
    /**
     * CA认证文件ID
     */
    @ApiModelProperty(value = "CA认证文件ID")
    private String caVerifyFileId;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String bcan;
    /**
     * 跳转码
     */
    @ApiModelProperty(value = "跳转码")
    private String jumpCode;

}
