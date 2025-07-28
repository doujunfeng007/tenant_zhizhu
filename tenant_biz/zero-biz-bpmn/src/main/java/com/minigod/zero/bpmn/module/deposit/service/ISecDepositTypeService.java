package com.minigod.zero.bpmn.module.deposit.service;

import com.minigod.zero.bpmn.module.deposit.entity.SecDepositTypeEntity;
import com.minigod.zero.bpmn.module.deposit.vo.SecDepositTypeVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 入金方式管理配置表 服务类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
public interface ISecDepositTypeService extends IService<SecDepositTypeEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param secDepositTypeVO
	 * @return
	 */
	IPage<SecDepositTypeVO> selectSecDepositTypePage(IPage<SecDepositTypeVO> page, SecDepositTypeVO secDepositTypeVO);


}
