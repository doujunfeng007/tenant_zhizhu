package com.minigod.zero.manage.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.biz.common.enums.AppCodeEnum;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.biz.common.enums.ErrorCodeEnum;
import com.minigod.zero.manage.entity.AppVersionEntity;
import com.minigod.zero.manage.service.IAppVersionService;
import com.minigod.zero.manage.service.CommonApiService;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.apivo.params.DeviceInfo;
import com.minigod.zero.cust.apivo.resp.UpdateAppInfoVO;
import com.minigod.zero.cust.feign.ICustInfoClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.minigod.zero.core.secure.utils.AuthUtil.getTenantId;

@Slf4j
@Service
public class CommonApiServiceImpl implements CommonApiService {

	@Resource
	private IAppVersionService appVersionService;
	@Resource
	private ICustInfoClient custInfoClient;

	// 是否检测更新
	@Value("${is.force.update:1}")
	private String isForceUpdate;

	// 前APP可查看参数集合
	private final static Set<String> SYS_CONFIG_MODULE = new HashSet<>();
	private final static Map<String, Class<?>> usableConfigMap = new HashMap<>();

	static {
		usableConfigMap.put("maxStks", Integer.class);// 每个组合拥有资产最大的个数
		usableConfigMap.put("initAmount", Double.class);// 模拟账户的初始资金
		usableConfigMap.put("questionUnsatisfyType", String.class); // 问答不满意类型
		usableConfigMap.put("adviserJobHour", String.class); // 投顾认证从业年限
		usableConfigMap.put("fnlHomePageUrl", String.class); // 理财首页URL
		usableConfigMap.put("forMoreCouponsUrl", String.class); // 获取更多的卡券跳转的URL
		usableConfigMap.put("marketQuestionTemplate", String.class); // 大盘类型问题模板
		usableConfigMap.put("noHoldStockQuestionTemplate", String.class); // 未持有股票类型问题模板
		usableConfigMap.put("otherQuestionTemplate", String.class); // 其他类型问题模板
		usableConfigMap.put("questionUnsatisfyType", String.class); // 问答不满意类型
		usableConfigMap.put("reportContentOption", String.class); // 举报无效问题选项
		usableConfigMap.put("stockQuestionTemplate", String.class); // 持有股票类型问题模板
		// 注意： 上面usableConfigMap中的Key所属的module需要在SYS_CONFIG_MODULE中指明
		SYS_CONFIG_MODULE.add("ptf");
		SYS_CONFIG_MODULE.add("cash");
		SYS_CONFIG_MODULE.add("fnlprod");
		SYS_CONFIG_MODULE.add("coupon");
		SYS_CONFIG_MODULE.add("mktinfo");
		SYS_CONFIG_MODULE.add("adviser");
	}

	@Override
	public R<UpdateAppInfoVO> appUpdateCheck() {
		try {
			String deviceType = WebUtil.getHeader(CommonConstant.DEVICE_TYPE);
			String osType = WebUtil.getHeader(CommonConstant.OS_TYPE);
			String deviceCode = WebUtil.getHeader(CommonConstant.DEVICE_CDE);
			String versionNo = WebUtil.getHeader("appVersion");
			if (deviceType == null || osType == null || versionNo == null) {
				return R.fail(ResultCode.PARAM_MISS);
			}
			DeviceInfo deviceInfo = new DeviceInfo();
			if (!StringUtils.isEmpty(deviceType)) {
				deviceInfo.setDeviceType(Integer.parseInt(deviceType));
			}
			if (!StringUtils.isEmpty(osType)) {
				deviceInfo.setOsType(Integer.parseInt(osType));
			}
			deviceInfo.setDeviceCode(deviceCode);
			deviceInfo.setAppVersion(versionNo);
			Long custId = AuthUtil.getCustId();
			UpdateAppInfoVO appInfoVO = findAppVersion(deviceInfo);
			// 更新下载渠道
			if (!StringUtils.isEmpty(deviceInfo.getChannel())) {
				custInfoClient.updateChannel(custId, deviceInfo.getChannel());
			}
			return R.data(appInfoVO);
		} catch (Exception e) {
			log.error("检测APP升级异常", e);
		}
		return R.fail();
	}

