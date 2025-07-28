package com.minigod.zero.bpm.service.impl;

import com.minigod.zero.bpm.entity.AcctCardVerifyEntity;
import com.minigod.zero.bpm.mapper.AcctCardVerifyMapper;
import com.minigod.zero.bpm.service.IAcctCardVerifyService;
import com.minigod.zero.bpm.vo.AcctCardVerifyVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 银行卡四要素验证信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Service
public class AcctCardVerifyServiceImpl extends ServiceImpl<AcctCardVerifyMapper, AcctCardVerifyEntity> implements IAcctCardVerifyService {

	@Override
	public IPage<AcctCardVerifyVO> selectAcctCardVerifyPage(IPage<AcctCardVerifyVO> page, AcctCardVerifyVO acctCardVerify) {
		return page.setRecords(baseMapper.selectAcctCardVerifyPage(page, acctCardVerify));
	}


}
