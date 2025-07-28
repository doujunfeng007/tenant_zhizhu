package com.minigod.zero.bpmn.module.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.stock.domain.bo.CustomerAccountStockApplicationQuery;
import com.minigod.zero.bpmn.module.stock.domain.vo.CustomerAccountStockApplicationVO;
import com.minigod.zero.bpmn.module.stock.entity.CustomerAccountStockApplicationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * 股票增开申请流程表 Mapper 接口
 * @author Administrator
 */
@Mapper
public interface CustomerAccountStockApplicationMapper extends BaseMapper<CustomerAccountStockApplicationEntity> {

    CustomerAccountStockApplicationVO queryApplicationId(String applicationId);

	IPage<CustomerAccountStockApplicationVO> selectCustomerAccountStockApplicationPage(IPage<Object> page, @Param("bo") CustomerAccountStockApplicationQuery applicationQuery);
}
