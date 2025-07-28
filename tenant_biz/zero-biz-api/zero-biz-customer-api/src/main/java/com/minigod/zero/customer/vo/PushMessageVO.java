package com.minigod.zero.customer.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/31 16:52
 * @description：
 */
@Data
public class PushMessageVO {
	private String accountId;
	private Integer templateCode;
	private List<String> params;
}
