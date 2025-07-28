package com.minigod.zero.bpmn.module.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 上传文件类型
 *
 * @author Lion Li
 */
@Getter
@AllArgsConstructor
public enum UploadFileType {
	/**
	 * PI认证通知书《关于被视为专业投资者的通知》
	 */
	PI_FILE_NOTICE_REGARDED_AS_PROFESSIONAL_INVESTORS(1);
	private final Integer fileType;
}
