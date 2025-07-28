package com.minigod.zero.bpm.service.impl;

import com.minigod.zero.bpm.entity.AcctOpenImageEntity;
import com.minigod.zero.bpm.mapper.AcctOpenImageMapper;
import com.minigod.zero.bpm.service.IAcctOpenImageService;
import com.minigod.zero.bpm.vo.AcctOpenImageVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户开户影像表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Service
public class AcctOpenImageServiceImpl extends ServiceImpl<AcctOpenImageMapper, AcctOpenImageEntity> implements IAcctOpenImageService {

	@Override
	public IPage<AcctOpenImageVO> selectAcctOpenImagePage(IPage<AcctOpenImageVO> page, AcctOpenImageVO acctOpenImage) {
		return page.setRecords(baseMapper.selectAcctOpenImagePage(page, acctOpenImage));
	}


}
