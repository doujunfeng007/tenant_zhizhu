package com.minigod.zero.bpm.service.impl;

import com.minigod.zero.bpm.entity.AcctOpenScheduleTempEntity;
import com.minigod.zero.bpm.mapper.AcctOpenScheduleTempMapper;
import com.minigod.zero.bpm.service.IAcctOpenScheduleTempService;
import com.minigod.zero.bpm.vo.AcctOpenScheduleTempVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 前端开户进度缓存表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Service
public class AcctOpenScheduleTempServiceImpl extends ServiceImpl<AcctOpenScheduleTempMapper, AcctOpenScheduleTempEntity> implements IAcctOpenScheduleTempService {

	@Override
	public IPage<AcctOpenScheduleTempVO> selectAcctOpenScheduleTempPage(IPage<AcctOpenScheduleTempVO> page, AcctOpenScheduleTempVO acctOpenScheduleTemp) {
		return page.setRecords(baseMapper.selectAcctOpenScheduleTempPage(page, acctOpenScheduleTemp));
	}


}
