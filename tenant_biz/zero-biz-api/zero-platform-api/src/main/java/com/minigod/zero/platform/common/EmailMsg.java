package com.minigod.zero.platform.common;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
@Data
public class EmailMsg implements Serializable{
	private static final long serialVersionUID = 1L;

	private String msgId;
    private String sender;
    private List<String> carbonCopy; //抄送人
    private List<String> blindCarbonCopy; //密抄送人
    private String sendId; // 唯一ID，外部系统调用发送消息传入
    private String fromName;
    private String accept;
	private List<String> accepts;
    private String title;
    private String content;
    private Integer userId;
    private List<String> attachmentUrls;
	private String tenantId;
    private Integer code; // 模板编码
	private List<String> list = new ArrayList<>();
	private String lang;
	/**
	 * 动态标题参数
	 */
	private List<String> titleParam;
}
