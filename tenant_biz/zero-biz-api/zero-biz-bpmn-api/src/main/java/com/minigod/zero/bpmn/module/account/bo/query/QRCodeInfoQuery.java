package com.minigod.zero.bpmn.module.account.bo.query;

import lombok.Data;

/**
 * @ClassName: QRCodeInfoQuery
 * @Description:
 * @Author chenyu
 * @Date 2022/8/31
 * @Version 1.0
 */
@Data
public class QRCodeInfoQuery {
    private String activityCode;
    private String qrcodeBelong;
    private String inviteCode;
    private String channelId;
}
