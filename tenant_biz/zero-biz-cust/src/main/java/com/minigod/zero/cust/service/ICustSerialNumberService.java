package com.minigod.zero.cust.service;

import com.minigod.zero.cust.entity.CustSerialNumberEntity;
import com.minigod.zero.cust.vo.CustSerialNumberVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 序列号管理 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-28
 */
public interface ICustSerialNumberService extends BaseService<CustSerialNumberEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param serialNumberManage
	 * @return
	 */
	IPage<CustSerialNumberVO> selectCustSerialNumberManagePage(IPage<CustSerialNumberVO> page, CustSerialNumberVO serialNumberManage);

}
