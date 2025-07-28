package com.minigod.zero.bpmn.module.margincredit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.bpmn.module.common.entity.FileUploadInfoEntity;
import com.minigod.zero.bpmn.module.common.enums.UploadBusinessType;
import com.minigod.zero.bpmn.module.common.service.IFileUploadInfoService;
import com.minigod.zero.bpmn.module.margincredit.entity.IncreaseCreditLimitApplicationEntity;
import com.minigod.zero.bpmn.module.margincredit.entity.IncreaseCreditLimitEntity;
import com.minigod.zero.bpmn.module.margincredit.mapper.IncreaseCreditLimitMapper;
import com.minigod.zero.bpmn.module.margincredit.service.IIncreaseCreditLimitApplicationService;
import com.minigod.zero.bpmn.module.margincredit.service.IIncreaseCreditLimitService;
import com.minigod.zero.bpmn.module.margincredit.vo.IncreaseCreditLimitVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chen
 * @ClassName IncreaseCreditLimitServiceImpl.java
 * @Description TODO
 * @createTime 2024年03月09日 17:24:00
 */
@Service
public class IncreaseCreditLimitServiceImpl extends BaseServiceImpl<IncreaseCreditLimitMapper, IncreaseCreditLimitEntity> implements IIncreaseCreditLimitService {

	@Autowired
	private IFileUploadInfoService fileUploadInfoService;

	@Autowired
	@Lazy
	private IIncreaseCreditLimitApplicationService increaseCreditLimitApplicationService;

	@Override
	public IncreaseCreditLimitVO queryDetailByApplication(String applicationId) {

		IncreaseCreditLimitEntity detail = queryByApplicationId(applicationId);
		IncreaseCreditLimitVO vo = new IncreaseCreditLimitVO();
		BeanUtil.copy(detail, vo);
		LambdaQueryWrapper<IncreaseCreditLimitApplicationEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(IncreaseCreditLimitApplicationEntity::getApplicationId, applicationId);
		IncreaseCreditLimitApplicationEntity application = increaseCreditLimitApplicationService.getBaseMapper().selectOne(wrapper);
		vo.setApplicationEntity(application);
		List<FileUploadInfoEntity> files = fileUploadInfoService.selectListByBusinessType(detail.getId(), UploadBusinessType.INCREASE_CREDIT_CERTIFICATE.getBusinessType());
		vo.setFileList(files);
		return vo;
	}

	@Override
	public IncreaseCreditLimitEntity queryByApplicationId(String applicationId) {
		LambdaQueryWrapper<IncreaseCreditLimitEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(IncreaseCreditLimitEntity::getApplicationId, applicationId);
		IncreaseCreditLimitEntity detail = baseMapper.selectOne(queryWrapper);
		return detail;
	}
}
