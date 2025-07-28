package com.minigod.zero.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.data.dto.CustomerHldTradingBackListDTO;
import com.minigod.zero.data.entity.CustomerInfo;
import com.minigod.zero.data.vo.CustomerHldTradingRecordsListVO;
import com.minigod.zero.data.dto.CustHldTradingListDO;
import com.minigod.zero.data.dto.CustomerHldTradingAppListDTO;
import com.minigod.zero.data.vo.CustomerHldTradingRecordsListVO;

import java.util.List;

public interface CustomerHldTradingRecordsMapper extends BaseMapper<CustomerInfo> {
	List<CustomerHldTradingAppListDTO> selectTradingAppList(CustHldTradingListDO custHldTradingListDO);

	List<CustomerHldTradingBackListDTO> selectHldTradingBackList(CustomerHldTradingRecordsListVO vo);
}
