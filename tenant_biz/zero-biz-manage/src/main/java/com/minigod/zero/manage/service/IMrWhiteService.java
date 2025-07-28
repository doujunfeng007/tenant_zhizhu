package com.minigod.zero.manage.service;

import com.minigod.zero.manage.entity.MrWhiteEntity;
import com.minigod.zero.manage.vo.MrWhiteVO;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * MR白名单 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
public interface IMrWhiteService extends BaseService<MrWhiteEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param mrWhite
	 * @return
	 */
	IPage<MrWhiteVO> selectMrWhitePage(IPage<MrWhiteVO> page, MrWhiteVO mrWhite);


	/**
	 * 添加白名单
	 * @param mrWhite
	 * @return
	 */
    R<Object> submit(MrWhiteVO mrWhite);
}
