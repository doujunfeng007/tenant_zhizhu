package com.minigod.zero.bpm.service.impl;

import com.minigod.zero.bpm.entity.AcctRealnameVerifyEntity;
import com.minigod.zero.bpm.mapper.AcctRealnameVerifyMapper;
import com.minigod.zero.bpm.service.IAcctRealnameVerifyService;
import com.minigod.zero.bpm.vo.AcctRealnameVerifyVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 用户实名认证表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Service
public class AcctRealnameVerifyServiceImpl extends ServiceImpl<AcctRealnameVerifyMapper, AcctRealnameVerifyEntity> implements IAcctRealnameVerifyService {

	@Override
	public IPage<AcctRealnameVerifyVO> selectAcctRealnameVerifyPage(IPage<AcctRealnameVerifyVO> page, AcctRealnameVerifyVO acctRealnameVerify) {
		return page.setRecords(baseMapper.selectAcctRealnameVerifyPage(page, acctRealnameVerify));
	}


}
