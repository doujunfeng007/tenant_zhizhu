package com.minigod.zero.bpmn.module.account.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.bpmn.module.account.dto.AccountOpenInfoModifyApplyPageDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenInfoModifyApplyEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoModifyApplyVO;
import com.minigod.zero.core.mp.base.BaseService;

import java.util.List;

/**
 * 客户开户资料修改申请记录表服务类
 *
 * @author eric
 * @since 2024-08-05 11:22:21
 */
public interface IAccountOpenInfoModifyApplyService extends BaseService<AccountOpenInfoModifyApplyEntity> {
	/**
	 * 持久化客户开户资料修改申请记录
	 *
	 * @param entity
	 * @return
	 */
	boolean insert(AccountOpenInfoModifyApplyEntity entity);

	/**
	 * 根据修改申请ID查询客户开户资料修改申请记录
	 *
	 * @param applyId
	 * @return
	 */
	AccountOpenInfoModifyApplyVO selectByApplyId(Long applyId);

	/**
	 * 根据开户申请ID查询客户开户资料修改申请记录
	 *
	 * @param applicationId
	 * @return
	 */
	List<AccountOpenInfoModifyApplyVO> selectByApplicationId(String applicationId);

	/**
	 * 根据用户ID查询客户开户资料修改申请记录
	 *
	 * @param applyPageDTO
	 * @return
	 */
	Page<AccountOpenInfoModifyApplyVO> pageByUserId(AccountOpenInfoModifyApplyPageDTO applyPageDTO);

	/**
	 * 根据userId、状态查询开户资料修改申请记录
	 *
	 * @param userId
	 * @return
	 */
	AccountOpenInfoModifyApplyEntity getInfoByUserIdStatus(Long userId);

	/**
	 * 根据userId查询客户开户资料修改申请记录
	 *
	 * @param userId
	 * @return
	 */
	AccountOpenInfoModifyApplyVO getApproveInfoByUserId(Long userId);
}
