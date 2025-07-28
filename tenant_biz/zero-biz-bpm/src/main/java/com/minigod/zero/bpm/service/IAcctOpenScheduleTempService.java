package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.entity.AcctOpenScheduleTempEntity;
import com.minigod.zero.bpm.vo.AcctOpenScheduleTempVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 前端开户进度缓存表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface IAcctOpenScheduleTempService extends IService<AcctOpenScheduleTempEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctOpenScheduleTemp
	 * @return
	 */
	IPage<AcctOpenScheduleTempVO> selectAcctOpenScheduleTempPage(IPage<AcctOpenScheduleTempVO> page, AcctOpenScheduleTempVO acctOpenScheduleTemp);


}
