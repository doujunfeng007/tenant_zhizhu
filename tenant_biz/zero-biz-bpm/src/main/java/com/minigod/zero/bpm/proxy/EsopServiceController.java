package com.minigod.zero.bpm.proxy;

import com.minigod.zero.bpm.service.openAccount.IBpmAccountService;
import com.minigod.zero.bpm.vo.BpmHkidrGrantsVO;
import com.minigod.zero.bpm.vo.UserHkidrVo;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.cust.apivo.req.RegisterReq;
import com.minigod.zero.cust.feign.ICustAuthClient;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:yanghu.luo
 * @create: 2023-06-09 15:55
 * @Description: esop服务对接接口
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/proxy/esop")
public class EsopServiceController {

	private final IBpmAccountService bpmAccountService;
	private final ICustAuthClient custAuthClient;

	/**
	 * HKIDR授权
	 */
	@PostMapping("/grant_hkidr")
	@ApiOperation(value = "HKIDR授权", notes = "HKIDR授权")
	public R grantHkidr(@RequestBody BpmHkidrGrantsVO vo) {
		if(null == vo || null == vo.getCustId() || null == vo.getGrantType()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		UserHkidrVo hkidrVo = new UserHkidrVo();
		hkidrVo.setUserId(vo.getCustId().intValue());
		hkidrVo.setHkidrStatusApproach(vo.getGrantType());
		return bpmAccountService.grantHkidrEsop(hkidrVo);
	}

	/**
	 * esop账户激活注册
	 */
	@PostMapping("/esop_register")
	@ApiOperation(value = "esop账户激活注册", notes = "esop账户激活注册")
	public R esopRegister(@RequestBody RegisterReq vo) {
		return custAuthClient.esopCustRegister(vo);
	}

}
