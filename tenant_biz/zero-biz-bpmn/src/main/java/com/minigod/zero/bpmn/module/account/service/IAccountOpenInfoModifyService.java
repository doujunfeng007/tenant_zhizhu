package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.entity.AccountOpenInfoModifyEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountAssetInvestmentInfoModifyVO;
import com.minigod.zero.bpmn.module.account.vo.AccountAssetInvestmentInfoVO;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoModifyVO;
import com.minigod.zero.core.mp.base.BaseService;

import java.util.List;

/**
 * 客户开户详细资料修改表 服务类
 *
 * @author eric
 * @since 2024-08-02 19:06:01
 */
public interface IAccountOpenInfoModifyService extends BaseService<AccountOpenInfoModifyEntity> {
	/**
	 * 持久化客户开户详细资料修改记录
	 *
	 * @param entity
	 * @return
	 */
	boolean insert(AccountOpenInfoModifyEntity entity);

	/**
	 * 将地址名称根据字典及编码转换
	 *
	 * @param modifyVO
	 * @return
	 */
	void transferAddressName(AccountOpenInfoModifyVO modifyVO);

	/**
	 * 将交易名称根据字典及编码转换
	 *
	 * @param modifyVO
	 * @return
	 */
	void transferTradeName(AccountAssetInvestmentInfoModifyVO modifyVO);

	/**
	 * 将交易名称根据字典及编码转换
	 *
	 * @param accountAssetInvestmentInfoVO
	 */
	void transferTradeName(AccountAssetInvestmentInfoVO accountAssetInvestmentInfoVO);

	/**
	 * 根据修改申请ID查询客户开户详细资料记录
	 *
	 * @param applyId
	 * @return
	 */
	AccountOpenInfoModifyVO selectByApplyId(Long applyId);

	/**
	 * 根据开户申请ID查询客户开户详细资料修改记录
	 *
	 * @param applicationId
	 * @return
	 */
	List<AccountOpenInfoModifyVO> selectByApplicationId(String applicationId);

	/**
	 * 根据用户ID查询客户开户详细资料修改记录
	 *
	 * @param userId
	 * @return
	 */
	List<AccountOpenInfoModifyVO> selectByUserId(Long userId);
}
