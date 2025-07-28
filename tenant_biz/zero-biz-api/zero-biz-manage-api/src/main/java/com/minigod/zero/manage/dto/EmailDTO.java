package com.minigod.zero.manage.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/16 15:44
 * @description：
 */
@Data
public class EmailDTO {
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 正文
	 */
	private String content;
	/**
	 * 发送者
	 */
	private String attachmentUrls;
	/**
	 * 抄送人
	 */
	private String carbonCopy;
	/**
	 * 接收邮箱
	 */
	private String accepts;
	/**
	 * 接收用户id
	 */
	private String custId;
}
