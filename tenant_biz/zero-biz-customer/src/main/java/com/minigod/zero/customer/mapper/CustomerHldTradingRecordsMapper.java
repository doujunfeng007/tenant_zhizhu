package com.minigod.zero.customer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.customer.dto.*;
import com.minigod.zero.customer.entity.CustomerHldTradingRecordsEntity;
import com.minigod.zero.customer.entity.CustomerInfoEntity;
import com.minigod.zero.customer.vo.CustHldTradingListVO;
import com.minigod.zero.customer.vo.CustomerHldTradingRecordsListVO;

import java.util.List;

public interface CustomerHldTradingRecordsMapper extends BaseMapper<CustomerInfoEntity> {
	List<CustomerHldTradingAppListDTO> selectTradingAppList(CustHldTradingListDO custHldTradingListDO);

	List<CustomerHldTradingBackListDTO> selectHldTradingBackList(CustomerHldTradingRecordsListVO vo);
}
