package com.minigod.zero.bpm.service.impl;

import com.minigod.zero.bpm.entity.BpmFundAcctInfoEntity;
import com.minigod.zero.bpm.mapper.BpmFundAcctInfoMapper;
import com.minigod.zero.bpm.service.IBpmFundAcctInfoService;
import com.minigod.zero.bpm.vo.BpmFundAcctInfoVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 基金账户信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
public class BpmFundAcctInfoServiceImpl extends ServiceImpl<BpmFundAcctInfoMapper, BpmFundAcctInfoEntity> implements IBpmFundAcctInfoService {

	@Override
	public IPage<BpmFundAcctInfoVO> selectBpmFundAcctInfoPage(IPage<BpmFundAcctInfoVO> page, BpmFundAcctInfoVO bpmFundAcctInfo) {
		return page.setRecords(baseMapper.selectBpmFundAcctInfoPage(page, bpmFundAcctInfo));
	}


}
