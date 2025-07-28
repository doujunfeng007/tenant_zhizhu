package com.minigod.zero.cust.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.cust.service.ICustClickQuoteService;
import com.minigod.zero.core.mp.base.AppServiceImpl;
import com.minigod.zero.cust.entity.CustClickQuoteEntity;
import com.minigod.zero.cust.mapper.CustClickQuoteMapper;
import com.minigod.zero.cust.vo.CustClickQuoteVO;
import org.springframework.stereotype.Service;

/**
 * 点击报价权限表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@Service
public class CustClickQuoteServiceImpl extends AppServiceImpl<CustClickQuoteMapper, CustClickQuoteEntity> implements ICustClickQuoteService {

	@Override
	public IPage<CustClickQuoteVO> selectCustClickQuotePage(IPage<CustClickQuoteVO> page, CustClickQuoteVO clickQuote) {
		return page.setRecords(baseMapper.selectCustClickQuotePage(page, clickQuote));
	}


}
