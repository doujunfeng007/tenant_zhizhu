package com.minigod.zero.cust.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.cust.apivo.req.*;
import com.minigod.zero.biz.common.mq.ConnecterInfo;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.apivo.resp.CustDetailVO;
import com.minigod.zero.cust.entity.CustInfoEntity;

import java.util.List;

/**
 * 客户信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
public interface ICustInfoService extends BaseService<CustInfoEntity> {

	IPage<CustInfoEntity> selectCustInfoPage(IPage<CustInfoEntity> page, CustInfoQueryReq custInfo);

	/**
	 * 查询用户信息
	 * @param custId
	 * @return
	 */
	CustInfoEntity selectCustInfoById(Long custId);

	/**
	 *修改登录密码
	 * @param vo
	 * @return
	 */
	R updatePwd(ReqUpdatePwdVO vo);

	/**
	 *重置登录密码
	 * @param vo
	 * @return
	 */
	R resetPwd(ReqUpdatePwdVO vo);

	/**
	 * 更新用户信息并且更新缓存
	 */
	void updateCustInfoAndCache(CustInfoEntity custInfo);

	/**
	 * 获取用户详细信息
	 * @return
	 */
	R<CustDetailVO> fetchUserInfo(Long custId);

	/**
	 * 设置用户昵称
	 * @param nickName
	 * @return
	 */
	R<CustDetailVO> setNickName(NickNameVO nickName);

	/**
	 * 绑定手机号
	 * @param phoneNumber
	 * @param areaCode
	 * @param custId
	 * @return
	 */
    R toBindPhoneNumber(String phoneNumber, String areaCode, Long custId);

	/**
	 * 手机登录后绑定交易账户，或者交易账户登陆后绑定手机号，使用交易账户的数据，将手机设置到交易账户的custInfo中,
	 * @param custId 手机用户custId，该数据将失效
	 * @param phone 手机号
	 * @param area 区号
	 * @param account 交易账户，手机号被绑定到该数据下
	 * @return
	 */
	R phoneDataToAccount(Long custId,String phone,String area,String account);

	/**
	 * 设置用户隐私开关
	 * @param userSwitchVO
	 * @return
	 */
	R setUserSwitch(UserSwitchVO userSwitchVO);

	/**
	 * 校验登录手机号
	 */
	R checkPhone(String areaCode, String phone);

	/**
	 * 修改登录手机号
	 */
	R updatePhone(ReqUpdateCustVO req);

	/**
	 * 校验开户手机号
	 */
	R checkOpenPhone(String areaCode, String phone);

	/**
	 * 修改开户手机号
	 */
	R updateOpenPhone(ReqUpdateCustVO vo);

	/**
	 * 校验登录邮箱
	 */
	R checkEmail(String email);

	/**
	 * 修改登录邮箱
	 */
	R updateEmail(ReqUpdateCustVO req);

	/**
	 * 校验开户邮箱
	 */
	R checkOpenEmail(String email);

	/**
	 * 修改开户邮箱
	 */
	R updateOpenEmail(String key);

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

	/**
	 * 公司户授权人注册
	 * @return
	 */
	R registerAccount(ConnecterInfo connecterInfo);

	/**
	 * 注册智珠客户
	 */
	void saveCustInfo(CustInfoEntity custInfo, Integer sourceType, Integer channel);

	/**
	 * 修改设备下载渠道
	 * @param custId
	 * @param channel
	 */
    void updateChannel(Long custId, String channel);

	/**
	 * ESOP绑定个人账户
	 * @param tradeAccount
	 * @return
	 */
	R esopAcctBind(String tradeAccount);

	R esopCustBind(Long custId);


	/**
	 * 通过CustId修改关联esop账户密码
	 * @param custId
	 * @param password
	 */
	void changeEsopAccountPwdByCustId(Long custId,String password);


	/**
	 * 修改对应bindCust的密码
	 * @param bindCustId
	 * @param hexPassWord 时已经解析后又加密的密码，可以直接写导数据库的密码
	 */
	CustInfoEntity changeBindCustPwd(Long bindCustId,String hexPassWord);

	/**
	 * 该方法只提供给esop用户修改密码和重置密码时一起修改绑定的app密码,在这里不做任何限制,因为在esop已经验证过了
	 * @param custId
	 * @param passWord 未加密的密码
	 */
	void updatePwdByCustId(Long custId,String passWord,Integer changType);

	/**
	 * 通过CustIds集查询列表
	 * @param custIds
	 * @return
	 */
	List<CustInfoEntity> selectCustInfoListByIds(List<Long> custIds);

	/**
	 * 通过cellPhone查询列表
	 * @param cellPhone
	 * @return
	 */
	List<CustInfoEntity> selectCustInfoListByCellPhone(String cellPhone);

	/**
	 * esop修改邮箱同步更新
	 * @param custId
	 * @param email
	 */
	R updateEmailById(Long custId, String email);
}
