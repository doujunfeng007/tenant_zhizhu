package com.minigod.zero.biz.task.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * @author chen
 * @ClassName TableDataResp.java
 * @Description TODO
 * @createTime 2022年08月08日 15:09:00
 */
@Data
public class TableDataResp implements Serializable {

    private static final long serialVersionUID = -5272776080637943642L;

    /**
     * 申请日期
     */
    private String applyDate;

    /**
     * 板块
     */
    private String boardName;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 	公告
     */
    private String notice;

    /**
     * 	通过聆讯日期
     */
    private String passHearingDate;

    /**
     * 状态
     */
    private String status;

    /**
     * 股票代码
     */
    private String symbol;


}
