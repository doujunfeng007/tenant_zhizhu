package com.minigod.zero.cust.service.impl;

import com.minigod.zero.cust.entity.FuncWhiteConfigEntity;
import com.minigod.zero.cust.service.IFuncWhiteConfigService;
import com.minigod.zero.cust.vo.FuncWhiteConfigVO;
import com.minigod.zero.cust.mapper.FuncWhiteConfigMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 功能白名单启用配置 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
@Service
public class FuncWhiteConfigServiceImpl extends ServiceImpl<FuncWhiteConfigMapper, FuncWhiteConfigEntity> implements IFuncWhiteConfigService {

	@Override
	public IPage<FuncWhiteConfigVO> selectFuncWhiteConfigPage(IPage<FuncWhiteConfigVO> page, FuncWhiteConfigVO funcWhiteConfig) {
		return page.setRecords(baseMapper.selectFuncWhiteConfigPage(page, funcWhiteConfig));
	}


}
