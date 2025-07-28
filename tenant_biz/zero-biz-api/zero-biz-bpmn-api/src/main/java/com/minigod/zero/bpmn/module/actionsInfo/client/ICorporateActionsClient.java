package com.minigod.zero.bpmn.module.actionsInfo.client;

import com.minigod.zero.bpmn.module.actionsInfo.dto.CorporateActionsInfoDTO;
import com.minigod.zero.bpmn.module.actionsInfo.entity.CorporateActionsInfoEntity;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
	value = AppConstant.SERVICE_BIZ_BPMN_NAME
)
public interface ICorporateActionsClient {
	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/corporate";

	String SAVE_ACTIONS = API_PREFIX + "/save_actions";
	String GET_ACTIONS = API_PREFIX + "/get_actions";

	@PostMapping(SAVE_ACTIONS)
	R<Void> saveActions(@RequestBody CorporateActionsInfoDTO saveDTO);

	@PostMapping(GET_ACTIONS)
	R<List<CorporateActionsInfoEntity>> getActions(@RequestBody CorporateActionsInfoDTO saveDTO);

}
