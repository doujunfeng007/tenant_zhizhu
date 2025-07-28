package com.minigod.zero.bpm.service.impl;

import com.minigod.zero.bpm.entity.AcctChangeImageEntity;
import com.minigod.zero.bpm.mapper.AcctChangeImageMapper;
import com.minigod.zero.bpm.service.IAcctChangeImageService;
import com.minigod.zero.bpm.vo.AcctChangeImageVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户修改资料图片表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Service
public class AcctChangeImageServiceImpl extends ServiceImpl<AcctChangeImageMapper, AcctChangeImageEntity> implements IAcctChangeImageService {

	@Override
	public IPage<AcctChangeImageVO> selectAcctChangeImagePage(IPage<AcctChangeImageVO> page, AcctChangeImageVO acctChangeImage) {
		return page.setRecords(baseMapper.selectAcctChangeImagePage(page, acctChangeImage));
	}


}
