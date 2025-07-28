package com.minigod.zero.bpmn.module.margincredit.feign;

import com.minigod.zero.bpmn.module.margincredit.vo.CustMarginCreditVO;
import com.minigod.zero.bpmn.module.margincredit.vo.IncreaseCreditLimitVO;
import com.minigod.zero.bpmn.module.margincredit.vo.MarginCreditAdjustApplyDTO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author chen
 * @ClassName IMarginCreditClient.java
 * @Description 信用额度
 * @createTime 2024年03月09日 11:09:00
 */
@FeignClient(
	value = AppConstant.SERVICE_BIZ_BPMN_NAME
)
public interface IMarginCreditClient {

	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/marginCredit";

	String QUERY_CUST_MARGIN_CREDIT = API_PREFIX + "/getByFundAccount";

	String CUST_SUBMIT_MARGIN_CREDIT = API_PREFIX + "/submit";

	String CUST_QUERY_MARGIN_CREDIT = API_PREFIX + "/queryByCust";

	String CUST_QUERY_REVIEW_DATA = API_PREFIX + "/isReviewData";

	String MARGIN_CREDIT_ARCHIVE = API_PREFIX + "/archive";

	/**
	 * 查询当前信用额度
	 * @param tradeAccount
	 * @param fundAccount
	 * @return
	 */
	@GetMapping(QUERY_CUST_MARGIN_CREDIT)
	List<CustMarginCreditVO> getByFundAccount(@RequestParam String tradeAccount,@RequestParam String fundAccount);

	/**
	 * 提交信用额度申请
	 * @param dto
	 * @return
	 */
	@PostMapping(CUST_SUBMIT_MARGIN_CREDIT)
	R submit(@RequestBody MarginCreditAdjustApplyDTO dto);

	/**
	 * 获取用户申请列表
	 * @param custId
	 * @return
	 */
	@GetMapping(CUST_QUERY_MARGIN_CREDIT)
	List<IncreaseCreditLimitVO> selectListByCustId(@RequestParam Long custId);

	/**
	 * 判断是否有审核中的数据
	 * @param custId
	 * @return
	 */
	@GetMapping(CUST_QUERY_REVIEW_DATA)
	boolean isReviewData(@RequestParam Long custId);

	@PostMapping(MARGIN_CREDIT_ARCHIVE)
    R marginCreditArchiveJob(Map<String, Object> map);
}
