package com.minigod.zero.customer.api.controller;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.api.service.UserBiologicalFeatureService;
import com.minigod.zero.customer.dto.BiometricDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/2 17:27
 * @description：
 */
@RestController
@RequestMapping("/biometric")
public class UserBiologicalFeatureController {
	@Autowired
	private UserBiologicalFeatureService userBiologicalFeatureService;
	/**
	 * 绑定生物识别
	 * @param biometricDTO
	 * @return
	 */
	@PostMapping
	public R bindBiologicalFeature(@RequestBody BiometricDTO biometricDTO){
		return userBiologicalFeatureService.bindBiologicalFeature(biometricDTO);
	}

	/**
	 * 解绑生物识别
	 * @param biometricDTO
	 * @return
	 */
	@PutMapping
	public R unbindBiologicalFeature(@RequestBody BiometricDTO biometricDTO){
		return userBiologicalFeatureService.unbindBiologicalFeature(biometricDTO.getOpStation(),biometricDTO.getToken());
	}

	/**
	 * 验证生物识别
	 * @param biometricDTO
	 * @return
	 */
	@PostMapping("/identify")
	public R judgeBiologicalFeature(@RequestBody BiometricDTO biometricDTO){
		return userBiologicalFeatureService.judgeBiologicalFeature(biometricDTO.getOpStation(),biometricDTO.getToken());
	}
}