	private UpdateAppInfoVO findAppVersion(DeviceInfo deviceInfo) {
		Integer deviceType = deviceInfo.getDeviceType();
		Integer osType = deviceInfo.getOsType();
		String versionNo = deviceInfo.getAppVersion();
		//String channel = deviceInfo.getChannel();
		//String deviceCode = deviceInfo.getDeviceCode();
		UpdateAppInfoVO dto = new UpdateAppInfoVO();
		// 获取客户端当前版本的版本信息
		AppVersionEntity appVersionEntity = appVersionService.findAppVersionInfo(versionNo, deviceType, osType, AppCodeEnum.Sunline_ZSZZ.getTypeValue(), getTenantId());
		if (null == appVersionEntity) {
			log.warn(ErrorCodeEnum.APP_VERSION_ERROR.getMessage());
		}

		// 当前不是最新版本,则一定需要有最新版本
		// 前端传过来错误的版本号
		if (appVersionEntity == null || !appVersionEntity.getIsNew()) {//客户端版本不存在或者不是最新版本的情况
			// 获取服务端最新版本的版本信息
			AppVersionEntity newVersion = appVersionService.findNewVersion(deviceType, osType, AppCodeEnum.Sunline_ZSZZ.getTypeValue(), getTenantId());
			if (newVersion == null) {//服务端最新版本不存在
				if (appVersionEntity != null) {
					log.info("app升级检测,未能找到最新的版本,当前版本信息为:" + JSONObject.toJSONString(appVersionEntity));
					dto.setCheckCode(getCheckCode(appVersionEntity.getCheckCode()));
					dto.setCheckMsg(getMsgByCheckCode(versionNo, appVersionEntity.getCheckCode()));
				}
			} else {//服务端最新版本存在，同时客户端版本也存在
				dto.setUrl(newVersion.getUrl());
				dto.setLastVer(newVersion.getVersionNo());
				dto.setSize(newVersion.getSize());
				dto.setNote(getMessage(WebUtil.getLanguage(), newVersion.getUpdateDeclare(), newVersion.getUpdateDeclareHant(), newVersion.getUpdateDeclareEn()));
				dto.setMd5(newVersion.getMd5());
				//客户端版本号与服务端版本号比较规则需要优化,如客户端段 1.4.9 服务端1.4.12 实际客户端版本号<服务端版本号,比较结果为 客户端版本号(149)>服务端版本号(1412)
				if (StringUtils.isNotEmpty(versionNo)) {
					int flag = compareVersion(versionNo, newVersion.getVersionNo());//比较客户端版本号与服务端版本号
					if (flag == 1) {//客户端版本号>服务端版本号
						dto.setCheckCode(0);
						dto.setCheckMsg(getMsgByCheckCode(newVersion.getVersionNo(), 0));
					} else {//客户端版本号<服务端版本号
						if (appVersionEntity != null) {//客户端版本号在服务端存在且<服务端最新版本号
							// 跨版本是否需要强更
							Integer checkCode = newVersion.getCheckCode();
							if (3 != checkCode) {
								boolean isExist = appVersionService.findCodeByVersion(versionNo, osType, getTenantId());
								if (isExist) {
									checkCode = 3;
								}
							}
							dto.setCheckMsg(getMsgByCheckCode(newVersion.getVersionNo(), checkCode));
							dto.setCheckCode(getCheckCode(checkCode));
						} else {//客户端版本号信息在服务端不存在
							dto.setCheckCode(getCheckCode(3));
							dto.setCheckMsg(getMsgByCheckCode(newVersion.getVersionNo(), 3));
						}
					}

				}

			}
		} else {//客户端版本是最新版本
			dto.setCheckCode(getCheckCode(0));
			dto.setCheckMsg(getMsgByCheckCode(appVersionEntity.getVersionNo(), 0));
		}
		return dto;
	}

	private int getCheckCode(int checkCode) {
		if (checkCode == 3) {
			if ("1".equals(isForceUpdate)) { //表示是否启用该功能
				/*if (!isForceUpdateReminder()) {
					return 2;
				}*/
			}
		}
		return checkCode;
	}

