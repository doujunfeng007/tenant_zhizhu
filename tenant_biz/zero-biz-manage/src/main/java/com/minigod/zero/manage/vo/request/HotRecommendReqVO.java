package com.minigod.zero.manage.vo.request;

import com.minigod.zero.manage.vo.HotRecommendVO;
import com.minigod.zero.core.tool.api.SNVersion;
import lombok.Data;

@Data
public class HotRecommendReqVO extends SNVersion {

	private static final long serialVersionUID = 1L;

	private HotRecommendVO params;
}
