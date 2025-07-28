package com.minigod.zero.customer.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.minigod.common.constant.CommonConstant;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.customer.api.service.AppCustomerInfoService;
import com.minigod.zero.customer.api.service.ICustInfoService;
import com.minigod.zero.customer.dto.*;
import com.minigod.zero.customer.entity.CustomerInfoEntity;
import com.minigod.zero.resource.feign.IOssClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: guangjie.liao
 * @Date: 2024/4/14 14:49
 * @Description: 客户相关接口
 */
@Slf4j
@RestController
public class CustomerController {

	@Autowired
	private AppCustomerInfoService customerInfoService;

	@Resource
	private ICustInfoService custInfoService;

	@Resource
	private IOssClient fileClient;
	/**
	 * 客户注册
	 * @return
	 */
	@PostMapping("/register")
	public R customerRegister(@RequestBody CustomerInfoDTO customerInfo){
		return customerInfoService.customerRegister(customerInfo);
	}

	/**
	 * 租户客户注册
	 * @return
	 */
	@PostMapping("/tenantRegister")
	public R tenantCustomerRegister(@RequestBody CustomerInfoDTO customerInfo){
		return customerInfoService.tenantCustomerRegister(customerInfo);
	}

	/**
	 * 查询客户详情
	 * @param custId
	 * @return
	 */
	@GetMapping("/detail")
	public R selectCustomerDetail(Long custId){
		return customerInfoService.selectCustomerDetail(custId);
	}



	/**
	 * 设置头像
	 * @param file
	 * @return
	 */
	@PostMapping("/upload_user_icon")
	public R uploadUserIcon(@RequestParam("file") MultipartFile file) {
		if (file == null) {
			return R.fail(ResultCode.FAILURE, I18nUtil.getMessage(CommonConstant.UPLOAD_FAILED));
		}
		CustomerInfoEntity custInfo = custInfoService.selectCustInfoById(AuthUtil.getTenantUser().getUserId());
		if (custInfo != null) {
			/**
			 * 上传文件  七牛云 保存图片路径数据库
			 */
			com.minigod.zero.core.tool.api.R<ZeroFile> rt = fileClient.uploadMinioFile(file);
			log.info("更新头像上传返回：{}", JSONObject.toJSONString(rt));
			if (rt.isSuccess()) {
				custInfo.setCustIcon(rt.getData().getLink());
				custInfoService.updateCustInfoAndCache(custInfo);
				return R.success(I18nUtil.getMessage(CommonConstant.AVATAR_UPDATED_SUCCESSFULLY));
			}
			return R.fail(ResultCode.FAILURE,I18nUtil.getMessage(CommonConstant.AVATAR_UPDATED_FAILED));
		} else {
			return R.fail(I18nUtil.getMessage(CommonConstant.ABNORMAL_USER_INFORMATION));
		}
	}

	/**
	 * 设置昵称
	 * @param nickName
	 * @return
	 */
	@PostMapping("/set_nick_name")
	public R setNickName(@RequestBody NickNameDTO nickName) {
		return custInfoService.setNickName(nickName);
	}

	/**
	 * 校验登录手机号
	 * @param req
	 * @return
	 */
	@PostMapping("/check_phone")
	public R checkPhone(@RequestBody ReqUpdateCustDTO req) {
		if (req == null || StringUtil.isAnyBlank(req.getPhone(), req.getAreaCode())) {
			return R.fail(ResultCode.FAILURE,I18nUtil.getMessage(CommonConstant.PHONE_NUMBER_OR_AREA_CODE_CANNOT_BE_EMPTY));
		}
		return custInfoService.checkPhone(req.getAreaCode(), req.getPhone());
	}

	/**
	 * 绑定登录手机号
	 * @param req
	 * @return
	 */
	@PostMapping("/update_phone")
	public R updatePhone(@RequestBody ReqUpdateCustDTO req) {
		if (req == null || StringUtil.isAnyBlank(req.getPhone(), req.getAreaCode(), req.getCaptchaCode(), req.getCaptchaKey())) {
			return R.fail(I18nUtil.getMessage(CommonConstant.PHONE_NUMBER_OR_CODE_CANNOT_BE_EMPTY));
		}
		return custInfoService.updatePhone(req);
	}


