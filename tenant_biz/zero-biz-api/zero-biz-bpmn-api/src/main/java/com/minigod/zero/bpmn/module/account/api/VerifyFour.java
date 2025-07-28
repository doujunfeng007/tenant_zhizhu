package com.minigod.zero.bpmn.module.account.api;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: VerifyFourBo
 * @Description:
 * @Author chenyu
 * @Date 2022/8/15
 * @Version 1.0
 */
@Data
public class VerifyFour implements Serializable {
    private static final long serialVersionUID = 3292358324087632087L;
	private String phoneArea;
    private String phoneNumber;
    private String bankNo;
    private String idNo;
    private String name;
    private Long userId;
    private Date verifyTime;
    private Date liveVerifyTime;
}
