package com.minigod.zero.bpmn.module.account.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.bpmn.module.account.dto.AccountRiskQuestionOptionDTO;
import com.minigod.zero.bpmn.module.account.entity.AccountRiskQuestionOptionEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountRiskQuestionOptionMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountRiskQuestionOptionService;
import com.minigod.zero.bpmn.module.account.vo.AccountRiskQuestionOptionVO;
import com.minigod.zero.bpmn.module.account.vo.FundRiskQuestionOptionVO;
import com.minigod.zero.bpmn.module.constant.RiskMessageConstant;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.WebUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 风险测评题目选项服务实现类
 *
 * @author eric
 * @since 2024-08-20 14:40:05
 */
@Slf4j
@Service
@AllArgsConstructor
public class AccountRiskQuestionOptionServiceImpl extends BaseServiceImpl<AccountRiskQuestionOptionMapper, AccountRiskQuestionOptionEntity>
	implements IAccountRiskQuestionOptionService {
	private final ZeroRedis zeroRedis;

	/**
	 * 风险测评问题选项列表分页查询
	 *
	 * @param page
	 * @param acctRiskQuestionOption
	 * @return
	 */
	@Override
	public IPage<AccountRiskQuestionOptionVO> selectAcctRiskQuestionOptionPage(IPage<AccountRiskQuestionOptionVO> page, AccountRiskQuestionOptionDTO acctRiskQuestionOption) {
		List<AccountRiskQuestionOptionVO> acctRiskQuestionOptionVOS = new ArrayList<>();
		List<AccountRiskQuestionOptionEntity> acctRiskQuestionOptionEntities = this.baseMapper.selectAcctRiskQuestionOptionPage(page, acctRiskQuestionOption);
		for (AccountRiskQuestionOptionEntity acctRiskQuestionOptionEntity : acctRiskQuestionOptionEntities) {
			AccountRiskQuestionOptionVO acctRiskQuestionOptionVO = new AccountRiskQuestionOptionVO();
			acctRiskQuestionOptionVO.setId(acctRiskQuestionOptionEntity.getId());
			acctRiskQuestionOptionVO.setOptionId(acctRiskQuestionOptionEntity.getOptionId());
			acctRiskQuestionOptionVO.setOptionValue(acctRiskQuestionOptionEntity.getOptionValue());
			acctRiskQuestionOptionVO.setOptionScore(acctRiskQuestionOptionEntity.getOptionScore());
			acctRiskQuestionOptionVO.setSort(acctRiskQuestionOptionEntity.getSort());
			acctRiskQuestionOptionVO.setFlag(acctRiskQuestionOptionEntity.getFlag());
			acctRiskQuestionOptionVO.setLang(acctRiskQuestionOptionEntity.getLang());
			acctRiskQuestionOptionVO.setQuestionId(acctRiskQuestionOptionEntity.getQuestionId());
			acctRiskQuestionOptionVO.setCreateTime(acctRiskQuestionOptionEntity.getCreateTime());
			acctRiskQuestionOptionVOS.add(acctRiskQuestionOptionVO);
		}
		return page.setRecords(acctRiskQuestionOptionVOS);
	}

	/**
	 * 获取指定条件的风险测评问题选项列表
	 *
	 * @param questionId
	 * @return
	 */
	@Override
	public List<AccountRiskQuestionOptionVO> selectAcctRiskQuestionOptionList(Long questionId) {
		List<AccountRiskQuestionOptionVO> acctRiskQuestionOptionVOS = new ArrayList<>();
		List<AccountRiskQuestionOptionEntity> acctRiskQuestionOptionEntities = this.baseMapper.selectAcctRiskQuestionOptionList(questionId);
		for (AccountRiskQuestionOptionEntity acctRiskQuestionOptionEntity : acctRiskQuestionOptionEntities) {
			AccountRiskQuestionOptionVO acctRiskQuestionOptionVO = new AccountRiskQuestionOptionVO();
			acctRiskQuestionOptionVO.setId(acctRiskQuestionOptionEntity.getId());
			acctRiskQuestionOptionVO.setOptionId(acctRiskQuestionOptionEntity.getOptionId());
			acctRiskQuestionOptionVO.setOptionValue(acctRiskQuestionOptionEntity.getOptionValue());
			acctRiskQuestionOptionVO.setOptionScore(acctRiskQuestionOptionEntity.getOptionScore());
			acctRiskQuestionOptionVO.setSort(acctRiskQuestionOptionEntity.getSort());
			acctRiskQuestionOptionVO.setFlag(acctRiskQuestionOptionEntity.getFlag());
			acctRiskQuestionOptionVO.setLang(acctRiskQuestionOptionEntity.getLang());
			acctRiskQuestionOptionVO.setQuestionId(acctRiskQuestionOptionEntity.getQuestionId());
			acctRiskQuestionOptionVO.setCreateTime(acctRiskQuestionOptionEntity.getCreateTime());
			acctRiskQuestionOptionVOS.add(acctRiskQuestionOptionVO);
		}
		return acctRiskQuestionOptionVOS;
	}

	/**
	 * 获取指定条件的风险测评问题选项
	 *
	 * @param questionId
	 * @param optionId
	 * @return
	 */
	@Override
	public FundRiskQuestionOptionVO getQuestionOption(Long questionId, Integer optionId) {
		String lang = WebUtil.getHeader(CommonConstant.LANG);
		if (StringUtils.isBlank(lang))
			lang = CommonConstant.ZH_CN;
		return baseMapper.getQuestionOption(questionId, optionId, lang);
	}

	/**
	 * 添加风险测评问题选项
	 *
	 * @param acctRiskQuestionOption
	 * @return
	 */
	@Override
	public R addQuestionOption(AccountRiskQuestionOptionDTO acctRiskQuestionOption) {
		if (null == acctRiskQuestionOption.getQuestionId()) {
			return R.fail(ResultCode.PARAM_VALID_ERROR, I18nUtil.getMessage(RiskMessageConstant.RISK_QUESTION_ID_NOT_BE_NULL_NOTICE));
		}
		AccountRiskQuestionOptionEntity acctRiskQuestionOptionEntity = new AccountRiskQuestionOptionEntity();
		acctRiskQuestionOptionEntity.setQuestionId(acctRiskQuestionOption.getQuestionId());
		acctRiskQuestionOptionEntity.setOptionId(acctRiskQuestionOption.getOptionId());
		acctRiskQuestionOptionEntity.setOptionValue(acctRiskQuestionOption.getOptionValue());
		acctRiskQuestionOptionEntity.setOptionScore(acctRiskQuestionOption.getOptionScore());
		acctRiskQuestionOptionEntity.setSort(acctRiskQuestionOption.getSort());
		acctRiskQuestionOptionEntity.setFlag(acctRiskQuestionOption.getFlag());
		acctRiskQuestionOptionEntity.setLang(acctRiskQuestionOption.getLang());

		return R.data(this.save(acctRiskQuestionOptionEntity));
	}

	/**
	 * 批量添加风险测评问题选项
	 *
	 * @param acctRiskQuestionOptions
	 * @return
	 */
	@Override
	public R batchAddQuestionOption(List<AccountRiskQuestionOptionDTO> acctRiskQuestionOptions) {
		if (null == acctRiskQuestionOptions || acctRiskQuestionOptions.size() == 0) {
			return R.fail(ResultCode.PARAM_VALID_ERROR, I18nUtil.getMessage(RiskMessageConstant.RISK_QUESTION_ITEM_NOT_BE_NULL_NOTICE));
		}
		List<AccountRiskQuestionOptionEntity> acctRiskQuestionOptionEntities = new ArrayList<>();
		for (AccountRiskQuestionOptionDTO acctRiskQuestionOption : acctRiskQuestionOptions) {
			if (null == acctRiskQuestionOption.getQuestionId() || null == acctRiskQuestionOption.getQuestionId()) {
				return R.fail(ResultCode.PARAM_VALID_ERROR, I18nUtil.getMessage(RiskMessageConstant.RISK_QUESTION_ID_NOT_BE_NULL_NOTICE));
			}
			AccountRiskQuestionOptionEntity acctRiskQuestionOptionEntity = new AccountRiskQuestionOptionEntity();
			acctRiskQuestionOptionEntity.setQuestionId(acctRiskQuestionOption.getQuestionId());
			acctRiskQuestionOptionEntity.setOptionId(acctRiskQuestionOption.getOptionId());
			acctRiskQuestionOptionEntity.setOptionValue(acctRiskQuestionOption.getOptionValue());
			acctRiskQuestionOptionEntity.setOptionScore(acctRiskQuestionOption.getOptionScore());
			acctRiskQuestionOptionEntity.setSort(acctRiskQuestionOption.getSort());
			acctRiskQuestionOptionEntity.setFlag(acctRiskQuestionOption.getFlag());
			acctRiskQuestionOptionEntity.setLang(acctRiskQuestionOption.getLang());
			acctRiskQuestionOptionEntities.add(acctRiskQuestionOptionEntity);
		}
		if (acctRiskQuestionOptionEntities.size() > 0) {
			return R.data(this.saveBatch(acctRiskQuestionOptionEntities, 100));
		} else {
			return R.fail(ResultCode.PARAM_VALID_ERROR, I18nUtil.getMessage(RiskMessageConstant.RISK_QUESTION_ITEM_IS_EMPTY_SAVE_FAILED_NOTICE));
		}
	}

	/**
	 * 删除风险测评问题选项
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public R deleteQuestionOption(List<Long> ids) {
		if (null == ids || ids.size() == 0) {
			return R.fail(ResultCode.PARAM_VALID_ERROR, I18nUtil.getMessage(RiskMessageConstant.RISK_QUESTION_DELETE_ID_NOT_BE_NULL_NOTICE));
		}
		boolean result = this.deleteLogic(ids);
		if (result) {
			boolean isDeleted = zeroRedis.delAll(String.class);
			if (isDeleted) {
				log.info("删除风险测评问题缓存成功!");
			} else {
				log.info("删除风险测评问题缓存失败!");
			}
			return R.success();
		} else {
			return R.fail();
		}
	}
}
