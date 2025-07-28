package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName organization_basic_info
 */
@Data
public class OrganizationBasicInfo implements Serializable {
    /**
     *
     */
    private Integer id;

	/**
	 * 客户id
	 */
	private Long custId;
	/**
	 * 租户id
	 */
	private String tenantId;

    /**
     * 公司名称
     */
    private String name;

    /**
     * 公司地址
     */
    private String address;

    /**
     * 注册地址
     */
    private String registrationAddress;

    /**
     *
     */
    private String registrationCertificate;

    /**
     * 商业登记证
     */
    private String businessRegistrationCertificate;

    /**
     * 业务性质
     */
    private String businessNature;

    /**
     * 资金来源
     */
    private String fundingSource;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系人电话
     */
    private String contactsMobile;
	/**
	 * 区号
	 */
	@TableField(exist = false)
	private String areaCode;

    /**
     * 联系人邮箱
     */
    private String contactsEmail;

    /**
     * 开户目的，0股票基金交易，1发行产品
     */
    private String target;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行代码
     */
    private String swiftCode;

    /**
     * 银行账户名
     */
    private String accountName;

    /**
     * 银行账户号码
     */
    private String bankAccountNumber;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     *
     */
    private Integer status;

    /**
     *
     */
    private Integer isDeleted;

	/**
	 * 风险等级
	 */
	@TableField(exist = false)
	private Integer riskLevel;


	/**
	 * {@link com.minigod.zero.customer.enums.OpenChannel}
	 */
	@TableField(exist = false)
	private Integer openChannel;

    private static final long serialVersionUID = 1L;
}
