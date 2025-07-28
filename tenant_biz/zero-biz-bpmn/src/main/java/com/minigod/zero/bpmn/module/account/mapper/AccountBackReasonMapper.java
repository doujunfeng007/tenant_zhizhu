package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.account.entity.AccountBackReasonEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountBackReasonVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Chill
 */
public interface AccountBackReasonMapper extends BaseMapper<AccountBackReasonEntity> {

    List<AccountBackReasonVO> selectByApplicationId(@Param("applicationId") String applicationId,@Param("tenantId") String tenantId);

     AccountBackReasonVO selectByApplicationIdAndCode(@Param("applicationId") String applicationId, @Param("code") String code,@Param("tenantId") String tenantId);

    List<String> selectCodeListByApplicationId(String applicationId,@Param("tenantId") String tenantId);

    int deleteWithCode(@Param("applicationId") String applicationId, @Param("code") String code,@Param("tenantId") String tenantId);

    void deleteByApplicationId(String applicationId,@Param("tenantId") String tenantId);
}
