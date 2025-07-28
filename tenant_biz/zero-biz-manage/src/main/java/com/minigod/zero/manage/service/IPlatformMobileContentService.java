package com.minigod.zero.manage.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.vo.SmsMessageRecord;
import com.minigod.zero.platform.entity.PlatformMobileContentEntity;
import com.minigod.zero.platform.vo.PlatformMobileContentVO;

/**
 * 短信内容信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-23
 */
public interface IPlatformMobileContentService extends BaseService<PlatformMobileContentEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param platformMobileContent
	 * @return
	 */
	IPage<PlatformMobileContentVO> selectPlatformMobileContentPage(IPage<PlatformMobileContentVO> page, PlatformMobileContentVO platformMobileContent);


	R pageList(IPage<SmsMessageRecord> page, String startTime, String endTime, String phone , String sendStatus);


}
