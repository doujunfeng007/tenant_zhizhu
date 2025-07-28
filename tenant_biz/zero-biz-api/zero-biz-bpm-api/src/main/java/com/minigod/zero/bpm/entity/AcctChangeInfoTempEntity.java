package com.minigod.zero.bpm.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户证券资料修改缓存表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Data
@TableName("acct_change_info_temp")
@ApiModel(value = "AcctChangeInfoTemp对象", description = "客户证券资料修改缓存表")
public class AcctChangeInfoTempEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
	@JSONField(serializeUsing = ToStringSerializer.class)
	private Long id;
    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long custId;
    /**
     * 修改资料缓存
     */
    @ApiModelProperty(value = "修改资料缓存")
    private String jsonInfo;
    /**
     * 图片列表
     */
    @ApiModelProperty(value = "图片列表")
    private String imgList;
    /**
     * 1：中文/拼音/英文名；2：现时住址/通讯地址；3：职业信息与财务状况；4：投资经验与目标；5：税务信息；6：身份资料申报；7：现时地址；8：通讯地址
     */
    @ApiModelProperty(value = "1：中文/拼音/英文名；2：现时住址/通讯地址；3：职业信息与财务状况；4：投资经验与目标；5：税务信息；6：身份资料申报；7：现时地址；8：通讯地址")
    private Integer step;
    /**
     * 0：未提交；1：已提交；2：审核通过；3：审核拒绝
     */
    @ApiModelProperty(value = "0：未提交；1：已提交；2：审核通过；3：审核拒绝")
    private Integer checkStatus;
    /**
     * 修改结果描述
     */
    @ApiModelProperty(value = "修改结果描述")
    private String changeResult;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    /**
     * 推送是否接收正常
     */
    @ApiModelProperty(value = "推送是否正常 0-否 1-是")
    private String pushRecved;
    /**
     * 返回的修改订单号
     */
    @ApiModelProperty(value = "返回的修改流水号")
    private String appointmentId;
    /**
     * 失败次数
     */
    @ApiModelProperty(value = "失败次数")
    private Integer errCnt;

}
