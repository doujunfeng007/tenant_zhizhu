package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.PublishItemEntity;
import com.minigod.zero.manage.vo.PublishItemVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 帮助中心目录配置 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-21
 */
public interface PublishItemMapper extends BaseMapper<PublishItemEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param publishItem
	 * @return
	 */
	List<PublishItemVO> selectPublishItemPage(IPage page, PublishItemVO publishItem);


}
