package com.minigod.zero.bpm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpm.entity.BpmAccountInfoEntity;
import com.minigod.zero.bpm.mapper.BpmAccountInfoMapper;
import com.minigod.zero.bpm.service.IBpmAccountInfoService;
import com.minigod.zero.bpm.vo.BpmAccountInfoVO;
import com.minigod.zero.core.mp.base.AppServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 交易账户信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@Service
public class BpmAccountInfoServiceImpl extends AppServiceImpl<BpmAccountInfoMapper, BpmAccountInfoEntity> implements IBpmAccountInfoService {


	@Override
	public IPage<BpmAccountInfoVO> selectBpmAccountInfoPage(IPage<BpmAccountInfoVO> page, BpmAccountInfoVO acctInfo) {
		return page.setRecords(baseMapper.selectBpmAccountInfoPage(page, acctInfo));
	}

}
