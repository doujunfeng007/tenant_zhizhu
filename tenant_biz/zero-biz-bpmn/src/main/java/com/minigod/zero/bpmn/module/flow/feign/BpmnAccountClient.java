package com.minigod.zero.bpmn.module.flow.feign;

import static com.minigod.zero.bpmn.module.account.enums.AccountPdfEnum.ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.minigod.zero.biz.common.utils.FileUtil;
import com.minigod.zero.bpmn.module.account.bo.OpenAccountCallbackBo;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenApplicationEntity;
import com.minigod.zero.bpmn.module.account.entity.AccountAdditionalFileEntity;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenInfoEntity;
import com.minigod.zero.bpmn.module.account.enums.AccountPdfEnum;
import com.minigod.zero.bpmn.module.account.mapper.AccountAdditionalFileMapper;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenApplicationMapper;
import com.minigod.zero.bpmn.module.account.mapper.AccountOpenInfoMapper;
import com.minigod.zero.bpmn.module.account.service.IAccountAdditionalFileService;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenApplicationService;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenInfoService;
import com.minigod.zero.bpmn.module.account.service.impl.CustomerAccOpenReportGenerate;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoVO;
import com.minigod.zero.bpmn.module.feign.IBpmnAccountClient;
import com.minigod.zero.bpmn.utils.FileUtils;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.flow.core.common.constant.TaskConstants;
import com.minigod.zero.flow.core.common.enums.FlowComment;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.bpmn.module.account.bo.OpenAccountCallbackBo;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenApplicationEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoVO;
import com.minigod.zero.bpmn.module.feign.IBpmnAccountClient;
import com.minigod.zero.platform.enums.InformEnum;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.enums.PushTemplate;
import com.minigod.zero.platform.enums.PushTypeEnum;
import com.minigod.zero.platform.utils.PushUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static com.minigod.zero.bpmn.module.account.enums.AccountPdfEnum.ACCOUNT_OPEN_REPORT_USER_FORM_REPORT;
import static com.minigod.zero.bpmn.module.account.enums.AccountPdfEnum.ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT;

/**
 * @ClassName: BpmnAccountClient
 * @Description:
 * @Author chenyu
 * @Date 2024/2/23
 * @Version 1.0
 */
@Slf4j
@AllArgsConstructor
@RestController
public class BpmnAccountClient implements IBpmnAccountClient {
	private final IAccountOpenApplicationService accountOpenApplicationService;
	private final IAccountOpenInfoService accountOpenInfoService;
	private final CustomerAccOpenReportGenerate customerAccOpenReportGenerate;
	private final IAccountAdditionalFileService accountAdditionalFileService;
	private final IFlowClient flowClient;


	private final AccountAdditionalFileMapper accountAdditionalFileMapper;
	@Override
	@PostMapping(CA_AUTH)
	public R caAuthTask(Map<String, Object> map) {
		List<AccountOpenApplicationEntity> list = accountOpenApplicationService.queryListByNode("CA认证");
		for (AccountOpenApplicationEntity application : list) {
			try {
				if (null != map && (null != map.get("skip") && (map.get("skip").toString().equals("true") || map.get("skip").toString().equals("1")))) {
					log.info("-->CA认证定时器变量配置值:{}", JSONObject.toJSONString(map));
					log.info("---------跳过CA认证--------");
					flowClient.completeTask(application.getTaskId(), "跳过CA认证", new HashMap<>());
				} else {
					log.info("-->CA认证定时器变量配置值:{}", JSONObject.toJSONString(map));
					//accountOpenInfoService.szCaAuth(application.getApplicationId());
					// 继续使用这个配置值进行操作
					log.info("---------开始执行CA认证--------");
					accountOpenInfoService.gdCaAuth(application.getApplicationId());
					log.info("---------执行CA认证成功--------");
					flowClient.completeTask(application.getTaskId(), "CA认证成功", new HashMap<>());
				}
			} catch (Exception e) {
				log.error(application.getApplicationId() + "CA认证失败->", e);
				//记录失败或异常详情到任务中
				flowClient.taskComment(application.getTaskId(), FlowComment.NORMAL.getType(), "CA认证失败->" + e.getMessage());
				//CA认证失败自动退回到资料终审
				accountOpenApplicationService.rejectPreNode(application.getApplicationId(), application.getTaskId(), "CA认证失败->" + e.getMessage());
			}
		}
		return R.success();
	}

	@Override
	@PostMapping(AML_CHECK)
	public R amlCheckTask(Map<String, Object> map) {
		List<AccountOpenApplicationEntity> list = accountOpenApplicationService.queryAmlChecking();
		for (AccountOpenApplicationEntity application : list) {
			try {
				accountOpenApplicationService.updateRefKeyByApplicationId(application.getApplicationId(), UUID.randomUUID().toString(), "2");
				flowClient.taskComment(application.getTaskId(), FlowComment.NORMAL.getType(), "默认AML检查成功");
			} catch (Exception e) {
				log.error(application.getApplicationId() + "AML检查失败:", e);
			}

		}
		return R.success();
	}

