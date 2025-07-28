package com.minigod.zero.cust.mapper;

import com.minigod.zero.cust.entity.CustClickQuoteEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.cust.vo.CustClickQuoteVO;

import java.util.List;

/**
 * 点击报价权限表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
public interface CustClickQuoteMapper extends BaseMapper<CustClickQuoteEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param clickQuote
	 * @return
	 */
	List<CustClickQuoteVO> selectCustClickQuotePage(IPage page, CustClickQuoteVO clickQuote);


}
