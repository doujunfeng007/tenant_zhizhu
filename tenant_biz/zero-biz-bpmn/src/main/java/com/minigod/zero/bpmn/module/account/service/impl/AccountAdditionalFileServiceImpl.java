package com.minigod.zero.bpmn.module.account.service.impl;

import cn.hutool.core.util.IdUtil;
import com.minigod.zero.biz.common.utils.FileOperaterUtil;
import com.minigod.zero.biz.common.utils.FileUtil;
import com.minigod.zero.bpmn.module.account.bo.OpenSignImgBo;
import com.minigod.zero.bpmn.module.account.entity.AccountAdditionalDetailEntity;
import com.minigod.zero.bpmn.module.account.enums.AccountPdfEnum;
import com.minigod.zero.bpmn.module.account.service.IAccountAdditionalDetailService;
import com.minigod.zero.bpmn.module.account.vo.AccountAdditionalFileVO;
import com.minigod.zero.bpmn.module.account.vo.OpenImgVo;
import com.minigod.zero.bpmn.module.constant.ErrorMessageConstant;
import com.minigod.zero.bpmn.module.constant.ModifyOpenAccountMessageConstant;
import com.minigod.zero.bpmn.utils.ApplicationIdUtil;
import com.minigod.zero.bpmn.utils.DateUtils;
import com.minigod.zero.bpmn.utils.FileUtils;
import com.minigod.zero.bpmn.utils.ImageUtils;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.bpmn.module.account.entity.AccountAdditionalFileEntity;
import com.minigod.zero.bpmn.module.account.mapper.AccountAdditionalFileMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountAdditionalFileService;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.resource.feign.IOssClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 服务实现类
 *
 * @author Chill
 */
@AllArgsConstructor
@Service
@Slf4j
public class AccountAdditionalFileServiceImpl extends BaseServiceImpl<AccountAdditionalFileMapper, AccountAdditionalFileEntity> implements IAccountAdditionalFileService {
    private final IOssClient ossClient;
    private final IAccountAdditionalDetailService iAccountAdditionalDetailService;

    @Override
    public List<AccountAdditionalFileVO> queryListByApplicationId(String applicationId, Integer type, Long userId) {
        List<AccountAdditionalFileVO> list = baseMapper.queryListByApplicationId(applicationId, type);
        list.stream().forEach(s -> {
            if (userId != null && userId.equals(s.getCreateUser())) {
                s.setDelPermissions(1);
            } else {
                s.setDelPermissions(0);
            }
        });
        return list;
    }

    @Override
    public String uploadAdditionalFile(String applicationId, Integer type, Integer fileType, String remark, MultipartFile file) {
        String originalfileName = file.getOriginalFilename();
        String suffix = StringUtils.substring(originalfileName, originalfileName.lastIndexOf("."), originalfileName.length());
        String uuid = IdUtil.fastSimpleUUID();
        String fileName = uuid + suffix;

        String path = DateUtils.datePath() + "/" + uuid;
        R<ZeroFile> zeroFileR = ossClient.uploadMinioFile(file, path + suffix);
        if (!zeroFileR.isSuccess()) {
            throw new ServiceException(zeroFileR.getMsg());
        }
        String additionalId = ApplicationIdUtil.generateCommonId(AuthUtil.getTenantId());
        // 保存文件信息
        AccountAdditionalDetailEntity openAccountAdditionalDetail = new AccountAdditionalDetailEntity();
        openAccountAdditionalDetail.setAdditionalId(additionalId);
        openAccountAdditionalDetail.setRemark(remark);
        openAccountAdditionalDetail.setCreateTime(new Date());
        openAccountAdditionalDetail.setCreateUser(AuthUtil.getUserId());
        openAccountAdditionalDetail.setApplicationId(applicationId);
		openAccountAdditionalDetail.setTenantId(AuthUtil.getTenantId());
        iAccountAdditionalDetailService.save(openAccountAdditionalDetail);

        AccountAdditionalFileEntity openAccountAdditionalFile = new AccountAdditionalFileEntity();
        openAccountAdditionalFile.setAdditionalId(additionalId);
        openAccountAdditionalFile.setApplicationId(applicationId);
        openAccountAdditionalFile.setFileExtName(suffix);
        openAccountAdditionalFile.setFileName(originalfileName);
        openAccountAdditionalFile.setFilePath(zeroFileR.getData().getLink());
        openAccountAdditionalFile.setFileStorageName(fileName);
        openAccountAdditionalFile.setFileType(fileType);
        openAccountAdditionalFile.setType(type);
		openAccountAdditionalDetail.setTenantId(AuthUtil.getTenantId());
        openAccountAdditionalFile.setCreateTime(new Date());
        openAccountAdditionalFile.setCreateUser(AuthUtil.getUserId());
        baseMapper.insert(openAccountAdditionalFile);


        return additionalId;
    }


