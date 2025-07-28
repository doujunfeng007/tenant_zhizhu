package com.minigod.zero.bpmn.module.common.dto;

import com.minigod.zero.bpmn.module.common.entity.FileUploadInfoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 附件上传表 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2024-03-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileUploadInfoDTO extends FileUploadInfoEntity {
	private static final long serialVersionUID = 1L;

}
