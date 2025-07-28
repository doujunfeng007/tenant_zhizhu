package com.minigod.zero.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.manage.comm.EamilFilterCode;
import com.minigod.zero.manage.proxy.CustomerProxy;
import com.minigod.zero.manage.service.IPlatformEmailContentService;
import com.minigod.zero.manage.utils.FileReadUtil;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.entity.PlatformEmailContentEntity;
import com.minigod.zero.platform.enums.EmailTemplate;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.platform.utils.PlatformUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OMS邮件消息
 *
 * @author Zhe.Xiao
 */
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/email")
@Slf4j
public class EmailMsgController {
	@Autowired
	private IPlatformMsgClient platformMsgClient;

	@Autowired
	private CustomerProxy customerProxy;

	@Autowired
	private IPlatformEmailContentService platformEmailContentService;

	@Value("${email.isDataMask:true}")
	private Boolean isEmailDataMask;

	/**
	 * 邮件消息-发送记录-详情
	 *
	 * @return
	 */
	@ApiOperation("邮件消息-发送记录-详情")
	@GetMapping("/send/msgDetail")
	public R<Object> msgDetail(Long id) {
		PlatformEmailContentEntity detail = platformEmailContentService.getById(id);
		if (isEmailDataMask != null && !isEmailDataMask) {
			detail.setContent(detail.getContent());
		} else {
			detail.setContent(PlatformUtil.getDataMaskContent(detail.getContent()));
		}
		if (Arrays.stream(EamilFilterCode.EAMIL_FILTER_CODE).anyMatch(code -> code.equals(String.valueOf(detail.getTempCode())))){
			String maskedContent = detail.getContent();
			maskedContent = (maskedContent.replaceAll("密码：\\d{6}", "密码：******"));
			maskedContent = (maskedContent.replaceAll("验证码：\\d{6}", "验证码：******"));
			if (detail.getTempCode()==EmailTemplate.OPEN_SUB_ACCOUNT.getCode()) {
                maskedContent = (maskedContent.replaceAll(" \\d{6}", " ******"));
            }
			if (detail.getTempCode()==EmailTemplate.RESET_TRANSACTION_PASSWORD.getCode()) {
                maskedContent = (maskedContent.replaceAll("\\d{6}</div>", "******</div>"));
            }
			detail.setContent(maskedContent);
		}
		return R.data(detail);
	}

	/**
	 * 发送邮件页面 发送 （dto有改动，前端原来传userIds，现需要改成cellEmail）
	 *
	 * @return
	 */
	@ApiOperation("发送邮件页面 发送（dto有改动，前端原来传userIds，现需要把cellEmail列表传到参数accepts）")
	@PostMapping("/send/sendEmail")
	public R<Object> confirmSysMsg(@RequestParam(value = "file", required = false) MultipartFile file,
								   @RequestParam(value = "file2", required = false) MultipartFile file2,
								   SendEmailDTO sendEmailDTO) {
		try {
			boolean needAccepts = (file == null || file.isEmpty()) && (file2 == null || file2.isEmpty()) && CollectionUtil.isEmpty(sendEmailDTO.getAccepts());
			if (needAccepts) {
				return R.fail(ResultCode.PARAMETER_ERROR);
			}
			if (StringUtils.isBlank(sendEmailDTO.getTitle()) || StringUtils.isBlank(sendEmailDTO.getContent())) {
				return R.fail(ResultCode.PARAMETER_ERROR);
			}

			// 是否通过文件导入
			if (file != null && !file.isEmpty()) {
				List<Long> lstUserIds = FileReadUtil.getUserIdsFromFile(file);
				if (CollectionUtils.isEmpty(lstUserIds)) {
					return R.fail("导入文件内容为空");
				} else {
					R<String> result = customerProxy.customerEmailList(lstUserIds);
					if (!result.isSuccess()) {
						return R.fail("发送失败，获取用户邮箱失败");
					}
					String emailStr = result.getData();
					if (StringUtils.isEmpty(emailStr)) {
						return R.fail("发送失败，获取用户邮箱为空");
					}
					List<String> emailList = Arrays.asList(emailStr.split(","));
					sendEmailDTO.setAccepts(emailList);
				}
			} else if (file2 != null && !file2.isEmpty()) {
				List<String> lstEmails = FileReadUtil.getCellPhoneOrEmailFromFile(file2);
				if (CollectionUtils.isEmpty(lstEmails)) {
					return R.fail("导入文件内容为空");
				} else {
					sendEmailDTO.setAccepts(lstEmails);
				}
			}
			if (CollectionUtil.isNotEmpty(sendEmailDTO.getAccepts())) {
				sendEmailDTO.setAccepts(sendEmailDTO.getAccepts().stream().distinct().collect(Collectors.toList()));
			}
			log.info("发送参数：{}", JSONObject.toJSONString(sendEmailDTO));
			//对富文本字段进行Base64转码
			String encodedContent = Base64.getEncoder().encodeToString(sendEmailDTO.getContent().getBytes());
			sendEmailDTO.setContent(encodedContent);
			log.info("转码Base64后的发送参数：{}", JSONObject.toJSONString(sendEmailDTO));
			platformMsgClient.sendEmailBase64(sendEmailDTO);
			return R.success();
		} catch (Exception e) {
			log.error("发送邮件失败：", e);
			return R.fail(ResultCode.INTERNAL_ERROR);
		}
	}
}
