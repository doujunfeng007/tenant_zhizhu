package com.minigod.zero.bpmn.module.edda.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @TableName dbs_edda_req_Log
 */
@TableName(value ="dbs_edda_req_log")
@Data
@Slf4j
public class DbsEddaReqLogEntity implements Serializable {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 流水号
     */
    private String applicationid;

    /**
     * 请求业务类型：initiation授权 enquiry查询
     */
    private String businesstype;

    /**
     * 请求流水号
     */
    private String msgid;

    /**
     * 申请编号
     */
    private String ddaref;

    /**
     * 设置：请求状态:0-系统异常；1-成功
     */
    private String reqstatus;

    /**
     * 银行响应状态:ACSP-查询成功；RJCT-查询失败；PART-查询成功记录超过1000
     */
    private String enqstatus;

    /**
     * 请求报文
     */
    private String reqmessage;

    /**
     * 请求时间
     */
    private Date reqtime;

    /**
     * 响应报文
     */
    private String resmessage;

    /**
     * 响应时间
     */
    private Date restime;

    /**
     * ack2响应报文
     */
    private String ackmessage;

    /**
     * ackTime
     */
    private String acktime;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 修改时间
     */
    private Date updatetime;


}
