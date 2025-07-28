package com.minigod.zero.bpmn.module.account.api;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: BaseAccount
 * @Description:
 * @Author chenyu
 * @Date 2022/8/9
 * @Version 1.0
 */
@Data
public class BaseAccount implements Serializable {
    private Long userId;
    private String phoneNumber;
	private String phoneArea;
    private String bankCardNo;
    private String inviterId;
    private String sourceChannelId;
	private Integer acceptRisk;
	private Date expiryDate;
    private Integer openAccountAccessWay;
    private Integer language;
    private Integer fundAccountType;
    private Integer openAccountType;
    private String stockTypes;
    private String applicationId;
}
