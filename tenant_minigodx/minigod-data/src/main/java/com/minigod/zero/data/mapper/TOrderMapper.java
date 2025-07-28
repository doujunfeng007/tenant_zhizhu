package com.minigod.zero.data.mapper;


import com.minigod.zero.data.entity.TOrder;
import com.minigod.zero.data.vo.ProductAssetVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author dell
* @description 针对表【t_order】的数据库操作Mapper
* @createDate 2024-10-29 10:45:18
* @Entity com.minigod.zero.data.entity.TOrder
*/
@Mapper
public interface TOrderMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TOrder record);

    int insertSelective(TOrder record);

    TOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TOrder record);

    int updateByPrimaryKey(TOrder record);

	List<ProductAssetVO> selectProductAsset();
}
