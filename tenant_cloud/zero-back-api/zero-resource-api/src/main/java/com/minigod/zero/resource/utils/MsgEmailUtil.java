package com.minigod.zero.resource.utils;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.sendcloud.sdk.builder.SendCloudBuilder;
import com.sendcloud.sdk.core.SendCloud;
import com.sendcloud.sdk.model.*;
import com.sendcloud.sdk.util.ResponseData;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zhe.Xiao
 */
@Slf4j
public class MsgEmailUtil {

	/**
	 * SendCloud发送邮件
	 *
	 * @param accept
	 * @param carbonCopy
	 * @param sender
	 * @param fromName
	 * @param title
	 * @param attachmentUrls
	 * @param code
	 * @param content
	 * @return
	 */
	public static R sendCloudEmail(String accept, List<String> carbonCopy, List<String> blindCarbonCopy, String sender, String fromName, String title, List<String> attachmentUrls, Integer code, String content) {
		R rt = R.success();

		ResponseData res = new ResponseData();
		MailAddressReceiver receiver = new MailAddressReceiver();
		receiver.addTo(accept);
		receiver.addCc(StringUtil.join(carbonCopy, ";"));
		receiver.addBcc(StringUtil.join(blindCarbonCopy, ";"));

		MailBody body = new MailBody();
		body.setFrom(sender);
		body.setFromName(fromName);
		body.setReplyTo(accept);
		body.setSubject(title);

		//读取邮件附件链接
		if (null != attachmentUrls && attachmentUrls.size() > 0) {
			List<File> attachments = Lists.newArrayList();
			for (String url : attachmentUrls) {
				try {
					File file = ImageUtil.getFile(url);
					if (null != file) {
						attachments.add(file);
					}
				} catch (Exception e) {
					log.error("getAttachmentUrls file error", e);
				}
			}
			if (attachments.size() > 0) {
				for (File attachment : attachments) {
					if (attachment.exists()) {
						body.addAttachments(attachment);
					}
				}
			}
		}

		SendCloudMail mail = new SendCloudMail();
		if (code == null) {
			TextContent textContent = new TextContent();
			textContent.setContent_type(TextContent.ScContentType.html);
			textContent.setText(content);
//	            SendCloudMail mail = new SendCloudMail();
			mail.setTo(receiver);
			mail.setBody(body);
			mail.setContent(textContent);
		} else {
			List<String> toList = new ArrayList<String>();
			toList.add(accept);
			List<String> contentList = new ArrayList<String>();
			contentList.add(content);
			Map<String, List<String>> sub = new HashMap<String, List<String>>();
			sub.put("%content%", contentList);
			// 此时, receiver 中添加的 to, cc, bcc 均会失效
			body.addXsmtpapi("to", toList);
			body.addXsmtpapi("sub", sub);
			//    		body.addHeader("SC-Custom-test_key1", "test1");
			//    		body.addHeader("NO-SC-Custom-test_key1", "test2");
			// 使用邮件模板
			TemplateContent temp = new TemplateContent();
			temp.setTemplateInvokeName("sunline_email");
//	    		SendCloudMail mail = new SendCloudMail();
			// 模板发送时, 必须使用 Xsmtpapi 来指明收件人; mail.setTo();
			mail.setBody(body);
			mail.setContent(temp);
		}

		SendCloud sc = SendCloudBuilder.build();
		try {
			res = sc.sendMail(mail);
		} catch (Throwable e) {
			log.error("SendCloudEmail error", e);
		}

		if (res.getStatusCode() != 200) {
			rt.setCode(ResultCode.INTERNAL_ERROR.getCode());
			rt.setMsg(res.getMessage());
		} else {
			String info = res.getInfo();
			if (StringUtil.isNotBlank(info)) {
				JSONObject jsonObject = JSONObject.parseObject(info);
				List<String> emailIdList = jsonObject.getJSONArray("emailIdList").toJavaList(String.class);
				rt = R.data(emailIdList);
			}
		}
		return rt;
	}

}
