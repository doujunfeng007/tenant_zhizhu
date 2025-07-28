package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskQuestionEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionAndOptionCountVO;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionVO;
import com.minigod.zero.bpmn.module.account.vo.FundRiskQuestionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 风险评测题库接口
 *
 * @author eric
 * @since 2024-08-20 13:47:05
 */
public interface AccountRiskQuestionMapper extends BaseMapper<AccountRiskQuestionEntity> {
	/**
	 * 风险评测题库分页列表
	 *
	 * @param page
	 * @param acctRiskQuestion
	 * @return
	 */
	List<AccountRiskQuestionVO> selectAcctRiskQuestionPage(IPage page, @Param("acctRiskQuestion") AccountRiskQuestionDTO acctRiskQuestion);

	/**
	 * 风险评测题库绑定问卷题目选择列表
	 *
	 * @param page
	 * @param acctRiskQuestion
	 * @return
	 */
	List<AccountRiskQuestionAndOptionCountVO> selectAcctRiskQuestionAndOptionCountPage(IPage page, @Param("acctRiskQuestion") AccountRiskQuestionDTO acctRiskQuestion);

	/**
	 * 根据问题类型ID查询风险评测题库
	 *
	 * @param acctRiskQuestion
	 * @return
	 */
	List<FundRiskQuestionVO> findQuestion(@Param("acctRiskQuestion") AccountRiskQuestionDTO acctRiskQuestion);
}
