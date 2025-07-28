package com.minigod.zero.bpm.service.impl;

import com.minigod.zero.bpm.entity.AcctOpenOfflineEntity;
import com.minigod.zero.bpm.mapper.AcctOpenOfflineMapper;
import com.minigod.zero.bpm.service.IAcctOpenOfflineService;
import com.minigod.zero.bpm.vo.AcctOpenOfflineVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 线下开户-BPM回调记录表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Service
public class AcctOpenOfflineServiceImpl extends ServiceImpl<AcctOpenOfflineMapper, AcctOpenOfflineEntity> implements IAcctOpenOfflineService {

	@Override
	public IPage<AcctOpenOfflineVO> selectAcctOpenOfflinePage(IPage<AcctOpenOfflineVO> page, AcctOpenOfflineVO acctOpenOffline) {
		return page.setRecords(baseMapper.selectAcctOpenOfflinePage(page, acctOpenOffline));
	}


}
