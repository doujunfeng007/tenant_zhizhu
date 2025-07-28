package com.minigod.zero.cust.service;

import com.minigod.zero.cust.entity.FuncWhiteListEntity;
import com.minigod.zero.cust.vo.FuncWhiteListVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 功能白名单用户信息 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
public interface IFuncWhiteListService extends IService<FuncWhiteListEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param funcWhiteList
	 * @return
	 */
	IPage<FuncWhiteListVO> selectFuncWhiteListPage(IPage<FuncWhiteListVO> page, FuncWhiteListVO funcWhiteList);


}
