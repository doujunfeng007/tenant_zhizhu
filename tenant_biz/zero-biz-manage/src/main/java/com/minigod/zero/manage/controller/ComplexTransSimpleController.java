package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.manage.service.IComplexTranSimpleService;
import com.minigod.zero.manage.vo.request.ComplexTranSimpleRequestVo;
import com.minigod.zero.manage.vo.response.ComplexTranSimpleResponseDto;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 掌上智珠
 * @since 2023/7/28 16:14
 */

@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/complex_tran_simple")
@Api(value = "简繁转换接口", tags = "简繁转换接口")
@Slf4j
public class ComplexTransSimpleController {

	@Resource
	private IComplexTranSimpleService complexTranSimpleService;

	/**
	 * 目前没用到，使用的时候需要改一下变成RquestBody来进行传输
	 * @param type
	 * @param txt
	 * @return
	 */
	@GetMapping("/tran")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "简繁转换", notes = "简繁转换")
	public R<String> tran(@RequestParam Integer type,@RequestParam String txt) {
		if (StringUtils.isEmpty(txt)){
			return R.fail("输入不能为空");
		}
		return R.data(complexTranSimpleService.trans(type,txt));

	}

	/**
	 * 敏感词分页查找
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "分页", notes = "传入ComplexTranSimpleRequestVo")
	public R<IPage<ComplexTranSimpleResponseDto>> page(ComplexTranSimpleRequestVo requestVo, Query query) {
		if (null==query||null==query.getCurrent()||null==query.getSize()){
			return R.fail("分页参数缺失");
		}
		return R.data(complexTranSimpleService.page(requestVo,query));
	}

	@PostMapping("/save")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "添加转换词", notes = "传入ComplexTranSimpleRequestVo")
	public R save(@RequestBody ComplexTranSimpleRequestVo requestVo) {
		String beforeReplace = requestVo.getBeforeReplace();
		String afterReplace = requestVo.getAfterReplace();
		Integer status = requestVo.getStatus();

		if (StringUtils.isEmpty(beforeReplace)||StringUtils.isEmpty(afterReplace)||null==status){
			return R.fail("参数缺失");
		}
		return complexTranSimpleService.save(requestVo);
	}


	@PostMapping("/change_status")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "上下架", notes = "传入ComplexTranSimpleRequestVo")
	public R update(@RequestBody ComplexTranSimpleRequestVo requestVo) {
		Long id = requestVo.getId();
		Integer status = requestVo.getStatus();

		if (null==status||null==id){
			return R.fail("参数缺失");
		}
		return complexTranSimpleService.changeStatus(requestVo);
	}

	@PostMapping("/edit")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "编辑词", notes = "传入ComplexTranSimpleRequestVo")
	public R editWord(@RequestBody ComplexTranSimpleRequestVo requestVo) {
		Long id = requestVo.getId();
		if (null==id){
			return R.fail("参数缺失");
		}
		return complexTranSimpleService.edit(requestVo);
	}
}
