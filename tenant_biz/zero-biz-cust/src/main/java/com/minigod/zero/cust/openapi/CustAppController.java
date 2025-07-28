package com.minigod.zero.cust.openapi;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.CommonParams;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.cms.feign.oms.ILanguageClient;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.enums.CustEnums;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.apivo.req.NickNameVO;
import com.minigod.zero.cust.apivo.req.ReqUpdateCustVO;
import com.minigod.zero.cust.apivo.req.UserSwitchVO;
import com.minigod.zero.cust.apivo.resp.CustDetailVO;
import com.minigod.zero.cust.cache.AcctInfo;
import com.minigod.zero.cust.entity.CustDeviceEntity;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.enums.CustStaticType;
import com.minigod.zero.cust.service.IAccountLoginService;
import com.minigod.zero.cust.service.ICustAccountInfoService;
import com.minigod.zero.cust.service.ICustDeviceService;
import com.minigod.zero.cust.service.ICustInfoService;
import com.minigod.zero.cust.vo.TradeUnlockReq;
import com.minigod.zero.cust.vo.icbc.ClientAccount;
import com.minigod.zero.cust.vo.icbc.UserInfo;
import com.minigod.zero.cust.vo.icbc.W8InfoVO;
import com.minigod.zero.platform.utils.CheckCaptchaCache;
import com.minigod.zero.resource.feign.IOssClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.openapi.UserController
 * @Date: 2023年02月13日 18:05
 * @Description:
 */
@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX)
@Api(value = "客户中心接口", tags = "客户中心接口")
public class CustAppController extends ZeroController {

	@Resource
	private  ICustInfoService custInfoService;
	@Resource
	private IOssClient ossClient;
	@Resource
	private ICustDeviceService custDeviceService;
	@Resource
	private IAccountLoginService accountLoginService;
	@Resource
	private ICustAccountInfoService custAccountInfoService;
	@Resource
	private CheckCaptchaCache checkCaptchaCache;
	@Resource
	private ILanguageClient languageClient;

