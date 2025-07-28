package com.minigod.zero.bpmn.module.pi.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.biz.common.utils.FileUtil;
import com.minigod.zero.bpmn.module.constant.PIApplicationMessageConstant;
import com.minigod.zero.bpmn.module.pi.dto.ProfessionalInvestorPIImageDTO;
import com.minigod.zero.bpmn.module.pi.entity.ProfessionalInvestorPIVoucherImageEntity;
import com.minigod.zero.bpmn.module.pi.enums.VoucherImageTypeEnum;
import com.minigod.zero.bpmn.module.pi.mapper.ProfessionalInvestorPIVoucherImageMapper;
import com.minigod.zero.bpmn.module.pi.service.IProfessionalInvestorPIVoucherImageService;
import com.minigod.zero.bpmn.module.pi.vo.ProfessionalInvestorPIVoucherImageVO;
import com.minigod.zero.bpmn.utils.DateUtils;
import com.minigod.zero.bpmn.utils.ImageUtils;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.resource.feign.IOssClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 专业投资人PI申请凭证图片信息表 服务实现类
 *
 * @author eric
 * @since 2024-05-07
 */
@Slf4j
@Service
@AllArgsConstructor
public class ProfessionalInvestorPIVoucherImageServiceImpl extends BaseServiceImpl<ProfessionalInvestorPIVoucherImageMapper,
	ProfessionalInvestorPIVoucherImageEntity> implements IProfessionalInvestorPIVoucherImageService {
	/**
	 * 凭证图片信息表
	 */
	private final ProfessionalInvestorPIVoucherImageMapper voucherImageMapper;
	/**
	 * 上传凭证到OSS
	 */
	private final IOssClient ossClient;

	/**
	 * 查询指定PI审请记录的凭证图片
	 *
	 * @param applicationId
	 * @param imageTypes
	 * @return
	 */
	@Override
	public List<ProfessionalInvestorPIVoucherImageVO> queryByApplicationId(String applicationId, List<Integer> imageTypes) {
		LambdaQueryWrapper<ProfessionalInvestorPIVoucherImageEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(ProfessionalInvestorPIVoucherImageEntity::getApplicationId, applicationId);
		queryWrapper.in(ProfessionalInvestorPIVoucherImageEntity::getImageType, imageTypes);
		return voucherImageMapper.selectList(queryWrapper).stream().map(entity -> {
			ProfessionalInvestorPIVoucherImageVO vo = new ProfessionalInvestorPIVoucherImageVO();
			BeanUtils.copyProperties(entity, vo);
			return vo;
		}).collect(Collectors.toList());
	}

	/**
	 * 上传凭证图片
	 *
	 * @param type
	 * @param file
	 * @return
	 */
	@Override
	public R<Kv> uploadImage(Integer type, MultipartFile file) {
		try {
			Long startTime = System.currentTimeMillis();
			if (type == null || file == null) {
				return R.fail(ResultCode.PARAM_MISS);
			}
			VoucherImageTypeEnum voucherImageTypeEnum = VoucherImageTypeEnum.fromType(type);
			if (voucherImageTypeEnum == null) {
				return R.fail(ResultCode.PARAMETER_DISMATCH);
			}
			String originalFileName = file.getOriginalFilename();
			String suffix = StringUtils.substring(originalFileName, originalFileName.lastIndexOf("."), originalFileName.length());
			String uuid = IdUtil.fastSimpleUUID();
			String fileName = uuid + suffix;

			String path = DateUtils.datePath() + "/" + uuid;
			R<ZeroFile> zeroFileR = ossClient.uploadMinioFile(file, path + suffix);

            if (!zeroFileR.isSuccess()) {
                throw new ServiceException(zeroFileR.getMsg());
            }
            ProfessionalInvestorPIVoucherImageEntity voucherImageEntity = new ProfessionalInvestorPIVoucherImageEntity();
            voucherImageEntity.setExtName(suffix);
            voucherImageEntity.setFileName(originalFileName);
            voucherImageEntity.setStoragePath(zeroFileR.getData().getLink());
            voucherImageEntity.setFileStorageName(fileName);
            voucherImageEntity.setClientId(AuthUtil.getTenantCustId());
            voucherImageEntity.setImageType(voucherImageTypeEnum.getType());
            this.save(voucherImageEntity);
            log.info("上传凭证(file)完成,耗时：" + (System.currentTimeMillis() - startTime) + ",凭证ID：" + voucherImageEntity.getId().toString());
            return R.data(Kv.create().set("imgPath", zeroFileR.getData().getLink()).set("imgId", voucherImageEntity.getId()));
        } catch (Exception exception) {
            log.error("上传凭证(file)异常,异常信息:", exception);
            return R.fail(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_REVOKE_UPLOAD_FILE_FAILED_NOTICE));
        }
    }

	/**
	 * 上传图片
	 *
	 * @param imageDTO 图片传输实体
	 * @return R<Kv>
	 */
	@Override
	public R<Kv> uploadImage(ProfessionalInvestorPIImageDTO imageDTO) {
		try {
			Long startTime = System.currentTimeMillis();
			if (StrUtil.isBlank(imageDTO.getImgBase64()) || imageDTO.getType() == null) {
				return R.fail(ResultCode.PARAM_MISS);
			}
			VoucherImageTypeEnum voucherImageTypeEnum = VoucherImageTypeEnum.fromType(imageDTO.getType());
			if (voucherImageTypeEnum == null) {
				return R.fail(ResultCode.PARAMETER_DISMATCH);
			}
			byte[] b = ImageUtils.base64StringToImage(imageDTO.getImgBase64());
			String fileName = AuthUtil.getTenantId() + "_" + imageDTO.getType() + "_" + System.currentTimeMillis() + ".jpg";
			MultipartFile file = FileUtil.getMultipartFile(fileName, b);
			R<ZeroFile> ossResp = ossClient.uploadMinioFile(file, file.getOriginalFilename());

			if (null == ossResp || null == ossResp.getData() || org.apache.commons.lang.StringUtils.isBlank(ossResp.getData().getLink())) {
				return R.fail(ResultCode.H5_DISPLAY_ERROR);
			}

			ZeroFile zeroFile = ossResp.getData();

            ProfessionalInvestorPIVoucherImageEntity voucherImageEntity = new ProfessionalInvestorPIVoucherImageEntity();
            voucherImageEntity.setExtName(".jpg");
            voucherImageEntity.setFileName(zeroFile.getOriginalName());
            voucherImageEntity.setStoragePath(zeroFile.getLink());
            voucherImageEntity.setFileStorageName(fileName);
            voucherImageEntity.setClientId(AuthUtil.getTenantCustId());
            voucherImageEntity.setImageType(voucherImageTypeEnum.getType());

			this.save(voucherImageEntity);
			log.info("上传凭证(Base64)完成,耗时：" + (System.currentTimeMillis() - startTime) + ",凭证ID：" + voucherImageEntity.getId().toString());
			return R.data(Kv.create().set("imgPath", zeroFile.getLink()).set("imgId", voucherImageEntity.getId()));
		} catch (Exception exception) {
			log.error("上传凭证(Base64)异常,异常信息:", exception);
			return R.fail(I18nUtil.getMessage(PIApplicationMessageConstant.PI_APPLICATION_REVOKE_UPLOAD_FILE_FAILED_NOTICE));
		}
	}

	/**
	 * 查询指定Ids的凭证图片
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public List<ProfessionalInvestorPIVoucherImageEntity> queryByIds(List<String> ids) {
		LambdaQueryWrapper<ProfessionalInvestorPIVoucherImageEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(ProfessionalInvestorPIVoucherImageEntity::getId, ids);
		return voucherImageMapper.selectList(queryWrapper);
	}
}
