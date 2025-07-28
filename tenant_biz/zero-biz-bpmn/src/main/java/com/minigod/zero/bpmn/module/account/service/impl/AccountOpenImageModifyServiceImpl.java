package com.minigod.zero.bpmn.module.account.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenImageModifyEntity;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenImageModifyService;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenImageModifyMapper;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenImageModifyVO;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.tool.utils.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【customer_account_open_image_modify(客户开户申请图片信息表)】的数据库操作Service实现
 * @createDate 2024-08-13 13:46:02
 */
@Service
public class AccountOpenImageModifyServiceImpl extends BaseServiceImpl<AccountOpenImageModifyMapper, AccountOpenImageModifyEntity> implements IAccountOpenImageModifyService {

	@Override
	public AccountOpenImageModifyEntity selectByApplicationId(String applicationId, String type) {
		LambdaQueryWrapper<AccountOpenImageModifyEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(AccountOpenImageModifyEntity::getApplicationId, applicationId);
		queryWrapper.eq(AccountOpenImageModifyEntity::getImageLocationType, type);
		queryWrapper.last("limit 1");
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public List<AccountOpenImageModifyVO> getOpenImageModifyByApplyId(Long applyId) {
		LambdaQueryWrapper<AccountOpenImageModifyEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(AccountOpenImageModifyEntity::getApplyId, applyId);
		List<AccountOpenImageModifyEntity> openImageModifyList = list(queryWrapper);
		if (CollUtil.isEmpty(openImageModifyList)) {
			return Collections.emptyList();
		}
		return openImageModifyList.stream().map(openImageModify -> BeanUtil.copyProperties(openImageModify, AccountOpenImageModifyVO.class)).collect(Collectors.toList());
	}
}
