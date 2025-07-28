package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.account.entity.AccountOperateLogEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountOperateLogVO;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Chill
 */
public interface AccountOperateLogMapper extends BaseMapper<AccountOperateLogEntity> {

    List<AccountOperateLogVO> queryListByApplicationId(String applicationId);
}
