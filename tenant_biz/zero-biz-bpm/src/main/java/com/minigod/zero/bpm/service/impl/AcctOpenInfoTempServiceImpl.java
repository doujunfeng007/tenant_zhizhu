package com.minigod.zero.bpm.service.impl;

import com.minigod.zero.bpm.entity.AcctOpenInfoTempEntity;
import com.minigod.zero.bpm.mapper.AcctOpenInfoTempMapper;
import com.minigod.zero.bpm.service.IAcctOpenInfoTempService;
import com.minigod.zero.bpm.vo.AcctOpenInfoTempVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 开户申请-客户填写信息缓存表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Service
public class AcctOpenInfoTempServiceImpl extends ServiceImpl<AcctOpenInfoTempMapper, AcctOpenInfoTempEntity> implements IAcctOpenInfoTempService {

	@Override
	public IPage<AcctOpenInfoTempVO> selectAcctOpenInfoTempPage(IPage<AcctOpenInfoTempVO> page, AcctOpenInfoTempVO acctOpenInfoTemp) {
		return page.setRecords(baseMapper.selectAcctOpenInfoTempPage(page, acctOpenInfoTemp));
	}


}
