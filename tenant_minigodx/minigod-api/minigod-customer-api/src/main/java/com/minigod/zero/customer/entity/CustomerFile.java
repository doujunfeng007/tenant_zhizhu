package com.minigod.zero.customer.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minigod.zero.customer.emuns.StatementEnums;
import lombok.Data;

/**
 *
 * @author dell
 * @TableName customer_file
 */
@TableName(value ="customer_file")
@Data
public class CustomerFile implements Serializable {
    /**
     *
     */
    @TableId
    private Long id;

    /**
     * 用户id
     */
    private Long custId;

    /**
     *  类型  2月结单  1日结单
     */
    private Integer type;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
	/**
	 * 结单发送状态  0未发送 1已发送 2发送失败 3发送中
	 * * {@link StatementEnums.FileSendStatus}
	 */
	private Integer status;
	private String errorMsg;
	/**
	 * 文件名
	 */
	private String fileName;
	/**
	 * 结单类型：0基金 1股票
	 */
	private Integer statementType;


	@TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
