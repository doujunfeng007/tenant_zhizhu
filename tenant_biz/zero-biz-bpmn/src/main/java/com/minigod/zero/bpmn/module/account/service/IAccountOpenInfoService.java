package com.minigod.zero.bpmn.module.account.service;

import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.bpmn.module.account.bo.OpenAccountBo;
import com.minigod.zero.bpmn.module.account.bo.OpenAccountCallbackBo;
import com.minigod.zero.bpmn.module.account.bo.OpenSignImgBo;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenApplicationEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountDetailVO;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoVO;
import com.minigod.zero.bpmn.module.account.vo.BackCustomerDetailVO;
import com.minigod.zero.bpmn.module.account.vo.CustomerOpenAccountVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.*;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.R;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 客户开户详细资料
 *
 * @author Chill
 */
public interface IAccountOpenInfoService extends BaseService<AccountOpenInfoEntity> {

	/**
	 * 查询开户信息表
	 *
	 * @param applicationId
	 * @return
	 */
	AccountOpenInfoVO queryByApplicationId(String applicationId);

	/**
	 * 根据E-mail查询开户信息表
	 *
	 * @param email
	 * @return
	 */
	AccountOpenInfoVO queryByEmail(String email);

	/**
	 * 根据Id-No查询开户信息表
	 *
	 * @param idNo
	 * @return
	 */
	AccountOpenInfoVO queryByIdNo(String idNo);

	/**
	 * 获取开户详情信息
	 *
	 * @param applicationId
	 * @return
	 */
	AccountDetailVO queryDetailByApplicationId(String applicationId);

	/**
	 * 根据用户ID查询开户信息
	 *
	 * @param userId
	 */
	AccountOpenInfoVO queryAccountOpenInfoByUserId(Long userId);

	/**
	 * 根据用户ID查询开户信息
	 *
	 * @param userId
	 * @return
	 */
	AccountOpenInfoVO queryByUserId(Long userId);

	/**
	 * 创建aml查询请求
	 *
	 * @param applicationId
	 * @return
	 */
	void amlCreate(String applicationId);

	/**
	 * 深圳CA认证
	 *
	 * @param applicationId
	 */
	void szCaAuth(String applicationId);

	/**
	 * 广东CA认证
	 *
	 * @param applicationId
	 */
	void gdCaAuth(String applicationId);

	void updateClientId(String applicationId, String clientId, Integer idKind);

	/**
	 * 生成客户号
	 *
	 * @param idKind
	 */
	String generateClientId(Integer idKind);

	/**
	 * 下载开户文件
	 *
	 * @param applicationId
	 * @param httpServletResponse
	 */
	void downloadDoc(String applicationId, HttpServletResponse httpServletResponse);

	/**
	 * 生成开户文件
	 *
	 * @param applicationId
	 */
	void generatePdf(String applicationId);

	/**
	 * 生成W8
	 *
	 * @param applicationId
	 */
	void generateW8Pdf(String applicationId);
	/**
	 * 生成自我证明协议
	 *
	 * @param applicationId
	 */
	void generateSelfPdf(String applicationId);

	/**
	 * 生成保证金协议
	 *
	 * @param applicationId
	 */
	void generateMarginPdf(String applicationId);

	/**
	 * 生成归档文件
	 *
	 * @param applicationId
	 */
	void generatePlaceFile(String applicationId);

	/**
	 * 处理开户申请
	 *
	 * @param bo
	 * @return
	 */
	OpenAccountCallbackBo submitInfo(OpenAccountBo bo);

	/**
	 * 开户
	 *
	 * @param applicationId
	 */
	R openAccount(String applicationId);


	Page<CustomerOpenAccountVO> openAccountUserList(String keyword, String startTime, String endTime,
													Page page);

	BackCustomerDetailVO openAccountUserDetail(Long userId);

	R agreementStatus(Integer type);

	R saveSignImg(OpenSignImgBo params);

	List<String> queryW8ApplicationId();

	List<String> querySelfDeclareApplicationId();

	R confirmationProtocol(Integer type);

	List<AccountOpenInfoEntity> selW8ConfirmList(int w8Year);

	List<AccountOpenInfoEntity> selSelfConfirmList(int selfYear);

	R gen(String applicationId, Integer type);

	R w8SelfDeclare();


	Map<String, String> openAccount(AccountOpenInfoVO accountOpenInfoVO,
									List<AccountTaxationInfoVO> accountTaxationInfoVOList,
									List<AccountOpenImageVO> accountOpenImageVOs);
}
