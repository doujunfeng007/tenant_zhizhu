package com.minigod.zero.cust.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.cust.entity.CustDeviceEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 客户设备信息 Feign接口类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@FeignClient(
    value = AppConstant.SERVICE_BIZ_CUST_NAME
)
public interface ICustDeviceClient {

    String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/CustDevice";
    String GET_DEVICE_LIST = API_PREFIX + "/get-device-list";
	String GET_CUST_DEVICE = API_PREFIX + "/get-cust-device";
	String GET_CUST_ID_BY_DEVICE_CODE = API_PREFIX + "/get-cust-id-by-device-code";
	String ALL_CUST_ID = API_PREFIX + "/all-cust-id";
	String GET_BY_DEVICE_CODE = API_PREFIX + "/getByDeviceCode";
	String GET_ALL_DEVICE_LANG_LIST = API_PREFIX + "/getAllDeviceLangList";

	/**
	 * 根据客户id获取设备信息
	 */
	@GetMapping(GET_CUST_DEVICE)
	CustDeviceEntity getCustDevice(@RequestParam("userId") Long custId);

	/**
	 * 根据设备号获取客户id
	 * @return
	 */
	@GetMapping(GET_CUST_ID_BY_DEVICE_CODE)
	Long getCustIdByDeviceCode(@RequestParam("deviceCode") String deviceCode);

	/**
	 * 根据客户id批量获取设备信息
	 */
	@GetMapping(GET_DEVICE_LIST)
	List<CustDeviceEntity> getDeviceList(@RequestParam("custIds") List<Long> custIds);

	/**
	 * 获取设备表绑定的全部客户id
	 * @return
	 */
	@GetMapping(ALL_CUST_ID)
	List<Long> allCustId();

	/**
	 * 根据设备号获取设备信息
	 * @return
	 */
	@GetMapping(GET_BY_DEVICE_CODE)
	CustDeviceEntity getByDeviceCode(@RequestParam("deviceCode") String deviceCode);

	/**
	 * 根据客户id批量获取设备语言
	 */
	@GetMapping(GET_ALL_DEVICE_LANG_LIST)
	List<CustDeviceEntity> getAllDeviceLangList(@RequestParam("custIds") List<Long> custIds);
}
