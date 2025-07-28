package com.minigod.zero.cust.feign;

import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.apivo.req.FindCustByPhonesReq;
import com.minigod.zero.cust.entity.CustInfoEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 客户信息表 Feign接口类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@FeignClient(
    value = AppConstant.SERVICE_BIZ_CUST_NAME
)
public interface ICustInfoClient {

    String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/custInfo";
	String USER_INFO_BY_USER_ID = API_PREFIX + "/user-info-by-user-id";
	String ALL_CUST_ID = API_PREFIX + "/all-cust-id";
	String FIND_BY_CUSTIDS = API_PREFIX + "/find_by_custids";
	String FIND_CUST_BY_PHONES = API_PREFIX + "/find_cust_by_phones";
	String FIND_IN_CHANNELS = API_PREFIX + "/find_in_channels";
	String FIND_NOT_IN_CHANNELS = API_PREFIX + "/find_not_in_channels";
	String FIND_ALL_CUST_INFO = API_PREFIX + "/find_all_cust_info";
	String UPDATE_CUST_DEVICE_CHANNEL = API_PREFIX + "/updateDeviceChannel";
	String UPDATE_CUSTPWD_BY_ID = API_PREFIX + "/update_custpwd_by_id";
	String UPDATE_EMAIL_BY_ID = API_PREFIX + "/update_email_by_id";
	String SELECT_CUSTINFO_LIST_BY_IDS = API_PREFIX + "/selectCustInfoListByIds";
	String SELECT_CUSTINFO_LIST_BY_CELLPHONE = API_PREFIX + "/selectCustInfoListByCellPhone";

	/**
	 * 根据userId获取用户信息
	 * @param userId
	 * @return
	 */
	@GetMapping(USER_INFO_BY_USER_ID)
	CustInfoEntity userInfoByUserId(@RequestParam("userId") Long userId);

	/**
	 * 批量根据custId获取
	 * @param custIds
	 * @return
	 */
	@GetMapping(FIND_BY_CUSTIDS)
	List<CustInfoEntity> findByCustIds(@RequestParam("custIds") List<Long> custIds);

	/**
	 * 批量根据手机号(带区号) 获取Map key-手机号 value-客户ID
	 * @param req
	 * @return
	 */
	@PostMapping(FIND_CUST_BY_PHONES)
	Map<String, Long> findCustByPhones(@RequestBody FindCustByPhonesReq req);

	@GetMapping(ALL_CUST_ID)
	List<Long> allCustId();

	@GetMapping(FIND_IN_CHANNELS)
	List<Long> findCustInfoInChannels(@RequestParam("channelIds") String[] channelIds);

	@GetMapping(FIND_NOT_IN_CHANNELS)
	List<Long> findUserInfoNotInChannels(@RequestParam("channelIds") String[] channelIds);

	/**
	 * 查询用户集
	 * @return
	 */
	@GetMapping(FIND_ALL_CUST_INFO)
	List<CustInfoEntity> allCustInfo(@RequestParam("custIds") List<Long> custIds);

	@GetMapping(UPDATE_CUST_DEVICE_CHANNEL)
    void updateChannel(@RequestParam("custId")Long custId, @RequestParam("channel")String channel);


	@PostMapping(UPDATE_CUSTPWD_BY_ID)
	void updateCustpwdById(@RequestParam("custId")Long custId,
						   @RequestParam("password")String password,
						   @RequestParam(value = "changeType",required = false)Integer changeType);

	@PostMapping(UPDATE_EMAIL_BY_ID)
	R updateEmailById(@RequestParam("custId")Long custId,
						   @RequestParam("email")String email);

	@PostMapping(SELECT_CUSTINFO_LIST_BY_IDS)
	List<CustInfoEntity> selectCustInfoListByIds(@RequestParam("custIds")List<Long> custIds);

	@PostMapping(SELECT_CUSTINFO_LIST_BY_CELLPHONE)
	List<CustInfoEntity> selectCustInfoListByCellPhone(@RequestParam("cellPhone")String cellPhone);
}
