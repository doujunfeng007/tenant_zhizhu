package com.minigod.zero.user.proxy;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.user.utils.UserUtils;
import com.minigod.zero.user.vo.*;
import com.minigod.zero.common.cache.CacheNames;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.ZeroUser;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.enums.CustEnums;
import com.minigod.zero.core.tool.utils.DigestUtil;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.feign.ICustAuthClient;
import com.minigod.zero.cust.feign.ICustInfoClient;
import com.minigod.zero.cust.utils.RSANewUtil;
import com.minigod.zero.platform.constants.CommonTemplateCode;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.platform.utils.CheckCaptchaCache;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.user.service.IUserOldPasswordService;
import com.minigod.zero.user.service.IUserService;
import com.minigod.zero.user.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户通用维护接口
 */
@Slf4j
@RestController
@RequestMapping(AppConstant.PROXY_API_PREFIX + "/esop")
@Api(value = "用户信息表", tags = "用户信息表接口")
public class UserEsopController {

	@Resource
	private IUserService userService;
	@Resource
	private IUserOldPasswordService userOldPasswordService;
	@Resource
	private CheckCaptchaCache checkCaptchaCache;
	@Resource
	private IPlatformMsgClient platformMsgClient;
	@Resource
	private ICustAuthClient custAuthClient;
	@Resource
	private ICustInfoClient custInfoClient;
	@Resource
	private ZeroRedis zeroRedis;
	@Value("${esop.loginUrl}")
	private String esopLoginUrl;

	@Value("${client.tenant.id:000001}")
	public String tenantId; //租户ID

	public static final Integer FAIL_COUNT = 5;

