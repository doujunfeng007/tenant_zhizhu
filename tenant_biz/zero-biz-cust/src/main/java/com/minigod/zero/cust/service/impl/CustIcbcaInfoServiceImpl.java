package com.minigod.zero.cust.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.AppServiceImpl;
import com.minigod.zero.cust.entity.CustIcbcaInfoEntity;
import com.minigod.zero.cust.mapper.CustIcbcaInfoMapper;
import com.minigod.zero.cust.service.ICustIcbcaInfoService;
import com.minigod.zero.cust.vo.CustIcbcaInfoVO;
import org.springframework.stereotype.Service;

/**
 * 工银客户信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-28
 */
@Service
public class CustIcbcaInfoServiceImpl extends AppServiceImpl<CustIcbcaInfoMapper, CustIcbcaInfoEntity> implements ICustIcbcaInfoService {

	@Override
	public IPage<CustIcbcaInfoVO> selectCustIcbcaInfoPage(IPage<CustIcbcaInfoVO> page, CustIcbcaInfoVO icbcaInfo) {
		return page.setRecords(baseMapper.selectCustIcbcaInfoPage(page, icbcaInfo));
	}


}
