package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.cust.apivo.req.FuncWhiteListQueryReq;
import com.minigod.zero.cust.entity.FuncWhiteListEntity;
import com.minigod.zero.cust.feign.IFuncWhiteListClient;
import com.minigod.zero.manage.vo.request.FuncWhiteSubmitReqVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 功能白名单用户信息 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX+"/funcWhite")
@Api(value = "功能白名单用户信息", tags = "功能白名单用户信息接口")
public class FuncWhiteListController extends ZeroController {


	@Resource
	private IFuncWhiteListClient funcWhiteListClient;


	/**
	 * 功能白名单用户信息 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "分页", notes = "传入funcWhiteList")
	public R<Page<FuncWhiteListEntity>> page(FuncWhiteListQueryReq funcWhiteListQueryReq, Query query) {
		Page<FuncWhiteListEntity> pages = funcWhiteListClient.queryPageFuncWhiteList(funcWhiteListQueryReq, query.getCurrent(),query.getSize());
		return R.data(pages);
	}


	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入funcWhiteList")
	public R<Object> save(@Valid @RequestBody FuncWhiteSubmitReqVo reqVo) {
		if (StringUtils.isBlank(reqVo.getCustId())||StringUtils.isBlank(reqVo.getFuncId())){
			return R.fail("参数缺失");
		}
		FuncWhiteListEntity base = new FuncWhiteListEntity();
		Date now = DateUtil.now();
		Long userId = AuthUtil.getUserId();
		base.setFuncId(reqVo.getFuncId());
		base.setCreateTime(now);
		base.setUpdateTime(now);
		base.setCreateUser(userId);
		base.setUpdateUser(userId);

		String digitPattern = "^\\d+$";
		Pattern pattern = Pattern.compile(digitPattern);
		Matcher matcher = pattern.matcher(reqVo.getCustId());
		if (matcher.matches()){
			List<FuncWhiteListEntity> funcWhiteListEntities = funcWhiteListClient.queryFuncWhiteList(List.of(Func.toLong(reqVo.getCustId())), reqVo.getFuncId());
			if (CollectionUtil.isNotEmpty(funcWhiteListEntities)){
				return R.fail("该用户已绑定该功能");
			}
			base.setCustId(Func.toLong(reqVo.getCustId()));
			return R.status(funcWhiteListClient.save(base));
		}else if (reqVo.getCustId().matches("^(\\d+,)+\\d+$")){
			List<Long> ids = Func.toLongList(reqVo.getCustId()).stream().distinct().collect(Collectors.toList());
			List<FuncWhiteListEntity> funcWhiteListEntities = funcWhiteListClient.queryFuncWhiteList(ids, reqVo.getFuncId());
			List<Long> dbIdList = funcWhiteListEntities.stream().map(FuncWhiteListEntity::getCustId).collect(Collectors.toList());
			if (CollectionUtil.isNotEmpty(dbIdList)){
				return R.data(ResultCode.FAILURE.getCode(),dbIdList,dbIdList+"已经存在");
			}else {
				for (Long id : ids) {
					base.setCustId(id);
					funcWhiteListClient.save(base);
				}
				return R.success();
			}
		}else {
			return R.fail("参数格式有误,请检查!");
		}
	}

	/**
	 * 功能白名单用户信息 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入funcWhiteList")
	public R<Object> update(@Valid @RequestBody FuncWhiteListEntity funcWhiteList) {
		Long userId = AuthUtil.getUserId();
		funcWhiteList.setUpdateUser(userId);
		return R.status(funcWhiteListClient.updateById(funcWhiteList));
	}


	private String matchStringType(String custId){
		// 使用正则表达式匹配纯数字
		String digitPattern = "^\\d+$";
		Pattern pattern = Pattern.compile(digitPattern);
		Matcher matcher = pattern.matcher(custId);

		if (matcher.matches()) {
			return "纯数字";
		} else if (custId.matches("^(\\d+,)+\\d+$")) {
			return "以逗号分割的纯数字";
		} else {
			return "不匹配任何类型";
		}
	}

}
