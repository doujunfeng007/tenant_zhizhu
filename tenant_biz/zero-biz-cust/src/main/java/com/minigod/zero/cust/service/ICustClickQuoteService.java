package com.minigod.zero.cust.service;

import com.minigod.zero.cust.entity.CustClickQuoteEntity;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.cust.vo.CustClickQuoteVO;

/**
 * 点击报价权限表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
public interface ICustClickQuoteService extends BaseService<CustClickQuoteEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param clickQuote
	 * @return
	 */
	IPage<CustClickQuoteVO> selectCustClickQuotePage(IPage<CustClickQuoteVO> page, CustClickQuoteVO clickQuote);


}
