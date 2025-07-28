package com.minigod.zero.platform.enums;

import com.minigod.zero.core.tool.utils.CollectionUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 个人消息通知分组枚举类
 * @author wc
 */
public enum EUserMsgGroupEnum {
    /**
     * 消息分组
     */
    A(MsgStaticType.DisplayGroup.TRADE_PUSH, "交易提醒"),
	B(MsgStaticType.DisplayGroup.MTK_NOTIFY, "行情提醒"),
	C(MsgStaticType.DisplayGroup.STK_NEW_REMIND, "新股提醒"),
	D(MsgStaticType.DisplayGroup.SERVICE_MSG, "服务通知"),
	E(MsgStaticType.DisplayGroup.SYSTEM_MSG, "系统通知"),
	F(MsgStaticType.DisplayGroup.ACTIVITY_PUSH, "活动推送"),
	G(MsgStaticType.DisplayGroup.HOT_NEWS, "热点资讯");

	private int code;
	private String desc;

    EUserMsgGroupEnum(int code, String desc) {
    	this.code = code;
        this.desc = desc;
    }

    public int getCode() {
    	return code;
	}

    public String getDesc() {
        return desc;
    }

	public static EUserMsgGroupEnum getByCode(int code) {
		List<EUserMsgGroupEnum> collect = Arrays.stream(values()).filter(o -> o.getCode() == code).collect(Collectors.toList());
		if (CollectionUtil.isNotEmpty(collect)) {
			return collect.get(0);
		}
		return null;
	}

	public static String getDescByCode(int code) {
		EUserMsgGroupEnum eUserMsgGroupEnum = getByCode(code);
		if (eUserMsgGroupEnum != null) {
			return eUserMsgGroupEnum.getDesc();
		} else {
			return null;
		}
	}
}
