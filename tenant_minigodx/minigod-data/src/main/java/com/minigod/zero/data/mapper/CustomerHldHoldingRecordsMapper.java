package com.minigod.zero.data.mapper;

import com.minigod.zero.data.entity.CustomerBondHoldingRecords;
import com.minigod.zero.data.vo.CustomerHldHoldingListVO;
import com.minigod.zero.data.vo.CustomerHldHoldingRecordsListVO;
import com.minigod.zero.data.entity.CustomerHldHoldingRecords;
import com.minigod.zero.data.vo.CustomerHldHoldingRecordsListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dell
* @description 针对表【customer_hld_holding_records(客户活利得持仓表)】的数据库操作Mapper
* @createDate 2024-05-09 09:44:29
* @Entity com.minigod.zero.customer.entity.CustomerHldHoldingRecords
*/
public interface CustomerHldHoldingRecordsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerHldHoldingRecords record);

    int insertSelective(CustomerHldHoldingRecords record);

    CustomerHldHoldingRecords selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerHldHoldingRecords record);

    int updateByPrimaryKey(CustomerHldHoldingRecords record);

	List<CustomerHldHoldingRecords> selectBySubAccountIds(@Param("subAccounts") List<String> subAccounts);

    List<CustomerHldHoldingRecordsListVO> hldHoldingList(CustomerHldHoldingListVO vo);

	List<CustomerBondHoldingRecords> selectBySubAccountList(@Param("subAccountList") List<String> subAccountList);
}
