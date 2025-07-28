package com.minigod.zero.bpmn.i18n;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.system.entity.I18nConfigEntity;
import com.minigod.zero.system.feign.IDictBizClient;
import com.minigod.zero.core.i18n.source.II18nConfigDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化i18N多语言配置
 *
 * @author eric
 * @since 2024-09-23 19:29:14
 */
@Slf4j
@Component
public class AuthI18nConfigDataSource implements II18nConfigDataSource {

	@Autowired
	private IDictBizClient dictBizClient;

	@Override
	public Map<String, Map<String, String>> selectI18nConfigByModule(String moduleName) {
		log.info("获取国际化语言配置数据源数据,moduleName is {}！", moduleName);
		R<List<I18nConfigEntity>> result = dictBizClient.getLanguageByModelName(moduleName);
		if (!result.isSuccess()) {
			log.error("国际化语言配置获取失败！");
			return new HashMap<>();
		}
		List<I18nConfigEntity> list = result.getData();
		log.info("国际化语言配置获取成功,数据条数:{}条!", list == null ? 0 : list.size());
		Map<String, Map<String, String>> configMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (I18nConfigEntity entity : list) {
				String language = entity.getLanguageType();
				Map<String, String> valueMap = configMap.get(language) == null ? new HashMap<>() : configMap.get(language);
				String key = entity.getConfigKey();
				String value = entity.getContent();
				valueMap.put(key, value);
				configMap.put(language, valueMap);
			}
		}

		return configMap;
	}
}
