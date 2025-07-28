package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.account.entity.AccountAdditionalDetailEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountAdditionalDetailVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface AccountAdditionalDetailMapper extends BaseMapper<AccountAdditionalDetailEntity> {

    List<AccountAdditionalDetailVO> queryListByApplicationId(@Param("applicationId") String applicationId);
}
