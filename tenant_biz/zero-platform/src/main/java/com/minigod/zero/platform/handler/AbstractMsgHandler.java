package com.minigod.zero.platform.handler;

import com.minigod.zero.core.tool.api.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author sunline
 */
@Slf4j
public abstract class AbstractMsgHandler {

    /**
     * 安卓强消息校验与转换参数
     *
     * @param lstAndroidTemplate
     * @param mapSmsTempExt
     * @return
     */
    R checkAndConvertAnStrongMsg(List<String> lstAndroidTemplate, Map<Object, Object> mapSmsTempExt) {
        R respVO = R.success();
        String strTransTemplateAndroid;
        // 检验参数是否配置
        if (StringUtils.isBlank((String) mapSmsTempExt.get("trans_template_android"))) {
            log.warn("没有取消息模板扩展表信息或者取到的信息不完整");
            return R.fail("没有取消息模板扩展表信息或者取到的信息不完整");
        }

        // 取安卓模板
        strTransTemplateAndroid = (String) mapSmsTempExt.get("trans_template_android");
        // 模板中的替代参数
        String strVar = "%s";
        // 判断模板中s%替代参数的个数
        int iTotal = 0;
        for (String tmp = strTransTemplateAndroid; tmp != null
                && tmp.length() >= strVar.length(); ) {
            if (tmp.indexOf(strVar) == 0) {
                iTotal++;
                tmp = tmp.substring(strVar.length());
            } else {
                tmp = tmp.substring(1);
            }
        }

        // 判断安卓设备请求参数的个数与消息配置表里的个数是否一致
        if (lstAndroidTemplate != null && iTotal != lstAndroidTemplate.size()) {
            log.warn("请求的参数与安卓设备消息代码配置表未正确配置");
            return R.fail("请求的参数与安卓设备消息代码配置表未正确配置");
        }
        String msg = strTransTemplateAndroid;
        // 组装消息模板，将可变参数代入通知模板中
        if (lstAndroidTemplate != null && lstAndroidTemplate.size() != 0) {
            Object[] objArg = lstAndroidTemplate.toArray(new String[]{});
            msg = String.format(strTransTemplateAndroid, objArg);
        }
        respVO.setData(msg);
        return respVO;
    }

    /**
     * IOS强消息校验与转换参数
     *
     * @param lstIosContent
     * @param lstIosModule
     * @param lstIosTemplate
     * @param mapSmsTempExt
     * @return
     */
    R checkAndConvertIosStrongMsg(List<String> lstIosContent, List<String> lstIosModule,
                                                   List<String> lstIosTemplate, Map<Object, Object> mapSmsTempExt) {
        R respVO = R.success();
        // 检验参数是否配置
        if (StringUtils.isBlank((String) mapSmsTempExt.get("trans_template_ios"))
                || StringUtils.isBlank((String) mapSmsTempExt.get("content_ios"))
                || StringUtils.isBlank((String) mapSmsTempExt.get("module_ios"))) {
            log.warn("没有取消息模板扩展表信息或者取到的信息不完整");
            return R.fail("没有取消息模板扩展表信息或者取到的信息不完整");
        }
        // 取IOS模板
        String strTransTemplateIos = (String) mapSmsTempExt.get("trans_template_ios");
        String strContentIos = (String) mapSmsTempExt.get("content_ios");
        String strModuleIos = (String) mapSmsTempExt.get("module_ios");
        // 模板中的替代参数
        String strVar = "%s";

        // 判断模板中s%替代参数的个数
        int iTotal1 = 0;
        for (String tmp = strTransTemplateIos; tmp != null && tmp.length() >= strVar.length(); ) {
            if (tmp.indexOf(strVar) == 0) {
                iTotal1++;
                tmp = tmp.substring(strVar.length());
            } else {
                tmp = tmp.substring(1);
            }
        }
        // 判断请求参数的个数与消息配置表里的个数是否一致
        if (lstIosTemplate != null && iTotal1 != lstIosTemplate.size()) {
            log.warn("请求的参数与消息代码配置表未正确配置");
            return R.fail("请求的参数与消息代码配置表未正确配置");
        }
        // 组装消息模板，将可变参数代入通知模板中
        if (lstIosTemplate != null && lstIosTemplate.size() != 0) {
            Object[] objArg = lstIosTemplate.toArray(new String[]{});
            strTransTemplateIos = String.format(strTransTemplateIos, objArg);
        }

        // 判断模板中s%替代参数的个数
        int iTotal2 = 0;
        for (String tmp = strContentIos; tmp != null && tmp.length() >= strVar.length(); ) {
            if (tmp.indexOf(strVar) == 0) {
                iTotal2++;
                tmp = tmp.substring(strVar.length());
            } else {
                tmp = tmp.substring(1);
            }
        }
        // 判断请求参数的个数与消息配置表里的个数是否一致
        if (lstIosContent != null && iTotal2 != lstIosContent.size()) {
            log.warn("请求的参数与消息代码配置表未正确配置");
            return R.fail("请求的参数与消息代码配置表未正确配置");
        }
        // 组装消息模板，将可变参数代入通知模板中
        if (lstIosContent != null && lstIosContent.size() != 0) {
            Object[] objArg = lstIosContent.toArray(new String[]{});
            strContentIos = String.format(strContentIos, objArg);
        }

        // 判断模板中s%替代参数的个数
        int iTotal3 = 0;
        for (String tmp = strModuleIos; tmp != null && tmp.length() >= strVar.length(); ) {
            if (tmp.indexOf(strVar) == 0) {
                iTotal3++;
                tmp = tmp.substring(strVar.length());
            } else {
                tmp = tmp.substring(1);
            }
        }
        // 判断请求参数的个数与消息配置表里的个数是否一致
        if (lstIosModule != null && iTotal3 != lstIosModule.size()) {
            log.warn("请求的参数与消息代码配置表未正确配置");
            return R.fail("请求的参数与消息代码配置表未正确配置");
        }
        // 组装消息模板，将可变参数代入通知模板中
        if (lstIosModule != null && lstIosModule.size() != 0) {
            Object[] objArg = lstIosModule.toArray(new String[]{});
            strModuleIos = String.format(strModuleIos, objArg);
        }

        HashMap<String, String> msgMap = new HashMap<>();
        msgMap.put("strModuleIos", strModuleIos);
        msgMap.put("strContentIos", strContentIos);
        msgMap.put("strTransTemplateIos", strTransTemplateIos);

        respVO.setData(msgMap);
        return respVO;
    }

    /**
     * 判断是否为新版本 2.8.6后使用极光推送，之前为个推
     *
     * @param appVersion
     * @return
     */
    /*boolean isNewVersion(String appVersion) {
        if (StringUtils.isBlank(appVersion)) {
            return false;
        }
        return VersionUtils.compareAppVersion(appVersion, "2.8.6") > 0;
    }*/

    /**
     * 分割List
     *
     * @param list     待分割的list
     * @param pageSize 每段list的大小
     * @return List<< List < T>>
     */
    static <T> List<List<T>> splitList(List<T> list, int pageSize) {
        int listSize = list.size();
        int page = (listSize + (pageSize - 1)) / pageSize;
        return Stream.iterate(
                0, n -> n + 1).limit(page).parallel().map(
                a -> list.stream().skip((long) a * pageSize).limit(pageSize).parallel()
                        .collect(Collectors.toList())).collect(Collectors.toList());
    }
}
