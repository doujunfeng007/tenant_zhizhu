
package com.minigod.zero.system.feign;

import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.system.entity.I18nConfigEntity;
import com.minigod.zero.system.entity.I18nLanguageEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign失败配置
 *
 * @author Chill
 */
@Component
public class IDictBizClientFallback implements IDictBizClient {
	@Override
	public R<DictBiz> getById(Long id) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getValue(String code, String dictKey) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getValue(String code, String dictKey, String lang) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<String> getLabel(String code, String dictLabel) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<DictBiz>> getList(String code) {
		return R.fail("获取数据失败");
	}
	@Override
	public R<List<DictBiz>> getAll() {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<DictBiz>> getListByTenantId(String tenantId, String code) {
		return R.fail("获取数据失败");
	}

	@Override
	public R<List<I18nConfigEntity>> getLanguageByModelName(String modelName) {
		return R.fail("获取数据失败");
	}

}