	/**
	 * 比较两个版本号的大小,设定版本号为X.X.X...形式，X不超过4位
	 * 1:version1>version2
	 * 0:version1=version2
	 * -1:version1<version2
	 * 规则:缺省的用0补全 如X.X->X.X.0
	 * 按照.进行切片，从左至右逐位比较
	 *
	 * @param version1
	 * @param version2
	 * @return
	 */
	public static int compareVersion(String version1, String version2) {
		int arr1[] = {0, 0, 0, 0};
		int arr2[] = {0, 0, 0, 0};
		String v1[] = version1.split("\\.");
		String v2[] = version2.split("\\.");
		for (int i = 0; i < v1.length; i++) {
			if (StringUtils.isNotEmpty(v1[i])) {
				arr1[i] = Integer.valueOf(v1[i]);
			}
		}
		for (int i = 0; i < v2.length; i++) {
			if (StringUtils.isNotEmpty(v2[i])) {
				arr2[i] = Integer.valueOf(v2[i]);
			}
		}

		return compare(arr1, arr2, 0, 4);//从0位开始比较，往后递归
	}

	/**
	 * 比较，前提是两个数组的长度要相等
	 *
	 * @param arr
	 * @param brr
	 * @param i
	 * @param n   比较的最大位数，如果输入的超出比较位数的数组，返回前面N位比较的结果
	 * @return
	 */
	public static int compare(int arr[], int brr[], int i, int n) {
		if (i < n) {
			if (arr[i] > brr[i]) {
				return 1;
			} else if (arr[i] < brr[i]) {
				return -1;
			} else {
				return compare(arr, brr, i + 1, n);
			}
		} else {
			return 0;
		}

	}

	/**
	 * 版本更新提示消息
	 *
	 * @param sVersionNo
	 * @param checkCode
	 * @return
	 */
	private String getMsgByCheckCode(String sVersionNo, int checkCode) {
		String lang = WebUtil.getLanguage();
		switch (checkCode) {
			case 0:
				return getMessage(lang, CommonEnums.AppGradeUpMessage.APP_CHECK_MESSAGE_0.sc, CommonEnums.AppGradeUpMessage.APP_CHECK_MESSAGE_0.tc, CommonEnums.AppGradeUpMessage.APP_CHECK_MESSAGE_0.en);
			case 1:
				return String.format(getMessage(lang, CommonEnums.AppGradeUpMessage.APP_CHECK_MESSAGE_1.sc, CommonEnums.AppGradeUpMessage.APP_CHECK_MESSAGE_1.tc, CommonEnums.AppGradeUpMessage.APP_CHECK_MESSAGE_1.en), sVersionNo);
			case 2:
				return String.format(getMessage(lang, CommonEnums.AppGradeUpMessage.APP_CHECK_MESSAGE_2.sc, CommonEnums.AppGradeUpMessage.APP_CHECK_MESSAGE_2.tc, CommonEnums.AppGradeUpMessage.APP_CHECK_MESSAGE_2.en), sVersionNo);
			case 3:
				// 检测是否在可更新时间点,如果不是更新的时间点，那么就建议更新。否则强制更新
				if ("1".equals(isForceUpdate)) { //表示是否启用该功能
					/*if (!isForceUpdateReminder()) {
						return UserStaticType.APP_CHECK_MESSAGE_2;
					}*/
				}

				return String.format(getMessage(lang, CommonEnums.AppGradeUpMessage.APP_CHECK_MESSAGE_3.sc, CommonEnums.AppGradeUpMessage.APP_CHECK_MESSAGE_3.tc, CommonEnums.AppGradeUpMessage.APP_CHECK_MESSAGE_3.en), sVersionNo);
			default:
				log.warn("不存在此checkCode相应的内容:" + checkCode);
				break;
		}
		return "";
	}

	String getMessage(String lang, String sc, String tc, String en) {
		if (CommonConstant.ZH_CN.equals(lang)) {
			return sc;
		} else if (CommonConstant.EN_US.equals(lang)) {
			return en;
		} else {
			return tc;
		}
	}


}
