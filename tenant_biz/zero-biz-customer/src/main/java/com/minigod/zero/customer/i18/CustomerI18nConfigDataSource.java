package com.minigod.zero.customer.i18;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.i18n.source.II18nConfigDataSource;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.system.entity.I18nConfigEntity;
import com.minigod.zero.system.feign.IDictBizClient;
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
public class CustomerI18nConfigDataSource implements II18nConfigDataSource {

	@Autowired
	private IDictBizClient dictBizClient;

	@Override
	public Map<String, Map<String, String>> selectI18nConfigByModule(String moduleName) {
		R<List<I18nConfigEntity>> result = dictBizClient.getLanguageByModelName(moduleName);
		if (!result.isSuccess()){
			log.error("国际化语言配置获取失败！");
			return new HashMap<>();
		}
		List<I18nConfigEntity> list = result.getData();
		Map<String, Map<String, String>> configMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (I18nConfigEntity entity : list) {
				String language = entity.getLanguageType();
				Map<String, String> valueMap = configMap.get(language)== null ? new HashMap<>(): configMap.get(language);
				String key = entity.getConfigKey();
				String value = entity.getContent();
				valueMap.put(key, value);
				configMap.put(language,valueMap);
			}
		}
		log.info("国际化语言初始化：{}", JSONObject.toJSONString(configMap));
		return configMap;
	}
}
