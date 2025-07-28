package com.minigod.zero.bpmn.module.flow.feign;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.minigod.zero.bpm.enums.OpenAccountEnum;
import com.minigod.zero.bpmn.module.account.api.ImageInfo;
import com.minigod.zero.bpmn.module.account.api.InvestQuestionnaires;
import com.minigod.zero.bpmn.module.account.api.VerifyFour;
import com.minigod.zero.bpmn.module.account.bo.OpenAccountBo;
import com.minigod.zero.bpmn.module.account.bo.OpenAccountCallbackBo;
import com.minigod.zero.bpmn.module.account.entity.CustomOpenImgEntity;
import com.minigod.zero.bpmn.module.account.entity.CustomOpenInfoEntity;
import com.minigod.zero.bpmn.module.account.enums.CustomOpenAccountEnum;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenInfoService;
import com.minigod.zero.bpmn.module.account.service.ICustomOpenImgService;
import com.minigod.zero.bpmn.module.account.service.ICustomOpenInfoService;
import com.minigod.zero.bpmn.module.deposit.service.IFundDepositInfoService;
import com.minigod.zero.bpmn.module.deposit.service.ISecAccImgService;
import com.minigod.zero.bpmn.module.deposit.service.ISecDepositFundsService;
import com.minigod.zero.bpmn.module.deposit.vo.SecDepositFundsVO;
import com.minigod.zero.bpmn.module.feign.IBpmnSyncClient;
import com.minigod.zero.core.tool.api.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @ClassName: BpmnSyncClient
 * @Description:
 * @Author chenyu
 * @Date 2024/2/27
 * @Version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
public class BpmnSyncClient implements IBpmnSyncClient {
	@Resource
	ICustomOpenInfoService customOpenInfoService;
	@Resource
	ICustomOpenImgService customOpenImgService;
	@Autowired
	IAccountOpenInfoService accountOpenInfoService;
	@Autowired
	ISecDepositFundsService secDepositFundsService;
	@Autowired
	ISecAccImgService secAccImgService;
	@Autowired
	IFundDepositInfoService iFundDepositInfoService;

