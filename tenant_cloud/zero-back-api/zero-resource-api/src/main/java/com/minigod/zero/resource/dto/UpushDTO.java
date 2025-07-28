package com.minigod.zero.resource.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 友盟推送参数
 *
 * @author Zhe.Xiao
 */
@Data
public class UpushDTO {
	/**
	 * 应用鉴权
	 */
	private String authorization;
	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 消息内容
	 */
	private String text;
	/**
	 * 系统类型 1-安卓 2-IOS
	 */
	private Integer osType;
	/**
	 * 友盟设备注册号
	 */
	private List<String> deviceTokenList;
	/**
	 * 自定义额外信息
	 */
	private Map<String, String> extra;
	/**
	 * 标签
	 */
	private List<String> tagList;
	/**
	 * 别名
	 */
	private List<String> aliasList;
	/**
	 * 别名自定义类型
	 */
	private String aliasType;
	/**
	 * 上传内容
	 */
	private String contents;
	/**
	 * 可选，正式/测试模式。默认为true
	 * 广播、组播下的测试模式只会将消息发给测试设备。测试设备需要到web上添加
	 * 单播、文件播不受测试设备限制
	 */
	private Boolean prodMode;
}
