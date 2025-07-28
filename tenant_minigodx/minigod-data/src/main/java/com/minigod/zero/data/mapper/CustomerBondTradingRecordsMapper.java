package com.minigod.zero.data.mapper;

import com.minigod.zero.data.dto.CustHldTradingListDO;
import com.minigod.zero.data.dto.CustomerBondTradingBackListDTO;
import com.minigod.zero.data.dto.CustomerHldTradingAppListDTO;
import com.minigod.zero.data.entity.CustomerBondHoldingRecords;
import com.minigod.zero.data.entity.CustomerBondTradingRecords;
import com.minigod.zero.data.vo.CustomerBondHoldingListVO;
import com.minigod.zero.data.vo.CustomerBondTradingRecordsListVO;

import java.util.List;

/**
* @author dell
* @description 针对表【customer_bond_trading_records(客户债券交易流水汇总表)】的数据库操作Mapper
* @createDate 2024-05-09 09:44:05
* @Entity com.minigod.zero.customer.entity.CustomerBondTradingRecords
*/
public interface CustomerBondTradingRecordsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerBondTradingRecords record);

    int insertSelective(CustomerBondTradingRecords record);

    CustomerBondTradingRecords selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerBondTradingRecords record);

    int updateByPrimaryKey(CustomerBondTradingRecords record);

    List<CustomerBondTradingBackListDTO> selectBondTradingBackList(CustomerBondTradingRecordsListVO vo);

	List<CustomerBondHoldingRecords> bondHoldingList(CustomerBondHoldingListVO vo);

    List<CustomerHldTradingAppListDTO> selectTradingAppList(CustHldTradingListDO custHldTradingListDO);
}
