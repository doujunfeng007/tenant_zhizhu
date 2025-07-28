package com.minigod.zero.bpmn.module.account.api;

import lombok.Data;

import java.util.List;

/**
 * 投资知识问卷返回对象
 *
 * @author zxq
 * @date 2024/11/18
 **/
@Data
public class InvestQuestionnaires {

	private List<QuestionAnswer> questionAnswers;

	@Data
	public static class QuestionAnswer {
		/**
		 * 问题编号
		 */
		private String questionNum;

		/**
		 * 问题描述
		 */
		private String questionDesc;

		/**
		 * 答案编号
		 */
		private String answerNum;

		/**
		 * 答案描述
		 */
		private String answerDesc;
	}

}
