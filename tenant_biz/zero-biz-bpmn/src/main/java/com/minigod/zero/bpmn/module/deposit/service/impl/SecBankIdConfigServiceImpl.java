package com.minigod.zero.bpmn.module.deposit.service.impl;

import com.minigod.zero.bpmn.module.deposit.entity.SecBankIdConfigEntity;
import com.minigod.zero.bpmn.module.deposit.vo.SecBankIdConfigVO;
import com.minigod.zero.bpmn.module.deposit.mapper.SecBankIdConfigMapper;
import com.minigod.zero.bpmn.module.deposit.service.ISecBankIdConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 银行bankId配置表 服务实现类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
@Service
public class SecBankIdConfigServiceImpl extends ServiceImpl<SecBankIdConfigMapper, SecBankIdConfigEntity> implements ISecBankIdConfigService {

	@Override
	public IPage<SecBankIdConfigVO> selectSecBankIdConfigPage(IPage<SecBankIdConfigVO> page, SecBankIdConfigVO secBankIdConfigVO) {
		return page.setRecords(baseMapper.selectSecBankIdConfigPage(page, secBankIdConfigVO));
	}


}
