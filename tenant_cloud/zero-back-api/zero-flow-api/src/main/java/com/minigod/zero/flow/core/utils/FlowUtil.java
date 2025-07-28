
package com.minigod.zero.flow.core.utils;

import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.flow.core.enums.FLowBusinessEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 工作流工具类
 *
 * @author Chill
 */
public class FlowUtil {

    /**
     * 定义流程key对应的表名
     */
    private final static Map<String, String> BUSINESS_TABLE = new HashMap<>();
    private final static Map<String, String> BUSINESS_KEY = new HashMap<>();

    static {
        for (FLowBusinessEnum enums : FLowBusinessEnum.values()) {
            BUSINESS_TABLE.put(enums.getBusinessKey(), enums.getTable());
            BUSINESS_KEY.put(enums.getBusinessKey(), enums.getDictKey());
        }
    }

    /**
     * 通过流程key获取业务表名
     *
     * @param key 流程key
     */
    public static String getBusinessTable(String key) {
        String businessTable = BUSINESS_TABLE.get(key);
        if (Func.isEmpty(businessTable)) {
            throw new RuntimeException("流程启动失败,未找到相关业务表");
        }
        return businessTable;
    }

    /**
     * 通过流程key获取业务字典
     *
     * @param key
     * @return
     */
    public static String getBusinessDictKey(String key) {
        String businessKey = BUSINESS_KEY.get(key);
        if (Func.isEmpty(businessKey)) {
            throw new RuntimeException("流程启动失败,未找到相关业务字典");
        }
        return businessKey;
    }

    /**
     * 获取业务标识
     *
     * @param businessTable 业务表
     * @param businessId    业务表主键
     * @return businessKey
     */
    @Deprecated
    public static String getBusinessKey(String businessTable, String businessId) {
        return StringUtil.format("{}:{}", businessTable, businessId);
    }

}
