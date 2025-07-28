package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.manage.vo.request.SensitiveWordRequestVo;
import com.minigod.zero.manage.vo.response.SensitiveWordLogRespDto;
import com.minigod.zero.manage.vo.response.SensitiveWordRespDto;
import com.minigod.zero.manage.mapper.SensitiveWordLogMapper;
import com.minigod.zero.manage.service.ISensitiveWordService;
import com.minigod.zero.manage.vo.request.SensitiveWordEditVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * @author caizongtai
 * @since 2023/7/17 18:12
 */

@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX  + "/sensitive_word")
@Api(value = "敏感词接口", tags = "敏感词接口")
public class SensitiveWordController {
	@Resource
	private ISensitiveWordService sensitiveWordService;
	@Resource
	private SensitiveWordLogMapper sensitiveWordLogMapper;


	/**
	 * 敏感词分页查找
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "分页", notes = "传入SensitiveWordRequestVo")
	public R<IPage<SensitiveWordRespDto>> page(SensitiveWordRequestVo requestVo, Query query) {
		if (null==query||null==query.getCurrent()||null==query.getSize()){
			return R.fail("分页参数缺失");
		}
		return R.data(sensitiveWordService.page(requestVo,query));
	}

	@PostMapping("/save")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "添加敏感词", notes = "传入SensitiveWordRequestVo")
	public R save(@RequestBody SensitiveWordRequestVo requestVo) {
		if (StringUtil.isBlank(requestVo.getWord()) || null == requestVo.getScope()){
			return R.fail("敏感词或作用域为空");
		}
		return sensitiveWordService.save(requestVo);
	}

	@PostMapping("/change_status")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "上下架敏感词", notes = "传入SensitiveWordEditVo")
	public R update(@RequestBody SensitiveWordEditVo requestVo) {
		if (requestVo.getId()==null||requestVo.getStatus()==null){
			return R.fail("缺少必要请求参数");
		}
		return sensitiveWordService.changeStatus(requestVo);
	}


	@PostMapping("/edit_word")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "编辑敏感词", notes = "传入SensitiveWordEditVo")
	public R editWord(@RequestBody SensitiveWordEditVo requestVo) {
		if (requestVo.getId()==null){
			return R.fail("缺少必要请求参数");
		}
		return sensitiveWordService.editWord(requestVo);
	}


	/**
	 * 敏感词状态分页查找
	 *
	 */
	@GetMapping("/detail_page")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "分页", notes = "传入SensitiveWordRequestVo")
	public R<IPage<SensitiveWordLogRespDto>> detailPage(SensitiveWordRequestVo requestVo, Query query) {
		if (null==query||null==query.getCurrent()||null==query.getSize()){
			return R.fail("分页参数缺失");
		}
		return R.data(sensitiveWordService.detailPage(requestVo,query));
	}


}
