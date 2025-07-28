package com.minigod.zero.bpmn.module.withdraw.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import lombok.Data;

/**
 * 客户电汇取款信息对象 client_teltransfer_info
 *
 * @author chenyu
 * @date 2023-04-06
 */
@Data
@TableName("client_teltransfer_info")
public class ClientTeltransferInfo extends BaseEntity {


    /**
     * 自增ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 流水号
     */
    private String applicationId;
    /**
     * 客户帐号
     */
    private String clientId;
    /**
     * 资金帐号
     */
    private String fundAccount;
    /**
     * 收款银行代码
     */
    private String bankCode;
    /**
     * 收款银行名称
     */
    private String bankName;
    /**
     * 收款银行帐户
     */
    private String bankAcct;
    /**
     * 国际[0-中国大陆 2-海外]
     */
    private String nationality;
    /**
     * 收款银行省份id
     */
    private Long provinceId;
    /**
     * 收款银行省份
     */
    private String provinceName;
    /**
     * 收款银行城市ID
     */
    private Long cityId;
    /**
     * 收款银行城市名称
     */
    private String cityName;
    /**
     * 收款SWIFT代码
     */
    private String swiftCode;
    /**
     * 是否可见[0-否 1-是]
     */
    private String isVisible;
    /**
     * 国家
     */
    private String country;
    /**
     * 地址
     */
    private String address;
}
