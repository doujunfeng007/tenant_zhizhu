package com.minigod.zero.platform.core;

import lombok.Data;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/11 9:55
 * @description：
 */
@Data
public class LanguageTemplate {
	private String title;
	private Integer templateCode;
	private String content;
	private String url;

	public LanguageTemplate(){

	}

	public LanguageTemplate(String title,Integer templateCode,String content,String url){
		this.content = content;
		this.templateCode = templateCode;
		this.title = title;
		this.url = url;
	}
}
