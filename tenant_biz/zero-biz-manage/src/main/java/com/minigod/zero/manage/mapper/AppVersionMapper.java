package com.minigod.zero.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.AppVersionEntity;
import com.minigod.zero.manage.vo.request.AppVersionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * APP版本信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-04-24
 */
public interface AppVersionMapper extends BaseMapper<AppVersionEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param appVersion
	 * @return
	 */
	List<AppVersionVO> selectAppVersionPage(@Param("page") IPage page, @Param("appVersion") AppVersionVO appVersion);


}
