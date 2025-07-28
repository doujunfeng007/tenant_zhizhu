package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.dto.CustHldTradingListDO;
import com.minigod.zero.customer.dto.CustomerBondTradingBackListDTO;
import com.minigod.zero.customer.dto.CustomerHldTradingAppListDTO;
import com.minigod.zero.customer.entity.CustomerBondHoldingRecords;
import com.minigod.zero.customer.entity.CustomerBondTradingRecords;
import com.minigod.zero.customer.vo.CustomerBondHoldingListVO;
import com.minigod.zero.customer.vo.CustomerBondTradingRecordsListVO;

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
