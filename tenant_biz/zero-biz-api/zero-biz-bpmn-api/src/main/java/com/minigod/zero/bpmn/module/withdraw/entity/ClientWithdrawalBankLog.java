package com.minigod.zero.bpmn.module.withdraw.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.mp.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 收款银行登记操作日志对象 client_withdrawal_bank_log
 *
 * @author zsdp
 * @date 2023-04-09
 */
@Data
@TableName("client_withdrawal_bank_log")
public class ClientWithdrawalBankLog extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     * 自增ID
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 操作编码
     */
    private String oprcode;
    /**
     * 操作时间
     */
    private Date oprtime;
    /**
     * 操作类型
     */
    private String oprtype;
    /**
     * 渠道[MOBILE-手机 INTERNET-网厅]
     */
    private String channel;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 备注
     */
    private String remark;

}
