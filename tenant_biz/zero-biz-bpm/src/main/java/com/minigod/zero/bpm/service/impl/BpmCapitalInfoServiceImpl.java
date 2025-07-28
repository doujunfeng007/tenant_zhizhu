package com.minigod.zero.bpm.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpm.entity.BpmCapitalInfoEntity;
import com.minigod.zero.bpm.mapper.BpmCapitalInfoMapper;
import com.minigod.zero.bpm.service.IBpmCapitalInfoService;
import com.minigod.zero.bpm.vo.BpmCapitalInfoVO;
import com.minigod.zero.core.mp.base.AppServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 客户资金账号信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@Service
public class BpmCapitalInfoServiceImpl extends AppServiceImpl<BpmCapitalInfoMapper, BpmCapitalInfoEntity> implements IBpmCapitalInfoService {


	@Override
	public IPage<BpmCapitalInfoVO> selectBpmCapitalInfoPage(IPage<BpmCapitalInfoVO> page, BpmCapitalInfoVO capitalInfo) {
		return page.setRecords(baseMapper.selectBpmCapitalInfoPage(page, capitalInfo));
	}

}
