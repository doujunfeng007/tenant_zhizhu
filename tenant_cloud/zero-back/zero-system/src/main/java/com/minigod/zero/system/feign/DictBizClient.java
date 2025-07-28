
package com.minigod.zero.system.feign;


import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.entity.I18nConfigEntity;
import com.minigod.zero.system.entity.I18nLanguageEntity;
import com.minigod.zero.system.service.IDictBizService;
import com.minigod.zero.system.service.II18nConfigService;
import lombok.AllArgsConstructor;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * 字典服务Feign实现类
 *
 * @author Chill
 */
@NonDS
@ApiIgnore
@RestController
@AllArgsConstructor
public class DictBizClient implements IDictBizClient {

	private final IDictBizService service;
	private final II18nConfigService ii18nConfigService;

	@Override
	@GetMapping(GET_BY_ID)
	public R<DictBiz> getById(Long id) {
		return R.data(service.getById(id));
	}

	@Override
	@GetMapping(GET_VALUE)
	public R<String> getValue(String code, String dictKey) {
		return R.data(service.getValue(code, dictKey));
	}

	@Override
	@GetMapping(GET_VALUE_LANG)
	public R<String> getValue(String code, String dictKey, String lang) {
		return R.data(service.getValue(code, dictKey,lang));
	}

	@Override
	@GetMapping(GET_LABEL)
	public R<String> getLabel(String code, String dictLabel) {
		return R.data(service.getLabel(code,dictLabel));
	}

	@Override
	@GetMapping(GET_LIST)
	public R<List<DictBiz>> getList(String code) {
		return R.data(service.getList(code));
	}

	@Override
	public R<List<DictBiz>> getAll() {
		return R.data(service.getAll());
	}

	@Override
	public R<List<DictBiz>> getListByTenantId(String tenantId, String code) {
		return R.data(service.getListByTenantId(tenantId,code));
	}

	@Override
	public R<List<I18nConfigEntity>> getLanguageByModelName(String modelName) {
		return R.data(ii18nConfigService.selectI18nConfigByModule(modelName));
	}

}
