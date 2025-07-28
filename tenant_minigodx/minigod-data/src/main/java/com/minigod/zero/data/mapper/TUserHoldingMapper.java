package com.minigod.zero.data.mapper;


import com.minigod.zero.data.entity.TUserHolding;
import com.minigod.zero.data.vo.AccumulatedProfitVO;
import com.minigod.zero.data.vo.CustomerAssetVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author dell
* @description 针对表【t_user_holding】的数据库操作Mapper
* @createDate 2024-10-28 19:41:11
* @Entity generator.TUserHolding
*/
@Mapper
public interface TUserHoldingMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TUserHolding record);

    int insertSelective(TUserHolding record);

    TUserHolding selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TUserHolding record);

    int updateByPrimaryKey(TUserHolding record);

	List<CustomerAssetVO> customerAssets();

	CustomerAssetVO selectAllAssets();

	List<AccumulatedProfitVO> selectAccumulatedProfitByCurrency();
}
