package com.minigod.zero.cust.feign;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.cust.feign.ICustDeviceClient;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.cust.entity.CustDeviceEntity;
import com.minigod.zero.cust.feign.ICustDeviceClient;
import com.minigod.zero.cust.service.ICustDeviceService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户设备信息 Feign实现类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@RestController
@AllArgsConstructor
public class CustDeviceClient implements ICustDeviceClient {

    private final ICustDeviceService CustDeviceService;

	@Override
	public List<CustDeviceEntity> getDeviceList(List<Long> custIds) {
		if (CollectionUtil.isEmpty(custIds)) {
    		return new ArrayList<>();
		}
		return CustDeviceService.getDeviceList(custIds);
	}

	@Override
	public CustDeviceEntity getCustDevice(Long custId) {
		return CustDeviceService.getCustDevice(custId);
	}

	@Override
	public Long getCustIdByDeviceCode(String deviceCode) {
		if (StringUtils.isBlank(deviceCode)) {
			return null;
		}
		LambdaQueryWrapper<CustDeviceEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CustDeviceEntity::getDeviceCode, deviceCode)
			.eq(CustDeviceEntity::getIsDeleted, 0)
			.eq(BaseEntity::getStatus, 1)
			.select(CustDeviceEntity::getCustId);
		List<CustDeviceEntity> list = CustDeviceService.getBaseMapper().selectList(wrapper);
		if (CollectionUtil.isEmpty(list)) {
			return null;
		}
		return list.get(0).getCustId();
	}

	@Override
	public List<Long> allCustId() {
		LambdaQueryWrapper<CustDeviceEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.isNotNull(CustDeviceEntity::getCustId)
			.eq(CustDeviceEntity::getIsDeleted, 0)
			.select(CustDeviceEntity::getCustId);
		List<CustDeviceEntity> list = CustDeviceService.getBaseMapper().selectList(wrapper);
		return list.stream().map(o -> o.getCustId()).collect(Collectors.toList());
	}

	@Override
	public CustDeviceEntity getByDeviceCode(String deviceCode) {
		return CustDeviceService.getByDeviceCode(deviceCode);
	}

	@Override
	public List<CustDeviceEntity> getAllDeviceLangList(List<Long> custIds) {
		return CustDeviceService.getAllDeviceLangList(custIds);
	}
}
