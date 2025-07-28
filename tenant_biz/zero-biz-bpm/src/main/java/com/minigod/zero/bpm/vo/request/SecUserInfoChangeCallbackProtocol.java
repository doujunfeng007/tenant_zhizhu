package com.minigod.zero.bpm.vo.request;


import lombok.Data;

import java.io.Serializable;

@Data
public class SecUserInfoChangeCallbackProtocol implements Serializable {

	private String applicationId; // 预约号
    private Integer checkStatus; // 线上修改资料状态 0：未提交；1：已提交；2：审核通过；3：审核拒绝
    private String backReason; //退回理由
    private Integer userId;
    private String ClientName;


}
