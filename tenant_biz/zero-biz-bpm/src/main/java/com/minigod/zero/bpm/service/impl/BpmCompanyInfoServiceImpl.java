package com.minigod.zero.bpm.service.impl;

import com.minigod.zero.bpm.entity.BpmCompanyInfoEntity;
import com.minigod.zero.bpm.mapper.BpmCompanyInfoMapper;
import com.minigod.zero.bpm.service.IBpmCompanyInfoService;
import com.minigod.zero.bpm.vo.BpmCompanyInfoVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 公司户详细资料表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
public class BpmCompanyInfoServiceImpl extends ServiceImpl<BpmCompanyInfoMapper, BpmCompanyInfoEntity> implements IBpmCompanyInfoService {

	@Override
	public IPage<BpmCompanyInfoVO> selectBpmCompanyInfoPage(IPage<BpmCompanyInfoVO> page, BpmCompanyInfoVO BpmCompanyInfo) {
		return page.setRecords(baseMapper.selectBpmCompanyInfoPage(page, BpmCompanyInfo));
	}


}
