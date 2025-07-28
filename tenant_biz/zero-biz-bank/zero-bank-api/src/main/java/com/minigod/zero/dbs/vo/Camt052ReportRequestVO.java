package com.minigod.zero.dbs.vo;

import com.minigod.zero.dbs.bo.DbsBaseRequestVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 小时报表
 * @author: chenyu
 * @date: 2021/05/06 10:11
 * @version: v1.0
 */
@ApiModel("小时报表查询条件")
@Data
public class Camt052ReportRequestVO extends DbsBaseRequestVO implements Serializable {

    @ApiModelProperty(value = "消息流水号")
    private String msgId;
    @ApiModelProperty(value = "银行账号")
    private String accountNo;
    @ApiModelProperty(value = "币种[CNY-人民币 USD-美元 HKD-港币]")
    private String accountCcy;

}
