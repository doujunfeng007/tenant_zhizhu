package com.minigod.auth.utils;

import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/1 18:45
 * @description：
 */
@Component
public class AccountLoginUtils {

	@Value("${login.failureNum}")
	private Integer failureNum;

	@Autowired
	private ZeroRedis zeroRedis;

	/**
	 * 统计失败次数
	 * @return
	 */
	public Integer failureCount(String tenantId,String phone,String areaCode){
		String key = String.format(CommonConstant.ACCOUNT_LOGIN_FAILURE_NUM,tenantId,phone,areaCode);
		if (!zeroRedis.exists(key)){
			zeroRedis.set(key,1);
		}else{
			Integer value = zeroRedis.get(key);
			zeroRedis.set(key,(value+1));
		}
		return zeroRedis.get(key);
	}

	/**
	 * 删除失败次数
	 */
	public void delFailureCount(String tenantId,String phone,String areaCode){
		String key = String.format(CommonConstant.ACCOUNT_LOGIN_FAILURE_NUM,tenantId,phone,areaCode);
		zeroRedis.del(key);
	}

	/**
	 * 获取失败次数阈值
	 * @return
	 */
	public Integer getMaxFailureNum(){
		return this.failureNum;
	}

	/**
	 * 获取当前失败次数
	 * @return
	 */
	public Integer getCurrentFailureNum(String tenantId,String phone,String areaCode){
		String key = String.format(CommonConstant.ACCOUNT_LOGIN_FAILURE_NUM,tenantId,phone,areaCode);
		Integer current = zeroRedis.get(key);
		if(current == null){
			current = 0;
		}
		current = current + 1;
		return current;
	}

	/**
	 * 异常提示信息
	 * @return
	 */
	public String getFailureMessage(String tenantId,String phone,String areaCode){
		String key = String.format(CommonConstant.ACCOUNT_LOGIN_FAILURE_NUM,tenantId,phone,areaCode);
		Integer current = zeroRedis.get(key);
		if(current == null){
			current = 0;
		}
		current = current + 1;

		if (failureNum <= current){
			Date now = new Date();
			Date endTime = DateUtil.parseDate(DateUtil.format(now,"yyyy-MM-dd 59:59:59"));
			zeroRedis.setEx(key,failureNum,(endTime.getTime() - now.getTime()) / 1000);
			return I18nUtil.getMessage(CommonConstant.ACCOUNT_LOCKED);
		}
		String message = I18nUtil.getMessage(CommonConstant.PASSWORD_INPUT_ERROR);
		return String.format(message,failureNum,(failureNum - current));
	}

	/**
	 * 锁定用户
	 */
	public boolean lockAccount(String tenantId,String phone,String areaCode){
		String key = String.format(CommonConstant.ACCOUNT_LOGIN_FAILURE_NUM,tenantId,phone,areaCode);
		Integer current = zeroRedis.get(key);
		return (current == null ? 0 : current) >= failureNum;
	}
}
