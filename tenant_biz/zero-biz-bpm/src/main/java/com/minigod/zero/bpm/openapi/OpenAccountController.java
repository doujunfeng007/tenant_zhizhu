package com.minigod.zero.bpm.openapi;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpm.dto.acct.req.CaCertificationDto;
import com.minigod.zero.bpm.dto.acct.req.OpenAccountImgReqDto;
import com.minigod.zero.bpm.dto.acct.req.OpenInfoTempDto;
import com.minigod.zero.bpm.dto.acct.resp.DataDictionaryDto;
import com.minigod.zero.bpm.dto.OpenAccountStatusRespDto;
import com.minigod.zero.bpm.service.openAccount.IOpenAccountService;
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
import java.util.List;

@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/open_account")
@Api(value = "开户业务", tags = "开户业务")
public class OpenAccountController {

	@Resource
	private IOpenAccountService openAccountService;


	@PostMapping("/ocr")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "身份证OCR识别", notes = "身份证OCR识别")
	public R ocr(@RequestBody CommonReqVO<OpenAccountImgReqDto> vo) {
		if(null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return openAccountService.ocr(vo.getParams());
	}

	@PostMapping("/open_acc_status")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "获取用户开户状态信息", notes = "获取用户开户状态信息")
	public R<OpenAccountStatusRespDto> openAccStatus() {
		return openAccountService.openAcctStatus();
	}

	@PostMapping("/save_img")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "保存修改开户文件", notes = "保存修改开户文件")
	public R<Kv> openSaveImg(@RequestBody CommonReqVO<CaCertificationDto> vo) {
		return openAccountService.openSaveImg(vo.getParams());
	}


	@PostMapping("/real_auth")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "证件信息校验", notes = "证件信息校验")
	public R<Kv> realAuth(@RequestBody CommonReqVO<CaCertificationDto> vo) {

		if(null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);

		return openAccountService.realAuth(AuthUtil.getCustId(),vo.getParams());
	}

	@PostMapping("/save_open_info_temp")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "保存开户缓存信息", notes = "保存开户缓存信息")
	public R saveOpenInfoTemp(@RequestBody CommonReqVO<OpenInfoTempDto> vo) {
		if(null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return openAccountService.saveOpenInfoTemp(AuthUtil.getCustId(),vo.getParams());
	}

	@PostMapping("/get_open_info_temp")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "获取相关开户数据缓存", notes = "获取相关开户数据缓存")
	public R<Kv> getOpenInfoTemp(@RequestBody CommonReqVO<OpenInfoTempDto> vo) {
		if(null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return openAccountService.getOpenInfoTemp(AuthUtil.getCustId(),vo.getParams());
	}

	@PostMapping("/save_open_info")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "保存开户申请信息", notes = "保存开户申请信息")
	public R saveOpenInfo(@RequestBody CommonReqVO<OpenInfoTempDto> vo) {
		if(null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return openAccountService.saveOpenInfo(AuthUtil.getCustId(),vo.getParams());
	}

	@PostMapping("/update_open_email")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "修改开户邮箱", notes = "修改开户邮箱")
	public R updOpenEmail(@RequestBody CommonReqVO<CaCertificationDto> vo) {
		if(null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return openAccountService.updOpenEmail(AuthUtil.getCustId(),vo.getParams());
	}

	@PostMapping("/bpm_data_code")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "获取BPM字典数据", notes = "获取BPM字典数据")
	public R<List<DataDictionaryDto>> bpmDataCode(@RequestBody CommonReqVO<OpenInfoTempDto> vo) {
		if(null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return openAccountService.bpmDataCode(vo.getParams());
	}

	@PostMapping("/get_name_py")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "获取姓名拼音", notes = "获取姓名拼音")
	public R getUserNamePy(@RequestBody CommonReqVO<CaCertificationDto> vo) {
		if(null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return openAccountService.getUserNamePy(vo.getParams());
	}


}

