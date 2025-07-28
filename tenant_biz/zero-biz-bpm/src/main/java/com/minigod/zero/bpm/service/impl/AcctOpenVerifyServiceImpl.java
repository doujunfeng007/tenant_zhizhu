package com.minigod.zero.bpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpm.entity.AcctOpenVerifyEntity;
import com.minigod.zero.bpm.mapper.AcctOpenVerifyMapper;
import com.minigod.zero.bpm.service.IAcctOpenVerifyService;
import com.minigod.zero.bpm.vo.AcctOpenVerifyVO;
import org.springframework.stereotype.Service;

/**
 * 客户认证记录表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Service
public class AcctOpenVerifyServiceImpl extends ServiceImpl<AcctOpenVerifyMapper, AcctOpenVerifyEntity> implements IAcctOpenVerifyService {

	@Override
	public IPage<AcctOpenVerifyVO> selectAcctOpenVerifyPage(IPage<AcctOpenVerifyVO> page, AcctOpenVerifyVO acctOpenVerify) {
		return page.setRecords(baseMapper.selectAcctOpenVerifyPage(page, acctOpenVerify));
	}

	@Override
	public boolean saveOrUpdateByCustId(AcctOpenVerifyEntity entity) {
		if(null == entity.getCustId()) return false;
		LambdaQueryWrapper<AcctOpenVerifyEntity> openVerifyWrapper = new LambdaQueryWrapper<>();
		openVerifyWrapper.eq(AcctOpenVerifyEntity::getCustId,entity.getCustId());
		AcctOpenVerifyEntity selObj = baseMapper.selectOne(openVerifyWrapper);
		if(null != selObj) entity.setId(selObj.getId());

		return saveOrUpdate(entity);
	}


}
