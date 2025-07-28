package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskQuestionnaireRatingEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 风险测评问卷及风险等级关联
 *
 * @author eric
 * @since 2024-09-02 09:28:10
 */
@Mapper
public interface AccountRiskQuestionnaireRatingMapper extends BaseMapper<AccountRiskQuestionnaireRatingEntity> {
}
