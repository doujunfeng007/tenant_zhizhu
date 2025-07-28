package com.minigod.zero.cust.service.impl;

import com.minigod.zero.cust.entity.FeedbackEntity;
import com.minigod.zero.cust.vo.FeedbackVO;
import com.minigod.zero.cust.mapper.FeedbackMapper;
import com.minigod.zero.cust.service.IFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户意见反馈表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, FeedbackEntity> implements IFeedbackService {

	@Override
	public IPage<FeedbackVO> selectFeedbackPage(IPage<FeedbackVO> page, FeedbackVO feedback) {
		return page.setRecords(baseMapper.selectFeedbackPage(page, feedback));
	}


}
