package com.minigod.zero.bpm.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.minigod.zero.bpm.entity.BpmSystemConfigEntity;
import com.minigod.zero.bpm.mapper.BpmSystemConfigMapper;
import com.minigod.zero.bpm.service.IBpmSystemConfigService;
import com.minigod.zero.bpm.vo.BpmSystemConfigVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 系统配置信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
public class BpmSystemConfigServiceImpl extends ServiceImpl<BpmSystemConfigMapper, BpmSystemConfigEntity> implements IBpmSystemConfigService {

	@Override
	public IPage<BpmSystemConfigVO> selectBpmSystemConfigPage(IPage<BpmSystemConfigVO> page, BpmSystemConfigVO bpmSystemConfig) {
		return page.setRecords(baseMapper.selectBpmSystemConfigPage(page, bpmSystemConfig));
	}

	@Override
	public String getSysConfigValue(String key, String defaultValue) {
		BpmSystemConfigEntity bpmSystemConfig = new LambdaQueryChainWrapper<>(baseMapper)
			.eq(BpmSystemConfigEntity::getKey, key)
			.one();
		if (bpmSystemConfig != null && StringUtils.isNotBlank(bpmSystemConfig.getValue())) {
			return bpmSystemConfig.getValue();
		}
		return defaultValue;
	}

}
