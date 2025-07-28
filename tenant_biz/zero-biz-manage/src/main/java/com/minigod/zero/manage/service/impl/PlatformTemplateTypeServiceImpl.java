package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.manage.vo.MsgTemplateVO;
import com.minigod.zero.manage.mapper.PlatformCommonTemplateMapper;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.manage.mapper.PlatformTemplateTypeMapper;
import com.minigod.zero.manage.service.IPlatformTemplateTypeService;
import com.minigod.zero.platform.entity.PlatformTemplateTypeEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;

/**
 * 模块类型信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
@Service
@RequiredArgsConstructor
public class PlatformTemplateTypeServiceImpl extends BaseServiceImpl<PlatformTemplateTypeMapper, PlatformTemplateTypeEntity> implements IPlatformTemplateTypeService {

	private final PlatformTemplateTypeMapper platformTemplateTypeMapper;
	private final PlatformCommonTemplateMapper platformCommonTemplateMapper;

//	@Override
//	public IPage<PlatformTemplateTypeVO> selectPlatformTemplateTypePage(IPage<PlatformTemplateTypeVO> page, PlatformTemplateTypeVO platformTemplateType) {
//		return page.setRecords(baseMapper.selectPlatformTemplateTypePage(page, platformTemplateType));
//	}

	@Override
	public List<PlatformTemplateTypeEntity> findInformTempTypeList(int busType, int parentId) {
		LambdaQueryWrapper<PlatformTemplateTypeEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(PlatformTemplateTypeEntity::getBusType, busType)
			.eq(PlatformTemplateTypeEntity::getParentId, parentId)
			.eq(BaseEntity::getStatus, 1)
			.eq(BaseEntity::getIsDeleted, 0);
		return baseMapper.selectList(wrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R addTypes(int busType, String typeStr) {
		R rt = R.success();
		if (StringUtils.isBlank(typeStr)) {
			return R.fail(ResultCode.PARAMETER_ERROR);
		}
		String[] tempNames = typeStr.split(";");
		for (int i = 0; i < tempNames.length; i++) {
			String tempName = tempNames[i];
			tempName = tempName.trim();

			List<PlatformTemplateTypeEntity> byTempName = findByTempName(tempName);
			if (CollectionUtil.isNotEmpty(byTempName)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return R.fail("操作失败：模板名称已存在");
			}

			Date now = new Date();
			Long userId = AuthUtil.getUserId();
			PlatformTemplateTypeEntity typeEntity = new PlatformTemplateTypeEntity();
			typeEntity.setBusType(busType);
			typeEntity.setTempName(tempName);
			typeEntity.setParentId(0L);
			typeEntity.setCreateUser(userId);
			typeEntity.setCreateTime(now);
			typeEntity.setCreateUser(userId);
			typeEntity.setUpdateTime(now);
			typeEntity.setStatus(1);
			typeEntity.setIsDeleted(0);
			baseMapper.insert(typeEntity);
		}
		return rt;
	}

	@Override
	public List<PlatformTemplateTypeEntity> findByTempName(String tempName) {
		LambdaQueryWrapper<PlatformTemplateTypeEntity> wrapper = new LambdaQueryWrapper();
		wrapper.eq(PlatformTemplateTypeEntity::getTempName, tempName)
			.eq(BaseEntity::getStatus, 1)
			.eq(BaseEntity::getIsDeleted, 0);
		return baseMapper.selectList(wrapper);
	}

	@Override
	public MsgTemplateVO findTemplate(Long id) {
		return baseMapper.findTemplate(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteTemplate(Long id) {
//		LambdaQueryWrapper<PlatformTemplateTypeEntity> templateTypeWrapper = new LambdaQueryWrapper();
//		templateTypeWrapper.eq(BaseEntity::getId, id).eq(BaseEntity::getStatus, 1).eq(BaseEntity::getIsDeleted, 0);
//		PlatformTemplateTypeEntity platformTemplateType = baseMapper.selectOne(templateTypeWrapper);
//		if (platformTemplateType != null) {
//			platformTemplateType.setStatus(0);
//			platformTemplateType.setIsDeleted(1);
//			platformTemplateType.setUpdateTime(new Date());
//			platformTemplateType.setUpdateUser(AuthUtil.getUserId());
//			platformTemplateTypeMapper.update(platformTemplateType);
//
//			PlatformCommonTemplateEntity commonTemplate = null;
//			LambdaQueryWrapper<PlatformCommonTemplateEntity> commonTemplateWrapper = new LambdaQueryWrapper();
//			commonTemplateWrapper.eq(PlatformCommonTemplateEntity::getBusId, id).eq(BaseEntity::getStatus, 1).eq(BaseEntity::getIsDeleted, 0);
//			List<PlatformCommonTemplateEntity> commonTemplateList = platformCommonTemplateMapper.selectList(commonTemplateWrapper);
//			if (CollectionUtil.isNotEmpty(commonTemplateList)) {
//				commonTemplate = commonTemplateList.get(0);
//			}
//			if (commonTemplate != null) {
//				commonTemplate.setStatus(0);
//				commonTemplate.setIsDeleted(1);
//				commonTemplate.setUpdateTime(new Date());
//				commonTemplate.setUpdateUser(AuthUtil.getUserId());
//			}
//			platformCommonTemplateMapper.update(commonTemplate);
//		}
	}

}
