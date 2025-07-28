package com.minigod.zero.bpmn.module.common.service.impl;

import com.minigod.zero.bpmn.module.common.vo.address.Province;
import com.minigod.zero.bpmn.module.common.bo.QueryAddressCode;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.system.cache.LanguageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpmn.module.common.mapper.AddressMapper;
import com.minigod.zero.bpmn.module.common.entity.Address;
import com.minigod.zero.bpmn.module.common.service.AddressService;

/**
 * @ClassName: AddressServiceImpl
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/3/9
 * @Version 1.0
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

	@Autowired
	ZeroRedis zeroRedis;

	private final static String KEY = "ADDRESS";
	private final static String NAME_KEY = "ADDRESS_NAME";

	@Override
	public int batchInsert(List<Address> list) {
		return baseMapper.batchInsert(list);
	}


	private String getAddressKey(QueryAddressCode queryAddressCode, String lang) {
		StringBuilder key = new StringBuilder(KEY);
		if (StringUtil.isNotBlank(queryAddressCode.getCountryCode())) {
			key.append(":");
			key.append(queryAddressCode.getCountryCode());
		}
		if (StringUtil.isNotBlank(queryAddressCode.getProvinceCode())) {
			key.append(":");
			key.append(queryAddressCode.getProvinceCode());
		}
		if (StringUtil.isNotBlank(queryAddressCode.getCityCode())) {
			key.append(":");
			key.append(queryAddressCode.getCityCode());
		}
		if (StringUtil.isNotBlank(lang)) {
			key.append(":");
			key.append(lang);
		}
		return key.toString();
	}

	private String getAddressNameKey(String value) {
		StringBuilder key = new StringBuilder(NAME_KEY);
		if (StringUtil.isNotBlank(value)) {
			key.append(":");
			key.append(value);
		}
		return key.toString();
	}

	@Override
	public List<Province> getAddressList(QueryAddressCode queryAddressCode) {
		String language = LanguageUtils.getLanguage();
		List<Province> list = zeroRedis.get(getAddressKey(queryAddressCode, language));
		if (ObjectUtil.isEmpty(list)) {
			list = baseMapper.getAddressList(queryAddressCode, language);
			if (list.size() > 0) {
				zeroRedis.set(getAddressKey(queryAddressCode, language), list);
			}
		}
		return list;
	}

	@Override
	public String getAddressName(String value) {
		Long id = null;
		try {
			if (!StringUtils.isNotBlank(value)) {
				return value;
			}
			id = Long.valueOf(value);
			Address address = zeroRedis.get(getAddressNameKey(value));
			if (ObjectUtil.isEmpty(address)) {
				address = baseMapper.selectById(id);
				if (ObjectUtil.isNotEmpty(address)) {
					zeroRedis.set(getAddressNameKey(value), address);
				}
			}
			if (ObjectUtil.isNotEmpty(address)) {
				return StringUtils.isNotBlank(address.getName()) ? address.getName() : "";
			} else {
				return value;
			}
		} catch (NumberFormatException e) {
			return value;
		}
	}

	@Override
	public String getAddressCode(String value) {
		Long id = null;
		try {
			if (!StringUtils.isNotBlank(value)) {
				return value;
			}
			id = Long.valueOf(value);
			Address address = zeroRedis.get(getAddressNameKey(value));
			if (ObjectUtil.isEmpty(address)) {
				address = baseMapper.selectById(id);
				if (ObjectUtil.isNotEmpty(address)) {
					zeroRedis.set(getAddressNameKey(value), address);
				}
			}
			if (ObjectUtil.isNotEmpty(address)) {
				return StringUtils.isNotBlank(address.getCode()) ? address.getCode() : "";
			} else {
				return value;
			}
		} catch (NumberFormatException e) {
			return value;
		}
	}

	@Override
	public void resetCache() {
		Collection<String> listKey = zeroRedis.keys("*" + KEY + "*");
		zeroRedis.del(listKey);
	}
}
