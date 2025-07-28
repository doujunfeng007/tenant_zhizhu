package com.minigod.zero.cust.service.impl;

import com.minigod.zero.cust.entity.FuncWhiteListEntity;
import com.minigod.zero.cust.vo.FuncWhiteListVO;
import com.minigod.zero.cust.mapper.FuncWhiteListMapper;
import com.minigod.zero.cust.service.IFuncWhiteListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 功能白名单用户信息 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
@Service
public class FuncWhiteListServiceImpl extends ServiceImpl<FuncWhiteListMapper, FuncWhiteListEntity> implements IFuncWhiteListService {

	@Override
	public IPage<FuncWhiteListVO> selectFuncWhiteListPage(IPage<FuncWhiteListVO> page, FuncWhiteListVO funcWhiteList) {
		return page.setRecords(baseMapper.selectFuncWhiteListPage(page, funcWhiteList));
	}


}
