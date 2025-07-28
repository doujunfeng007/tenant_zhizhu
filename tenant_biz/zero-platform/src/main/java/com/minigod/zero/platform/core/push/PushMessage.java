package com.minigod.zero.platform.core.push;

import com.minigod.zero.platform.core.Message;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.PlatformOsTypeEnum;
import com.minigod.zero.platform.enums.PushTypeEnum;
import com.minigod.zero.platform.vo.CustomerDeviceInfoVO;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/12 16:19
 * @description：
 */
@Data
public class PushMessage extends Message {
	/**
	 * 唯一标识
	 */
	private String msgId;
	/**
	 * 默认非离线推送
	 */
	private boolean isOfflinePush = true;
	/**
	 * 0=全部(默认) 1=安卓 2=IOS
	 */
	private int devType = PlatformOsTypeEnum.OS_ALL.getTypeValue();
	/**
	 * A=全站  P=个人 (默认）
	 */
	private String msgGroup = InformEnum.MsgGroupEnum.PERSON.getTypeCode();
	/**
	 * 消息分组
	 */
	private Integer displayGroup;
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
	private List<String> registrationIds;
	/**
	 * true：表示推送生产环境。
	 * false：表示推送开发环境。
	 */
	private boolean apnsProduction;
	/**
	 * 附加参数，客户端可通过附加参数做其他业务
	 */
	private Map<String, String> extras;

	/**
	 * 定时推送任务名称
	 */
	private String scheduleName;
	/**
	 * 定时任务推送时间
	 */
	private String sendTime;

	//---------------------------------------兼容现有逻辑字段-----------------------------------
	/**
	 * 推送设备信息
	 */
	private List<CustomerDeviceInfoVO>  deviceList;
	/**
	 * 推送用户id
	 */
	private List<Long> userIds;
}
