package com.minigod.zero.cust.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.AppServiceImpl;
import com.minigod.zero.cust.entity.CustCityLoginEntity;
import com.minigod.zero.cust.mapper.CustCityLoginMapper;
import com.minigod.zero.cust.service.ICustCityLoginService;
import com.minigod.zero.cust.vo.CustCityLoginVO;
import org.springframework.stereotype.Service;

/**
 * 登录地记录表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@Service
public class CustCityLoginServiceImpl extends AppServiceImpl<CustCityLoginMapper, CustCityLoginEntity> implements ICustCityLoginService {

	@Override
	public IPage<CustCityLoginVO> selectCustCityLoginPage(IPage<CustCityLoginVO> page, CustCityLoginVO cityLogin) {
		return page.setRecords(baseMapper.selectCustCityLoginPage(page, cityLogin));
	}


}
