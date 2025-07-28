package com.minigod.zero.bpmn.module.account.controller;

import com.minigod.zero.biz.common.vo.CommonReqVO;
import com.minigod.zero.bpmn.module.account.bo.OpenAttachmentBo;
import com.minigod.zero.bpmn.module.account.bo.OpenImgBo;
import com.minigod.zero.bpmn.module.account.bo.OpenInfoBo;
import com.minigod.zero.bpmn.module.account.service.IOpenAccountOfflineService;
import com.minigod.zero.bpmn.module.account.vo.OpenAttachmentVo;
import com.minigod.zero.bpmn.module.account.vo.OpenImgVo;
import com.minigod.zero.bpmn.utils.FileUtils;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.annotation.ApiLog;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.feign.IDictBizClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 线下开户资料API服务
 *
 * @author eric
 * @since 2024-06-12 17:45:15
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/account/offline-account-open-info")
@Api(value = "线下开户资料API服务", tags = "线下开户资料API服务")
public class OfflineAccountOpenInfoController {
	private final IDictBizClient iDictBizClient;
	private final IOpenAccountOfflineService iOpenAccountOfflineService;

	/**
	 * 提交开户
	 */
	@ApiLog("提交开户")
	@ApiOperation("提交开户")
	@PostMapping("/submit_open_info")
	public R submitOpenInfo(@RequestBody CommonReqVO<OpenInfoBo> bo) {
		iOpenAccountOfflineService.submitOpenInfo(bo.getParams());
		return R.success();
	}

	/**
	 * 查询字典
	 *
	 * @param code
	 * @return
	 */
	@ApiLog("查询字典")
	@ApiOperation("查询字典")
	@GetMapping("/dict_data_type/{code}")
	public R<List<DictBiz>> getDictDataType(@PathVariable("code") String code) {
		return iDictBizClient.getList(code);
	}

	/**
	 * 上传附件
	 */
	@ApiLog("上传附件")
	@ApiOperation("上传附件")
	@PostMapping(value = "/upload_attachment", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public R uploadAttachment(@RequestPart("location") String location,
							  @RequestPart("type") String type,
							  @RequestPart("file") MultipartFile file) {
		try {
			if (!FileUtils.checkFormats(file.getOriginalFilename())) {
				return R.fail("非法文件类型,只支持doc,docx,xls,xlsx,ppt,pptx,pdf,txt,wmv,mp4,html,xml,jpg,jpeg,png,gif,heic格式!");
			}
			if (!FileUtils.checkFileSize(file, FileUtils.MAX_FILE_SIZE, FileUtils.MIN_FILE_SIZE)) {
				return R.fail(String.format("上传文件大小不能超过%sM和小于%sK!", FileUtils.MAX_FILE_SIZE / 1024 / 1024, FileUtils.MIN_FILE_SIZE));
			}
			OpenAttachmentBo params = new OpenAttachmentBo();
			params.setLocation(location);
			params.setType(type);
			params.setFile(file);
			OpenAttachmentVo res = iOpenAccountOfflineService.uploadAttachment(params);
			return R.data(res);
		} catch (Exception e) {
			log.error("线下开户上传附件失败:", e);
			return R.fail("上传附件失败!");
		}
	}

	/**
	 * 上传开户证件照片
	 */
	@ApiLog("上传开户证件照片")
	@ApiOperation("上传开户证件照片")
	@PostMapping(value = "/upload_open_image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public R uploadOpenImage(@RequestPart("location") String location,
							  @RequestPart("type") String type,
							  @RequestPart("file") MultipartFile file) {
		try {
			if (!FileUtils.checkFormats(file.getOriginalFilename())) {
				return R.fail("非法文件类型,只支持doc,docx,xls,xlsx,ppt,pptx,pdf,txt,wmv,mp4,html,xml,jpg,jpeg,png,gif,heic格式!");
			}
			if (!FileUtils.checkFileSize(file, FileUtils.MAX_FILE_SIZE, FileUtils.MIN_FILE_SIZE)) {
				return R.fail(String.format("上传文件大小不能超过%sM和小于%sK!", FileUtils.MAX_FILE_SIZE / 1024 / 1024, FileUtils.MIN_FILE_SIZE));
			}
			OpenAttachmentBo params = new OpenAttachmentBo();
			params.setLocation(location);
			params.setType(type);
			params.setFile(file);
			OpenAttachmentVo res = iOpenAccountOfflineService.uploadAttachment(params);
			return R.data(res);
		} catch (Exception e) {
			log.error("线下开户上传开户证件照片失败:", e);
			return R.fail("上传开户证件照片失败!");
		}
	}

	/**
	 * 上传开户证件照片
	 */
	@ApiLog("上传开户证件照片-Base64")
	@ApiOperation("上传开户证件照片-Base64")
	@PostMapping(value = "/upload_open_image_base64", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public R uploadOpenImageBase64(@RequestBody CommonReqVO<OpenImgBo> bo) {
		try {
			OpenImgBo params = bo.getParams();
			OpenImgVo res = iOpenAccountOfflineService.saveOpenAccountImg(params);
			return R.data(res);
		} catch (Exception e) {
			log.error("线下开户上传开户证件照片失败:", e);
			return R.fail("上传开户证件照片失败!");
		}
	}

	/**
	 * 查询附件列表
	 *
	 * @param userId
	 * @return
	 */
	@ApiLog("查询附件列表")
	@ApiOperation("查询附件列表")
	@GetMapping("/query_attachment_list/{userId}")
	public R<List<OpenAttachmentVo>> queryAttachmentList(@PathVariable("userId") Long userId) {
		List<OpenAttachmentVo> res = iOpenAccountOfflineService.queryAttachmentList(userId);
		return R.data(res);
	}
}
