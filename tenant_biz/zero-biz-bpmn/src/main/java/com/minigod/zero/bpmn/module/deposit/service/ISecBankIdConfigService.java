package com.minigod.zero.bpmn.module.deposit.service;

import com.minigod.zero.bpmn.module.deposit.entity.SecBankIdConfigEntity;
import com.minigod.zero.bpmn.module.deposit.vo.SecBankIdConfigVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 银行bankId配置表 服务类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
public interface ISecBankIdConfigService extends IService<SecBankIdConfigEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param secBankIdConfigVO
	 * @return
	 */
	IPage<SecBankIdConfigVO> selectSecBankIdConfigPage(IPage<SecBankIdConfigVO> page, SecBankIdConfigVO secBankIdConfigVO);


}
