package com.minigod.zero.cust.service;

import com.minigod.zero.cust.entity.FeedbackEntity;
import com.minigod.zero.cust.vo.FeedbackVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户意见反馈表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
public interface IFeedbackService extends IService<FeedbackEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param feedback
	 * @return
	 */
	IPage<FeedbackVO> selectFeedbackPage(IPage<FeedbackVO> page, FeedbackVO feedback);


}
