package com.minigod.zero.customer.entity;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName cost_package
 */
@Data
public class CostPackage implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     * 套餐编号
     */
    private String number;

    /**
     * 套餐名称
     */
    private String packageName;

	private String packageNameHk;

	private String packageNameEn;

    /**
     * 1开启，2停用
     */
    private Integer status;

    /**
     *
     */
    private Date createTime;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 备注
     */
    private String remark;

	private String remarkHk;

	private String remarkEn;

    /**
     * 创建人id
     */
    private Long createrId;

    /**
     * 创建人名称
     */
    private String createrName;

    /**
     *
     */
    private Date updateTime;

    /**
     * 修改人id
     */
    private Long updaterId;

    /**
     * 修改人名称
     */
    private String updaterName;
	/**
	 * 是否是默认套餐，0不是，1是
	 */
	private Integer isDefault;

    private static final long serialVersionUID = 1L;

}
