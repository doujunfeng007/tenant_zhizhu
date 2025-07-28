package com.minigod.zero.cust.service;

import com.minigod.zero.cust.entity.CustIcbcaInfoEntity;
import com.minigod.zero.cust.vo.CustIcbcaInfoVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 工银客户信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-28
 */
public interface ICustIcbcaInfoService extends BaseService<CustIcbcaInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param icbcaInfo
	 * @return
	 */
	IPage<CustIcbcaInfoVO> selectCustIcbcaInfoPage(IPage<CustIcbcaInfoVO> page, CustIcbcaInfoVO icbcaInfo);


}
