package com.minigod.zero.trade.hs.vo;

/**
 * Created by sunline on 2016/4/26 13:51.
 * sunline
 */
public interface GrmBiz {

	/**
	 * 参数处理并调用恒生接口
	 */
	<R extends GrmRequestVO> GrmResponseVO requestData(R requestVo);
}
