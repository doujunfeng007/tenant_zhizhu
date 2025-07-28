package com.minigod.zero.cms.vo;

import com.minigod.zero.core.tool.api.SNVersion;
import lombok.Data;

@Data
public class OptionalGroupNewRequest extends SNVersion {
	private static final long serialVersionUID = 6403880690995052230L;
	private OptionalGroupNewVO params;
}
