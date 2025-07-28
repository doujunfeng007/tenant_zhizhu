package com.minigod.zero.trade.vo.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: IFundAccount
 * @Description:
 * @Author chenyu
 * @Date 2024/3/2
 * @Version 1.0
 */
@Data
public class IFundAccountBo implements Serializable {
    private String company = "GUOSEN";
    private String extAccountId;
    private Integer riskScore;
}