    @Override
    public void deleteByApplicationAndTypeAndFileType(String applicationId,Integer type, Integer fileType) {
        baseMapper.deleteByApplicationAndTypeAndFileType(applicationId,type,fileType);
    }

    @Override
    public void deleteByApplicationAndType(String applicationId, Integer type) {
        baseMapper.deleteByApplicationAndType(applicationId,type);
    }

	@Override
	public List<AccountAdditionalFileEntity> queryListByType(Integer type) {
		List<AccountAdditionalFileEntity> accountAdditionalFileEntities = baseMapper.queryListByType(AuthUtil.getUserId(), type);

		/*for (AccountAdditionalFileEntity file : accountAdditionalFileEntities) {
			if (file.getFileType() == AccountPdfEnum.ACCOUNT_OPEN_REPORT_USER_W8_REPORT.getValue()) {
                try {
					String suffix = ".png";
					String uuid = IdUtil.fastSimpleUUID();
					String path = DateUtils.datePath() + "/" + uuid;
					MultipartFile fileItem = FileOperaterUtil.createFileItem(file.getFilePath(), "w8_report.pdf");
					MultipartFile imageFile = convertPdfMultipartFileToMergedImage(fileItem);
					R<ZeroFile> zeroFileR = ossClient.uploadMinioFile(imageFile, path + suffix);
					if (!zeroFileR.isSuccess()) {
						throw new ServiceException(zeroFileR.getMsg());
					}
					file.setFilePath(zeroFileR.getData().getLink());
					log.info("img文件上传成功，文件路径：{}", zeroFileR.getData().getLink());
				} catch (IOException e) {
                    log.error("转换pdf文件为图片失败", e);
                }
            }
		}*/
		return accountAdditionalFileEntities;
	}
	public static MultipartFile convertPdfMultipartFileToSingleImage(MultipartFile pdfFile) throws IOException {
		try (PDDocument document = PDDocument.load(pdfFile.getInputStream())) {
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300); // 渲染第一页，300 DPI

			// 将生成的图像转换为 MultipartFile
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bim, "png", baos);
			byte[] imageBytes = baos.toByteArray();

			String imageFileName = pdfFile.getOriginalFilename().replace(".pdf", "_page1.png");
			return new MockMultipartFile("file", imageFileName, "image/png", imageBytes);
		}
	}

	public static MultipartFile convertPdfMultipartFileToMergedImage(MultipartFile pdfFile) throws IOException {
		try (PDDocument document = PDDocument.load(pdfFile.getInputStream())) {
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			int pageCount = document.getNumberOfPages();

			// 创建合并后的图像，假设每一页的大小相同
			BufferedImage combinedImage = new BufferedImage(595, 842 * pageCount, BufferedImage.TYPE_INT_RGB); // A4 纸张的大小
			Graphics2D g = combinedImage.createGraphics();

			// 渲染每一页并绘制到合并的图像上
			for (int page = 0; page < pageCount; page++) {
				BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300); // 渲染每一页的图像
				g.drawImage(bim, 0, page * 842, null); // 按顺序绘制每一页到合并图像中
			}
			g.dispose(); // 释放资源

			// 将合成的图像转换为 MultipartFile
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(combinedImage, "png", baos);
			byte[] imageBytes = baos.toByteArray();

			String imageFileName = pdfFile.getOriginalFilename().replace(".pdf", "_merged.png");
			return new MockMultipartFile("file", imageFileName, "image/png", imageBytes);
		}
	}
}
