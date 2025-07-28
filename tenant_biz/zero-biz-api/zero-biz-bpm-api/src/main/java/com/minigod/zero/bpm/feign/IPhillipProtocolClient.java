package com.minigod.zero.bpm.feign;

import com.minigod.zero.bpm.dto.PhillipProtocolSaveDTO;
import com.minigod.zero.bpm.vo.PhillipProtocolVo;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
	value = AppConstant.SERVICE_BIZ_BPM_NAME
)
public interface IPhillipProtocolClient {
	String API_PREFIX = AppConstant.FEIGN_API_PREFIX + "/ph";
	String SAVE_PH_PROTOCOL = API_PREFIX + "/save_protocol";
	String FIND_PH_PROTOCOL = API_PREFIX + "/find_protocol";

	@PostMapping(SAVE_PH_PROTOCOL)
	R<Void> savePhProtocol(@RequestBody PhillipProtocolSaveDTO saveDTO);

	@GetMapping(FIND_PH_PROTOCOL)
	R<PhillipProtocolVo> findPhProtocolLog();

}
