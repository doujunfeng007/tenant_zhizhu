package com.minigod.zero.platform.utils;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.sms.model.SmsData;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.RandomType;
import com.minigod.zero.core.tool.utils.SpringUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.platform.feign.ISmsClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/13 15:57
 * @description：
 */
@Slf4j
public class SmsUtil {
	private static ISmsClient smsClient;
	/**
	 * 验证码默认过期时间
	 */
	public static Long DEFAULT_EXPIRE = 5L;

	public static Builder builder(){
		return new Builder();
	}

	public static class Builder{
		SmsData sendSmsDTO = new SmsData();

		public Builder tenantId(String tenantId){
			sendSmsDTO.setTenantId(tenantId);
			return this;
		}

		public Builder language(String language){
			sendSmsDTO.setLanguage(language);
			return this;
		}

		public Builder templateCode(Integer templateCode){
			sendSmsDTO.setTempCode(String.valueOf(templateCode));
			return this;
		}

		public Builder phoneNumber(String phoneNumber){
			sendSmsDTO.setPhone(phoneNumber);
			return this;
		}

		public Builder areaCode(String areaCode){
			sendSmsDTO.setAreaCode(areaCode);
			return this;
		}

		public Builder listParam(List<String> listParam){
			sendSmsDTO.setTempParam(String.join(",",listParam));
			return this;
		}

		public Builder param(String param){
			if (StringUtils.isEmpty(this.sendSmsDTO.getTempParam())){
				this.sendSmsDTO.setTempParam(param);
			}else{
				this.sendSmsDTO.setTempParam(this.sendSmsDTO.getTempParam() + "," +param);
			}
			return this;
		}

		public Builder isValidateMessage(){
			String code = getValidateParams();
			log.info("自定义验证码：{}",sendSmsDTO.getCaptchaCode());
			if (sendSmsDTO.getCaptchaCode() != null){
				code = sendSmsDTO.getCaptchaCode();
			}
			sendSmsDTO.setVerificationCode(true);
			sendSmsDTO.setExpire(DEFAULT_EXPIRE);
			sendSmsDTO.setCaptchaCode(code);
			sendSmsDTO.setTempParam(code+","+DEFAULT_EXPIRE);
			return this;
		}

		public Builder captchaKey(String captchaKey){
			sendSmsDTO.setCaptchaKey(captchaKey);
			return this;
		}

		public Builder captchaCode(String captchaCode){
			sendSmsDTO.setCaptchaCode(captchaCode);
			return this;
		}

		public R sendSync(){

			paramCheck(sendSmsDTO);
			log.info("短信发送参数：{}",JSONObject.toJSONString(sendSmsDTO));
			R result =  getSmsClient().sendMessage(sendSmsDTO);
			log.info("短信发送返回参数：{}",JSONObject.toJSONString(result));
			return result;
		}

		public void sendAsync(){
			threadPoolExecutor().execute(()->{
				sendSync();
			});
		}

		public Boolean validate(){
			R result =  getSmsClient().validateMessage(sendSmsDTO);
			if (result.isSuccess()){
				Boolean flag = (Boolean) result.getData();
				return flag;
			}
			return false;
		}

	}

	/**
	 * 获取短信服务构建类
	 *
	 * @return SmsBuilder
	 */
	public static ISmsClient getSmsClient() {
		if (smsClient == null) {
			smsClient = SpringUtil.getBean(ISmsClient.class);
		}
		return smsClient;
	}

	/**
	 * 生产验证码
	 * @return
	 */
	private static String getValidateParams() {
		return StringUtil.random(6, RandomType.INT);
	}

	/**
	 * 异步线程池
	 * @return
	 */
	public static ThreadPoolExecutor threadPoolExecutor() {
		return SpringUtil.getBean(ThreadPoolExecutor.class);
	}

	/**
	 * 参数校验
	 * @param sendSmsDTO
	 */
	private static void paramCheck(SmsData sendSmsDTO){
		if (StringUtils.isEmpty(sendSmsDTO.getAreaCode())){
			throw new ZeroException("发送失败，区号不能为空");
		}
		if (StringUtils.isEmpty(sendSmsDTO.getPhone())){
			throw new ZeroException("发送失败，手机号不能为空");
		}
		if (sendSmsDTO.getTempCode() == null){
			throw new ZeroException("发送失败，模板不能为空");
		}
//		if (StringUtils.isEmpty(sendSmsDTO.getLanguage())){
//			throw new ZeroException("发送失败，语种不能为空");
//		}
//		if (StringUtils.isEmpty(sendSmsDTO.getTenantId())){
//			throw new ZeroException("发送失败，租户id不能为空");
//		}
	}
}
