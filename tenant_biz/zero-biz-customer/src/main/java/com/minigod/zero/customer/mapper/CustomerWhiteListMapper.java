package com.minigod.zero.customer.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.customer.entity.CustomerWhiteList;
import com.minigod.zero.customer.vo.CustomerOpenAccountVO;
import com.minigod.zero.customer.vo.CustomerWhiteListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dell
* @description 针对表【customer_white_list】的数据库操作Mapper
* @createDate 2024-08-02 18:44:28
* @Entity com.minigod.zero.customer.entity.CustomerWhiteList
*/
public interface CustomerWhiteListMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CustomerWhiteList record);

    int insertSelective(CustomerWhiteList record);

    CustomerWhiteList selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerWhiteList record);

    int updateByPrimaryKey(CustomerWhiteList record);

	CustomerWhiteList selectByCustId(@Param("custId")Long custId,@Param("tenantId") String  tenantId);

	List<CustomerWhiteListVO> queryCustomerWhiteListPage(IPage<CustomerWhiteListVO> page, @Param("keyword") String keyword);

}
