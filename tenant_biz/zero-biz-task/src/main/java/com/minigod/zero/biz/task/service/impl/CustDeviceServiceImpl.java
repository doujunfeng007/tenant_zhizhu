package com.minigod.zero.biz.task.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.biz.task.mapper.CustDeviceMapper;
import com.minigod.zero.biz.task.service.ICustDeviceService;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.cust.entity.CustDeviceEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户设备信息 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Service
public class CustDeviceServiceImpl extends BaseServiceImpl<CustDeviceMapper, CustDeviceEntity> implements ICustDeviceService {


	@Override
	public CustDeviceEntity getCustDevice(Long custId) {
		List<CustDeviceEntity> deviceList = baseMapper.selectList(Wrappers.<CustDeviceEntity>lambdaQuery()
			.eq(CustDeviceEntity::getCustId, custId).eq(CustDeviceEntity::getStatus, 1)
			.eq(CustDeviceEntity::getIsDeleted, 0).orderByDesc(CustDeviceEntity::getUpdateTime));
		if (CollectionUtils.isNotEmpty(deviceList)) {
			return deviceList.get(0);
		}
		return new CustDeviceEntity();
	}
}
