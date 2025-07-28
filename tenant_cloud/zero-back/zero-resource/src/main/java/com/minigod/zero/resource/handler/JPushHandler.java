package com.minigod.zero.resource.handler;

import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONUtil;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.minigod.zero.resource.enums.CodeType;
import com.minigod.zero.resource.enums.OsTypeEnum;
import com.minigod.zero.resource.enums.PushTypeEnum;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.resource.service.impl.JpushServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 极光推送
 *
 * @author sunline
 */
@Slf4j
public class JPushHandler {

    public static R syncPushMsgToListSend(String masterSecret, String appKey, Boolean istest, JpushServiceImpl.PushToList pushToList) {
        R respVO = R.success();
        PushPayload payload;
        try {
            PushPayload.Builder builder = PushPayload.newBuilder();
            List<String> lstTokenId = pushToList.getLstTokenId();
            int pushType = pushToList.getPushType();
            int osType = pushToList.getOsType();
            String title = pushToList.getTitle();
            String transMessage = pushToList.getTransMessage();
            boolean isOfflinePush = pushToList.isOfflinePush();

            //通知对象
            builder.setAudience(Audience.registrationId(lstTokenId));
            ClientConfig clientConfig = ClientConfig.getInstance();
            JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
            convert(builder, pushType, osType, title, transMessage, isOfflinePush, istest);
            payload = builder.build();
            long lBegin = System.currentTimeMillis();
            log.info("极光推送参数：{}", JSONObject.toJSONString(payload));
            PushResult result = jpushClient.sendPush(payload);
            int responseCode = result.getResponseCode();
            long lEnd = System.currentTimeMillis();
            log.info("极光推送花费时间(ms):{},result:{}", (lEnd - lBegin), result);
            if (responseCode == 200) {
                log.info("极光推送成功：{}", JSONUtil.toJsonStr(payload));
            } else {
                log.warn("极光推送失败，返回结果：{}, 设备的clientId: {}", result, JSONUtil.toJsonStr(payload));
                respVO.setCode(CodeType.PUSH_ERROR.getErrorCode());
                respVO.setMsg("极光推送失败");
            }
            return respVO;
        } catch (APIConnectionException e) {
            log.error("极光推送(批量推送)异常(APIConnectionException)", e);
            respVO.setCode(CodeType.UNKNOWN_ERROR.getErrorCode());
            respVO.setMsg("极光推送(批量推送)异常");
            return respVO;
        } catch (APIRequestException e) {
            log.error("极光推送(批量推送)异常(APIRequestException)", e);
            respVO.setCode(CodeType.UNKNOWN_ERROR.getErrorCode());
            respVO.setMsg("极光推送(批量推送)异常");
            return respVO;
        } catch (Exception e) {
            log.error("极光推送(批量推送)异常", e);
            respVO.setCode(CodeType.UNKNOWN_ERROR.getErrorCode());
            respVO.setMsg("极光推送(批量推送)异常");
            return respVO;
        }
    }

    public static R syncPushMsgToAppSend(String masterSecret, String appKey, Boolean istest, JpushServiceImpl.PushToApp pushToApp) {
        R respVO = R.success();
        PushPayload payload;
        try {
            PushPayload.Builder builder = PushPayload.newBuilder();
            int pushType = pushToApp.getPushType();
            int osType = pushToApp.getOsType();
            String title = pushToApp.getTitle();
            String transMessage = pushToApp.getTransMessage();
            boolean isOfflinePush = pushToApp.isOfflinePush();

            //通知对象
            builder.setAudience(Audience.all());
            ClientConfig clientConfig = ClientConfig.getInstance();
            JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
            convert(builder, pushType, osType, title, transMessage, isOfflinePush, istest);
            payload = builder.build();
            long lBegin = System.currentTimeMillis();
            PushResult result = jpushClient.sendPush(payload);
            int responseCode = result.getResponseCode();
            long lEnd = System.currentTimeMillis();
            log.info("极光推送花费时间(ms):{},result:{}", (lEnd - lBegin), result);
            if (responseCode == 200) {
                log.info("极光推送成功：{}", JSONUtil.toJsonStr(payload));
            } else {
                log.warn("极光推送失败，返回结果：{}, 设备的clientId: {}", result, JSONUtil.toJsonStr(payload));
                respVO.setCode(CodeType.PUSH_ERROR.getErrorCode());
                respVO.setMsg("极光推送失败");
            }
            return respVO;
        } catch (APIConnectionException e) {
            log.error("极光推送(按平台推送)异常(APIConnectionException)", e);
            respVO.setCode(CodeType.UNKNOWN_ERROR.getErrorCode());
            respVO.setMsg("极光推送(按平台推送)异常");
            return respVO;
        } catch (APIRequestException e) {
            log.error("极光推送(按平台推送)异常(APIRequestException)", e);
            respVO.setCode(CodeType.UNKNOWN_ERROR.getErrorCode());
            respVO.setMsg("极光推送(按平台推送)异常");
            return respVO;
        } catch (Exception e) {
            log.error("极光推送(按平台推送)异常", e);
            respVO.setCode(CodeType.UNKNOWN_ERROR.getErrorCode());
            respVO.setMsg("极光推送(按平台推送)异常");
            return respVO;
        }
    }

