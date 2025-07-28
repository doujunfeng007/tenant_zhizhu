package com.minigod.zero.bpmn.module.deposit.service;

import com.minigod.zero.bpmn.module.deposit.bo.DepositBankInfoBo;
import com.minigod.zero.bpmn.module.deposit.entity.SecDepositBankEntity;
import com.minigod.zero.bpmn.module.deposit.vo.DepositBankRespVO;
import com.minigod.zero.bpmn.module.deposit.vo.SecDepositBankVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.deposit.vo.SecDepositTypeVO;

import java.util.List;

/**
 * 入金银行配置表 服务类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
public interface ISecDepositBankService extends IService<SecDepositBankEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param secDepositBankVO
	 * @return
	 */
	IPage<SecDepositBankVO> selectSecDepositBankPage(IPage<SecDepositBankVO> page, SecDepositBankVO secDepositBankVO);

	/**
	 * 获取入金银行配置
	 * @param params
	 * @return
	 */
    List<DepositBankRespVO> findDepositBankSetting(DepositBankInfoBo params);

	/**
	 * 获取银行配置类型
	 * @param params
	 * @return
	 */
	List<SecDepositTypeVO> findDepositTypeSetting(DepositBankInfoBo params);
}
