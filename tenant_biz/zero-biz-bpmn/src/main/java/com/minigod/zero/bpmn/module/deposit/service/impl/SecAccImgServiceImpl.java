package com.minigod.zero.bpmn.module.deposit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.minigod.zero.biz.common.utils.FileUtil;
import com.minigod.zero.bpmn.module.constant.ErrorMessageConstant;
import com.minigod.zero.bpmn.module.deposit.bo.SecImgBo;
import com.minigod.zero.bpmn.module.deposit.entity.SecAccImgEntity;
import com.minigod.zero.bpmn.module.deposit.vo.SecAccImgVO;
import com.minigod.zero.bpmn.module.deposit.mapper.SecAccImgMapper;
import com.minigod.zero.bpmn.module.deposit.service.ISecAccImgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpmn.module.deposit.vo.SecImgRespVo;
import com.minigod.zero.bpmn.utils.ImageUtils;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.resource.feign.IOssClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 存取资金图片表 服务实现类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
@Service
public class SecAccImgServiceImpl extends ServiceImpl<SecAccImgMapper, SecAccImgEntity> implements ISecAccImgService {
    @Autowired
    private IOssClient ossClient;

    @Override
    public IPage<SecAccImgVO> selectSecAccImgPage(IPage<SecAccImgVO> page, SecAccImgVO secAccImgVO) {
        return page.setRecords(baseMapper.selectSecAccImgPage(page, secAccImgVO));
    }

    @Override
    public SecImgRespVo saveSecAccImg(SecImgBo params) {
        Long custId = AuthUtil.getTenantCustId();
        String tenantId = AuthUtil.getTenantId();
        byte[] b = ImageUtils.base64StringToImage(params.getImgBase64());
        String fileName = tenantId + "_" + custId + "_" + params.getType() + "_" + System.currentTimeMillis() + ".jpg";
        MultipartFile file = FileUtil.getMultipartFile(fileName, b);
        R<ZeroFile> ossResp = ossClient.uploadMinioFile(file, file.getOriginalFilename());
        if (null == ossResp || null == ossResp.getData() || StringUtils.isBlank(ossResp.getData().getLink())) {
			throw new ServiceException(I18nUtil.getMessage(ErrorMessageConstant.ERROR_SERVER_BUSY_NOTICE));
        }
        ZeroFile zeroFile = ossResp.getData();
        SecAccImgEntity entity = new SecAccImgEntity();
        entity.setErrorStatus(false);
        entity.setImageLocationType(params.getType());
        entity.setUserId(custId);
        entity.setTenantId(tenantId);
        entity.setAccImgPath(zeroFile.getLink());
        entity.setServiceType(params.getServiceType());
        entity.setCreateTime(new Date());
        entity.setIsFind(Boolean.TRUE);
        entity.setTransactId(params.getTransactId());
        baseMapper.insert(entity);
        return convertSecImgRespVo(entity);
    }

    /**
     * 转换 SecImgRespVo
     *
     * @param entity
     * @return
     */
    public SecImgRespVo convertSecImgRespVo(SecAccImgEntity entity) {
        SecImgRespVo secImgRespVo = new SecImgRespVo();
        secImgRespVo.setImgId(entity.getId());
        secImgRespVo.setImgPath(entity.getAccImgPath());
        secImgRespVo.setTransactId(entity.getTransactId());
        return secImgRespVo;
    }

    @Override
    public List<SecImgRespVo> findSecAccImg(SecImgBo params) {
        LambdaQueryWrapper<SecAccImgEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SecAccImgEntity::getTransactId, params.getTransactId());
        queryWrapper.eq(SecAccImgEntity::getUserId, AuthUtil.getTenantCustId());
        queryWrapper.eq(ObjectUtil.isNotEmpty(params.getServiceType()),SecAccImgEntity::getServiceType, params.getServiceType());
        queryWrapper.eq(SecAccImgEntity::getTenantId, AuthUtil.getTenantId());
        queryWrapper.eq(SecAccImgEntity::getIsFind, Boolean.TRUE);
        queryWrapper.eq(SecAccImgEntity::getErrorStatus, Boolean.FALSE);
        queryWrapper.orderByAsc(SecAccImgEntity::getCreateTime);
        return baseMapper.selectList(queryWrapper).stream().map(entity -> convertSecImgRespVo(entity)).collect(Collectors.toList());
    }

    @Override
    public List<SecAccImgEntity> findSecAccImg(Long transactId) {
        LambdaQueryWrapper<SecAccImgEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SecAccImgEntity::getTransactId, transactId);
        queryWrapper.eq(SecAccImgEntity::getIsFind, Boolean.TRUE);
        queryWrapper.eq(SecAccImgEntity::getErrorStatus, Boolean.FALSE);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public void batchUpdateImgTransactId(Long transactId, List<Long> ids) {
        if(ids!=null && ids.size()>0){
            LambdaUpdateWrapper<SecAccImgEntity> updateWrapper = new LambdaUpdateWrapper();
            updateWrapper.set(SecAccImgEntity::getTransactId,transactId);
            updateWrapper.in(SecAccImgEntity::getId,ids);
            updateWrapper.eq(SecAccImgEntity::getTenantId,AuthUtil.getTenantId());
//            updateWrapper.eq(SecAccImgEntity::getUserId,AuthUtil.getTenantCustId());
            baseMapper.update(null,updateWrapper);
        }

    }

    @Override
    public SecImgRespVo saveSecAccImg(MultipartFile multipartFile, Long custId) {
        String tenantId = AuthUtil.getTenantId();
        R<ZeroFile> ossResp = ossClient.uploadMinioFile(multipartFile, multipartFile.getOriginalFilename());
        if (null == ossResp || null == ossResp.getData() || StringUtils.isBlank(ossResp.getData().getLink())) {
            throw new ServiceException(ResultCode.H5_DISPLAY_ERROR);
        }
        ZeroFile zeroFile = ossResp.getData();
        SecAccImgEntity entity = new SecAccImgEntity();
        entity.setErrorStatus(false);
        entity.setImageLocationType(1);
        entity.setUserId(custId);
        entity.setTenantId(tenantId);
        entity.setAccImgPath(zeroFile.getLink());
        entity.setServiceType(1);
        entity.setCreateTime(new Date());
        entity.setIsFind(Boolean.TRUE);
//        entity.setTransactId(params.getTransactId());
        baseMapper.insert(entity);
        return convertSecImgRespVo(entity);
    }
}
