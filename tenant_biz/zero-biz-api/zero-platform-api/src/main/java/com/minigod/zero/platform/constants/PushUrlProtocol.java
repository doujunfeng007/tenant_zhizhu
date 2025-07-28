package com.minigod.zero.platform.constants;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.URLEncoder;

/**
 * 推送跳转链接协议
 *
 * @author Zhe.Xiao
 */
@Slf4j
public class PushUrlProtocol {

	public enum AppUrlEnum {

		//注意stockType表示证券子类型
		STOCK_DEATIL("个股详情页", "yystock://stockDetail?assetId={0}&stockType={1}"),
		//跳转对应消息分组内
		DISPLAY_GROUP("消息分组二级页", "yystock://messages?group={0}"),
		//新股中心-认购中tab
		IPO_OFFER_TAB("新股中心-认购中tab", "yystock://IPOCalendar?tabIndex=0"),
		//新股中心-待上市(暗盘)tab
		IPO_WAIT_LISTED_TAB("新股中心-认购中tab", "yystock://IPOCalendar?tabIndex=1");


		private String urlName;
		private String urlProtocol;

		AppUrlEnum(String urlName, String urlProtocol) {
			this.urlName = urlName;
			this.urlProtocol = urlProtocol;
		}

		public String getUrlProtocol() {
			return this.urlProtocol;
		}
	}

	public static boolean isAppUrl(String url) {
		if (StringUtils.isBlank(url)) {
			return false;
		}
		for (AppUrlEnum appUrlEnum : AppUrlEnum.values()) {
			if (appUrlEnum.getUrlProtocol().equals(url.trim())) {
				return true;
			}
		}
		return false;
	}

	public static final String WEBVIEW_URL_PREFIX = "yystock://webView?url=";
	public static final String H5_URL_PREFIX_HTTP = "http://";
	public static final String H5_URL_PREFIX_HTTPS = "https://";

	public static String convertH5Url(String url) {
		try {
			if (StringUtils.isBlank(url)) {
				return "";
			}
			url = WEBVIEW_URL_PREFIX + URLEncoder.encode(url.trim(), "UTF-8");
		} catch (Exception e) {
			log.error("convertH5Url error:{}", e.getMessage(), e);
		}
		return url;
	}

}
