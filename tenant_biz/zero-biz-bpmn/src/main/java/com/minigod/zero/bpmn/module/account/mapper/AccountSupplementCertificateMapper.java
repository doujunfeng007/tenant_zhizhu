package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.account.entity.AccountSupplementCertificateEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountSupplementCertificateVO;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface AccountSupplementCertificateMapper extends BaseMapper<AccountSupplementCertificateEntity> {
	AccountSupplementCertificateVO queryByApplicationId(String applicationId);

	AccountSupplementCertificateVO queryBySupIdCardNumber(String supIdCardNumber, Long userId);

	void deleteByApplicationId(String applicationId);
}
