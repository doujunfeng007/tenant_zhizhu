package com.minigod.zero.data.mapper;


import com.minigod.zero.data.entity.TExchRate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author dell
* @description 针对表【t_exch_rate】的数据库操作Mapper
* @createDate 2024-10-28 20:19:17
* @Entity com.minigod.zero.data.entity.TExchRate
*/
@Mapper
public interface TExchRateMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TExchRate record);

    int insertSelective(TExchRate record);

    TExchRate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TExchRate record);

    int updateByPrimaryKey(TExchRate record);

	List<TExchRate> getAll();
}
