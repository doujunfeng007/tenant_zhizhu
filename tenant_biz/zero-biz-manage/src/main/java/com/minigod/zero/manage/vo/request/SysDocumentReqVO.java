package com.minigod.zero.manage.vo.request;

import com.minigod.zero.core.tool.api.SNVersion;
import com.minigod.zero.manage.entity.SysDocumentEntity;
import lombok.Data;

@Data
public class SysDocumentReqVO extends SNVersion {

	private static final long serialVersionUID = 1L;

	private SysDocumentEntity params;
}
