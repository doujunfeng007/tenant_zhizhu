package com.minigod.zero.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.minigod.zero.platform.mapper.PlatformSmsTemplateExtMapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.platform.entity.PlatformSmsTemplateExtEntity;
import com.minigod.zero.platform.service.ISmsTemplateExtService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 消息模板扩展表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-02-27
 */
@Service
public class SmsTemplateExtServiceImpl extends BaseServiceImpl<PlatformSmsTemplateExtMapper, PlatformSmsTemplateExtEntity> implements ISmsTemplateExtService {

	@Override
	public List<Map<String, Object>> findTemplateExtByCode(Integer iCode) {
		QueryWrapper<PlatformSmsTemplateExtEntity> queryWrapper=new QueryWrapper();
		// QueryWrapper<Employee> queryWrapper2=Wrappers.<Employee>query();
		queryWrapper.select("key_name","key_value").eq("code", iCode).eq("status", 1).eq("is_deleted", 0);
		return baseMapper.selectMaps(queryWrapper);
	}

//	@Override
//	public IPage<SmsTemplateExtVO> selectSmsTemplateExtPage(IPage<SmsTemplateExtVO> page, SmsTemplateExtVO smsTemplateExt) {
//		return page.setRecords(baseMapper.selectSmsTemplateExtPage(page, smsTemplateExt));
//	}


}
