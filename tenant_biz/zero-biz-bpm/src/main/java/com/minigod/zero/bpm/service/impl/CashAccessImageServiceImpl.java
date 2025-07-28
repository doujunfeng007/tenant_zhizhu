package com.minigod.zero.bpm.service.impl;

import com.minigod.zero.bpm.entity.CashAccessImageEntity;
import com.minigod.zero.bpm.mapper.CashAccessImageMapper;
import com.minigod.zero.bpm.service.ICashAccessImageService;
import com.minigod.zero.bpm.vo.CashAccessImageVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 存取资金图片表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
public class CashAccessImageServiceImpl extends ServiceImpl<CashAccessImageMapper, CashAccessImageEntity> implements ICashAccessImageService {

	@Override
	public IPage<CashAccessImageVO> selectCashAccessImagePage(IPage<CashAccessImageVO> page, CashAccessImageVO cashAccessImage) {
		return page.setRecords(baseMapper.selectCashAccessImagePage(page, cashAccessImage));
	}


}
