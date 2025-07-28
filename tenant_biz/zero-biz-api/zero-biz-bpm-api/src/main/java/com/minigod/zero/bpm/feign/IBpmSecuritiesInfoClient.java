package com.minigod.zero.bpm.feign;

import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
	value = AppConstant.SERVICE_BIZ_BPM_NAME
)
public interface IBpmSecuritiesInfoClient {

	String API_PREFIX = AppConstant.FEIGN_API_PREFIX;
	String SECURITIES_INFO_BY_CUST_IDS = API_PREFIX + "/securities_info_by_cust_ids";
	String SECURITIES_INFO_BY_CUST_NAME = API_PREFIX + "/securities_info_by_cust_name";
	String SECURITIES_INFO_BY_CUST_ID = API_PREFIX + "/securities_info_by_cust_id";
	String SECURITIES_INFO_LIKE_CUST_NAME = API_PREFIX + "/securities_info_like_cust_name";
	String SECURITIES_INFO_BY_PHONE = API_PREFIX + "/securities_info_by_phone";
	String SECURITIES_INFO_PLACE = API_PREFIX + "/securities_info_place";
	String SECURITIES_INFO_CLIENT_LEVEL = API_PREFIX + "/securities_info_client_level";

	@GetMapping(SECURITIES_INFO_BY_CUST_IDS)
	List<BpmSecuritiesInfoEntity> securitiesInfoByCustIds(@RequestParam("custIds") List<Long> custIds);

	@GetMapping(SECURITIES_INFO_BY_CUST_NAME)
	List<BpmSecuritiesInfoEntity> securitiesInfoByCustName(@RequestParam("custName") String custName);

	@GetMapping(SECURITIES_INFO_BY_CUST_ID)
	BpmSecuritiesInfoEntity securitiesInfoByCustId(@RequestParam("custId") Long custId);

	@GetMapping(SECURITIES_INFO_LIKE_CUST_NAME)
	List<BpmSecuritiesInfoEntity> securitiesInfoLikeCustName(@RequestParam("custName") String custName);

	@GetMapping(SECURITIES_INFO_BY_PHONE)
	BpmSecuritiesInfoEntity securitiesInfoByPhone(@RequestParam("area") String area,@RequestParam("phone") String phone);

	@PostMapping(SECURITIES_INFO_PLACE)
	R securitiesInfoPlace(@RequestBody BpmSecuritiesInfoEntity bpmSecuritiesInfoEntity, @RequestParam("tradeAccount") String tradeAccount, @RequestParam("accountType")  Integer accountType);

	@PutMapping(SECURITIES_INFO_CLIENT_LEVEL)
	R securitiesInfoClientLevelUpdate(@RequestParam("clientLevel") Integer clientLevel, @RequestParam("custId") Long custId);
}
