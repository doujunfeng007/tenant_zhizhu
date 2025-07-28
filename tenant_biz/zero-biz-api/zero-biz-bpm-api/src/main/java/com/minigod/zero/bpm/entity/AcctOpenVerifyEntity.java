package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * 客户认证记录表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Data
@TableName("acct_open_verify")
@ApiModel(value = "AcctOpenVerify对象", description = "客户认证记录表")
public class AcctOpenVerifyEntity implements Serializable {

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
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private Date createTime;
    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String idcard;
    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realName;
    /**
     * 银行卡
     */
    @ApiModelProperty(value = "银行卡")
    private String bankCard;
    /**
     * 银行代码
     */
    @ApiModelProperty(value = "银行代码")
    private String bankCode;
    /**
     * 风批开始时间
     */
    @ApiModelProperty(value = "风批开始时间")
    private Date riskStartTime;
    /**
     * 风批结束时间
     */
    @ApiModelProperty(value = "风批结束时间")
    private Date riskEndTime;
    /**
     * 手机信息
     */
    @ApiModelProperty(value = "手机信息")
    private String phoneInfo;
    /**
     * 客户IP
     */
    @ApiModelProperty(value = "客户IP")
    private String custIp;

}
