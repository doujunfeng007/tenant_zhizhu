package com.minigod.zero.bpm.proxy;

import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpm.dto.acct.resp.SecuritiesCacheDto;
import com.minigod.zero.bpm.dto.acct.resp.YfundInfoCacheDto;
import com.minigod.zero.bpm.service.api.IBpmProxyService;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(AppConstant.PROXY_API_PREFIX + "/fund")
@Api(value = "基金调用客户接口", tags = "基金调用客户接口")
public class FundApiController {

	@Resource
	private IBpmProxyService bpmProxyService;

	@PostMapping("/yfund_info")
	@ApiOperation(value = "基金账户信息获取", notes = "基金账户信息获取")
	public R<List<YfundInfoCacheDto>> yfundInfo(@RequestBody CommonReqVO<List<String>> req) {
		if (req == null || CollectionUtil.isEmpty(req.getParams())) {
			return R.fail(ResultCode.PARAMS_PARAMETER_ERROR);
		}
		return bpmProxyService.yfundInfo(req.getParams());
	}


	@GetMapping("/sec_user_info")
	@ApiOperation(value = "获取证券用户信息", notes = "获取证券用户信息")
	public R<SecuritiesCacheDto> getSecUserInfo(@RequestParam Long custId) {
		if (null == custId) {
			return R.fail(ResultCode.PARAMS_PARAMETER_ERROR);
		}
		return bpmProxyService.getSecUserInfo(custId);
	}
}
