package com.minigod.zero.bpmn.module.deposit.bo;

import lombok.Data;

/**
 * @ClassName: SecImgBo
 * @Description:
 * @Author chenyu
 * @Date 2024/3/1
 * @Version 1.0
 */
@Data
public class SecImgBo {
    private String imgBase64;
    private Integer serviceType; // 1:存入资金 2:转入股票 3:资产凭证 4:补充凭证 5：出金认证
    private Integer type;
    private Long transactId; //业务id
}
