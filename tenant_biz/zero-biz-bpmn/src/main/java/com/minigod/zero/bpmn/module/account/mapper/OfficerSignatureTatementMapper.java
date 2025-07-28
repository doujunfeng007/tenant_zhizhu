package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.account.entity.OfficerSignatureTatementEntity;
import com.minigod.zero.bpmn.module.account.vo.OfficerSignatureTatementVO;

/**
 *  Mapper 接口
 *
 * @author Chill
 */
public interface OfficerSignatureTatementMapper extends BaseMapper<OfficerSignatureTatementEntity> {
    OfficerSignatureTatementVO queryByApplicationId(String applicationId);

}
