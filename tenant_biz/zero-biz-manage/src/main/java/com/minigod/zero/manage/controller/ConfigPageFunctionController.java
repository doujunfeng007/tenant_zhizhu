package com.minigod.zero.manage.controller;

import cn.jiguang.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.enums.EUserTypeEnum;
import com.minigod.zero.biz.common.enums.FunctionPageEnum;
import com.minigod.zero.manage.entity.ConfigPageFunctionEntity;
import com.minigod.zero.manage.entity.DiscoverIconEntity;
import com.minigod.zero.manage.service.IConfigPageFunctionService;
import com.minigod.zero.manage.vo.ConfigPageFunctionVO;
import com.minigod.zero.manage.vo.response.IconExtVO;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 配置页面组件 控制器
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/ConfigPageFunction")
@Api(value = "配置页面组件", tags = "配置页面组件接口")
public class ConfigPageFunctionController extends ZeroController {

	private final IConfigPageFunctionService ConfigPageFunctionService;


	/**
	 * 配置页面组件 列表
	 */
	@GetMapping("/index")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询Icon首页列表", notes = "传入ConfigPageFunction")
	public R<List<Object>> index(ConfigPageFunctionVO entity) {
		if (null == entity.getFunctionType()) {
			entity.setFunctionType(FunctionPageEnum.MYSTOCK.getTypeValue());
		}
		List list = null;
		if (FunctionPageEnum.FOCUS.getTypeValue() == entity.getFunctionType()) {
			list = ConfigPageFunctionService.getIndex();
		} else {
			list = ConfigPageFunctionService.getConfigPageFunctionList(entity.getFunctionType());
			handleFunctionRight(list);
		}
		return R.data(list);
	}

