package com.minigod.zero.customer.api.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.dto.NickNameDTO;
import com.minigod.zero.customer.dto.ReqUpdateCustDTO;
import com.minigod.zero.customer.dto.ReqUpdatePwdDTO;
import com.minigod.zero.customer.dto.UserSwitchDTO;
import com.minigod.zero.customer.entity.CustomerInfoEntity;
import com.minigod.zero.customer.vo.ClientAccountVO;
import com.minigod.zero.customer.vo.CustDetailVO;
import com.minigod.zero.customer.vo.PushMessageVO;


/**
 * 客户信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
public interface ICustInfoService extends BaseService<CustomerInfoEntity> {

	/**
	 * 查询用户信息
	 * @param custId
	 * @return
	 */
	CustomerInfoEntity selectCustInfoById(Long custId);

	/**
	 *修改登录密码
	 * @param vo
	 * @return
	 */
	R updatePwd(ReqUpdatePwdDTO vo);

	/**
	 *重置登录密码
	 * @param vo
	 * @return
	 */
	R resetPwd(ReqUpdatePwdDTO vo);

	/**
	 * 重置交易密码
	 * @param vo
	 * @return
	 */
	R resetTradePwd(ReqUpdatePwdDTO vo);

	/**
	 * 更新用户信息并且更新缓存
	 */
	void updateCustInfoAndCache(CustomerInfoEntity custInfo);
	/**
	 * 设置用户昵称
	 * @param nickName
	 * @return
	 */
	R<CustDetailVO> setNickName(NickNameDTO nickName);
	/**
	 * 设置用户隐私开关
	 * @param userSwitchVO
	 * @return
	 */
	R setUserSwitch(UserSwitchDTO userSwitchVO);

	/**
	 * 校验登录手机号
	 */
	R checkPhone(String areaCode, String phone);

	/**
	 * 修改登录手机号
	 */
	R updatePhone(ReqUpdateCustDTO req);

	/**
	 * 校验开户手机号
	 */
	R checkOpenPhone(String areaCode, String phone);

	/**
	 * 修改开户手机号
	 */
	R updateOpenPhone(ReqUpdateCustDTO vo);

	/**
	 * 校验用户证件号
	 * @param idCard
	 * @return
	 */
	R checkIdCard(String idCard);

	/**
	 * 账户注销
	 * @return
	 */
	R cancelCust();

	R<ClientAccountVO> accountInfo();

	void updateChannel(Long custId, String channel);

	R updateTradPwd(ReqUpdatePwdDTO dto);

	R checkTradPwd(ReqUpdatePwdDTO dto);

	R checkTradPwd(String password,Long custId);

	R getCustomerInfoByAccount(String tradeAccount);

	R selectCustomerByPhone(String tenantId,String phone,String areaCode);

	R getCustomerStatus(String accountId);

	R customerAccountInfo(Long custId);

	R pwdStatus();

	void pushMessage(PushMessageVO pushMessageVO);

	R accountAssetInfo();

	R customerRole();

	R switchRole(Integer roleId);

	R getOrganizationAccountByRoleId(Integer roleId);
}
