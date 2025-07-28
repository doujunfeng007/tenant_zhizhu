package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.entity.OrganizationOpenShareholderInfoEntity;

import java.util.List;

/**
 * 机构股东信息
 *
 * @author eric
 * @since 2024-08-19 13:52:04
 */
public interface IOrganizationOpenShareholderInfoService {
	/**
	 * 批量保存机构股东信息
	 *
	 * @param records
	 * @return
	 */
	boolean batchSave(List<OrganizationOpenShareholderInfoEntity> records);
}
