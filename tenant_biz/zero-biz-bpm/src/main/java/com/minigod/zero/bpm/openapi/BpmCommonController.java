package com.minigod.zero.bpm.openapi;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpm.service.BpmCommonService;
import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpm.dto.acct.req.CommonImgDto;
import com.minigod.zero.bpm.dto.acct.resp.CommonImgRespDto;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.support.Kv;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 公共模块 控制器
 *
 * @author zejie.weng
 * @since 2023-05-27
 */
@AllArgsConstructor
@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/bpm_common")
@Api(value = "bpm公共模块", tags = "bpm公共模块")
public class BpmCommonController {

	private final BpmCommonService bpmCommonService;

	@PostMapping("/upload_img")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "上传图片", notes = "上传图片")
	public R<Kv> uploadImg(@RequestBody CommonReqVO<CommonImgDto> vo) {
		return bpmCommonService.uploadImg(vo.getParams());
	}

	@PostMapping("/download_img")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "获取客户图片", notes = "获取客户图片")
	public R<List<CommonImgRespDto>> downloadImg(@RequestBody CommonReqVO<CommonImgDto> vo) {
		if(null == vo || null == vo.getParams()) return R.fail(ResultCode.PARAM_VALID_ERROR);
		return bpmCommonService.downloadImg(vo.getParams());
	}

}