	@Override
	@PostMapping(PLACE)
	public R placeTask(Map<String, Object> map) {
		List<AccountOpenApplicationEntity> list = accountOpenApplicationService.queryListByNode("归档");
		for (AccountOpenApplicationEntity application : list) {
			try {
				String path = customerAccOpenReportGenerate.makeOutputPath(application.getApplicationId(), ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT);
				if (StringUtils.isNotBlank(path)) {
					accountAdditionalFileService.deleteByApplicationAndTypeAndFileType(application.getApplicationId(), 3, ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT.getValue());
					accountAdditionalFileService.uploadAdditionalFile(application.getApplicationId(), 3, ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT.getValue(), ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT.getName(), FileUtil.getMultipartFile(FileUtils.file(path)));
				}
			} catch (Exception e) {
				log.error("归档失败", e);
			}
		}

		for (AccountOpenApplicationEntity application : list) {
			AccountOpenInfoVO customerAccountOpenInfo = accountOpenInfoService.queryByApplicationId(application.getApplicationId());
			accountOpenInfoService.generatePlaceFile(application.getApplicationId());
			Map variable = new HashMap();
			try {
				variable.put(TaskConstants.STATUS, 1);
				variable.put(TaskConstants.FAIL_NUM, 0);
				OpenAccountCallbackBo bo = new OpenAccountCallbackBo();
				bo.setUserId(customerAccountOpenInfo.getUserId());
				bo.setStatus(3);
				bo.setApplicationId(customerAccountOpenInfo.getApplicationId());
				accountOpenApplicationService.callBackApplication(bo);
				flowClient.completeTask(application.getTaskId(), "归档成功", variable);
			} catch (Exception e) {
				variable.put(TaskConstants.STATUS, 0);
				variable.put(TaskConstants.FAIL_NUM, 1);
				flowClient.taskComment(application.getTaskId(), FlowComment.EXCEPTION.getType(), "归档异常");
			}
		}
		return R.success();
	}

	@Override
	@PostMapping(OPEN)
	public R openTask(Map<String, Object> map) {
		List<AccountOpenApplicationEntity> list = accountOpenApplicationService.queryListByNode("开户");
		for (AccountOpenApplicationEntity application : list) {
			try {
				R result = accountOpenInfoService.openAccount(application.getApplicationId());
				if (result.isSuccess()) {
					R r = flowClient.completeTask(application.getTaskId(), "开户成功", new HashMap<>());
					if (!r.isSuccess()) {
						log.error("流程ID:【" + application.getApplicationId() + "】执行<completeTask>方法失败，开户失败->" + r.getMsg());
						throw new ServiceException(r.getMsg());
					} else {
						log.info("流程ID:【" + application.getApplicationId() + "】开户成功->" + r.getMsg());
					}
				} else {
					log.error("流程ID:【" + application.getApplicationId() + "】执行<openAccount>方法失败，开户失败->" + result.getMsg());
					throw new ServiceException(result.getMsg());
				}
			} catch (Exception e) {
				log.error("流程ID:【" + application.getApplicationId() + "】开户异常,异常详情->", e);
				flowClient.taskComment(application.getTaskId(), FlowComment.EXCEPTION.getType(), "开户异常" + e.getMessage());
			}
		}
		return R.success();
	}

	/**
	 * W8确认
	 * @param map
	 * @return
	 */
	@Override
	public R w8ConfirmTask(Map<String, Object> map) {
		int w8Year= 3;
		//获取需要确认的用户
		List<AccountOpenInfoEntity> w8list = accountOpenInfoService.selW8ConfirmList(w8Year);
		for (AccountOpenInfoEntity info : w8list) {
			Long userId = info.getUserId();
			List<AccountAdditionalFileEntity> accountAdditionalFileEntities = accountAdditionalFileMapper.queryListByType(userId, AccountPdfEnum.ACCOUNT_OPEN_REPORT_USER_W8_REPORT.getValue());
			if (accountAdditionalFileEntities.size() == 0) {
				continue;
			}
			String filePath = accountAdditionalFileEntities.get(0).getFilePath();
			if (StringUtils.isBlank(filePath)) {
				continue;
			}
			List<String> params = new ArrayList<>();
			params.add(filePath);
			log.info("发送W8确认通知，用户ID：{},文件路径：{}", userId, filePath);
			//发送消息通知
			PushUtil.builder()
				.msgGroup("P")
				.custId(userId)
				.params(params)
				.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
				.pushType(PushTypeEnum.WEAK_MSG.getTypeValue())
				.templateCode(PushTemplate.W8BEN_EXPIRED_NOTIFICATION.getCode())
				.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
				.pushAsync();


		}

		int selfYear= 1;
		//获取需要确认的用户 自我证明
		List<AccountOpenInfoEntity> selfList = accountOpenInfoService.selSelfConfirmList(selfYear);
		for (AccountOpenInfoEntity info : selfList) {
			Long userId = info.getUserId();
			List<AccountAdditionalFileEntity> accountAdditionalFileEntities = accountAdditionalFileMapper.queryListByType(userId, AccountPdfEnum.ACCOUNT_OPEN_REPORT_USER_SELF_PROVE_REPORT.getValue());
			if (accountAdditionalFileEntities.size() == 0) {
				continue;
			}
			String filePath = accountAdditionalFileEntities.get(0).getFilePath();
			if (StringUtils.isBlank(filePath)) {
				continue;
			}
			List<String> params = new ArrayList<>();
			params.add(filePath);
			log.info("发送自我证明确认通知，用户ID：{},文件路径：{}", userId, filePath);
			//发送消息通知
			PushUtil.builder()
				.msgGroup("P")
				.custId(userId)
				.params(params)
				.group(MsgStaticType.DisplayGroup.SERVICE_MSG)
				.pushType(PushTypeEnum.WEAK_MSG.getTypeValue())
				.templateCode(PushTemplate.SELF_CERTIFICATION_EXPIRED_NOTIFICATION.getCode())
				.sendWay(InformEnum.SendWayTimeEnum.FORTHWITH.getTypeCode())
				.pushAsync();
		}
		return null;
	}
}
