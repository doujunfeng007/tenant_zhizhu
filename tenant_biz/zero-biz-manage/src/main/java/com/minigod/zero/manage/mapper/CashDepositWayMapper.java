package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.CashDepositWay;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dell
* @description 针对表【cash_deposit_way(入金方式管理配置表)】的数据库操作Mapper
* @createDate 2024-07-15 18:04:29
* @Entity generator.domain.CashDepositWay
*/
public interface CashDepositWayMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CashDepositWay record);

    int insertSelective(CashDepositWay record);

    CashDepositWay selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CashDepositWay record);

    int updateByPrimaryKey(CashDepositWay record);

	List<CashDepositWay> selectDepositWay(@Param("currency") Integer currency, @Param("bankType")Integer bankType);
}
