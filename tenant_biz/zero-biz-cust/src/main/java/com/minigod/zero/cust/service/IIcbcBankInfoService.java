package com.minigod.zero.cust.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.cust.entity.IcbcBankInfoEntity;
import com.minigod.zero.cust.vo.IcbcBankInfoVO;

/**
 * 客户银行账户信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-28
 */
public interface IIcbcBankInfoService extends BaseService<IcbcBankInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bankInfo
	 * @return
	 */
	IPage<IcbcBankInfoVO> selectIcbcBankInfoPage(IPage<IcbcBankInfoVO> page, IcbcBankInfoVO bankInfo);


}
