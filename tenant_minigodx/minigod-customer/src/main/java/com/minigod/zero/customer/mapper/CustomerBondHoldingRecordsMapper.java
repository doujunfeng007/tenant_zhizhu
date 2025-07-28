package com.minigod.zero.customer.mapper;

import com.minigod.zero.customer.entity.CustomerBondHoldingRecords;
import com.minigod.zero.customer.vo.CustomerBondHoldingListVO;
import com.minigod.zero.customer.vo.CustomerBondHoldingRecordsListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dell
* @description 针对表【customer_bond_holding_records(客户债卷持仓表)】的数据库操作Mapper
* @createDate 2024-05-09 09:44:05
* @Entity com.minigod.zero.customer.entity.CustomerBondHoldingRecords
*/
public interface CustomerBondHoldingRecordsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerBondHoldingRecords record);

    int insertSelective(CustomerBondHoldingRecords record);

    CustomerBondHoldingRecords selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerBondHoldingRecords record);

    int updateByPrimaryKey(CustomerBondHoldingRecords record);

    List<CustomerBondHoldingRecordsListVO> bondHoldingList(CustomerBondHoldingListVO vo);

	List<CustomerBondHoldingRecords> selectBySubAccountList(@Param("subAccountList") List<String> subAccountList);
}