	@PostMapping("/upload_device")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "设备上报", notes = "注册极光后上传设备信息")
	public R<Kv> uploadDeviceInfo(@RequestBody CustDeviceEntity deviceInfo) {
		if (deviceInfo == null || deviceInfo.getOsType() == null || StringUtil.isBlank(deviceInfo.getDeviceCode())) {
			return R.fail(ResultCode.PARAM_MISS.getMessage());
		}
		if (StringUtil.isBlank(deviceInfo.getOpenCode())) {
			return R.fail("设备上报失败：缺少极光ID");
		}
		if (custDeviceService.custDeviceReport(deviceInfo)) {
			return R.success("设备上报成功");
		}
		return R.fail("设备上报失败");
	}


	@PostMapping("/upload_user_icon")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "设置用户图像", notes = "传入file")
	public R uploadUserIcon(@RequestParam("file") MultipartFile file) {
		if (file == null) {
			return R.fail(CustStaticType.CodeType.UPLOAD_FILE_ERROR.getCode(), CustStaticType.CodeType.UPLOAD_FILE_ERROR.getMessage());
		}
		CustInfoEntity custInfo = custInfoService.selectCustInfoById(AuthUtil.getCustId());
		if (custInfo != null) {
			/**
			 * 上传文件  七牛云 保存图片路径数据库
			 */
			R<ZeroFile> rt = ossClient.uploadQiniuFile(file, file.getOriginalFilename());
			if (rt.isSuccess()) {
				custInfo.setCustIcon(rt.getData().getLink());
				custInfoService.updateCustInfoAndCache(custInfo);
				return R.success("头像更新成功");
			}
			return R.fail("头像更新失败");
		} else {
			return R.fail("未获取到用户信息");
		}
	}

	@PostMapping("/set_nick_name")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "设置用户昵称,签名，性别", notes = "设置用户昵称")
	public R setNickName(@RequestBody NickNameVO nickName) {
		return custInfoService.setNickName(nickName);
	}

	@PostMapping("/set_user_switch")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "设置用户隐私开关值", notes = "修改用户隐私开关值")
	public R setUserSwitch(@RequestBody UserSwitchVO userSwitchVO) {
		return custInfoService.setUserSwitch(userSwitchVO);
	}

	@PostMapping("/check_phone")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "校验登录手机号", notes = "校验登录手机号")
	public R checkPhone(@RequestBody ReqUpdateCustVO req) {
		if (req == null || StringUtil.isAnyBlank(req.getPhone(), req.getAreaCode())) {
			return R.fail("手机号和区号不能为空");
		}
		return custInfoService.checkPhone(req.getAreaCode(), req.getPhone());
	}

	@PostMapping("/update_phone")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "绑定登录手机号", notes = "修改登录手机号")
	public R updatePhone(@RequestBody ReqUpdateCustVO req) {
		if (req == null || StringUtil.isAnyBlank(req.getPhone(), req.getAreaCode(), req.getCaptchaCode(), req.getCaptchaKey())) {
			return R.fail("手机号和验证码不能为空");
		}
		return custInfoService.updatePhone(req);
	}

	@PostMapping("/check_open_phone")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "校验开户手机号", notes = "校验开户手机号")
	public R checkOpenPhone(@RequestBody ReqUpdateCustVO req) {
		if (req == null || StringUtil.isAnyBlank(req.getPhone(), req.getAreaCode())) {
			return R.fail("手机号和区号不能为空");
		}
		return custInfoService.checkOpenPhone(req.getAreaCode(), req.getPhone());
	}

	@PostMapping("/update_open_phone")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "修改开户手机号", notes = "修改开户手机号")
	public R updateOpenPhone(@RequestBody ReqUpdateCustVO req) {
		if (req == null || StringUtil.isAnyBlank(req.getPhone(), req.getAreaCode(), req.getCaptchaCode(), req.getCaptchaKey())) {
			return R.fail("手机号和验证码不能为空");
		}
		return custInfoService.updateOpenPhone(req);
	}

	@PostMapping("/check_email")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "校验登录邮箱", notes = "校验登录邮箱")
	public R checkEmail(@RequestBody ReqUpdateCustVO req) {
		if (req == null || StringUtil.isAnyBlank(req.getEmail())) {
			return R.fail("登录邮箱不能为空");
		}
		return custInfoService.checkEmail(req.getEmail());
	}

	@PostMapping("/update_email")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "绑定登录邮箱", notes = "修改登录邮箱")
	public R updateEmail(@RequestBody ReqUpdateCustVO req) {
		if (req == null || StringUtil.isAnyBlank(req.getEmail(), req.getCaptchaCode(), req.getCaptchaKey())) {
			return R.fail("邮箱和验证码不能为空");
		}
		return custInfoService.updateEmail(req);
	}

	@PostMapping("/check_open_email")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "校验开户邮箱", notes = "校验开户邮箱")
	public R check0penEmail(@RequestBody ReqUpdateCustVO req) {
		if (req == null || StringUtil.isAnyBlank(req.getEmail())) {
			return R.fail("开户邮箱不能为空");
		}
		return custInfoService.checkOpenEmail(req.getEmail());
	}

	@GetMapping("/update_open_email")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "修改开户邮箱", notes = "修改开户邮箱")
	public R updateOpenEmail(@RequestParam String key) {
		return custInfoService.updateOpenEmail(key);
	}

	@PostMapping("/checkIdCard")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "校验用户证件号", notes = "校验用户证件号")
	public R checkIdCard(@RequestBody Map<String,String> idCard) {
		return custInfoService.checkIdCard(idCard.get("idCard"));
	}

	@GetMapping("/fetch_user_info")
	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "获取用户详细信息", notes = "获取用户详细信息")
	public R<CustDetailVO> fetchUserInfo() {
		return custInfoService.fetchUserInfo(AuthUtil.getCustId());
	}

	@PostMapping("/fetch_user_info")
	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "获取用户详细信息", notes = "获取用户详细信息")
	public R<CustDetailVO> fetchUserInfo(CommonParams params) {
		return custInfoService.fetchUserInfo(AuthUtil.getCustId());
	}

	@GetMapping("/cancel_cust")
	@ApiOperationSupport(order = 18)
	@ApiOperation(value = "注销账户", notes = "账户注销")
	public R cancelCust() {
		return custInfoService.cancelCust();
	}

	@PostMapping("/esop_acct_bind")
	@ApiOperationSupport(order = 19)
	@ApiOperation(value = "ESOP绑定个人账户", notes = "ESOP绑定个人账户")
	public R esopAcctBind(@RequestBody ReqUpdateCustVO req) {
		if (req == null || StringUtils.isBlank(req.getTradeAccount()) || StringUtils.isBlank(req.getPassword())) {
			return R.fail("交易账号和密码不能为空");
		}
		TradeUnlockReq trade = new TradeUnlockReq();
		trade.setTradeAccount(req.getTradeAccount());
		trade.setPassword(req.getPassword());
		R result = accountLoginService.validTradePwd(trade);
		if (result.isSuccess()) {
			return custInfoService.esopAcctBind(trade.getTradeAccount());
		}
		return result;
	}

	@PostMapping("/esop_cust_regist")
	@ApiOperationSupport(order = 20)
	@ApiOperation(value = "ESOP注册绑定个人户", notes = "ESOP注册绑定个人户")
	public R esopCustRegist(@RequestBody ReqUpdateCustVO req) {
		if (req == null || StringUtils.isBlank(req.getPhone()) || StringUtils.isBlank(req.getCaptchaCode())) {
			return R.fail("手机号和验证码不能为空");
		}
		// 校验短信验证码
		if (!checkCaptchaCache.checkCaptcha(req.getPhone(), req.getCaptchaCode(), req.getCaptchaKey())) {
			return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_captcha_failed"));
		}
		// 查询客户手机号是否已注册
		CustInfoEntity custInfo = custInfoService.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
			.eq(CustInfoEntity::getCustType, CustEnums.CustType.GENERAL.getCode())
			.eq(CustInfoEntity::getAreaCode, req.getAreaCode())
			.eq(CustInfoEntity::getCellPhone, req.getPhone())
			.eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
			.ne(CustInfoEntity::getStatus, 3));
		if (custInfo != null) {
			// 是否已被停用
			if (custInfo.getStatus().equals(CustEnums.CustStatus.DISABLE.getCode()) || custInfo.getIsDeleted() == CommonEnums.StatusEnum.YES.getCode()) {
				return R.fail(CustStaticType.CodeType.USER_DISABLE_ERROR.getMessage());
			}
			return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_phone_register"));
		}
		// 完成个户注册
		custInfo = new CustInfoEntity();
		custInfo.setAreaCode(req.getAreaCode());
		custInfo.setCellPhone(req.getPhone());
		custInfoService.saveCustInfo(custInfo, 5, 1);
		// ESOP绑定
		custInfoService.esopCustBind(custInfo.getId());
		// 切换个户
		Long custId = custAccountInfoService.switchEsopCust(1);
		if (custId == null || custId == -1l) {
			return R.fail("个户切换失败，请手动切换");
		}
		return R.success("ESOP注册绑定个人户成功");
	}


	//下面是工银的接口
	@PostMapping("/icbcaW8Info")
	@ApiOperationSupport(order = 21)
	@ApiOperation(value = "W8客户信息查询", notes = "W8客户信息查询")
	public R<W8InfoVO> icbcaW8Info() {
		return accountLoginService.icbcaW8Info();
	}

	@PostMapping("/icbcaW8Create")
	@ApiOperationSupport(order = 22)
	@ApiOperation(value = "W8签署", notes = "W8签署")
	public R icbcaW8Create(W8InfoVO req) {
		return accountLoginService.icbcaW8Create(req);
	}

	/**
	 * 账号信息查询
	 */
	@PostMapping("/acctInfo")
	@ApiOperationSupport(order = 23)
	@ApiOperation(value = "账号信息查询", notes = "")
	public R<ClientAccount> accountInfo() {
		return accountLoginService.accountInfo();
	}

	/**
	 * 工银用户信息查询
	 */
	@PostMapping("/userInfo")
	@ApiOperationSupport(order = 24)
	@ApiOperation(value = "工银用户信息查询", notes = "")
	public R<UserInfo> userInfo() {
		return accountLoginService.userInfo();
	}

	/**
	 * 授权人负责公司户列表信息查询
	 */
	@PostMapping("/getAcctList")
	@ApiOperationSupport(order = 25)
	@ApiOperation(value = "授权人负责公司户列表信息查询", notes = "")
	public R<List<AcctInfo>> getAcctList() {
		return R.data(custAccountInfoService.getAcctList());
	}


}
