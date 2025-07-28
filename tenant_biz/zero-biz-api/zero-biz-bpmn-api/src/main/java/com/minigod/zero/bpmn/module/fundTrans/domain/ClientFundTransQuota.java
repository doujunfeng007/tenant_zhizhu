package com.minigod.zero.bpmn.module.fundTrans.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;


/**
 *  划拨额度对象 client_fund_trans_quota
 *
 * @author zsdp
 * @date 2024-12-25
 */
@Data
@TableName("client_fund_trans_quota")
public class ClientFundTransQuota {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    private Long id;
    /**
     * 出金账号类型
     */
    private String withdrawBusinessType;
    /**
     * 入金账号类型
     */
    private String depositBusinessType;
    /**
     * 额度
     */
    private BigDecimal quota;
    /**
     * 币种
     */
    private String currency;
    /**
     * 状态
     */
    private Integer status;
    /**
     *
     */
    private String createUser;
    /**
     *
     */
    private String updateUser;
    /**
     *
     */
    @TableLogic
    private Integer delFlag;

}
