package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.PublishContentEntity;
import com.minigod.zero.manage.vo.PublishContentVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 帮助中心内容发布信息 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
public interface PublishContentMapper extends BaseMapper<PublishContentEntity> {

	/**
	 * 帮助中心内容发布信息分页查询
	 *
	 * @param page
	 * @param publishContent
	 * @return
	 */
	List<PublishContentVO> selectPublishContentPage(@Param("page") IPage page, @Param("params") PublishContentVO publishContent);


}
