package com.minigod.zero.dbs.vo;

import com.minigod.zero.dbs.bo.DbsBaseRequestVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 日报表
 * @author: chenyu
 * @date: 2021/05/06 10:11
 * @version: v1.0
 */
@Data
@ApiModel("日报表查询条件")
public class Camt053ReportRequestVO extends DbsBaseRequestVO implements Serializable {

    /** 消息流水号 */
    @ApiModelProperty(value = "消息流水号")
    private String msgId;
    /** 账号 */
    @ApiModelProperty(value = "银行账号")
    private String accountNo;
    /** 币种 */
    @ApiModelProperty(value = "币种[CNY-人民币 USD-美元 HKD-港币]")
    private String accountCcy;
    /** 交易日期 格式：20210528 */
    @ApiModelProperty(value = "交易日期 格式：20210528")
    private String bizDate;

}
