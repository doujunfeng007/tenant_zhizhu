package com.minigod.zero.bpmn.module.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionAddDTO;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionDTO;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionOptionDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskQuestionEntity;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskQuestionOptionEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountRiskQuestionMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountRiskQuestionOptionService;
import com.minigod.zero.bpmn.module.account.service.IAccountRiskQuestionService;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionAndOptionCountVO;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionAndOptionVO;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionVO;
import com.minigod.zero.bpmn.module.account.vo.FundRiskQuestionVO;
import com.minigod.zero.bpmn.module.constant.RiskMessageConstant;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.WebUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 风险测评题库服务
 *
 * @author eric
 * @since 2024-08-20 14:53:05
 */
@Slf4j
@Service
@AllArgsConstructor
public class AccountRiskQuestionServiceImpl extends BaseServiceImpl<AccountRiskQuestionMapper, AccountRiskQuestionEntity> implements IAccountRiskQuestionService {
	private final IAccountRiskQuestionOptionService acctRiskQuestionOptionService;
	private final ZeroRedis zeroRedis;

	/**
	 * 风险测评题库自定查询分页
	 *
	 * @param page
	 * @param acctRiskQuestion
	 * @return
	 */
	@Override
	public IPage<AccountRiskQuestionVO> selectAcctRiskQuestionPage(IPage<AccountRiskQuestionVO> page, AccountRiskQuestionDTO acctRiskQuestion) {
		return page.setRecords(baseMapper.selectAcctRiskQuestionPage(page, acctRiskQuestion));
	}

	/**
	 * 风险测评题库绑定问卷题目选择列表分页
	 *
	 * @param page
	 * @param acctRiskQuestion
	 * @return
	 */
	@Override
	public IPage<AccountRiskQuestionAndOptionCountVO> selectAcctRiskQuestionAndOptionPage(IPage<AccountRiskQuestionAndOptionCountVO> page, AccountRiskQuestionDTO acctRiskQuestion) {
		return page.setRecords(baseMapper.selectAcctRiskQuestionAndOptionCountPage(page, acctRiskQuestion));
	}

	/**
	 * 获取基金风险评测题库
	 *
	 * @param acctRiskQuestion
	 * @return
	 */
	@Override
	public R<List<AccountRiskQuestionAndOptionVO>> riskQuestion(AccountRiskQuestionDTO acctRiskQuestion) {
		if (null == acctRiskQuestion.getQuestionType()) {
			return R.fail(ResultCode.PARAM_VALID_ERROR, I18nUtil.getMessage(RiskMessageConstant.RISK_QUESTION_TYPE_NOT_BE_NULL_NOTICE));
		}

		String lang = WebUtil.getHeader(CommonConstant.LANG);
		if (StringUtils.isBlank(lang))
			lang = CommonConstant.ZH_CN;

		String key = "ifund_risk_question_" + acctRiskQuestion.getQuestionType() + "_" + lang;
		List<AccountRiskQuestionAndOptionVO> questions = null;
		String questionCache = zeroRedis.protoGet(key, String.class);
		if (StringUtils.isNotBlank(questionCache)) {
			JSONArray array = JSONUtil.parseArray(questionCache);
			questions = JSONUtil.toList(array, AccountRiskQuestionAndOptionVO.class);
		}
		if (null != questions && questions.size() > 0) {
			return R.data(questions);
		}

		LambdaQueryWrapper<AccountRiskQuestionEntity> riskQuestionWrapper = new LambdaQueryWrapper<>();
		riskQuestionWrapper.eq(AccountRiskQuestionEntity::getQuestionType, acctRiskQuestion.getQuestionType());
		riskQuestionWrapper.eq(AccountRiskQuestionEntity::getLang, lang);
		riskQuestionWrapper.eq(AccountRiskQuestionEntity::getFlag, 0);
		riskQuestionWrapper.orderByAsc(AccountRiskQuestionEntity::getQuestionId);
		List<AccountRiskQuestionEntity> questionsEntityList = this.list(riskQuestionWrapper);

		if (CollectionUtil.isNotEmpty(questionsEntityList)) {
			questions = BeanUtil.copyToList(questionsEntityList, AccountRiskQuestionAndOptionVO.class);
			for (AccountRiskQuestionAndOptionVO question : questions) {
				LambdaQueryWrapper<AccountRiskQuestionOptionEntity> riskOptionWrapper = new LambdaQueryWrapper<>();
				riskOptionWrapper.eq(AccountRiskQuestionOptionEntity::getQuestionId, question.getQuestionId());
				riskOptionWrapper.eq(AccountRiskQuestionOptionEntity::getLang, lang);
				riskOptionWrapper.eq(AccountRiskQuestionOptionEntity::getFlag, 0);
				riskOptionWrapper.orderByAsc(AccountRiskQuestionOptionEntity::getOptionId);
				List<AccountRiskQuestionOptionEntity> optionEntityList = acctRiskQuestionOptionService.list(riskOptionWrapper);

				if (CollectionUtil.isNotEmpty(optionEntityList)) {
					List<AccountRiskQuestionAndOptionVO.OptionVO> options = BeanUtil.copyToList(optionEntityList, AccountRiskQuestionAndOptionVO.OptionVO.class);
					question.setOptions(options);
				}
			}
		}

		if (CollectionUtil.isNotEmpty(questions)) {
			zeroRedis.protoSet(key, JSONUtil.toJsonStr(questions));
		}
		return R.data(questions);
	}