	/**
	 * 校验开户手机号
	 * @param req
	 * @return
	 */
	@PostMapping("/check_open_phone")
	public R checkOpenPhone(@RequestBody ReqUpdateCustDTO req) {
		if (req == null || StringUtil.isAnyBlank(req.getPhone(), req.getAreaCode())) {
			return R.fail(I18nUtil.getMessage(CommonConstant.PHONE_NUMBER_OR_AREA_CODE_CANNOT_BE_EMPTY));
		}
		return custInfoService.checkOpenPhone(req.getAreaCode(), req.getPhone());
	}


	/**
	 * 修改开户手机号
	 * @param req
	 * @return
	 */
	@PostMapping("/update_open_phone")
	public R updateOpenPhone(@RequestBody ReqUpdateCustDTO req) {
		if (req == null || StringUtil.isAnyBlank(req.getPhone(), req.getAreaCode(), req.getCaptchaCode(), req.getCaptchaKey())) {
			return R.fail(I18nUtil.getMessage(CommonConstant.PHONE_NUMBER_OR_CODE_CANNOT_BE_EMPTY));
		}
		return custInfoService.updateOpenPhone(req);
	}

	/**
	 * 校验用户证件号
	 * @param idCard
	 * @return
	 */
	@PostMapping("/checkIdCard")
	public R checkIdCard(@RequestBody Map<String,String> idCard) {
		return custInfoService.checkIdCard(idCard.get("idCard"));
	}

	/**
	 * 注销账户
	 * @return
	 */
	@GetMapping("/cancel_cust")
	public R cancelCust() {
		return custInfoService.cancelCust();
	}

	/**
	 * 设置/修改登录密码（通用）
	 * @param vo
	 * @return
	 */
	@PostMapping("/update_pwd")
	public R updatePwd(@RequestBody ReqUpdatePwdDTO vo) {
		if (vo == null || StringUtil.isBlank(vo.getNewPassword())) {
			return R.fail(I18nUtil.getMessage(CommonConstant.NEW_PASSWORD_CANNOT_BE_EMPTY));
		}
		return custInfoService.updatePwd(vo);
	}

	/**
	 * 重置个人户登录密码
	 * @param vo
	 * @return
	 */
	@PostMapping("/reset_pwd")
	public R resetPwd(@RequestBody ReqUpdatePwdDTO vo) {
		if (vo == null || StringUtil.isBlank(vo.getNewPassword()) || StringUtil.isBlank(vo.getCaptchaCode())
			|| StringUtil.isBlank(vo.getCaptchaKey())) {
			return R.fail(I18nUtil.getMessage(CommonConstant.PASSWORD_OR_CODE_CANNOT_BE_EMPTY));
		}
		if (StringUtil.isBlank(vo.getPhone())) {
			return R.fail(I18nUtil.getMessage(CommonConstant.PHONE_NUMBER_CANNOT_BE_EMPTY));
		}
		return custInfoService.resetPwd(vo);
	}


	@GetMapping("/getInfoByAccount")
	public R getCustomerInfoByAccount(String tradeAccount){
		return custInfoService.getCustomerInfoByAccount(tradeAccount);
	}


	@GetMapping("/customer/getCustomer_by_phone")
	public R getCustomerInfoByPhone(String tenantId,String phone,String areaCode){
		return custInfoService.selectCustomerByPhone(tenantId, phone, areaCode);
	}

	/**
	 * 获取用户状态是否满足交易 TA 调用此接口
	 * @return
	 */
	@GetMapping("/status")
	public R getCustomerStatus(String accountId){
		return custInfoService.getCustomerStatus(accountId);
	}

	/**
	 * 账户资料
	 * @param custId
	 * @return
	 */
	@GetMapping("/account_info")
	public R customerAccountInfo(Long custId){
		return custInfoService.customerAccountInfo(custId);
	}

	/**
	 * 密码超时状态
	 * @return
	 */
	@GetMapping("/pwd_status")
	public R pwdStatus() {
		return custInfoService.pwdStatus();
	}

	/**
	 * h5账户信息
	 * @return
	 */
	@GetMapping("/account_asset")
	public R accountAssetInfo(){
		return custInfoService.accountAssetInfo();
	}

	/**
	 * pc获取用户角色
	 * @return
	 */
	@GetMapping("/customer_role")
	public R customerRole(){
		return custInfoService.customerRole();
	}

	/**
	 *
	 * @return
	 */
	@PostMapping("/customer_role")
	public R switchRole(@RequestBody CustomerRoleDTO customerRole){
		return custInfoService.switchRole(customerRole.getRoleId());
	}

	@GetMapping("/organization_account")
	public R getOrganizationAccountByRoleId(Integer roleId){
		return custInfoService.getOrganizationAccountByRoleId(roleId);
	}
}
