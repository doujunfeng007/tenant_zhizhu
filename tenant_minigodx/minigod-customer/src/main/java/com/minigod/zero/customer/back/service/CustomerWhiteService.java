package com.minigod.zero.customer.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.vo.CustomerOpenAccountVO;
import com.minigod.zero.customer.vo.CustomerWhiteListVO;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/2 18:49
 * @description：
 */
public interface CustomerWhiteService {
	/**
	 * 添加白名单
	 * @param custId
	 * @return
	 */
	R addWhiteList(Long custId);

	/**
	 * 修改状态
	 * @param custId
	 * @param status
	 * @return
	 */
	R updateStatus(Long custId,Integer status);

	/**
	 * 分页列表
	 * @param page
	 * @param keyword
	 * @return
	 */
	R queryPage(IPage<CustomerWhiteListVO> page, String keyword);
}
