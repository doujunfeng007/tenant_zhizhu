package com.minigod.zero.biz.common.enums;


/**
 * 消费行情详细数据时的数据字段映射
 * @author bpmx
 */
public enum QuotesInfoMqInfoMapping {
    MKT_USER_ID("mktUserId", "行情id"),
    CUST_ID("userId","用户Id"),
	TENANT_ID("tenantId","所属机构"),
    LOG_TIME("logTime", "上报时间"),
    IS_PROFESSIONAL_USER("isProfessionalUser", "是否是专业投资者"),
    SOURCE_CODE("sourceCode", "终端/产品"),
    SERVICE_TYPE("serviceType", "服务类型"),
    IS_LOGIN("isLogin", "是否登录"),
    SERVICE_PAGE("servicePage", "服务页面/功能"),
    SERVICE_URL("serviceUrl", "服务URL"),
    QUOTES_TYPE("quotesType", "行情品种&收费模式"),
    DEVICE_INFO("deviceInfo", "设备信息"),
    IP("ip", "请求IP"),
    IP_ADDRESS("ipAddress", "IP地址"),
    STOCK_CODE("stockCode", "证券代码"),
    STOCK_MARKET("stockMarket", "证券类型/市场"),
    FEE_TYPE("feeType", "收费模式"),
    START_TIME("startTime", "使用开始时间"),
    END_TIME("endTime", "使用结束时间"),
    MARKET_CODE("marketCode", "市场code"),
    IS_DEDUCT("isDeduct", "是否扣减次数"),
    MARKET_STATUS("marketStatus", "市场状态");

    private final String code;
    private final String info;

    QuotesInfoMqInfoMapping(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
