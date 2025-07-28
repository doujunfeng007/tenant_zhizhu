package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.account.entity.AccountBankVerityInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountBankVerityInfoVO;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface AccountBankVerityInfoMapper extends BaseMapper<AccountBankVerityInfoEntity> {

	AccountBankVerityInfoVO queryByApplicationId(@Param("applicationId") String applicationId);

	void deleteByApplicationId(@Param("applicationId") String applicationId);

	AccountBankVerityInfoVO queryByBankFourInfo(@Param("bankCard") String bankCard, @Param("idNo") String idNo,
												@Param("name") String name, @Param("bankMobileArea") String bankMobileArea,
												@Param("bankMobile") String bankMobile);

	AccountBankVerityInfoVO queryByIdNo(@Param("idNo") String idNo, @Param("custId") Long custId);

	AccountBankVerityInfoVO queryByBankCard(@Param("bankCard") String bankCard, @Param("custId") Long custId);

	AccountBankVerityInfoVO queryByPhoneNumber(@Param("bankMobile") String bankMobile, @Param("custId") Long custId);
}
