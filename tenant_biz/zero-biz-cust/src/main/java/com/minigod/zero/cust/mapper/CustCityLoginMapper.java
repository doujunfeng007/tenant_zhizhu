package com.minigod.zero.cust.mapper;

import com.minigod.zero.cust.entity.CustCityLoginEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.cust.vo.CustCityLoginVO;

import java.util.List;

/**
 * 登录地记录表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
public interface CustCityLoginMapper extends BaseMapper<CustCityLoginEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cityLogin
	 * @return
	 */
	List<CustCityLoginVO> selectCustCityLoginPage(IPage page, CustCityLoginVO cityLogin);


}