    private static void convert(PushPayload.Builder builder, int pushType, int osType, String title, String transMessage, boolean isOfflinePush, Boolean istest) {
        istest = !istest;
        Options build;
        if (PushTypeEnum.STRONG_MSG.getTypeValue().equals(pushType)) {
            HashMap<String, JsonObject> map = new HashMap<>(5, 1);
            JsonObject xiaomiObj = new JsonObject();
            xiaomiObj.addProperty("distribution", "secondary_push");
            map.put("xiaomi", xiaomiObj);
            JsonObject huaweiObj = new JsonObject();
            huaweiObj.addProperty("distribution", "secondary_push");
            map.put("huawei", huaweiObj);
            JsonObject oppoObj = new JsonObject();
            oppoObj.addProperty("distribution", "secondary_push");
            map.put("oppo", oppoObj);
            JsonObject vivoObj = new JsonObject();
            vivoObj.addProperty("distribution", "secondary_push");
            map.put("vivo", vivoObj);
            JsonObject meizuObj = new JsonObject();
            meizuObj.addProperty("distribution", "secondary_push");
            map.put("meizu", meizuObj);
            JsonObject fcmObj = new JsonObject();
            fcmObj.addProperty("distribution", "secondary_push");
            map.put("fcm", fcmObj);
            build = Options.newBuilder().setApnsProduction(istest).setThirdPartyChannelV2(map).build();
        } else {
            build = Options.newBuilder().setApnsProduction(istest).build();
        }

        // 判断离线是否推送
        if (isOfflinePush) {
            build.setTimeToLive(86000);
        }

        builder.setOptions(build);

        // 如果是强提醒消息：0
        if (PushTypeEnum.STRONG_MSG.getTypeValue().equals(pushType)) {
            Notification notification = null;
            if (OsTypeEnum.OS_ANDROID.getTypeValue().equals(osType)) {
                builder.setPlatform(Platform.android());
                if (StringUtils.isNotBlank(transMessage)) {
                    JSONObject jsonObject = JSONObject.parseObject(transMessage);
                    String alert = jsonObject.getString("msg");
					Integer group = jsonObject.getInteger("group");
					String url = jsonObject.getString("url");
					notification = Notification.newBuilder().addPlatformNotification(
                            AndroidNotification.newBuilder().setTitle(title).setAlert(alert)
                                    .setUriActivity("cn.zhizhu.supery.activity.OpenClickActivity")
                                    .setUriAction("cn.zhizhu.supery.activity.OpenClickActivity")
                                    .addExtra("group", group)
                                    .addExtra("url", HtmlUtil.unescape(url))
                                    .build()
                    ).build();
                } else {
                    notification = Notification.newBuilder().addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).setAlert(transMessage).build()).build();
                }
            }

            if (OsTypeEnum.OS_IOS.getTypeValue().equals(osType)) {
				JSONObject transMessageJsonObject = JSONObject.parseObject(transMessage);
				String alert = transMessageJsonObject.getString("msg");
				String group = transMessageJsonObject.getString("group");
				String url = transMessageJsonObject.getString("url");
                builder.setPlatform(Platform.ios());
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("title",title);
                jsonObject.addProperty("body",alert);
                notification = Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder().setAlert(jsonObject).addExtra("group", group).addExtra("url", HtmlUtil.unescape(url)).build())
                        .build();
            }
            builder.setNotification(notification);
        }

        // 如果是弱提醒信息:1
        if (PushTypeEnum.WEAK_MSG.getTypeValue().equals(pushType)) {
            Message message = null;
            if (OsTypeEnum.OS_ANDROID.getTypeValue().equals(osType)) {
                builder.setPlatform(Platform.android());
                message = Message.newBuilder().setTitle(title).setMsgContent(transMessage).build();
            }
            if (OsTypeEnum.OS_IOS.getTypeValue().equals(osType)) {
                builder.setPlatform(Platform.ios());
                message = Message.newBuilder().setTitle(title).setMsgContent(transMessage).build();
            }
            builder.setMessage(message);
        }
    }
}
