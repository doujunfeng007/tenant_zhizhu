package com.minigod.zero.trade;

import com.minigod.zero.biz.common.constant.LauncherConstant;
import com.minigod.zero.core.cloud.client.ZeroCloudApplication;
import com.minigod.zero.core.launch.ZeroApplication;
import com.minigod.zero.core.launch.constant.AppConstant;

/**
 * @author:yanghu.luo
 * @create: 2023-04-17 15:32
 * @Description: 交易服务启动类
 */
@ZeroCloudApplication
public class ZeroTradeApplication {

	public static void main(String[] args) {
		ZeroApplication.run(AppConstant.SERVICE_BIZ_TRADE_NAME, ZeroTradeApplication.class, args);

		System.out.println("spring.cloud.nacos.server-addr---------------:"+ LauncherConstant.nacosAddr("sit"));
		System.out.println("spring.cloud.nacos.discovery.namespace---------:"+LauncherConstant.nacosNamespace("sit"));
	}
}
