package com.minigod.zero.customer.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.customer.entity.CustomerFundHoldingRecordsEntity;
import com.minigod.zero.customer.vo.CustomerFundHoldingListVO;
import com.minigod.zero.customer.vo.CustomerFundHoldingRecordsEntityListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dell
* @description 针对表【customer_fund_holding_records(客户基金持仓表)】的数据库操作Mapper
* @createDate 2024-05-06 20:31:54
* @Entity com.minigod.zero.customer.entity.CustomerFundHoldingRecords
*/
@DS("customer")
public interface CustomerFundHoldingRecordsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerFundHoldingRecordsEntity record);

    int insertSelective(CustomerFundHoldingRecordsEntity record);

    CustomerFundHoldingRecordsEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerFundHoldingRecordsEntity record);

    int updateByPrimaryKey(CustomerFundHoldingRecordsEntity record);

	List<CustomerFundHoldingRecordsEntity> selectBySubAccountIds(@Param("subAccountList") List<String> subAccountList);

	List<CustomerFundHoldingRecordsEntityListVO> fundHoldingList(CustomerFundHoldingListVO vo);
}
