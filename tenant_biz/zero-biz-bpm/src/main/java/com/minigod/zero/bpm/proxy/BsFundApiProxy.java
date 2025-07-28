package com.minigod.zero.bpm.proxy;

import cn.hutool.json.JSONUtil;
import com.minigod.zero.bpm.constant.CashConstant;
import com.minigod.zero.bpm.service.api.IBsFundApiProxyService;
import com.minigod.zero.bpm.vo.request.BsCashDepositReqVo;
import com.minigod.zero.bpm.vo.request.BsStkTrdCaleReqVo;
import com.minigod.zero.bpm.vo.request.BsUserCheckReqVo;
import com.minigod.zero.bpm.vo.response.ResponseVO;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 银证中心接口
 *
 * @author Zhe.Xiao
 */
@RestController
@RequestMapping(AppConstant.PROXY_API_PREFIX + "/bsFund_api")
@Api(value = "银证中心接口", tags = "银证中心接口")
@Slf4j
public class BsFundApiProxy {

	@Resource
	private IBsFundApiProxyService bsFundApiProxyService;

	@PostMapping("/checkSecUserInfo")
	@ApiOperation(value = "银证中心校验券商用户", notes = "银证中心校验券商用户")
	public ResponseVO checkSecUserInfo(@RequestBody CommonReqVO<BsUserCheckReqVo> req) {
		ResponseVO rt = new ResponseVO();
		rt.setCode(ResultCode.PARAMETER_ERROR.getCode());

		log.info("BsFundApiService checkSecUserInfo start , request:{}", JSONUtil.toJsonStr(req));

		if (req == null || req.getParams() == null) {
			log.info("checkSecUserInfo => params error");
			rt.setMessage("parameter error");
			return rt;
		}
		if (StringUtils.isBlank(req.getParams().getClientId())) {
			log.info("checkSecUserInfo => clientId is blank");
			rt.setMessage("need parameter clientId");
			return rt;
		}
		if (StringUtils.isBlank(req.getParams().getIdNo()) && StringUtils.isBlank(req.getParams().getHidNo())) {
			log.info("checkSecUserInfo => idNo is blank and hidNo is blank");
			rt.setMessage("need parameter idNo or hidNo");
			return rt;
		}

		return bsFundApiProxyService.checkSecUserInfo(req.getParams());
	}

	@PostMapping("/getSecUserInfo")
	@ApiOperation(value = "银证中心获取用户信息", notes = "银证中心获取用户信息")
	public ResponseVO getSecUserInfo(@RequestBody CommonReqVO<BsUserCheckReqVo> req) {
		ResponseVO rt = new ResponseVO();
		rt.setCode(ResultCode.PARAMETER_ERROR.getCode());

		log.info("BsFundApiService getSecUserInfo start , request:{}", JSONUtil.toJsonStr(req));

		if (req == null || req.getParams() == null) {
			log.info("getSecUserInfo => params error");
			rt.setMessage("parameter error");
			return rt;
		}
		BsUserCheckReqVo params = req.getParams();
		if (StringUtils.isBlank(params.getClientId()) && null == params.getUserId()) {
			log.info("getSecUserInfo => clientId and userId is null");
			rt.setMessage("need parameter clientId");
			return rt;
		}

		return bsFundApiProxyService.getBsSecUserInfo(req.getParams());
	}

	@PostMapping("/bsCashDeposit")
	@ApiOperation(value = "银证中心请求资金存入", notes = "银证中心请求资金存入")
	public ResponseVO bsCashDeposit(@RequestBody CommonReqVO<BsCashDepositReqVo> req) {
		ResponseVO rt = new ResponseVO();
		log.info("BsFundApiService bsCashDeposit start , request:{}", JSONUtil.toJsonStr(req));

		if (req.getParams() == null
			|| req.getParams().getBsBank() == null
			|| StringUtils.isBlank(req.getParams().getDepositStatementId())
			|| StringUtils.isBlank(req.getParams().getClientId())
			|| StringUtils.isBlank(req.getParams().getValueDate())) {
			rt.setCode(ResultCode.PARAMETER_ERROR.getCode());
			rt.setMessage(ResultCode.PARAMETER_ERROR.getMessage());
			return rt;
		}
		if (StringUtils.isBlank(req.getParams().getMoneyType())
			|| (!CashConstant.MoneyTypeDescEnum.CNY.getValue().equals(req.getParams().getMoneyType())
			&& !CashConstant.MoneyTypeDescEnum.USD.getValue().equals(req.getParams().getMoneyType())
			&& !CashConstant.MoneyTypeDescEnum.HKD.getValue().equals(req.getParams().getMoneyType()))) {
			rt.setCode(ResultCode.PARAMETER_ERROR.getCode());
			rt.setMessage("param moneyType error");
			return rt;
		}

		return bsFundApiProxyService.bsCashDeposit(req.getParams());
	}

	@PostMapping("/getStkTrdCale")
	@ApiOperation(value = "银证中心获取交易日历", notes = "银证中心获取交易日历")
	public ResponseVO getStkTrdCale(@RequestBody CommonReqVO<BsStkTrdCaleReqVo> req) {
		log.info("BsFundApiService getStkTrdCale start , request:{}", JSONUtil.toJsonStr(req));
		return bsFundApiProxyService.getStkTrdCale(req.getParams());
	}
}
