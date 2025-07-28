package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.manage.entity.SensitiveWordLogEntity;
import com.minigod.zero.manage.vo.request.SensitiveWordLogSaveVo;
import com.minigod.zero.manage.service.ISensitiveWordLogService;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.manage.mapper.SensitiveWordLogMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 掌上智珠
 * @since 2023/7/25 9:54
 */
@Service
public class SensitiveWordLogServiceImpl extends ServiceImpl<SensitiveWordLogMapper, SensitiveWordLogEntity> implements ISensitiveWordLogService {

	@Override
	public boolean saveBatch(ArrayList<SensitiveWordLogSaveVo> list) {
		List<SensitiveWordLogEntity> saveList = list.stream().map(vo -> {
			SensitiveWordLogEntity sensitiveWordLogEntity = new SensitiveWordLogEntity();
			BeanUtil.copyNonNull(vo, sensitiveWordLogEntity);
			sensitiveWordLogEntity.setStatus(1);
			return sensitiveWordLogEntity;
		}).collect(Collectors.toList());
		return this.saveBatch(saveList, DEFAULT_BATCH_SIZE);
	}
}
