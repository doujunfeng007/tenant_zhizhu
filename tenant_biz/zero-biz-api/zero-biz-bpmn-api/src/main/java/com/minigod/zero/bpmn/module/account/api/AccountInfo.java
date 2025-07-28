package com.minigod.zero.bpmn.module.account.api;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: AccountInfo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/7
 * @Version 1.0
 */
@Data
public class AccountInfo implements Serializable {
    private static final long serialVersionUID = 3292358324087632087L;

    @JSONField(name = "treadMarket")
    private String[] treadMarket;
    @JSONField(name = "inviteCode")
    private String inviteCode;
    @JSONField(name = "fundAccountType")
    private Integer fundAccountType;
    private String sourceChannelId;
    private Integer openAccountType;
    private Integer openAccountAccessWay;
    private Long userId;
    private Integer bankType;
    private Integer againstDataPromotion;
    private Integer againstDataPromotionToCompany;
    private String plan;

}
