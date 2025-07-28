package com.minigod.zero.cust.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.cust.entity.CustDoubleVerifyEntity;
import com.minigod.zero.cust.mapper.CustDoubleVerifyMapper;
import com.minigod.zero.cust.service.ICustDoubleVerifyService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 二重认证信息 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Service
public class CustDoubleVerifyServiceImpl extends BaseServiceImpl<CustDoubleVerifyMapper, CustDoubleVerifyEntity> implements ICustDoubleVerifyService {

	@Override
	public CustDoubleVerifyEntity findUserDoubleVerifyRecord(Long custId, String deviceCode) {

		LambdaQueryWrapper<CustDoubleVerifyEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(CustDoubleVerifyEntity ::getCustId,custId);
		queryWrapper.eq(CustDoubleVerifyEntity::getEquipmentNum,deviceCode);
		queryWrapper.eq(CustDoubleVerifyEntity::getStatus,1);
		queryWrapper.eq(CustDoubleVerifyEntity::getIsDeleted,0);
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public CustDoubleVerifyEntity verifyWtForward(Long custId, String deviceCode) {
		LambdaQueryWrapper<CustDoubleVerifyEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(CustDoubleVerifyEntity ::getCustId,custId);
		queryWrapper.eq(CustDoubleVerifyEntity::getEquipmentNum,deviceCode);
		queryWrapper.gt(CustDoubleVerifyEntity ::getLastDatetime,new Date());
		queryWrapper.eq(CustDoubleVerifyEntity::getStatus,1);
		queryWrapper.eq(CustDoubleVerifyEntity::getIsDeleted,0);
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public CustDoubleVerifyEntity updateDoubleVerify(Long custId, String deviceCode, Integer cancel2fa) {

		CustDoubleVerifyEntity custDoubleVerifyEntity = findUserDoubleVerifyRecord(custId,deviceCode);
		if(custDoubleVerifyEntity == null){
			custDoubleVerifyEntity =new CustDoubleVerifyEntity();
			custDoubleVerifyEntity.setCustId(custId);
			custDoubleVerifyEntity.setEquipmentNum(deviceCode);
			custDoubleVerifyEntity.setLastDatetime(DateUtil.plusDays(new Date(),7));//设置过期时间
			custDoubleVerifyEntity.setCreateTime(new Date());
			custDoubleVerifyEntity.setSelectedType(cancel2fa != 0);
			baseMapper.insert(custDoubleVerifyEntity);
		}else{
			custDoubleVerifyEntity.setLastDatetime(DateUtil.plusDays(new Date(),7));
			custDoubleVerifyEntity.setSelectedType(cancel2fa != 0);
			baseMapper.updateById(custDoubleVerifyEntity);
		}

		return custDoubleVerifyEntity;
	}

}
