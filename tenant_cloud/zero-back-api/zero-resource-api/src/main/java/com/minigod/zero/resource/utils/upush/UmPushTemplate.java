package com.minigod.zero.resource.utils.upush;

import com.minigod.zero.resource.utils.upush.android.*;
import com.minigod.zero.resource.utils.upush.ios.*;
import com.minigod.zero.resource.utils.upush.android.*;
import com.minigod.zero.resource.utils.upush.ios.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Zhe.Xiao
 */
@Slf4j
@Component
public class UmPushTemplate {

	private PushClient client = new PushClient();

	/**
	 * 安卓广播
	 *
	 * @param title
	 * @param text
	 * @param extra
	 * @param appKey
	 * @param masterSecret
	 * @param prodMode
	 * @throws Exception
	 */
	@SneakyThrows
	public boolean sendAndroidBroadcast(String title, String text, Map<String, String> extra,
										String appKey, String masterSecret, Boolean prodMode) {
		AndroidBroadcast broadcast = new AndroidBroadcast(appKey, masterSecret);
		broadcast.setTitle(title);
		broadcast.setText(text);
		broadcast.goAppAfterOpen();
		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		broadcast.setProductionMode(prodMode != null ? prodMode : true);
		for (Map.Entry<String, String> entry : extra.entrySet()) {
			broadcast.setExtraField(entry.getKey(), entry.getValue());
		}
		return client.send(broadcast);
	}

	/**
	 * 安卓单播
	 *
	 * @param deviceToken
	 * @param title
	 * @param text
	 * @param extra
	 * @param appKey
	 * @param masterSecret
	 * @param prodMode
	 * @throws Exception
	 */
	@SneakyThrows
	public boolean sendAndroidUnicast(String deviceToken, String title, String text, Map<String, String> extra,
									  String appKey, String masterSecret, Boolean prodMode) {
		AndroidUnicast unicast = new AndroidUnicast(appKey, masterSecret);
		unicast.setDeviceToken(deviceToken);
		unicast.setTitle(title);
		unicast.setText(text);
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		unicast.setProductionMode(prodMode != null ? prodMode : true);
		for (Map.Entry<String, String> entry : extra.entrySet()) {
			unicast.setExtraField(entry.getKey(), entry.getValue());
		}
		return client.send(unicast);
	}

	/**
	 * 安卓列播
	 *
	 * @param deviceToken
	 * @param title
	 * @param text
	 * @param extra
	 * @param appKey
	 * @param masterSecret
	 * @param prodMode
	 * @throws Exception
	 */
	@SneakyThrows
	public boolean sendAndroidListcast(String deviceToken, String title, String text, Map<String, String> extra,
									   String appKey, String masterSecret, Boolean prodMode) {
		AndroidListcast listcast = new AndroidListcast(appKey, masterSecret);
		listcast.setDeviceToken(deviceToken);
		listcast.setTitle(title);
		listcast.setText(text);
		listcast.goAppAfterOpen();
		listcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		listcast.setProductionMode(prodMode != null ? prodMode : true);
		for (Map.Entry<String, String> entry : extra.entrySet()) {
			listcast.setExtraField(entry.getKey(), entry.getValue());
		}
		return client.send(listcast);
	}

	/**
	 * 安卓组播
	 *
	 * @param tags
	 * @param title
	 * @param text
	 * @param extra
	 * @param appKey
	 * @param masterSecret
	 * @param prodMode
	 * @throws Exception
	 */
	@SneakyThrows
	public boolean sendAndroidGroupcast(List<String> tags, String title, String text, Map<String, String> extra,
										String appKey, String masterSecret, Boolean prodMode) {
		AndroidGroupcast groupcast = new AndroidGroupcast(appKey, masterSecret);
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		for (String tag : tags) {
			JSONObject tagObj = new JSONObject();
			tagObj.put("tag", tag);
			tagArray.put(tagObj);
		}
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);

