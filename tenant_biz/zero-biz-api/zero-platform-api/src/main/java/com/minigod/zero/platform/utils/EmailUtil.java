package com.minigod.zero.platform.utils;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.SpringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.platform.vo.SmsCaptchaVO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/6 13:33
 * @description：
 */
public class EmailUtil {
	public static IPlatformMsgClient platformMsgClient ;


	public static IPlatformMsgClient getEmailClient() {
		if (platformMsgClient == null) {
			platformMsgClient = SpringUtil.getBean(IPlatformMsgClient.class);
		}
		return platformMsgClient;
	}

	public static Builder builder(){
		return new Builder();
	}



	public static ThreadPoolExecutor threadPoolExecutor() {
		return SpringUtil.getBean(ThreadPoolExecutor.class);
	}


	public static class Builder{
		private SendEmailDTO  emailDTO = new SendEmailDTO();

		public Builder title(String title) {
			this.emailDTO.setTitle(title);
			return this;
		}

		public Builder content(String content){
			this.emailDTO.setContent(content);
			return this;
		}

		public Builder templateCode(Integer templateCode){
			this.emailDTO.setCode(templateCode);
			return this;
		}

		public Builder paramList(List<String> paramList){
			this.emailDTO.setList(paramList);
			return this;
		}

		public Builder param(String param){
			if (this.emailDTO.getList() == null){
				this.emailDTO.setList(new ArrayList<>());
			}
			this.emailDTO.getList().add(param);
			return this;
		}

		public Builder titleParam(String param){
			if (this.emailDTO.getTitleParam() == null){
				this.emailDTO.setTitleParam(new ArrayList<>());
			}
			this.emailDTO.getTitleParam().add(param);
			return this;
		}

		public Builder titleParams(List<String> param){
			this.emailDTO.setTitleParam(param);
			return this;
		}

		public Builder sender(String sender){
			this.emailDTO.setSender(sender);
			return this;
		}

		public Builder fromName(String fromName){
			this.emailDTO.setFromName(fromName);
			return this;
		}

		public Builder attachmentUrls(List<String> attachmentUrls){
			this.emailDTO.setAttachmentUrls(attachmentUrls);
			return this;
		}

		public Builder carbonCopy(List<String> carbonCopy){
			this.emailDTO.setCarbonCopy(carbonCopy);
			return this;
		}

		public Builder blindCarbonCopy(List<String> blindCarbonCopy){
			this.emailDTO.setBlindCarbonCopy(blindCarbonCopy);
			return this;
		}

		public Builder  accepts(List<String> accepts){
			this.emailDTO.setAccepts(accepts);
			return this;
		}

		public Builder lang(String land){
			this.emailDTO.setLang(land);
			return this;
		}

		public Builder captchaKey(String captchaKey){
			this.emailDTO.setCaptchaKey(captchaKey);
			return this;
		}

		public Builder captchaCode(String captchaCode){
			this.emailDTO.setCaptchaCode(captchaCode);
			return this;
		}

		public Builder tenantId(String tenantId){
			this.emailDTO.setTenantId(tenantId);
			return this;
		}

		public void sendAsync(){
			threadPoolExecutor().execute(()->{
				getEmailClient().sendEmail(this.emailDTO);
			});
		}

		public R sendSync(){
			return getEmailClient().sendEmail(this.emailDTO);
		}

		public R sendCaptcha(){
			if (CollectionUtil.isEmpty(this.emailDTO.getAccepts())){
				return R.fail("接收者邮箱不能为空！");
			}
			SmsCaptchaVO smsCaptcha = new SmsCaptchaVO();
			smsCaptcha.setEmail(this.emailDTO.getAccepts().get(0));
			smsCaptcha.setCode(this.emailDTO.getCode());
			smsCaptcha.setLang(WebUtil.getLanguage());
			smsCaptcha.setCaptcha(this.emailDTO.getCaptchaCode());
			return getEmailClient().sendEmailCaptchaNew(smsCaptcha);
		}

		public Boolean validate(){
			R result =  getEmailClient().checkEmailCaptcha(this.emailDTO.getCaptchaKey(),this.emailDTO.getCaptchaCode(),this.emailDTO.getCode());
			if (result.isSuccess()){
				Boolean flag = (Boolean) result.getData();
				return flag;
			}
			return false;
		}
	}
}
