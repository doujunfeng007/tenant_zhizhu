package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.entity.AcctOpenOfflineEntity;
import com.minigod.zero.bpm.vo.AcctOpenOfflineVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 线下开户-BPM回调记录表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface IAcctOpenOfflineService extends IService<AcctOpenOfflineEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctOpenOffline
	 * @return
	 */
	IPage<AcctOpenOfflineVO> selectAcctOpenOfflinePage(IPage<AcctOpenOfflineVO> page, AcctOpenOfflineVO acctOpenOffline);


}
