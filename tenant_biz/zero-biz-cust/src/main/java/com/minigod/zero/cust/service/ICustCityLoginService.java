package com.minigod.zero.cust.service;

import com.minigod.zero.cust.entity.CustCityLoginEntity;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.cust.vo.CustCityLoginVO;

/**
 * 登录地记录表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
public interface ICustCityLoginService extends BaseService<CustCityLoginEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cityLogin
	 * @return
	 */
	IPage<CustCityLoginVO> selectCustCityLoginPage(IPage<CustCityLoginVO> page, CustCityLoginVO cityLogin);


}
