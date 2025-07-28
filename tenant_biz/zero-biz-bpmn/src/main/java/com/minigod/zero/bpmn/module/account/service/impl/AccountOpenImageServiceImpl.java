package com.minigod.zero.bpmn.module.account.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.minigod.zero.bpmn.module.account.api.ImageInfo;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenImageEntity;
import com.minigod.zero.bpmn.module.account.enums.ImageDescEnum;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenImageMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenImageService;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenImageVO;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.resource.feign.IOssClient;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *  服务实现类
 *
 * @author Chill
 */
@Slf4j
@Service
@AllArgsConstructor
public class AccountOpenImageServiceImpl extends BaseServiceImpl<AccountOpenImageMapper, AccountOpenImageEntity> implements IAccountOpenImageService {
    private final IOssClient iOssClient;
    @Override
    public List<AccountOpenImageVO> queryListByApplicationId(String applicationId, Integer imageLocation) {
        return baseMapper.queryListByApplicationId(applicationId, imageLocation, null);
    }

	@Override
	public List<AccountOpenImageVO> queryListByApplicationId(String applicationId) {
		LambdaQueryWrapper<AccountOpenImageEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(AccountOpenImageEntity::getApplicationId,applicationId);
		return baseMapper.selectList(queryWrapper).stream().map(entity -> {
			AccountOpenImageVO vo = new AccountOpenImageVO();
			BeanUtil.copyProperties(entity,vo);
			return vo;
		}).collect(Collectors.toList());
	}

	@Override
    public AccountOpenImageVO queryOneByApplicationId(String applicationId, Integer imageLocation, Integer imageLocationType) {
        return baseMapper.queryOneByApplicationId(applicationId,imageLocation,imageLocationType);
    }

    @Override
    public Boolean uploadImageInfoList(List<ImageInfo> imageInfos, String applicationId,String tenantId) {
        try {
            List<AccountOpenImageEntity> customerAccountOpenImages = new ArrayList<>(imageInfos.size());
            for (ImageInfo image : imageInfos) {
                String suffix = StringUtils.substring(image.getImageUrl(), image.getImageUrl().lastIndexOf("."), image.getImageUrl().length());
                AccountOpenImageEntity customerAccountOpenImage = new AccountOpenImageEntity();
                customerAccountOpenImage.setApplicationId(applicationId);
                customerAccountOpenImage.setImageLocation(image.getImageLocation());
                customerAccountOpenImage.setImageLocationType(image.getImageLocationType());
                customerAccountOpenImage.setExtName(suffix);
                customerAccountOpenImage.setStoragePath(image.getImageUrl());
                customerAccountOpenImage.setFileStorageName("");
                customerAccountOpenImage.setFileName(ImageDescEnum.find(image.getImageLocation(),image.getImageLocationType()).getFileName());
                customerAccountOpenImage.setRemark(ImageDescEnum.find(image.getImageLocation(),image.getImageLocationType()).getFileName());
                customerAccountOpenImage.setCreateTime(new Date());
                customerAccountOpenImage.setTenantId(tenantId);
                customerAccountOpenImages.add(customerAccountOpenImage);
            }
            if (imageInfos.size() > 0) {
                for (AccountOpenImageEntity entity : customerAccountOpenImages){
                    baseMapper.insert(entity);
                }
            }
        } catch (Exception e) {
            log.error("开户同步图片异常", e);
            throw new ServiceException("同步图片异常" + e.getMessage());
        }
        return true;
    }

    @Override
    public void deleteByApplicationId(String applicationId) {
        baseMapper.deleteByApplicationId(applicationId);
    }
}
