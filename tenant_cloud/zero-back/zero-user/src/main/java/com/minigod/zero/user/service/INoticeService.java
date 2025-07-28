package com.minigod.zero.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.user.entity.Notice;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.user.vo.NoticeVO;

/**
 * 服务类
 *
 * @author Chill
 */
public interface INoticeService extends BaseService<Notice> {

	/**
	 * 自定义分页
	 * @param page
	 * @param notice
	 * @return
	 */
	IPage<NoticeVO> selectNoticePage(IPage<NoticeVO> page, NoticeVO notice);

}
