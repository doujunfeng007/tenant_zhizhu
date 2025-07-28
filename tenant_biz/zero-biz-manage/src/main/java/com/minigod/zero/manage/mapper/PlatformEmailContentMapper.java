package com.minigod.zero.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.platform.entity.PlatformEmailContentEntity;
import com.minigod.zero.platform.vo.PlatformEmailContentVO;
import org.apache.ibatis.annotations.Param;

/**
 * 短信内容信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
public interface PlatformEmailContentMapper extends BaseMapper<PlatformEmailContentEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param platformEmailContentVO
	 * @return
	 */
	IPage<PlatformEmailContentVO> selectPlatformEmailContentPage(@Param("page") IPage page, @Param("params") PlatformEmailContentVO platformEmailContentVO);


}