	/**
	 * 获取问题列表
	 *
	 * @param acctRiskQuestion
	 * @return
	 */
	@Override
	public R<List<FundRiskQuestionVO>> findQuestion(AccountRiskQuestionDTO acctRiskQuestion) {
		if (null == acctRiskQuestion.getQuestionType())
			return R.fail(ResultCode.PARAM_VALID_ERROR);
		String lang = WebUtil.getHeader(CommonConstant.LANG);
		if (StringUtils.isBlank(lang))
			lang = CommonConstant.ZH_CN;
		acctRiskQuestion.setLang(lang);
		return R.data(baseMapper.findQuestion(acctRiskQuestion));
	}

	/**
	 * 添加问题
	 *
	 * @param acctRiskQuestion
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R addQuestion(AccountRiskQuestionAddDTO acctRiskQuestion) {
		if (null == acctRiskQuestion.getAcctRiskQuestion()) {
			return R.fail(ResultCode.PARAM_VALID_ERROR, "问题不能为空!");
		}
		if (null == acctRiskQuestion.getAcctRiskQuestionOption() || acctRiskQuestion.getAcctRiskQuestionOption().size() < 1) {
			return R.fail(ResultCode.PARAM_VALID_ERROR, "问题选项不能为空");
		}
		try {
			Long questionId = IdUtil.getSnowflakeNextId();
			AccountRiskQuestionEntity acctRiskQuestionEntity = new AccountRiskQuestionEntity();
			acctRiskQuestionEntity.setQuestionId(questionId);
			acctRiskQuestionEntity.setQuestionType(acctRiskQuestion.getAcctRiskQuestion().getQuestionType());
			acctRiskQuestionEntity.setQuestion(acctRiskQuestion.getAcctRiskQuestion().getQuestion());
			acctRiskQuestionEntity.setLang(acctRiskQuestion.getAcctRiskQuestion().getLang());
			acctRiskQuestionEntity.setSort(acctRiskQuestion.getAcctRiskQuestion().getSort());
			acctRiskQuestionEntity.setCheckType(acctRiskQuestion.getAcctRiskQuestion().getCheckType());
			acctRiskQuestionEntity.setFlag(acctRiskQuestion.getAcctRiskQuestion().getFlag());
			acctRiskQuestionEntity.setOptType(acctRiskQuestion.getAcctRiskQuestion().getOptType());

			boolean isSuccess = this.save(acctRiskQuestionEntity);
			if (isSuccess) {
				List<AccountRiskQuestionOptionDTO> acctRiskQuestionOptions = acctRiskQuestion.getAcctRiskQuestionOption();
				acctRiskQuestionOptions.forEach(acctRiskQuestionOption -> acctRiskQuestionOption.setQuestionId(questionId));
				R result = acctRiskQuestionOptionService.batchAddQuestionOption(acctRiskQuestionOptions);
				if (!result.isSuccess()) {
					throw new ServiceException("保存风险测评问题失败,原因:" + result.getMsg());
				}
			} else {
				throw new ServiceException("保存风险测评问题失败!");
			}
		} catch (Exception exception) {
			throw new ServiceException("保存风险测评问题失败,原因:" + exception.getMessage());
		}
		return R.success();
	}

	/**
	 * 批量添加问题
	 *
	 * @param acctRiskQuestions
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R batchAddQuestion(List<AccountRiskQuestionAddDTO> acctRiskQuestions) {
		if (null == acctRiskQuestions || acctRiskQuestions.size() < 1) {
			return R.fail(ResultCode.PARAM_VALID_ERROR, "问题列表不能为空!");
		}
		try {
			for (AccountRiskQuestionAddDTO acctRiskQuestion : acctRiskQuestions) {
				if (null == acctRiskQuestion.getAcctRiskQuestion()) {
					return R.fail(ResultCode.PARAM_VALID_ERROR, "问题不能为空!");
				}
				if (null == acctRiskQuestion.getAcctRiskQuestionOption() || acctRiskQuestion.getAcctRiskQuestionOption().size() < 1) {
					return R.fail(ResultCode.PARAM_VALID_ERROR, "问题选项不能为空");
				}
				Long questionId = IdUtil.getSnowflakeNextId();
				AccountRiskQuestionEntity acctRiskQuestionEntity = new AccountRiskQuestionEntity();
				acctRiskQuestionEntity.setQuestionId(questionId);
				acctRiskQuestionEntity.setQuestionType(acctRiskQuestion.getAcctRiskQuestion().getQuestionType());
				acctRiskQuestionEntity.setQuestion(acctRiskQuestion.getAcctRiskQuestion().getQuestion());
				acctRiskQuestionEntity.setLang(acctRiskQuestion.getAcctRiskQuestion().getLang());
				acctRiskQuestionEntity.setSort(acctRiskQuestion.getAcctRiskQuestion().getSort());
				acctRiskQuestionEntity.setCheckType(acctRiskQuestion.getAcctRiskQuestion().getCheckType());
				acctRiskQuestionEntity.setFlag(acctRiskQuestion.getAcctRiskQuestion().getFlag());
				acctRiskQuestionEntity.setOptType(acctRiskQuestion.getAcctRiskQuestion().getOptType());

				boolean isSuccess = this.save(acctRiskQuestionEntity);
				if (isSuccess) {
					List<AccountRiskQuestionOptionDTO> acctRiskQuestionOptions = acctRiskQuestion.getAcctRiskQuestionOption();
					// 问题ID关联到选项
					acctRiskQuestionOptions.forEach(acctRiskQuestionOption -> acctRiskQuestionOption.setQuestionId(questionId));
					R result = acctRiskQuestionOptionService.batchAddQuestionOption(acctRiskQuestionOptions);
					if (!result.isSuccess()) {
						throw new ServiceException("保存风险测评问题失败,原因:" + result.getMsg());
					}
				} else {
					throw new ServiceException("保存风险测评问题失败!");
				}
			}
		} catch (Exception exception) {
			throw new ServiceException("保存风险测评问题失败,原因:" + exception.getMessage());
		}
		return R.success();
	}

	/**
	 * 根据主键IDs删除问题
	 *
	 * @param ids
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R deleteQuestion(List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return R.fail(ResultCode.PARAM_VALID_ERROR, "问题ID不能为空!");
		}
		log.info("删除问题，问题主键Id列表: {}", ids);
		try {
			List<AccountRiskQuestionEntity> acctRiskQuestions = baseMapper.selectBatchIds(ids);
			if (acctRiskQuestions.isEmpty()) {
				return R.fail(ResultCode.PARAM_VALID_ERROR, "问题不存在!");
			}

			List<Long> questionIds = acctRiskQuestions.stream()
				.map(AccountRiskQuestionEntity::getQuestionId)
				.collect(Collectors.toList());

			log.info("删除问题，问题QuestionId列表: {}", questionIds);
			boolean isSuccess = removeBatchByIds(ids);
			if (!isSuccess) {
				log.error("删除问题失败，问题ID列表: {}", ids);
				throw new ServiceException("删除风险测评问题失败!");
			}

			// 只有当问题删除成功时才尝试删除相关选项
			boolean isDeleted = acctRiskQuestionOptionService.remove(Wrappers.<AccountRiskQuestionOptionEntity>lambdaQuery().in(AccountRiskQuestionOptionEntity::getQuestionId, questionIds));
			if (!isDeleted) {
				log.error("删除问题选项失败，问题ID列表: {}", questionIds);
				throw new ServiceException("删除风险测评问题选项失败!");
			}
			boolean result = zeroRedis.delAll(String.class);
			if (result) {
				log.info("删除风险测评问题缓存成功!");
			} else {
				log.info("删除风险测评问题缓存失败!");
			}
			return R.success();
		} catch (ServiceException e) {
			// 直接抛出服务异常
			throw e;
		} catch (Exception exception) {
			log.error("删除风险测评问题失败, 原因: {}", exception.getMessage(), exception);
			throw new ServiceException("删除风险测评问题失败, 原因: " + exception.getMessage());
		}
	}
}
