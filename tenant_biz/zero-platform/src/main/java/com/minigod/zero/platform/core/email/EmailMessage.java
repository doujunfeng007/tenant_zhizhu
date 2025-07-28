package com.minigod.zero.platform.core.email;

import com.minigod.zero.platform.core.VerificationCodeMessage;
import lombok.Data;

import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/11 11:19
 * @description：
 */
@Data
public class EmailMessage extends VerificationCodeMessage {
	/**
	 * 发送人
	 */
	private String sender;
	/**
	 *抄送人
	 */
	private List<String> carbonCopy;
	/**
	 * 密抄送人
	 */
	private List<String> blindCarbonCopy;
	/**
	 * 唯一ID
	 */
	private String sendId;
	/**
	 * 发件人
	 */
	private String fromName;
	/**
	 * 收件邮箱
	 */
	private List<String> accepts;
	/**
	 * 收件人id
	 */
	private Integer userId;
	/**
	 * 附件
	 */
	private List<String> attachmentUrls;

}
