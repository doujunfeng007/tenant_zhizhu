package com.minigod.zero.bpmn.module.deposit.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.biz.common.utils.FileUtil;
import com.minigod.zero.bpmn.module.constant.WithdrawalsFundMessageConstant;
import com.minigod.zero.bpmn.module.deposit.dto.BankCardImageDTO;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardImageEntity;
import com.minigod.zero.bpmn.module.deposit.mapper.BankCardImageMapper;
import com.minigod.zero.bpmn.module.deposit.service.BankCardImageService;
import com.minigod.zero.bpmn.module.deposit.vo.BankCardImageVO;
import com.minigod.zero.bpmn.module.pi.enums.BankCardImageTypeEnum;
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
 * 银行卡凭证图片表 服务实现类
 */
@Slf4j
@Service
@AllArgsConstructor
public class BankCardImageServiceImpl extends BaseServiceImpl<BankCardImageMapper, BankCardImageEntity> implements BankCardImageService {

	private final BankCardImageMapper bankCardImageMapper;
	/**
	 * 上传凭证到OSS
	 */
	private final IOssClient ossClient;

	/**
	 * 批量插入凭证图片
	 *
	 * @param list
	 * @return
	 */
	@Override
	public boolean batchInsert(List<BankCardImageEntity> list) {
		return this.saveBatch(list);
	}

	/**
	 * 根据id集合查询凭证图片
	 *
	 * @param ids
	 * @return
	 */
	@Override
	public List<BankCardImageEntity> queryByIds(List<String> ids) {
		LambdaQueryWrapper<BankCardImageEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(BankCardImageEntity::getId, ids);
		return baseMapper.selectList(queryWrapper);
	}

	/**
	 * 查询指定PI审请记录的凭证图片
	 *
	 * @param applicationId
	 * @return
	 */
	@Override
	public List<BankCardImageVO> queryByApplicationId(String applicationId) {
		LambdaQueryWrapper<BankCardImageEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(BankCardImageEntity::getApplicationId, applicationId);
		return bankCardImageMapper.selectList(queryWrapper).stream().map(entity -> {
			BankCardImageVO vo = new BankCardImageVO();
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
			BankCardImageTypeEnum bankCardImageTypeEnum = BankCardImageTypeEnum.fromType(type);
			if (bankCardImageTypeEnum == null) {
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
			BankCardImageEntity bankCardImageEntity = new BankCardImageEntity();
			bankCardImageEntity.setExtName(suffix);
			bankCardImageEntity.setFileName(originalFileName);
			bankCardImageEntity.setStoragePath(zeroFileR.getData().getLink());
			bankCardImageEntity.setFileStorageName(fileName);
			bankCardImageEntity.setImageType(bankCardImageTypeEnum.getType());
			this.save(bankCardImageEntity);
			log.info("上传凭证(file)完成,耗时：" + (System.currentTimeMillis() - startTime) + ",凭证ID：" + bankCardImageEntity.getId().toString());
			return R.data(Kv.create().set("imgPath", zeroFileR.getData().getLink()).set("imgId", bankCardImageEntity.getId()));
		} catch (Exception exception) {
			log.error("上传凭证(file)异常,异常信息:", exception);
			return R.fail(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_UPLOAD_VOUCHER_FAILED_NOTICE));
		}
	}

	/**
	 * 上传图片
	 *
	 * @param imageDTO 图片传输实体
	 * @return R<Kv>
	 */
	@Override
	public R<Kv> uploadImage(BankCardImageDTO imageDTO) {
		try {
			Long startTime = System.currentTimeMillis();
			if (StrUtil.isBlank(imageDTO.getImgBase64()) || imageDTO.getType() == null) {
				return R.fail(ResultCode.PARAM_MISS);
			}
			BankCardImageTypeEnum bankCardImageTypeEnum = BankCardImageTypeEnum.fromType(imageDTO.getType());
			if (bankCardImageTypeEnum == null) {
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

			BankCardImageEntity bankCardImageEntity = new BankCardImageEntity();
			bankCardImageEntity.setExtName(".jpg");
			bankCardImageEntity.setFileName(zeroFile.getOriginalName());
			bankCardImageEntity.setStoragePath(zeroFile.getLink());
			bankCardImageEntity.setFileStorageName(fileName);
			bankCardImageEntity.setImageType(bankCardImageTypeEnum.getType());

			this.save(bankCardImageEntity);
			log.info("上传凭证(Base64)完成,耗时：" + (System.currentTimeMillis() - startTime) + ",凭证ID：" + bankCardImageEntity.getId().toString());
			return R.data(Kv.create().set("imgPath", zeroFile.getLink()).set("imgId", bankCardImageEntity.getId()));
		} catch (Exception exception) {
			log.error("上传凭证(Base64)异常,异常信息:", exception);
			return R.fail(I18nUtil.getMessage(WithdrawalsFundMessageConstant.WITHDRAWALS_FUND_UPLOAD_VOUCHER_FAILED_NOTICE));
		}
	}
}
