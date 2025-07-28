package com.minigod.zero.cust.feign;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.cust.feign.ICustFeedbackClient;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.cust.apivo.excel.FeedbackExcel;
import com.minigod.zero.cust.apivo.req.FeedbackQueryReq;
import com.minigod.zero.cust.entity.FeedbackEntity;
import com.minigod.zero.cust.feign.ICustFeedbackClient;
import com.minigod.zero.cust.service.IFeedbackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.feign.CustFeedbackClient
 * @Date: 2023年03月17日 10:36
 * @Description:
 */
@ApiIgnore()
@RestController
@AllArgsConstructor
@Slf4j
public class CustFeedbackClient implements ICustFeedbackClient {
	@Resource
	private IFeedbackService feedbackService;

	@Override
	public Page<FeedbackEntity> queryPageCustFeedback(FeedbackQueryReq feedbackQueryReq, Query query) {

		LambdaQueryWrapper<FeedbackEntity> queryWrapper = Wrappers.lambdaQuery();
        if(StringUtils.isNotEmpty(feedbackQueryReq.getCondition())){
			queryWrapper.like(FeedbackEntity::getNickName,feedbackQueryReq.getCondition())
				.or().like(FeedbackEntity::getContact,feedbackQueryReq.getCondition());

		}
		if(null !=feedbackQueryReq.getCustId()){
			queryWrapper.eq(FeedbackEntity::getCustId,feedbackQueryReq.getCustId());
		}
		queryWrapper.orderByDesc(FeedbackEntity::getCreateTime);

		IPage<FeedbackEntity> page = feedbackService.getBaseMapper().selectPage(Condition.getPage(query), queryWrapper);
		return new Page<FeedbackEntity>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(page.getRecords());
	}

	@Override
	public List<FeedbackExcel> exportFeedback(FeedbackQueryReq feedbackQueryReq) {
		LambdaQueryWrapper<FeedbackEntity> queryWrapper = Wrappers.lambdaQuery();
		if(StringUtils.isNotEmpty(feedbackQueryReq.getCondition())){
			queryWrapper.like(FeedbackEntity::getNickName,feedbackQueryReq.getCondition())
				.or().like(FeedbackEntity::getContact,feedbackQueryReq.getCondition());

		}
		if(null !=feedbackQueryReq.getCustId()){
			queryWrapper.eq(FeedbackEntity::getCustId,feedbackQueryReq.getCustId());
		}
		queryWrapper.orderByDesc(FeedbackEntity::getCreateTime);
		List<FeedbackEntity> list =feedbackService.getBaseMapper().selectList(queryWrapper);

		List<FeedbackExcel> result =BeanUtil.copy(list,FeedbackExcel.class);
		return result;
	}
}
