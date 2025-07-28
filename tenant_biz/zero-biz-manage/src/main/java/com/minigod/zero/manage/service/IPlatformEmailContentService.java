package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.platform.entity.PlatformEmailContentEntity;
import com.minigod.zero.platform.vo.PlatformEmailContentVO;

/**
 * 短信内容信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
public interface IPlatformEmailContentService extends BaseService<PlatformEmailContentEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param platformEmailContent
	 * @return
	 */
	IPage<PlatformEmailContentVO> selectPlatformEmailContentPage(IPage<PlatformEmailContentEntity> page, PlatformEmailContentVO platformEmailContent);


}
