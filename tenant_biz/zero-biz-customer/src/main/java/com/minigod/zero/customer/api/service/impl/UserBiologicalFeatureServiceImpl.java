package com.minigod.zero.customer.api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.minigod.zero.biz.common.constant.AccountMessageConstant;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.api.service.UserBiologicalFeatureService;
import com.minigod.zero.customer.dto.BiometricDTO;
import com.minigod.zero.customer.entity.CustomerBiologyFeature;
import com.minigod.zero.customer.mapper.CustomerBiologyFeatureMapper;
import com.minigod.zero.customer.utils.ProtocolUtils;
import com.minigod.zero.customer.utils.RSANewUtil;
import com.minigod.zero.customer.utils.SecurityKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
public class UserBiologicalFeatureServiceImpl implements UserBiologicalFeatureService {
    @Resource
    private ZeroRedis redisMapService;

    @Resource
    private CustomerBiologyFeatureMapper customerBiologyFeatureMapper;

    /**
     * 绑定用户生物特征
     * @return
     */
	@Override
    public R bindBiologicalFeature(BiometricDTO biometricDTO){
        //获取参数
        Integer type = biometricDTO.getType();
        String password = biometricDTO.getPassword();
        String opStation = biometricDTO.getOpStation();

		Long custId = AuthUtil.getCustId();

        //拼装参数
        StringBuilder sb = new StringBuilder(opStation);
        if(!StringUtils.isEmpty(password)){
            sb.append("&").append(RSANewUtil.decrypt(password));
        }
        sb.append("&").append(type).append("&").append(custId).append("&").append(System.currentTimeMillis());
        String token = ProtocolUtils.getEncryptToken(sb.toString(), SecurityKey.MOBILE_PHONE_KEY);

		CustomerBiologyFeature customerBiologyFeature =
			customerBiologyFeatureMapper.selectByCustIdAndDeviceCode(custId,opStation);
		if (customerBiologyFeature == null){
			//将token信息保存到数据库
			customerBiologyFeature = new CustomerBiologyFeature();
			customerBiologyFeature.setCustId(custId);
			customerBiologyFeature.setDeviceCode(opStation);
			customerBiologyFeature.setToken(token);
			customerBiologyFeature.setStatus(1);
			customerBiologyFeature.setCreateTime(new Date());
			customerBiologyFeature.setIsDeleted(0);
			customerBiologyFeatureMapper.insert(customerBiologyFeature);
		}else{
			customerBiologyFeature.setToken(token);
			customerBiologyFeature.setUpdateTime(new Date());
			customerBiologyFeatureMapper.updateByPrimaryKey(customerBiologyFeature);
		}
        //将token更新到redis，兼容重新绑定的情况，保持redis与数据库信息一致
        redisMapService.saveUpdate(customerBiologyFeature,token);
        return R.data(token);
    }

    /**
     * 解绑用户生物特征
     * @param token
     * @return
     */
    public R unbindBiologicalFeature(String deviceCode, String token){
		//先判断用户生物特征是否有效
		R result = this.judgeBiologicalFeature(deviceCode,token);
		if (result.isSuccess()){
			//为了保持数据一致性，先删数据库，再删redis
			customerBiologyFeatureMapper.deleteByToken(token);
			redisMapService.del(token);
			return R.success();
		}
		return R.fail(result.getMsg());
    }

    /**
     * 识别用户生物特征
     * @param token
     * @return
     */
    public R judgeBiologicalFeature(String deviceCode, String token){
		//根据token解出设备号,如果参数里得设备号与token中的设备号不一致，提示token无效
		String sDeviceCode = ProtocolUtils.getDeviceCodeFromToken(token);
		if(!sDeviceCode.equals(deviceCode)){
			return R.fail(10002, I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_BIOMETRIC_TOKEN_INVALID_NOTICE));
		}
		CustomerBiologyFeature userBiologicalFeature = redisMapService.findObject(CustomerBiologyFeature.class, token);
		if (userBiologicalFeature != null && userBiologicalFeature.getStatus() == 1){
			return R.success();
		}
		userBiologicalFeature = customerBiologyFeatureMapper.selectByToken(token);
		if (userBiologicalFeature == null){
			return R.fail(10002, I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_BIOMETRIC_TOKEN_INVALID_NOTICE));
		}
		if (userBiologicalFeature.getStatus() == 1) {
			//加载到redis
			redisMapService.saveUpdate(CustomerBiologyFeature.class, token);
			return R.success();
		} else {
			return R.fail(10002, I18nUtil.getMessage(AccountMessageConstant.ACCOUNT_OPEN_BIOMETRIC_TOKEN_INVALID_NOTICE));
		}
    }
}
