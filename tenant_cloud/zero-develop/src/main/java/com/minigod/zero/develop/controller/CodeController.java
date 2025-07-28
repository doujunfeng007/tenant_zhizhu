
package com.minigod.zero.develop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.develop.entity.Code;
import com.minigod.zero.develop.entity.ModelPrototype;
import com.minigod.zero.develop.service.ICodeService;
import com.minigod.zero.develop.service.IModelService;
import com.minigod.zero.develop.support.ZeroCodeGenerator;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.annotation.PreAuth;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.constant.RoleConstant;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.develop.entity.Datasource;
import com.minigod.zero.develop.entity.Model;
import com.minigod.zero.develop.service.IDatasourceService;
import com.minigod.zero.develop.service.IModelPrototypeService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 */
@NonDS
@RestController
@AllArgsConstructor
@RequestMapping("/code")
@Api(value = "代码生成", tags = "代码生成")
@PreAuth(RoleConstant.HAS_ROLE_ADMINISTRATOR)
public class CodeController extends ZeroController {

	private final ICodeService codeService;
	private final IDatasourceService datasourceService;
	private final IModelService modelService;
	private final IModelPrototypeService modelPrototypeService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入code")
	public R<Code> detail(Code code) {
		Code detail = codeService.getOne(Condition.getQueryWrapper(code));
		return R.data(detail);
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "codeName", value = "模块名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "tableName", value = "表名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "modelName", value = "实体名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入code")
	public R<IPage<Code>> list(@ApiIgnore @RequestParam Map<String, Object> code, Query query) {
		IPage<Code> pages = codeService.page(Condition.getPage(query), Condition.getQueryWrapper(code, Code.class));
		return R.data(pages);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增或修改", notes = "传入code")
	public R submit(@Valid @RequestBody Code code) {
		return R.status(codeService.submit(code));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(codeService.removeByIds(Func.toLongList(ids)));
	}

	/**
	 * 复制
	 */
	@PostMapping("/copy")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "复制", notes = "传入id")
	public R copy(@ApiParam(value = "主键", required = true) @RequestParam Long id) {
		Code code = codeService.getById(id);
		code.setId(null);
		code.setCodeName(code.getCodeName() + "-copy");
		return R.status(codeService.save(code));
	}

	/**
	 * 代码生成
	 */
	@PostMapping("/gen-code")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "代码生成", notes = "传入ids")
	public R genCode(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		Collection<Code> codes = codeService.listByIds(Func.toLongList(ids));
		codes.forEach(code -> {
			ZeroCodeGenerator generator = new ZeroCodeGenerator();
			// 设置基础模型
			Model model = modelService.getById(code.getModelId());
			generator.setModelCode(model.getModelCode());
			generator.setModelClass(model.getModelClass());
			// 设置模型集合
			List<ModelPrototype> prototypes = modelPrototypeService.prototypeList(model.getId());
			generator.setModel(JsonUtil.readMap(JsonUtil.toJson(model)));
			generator.setPrototypes(JsonUtil.readListMap(JsonUtil.toJson(prototypes)));
			if (StringUtil.isNotBlank(code.getSubModelId())) {
				Model subModel = modelService.getById(Func.toLong(code.getSubModelId()));
				List<ModelPrototype> subPrototypes = modelPrototypeService.prototypeList(subModel.getId());
				generator.setSubModel(JsonUtil.readMap(JsonUtil.toJson(subModel)));
				generator.setSubPrototypes(JsonUtil.readListMap(JsonUtil.toJson(subPrototypes)));
			}
			// 设置数据源
			Datasource datasource = datasourceService.getById(model.getDatasourceId());
			generator.setDriverName(datasource.getDriverClass());
			generator.setUrl(datasource.getUrl());
			generator.setUsername(datasource.getUsername());
			generator.setPassword(datasource.getPassword());
			// 设置基础配置
			generator.setCodeStyle(code.getCodeStyle());
			generator.setCodeName(code.getCodeName());
			generator.setServiceName(code.getServiceName());
			generator.setPackageName(code.getPackageName());
			generator.setPackageDir(code.getApiPath());
			generator.setPackageWebDir(code.getWebPath());
			generator.setTablePrefix(Func.toStrArray(code.getTablePrefix()));
			generator.setIncludeTables(Func.toStrArray(code.getTableName()));
			// 设置模版信息
			generator.setTemplateType(code.getTemplateType());
			generator.setAuthor(code.getAuthor());
			generator.setSubModelId(code.getSubModelId());
			generator.setSubFkId(code.getSubFkId());
			generator.setTreeId(code.getTreeId());
			generator.setTreePid(code.getTreePid());
			generator.setTreeName(code.getTreeName());
			// 设置是否继承基础业务字段
			generator.setHasSuperEntity(code.getBaseMode() == 1);
			// 设置是否开启包装器模式
			generator.setHasWrapper(code.getWrapMode() == 1);
			// 设置是否开启远程调用模式
			generator.setHasFeign(code.getFeignMode() == 1);
			// 设置控制器服务名前缀
			generator.setHasServiceName(Boolean.TRUE);
			// 启动代码生成
			try {
				generator.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return R.success("代码生成成功");
	}

}