	@Override
	@PostMapping(ACCOUNT_PUSH)
	public R accountPushTask(Map<String, Object> map) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		List<CustomOpenInfoEntity> openInfoList = customOpenInfoService.selectByIsPushedFalse();
		if (null == openInfoList || openInfoList.size() == 0) {
			log.info("*********************【预批客户上传BPM】为空**************************");
		}
		log.info("----------准备执行【预批客户上传BPM】任务---------");
		for (CustomOpenInfoEntity customOpenInfo : openInfoList) {
			Long userId = customOpenInfo.getUserId();
			Integer openStatus = customOpenInfo.getStatus();
			// 非开户中的跳出循环
			if (openStatus == null || !openStatus.equals(CustomOpenAccountEnum.OpenStatus.PENDING.getCode())) {
				continue;
			}

			// 同步错误次数过多
			if (customOpenInfo.getPushErrCount() != null && customOpenInfo.getPushErrCount() >= 3 && customOpenInfo.getIsSend()) {
				log.error("*********************【预批客户上传BPM】开户同步异常超过3次**************************, userId = {}", userId);
				continue;
			}

			// 处理图片数据-内地身份开户
			List<CustomOpenImgEntity> customOpenImgs = customOpenImgService.selectByUserId(userId);
			List<ImageInfo> imageInfos = Lists.newArrayList();

			if (CollectionUtils.isNotEmpty(customOpenImgs)) {
				for (CustomOpenImgEntity customOpenImg : customOpenImgs) {
					ImageInfo openAccountImageInfoProtocol = new ImageInfo();

					Integer locationKey = Integer.parseInt(customOpenImg.getLocationKey());
					Integer locationType = Integer.parseInt(customOpenImg.getLocationType());
					openAccountImageInfoProtocol.setImageLocation(locationKey);
					openAccountImageInfoProtocol.setImageLocationType(locationType);
					openAccountImageInfoProtocol.setImageUrl(customOpenImg.getUrl());
					imageInfos.add(openAccountImageInfoProtocol);
				}
			}

			//如果不是线下开户，则图片数据必传
			if (CollectionUtils.isEmpty(imageInfos) && customOpenInfo.getAccessWay() != OpenAccountEnum.OpenAccountAccessWay.OFFLINE.getCode()) {
				log.error("*********************【预批客户上传BPM】开户图片数据不完整**************************, userId = {}", userId);
				saveOpenAccountErrorInfo(customOpenInfo, "【预批客户上传BPM】开户图片数据不完整", "");
				continue;
			}

			executorService.submit(new Runnable() {
				@Override
				public void run() {
					try {
						VerifyFour verifyFour = new VerifyFour();
						String idCard = customOpenInfo.getIdCard();
						String bankCard = customOpenInfo.getBankCard();
						Integer openType = customOpenInfo.getOpenType(); // 开户方式：1、线上预约开户，2、线下（开户宝）3、香港预约开户

						OpenAccountBo openInfo = JSONObject.parseObject(customOpenInfo.getFormData(), OpenAccountBo.class);
						openInfo.setUserId(Long.valueOf(userId));
						openInfo.setTenantId(customOpenInfo.getTenantId());
						openInfo.setOpenAccountType(customOpenInfo.getFundAccountType());
						openInfo.setOpenAccountAccessWay(customOpenInfo.getAccessWay());
						openInfo.setApplicationId(customOpenInfo.getRemoteId());
						openInfo.setStockTypes(customOpenInfo.getStockTypes());
						//如果是线下开户的,同时风险等级日期为空则默认当前系统日期+365天
						if (openInfo.getAcceptRisk() != null && openInfo.getOpenAccountAccessWay().equals(OpenAccountEnum.OpenAccountAccessWay.OFFLINE.getCode())) {
							if (openInfo.getExpiryDate() == null) {
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(new Date());
								calendar.add(Calendar.DAY_OF_YEAR, 365);
								openInfo.setExpiryDate(calendar.getTime());
							}
						}

						verifyFour.setUserId(userId);
						if (!StringUtils.isBlank(bankCard)) {
							verifyFour.setBankNo(bankCard);
						} else if (openInfo.getVerifyFour() != null && !StringUtils.isBlank(openInfo.getVerifyFour().getBankNo())) {
							verifyFour.setBankNo(openInfo.getVerifyFour().getBankNo());
						} else {
							log.warn("-->开户资料中银行卡号信息为空!!!");
						}
						verifyFour.setIdNo(idCard);
						verifyFour.setName(openInfo.getPersonalInfo().getFamilyName() + openInfo.getPersonalInfo().getGivenName());

						if (openInfo.getVerifyFour() != null && !StringUtils.isBlank(openInfo.getVerifyFour().getPhoneArea())) {
							verifyFour.setPhoneArea(openInfo.getVerifyFour().getPhoneArea());
						} else if (!StringUtils.isBlank(openInfo.getPhoneArea())) {
							verifyFour.setPhoneArea(openInfo.getPhoneArea());
						} else {
							log.warn("-->开户资料中银行预留手机号码国家编码为空!!!");
						}

						if (openInfo.getVerifyFour() != null && !StringUtils.isBlank(openInfo.getVerifyFour().getPhoneNumber())) {
							verifyFour.setPhoneNumber(openInfo.getVerifyFour().getPhoneNumber());
						} else if (!StringUtils.isBlank(openInfo.getPhoneNumber())) {
							verifyFour.setPhoneNumber(openInfo.getPhoneNumber());
						} else {
							log.warn("-->开户资料中银行预留手机号码为空!!!");
						}

						// 完善开户数据
						openInfo.setImageList(imageInfos);
						openInfo.setVerifyFour(verifyFour);
						// 投资知识调查问卷
						String formData = customOpenInfo.getFormData();
						JSONObject jsonObject = JSONObject.parseObject(formData);
						JSONArray investQuestionnairesArray = jsonObject != null ? jsonObject.getJSONArray("investQuestionnaires") : null;
						List<InvestQuestionnaires.QuestionAnswer> questionAnswers = CollUtil.isNotEmpty(investQuestionnairesArray) ? investQuestionnairesArray.toJavaList(InvestQuestionnaires.QuestionAnswer.class) : null;
						InvestQuestionnaires investQuestionnaires = new InvestQuestionnaires();
						investQuestionnaires.setQuestionAnswers(questionAnswers);
						openInfo.setInvestQuestionnaires(investQuestionnaires);


						log.info("*********************【预批客户上传BPM】加载四要素**************************【userId = {}】", userId);
						OpenAccountCallbackBo openAccountCallbackBo = accountOpenInfoService.submitInfo(openInfo);
						saveOpenAccountSuccessInfo(customOpenInfo, openAccountCallbackBo.getApplicationId());
						log.info(userId + "【预批客户上传BPM】回调传入数据：" + JSONObject.toJSONString(openInfo));
					} catch (Exception e) {
						log.error("*********************【预批客户上传BPM】异常,异常详情**************************,", e);
						// 异常，记录操作日志
						saveOpenAccountErrorInfo(customOpenInfo, "【预批客户上传BPM】异常", "服务器异常:" + e.getMessage());
					}
					log.info("*********************结束用户【预批客户上传BPM】**************************【userId = {}】", userId);
				}
			});
		}
		log.info("----------执行【预批客户上传BPM】任务结束---------");
		return R.success();
	}

	private void saveOpenAccountErrorInfo(CustomOpenInfoEntity customOpenInfo, String errorMsg, String body) {
		customOpenInfo.setUpdateTime(new Date());
		// 更新开户调度任务失败次数
		if (customOpenInfo.getPushErrCount() < 3) {
			customOpenInfo.setPushErrCount(customOpenInfo.getPushErrCount() + 1);
		} else {
			// 发送邮件
			if (!customOpenInfo.getIsSend()) {
				// 避免重复发送邮件，已发送邮件的不再发送
				//String title = "用户账号：" + customOpenInfo.getId() + " 开户任务异常";
				//errorMsg = errorMsg + "【" + body + "】";
				//发送邮件通知
				//msgService.sendEmailCloud(sender, accept, title, errorMsg, null);
				customOpenInfo.setIsSend(true);
			}
		}
		customOpenInfoService.updateById(customOpenInfo);
	}

	private void saveOpenAccountSuccessInfo(CustomOpenInfoEntity customOpenInfo, String applicationId) {
		customOpenInfo.setUpdateTime(new Date());
		customOpenInfo.setIsPushed(true);
		customOpenInfo.setIsSend(true);
		customOpenInfo.setRemoteId(applicationId);
		customOpenInfoService.updateById(customOpenInfo);
	}


	@Override
	@PostMapping(DEPOSIT_PUSH)
	public R depositPushTask(Map<String, Object> map) {
		ExecutorService executorService = new ThreadPoolExecutor(10, 20,
			60L, TimeUnit.SECONDS,
			new LinkedBlockingQueue<>(10));
		List<SecDepositFundsVO> list = secDepositFundsService.selectByIsPushedFalse();
		for (SecDepositFundsVO vo : list) {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					iFundDepositInfoService.submitDeposit(vo);
				}
			});
		}
		return R.success();
	}


}
