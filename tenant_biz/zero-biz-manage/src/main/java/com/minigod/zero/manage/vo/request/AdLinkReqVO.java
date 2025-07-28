package com.minigod.zero.manage.vo.request;

import com.minigod.zero.core.tool.api.SNVersion;
import com.minigod.zero.manage.vo.AdLinkDetailVO;
import lombok.Data;

@Data
public class AdLinkReqVO extends SNVersion {

	private static final long serialVersionUID = 1L;

	private AdLinkDetailVO params;
}
