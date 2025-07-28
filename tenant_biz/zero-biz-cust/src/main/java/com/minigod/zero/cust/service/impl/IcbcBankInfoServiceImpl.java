package com.minigod.zero.cust.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.cust.service.IIcbcBankInfoService;
import com.minigod.zero.core.mp.base.AppServiceImpl;
import com.minigod.zero.cust.entity.IcbcBankInfoEntity;
import com.minigod.zero.cust.mapper.IcbcBankInfoMapper;
import com.minigod.zero.cust.vo.IcbcBankInfoVO;
import org.springframework.stereotype.Service;

/**
 * 客户银行账户信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-28
 */
@Service
public class IcbcBankInfoServiceImpl extends AppServiceImpl<IcbcBankInfoMapper, IcbcBankInfoEntity> implements IIcbcBankInfoService {

	@Override
	public IPage<IcbcBankInfoVO> selectIcbcBankInfoPage(IPage<IcbcBankInfoVO> page, IcbcBankInfoVO bankInfo) {
		return page.setRecords(baseMapper.selectIcbcBankInfoPage(page, bankInfo));
	}


}
