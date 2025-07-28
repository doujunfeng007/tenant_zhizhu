package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskQuestionnaireQuestionEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionnaireQuestionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 风险测评问卷及题目关联持久层
 *
 * @author eric
 * @since 2024-09-02 09:27:55
 */
@Mapper
public interface AccountRiskQuestionnaireQuestionMapper extends BaseMapper<AccountRiskQuestionnaireQuestionEntity> {
	/**
	 * 根据问卷ID查询指定语言题目列表
	 *
	 * @param questionnaireId
	 * @param lang
	 * @return
	 */
	List<AccountRiskQuestionnaireQuestionVO> getByQuestionnaireId(@Param("questionnaireId") Long questionnaireId, @Param("lang") String lang);
}
