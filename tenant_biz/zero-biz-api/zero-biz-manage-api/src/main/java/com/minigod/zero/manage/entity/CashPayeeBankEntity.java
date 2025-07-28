package com.minigod.zero.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 付款、收款银行信息 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("cash_payee_bank")
@ApiModel(value = "CashPayeeBank对象", description = "付款、收款银行信息")
public class CashPayeeBankEntity extends TenantEntity {

	private static final long serialVersionUID = 1L;

    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称")
    private String bankName;
	/**
	 * 银行名称繁体
	 */
	@ApiModelProperty(value = "银行名称繁体")
	private String bankNameHant;
    /**
     * 银行名称英文
     */
    @ApiModelProperty(value = "银行名称英文")
    private String bankNameEn;
    /**
     * 国际编号
     */
    @ApiModelProperty(value = "国际编号")
    private String bankId;
	/**
	 * 银行卡类型(1.收款银行 2.付款银行)
	 */
	@ApiModelProperty(value = "银行卡类型(1.收款银行 2.付款银行)")
	private Integer bankType;
    /**
     * SWIFT编码
     */
    @ApiModelProperty(value = "SWIFT编码")
    private String swiftCode;
    /**
     * 银行地址
     */
    @ApiModelProperty(value = "银行地址")
    private String address;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createUserName;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private String updateUserName;
    /**
     * 是否启用
     */
    @ApiModelProperty(value = "是否启用")
    private Boolean enable;
    /**
     * 银行编号
     */
    @ApiModelProperty(value = "银行编号")
    private String bankCode;
    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;

	/**
	 * 分行号集合,逗号相隔
	 */
	@ApiModelProperty(value = "分行号集合,逗号相隔")
	private String bankIdQuick;

}