		groupcast.setFilter(filterJson);
		groupcast.setTitle(title);
		groupcast.setText(text);
		groupcast.goAppAfterOpen();
		groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		groupcast.setProductionMode(prodMode != null ? prodMode : true);
		for (Map.Entry<String, String> entry : extra.entrySet()) {
			groupcast.setExtraField(entry.getKey(), entry.getValue());
		}
		return client.send(groupcast);
	}

	/**
	 * 安卓对单个或者多个alias进行推送
	 *
	 * @param alias
	 * @param aliasType
	 * @param title
	 * @param text
	 * @param appKey
	 * @param masterSecret
	 * @param prodMode
	 * @throws Exception
	 */
	@SneakyThrows
	public boolean sendAndroidCustomizedcast(String alias, String aliasType, String title, String text,
											 String appKey, String masterSecret, Boolean prodMode) {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appKey, masterSecret);
		customizedcast.setAlias(alias, aliasType);
		customizedcast.setTitle(title);
		customizedcast.setText(text);
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		customizedcast.setProductionMode(prodMode != null ? prodMode : true);
		return client.send(customizedcast);
	}

	/**
	 * 安卓将alias存放到文件后，根据file_id来推送
	 *
	 * @param contents
	 * @param aliasType
	 * @param title
	 * @param text
	 * @param appKey
	 * @param masterSecret
	 * @param prodMode
	 * @throws Exception
	 */
	@SneakyThrows
	public boolean sendAndroidCustomizedcastFile(String contents, String aliasType, String title, String text,
												 String appKey, String masterSecret, Boolean prodMode) {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appKey, masterSecret);
		String fileId = client.uploadContents(appKey, masterSecret, contents);
		customizedcast.setFileId(fileId, aliasType);
		customizedcast.setTitle(title);
		customizedcast.setText(text);
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		customizedcast.setProductionMode(prodMode != null ? prodMode : true);
		return client.send(customizedcast);
	}

	/**
	 * 安卓文件播，多个device_token可通过文件形式批量发送
	 *
	 * @param contents
	 * @param title
	 * @param text
	 * @param appKey
	 * @param masterSecret
	 * @param prodMode
	 * @throws Exception
	 */
	@SneakyThrows
	public boolean sendAndroidFilecast(String contents, String title, String text,
									   String appKey, String masterSecret, Boolean prodMode) {
		AndroidFilecast filecast = new AndroidFilecast(appKey, masterSecret);
		String fileId = client.uploadContents(appKey, masterSecret, contents);
		filecast.setFileId(fileId);
		filecast.setTitle(title);
		filecast.setText(text);
		filecast.goAppAfterOpen();
		filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		filecast.setProductionMode(prodMode != null ? prodMode : true);
		return client.send(filecast);
	}

	/**
	 * 苹果广播
	 *
	 * @param title
	 * @param subtitle
	 * @param body
	 * @param extra
	 * @param appKey
	 * @param masterSecret
	 * @param prodMode
	 * @throws Exception
	 */
	@SneakyThrows
	public boolean sendIOSBroadcast(String title, String subtitle, String body, Map<String, String> extra,
									String appKey, String masterSecret, Boolean prodMode) {
		IOSBroadcast broadcast = new IOSBroadcast(appKey, masterSecret);
		broadcast.setAlert(title, subtitle, body);
		broadcast.setBadge(0);
		broadcast.setSound("default");
		broadcast.setProductionMode(prodMode != null ? prodMode : true);
		for (Map.Entry<String, String> entry : extra.entrySet()) {
			broadcast.setCustomizedField(entry.getKey(), entry.getValue());
		}
		return client.send(broadcast);
	}

	/**
	 * 苹果单播
	 *
	 * @param deviceToken
	 * @param title
	 * @param subtitle
	 * @param body
	 * @param extra
	 * @param appKey
	 * @param masterSecret
	 * @param prodMode
	 * @throws Exception
	 */
	@SneakyThrows
	public boolean sendIOSUnicast(String deviceToken, String title, String subtitle, String body, Map<String, String> extra,
								  String appKey, String masterSecret, Boolean prodMode) {
		IOSUnicast unicast = new IOSUnicast(appKey, masterSecret);
		unicast.setDeviceToken(deviceToken);
		unicast.setAlert(title, subtitle, body);
		unicast.setBadge(0);
		unicast.setSound("default");
		unicast.setProductionMode(prodMode != null ? prodMode : true);
		for (Map.Entry<String, String> entry : extra.entrySet()) {
			unicast.setCustomizedField(entry.getKey(), entry.getValue());
		}
		return client.send(unicast);
	}

	/**
	 * 苹果列播
	 *
	 * @param deviceToken
	 * @param title
	 * @param subtitle
	 * @param body
	 * @param extra
	 * @param appKey
	 * @param masterSecret
	 * @param prodMode
	 * @throws Exception
	 */
	@SneakyThrows
	public boolean sendIOSListcast(String deviceToken, String title, String subtitle, String body, Map<String, String> extra,
								   String appKey, String masterSecret, Boolean prodMode) {
		IOSListcast unicast = new IOSListcast(appKey, masterSecret);
		unicast.setDeviceToken(deviceToken);
		unicast.setAlert(title, subtitle, body);
		unicast.setBadge(0);
		unicast.setSound("default");
		unicast.setProductionMode(prodMode != null ? prodMode : true);
		for (Map.Entry<String, String> entry : extra.entrySet()) {
			unicast.setCustomizedField(entry.getKey(), entry.getValue());
		}
		return client.send(unicast);
	}

	/**
	 * 苹果组播
	 *
	 * @param tags
	 * @param title
	 * @param subtitle
	 * @param body
	 * @param extra
	 * @param appKey
	 * @param masterSecret
	 * @param prodMode
	 * @throws Exception
	 */
	@SneakyThrows
	public boolean sendIOSGroupcast(List<String> tags, String title, String subtitle, String body, Map<String, String> extra,
									String appKey, String masterSecret, Boolean prodMode) {
		IOSGroupcast groupcast = new IOSGroupcast(appKey, masterSecret);
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		for (String tag : tags) {
			JSONObject tagObj = new JSONObject();
			tagObj.put("tag", tag);
			tagArray.put(tagObj);
		}
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		log.info("filterJson:{}", filterJson.toString());

		groupcast.setFilter(filterJson);
		groupcast.setAlert(title, subtitle, body);
		groupcast.setBadge(0);
		groupcast.setSound("default");
		groupcast.setProductionMode(prodMode != null ? prodMode : true);
		for (Map.Entry<String, String> entry : extra.entrySet()) {
			groupcast.setCustomizedField(entry.getKey(), entry.getValue());
		}
		return client.send(groupcast);
	}

	/**
	 * 苹果对单个或者多个alias进行推送
	 *
	 * @param alias
	 * @param aliasType
	 * @param title
	 * @param subtitle
	 * @param body
	 * @param appKey
	 * @param masterSecret
	 * @param prodMode
	 * @throws Exception
	 */
	@SneakyThrows
	public boolean sendIOSCustomizedcast(String alias, String aliasType, String title, String subtitle, String body,
										 String appKey, String masterSecret, Boolean prodMode) {
		IOSCustomizedcast customizedcast = new IOSCustomizedcast(appKey, masterSecret);
		customizedcast.setAlias(alias, aliasType);
		customizedcast.setAlert(title, subtitle, body);
		customizedcast.setBadge(0);
		customizedcast.setSound("default");
		customizedcast.setProductionMode(prodMode != null ? prodMode : true);
		return client.send(customizedcast);
	}

	/**
	 * 苹果文件播，多个device_token可通过文件形式批量发送
	 *
	 * @param contents
	 * @param title
	 * @param subtitle
	 * @param body
	 * @param appKey
	 * @param masterSecret
	 * @param prodMode
	 * @throws Exception
	 */
	@SneakyThrows
	public boolean sendIOSFilecast(String contents, String title, String subtitle, String body,
								   String appKey, String masterSecret, Boolean prodMode) {
		IOSFilecast filecast = new IOSFilecast(appKey, masterSecret);
		String fileId = client.uploadContents(appKey, masterSecret, contents);
		filecast.setFileId(fileId);
		filecast.setAlert(title, subtitle, body);
		filecast.setBadge(0);
		filecast.setSound("default");
		filecast.setProductionMode(prodMode != null ? prodMode : true);
		return client.send(filecast);
	}

}