	/**
	 * 配置页面组件 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入ConfigPageFunction")
	public R<ConfigPageFunctionEntity> detail(ConfigPageFunctionEntity ConfigPageFunction) {
		ConfigPageFunctionEntity detail = ConfigPageFunctionService.getOne(Condition.getQueryWrapper(ConfigPageFunction));
		return R.data(detail);
	}

	public void handleFunctionRight(List<ConfigPageFunctionEntity> list) {
		for (ConfigPageFunctionEntity iconExtVO : list) {
			String functionRight = iconExtVO.getFunctionRight();
			if (null != functionRight && functionRight.trim().length() > 0) {
				int length = functionRight.length();
				StringBuilder sb = new StringBuilder();
				for (int index = 0; index < length; index++) {
					//List values = EUserTypeEnum.getValues();
					char item = functionRight.charAt(index);
					if (item == '1') {
						sb.append(EUserTypeEnum.getName(index + 1)).append("，");
					}
				}
				if (sb.toString().length() > 0) {
					iconExtVO.setFunctionRight(sb.toString().substring(0, sb.toString().length() - 1));
				}
			}

		}
	}

	@GetMapping("/list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询角色对应的图标列表", notes = "传入ConfigPageFunction")
	public R<List<IconExtVO>> list(Integer roleId, Integer showType) {
		List<IconExtVO> list = ConfigPageFunctionService.getList(roleId, showType);
		return R.data(list);
	}


	@GetMapping("/focus_list")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询角色对应的图标列表", notes = "传入ConfigPageFunction")
	public R<List<IconExtVO>> focusList(Integer roleId, Integer showType) {
		List<IconExtVO> list = ConfigPageFunctionService.getList(roleId, showType);
		return R.data(list);
	}

	/**
	 * 配置页面组件 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入ConfigPageFunction")
	public R<Object> submit(@Valid @RequestBody ConfigPageFunctionEntity ConfigPageFunction) {
		return R.status(ConfigPageFunctionService.saveOrUpdate(ConfigPageFunction));
	}

	/**
	 * 配置页面组件 删除
	 */
	@DeleteMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R<Object> remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(ConfigPageFunctionService.deleteLogic(Func.toLongList(ids)));
	}

	@GetMapping("/changeDisplayStatus")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "修改展示状态", notes = "传入ConfigPageFunction")
	public R<Object> changeDisplayStatus(@RequestParam("id")  Long id, @RequestParam("isDisplay")boolean isDisplay) {
		return ConfigPageFunctionService.updataDisplayStatus(id, isDisplay);
	}

	@GetMapping("/saveDisplayStatus")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "保存修改状态", notes = "传入ConfigPageFunction")
	public R<Object> saveDisplayStatus(String arrayJson, String sortIds) {
		Map<String, Object> changeMap = (Map) JSON.parse(arrayJson);
		for (String id : changeMap.keySet()) {
			ConfigPageFunctionService.updataDisplayStatus(Long.parseLong(id), Boolean.valueOf((String) changeMap.get(id)));
		}
		if (StringUtils.isNotEmpty(sortIds)) {
			String[] arr = sortIds.split("\\|");
			Long[] intTemp = new Long[arr.length];
			for (int i = 0; i < arr.length; i++) {
				intTemp[i] = Long.parseLong(arr[i]);
			}
			ConfigPageFunctionService.sortIcons(intTemp);
		}
		return R.success();
	}

	@GetMapping("/updateSort")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "置顶", notes = "传入ConfigPageFunction")
	public R<Object> updateSort(Long id) {
		ConfigPageFunctionService.updateSort(id);
		return R.success();
	}

	@GetMapping("/saveDisplayStatusAndSort")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "保存修改状态和排序", notes = "传入ConfigPageFunction")
	public R<Object> saveDisplayStatusAndSort(String arrayJson, String sortIds) {
		Map<String, Object> changeMap = (Map) JSON.parse(arrayJson);
		for (String id : changeMap.keySet()) {
			ConfigPageFunctionService.saveDisplayStatusAndSort(Long.parseLong(id), Boolean.valueOf((String) changeMap.get(id)));
		}
		if (StringUtils.isNotEmpty(sortIds)) {
			String[] arr = sortIds.split("\\|");
			Long[] intTemp = new Long[arr.length];
			for (int i = 0; i < arr.length; i++) {
				intTemp[i] = Long.parseLong(arr[i]);
			}
			ConfigPageFunctionService.sortConfigPageFunction(intTemp);
		}
		return R.success();
	}

	@GetMapping("/customerMgrData")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "浮窗展示修改列表", notes = "传入ConfigPageFunction")
	public R<Object> customerMgrData() {
		return R.data(ConfigPageFunctionService.queryDiscoverIconList());
	}

	@GetMapping("/saveUpdate")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "修改系统通知内容", notes = "传入参数")
	public R<Object> saveUpdate(Long[] id, String[] name, String[] urlPage, Boolean[] ipFilter, String
		[] smallFiles, String[] bigFiles, String[] androidFiles, String[] iosBlackFiles, String[] iosWhiteFiles, String[] androidBlackFiles,
								String[] androidWhiteFiles
	) {
		String msg = "";
		List<DiscoverIconEntity> discoverIconList = new ArrayList<>();
		try{
			for(int i = 0;i<id.length;i++){
				//上传文件
				DiscoverIconEntity discoverIcon = new DiscoverIconEntity();
				discoverIcon.setId(id[i]);
				discoverIcon.setFunctionName(name[i]);
				discoverIcon.setName(name[i]);
				discoverIcon.setUrlPage(urlPage[i]);
				discoverIcon.setIpFilter(ipFilter[i]);
				msg = checkFileType(smallFiles[i]);
				if(StringUtils.isNotEmpty(msg)){
					return R.fail(msg);
				}
				discoverIcon.setUrlSmall(smallFiles[i]);

				msg = checkFileType(bigFiles[i]);
				if(StringUtils.isNotEmpty(msg)){
					return R.fail(msg);
				}
				discoverIcon.setUrlBig(bigFiles[i]);

				msg = checkFileType(androidFiles[i]);
				if(StringUtils.isNotEmpty(msg)){
					return R.fail(msg);
				}
				discoverIcon.setUrlAndroid(androidFiles[i]);

				msg = checkFileType(iosBlackFiles[i]);
				if(StringUtils.isNotEmpty(msg)){
					return R.fail(msg);
				}
				discoverIcon.setUrlIosBlack(iosBlackFiles[i]);

				msg = checkFileType(iosWhiteFiles[i]);
				if(StringUtils.isNotEmpty(msg)){
					return R.fail(msg);
				}
				discoverIcon.setUrlIosWhite(iosWhiteFiles[i]);

				msg = checkFileType(androidBlackFiles[i]);
				if(StringUtils.isNotEmpty(msg)){
					return R.fail(msg);
				}
				discoverIcon.setUrlAndroidBlack(androidBlackFiles[i]);

				msg = checkFileType(androidWhiteFiles[i]);
				if(StringUtils.isNotEmpty(msg)){
					return R.fail(msg);
				}
				discoverIcon.setUrlAndroidWhite(androidWhiteFiles[i]);

				discoverIconList.add(discoverIcon);
			}
			ConfigPageFunctionService.updateDiscoverIcon(discoverIconList);
		}catch (Exception e){
			return R.fail("操作失败：" +e.getMessage());
		}
		return R.success();
	}

	public String checkFileType(String filePath){
		String photoType = "jpg,png,gif,jpeg";
		String msg = "";
		String ext = filePath.substring(filePath.lastIndexOf(".") + 1);
		if (!photoType.contains(ext.toLowerCase())) {
			msg = "请上传jpg,png,gif,jpeg格式的图片";
		}
		return msg;
	}
}
