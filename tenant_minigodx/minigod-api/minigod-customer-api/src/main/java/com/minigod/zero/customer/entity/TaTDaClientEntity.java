package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 同步ta库  t_da_client 的实体类
 * @TableName ta_t_da_client
 */
@TableName(value ="ta_t_da_client")
@Data
public class TaTDaClientEntity implements Serializable {
    /**
     * daAccountId+daClientId
     */
    @TableId
    private String subaccountid;

    /**
     * da 主账号 id
     */
    private String daaccountid;

    /**
     * da 下的客户 id
     */
    private String daclientid;

    /**
     * 客户ID
     */
    private String custid;

    /**
     * 理财账号
     */
    private String extaccountid;

    /**
     * 中文姓名
     */
    private String chinesename;

    /**
     * 英文名
     */
    private String firstname;

    /**
     * 英文姓
     */
    private String lastname;

    /**
     * 记录创建时间
     */
    private Date createtime;

    /**
     * 记录修改时间
     */
    private Date updatetime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
