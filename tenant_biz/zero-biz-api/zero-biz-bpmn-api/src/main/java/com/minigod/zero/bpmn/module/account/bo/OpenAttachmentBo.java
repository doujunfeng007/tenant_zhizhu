package com.minigod.zero.bpmn.module.account.bo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 开户附件
 *
 * @author eric
 * @since 2024-06-12 18:06:01
 */
@Data
public class OpenAttachmentBo {
	private MultipartFile file;
	private String location;
	private String type;
}
