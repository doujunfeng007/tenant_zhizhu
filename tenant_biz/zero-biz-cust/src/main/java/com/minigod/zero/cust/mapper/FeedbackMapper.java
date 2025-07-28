package com.minigod.zero.cust.mapper;

import com.minigod.zero.cust.entity.FeedbackEntity;
import com.minigod.zero.cust.vo.FeedbackVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户意见反馈表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
public interface FeedbackMapper extends BaseMapper<FeedbackEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param feedback
	 * @return
	 */
	List<FeedbackVO> selectFeedbackPage(IPage page, FeedbackVO feedback);


}
