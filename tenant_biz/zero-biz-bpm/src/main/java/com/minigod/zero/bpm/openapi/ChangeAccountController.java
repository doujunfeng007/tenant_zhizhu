package com.minigod.zero.bpm.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpm.dto.acct.req.CaCertificationDto;
import com.minigod.zero.bpm.dto.acct.req.ChangeSecuritiesDto;
import com.minigod.zero.bpm.service.openAccount.IChangeAccountService;
import com.minigod.zero.bpm.vo.AcctChangeInfoTempVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.support.Kv;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/change_account")
@Api(value = "修改资料业务", tags = "修改资料业务")
public class ChangeAccountController {

	@Resource
	private IChangeAccountService changeAccountService;

	@PostMapping("/get_securities_temp")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "证券资料缓存查询", notes = "证券资料缓存查询")
	public R<AcctChangeInfoTempVO> getSecuritiesTemp(@RequestBody CommonReqVO<ChangeSecuritiesDto> vo) {
		if(null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return changeAccountService.getSecuritiesTemp(AuthUtil.getCustId(),vo.getParams());
	}

	@PostMapping("/upd_securities_temp")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "提交修改证券资料", notes = "提交修改证券资料")
	public R updSecuritiesTemp(@RequestBody CommonReqVO<ChangeSecuritiesDto> vo) {
		if(null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return changeAccountService.updSecuritiesTemp(AuthUtil.getCustId(),vo.getParams());
	}

	@PostMapping("/remove_img")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "修改资料图片删除", notes = "修改资料图片删除")
	public R removeImg(@RequestBody CommonReqVO<ChangeSecuritiesDto> vo) {
		if(null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return changeAccountService.removeImg(AuthUtil.getCustId(),vo.getParams());
	}

	@PostMapping("/get_securities_info_real")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "查询客户原始证券资料", notes = "查询客户原始证券资料")
	public R<AcctChangeInfoTempVO> getSecuritiesInfoReal(@RequestBody CommonReqVO<ChangeSecuritiesDto> vo) {
		if(null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return changeAccountService.getSecuritiesInfoReal(AuthUtil.getCustId(),vo.getParams());
	}

	@PostMapping("/get_securities_check_status")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "查询客户线上修改资料审核状态", notes = "查询客户线上修改资料审核状态")
	public R<AcctChangeInfoTempVO> getSecuritiesCheckStatus(@RequestBody CommonReqVO<ChangeSecuritiesDto> vo) {
		if(null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return changeAccountService.getSecuritiesCheckStatus(AuthUtil.getCustId(),vo.getParams());
	}

	@PostMapping("/get_securities_changing_status")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "查询客户是否正在修改资料", notes = "查询客户是否正在修改资料")
	public R<Boolean> getSecuritiesChangingStatus() {
 		return changeAccountService.getSecuritiesChangingStatus(AuthUtil.getCustId());
	}

	@PostMapping("/reset_check_status")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "线上修改资料审核被拒，重新编辑资料，重置审核状态至未提交", notes = "线上修改资料审核被拒，重新编辑资料，重置审核状态至未提交")
	public R resetCheckStatus(@RequestBody CommonReqVO<ChangeSecuritiesDto> vo) {
		if(null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return changeAccountService.resetCheckStatus(AuthUtil.getCustId(),vo.getParams());
	}

	@PostMapping("/save_img")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "保存修改凭证图片", notes = "保存修改凭证图片")
	public R<Kv> changeSaveImg(@RequestBody CommonReqVO<CaCertificationDto> vo) {
		return changeAccountService.changeSaveImg(vo.getParams());
	}






}
