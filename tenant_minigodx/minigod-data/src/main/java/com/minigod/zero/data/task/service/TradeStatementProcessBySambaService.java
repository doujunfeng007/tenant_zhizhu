package com.minigod.zero.data.task.service;


import java.io.IOException;

/**
 * 客户结单预处理，扫描客户结单文件，将文件送到下载目录，同时将结单信息写到数据库
 * @author wengzejie
 */
public interface TradeStatementProcessBySambaService {

	/**
	 * 连接smb服务器，扫描约定目录下的结单文件，将文件送到下载目录，同时将结单信息写到数据库
	 */
	void processStatementFiles();
}
