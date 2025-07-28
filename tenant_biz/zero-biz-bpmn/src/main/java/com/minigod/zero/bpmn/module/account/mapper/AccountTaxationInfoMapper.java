package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.account.entity.AccountTaxationInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountTaxationInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Chill
 */
public interface AccountTaxationInfoMapper extends BaseMapper<AccountTaxationInfoEntity> {

    List<AccountTaxationInfoVO> queryListByApplicationId(@Param("applicationId") String applicationId);

    void deleteByApplicationId(String applicationId);
}
