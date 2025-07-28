package com.minigod.zero.bpmn.module.account.bo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: OpenInfoBo
 * @Description:
 * @Author chenyu
 * @Date 2024/1/31
 * @Version 1.0
 */
@Data
public class OpenInfoBo {
    private Integer openType;
	private Integer acceptRisk;
    private Integer accessWay;
    private Integer fundAccountType;
    private Integer inviteId;
    private String channelId;
    private Integer activeId;
    private Integer language;
    private String phoneNum;
	private String phoneArea;
    private String bankCardNo;
    private ArrayList<Integer> accountMarkets;
    private Map formData;
	private String clientId;
	private List<Long> fileIds;
	private List<String> stockTypes;
}
