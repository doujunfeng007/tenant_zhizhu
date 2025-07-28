package com.minigod.zero.bpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpm.entity.AcctOpenInfoEntity;
import com.minigod.zero.bpm.mapper.AcctOpenInfoMapper;
import com.minigod.zero.bpm.service.IAcctOpenInfoService;
import com.minigod.zero.bpm.vo.AcctOpenInfoVO;
import org.springframework.stereotype.Service;

/**
 * 开户申请信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Service
public class AcctOpenInfoServiceImpl extends ServiceImpl<AcctOpenInfoMapper, AcctOpenInfoEntity> implements IAcctOpenInfoService {

	@Override
	public IPage<AcctOpenInfoVO> selectAcctOpenInfoPage(IPage<AcctOpenInfoVO> page, AcctOpenInfoVO acctOpenInfo) {
		return page.setRecords(baseMapper.selectAcctOpenInfoPage(page, acctOpenInfo));
	}

	@Override
	public boolean saveOrUpdateByCustId(AcctOpenInfoEntity entity) {
		if(null == entity.getCustId()) return false;
		LambdaQueryWrapper<AcctOpenInfoEntity> openInfoWrapper = new LambdaQueryWrapper<>();
		openInfoWrapper.eq(AcctOpenInfoEntity::getCustId,entity.getCustId());
		openInfoWrapper.last("limit 1");
		AcctOpenInfoEntity selObj = baseMapper.selectOne(openInfoWrapper);
		if(null != selObj) entity.setId(selObj.getId());

		return saveOrUpdate(entity);
	}


}
