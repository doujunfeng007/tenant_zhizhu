package com.minigod.zero.bpm.service.impl;

import cn.hutool.core.util.StrUtil;
import com.minigod.zero.bpm.constant.BpmConstants;
import com.minigod.zero.bpm.dto.acct.req.CommonImgDto;
import com.minigod.zero.bpm.dto.acct.resp.CommonImgRespDto;
import com.minigod.zero.bpm.entity.AcctChangeImageEntity;
import com.minigod.zero.bpm.entity.AcctOpenImageEntity;
import com.minigod.zero.bpm.entity.CashAccessImageEntity;
import com.minigod.zero.bpm.service.BpmCommonService;
import com.minigod.zero.bpm.service.IAcctChangeImageService;
import com.minigod.zero.bpm.service.IAcctOpenImageService;
import com.minigod.zero.bpm.service.ICashAccessImageService;
import com.minigod.zero.bpm.utils.ImageUtils;
import com.minigod.zero.biz.common.utils.FileUtil;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.resource.feign.IOssClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BpmCommonServiceImpl implements BpmCommonService {

	@Resource
	private IOssClient ossClient;

	@Resource
	private IAcctOpenImageService acctOpenImageService;

	@Resource
	private IAcctChangeImageService acctChangeImageService;

	@Resource
	private ICashAccessImageService cashAccessImageService;

	/**
	 * 上传图片
	 *
	 * @param dto 图片传输实体
	 * @return R<Kv>
	 */
	@Override
	public R<Kv> uploadImg(CommonImgDto dto) {
		Long startTime = System.currentTimeMillis();
		if (StrUtil.isBlank(dto.getImgBase64()) || StrUtil.isBlank(dto.getType())) {
			return R.fail(ResultCode.PARAMS_PARAMETER_ERROR);
		}
		byte[] b = ImageUtils.base64StringToImage(dto.getImgBase64());
		String fileName = AuthUtil.getCustId() + "_" + dto.getType() + "_" + System.currentTimeMillis() + ".jpg";
		MultipartFile file = FileUtil.getMultipartFile(fileName, b);
		R<ZeroFile> ossResp = ossClient.uploadMinioFile(file, file.getOriginalFilename());
		if (null == ossResp || null == ossResp.getData() || org.apache.commons.lang.StringUtils.isBlank(ossResp.getData().getLink())) {
			return R.fail(ResultCode.H5_DISPLAY_ERROR);
		}
		ZeroFile zeroFile = ossResp.getData();
		log.info("上传图片耗时：" + (System.currentTimeMillis() - startTime));
		return R.data(Kv.create().set("imgPath", zeroFile.getLink()));
	}

	@Override
	public R<List<CommonImgRespDto>> downloadImg(CommonImgDto dto) {
		if(null == dto.getIds() || 0 == dto.getIds().size() || StringUtils.isBlank(dto.getType())) return R.fail(ResultCode.PARAMS_PARAMETER_ERROR);

		String type = dto.getType().trim().toLowerCase();
		Long custId = AuthUtil.getCustId();
		if (null == custId ) return R.fail(ResultCode.NONE_DATA);

		List<CommonImgRespDto> list = new ArrayList<>();

		for(Long id : dto.getIds()) {
			try{
				String path = null;
				Long imgCustId = null;
				if(BpmConstants.DownloadImgTypeEnum.OPEN.value.equals(type)) {
					AcctOpenImageEntity entity = acctOpenImageService.getBaseMapper().selectById(id);
					if(null != entity && StringUtils.isNotBlank(entity.getImgPath())){
						imgCustId = entity.getCustId();
						path = entity.getImgPath();
					}

				} else if(BpmConstants.DownloadImgTypeEnum.CHANGE.value.equals(type)) {
					AcctChangeImageEntity entity = acctChangeImageService.getBaseMapper().selectById(id);
					if(null != entity && StringUtils.isNotBlank(entity.getImgPath())) {
						imgCustId = entity.getCustId();
						path = entity.getImgPath();
					}

				} else if(BpmConstants.DownloadImgTypeEnum.CASH.value.equals(type)) {
					CashAccessImageEntity entity = cashAccessImageService.getBaseMapper().selectById(id);
					if(null != entity && StringUtils.isNotBlank(entity.getAccImgPath())){
						imgCustId = entity.getCustId();
						path = entity.getAccImgPath();
					}

				}
				if (StringUtils.isBlank(path) || imgCustId.intValue() != custId.intValue()) continue;

				R<byte[]> resp = ossClient.downloadMinioFile(path);

				if (null == resp || null == resp.getData() || 0 == resp.getData().length) continue;

				byte[] bytes = resp.getData();
				String base64Str = ImageUtils.getImgStr(bytes);
				CommonImgRespDto imgDto = new CommonImgRespDto();
				imgDto.setId(id);
				imgDto.setImgBase64(base64Str);
				list.add(imgDto);

			}catch (Exception e) {
				log.error("downloadImg fail id:"+id,e);
			}
		}
		return R.data(list);
	}
}
