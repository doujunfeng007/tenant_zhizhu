package com.minigod.zero.bpm.service.impl;

import com.minigod.zero.bpm.entity.AcctChangeInfoTempEntity;
import com.minigod.zero.bpm.mapper.AcctChangeInfoTempMapper;
import com.minigod.zero.bpm.service.IAcctChangeInfoTempService;
import com.minigod.zero.bpm.vo.AcctChangeInfoTempVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户证券资料修改缓存表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Service
public class AcctChangeInfoTempServiceImpl extends ServiceImpl<AcctChangeInfoTempMapper, AcctChangeInfoTempEntity> implements IAcctChangeInfoTempService {

	@Override
	public IPage<AcctChangeInfoTempVO> selectAcctChangeInfoTempPage(IPage<AcctChangeInfoTempVO> page, AcctChangeInfoTempVO acctChangeInfoTemp) {
		return page.setRecords(baseMapper.selectAcctChangeInfoTempPage(page, acctChangeInfoTemp));
	}


}