	@PostMapping("/batch_regist")
	@ApiOperation(value = "批量注册用户", notes = "批量注册用户")
	public R<Object> batchRegister(@RequestBody List<RegisterReq> registerList) {
		if (CollectionUtils.isEmpty(registerList)) {
			return R.fail("用户注册信息不能为空");
		}
		List<User> userList = new ArrayList<>();
		List<RegisterResp> respList = new ArrayList<>();
		List<String> accountList = new ArrayList<>();
		List<String> emailList = new ArrayList<>();

		for (RegisterReq registerReq : registerList) {
			RegisterResp resp = new RegisterResp();
			if (StringUtil.isBlank(registerReq.getEmail())) {
				log.warn("邮箱为空，跳过注册");
				continue;
			}

			String[] areaAndphoneNumber = parsePhone(registerReq.getPhone());
			if (null==areaAndphoneNumber){
				return R.fail("手机号格式必须为:区号-手机号");
			}else {
				registerReq.setArea(areaAndphoneNumber[0]);
				registerReq.setPhone(areaAndphoneNumber[1]);
			}

			if (StringUtil.isBlank(registerReq.getTenantId())){
				registerReq.setTenantId(tenantId);
			}

			resp.setEmail(registerReq.getEmail());
			/**
			 * 查询邮箱是否已注册
			 */
			User userInfo = userService.getBaseMapper().selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserType, 4).eq(User::getEmail, registerReq.getEmail()));
			if (userInfo != null) {
				resp.setAccount(userInfo.getAccount());
				resp.setResult(2);
				respList.add(resp);
				continue;
			}
			if (emailList.contains(registerReq.getEmail())) {
				return R.fail("存在重复邮箱，请检查");
			}
			emailList.add(registerReq.getEmail());
			userInfo = initUserInfo(registerReq);
			// 根据真实姓名自动生成拼音账号
			String account = UserUtils.generateAccount(registerReq.getRealName());
			String password = UserUtils.generatePassword(8);// 生成初始密码
			// 账号重名处理
			Long count = userService.getBaseMapper().selectCount(Wrappers.<User>lambdaQuery().eq(User::getTenantId, registerReq.getTenantId()).likeRight(User::getAccount, account));
			if (count > 0) {
				// 如果账号已存在，则添加两位数字后缀
				account += String.format("%03d", count + accountList.size() + 1);
			}
			accountList.add(account);
			userInfo.setUserType(4);// ESOP员工
			userInfo.setAccount(account);
			userInfo.setPassword(DigestUtil.hex(password));
			userList.add(userInfo);
			// 封装响应数据
			resp.setAccount(userInfo.getAccount());
			resp.setPassword(password);
			respList.add(resp);
		}
		if (userService.saveBatch(userList)) {
			return R.data(respList);
		} else {
			return R.fail("员工批量注册失败");
		}
	}

	//前端需要一个单员工注册的接口
	@PostMapping("/single_regist")
	@ApiOperation(value = "注册单个用户", notes = "注册单个用户")
	public R<Object> singleRegister(@RequestBody RegisterReq registerReq) {
		if (StringUtil.isBlank(registerReq.getEmail())) {
			log.warn("邮箱为空，跳过注册");
			return R.fail("用户注册邮箱不能为空");
		}

		String[] areaAndphoneNumber = parsePhone(registerReq.getPhone());
		if (null==areaAndphoneNumber){
			return R.fail("手机号格式必须为:区号-手机号");
		}else {
			registerReq.setArea(areaAndphoneNumber[0]);
			registerReq.setPhone(areaAndphoneNumber[1]);
		}


		RegisterResp resp = new RegisterResp();

		if (StringUtil.isBlank(registerReq.getTenantId())){
			registerReq.setTenantId(tenantId);
		}

		resp.setEmail(registerReq.getEmail());
		/**
		 * 查询邮箱是否已注册
		 */
		User userInfo = userService.getBaseMapper().selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserType, 4).eq(User::getEmail, registerReq.getEmail()));
		if (userInfo != null) {
			resp.setAccount(userInfo.getAccount());
			resp.setResult(2);
			return R.fail("邮箱已经注册");
		}
		userInfo = initUserInfo(registerReq);


		//创建人信息
		ZeroUser user = AuthUtil.getUser();
		if (null!=user){
			userInfo.setCreateUser(user.getUserId());
			userInfo.setCreateDept(StringUtil.isNotBlank(user.getDeptId())?Long.valueOf(user.getDeptId()):null);
			userInfo.setUpdateUser(user.getUserId());
		}

		// 根据真实姓名自动生成拼音账号
		String account = UserUtils.generateAccount(registerReq.getRealName());
		String password = UserUtils.generatePassword(8);// 生成初始密码
		// 账号重名处理
		Long count = userService.getBaseMapper().selectCount(Wrappers.<User>lambdaQuery().eq(User::getTenantId, registerReq.getTenantId()).likeRight(User::getAccount, account));
		if (count > 0) {
			// 如果账号已存在，则添加两位数字后缀
			account += String.format("%03d", count + 1);
		}
		userInfo.setUserType(4);// ESOP员工
		userInfo.setAccount(account);
		userInfo.setPassword(DigestUtil.hex(password));
		//增加证件号码
		userInfo.setIdcardType(registerReq.getIdCardType());
		userInfo.setIdcardNo(registerReq.getIdCardNo());
		// 封装响应数据
		resp.setAccount(userInfo.getAccount());
		resp.setPassword(password);
		if (userService.save(userInfo)){
			// 封装响应数据
			return R.data(resp);
		}else {
			return R.fail("员工批量注册失败");
		}
	}

	@PostMapping("/manager_regist")
	@ApiOperation(value = "注册管理员用户", notes = "管理员用户注册")
	public R<Object> managerRegister(@RequestBody RegisterReq registerReq) {
		if (registerReq == null || StringUtil.isBlank(registerReq.getEmail())) {
			return R.fail("用户注册邮箱不能为空");
		}

		if (StringUtil.isBlank(registerReq.getTenantId())){
			registerReq.setTenantId(tenantId);
		}

		RegisterResp resp = new RegisterResp();
		resp.setEmail(registerReq.getEmail());

		/**
		 * 查询邮箱是否已注册
		 */
		User userInfo = userService.getBaseMapper().selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserType, 3).eq(User::getEmail, registerReq.getEmail()));
		if (userInfo != null) {
			return R.fail("注册失败：邮箱已被注册");
		}

		//初始化用户信息
		userInfo = initUserInfo(registerReq);

		// 根据真实姓名自动生成拼音账号
		String account = UserUtils.generateAccount(registerReq.getRealName());
		String password = UserUtils.generatePassword(8);// 生成初始密码

		// 账号重名处理
		Long count = userService.getBaseMapper().selectCount(Wrappers.<User>lambdaQuery().eq(User::getTenantId, registerReq.getTenantId()).likeRight(User::getAccount, account));
		if (count > 0) {
			// 如果账号已存在，则添加两位数字后缀
			account += String.format("%03d", count + 1);
		}

		//设置用户类型账号密码
		userInfo.setUserType(3);// ESOP HR
		userInfo.setAccount(account);
		userInfo.setPassword(DigestUtil.hex(password));

		//创建人信息
		ZeroUser user = AuthUtil.getUser();
		if (null!=user){
			userInfo.setCreateUser(user.getUserId());
			userInfo.setCreateDept(StringUtil.isNotBlank(user.getDeptId())?Long.valueOf(user.getDeptId()):null);
			userInfo.setUpdateUser(user.getUserId());
		}

		if (userService.save(userInfo)) {
			this.sendEsopRegisterUserEmail(password,userInfo.getAccount(),userInfo.getEmail());
			// 封装响应数据
			resp.setAccount(userInfo.getAccount());
			resp.setPassword(password);
			resp.setResult(1);
			return R.data(resp);
		} else {
			return R.fail("管理员注册失败");
		}
	}


	@PostMapping("/update_email")
	@ApiOperation(value = "更新邮箱", notes = "更新邮箱")
	public R<Object> updateEmail(@RequestBody UpdateEmailReq req) {
		String loginEmail = req.getLoginEmail();
		String email = req.getEmail();
		Integer userType = req.getUserType();
		if (StringUtil.isBlank(loginEmail)){
			return R.fail("登录邮箱不能为空");
		}
		if (StringUtil.isBlank(email)){
			return R.fail("新邮箱不能为空");
		}
		if (null==userType){
			return R.fail("用户类型不能为空");
		}
		if (loginEmail.equals(email)){
			return R.fail("新旧邮箱不能一致");
		}
		log.info("登陆邮箱:{},修改邮箱:{},用户类型:{}",loginEmail,email,userType);

		User userInfo = userService.getBaseMapper().selectOne(
			Wrappers.<User>lambdaQuery()
			.eq(User::getEmail,loginEmail).eq(User::getUserType,userType));

		if (null==userInfo){
			return R.fail("用户数据异常");
		}

		//查询该邮箱是否被占用
		User userInfoByEmail = userService.getBaseMapper().selectOne(
			Wrappers.<User>lambdaQuery().eq(User::getEmail,email)
			.eq(User::getUserType,userType)
		);

		if (null!=userInfoByEmail){
			return R.fail("该邮箱已经被使用");
		}

		//邮箱可用
		userInfo.setEmail(email);
		int isUpdate = userService.getBaseMapper().updateById(userInfo);

		if (isUpdate==1){
			//如果是管理员并且custId不为空,同步更新app密码
			if (userType==4&&null!=userInfo.getCustId()){
				R r = custInfoClient.updateEmailById(userInfo.getCustId(), email);
				int code = r.getCode();

				if (code==238001){
					log.info("{}esop邮箱更新成功,该用户在app无绑定账号",userInfo.getId());
					return R.success();
				}else if (code==238002){
					log.info("{}在app存在账号,但不是esop用户",userInfo.getId());
					return R.success();
				}else if (code==238003){
					log.info("{}esop邮箱更新成功,app绑定邮箱已被使用",userInfo.getId());
					return R.success();
				}else if (code==200){
					log.info("{}esop邮箱和app邮箱同步更新",userInfo.getId());
					return R.success();
				}else {
					log.info("app账户邮箱同步更新出现错误");
					return R.fail("app账户邮箱同步更新出现错误");
				}
			}else {
				log.info("{}邮箱更新成功,用户类型为{}",userInfo.getId(),userType);
				return R.success();
			}
		}else {
			return R.fail("更新邮箱失败");
		}


	}

	// 注册新用户
	private User initUserInfo(@RequestBody RegisterReq registerReq) {
		User userInfo = new User();
		userInfo.setTenantId(registerReq.getTenantId());
		userInfo.setEmail(registerReq.getEmail());
		userInfo.setArea(registerReq.getArea());
		userInfo.setPhone(registerReq.getPhone());
		userInfo.setRealName(registerReq.getRealName());
		userInfo.setSex(registerReq.getSex());
		userInfo.setStatus(1);
		userInfo.setCreateTime(new Date());
		userInfo.setCreateUser(AuthUtil.getUserId());
		//增加证件号码
		userInfo.setIdcardType(registerReq.getIdCardType());
		userInfo.setIdcardNo(registerReq.getIdCardNo());
		return userInfo;
	}

	/**
	 *
	 * @param password 初始密码
	 * @param account 账号名称
	 * @param email 注册邮箱号
	 */
	private void sendEsopRegisterUserEmail(String password,String account,String email){
		// 发送初始密码通知邮件
		SendEmailDTO sendDto = new SendEmailDTO();

		//接收人
		List<String> accepts = new ArrayList<>();
		accepts.add(email);
		sendDto.setAccepts(accepts);

		sendDto.setCode(CommonTemplateCode.Email.ESOP_ADMIN_INITIAL_PASSWORD);

		//模板参数
		List<String> params = new ArrayList<>();
		params.add(email);
		params.add(password);
		params.add(esopLoginUrl);
		sendDto.setList(params);

		sendDto.setLang(WebUtil.getLanguage());

		platformMsgClient.sendEmail(sendDto);
	}



	@PostMapping("/update_pwd")
	@ApiOperation(value = "修改登录密码", notes = "修改登录密码")
	public R updatePwd(@RequestBody UpdatePwdReq req) {
		if (req == null || StringUtil.isAnyBlank(req.getOldPassword(), req.getNewPassword())) {
			return R.fail("密码不能为空，请重新录入");
		}
		User userInfo = userService.getBaseMapper().selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserType, req.getUserType()).eq(User::getEmail, req.getEmail()));
		if (userInfo == null) {
			return R.fail("客户数据异常，请联系客服咨询");
		}
		// 用户是否已被停用
		if (userInfo.getStatus().equals(CustEnums.CustStatus.DISABLE.getCode())) {
			return R.fail("用户已被停用，无法操作");
		}
		// 修改密码需校验旧密码，未设置过密码可不校验
		R result = this.checkOldPassword(userInfo.getAccount(), req.getOldPassword(), userInfo.getPassword());
		if (!result.isSuccess()) {
			return result;
		}
		userInfo.setPassword(DigestUtil.hex(RSANewUtil.decrypt(req.getNewPassword())));
		userInfo.setUpdateTime(new Date());
		userService.updateById(userInfo);

		// 重新激活存量客户，SupperApp客户迁移临时过渡
		userOldPasswordService.activeLoginPwd(userInfo.getAccount());

		//同步修改智珠的
		if (null!=userInfo.getCustId()){
			custInfoClient.updateCustpwdById(userInfo.getCustId(),req.getNewPassword(),null);
		}

		return R.success();
	}

	@PostMapping("/reset_pwd")
	@ApiOperation(value = "用户找回登录密码", notes = "找回登录密码")
	public R resetPwd(@RequestBody UpdatePwdReq req) {
		if (req == null || StringUtil.isAnyBlank(req.getEmail(), req.getNewPassword(), req.getCaptchaCode())) {
			return R.fail("邮箱、密码及验证码参数缺失！");
		}
		/**
		 * 校验授权人邮件验证码
		 */
		User userInfo = userService.getBaseMapper().selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserType, req.getUserType()).eq(User::getEmail, req.getEmail()));
		if (userInfo == null) {
			return R.fail("未注册邮箱，请检查后重试");
		}
		if (!checkCaptchaCache.checkEmailCaptcha(req.getEmail().trim().toLowerCase(), req.getCaptchaCode(), 3008, req.getCaptchaKey())) {
			return R.fail("验证码校验未通过");
		}
		// 解除锁定状态
		userInfo.setStatus(CustEnums.CustStatus.ENABLE.getCode());
		userInfo.setPassword(DigestUtil.hex(RSANewUtil.decrypt(req.getNewPassword())));
		userInfo.setUpdateTime(new Date());
		userService.updateById(userInfo);

		// 重新激活存量客户，SupperApp客户迁移临时过渡
		userOldPasswordService.activeLoginPwd(userInfo.getAccount());

		//同步修改智珠的
		if (null!=userInfo.getCustId()){
			custInfoClient.updateCustpwdById(userInfo.getCustId(),req.getNewPassword(),2);
		}

		//重置密码，清空账号错误次数
		zeroRedis.del(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, req.getEmail()));
		return R.success();
	}

	private R checkOldPassword (String account, String rsaPwd, String shaPwd) {
		// 解析密码
		String password = RSANewUtil.decrypt(rsaPwd);
		if (DigestUtil.hex(password).equals(shaPwd) || DigestUtil.md5Hex(password).equals(shaPwd)) {
			return R.success();
		}
		if (userOldPasswordService.checkOldLoginPwd(account, password)) {
			log.info("根据存量客户密码校验通过，account: {}", account);
			return R.success();
		} else {
			return R.fail("登录密码校验失败");
		}
	}

	/**
	 * esop账户激活注册
	 */
	@PostMapping("/esop_register")
	@ApiOperation(value = "esop账户激活注册", notes = "esop账户激活注册")
	public R esopRegister(@RequestBody RegisterReq req) {
		User userInfo = userService.getBaseMapper().selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserType, 4).eq(User::getEmail, req.getEmail()));
		if (userInfo == null) {
			return R.fail("未注册邮箱，请检查后重试");
		}
		com.minigod.zero.cust.apivo.req.RegisterReq registerReq = new com.minigod.zero.cust.apivo.req.RegisterReq();
		registerReq.setUsername(req.getEmail());
		registerReq.setPassword(userInfo.getPassword());
		registerReq.setAreaCode(userInfo.getArea());
		registerReq.setPhoneNum(userInfo.getPhone());
		R<Object> reuslt = custAuthClient.esopCustRegister(registerReq);
		if (reuslt.isSuccess()){
			if (reuslt.getData()!=null){
				Long custId = Long.valueOf(String.valueOf(reuslt.getData()));
				userInfo.setCustId(custId);
				userService.getBaseMapper().updateById(userInfo);
			}
		}
		return reuslt;
	}


	/**
	 * esop管理员密码校验
	 */
	@PostMapping("/manager_valid_pwd")
	@ApiOperation(value = "esop管理员密码校验", notes = "esop管理员密码校验")
	public R managerValidPwd(@Valid @RequestBody ValidPwdReq req) {
		// 账号冻结逻辑与登录时逻辑保持一致
		int count = Func.toInt(zeroRedis.get(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, req.getEmail())), 0);
		if(count >= FAIL_COUNT){
			return R.fail("账号已冻结,请重置密码后再试");
		}
		User userInfo = userService.getBaseMapper().selectOne(Wrappers.<User>lambdaQuery().in(User::getUserType, 3).eq(User::getEmail, req.getEmail()));
		if (userInfo == null) {
			return R.fail("未注册邮箱，请检查后重试");
		}
		//校验密码
		R check = this.checkOldPassword(userInfo.getAccount(), req.getPassword(), userInfo.getPassword());
		if(!check.isSuccess()){
			zeroRedis.setEx(CacheNames.tenantKey(tenantId, CacheNames.USER_FAIL_KEY, req.getEmail()), count + 1, Duration.ofMinutes(30));
		}
		return check;
	}


	private String[] parsePhone(String phone){
		if (StringUtil.isBlank(phone)){
			return null;
		}
		String[] res = phone.split("-");
		if (res.length<2){
			return null;
		}else {
			res[0]=res[0].replace("+","");
			return res;
		}

	}
}
