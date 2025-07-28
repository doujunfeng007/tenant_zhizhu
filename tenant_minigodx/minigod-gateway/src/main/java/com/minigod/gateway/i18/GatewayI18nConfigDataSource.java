package com.minigod.gateway.i18;

import com.minigod.zero.core.i18n.source.II18nConfigDataSource;
import com.minigod.zero.core.tool.api.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/12 15:42
 * @description：
 */
@Slf4j
@Component
public class GatewayI18nConfigDataSource implements II18nConfigDataSource {

	@Autowired
	private IDictBizClient dictBizClient;

	@Override
	public Map<String, Map<String, String>> selectI18nConfigByModule(String moduleName) {
		R<List<I18nConfig>> result = dictBizClient.getLanguageByModelName(moduleName);
		if (!result.isSuccess()){
			log.error("国际化语言配置获取失败！");
			return new HashMap<>();
		}
		List<I18nConfig> list = result.getData();
		Map<String, Map<String, String>> configMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (I18nConfig entity : list) {
				String language = entity.getLanguageType();
				Map<String, String> valueMap = configMap.get(language)== null ? new HashMap<>(): configMap.get(language);
				String key = entity.getConfigKey();
				String value = entity.getContent();
				valueMap.put(key, value);
				configMap.put(language,valueMap);
			}
		}
		return configMap;
	}
}
