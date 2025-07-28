package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 区域入金银行列表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("bpm_other_bank_info")
@ApiModel(value = "BpmOtherBankInfo对象", description = "区域入金银行列表")
public class BpmOtherBankInfoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long id;
    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称")
    private String bankName;
    /**
     * 银行英文名称
     */
    @ApiModelProperty(value = "银行英文名称")
    private String bankNameEn;
    /**
     * 银行国际编号
     */
    @ApiModelProperty(value = "银行国际编号")
    private String swiftCode;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 银行机构号
     */
    @ApiModelProperty(value = "银行机构号")
    private String bankId;
    /**
     * 是否入金银行 0 出入金 1 出金 2其他
     */
    @ApiModelProperty(value = "是否入金银行 0 出入金 1 出金 2其他")
    private Integer isAmount;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createUser;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private String updateUser;
    /**
     * 注册地
     */
    @ApiModelProperty(value = "注册地")
    private String registrationPlace;
    /**
     * 是否热门
     */
    @ApiModelProperty(value = "是否热门")
    private Integer isHot;
    /**
     * 区域 2香港 1美国 3大陆
     */
    @ApiModelProperty(value = "区域 2香港 1美国 3大陆")
    private Integer bankType;

}
