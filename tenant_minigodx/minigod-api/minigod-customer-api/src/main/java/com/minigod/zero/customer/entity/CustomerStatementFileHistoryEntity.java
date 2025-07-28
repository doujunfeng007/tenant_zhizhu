package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *
 * @TableName customer_statement_file_history
 */
@TableName(value ="customer_statement_file_history")
@Data
public class CustomerStatementFileHistoryEntity implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 结单文件id
     */
    private Long statementFileId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 邮件工具_发送id
     */
    private String sendEmailId;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 发送次数
     */
    private Integer sendNum;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
