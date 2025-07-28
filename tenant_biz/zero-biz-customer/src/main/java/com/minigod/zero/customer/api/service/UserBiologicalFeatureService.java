package com.minigod.zero.customer.api.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.dto.BiometricDTO;


/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/7/2 16:20
 * @description：
 */
public interface UserBiologicalFeatureService {
	/**
	 * 绑定生物识别
	 * @param biometricDTO
	 * @return
	 */
	R bindBiologicalFeature(BiometricDTO biometricDTO);

	/**
	 * 解绑生物识别
	 * @param deviceCode
	 * @param token
	 * @return
	 */
	R unbindBiologicalFeature(String deviceCode, String token);

	/**
	 * 验证生物识别
	 * @param deviceCode
	 * @param token
	 * @return
	 */
	R judgeBiologicalFeature(String deviceCode, String token);
}
