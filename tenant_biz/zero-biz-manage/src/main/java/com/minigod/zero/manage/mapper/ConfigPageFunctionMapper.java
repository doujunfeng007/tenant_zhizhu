package com.minigod.zero.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.ConfigPageFunctionEntity;
import com.minigod.zero.manage.vo.ConfigPageFunctionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 配置页面组件 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
public interface ConfigPageFunctionMapper extends BaseMapper<ConfigPageFunctionEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ConfigPageFunction
	 * @return
	 */
	List<ConfigPageFunctionVO> selectConfigPageFunctionPage(IPage page, ConfigPageFunctionVO ConfigPageFunction);


    int updateSort(@Param("id") Long id);
}
