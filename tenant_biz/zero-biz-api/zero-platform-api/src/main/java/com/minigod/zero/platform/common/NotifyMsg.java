package com.minigod.zero.platform.common;

import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.PlatformOsTypeEnum;
import com.minigod.zero.platform.enums.PushTypeEnum;
import com.minigod.zero.platform.vo.CustomerDeviceInfoVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NotifyMsg implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 唯一标识
	 */
	private String msgId;
	/**
	 * 默认非离线推送
	 */
	private boolean isOfflinePush = false;
	/**
	 * 用户ID
	 */
	private List<Long> userIds;
	/**
	 * 0=全部(默认) 1=安卓 2=IOS
	 */
	private int devType = PlatformOsTypeEnum.OS_ALL.getTypeValue();
	/**
	 * A=全站  P=个人 (默认）
	 */
	private String msgGroup = InformEnum.MsgGroupEnum.PERSON.getTypeCode();
	/**
	 * 消息内容
	 */
	private String msg;
	/**
	 * 消息分组
	 */
	private Integer displayGroup;
	/**
	 * 标题
	 */
    private String title;
	/**
	 * 跳转链接
	 */
	private String url;
	/**
	 * 推送方式(0-即时 1-定时)
	 */
	private int sendWay;
	/**
	 * 推送类型 {@link PushTypeEnum}
	 */
	private Integer pushType;
	/**
	 * 推送设备信息
	 */
	private List<CustomerDeviceInfoVO> deviceList;

}
