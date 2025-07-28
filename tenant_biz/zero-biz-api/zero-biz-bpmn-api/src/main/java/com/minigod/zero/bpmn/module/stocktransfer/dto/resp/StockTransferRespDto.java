package com.minigod.zero.bpmn.module.stocktransfer.dto.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StockTransferRespDto implements Serializable {

    private Integer id;

    private Long accImgId;

    private String accountName;

    private String accountNumber;

    private String backPerson;

    private String backReason;

    private String cashName;

    private String ccass;

    private String contactsPhonenum;

    private String createdTime;

    private String inviter;

    private Integer isFind;

    private Integer isReward;

    private Integer isShares;

    private String receiveAccount;

    private String receiveSec;

    private String rolloutContacts;

    private Integer status;

    private String tradeAccount;

    private Integer transferState;

    private List<CashSharesRespDto> sharesData;

	private Integer transferType;


}
