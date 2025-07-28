package com.minigod.zero.bpmn.module.account.service.impl;

import com.minigod.zero.bpmn.module.account.entity.OrganizationOpenShareholderInfoEntity;
import com.minigod.zero.bpmn.module.account.mapper.OrganizationOpenShareholderInfoMapper;
import com.minigod.zero.bpmn.module.account.service.IOrganizationOpenShareholderInfoService;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机构股东信息服务实现类
 *
 * @author eric
 * @since 2024-08-19 13:53:04
 */
@Slf4j
@Service
public class OrganizationOpenShareholderInfoServiceImpl extends BaseServiceImpl<OrganizationOpenShareholderInfoMapper, OrganizationOpenShareholderInfoEntity> implements IOrganizationOpenShareholderInfoService {
	/**
	 * 批量保存
	 *
	 * @param records
	 * @return
	 */
	@Override
	public boolean batchSave(List<OrganizationOpenShareholderInfoEntity> records) {
		return this.saveBatch(records, 10);
	}
}
