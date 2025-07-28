package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.AppConfigEntity;
import com.minigod.zero.manage.vo.AppConfigVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * APP配置 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
public interface AppConfigMapper extends BaseMapper<AppConfigEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param appConfig
	 * @return
	 */
	List<AppConfigVO> selectAppConfigPage(IPage page, @Param("appConfig") AppConfigVO appConfig);


}
