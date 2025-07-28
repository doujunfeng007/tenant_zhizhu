package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.account.entity.AccountPropertyTypeEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountPropertyTypeVO;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Chill
 */
public interface AccountPropertyTypeMapper extends BaseMapper<AccountPropertyTypeEntity> {

    List<AccountPropertyTypeVO> queryListByApplicationId(String applicationId);
}
