package com.minigod.zero.cust.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.biz.common.constant.AccountSequenceConstant;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.biz.common.mq.ConnecterInfo;
import com.minigod.zero.biz.common.utils.ProtocolUtils;
import com.minigod.zero.bpm.dto.BpmAccountRespDto;
import com.minigod.zero.bpm.dto.BpmSecuritiesRespDto;
import com.minigod.zero.bpm.dto.OpenAccountStatusRespDto;
import com.minigod.zero.bpm.entity.BpmAccountInfoEntity;
import com.minigod.zero.bpm.entity.BpmCapitalInfoEntity;
import com.minigod.zero.bpm.entity.BpmFundAcctInfoEntity;
import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.bpm.enums.OpenAccountEnum;
import com.minigod.zero.bpm.feign.IBpmAccountClient;
import com.minigod.zero.bpm.feign.IBpmSecuritiesInfoClient;
import com.minigod.zero.bpm.feign.IPhillipProtocolClient;
import com.minigod.zero.bpm.vo.PhillipProtocolVo;
import com.minigod.zero.cms.feign.oms.ILanguageClient;
import com.minigod.zero.core.jwt.Jwt2Util;
import com.minigod.zero.core.launch.constant.ESourceType;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.enums.CustEnums;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import com.minigod.zero.core.tool.support.Kv;
import com.minigod.zero.core.tool.utils.DigestUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.apivo.req.*;
import com.minigod.zero.cust.apivo.resp.CapitalAccountVO;
import com.minigod.zero.cust.apivo.resp.CustDetailVO;
import com.minigod.zero.cust.entity.CustDeviceEntity;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.entity.CustOldPasswordEntity;
import com.minigod.zero.cust.enums.CustStaticType;
import com.minigod.zero.cust.mapper.*;
import com.minigod.zero.cust.service.*;
import com.minigod.zero.cust.utils.RSANewUtil;
import com.minigod.zero.platform.constants.CommonTemplateCode;
import com.minigod.zero.platform.dto.SendEmailDTO;
import com.minigod.zero.platform.dto.SendNotifyDTO;
import com.minigod.zero.platform.dto.SendSmsDTO;
import com.minigod.zero.platform.enums.MsgStaticType;
import com.minigod.zero.platform.feign.IPlatformMsgClient;
import com.minigod.zero.platform.utils.CheckCaptchaCache;
import com.minigod.zero.user.feign.IUserClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 客户信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Service
@Slf4j
public class CustInfoServiceImpl extends BaseServiceImpl<CustInfoMapper, CustInfoEntity> implements ICustInfoService {

    @Resource
    private ZeroRedis zeroRedis;
    @Resource
    private IUserClient userClient;
    @Resource
    private SecuritiesInfoMapper securitiesInfoMapper;
    @Resource
    private CustAccountInfoMapper custAccountInfoMapper;
    @Resource
    private CustCapitalInfoMapper custCapitalInfoMapper;
    @Resource
    private CheckCaptchaCache checkCaptchaCache;
    @Resource
    private ICustOldPasswordService custOldPasswordService;
    @Resource
    private ICustInvestorStmtService custInvestorStmtService;
    @Resource
    private CustInfoMapper custInfoMapper;
    @Resource
    private IPlatformMsgClient platformMsgClient;
    @Resource
    private ILanguageClient languageClient;
    @Resource
    private ICustSequenceService custSequenceService;
    @Resource
    private IBpmAccountClient bpmAccountClient;
    @Resource
    private CustFundAcctInfoMapper custFundAcctInfoMapper;
    @Resource
    private ICustDeviceService custDeviceService;
    @Resource
    private ICustResetLogService resetLogService;
    @Resource
    private IPhillipProtocolClient phillipProtocolClient;

    @Resource
    private IBpmSecuritiesInfoClient bpmSecuritiesInfoClient;

    @Value("${zero.cust.nick-name:''}")
    private String nickName;
    // h5开户邮箱地址
    @Value("${h5.open-email-url:' '}")
    private String h5openEmailUrl;

    @Override
    public IPage<CustInfoEntity> selectCustInfoPage(IPage<CustInfoEntity> page, CustInfoQueryReq custInfo) {

        return page.setRecords(baseMapper.selectCustInfoPage(page, custInfo));
    }

    @Override
    public CustInfoEntity selectCustInfoById(Long custId) {
        CustInfoEntity custInfo = zeroRedis.protoGet(CacheNames.CUST_INFO_KEY.concat(custId.toString()), CustInfoEntity.class);
        if (null == custInfo) {
            custInfo = baseMapper.selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
                    .eq(CustInfoEntity::getId, custId)
                    .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                    .ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
            if (null != custInfo) {
                zeroRedis.protoSet(CacheNames.CUST_INFO_KEY.concat(custId.toString()), custInfo);
            }
        }
        return custInfo;
    }

    @Override
    public R updatePwd(ReqUpdatePwdVO vo) {
        CustInfoEntity custInfo = this.selectCustInfoById(AuthUtil.getCustId());
        if (custInfo == null) {
            return R.fail("客户数据异常，请联系客服咨询");
        }
        // 用户是否已被停用
        if (custInfo.getStatus().equals(CustEnums.CustStatus.DISABLE.getCode())) {
            return R.fail(CustStaticType.CodeType.USER_DISABLE_ERROR.getCode(), CustStaticType.CodeType.USER_DISABLE_ERROR.getMessage());
        }
        // 修改密码需校验旧密码，未设置过密码可不校验
        if (StringUtils.isNotBlank(custInfo.getPassword())) {
            if (StringUtil.isBlank(vo.getOldPassword())) {
                return R.fail("原密码不能为空，请重新录入");
            }
            R result = this.checkOldPassword(custInfo.getId(), vo.getOldPassword(), custInfo.getPassword());
            if (!result.isSuccess()) {
                return result;
            }
        }
		custInfo.setPassword(DigestUtil.hex(RSANewUtil.decrypt(vo.getNewPassword())));
        this.updateCustInfoAndCache(custInfo);

        // 重新激活存量客户，SupperApp客户迁移临时过渡
        custOldPasswordService.activeLoginPwd(custInfo.getId());
        try {
            sendPush(CommonTemplateCode.Push.UPDATE_LOGIN_PWD);
        } catch (Exception e) {
            log.error("修改密码推送消息异常", e);
        }
        try {
            sendSms(CommonTemplateCode.Sms.UPDATE_LOGIN_PWD, null);
        } catch (Exception e) {
            log.error("修改密码发送短信异常", e);
        }

        // 清除登录错误次数缓存

        return R.success();
    }

    /**
     * @param code        模板编号
     * @param deviceModel 设备名称
     */
    private void sendSms(int code, String deviceModel) {
        BpmSecuritiesInfoEntity securitiesInfo = bpmSecuritiesInfoClient.securitiesInfoByCustId(AuthUtil.getCustId());
        SendSmsDTO sendSmsDTO = new SendSmsDTO();
        sendSmsDTO.setPhone(new StringBuilder(securitiesInfo.getPhoneArea()).append("-").append(securitiesInfo.getPhoneNumber()).toString());
        sendSmsDTO.setTemplateCode(code);
        if (StringUtils.isNotBlank(deviceModel)) {
            sendSmsDTO.setParams(Arrays.asList(deviceModel));
        }
        platformMsgClient.sendSms(sendSmsDTO);
    }

    private void sendPush(int code) {
        SendNotifyDTO dto = new SendNotifyDTO();
        dto.setLstToUserId(Arrays.asList(AuthUtil.getCustId()));
        dto.setDisplayGroup(MsgStaticType.DisplayGroup.SERVICE_MSG);
        dto.setTemplateCode(code);
        dto.setLang(WebUtil.getLanguage());
        platformMsgClient.sendNotify(dto);
    }

    @Override
    public R resetPwd(ReqUpdatePwdVO vo) {
        CustInfoEntity custInfo = null;
        int custType = vo.getCustType();
        /**
         * 校验短信验证码
         */
        if (StringUtil.isNotBlank(vo.getPhone())) {
            custInfo = baseMapper.selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
                    .eq(CustInfoEntity::getCustType, custType)
                    .eq(CustInfoEntity::getAreaCode, vo.getAreaCode())
                    .eq(CustInfoEntity::getCellPhone, vo.getPhone())
                    .ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode())
                    .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode()));
            if (custInfo == null) {
                return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_reset_pwd"));
            }
            if (!checkCaptchaCache.checkCaptcha(vo.getPhone(), vo.getCaptchaCode(), vo.getCaptchaKey())) {
                return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_captcha_failed"));
            }
            vo.setEmail(null);
        }
        /**
         * 校验个人户邮件验证码
         */
        if (custType == CustEnums.CustType.GENERAL.getCode() && StringUtil.isNotBlank(vo.getEmail())) {
            custInfo = baseMapper.selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
                    .eq(CustInfoEntity::getCustType, custType)
                    .eq(CustInfoEntity::getCellEmail, vo.getEmail())
                    .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                    .ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
            if (custInfo == null) {
                return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_reset_pwd"));
            }
            if (!checkCaptchaCache.checkEmailCaptcha(vo.getEmail(), vo.getCaptchaCode(), CommonTemplateCode.Email.RESET_PASSWORD_BY_EMAIL, vo.getCaptchaKey())) {
                return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_captcha_failed"));
            }
        }

        /**
         * 校验授权人邮件验证码
         */
        if (custType == CustEnums.CustType.AUTHOR.getCode() && StringUtil.isNotBlank(vo.getEmail())) {
            List<Long> accountList = custAccountInfoMapper.getCustIdList(vo.getEmail());
            if (CollectionUtils.isEmpty(accountList) || accountList.size() != 1) {
                return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_reset_pwd"));
            }
            if (!checkCaptchaCache.checkEmailCaptcha(vo.getEmail(), vo.getCaptchaCode(), CommonTemplateCode.Email.RESET_PASSWORD_BY_EMAIL, vo.getCaptchaKey())) {
                return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_captcha_failed"));
            }
            custInfo.setId(accountList.get(0));
            custInfo.setCustType(CustEnums.CustType.AUTHOR.getCode());
        }

        /**
         * 校验esop用户邮件验证码
         */
        if (custType == CustEnums.CustType.ESOP.getCode() && StringUtil.isNotBlank(vo.getEmail())) {
            custInfo = baseMapper.selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
                    .eq(CustInfoEntity::getCustType, custType)
                    .eq(CustInfoEntity::getCellEmail, vo.getEmail())
                    .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                    .ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
            if (custInfo == null) {
                return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_reset_pwd"));
            }
            if (!checkCaptchaCache.checkEmailCaptcha(vo.getEmail(), vo.getCaptchaCode(), CommonTemplateCode.Email.RESET_PASSWORD_BY_EMAIL, vo.getCaptchaKey())) {
                return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_captcha_failed"));
            }
        }


        // 解除锁定状态
        custInfo.setStatus(CustEnums.CustStatus.ENABLE.getCode());
        custInfo.setPassword(DigestUtil.hex(RSANewUtil.decrypt(vo.getNewPassword())));
        this.updateCustInfoAndCache(custInfo);

        // 重新激活存量客户，SupperApp客户迁移临时过渡
        custOldPasswordService.activeLoginPwd(custInfo.getId());

        // 记录客户密码重置日志(2：登录密码)
        resetLogService.saveLog(custInfo.getId(), 2);

        //如果是esop用户修改密码
        if (custInfo.getCustType() == CustEnums.CustType.ESOP.getCode()) {
            //修改绑定esop用户密码,这里是远程调用,要传请求的密码
            changeEsopAccountPwdByCustId(custInfo.getId(), vo.getNewPassword());
            //修改bindCust的个人账户密码,这里传hex后的密码
            changeBindCustPwd(custInfo.getBindCust(), custInfo.getPassword());
        } else {
            //如果是个人账户
            if (custInfo.getCustType() == CustEnums.CustType.GENERAL.getCode()) {
                //先查询是不是有绑定了esop用户
                if (custInfo.getBindCust() != null) {
                    //修改绑定的esop账户密码
                    CustInfoEntity bindCustInfo = changeBindCustPwd(custInfo.getBindCust(), custInfo.getPassword());
                    //然后修改该esop用户pc端的密码
                    changeEsopAccountPwdByCustId(bindCustInfo.getId(), vo.getNewPassword());
                }
            }
        }

        try {
            sendPush(CommonTemplateCode.Push.RESET_LOGIN_PWD);
        } catch (Exception e) {
            log.error("重置登录密码推送消息异常", e);
        }
        try {
            sendSms(CommonTemplateCode.Sms.RESET_LOGIN_PWD, null);
        } catch (Exception e) {
            log.error("重置登录密码发送短信异常", e);
        }
        // 清除登录错误次数缓存

        return R.success();
    }

    private R checkOldPassword(Long custId, String rsaPwd, String shaPwd) {
        // 解析密码
        String password = RSANewUtil.decrypt(rsaPwd);
        if (DigestUtil.hex(password).equals(shaPwd) || DigestUtil.md5Hex(password).equals(shaPwd)) {
            return R.success();
        }
        if (custOldPasswordService.checkOldLoginPwd(custId, password)) {
            log.info("根据存量客户密码校验通过，custId: {}", custId);
            return R.success();
        } else {
            return R.fail(CustStaticType.CodeType.PWD_UPDATE_ERROR.getCode(), CustStaticType.CodeType.PWD_UPDATE_ERROR.getMessage());
        }
    }

    @Override
    public void updateCustInfoAndCache(CustInfoEntity custInfo) {
        zeroRedis.hDel(CustInfoEntity.class, CacheNames.CUST_INFO_KEY.concat(custInfo.getId().toString()));
        custInfo.setUpdateTime(new Date());
        baseMapper.updateById(custInfo);
        zeroRedis.hDel(CustInfoEntity.class, CacheNames.CUST_INFO_KEY.concat(custInfo.getId().toString()));
    }


    @Override
    public R<CustDetailVO> fetchUserInfo(Long custId) {
        CustInfoEntity custInfo = selectCustInfoById(custId);
        if (custInfo == null) {
            return R.fail("未查询到客户注册信息");
        }
        CustDetailVO dto = new CustDetailVO();
        dto.setUserIcon(custInfo.getCustIcon());
        dto.setNickName(custInfo.getNickName());
        dto.setGender(custInfo.getGender());
        dto.setAreaCode(custInfo.getAreaCode());
        if (StringUtils.isNotBlank(custInfo.getCellPhone())) {
            dto.setPhoneNum(ProtocolUtils.phone2Star(custInfo.getCellPhone()));
        }
        dto.setSignature(custInfo.getSignature());
        dto.setCustId(custInfo.getId());
        dto.setPrivacy(custInfo.getPrivacy());
        dto.setCustEmail(ProtocolUtils.maskEmail(custInfo.getCellEmail()));
        dto.setCreateTime(custInfo.getCreateTime());
        //推荐人ID
        dto.setInvCustId(custInfo.getInvCustId());
        dto.setCustChannel(custInfo.getCustChannel());
        //是否已设置登录密码
        dto.setHasLoginPass(StringUtils.isNotBlank(custInfo.getPassword()));
        // 是否为个人+ESOP户
        if (CustEnums.CustType.ESOP.getCode() == custInfo.getCustType()) {
            dto.setEsopStatus(custInfo.getBindCust() == null ? 1 : 2);
            dto.setAcctType(CustEnums.AcctType.ESOP.getCode());
        } else if (CustEnums.CustType.GENERAL.getCode() == custInfo.getCustType()) {
            dto.setEsopStatus(custInfo.getBindCust() == null ? 0 : 2);
            dto.setAcctType(CustEnums.AcctType.PERSON.getCode());
        } else {
            dto.setEsopStatus(0);
        }
        R<BpmAccountRespDto> resp = bpmAccountClient.bpmAccountInfo(null);
        // 账户信息获取
        if (resp.isSuccess() && null != resp.getData()) {
            //账户信息
            BpmAccountRespDto acct = resp.getData();
            Integer acctType = null;
            if (null != acct.getAcct()) {
                String tradeAcct = acct.getAcct().getTradeAccount();
                dto.setTradeAccount(tradeAcct);
                acctType = acct.getAcct().getAcctType();
                dto.setAcctType(acctType);

                List<BpmCapitalInfoEntity> custCapitalInfos = custCapitalInfoMapper.selectList(Wrappers.<BpmCapitalInfoEntity>lambdaQuery().eq(BpmCapitalInfoEntity::getTradeAccount, tradeAcct)
                        .eq(BpmCapitalInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode()));
                if (CollectionUtils.isNotEmpty(custCapitalInfos)) {
                    List<CapitalAccountVO> list = new ArrayList<>();
                    for (BpmCapitalInfoEntity custCapitalInfo : custCapitalInfos) {
                        CapitalAccountVO capitalAccount = new CapitalAccountVO();
                        capitalAccount.setCapitalAccount(custCapitalInfo.getCapitalAccount());
                        capitalAccount.setAccountType(custCapitalInfo.getAccountType());
                        list.add(capitalAccount);
                    }
                    dto.setCapitalAccounts(list);
                }
                // 兜底更新客户App端权限缓存
                String appAuth = String.valueOf(Jwt2Util.getRedisTemplate().opsForHash().get(TokenConstant.APP_AUTH_KEY, custId));
                if (StringUtils.isBlank(appAuth) || "null".equals(appAuth)) {
                    Jwt2Util.getRedisTemplate().opsForHash().put(TokenConstant.APP_AUTH_KEY, custId, acct.getAcct().getAppPermission());
                }
            }
            //客户信息
            if (null != acct.getCust()) {
                BpmSecuritiesRespDto cust = acct.getCust();
                dto.setAccountLevel(cust.getAccountLevel());
                dto.setBcan(cust.getBcanNo());
                dto.setBStatus(cust.getBcanStatus());
                dto.setOpenAreaCode(cust.getPhoneArea());
                dto.setIssueCountry(cust.getIssueCountry());
                if (StringUtils.isNotBlank(cust.getPhoneNumber())) {
                    dto.setOpenPhoneNum(ProtocolUtils.phone2Star(cust.getPhoneNumber()));
                }
                dto.setOpenCustEmail(ProtocolUtils.maskEmail(cust.getEmail()));
                if (null != cust.getIsFundUnilateralAccount() && cust.getIsFundUnilateralAccount() == 1) {
                    dto.setWealthOnlyFlag(true);
                }
                if (AccountSequenceConstant.NationalityEnum.CHN.value.equals(cust.getIssueCountry())) {
                    dto.setMainland(true);
                    if (null == cust.getBcanStatus()) {
                        dto.setHideA(true);
                    }
                }
                if (StringUtils.isNotBlank(cust.getAecode())) {
                    try {
                        int aecode = Integer.parseInt(cust.getAecode());
                        if (aecode >= 2000 && aecode <= 2999) {
                            dto.setOutStock(true);
                        }
                    } catch (Exception e) {
                        log.error("aecode format error");
                    }
                }
                if (AccountSequenceConstant.NationalityEnum.CHN.value.equals(cust.getIssueCountry()) && StringUtils.isBlank(cust.getBcanNo()))
                    dto.setHideA(true);
                if (null != acctType && CustEnums.AcctType.CORP.getCode() == acctType && 0 == cust.getNorthTrade())
                    dto.setHideA(true);
            }
            //基金账户信息
            if (null != acct.getFund()) {
                // 基金账号
                dto.setFundAccount(acct.getFund().getFundAccount());
                dto.setRiskType(acct.getFund().getRiskType());
            }
        }

        //验证是否弹出行情声明
        dto.setInvestorStmt(custInvestorStmtService.isInvestorStmt(CommonEnums.StatusEnum.YES.getCode()));
        dto.setInvestor(custInvestorStmtService.isIsInvestor());
        // 是否签署暗盘协议
        R<PhillipProtocolVo> phProtocolRes = phillipProtocolClient.findPhProtocolLog();
        dto.setIsAgreeGreyTradeProtocol(phProtocolRes.isSuccess() ? phProtocolRes.getData().getIsAgree() : "0");
        return R.data(dto);
    }

    @Override
    public R setNickName(NickNameVO nickName) {
        // 参数错误
        if (nickName == null) {
            return R.fail(ResultCode.PARAM_MISS.getCode(), ResultCode.PARAM_MISS.getMessage());
        }
        String name = nickName.getNickName();
        String signature = nickName.getSignature();
        Integer gender = nickName.getGender();
        CustInfoEntity custInfo = selectCustInfoById(AuthUtil.getCustId());
        if (custInfo == null) {
            return R.fail("未查询到客户注册信息");
        }
        if (StringUtils.isNotEmpty(name)) {
            custInfo.setNickName(name);
        }
        if (StringUtils.isNotEmpty(signature)) {
            custInfo.setSignature(signature);
        }
        if (null != gender) {
            custInfo.setGender(gender);
        }
        updateCustInfoAndCache(custInfo);
        return R.success();
    }

    @Override
    public R toBindPhoneNumber(String phoneNumber, String areaCode, Long custId) {

        CustInfoEntity custInfo = selectCustInfoById(custId);
        if (null == custInfo) {
            return R.fail("客户信息不存在");
        } else {
            custInfo.setCellPhone(phoneNumber);
            custInfo.setAreaCode(areaCode);
            updateCustInfoAndCache(custInfo);
        }

        return R.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R phoneDataToAccount(Long custId, String phone, String area, String account) {
        // 根据交易账号获取custInfo
        BpmAccountInfoEntity custAcct = custAccountInfoMapper.selectOne(Wrappers.<BpmAccountInfoEntity>lambdaQuery()
                .eq(BpmAccountInfoEntity::getTradeAccount, account));

        // 将手机号登陆的用户数据作废
        CustInfoEntity custInfo = selectCustInfoById(custId);
        custInfo.setCellPhone(account + ":" + custInfo.getCellPhone());
        custInfo.setStatus(0);
        custInfo.setIsDeleted(1);
        baseMapper.updateById(custInfo);

        // 更新交易账户的手机号
        toBindPhoneNumber(phone, area, custAcct.getCustId());
        return R.success();
    }

    @Override
    public R setUserSwitch(UserSwitchVO userSwitchVO) {
        if (null == userSwitchVO || StringUtils.isBlank(userSwitchVO.getValue())) {
            return R.fail(ResultCode.PARAM_VALID_ERROR.getCode(), ResultCode.PARAM_VALID_ERROR.getMessage());
        }
        Long custId = AuthUtil.getCustId();
        CustInfoEntity custInfo = selectCustInfoById(custId);
        if (null == custInfo) {
            return R.fail("用户不存在");
        } else {
            String privacy = custInfo.getPrivacy();
            if (userSwitchVO.getIndex() > privacy.length()) {
                privacy = privacy + userSwitchVO.getValue();
            } else {
                privacy = this.replaceChar(privacy, userSwitchVO.getIndex(), userSwitchVO.getValue());
            }
            custInfo.setPrivacy(privacy);
            this.updateCustInfoAndCache(custInfo);
        }

        return R.success();
    }

    @Override
    public R checkPhone(String areaCode, String phone) {
        Long count = baseMapper.selectCount(Wrappers.<CustInfoEntity>lambdaQuery()
                .eq(CustInfoEntity::getCustType, CustEnums.CustType.GENERAL.getCode())
                .eq(CustInfoEntity::getAreaCode, areaCode)
                .eq(CustInfoEntity::getCellPhone, phone)
                .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                .ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
        if (count > 0) {
            return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_phone_register"));
        }
        return R.success("登录手机号校验通过");
    }

    @Override
    public R updatePhone(ReqUpdateCustVO vo) {
        Long count = baseMapper.selectCount(Wrappers.<CustInfoEntity>lambdaQuery()
                .eq(CustInfoEntity::getCustType, CustEnums.CustType.GENERAL.getCode())
                .eq(CustInfoEntity::getAreaCode, vo.getAreaCode())
                .eq(CustInfoEntity::getCellPhone, vo.getPhone())
                .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                .ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
        if (count > 0) {
            return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_phone_register"));
        }
        /**
         * 判断验证码
         */
        if (!checkCaptchaCache.checkCaptcha(vo.getPhone(), vo.getCaptchaCode(), vo.getCaptchaKey())) {
            return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_captcha_failed"));
        }
        CustInfoEntity custInfo = new CustInfoEntity();
        custInfo.setId(AuthUtil.getCustId());
        custInfo.setCellPhone(vo.getPhone());
        custInfo.setAreaCode(vo.getAreaCode());
        this.updateCustInfoAndCache(custInfo);
        return R.success("登录手机号修改成功");
    }

    @Override
    public R checkOpenPhone(String areaCode, String phone) {
        Long count = securitiesInfoMapper.selectCount(Wrappers.<BpmSecuritiesInfoEntity>lambdaQuery().eq(BpmSecuritiesInfoEntity::getCustId, AuthUtil.getCustId())
                .eq(BpmSecuritiesInfoEntity::getPhoneNumber, phone)
                .eq(BpmSecuritiesInfoEntity::getPhoneArea, areaCode));
        if (count > 0) {
            return R.fail("新旧手机号一致，请重新输入");
        }
        return R.success("开户手机号校验通过");
    }

    @Override
    public R updateOpenPhone(ReqUpdateCustVO vo) {
        /**
         * 判断验证码
         */
        if (!checkCaptchaCache.checkCaptcha(vo.getPhone(), vo.getCaptchaCode(), vo.getCaptchaKey())) {
            return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_captcha_failed"));
        }
        Long custId = AuthUtil.getCustId();

        BpmSecuritiesRespDto param = new BpmSecuritiesRespDto();
        param.setCustId(custId);
        param.setPhoneNumber(vo.getAreaCode().concat("-").concat(vo.getPhone()));
        R r = bpmAccountClient.bpmUpdateInfo(param);
        if (r.isSuccess()) {
            BpmSecuritiesInfoEntity securitiesInfo = securitiesInfoMapper.selectOne(Wrappers.<BpmSecuritiesInfoEntity>lambdaQuery().eq(BpmSecuritiesInfoEntity::getCustId, custId));
            if (null != securitiesInfo) {
                securitiesInfo = securitiesInfoMapper.selectOne(Wrappers.<BpmSecuritiesInfoEntity>lambdaQuery().eq(BpmSecuritiesInfoEntity::getCustId, custId)
                        .ne(BpmSecuritiesInfoEntity::getClientStatus, OpenAccountEnum.BpmClientStatus.STS_3.value));
                securitiesInfo.setPhoneNumber(vo.getPhone());
                securitiesInfo.setPhoneArea(vo.getAreaCode());
                securitiesInfo.setUpdateTime(new Date());
                securitiesInfoMapper.updateById(securitiesInfo);

                zeroRedis.hDel(BpmAccountRespDto.class, CacheNames.CUST_ACCOUNT_INFO.concat(custId.toString()));
            }
            return R.success("开户手机号修改成功");
        }
        return R.fail("开户手机号修改失败");
    }

    @Override
    public R checkEmail(String email) {
        Long count = baseMapper.selectCount(Wrappers.<CustInfoEntity>lambdaQuery()
                .eq(CustInfoEntity::getCustType, CustEnums.CustType.GENERAL.getCode())
                .eq(CustInfoEntity::getCellEmail, email)
                .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                .ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
        if (count > 0) {
            return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_email_register"));
        }
        return R.success("登录邮箱校验通过");
    }

    @Override
    public R updateEmail(ReqUpdateCustVO vo) {
        Long count = baseMapper.selectCount(Wrappers.<CustInfoEntity>lambdaQuery()
                .eq(CustInfoEntity::getCustType, CustEnums.CustType.GENERAL.getCode())
                .eq(CustInfoEntity::getCellEmail, vo.getEmail())
                .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                .ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
        if (count > 0) {
            return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_email_register"));
        }
        /**
         * 判断验证码
         */
        if (!checkCaptchaCache.checkEmailCaptcha(vo.getEmail(), vo.getCaptchaCode(), CommonTemplateCode.Email.VERIFICATION_CODE_OF_EMAIL_BINDING, vo.getCaptchaKey())) {
            return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_captcha_failed"));
        }
        CustInfoEntity custInfo = new CustInfoEntity();
        custInfo.setId(AuthUtil.getCustId());
        custInfo.setCellEmail(vo.getEmail());
        this.updateCustInfoAndCache(custInfo);

        try {
            sendPush(CommonTemplateCode.Push.UPDATE_EMAIL);
        } catch (Exception e) {
            log.error("修改邮箱推送消息异常", e);
        }
        try {
            sendSms(CommonTemplateCode.Sms.UPDATE_EMAIL, null);
        } catch (Exception e) {
            log.error("修改邮箱发送短信异常", e);
        }

        return R.success("登录邮箱修改成功");
    }

    @Override
    public R checkOpenEmail(String email) {
        BpmSecuritiesInfoEntity bpmSecuritiesInfo = securitiesInfoMapper.selectOne(Wrappers.<BpmSecuritiesInfoEntity>lambdaQuery().eq(BpmSecuritiesInfoEntity::getCustId, AuthUtil.getCustId()));
        if (StringUtils.isNotBlank(bpmSecuritiesInfo.getEmail()) && email.equals(bpmSecuritiesInfo.getEmail())) {
            return R.fail("新旧邮箱地址一致，请重新输入");
        }
        BpmSecuritiesInfoEntity securities = new BpmSecuritiesInfoEntity();
        securities.setEmail(email);
        securities.setCustId(AuthUtil.getCustId());
        String key = UUID.randomUUID().toString().replace("-", "");
        //发送邮件
        List<String> params = new ArrayList<>();
        params.add(bpmSecuritiesInfo.getCustName());
        params.add(h5openEmailUrl + "?key=" + key);
        SendEmailDTO sendDto = new SendEmailDTO();
        List<String> accepts = new ArrayList<>();
        accepts.add(email);
        sendDto.setTitle("开户邮箱修改");
        sendDto.setAccepts(accepts);
        sendDto.setCode(CommonTemplateCode.Email.OPEN_EMAIL_MODIFY);
        sendDto.setList(params);
        sendDto.setLang(WebUtil.getLanguage());
        R r = platformMsgClient.sendEmail(sendDto);
        if (!r.isSuccess()) {
            return R.fail("邮件发送失败");
        }
        zeroRedis.setEx(CacheNames.UPDATE_OPEN_EMAIL.concat(key), securities, CacheNames.UPDATE_OPEN_EMAIL_EXPIRE_TIME);
        return R.data(Kv.create().set("key", key));
    }

    @Override
    public R updateOpenEmail(String key) {
        BpmSecuritiesInfoEntity securities = zeroRedis.get(CacheNames.UPDATE_OPEN_EMAIL.concat(key));
        if (securities == null) {
            return R.fail("登录会话已失效");
        }

        BpmSecuritiesRespDto param = new BpmSecuritiesRespDto();
        param.setCustId(securities.getCustId());
        param.setEmail(securities.getEmail());
        R r = bpmAccountClient.bpmUpdateInfo(param);
        if (r.isSuccess()) {
            BpmSecuritiesInfoEntity securitiesInfo = securitiesInfoMapper.selectOne(Wrappers.<BpmSecuritiesInfoEntity>lambdaQuery().eq(BpmSecuritiesInfoEntity::getCustId, securities.getCustId())
                    .ne(BpmSecuritiesInfoEntity::getClientStatus, OpenAccountEnum.BpmClientStatus.STS_3.value));
            if (securitiesInfo != null) {
                securitiesInfo.setEmail(securities.getEmail());
                securitiesInfo.setUpdateTime(new Date());
                securitiesInfoMapper.updateById(securitiesInfo);
                zeroRedis.del(CacheNames.UPDATE_OPEN_EMAIL.concat(key));
                zeroRedis.hDel(BpmAccountRespDto.class, CacheNames.CUST_ACCOUNT_INFO.concat(securities.getCustId().toString()));
                return R.success("开户邮箱修改成功");
            }
            zeroRedis.del(CacheNames.UPDATE_OPEN_EMAIL.concat(key));
        } else {
            return R.fail("开户邮箱修改失败");
        }
        return R.fail("客户信息不存在");
    }

    @Override
    public R cancelCust() {
        R<OpenAccountStatusRespDto> rt = bpmAccountClient.acctOpenStatus();
        if (!rt.isSuccess()) {
            return R.fail("获取客户开户信息失败！");
        }
        OpenAccountStatusRespDto openStatus = rt.getData();
        if (openStatus != null && !OpenAccountEnum.OpenAccountStatus.N_COMMIT_INFO.getCode().equals(openStatus.getOpenStatus())
                && !OpenAccountEnum.OpenAccountStatus.OPEN_CANCAL.getCode().equals(openStatus.getOpenStatus())) {
            // 开户中不允许注销用户
            return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_cancel"));
        }
        boolean hasAccount = custAccountInfoMapper.exists(Wrappers.<BpmAccountInfoEntity>lambdaQuery().eq(BpmAccountInfoEntity::getCustId, AuthUtil.getCustId()));
        if (hasAccount && CustEnums.CustType.GENERAL.getCode() == AuthUtil.getCustType()) {
            // 已开户账号注销需联系客服处理
            return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_cancel"));
        }
        CustInfoEntity custInfo = baseMapper.selectOne(Wrappers.<CustInfoEntity>lambdaQuery().eq(CustInfoEntity::getId, AuthUtil.getCustId()));
        this.cancelCust(custInfo);
        return R.success("客户销户成功");
    }

    private void cancelCust(CustInfoEntity custInfo) {
        custInfo.setCellEmail(custInfo.getCellEmail() == null ? String.valueOf(System.currentTimeMillis()) : custInfo.getCellEmail() + System.currentTimeMillis());
        custInfo.setStatus(CustEnums.CustStatus.CANCEL.getCode());
        custInfo.setCancelTime(new Date());
        this.updateCustInfoAndCache(custInfo);
    }

    @Override
    public R checkIdCard(String idCard) {
        if (CustEnums.CustType.AUTHOR.getCode() == AuthUtil.getCustType()) {
            // 公司户授权人证件号在cust_info表，从bpm同步
            CustInfoEntity custInfo = custInfoMapper.selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
                    .eq(CustInfoEntity::getId, AuthUtil.getCustId())
                    .eq(CustInfoEntity::getAuthorIdno, idCard)
                    .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                    .ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
            if (custInfo == null) {
                return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_reset_check"));
            }
        } else {
            BpmSecuritiesInfoEntity securitiesInfo = securitiesInfoMapper.selectOne(Wrappers.<BpmSecuritiesInfoEntity>lambdaQuery()
                    .eq(BpmSecuritiesInfoEntity::getCustId, AuthUtil.getCustId()).eq(BpmSecuritiesInfoEntity::getIdCard, idCard));
            if (securitiesInfo == null) {
                return R.fail(languageClient.getTextByLang(WebUtil.getLanguage(), "cust_reset_check"));
            }
        }
        zeroRedis.setEx(CacheNames.TRADE_RESET_TRADE_PWD.concat(AuthUtil.getCustId().toString()), idCard, CacheNames.RESET_TRADE_PWD_EXPIRE_TIME);
        return R.success();
    }

    @Override
    @Transactional
    public R registerAccount(ConnecterInfo connecterInfo) {
        if (connecterInfo.getCustType() == CustEnums.CustType.AUTHOR.getCode()) {
            /**
             * 公司授权人
             */
            // 获取授权人注册信息
            CustInfoEntity custInfo = baseMapper.selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
                    .eq(CustInfoEntity::getCustType, CustEnums.CustType.AUTHOR.getCode())
                    .eq(CustInfoEntity::getAuthorId, connecterInfo.getAuthorId())
                    .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                    .ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
            if (custInfo != null) {
                // 清理App权限缓存
                Jwt2Util.getRedisTemplate().opsForHash().delete(TokenConstant.APP_AUTH_KEY, custInfo.getId().toString());
                custInfo.setRegistType(connecterInfo.getRegisterType());
                custInfo.setAreaCode(connecterInfo.getAuthorArea());
                custInfo.setCellPhone(connecterInfo.getAuthorPhone());
                custInfo.setAuthorIdno(connecterInfo.getAuthorIdno());
                this.updateCustInfoAndCache(custInfo);
                // 是否已被禁止登录APP或关联
                if (connecterInfo.getLoginAuth() == 0 || connecterInfo.getRelationStatus() != 3) {
                    // 清理客户账号缓存
                    zeroRedis.hDel(BpmAccountRespDto.class, CacheNames.CUST_ACCOUNT_INFO.concat(custInfo.getId().toString()));
                    // 删除关联交易账号
                    BpmAccountInfoEntity accountInfo = new BpmAccountInfoEntity();
                    accountInfo.setTradeAuth(CommonEnums.StatusEnum.NO.getCode());
                    accountInfo.setAppPermission(null);
                    accountInfo.setStatus(connecterInfo.getAcctStatus());
                    accountInfo.setIsDeleted(CommonEnums.StatusEnum.YES.getCode());
                    // 关联状态为4-已终止 5-已失效，授权人状态设置为无效
                    accountInfo.setAuthorStatus(CommonEnums.StatusEnum.NO.getCode());
                    custAccountInfoMapper.update(accountInfo, Wrappers.<BpmAccountInfoEntity>lambdaQuery().eq(BpmAccountInfoEntity::getCustId, custInfo.getId())
                            .eq(BpmAccountInfoEntity::getTradeAccount, connecterInfo.getTradeAccount()));
                    log.warn("已禁用授权人注册账号：{}", custInfo.getId());
                    return R.fail("已禁用授权人注册账号");
                }
            } else {
                // 是否禁止登录APP或关联
                if (connecterInfo.getLoginAuth() == 0 || connecterInfo.getRelationStatus() != 3) {
                    log.warn("关联人无需注册为授权人：{}", connecterInfo.getAuthorId());
                    return R.fail("关联人无需注册为授权人");
                }
                // 新增注册授权人
                custInfo = new CustInfoEntity();
                custInfo.setRegistType(connecterInfo.getRegisterType());
                custInfo.setAreaCode(connecterInfo.getAuthorArea());
                custInfo.setCellPhone(connecterInfo.getAuthorPhone());
                custInfo.setPassword(connecterInfo.getLoginPwd());
                custInfo.setTradePwd(connecterInfo.getTradePwd());
                custInfo.setCustType(CustEnums.CustType.AUTHOR.getCode());
                custInfo.setAuthorIdno(connecterInfo.getAuthorIdno());
                if (null != connecterInfo.getAuthorId()) custInfo.setAuthorId(connecterInfo.getAuthorId().toString());
                this.saveCustInfo(custInfo, ESourceType.WEB.getCategory(), 3);
            }
            // 清理客户账号缓存
            zeroRedis.hDel(BpmAccountRespDto.class, CacheNames.CUST_ACCOUNT_INFO.concat(custInfo.getId().toString()));
            // 获取授权人关联的交易账号
            BpmAccountInfoEntity acctInfo = custAccountInfoMapper.selectOne(Wrappers.<BpmAccountInfoEntity>lambdaQuery().eq(BpmAccountInfoEntity::getCustId, custInfo.getId())
                    .eq(BpmAccountInfoEntity::getTradeAccount, connecterInfo.getTradeAccount()));
            if (acctInfo != null) {
                // 更新授权人邮箱及权限
                acctInfo.setAuthorEmail(connecterInfo.getAuthorEmail());
                acctInfo.setTradeAuth(connecterInfo.getTradeAuth());
                acctInfo.setAppPermission(connecterInfo.getAppPermission());
                acctInfo.setStatus(connecterInfo.getAcctStatus());
                acctInfo.setIsDeleted(CommonEnums.StatusEnum.NO.getCode());
                custAccountInfoMapper.updateById(acctInfo);
            } else {
                // 新增关联授权人账号
                acctInfo = new BpmAccountInfoEntity();
                acctInfo.setCustId(custInfo.getId());
                acctInfo.setAcctType(CustEnums.AcctType.CORP.getCode());
                acctInfo.setTradeAccount(connecterInfo.getTradeAccount());
                acctInfo.setAuthorEmail(connecterInfo.getAuthorEmail());
                acctInfo.setTradeAuth(connecterInfo.getTradeAuth());
                acctInfo.setAppPermission(connecterInfo.getAppPermission());
                acctInfo.setIsCurrent(CommonEnums.StatusEnum.NO.getCode());
                acctInfo.setStatus(connecterInfo.getAcctStatus());
                acctInfo.setAuthorStatus(CommonEnums.StatusEnum.YES.getCode());
                custAccountInfoMapper.insert(acctInfo);
            }
            // 更新用户资金账号和基金账号
            this.updateCapAndFund(connecterInfo);
            // 更新App权限缓存
            Jwt2Util.getRedisTemplate().opsForHash().put(TokenConstant.APP_AUTH_KEY, custInfo.getId().toString(), connecterInfo.getAppPermission());
            log.info("授权人更新注册成功：authorId = {}，custId = {}", connecterInfo.getAuthorId(), custInfo.getId());
            return R.data(custInfo.getId());
        } else if (connecterInfo.getCustType() == CustEnums.CustType.ESOP.getCode()) {
            /**
             * ESOP户
             */
            // 获取个人户注册信息
            CustInfoEntity custInfo = baseMapper.selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
                    .eq(CustInfoEntity::getId, connecterInfo.getCustId())
                    .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                    .ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
            boolean newCust = false;
            if (custInfo == null) {
                // 新增注册个人客户
                custInfo = new CustInfoEntity();
                custInfo.setCustType(CustEnums.CustType.ESOP.getCode());
                this.saveCustInfo(custInfo, ESourceType.WEB.getCategory(), 1);
                newCust = true;
            }
            // 获取授权人关联的交易账号
            BpmAccountInfoEntity acctInfo = custAccountInfoMapper.selectOne(Wrappers.<BpmAccountInfoEntity>lambdaQuery().eq(BpmAccountInfoEntity::getCustId, custInfo.getId()));
            // 更新APP权限
            if (acctInfo != null) {
                acctInfo.setTradeAuth(connecterInfo.getTradeAuth());
                acctInfo.setTradeAccount(connecterInfo.getTradeAccount());
                acctInfo.setAppPermission(connecterInfo.getAppPermission());
                acctInfo.setStatus(connecterInfo.getAcctStatus());
                acctInfo.setIsDeleted(CommonEnums.StatusEnum.NO.getCode());
                acctInfo.setIsCurrent(CommonEnums.StatusEnum.YES.getCode());
                custAccountInfoMapper.updateById(acctInfo);
            } else {
                // 新增交易账号
                acctInfo = new BpmAccountInfoEntity();
                acctInfo.setCustId(custInfo.getId());
                acctInfo.setTradeAuth(connecterInfo.getTradeAuth());
                acctInfo.setAppPermission(connecterInfo.getAppPermission());
                acctInfo.setAcctType(CustEnums.AcctType.ESOP.getCode());
                acctInfo.setTradeAccount(connecterInfo.getTradeAccount());
                acctInfo.setStatus(connecterInfo.getAcctStatus());
                acctInfo.setIsCurrent(CommonEnums.StatusEnum.YES.getCode());
                custAccountInfoMapper.insert(acctInfo);
            }
            // 更新用户资金账号和基金账号
            this.updateCapAndFund(connecterInfo);
            // 清理客户账号缓存
            zeroRedis.hDel(BpmAccountRespDto.class, CacheNames.CUST_ACCOUNT_INFO.concat(custInfo.getId().toString()));
            if (newCust) {
                // 首次注册，覆盖App权限缓存，解决客户首次注册时，客户权限缓存为空的情况
                Jwt2Util.getRedisTemplate().opsForHash().put(TokenConstant.APP_AUTH_KEY, custInfo.getId().toString(), connecterInfo.getAppPermission());
                // 完成首次登录时权限写缓存
                return R.data(custInfo.getId());
            } else {
                // 删除App权限缓存
                Jwt2Util.getRedisTemplate().opsForHash().delete(TokenConstant.APP_AUTH_KEY, custInfo.getId().toString());
                // 更新App权限缓存
                Jwt2Util.getRedisTemplate().opsForHash().put(TokenConstant.APP_AUTH_KEY, custInfo.getId().toString(), connecterInfo.getAppPermission());
                return R.fail("客户权限已更新");
            }
        } else {
            /**
             * 个人户
             */
            // 获取个人户注册信息
            CustInfoEntity custInfo = baseMapper.selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
                    .eq(CustInfoEntity::getId, connecterInfo.getCustId())
                    .eq(CustInfoEntity::getCustType, CustEnums.CustType.GENERAL.getCode())
                    .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                    .ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
            boolean newCust = false;
            if (custInfo == null) { //线下开户
                // 20231103新增优化：先判断开户手机号是否已注册，存在则进行绑定
                List<CustInfoEntity> custList = null;
                if (StringUtils.isNotBlank(connecterInfo.getAuthorPhone())) {
                    custList = baseMapper.selectList(Wrappers.<CustInfoEntity>lambdaQuery()
                            .eq(CustInfoEntity::getCustType, CustEnums.CustType.GENERAL.getCode())
                            .eq(CustInfoEntity::getAreaCode, connecterInfo.getAuthorArea())
                            .eq(CustInfoEntity::getCellPhone, connecterInfo.getAuthorPhone())
                            .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                            .ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode()));
                }
                if (CollectionUtils.isNotEmpty(custList)) {
                    if (custList.size() == 1) {
                        custInfo = custList.get(0);
                        if (StringUtils.isBlank(custInfo.getCellEmail())) {
                            custInfo.setCellEmail(connecterInfo.getAuthorEmail());
                        }
                    } else {
                        custInfo = new CustInfoEntity();
                        log.error("开户手机号存在多条有效注册！" + connecterInfo.getAuthorPhone());
                    }
                } else {
                    // 新增注册个人客户
                    custInfo = new CustInfoEntity();
                    custInfo.setAreaCode(connecterInfo.getAuthorArea());
                    custInfo.setCellPhone(connecterInfo.getAuthorPhone());
                    custInfo.setCellEmail(connecterInfo.getAuthorEmail());
                }
                this.saveCustInfo(custInfo, ESourceType.WEB.getCategory(), 2);
                newCust = true;
            } else if (CustEnums.AcctStatus.CANCEL.getCode().equals(connecterInfo.getAcctStatus())) {
                this.cancelCust(custInfo); //注销用户
            }
            // 获取关联的交易账号
            BpmAccountInfoEntity acctInfo = custAccountInfoMapper.selectOne(Wrappers.<BpmAccountInfoEntity>lambdaQuery().eq(BpmAccountInfoEntity::getCustId, custInfo.getId())
                    .eq(BpmAccountInfoEntity::getTradeAccount, connecterInfo.getTradeAccount()));
            // 更新APP权限
            if (acctInfo != null) {
                acctInfo.setTradeAuth(connecterInfo.getTradeAuth());
                acctInfo.setAppPermission(connecterInfo.getAppPermission());
                acctInfo.setStatus(connecterInfo.getAcctStatus());
                acctInfo.setIsDeleted(CommonEnums.StatusEnum.NO.getCode());
                acctInfo.setIsCurrent(CommonEnums.StatusEnum.YES.getCode());
                custAccountInfoMapper.updateById(acctInfo);
            } else {
                // 新增交易账号
                acctInfo = new BpmAccountInfoEntity();
                acctInfo.setCustId(custInfo.getId());
                acctInfo.setTradeAuth(connecterInfo.getTradeAuth());
                acctInfo.setAppPermission(connecterInfo.getAppPermission());
                acctInfo.setAcctType(CustEnums.AcctType.PERSON.getCode());
                acctInfo.setTradeAccount(connecterInfo.getTradeAccount());
                acctInfo.setStatus(connecterInfo.getAcctStatus());
                acctInfo.setIsCurrent(CommonEnums.StatusEnum.YES.getCode());
                custAccountInfoMapper.insert(acctInfo);
            }
            // 更新用户资金账号和基金账号
            this.updateCapAndFund(connecterInfo);
            // 清理客户账号缓存
            zeroRedis.hDel(BpmAccountRespDto.class, CacheNames.CUST_ACCOUNT_INFO.concat(custInfo.getId().toString()));
            if (newCust) {
                // 首次注册，覆盖App权限缓存，解决客户首次注册时，客户权限缓存为空的情况
                Jwt2Util.getRedisTemplate().opsForHash().put(TokenConstant.APP_AUTH_KEY, custInfo.getId().toString(), connecterInfo.getAppPermission());
                // 完成首次登录时权限写缓存
                return R.data(custInfo.getId());
            } else {
                // 删除App权限缓存
                Jwt2Util.getRedisTemplate().opsForHash().delete(TokenConstant.APP_AUTH_KEY, custInfo.getId().toString());
                // 更新App权限缓存
                Jwt2Util.getRedisTemplate().opsForHash().put(TokenConstant.APP_AUTH_KEY, custInfo.getId().toString(), connecterInfo.getAppPermission());
                return R.fail("客户权限已更新");
            }
        }
    }

    private void updateCapAndFund(ConnecterInfo connecterInfo) {
        // 获取授权人关联的资金账号
        BpmCapitalInfoEntity capitalInfo = custCapitalInfoMapper.selectOne(Wrappers.<BpmCapitalInfoEntity>lambdaQuery()
                .eq(BpmCapitalInfoEntity::getTradeAccount, connecterInfo.getTradeAccount()));
        // 更新资金账号
        if (capitalInfo != null) {
            capitalInfo.setTradeAccount(connecterInfo.getTradeAccount());
            capitalInfo.setCapitalAccount(connecterInfo.getFundAccount());
            capitalInfo.setAccountType(connecterInfo.getFundAccountType());
            capitalInfo.setStatus(CommonEnums.StatusEnum.YES.getCode());
            capitalInfo.setIsDeleted(CommonEnums.StatusEnum.NO.getCode());
            capitalInfo.setIsCurrent(CommonEnums.StatusEnum.YES.getCode());
            custCapitalInfoMapper.updateById(capitalInfo);
        } else {
            // 新增资金账号
            capitalInfo = new BpmCapitalInfoEntity();
            capitalInfo.setTradeAccount(connecterInfo.getTradeAccount());
            capitalInfo.setCapitalAccount(connecterInfo.getFundAccount());
            capitalInfo.setAccountType(connecterInfo.getFundAccountType());
            capitalInfo.setStatus(CommonEnums.StatusEnum.YES.getCode());
            capitalInfo.setIsCurrent(CommonEnums.StatusEnum.NO.getCode());
            capitalInfo.setIsDeleted(CommonEnums.StatusEnum.NO.getCode());
            capitalInfo.setIsCurrent(CommonEnums.StatusEnum.YES.getCode());
            custCapitalInfoMapper.insert(capitalInfo);
        }
        // 获取授权人关联的基金账户信息
        BpmFundAcctInfoEntity fundAcctInfo = custFundAcctInfoMapper.selectOne(Wrappers.<BpmFundAcctInfoEntity>lambdaQuery()
                .eq(BpmFundAcctInfoEntity::getTradeAccount, connecterInfo.getTradeAccount()));
        // 更新基金账户信息
        if (fundAcctInfo != null) {
            fundAcctInfo.setTradeAccount(connecterInfo.getTradeAccount());
            fundAcctInfo.setFundAccountMain(StrUtil.isEmpty(connecterInfo.getFundAccountMain()) ? null : connecterInfo.getFundAccountMain());
            fundAcctInfo.setFundAccount(StrUtil.isEmpty(connecterInfo.getYfFundAccount()) ? null : connecterInfo.getYfFundAccount());
            fundAcctInfo.setFundAccountType(StrUtil.isEmpty(connecterInfo.getYfFundAccountType()) ? null : Integer.valueOf(connecterInfo.getYfFundAccountType()));
            fundAcctInfo.setFundOperType(StrUtil.isEmpty(connecterInfo.getYfFundOperType()) ? null : Integer.valueOf(connecterInfo.getYfFundOperType()));
            custFundAcctInfoMapper.updateById(fundAcctInfo);
        } else {
            // 新增基金账户信息
            fundAcctInfo = new BpmFundAcctInfoEntity();
            fundAcctInfo.setTradeAccount(connecterInfo.getTradeAccount());
            fundAcctInfo.setFundAccountMain(StrUtil.isEmpty(connecterInfo.getFundAccountMain()) ? null : connecterInfo.getFundAccountMain());
            fundAcctInfo.setFundAccount(StrUtil.isEmpty(connecterInfo.getYfFundAccount()) ? null : connecterInfo.getYfFundAccount());
            fundAcctInfo.setFundAccountType(StrUtil.isEmpty(connecterInfo.getYfFundAccountType()) ? null : Integer.valueOf(connecterInfo.getYfFundAccountType()));
            fundAcctInfo.setFundOperType(StrUtil.isEmpty(connecterInfo.getYfFundOperType()) ? null : Integer.valueOf(connecterInfo.getYfFundOperType()));
            custFundAcctInfoMapper.insert(fundAcctInfo);
        }
    }

    /**
     * 注册智珠客户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCustInfo(CustInfoEntity custInfo, Integer sourceType, Integer channel) {
        // 新生成cust_id完成注册与登录处理
        try {
            Long custId = custInfo.getId();
            if (custId == null) {
                // 生成用户号
                custId = custSequenceService.queryNextSequenceIdByName(AccountSequenceConstant.CUST_ID_SEQUENCE_NAME);
            }
            custInfo.setId(custId);
            custInfo.setNickName(nickName + custId);
            if (null == custInfo.getCustType()) {
                custInfo.setCustType(CustEnums.CustType.GENERAL.getCode());
            } else {
                custInfo.setCustType(custInfo.getCustType());
            }
            Date date = new Date();
            custInfo.setStatus(CustEnums.CustStatus.ENABLE.getCode());
            custInfo.setRegSourceType(sourceType);
            custInfo.setRegChannel(channel);
            custInfo.setLastLoginTime(date);
            custInfo.setLoginCount(1L);
            custInfo.setLastLoginIp(WebUtil.getIP());
            custInfo.setCreateTime(date);
            custInfo.setUpdateTime(date);
            custInfo.setAcceptAgreement(CommonEnums.StatusEnum.YES.getCode());
            custInfo.setIsAgree(CommonEnums.StatusEnum.YES.getCode());
            /**
             * 默认繁体
             */
            log.info("用户注册存入数据库的信息为 ={}", JsonUtil.toJson(custInfo));
            int insert = baseMapper.insert(custInfo);
            log.info("用户注册影响行数：{}", insert);
        } catch (Exception e) {
            log.error("注册行情账号异常", e);
        }
    }

    @Override
    public void updateChannel(Long custId, String channel) {
        try {
            CustDeviceEntity entity = custDeviceService.getCustDevice(custId);
            if (null != entity) {
                entity.setChannel(channel);
                entity.setUpdateTime(new Date());
                custDeviceService.updateById(entity);
            }
        } catch (Exception e) {
            log.error("更新渠道信息异常", e);
        }
    }


    private String replaceChar(String str, int index, String ch) {
        // 角标从0开始
        index = index - 1;
        if (index < 0 || index >= str.length()) {
            return str;
        }
        char[] charArray = str.toCharArray();
        charArray[index] = ch.charAt(0);
        return new String(charArray);
    }


    @Override
    public R esopAcctBind(String tradeAccount) {
        Long custId = AuthUtil.getCustId();
        // 个人户与ESOP户相互绑定

        //判断该账号是否已经绑定
        CustInfoEntity dbCustInfo = custInfoMapper.selectOne(Wrappers.<CustInfoEntity>lambdaQuery().eq(CustInfoEntity::getId, custId));
        if (dbCustInfo.getBindCust() != null) {
            return R.fail("该账户已经绑定用户号" + dbCustInfo.getBindCust());
        } else {
            //如果该账号未绑定,那么查找该交易账号是否已经被绑定
            //查询交易账号对应的用户号
            Integer integer = custInfoMapper.checkCustBindIsExit(tradeAccount);
            if (integer >= 1) {
                return R.fail("该交易账号已经被绑定");
            }
        }

        custInfoMapper.esopAcctBind(tradeAccount, custId);
        CustInfoEntity custInfo = new CustInfoEntity();
        custInfo.setId(custId);
        this.updateCustInfoAndCache(custInfo);
        log.info("ESOP户 {} 与个人户 {} 绑定成功", custId, tradeAccount);
        return R.success("ESOP户与个人户绑定成功");
    }

    @Override
    public R esopCustBind(Long custId) {
        Long esopCust = AuthUtil.getCustId();
        // 个人户与ESOP户相互绑定
        custInfoMapper.esopCustBind(custId, esopCust);
        CustInfoEntity custInfo = new CustInfoEntity();
        custInfo.setId(esopCust);
        this.updateCustInfoAndCache(custInfo);
        log.info("ESOP户 {} 与个人户 {} 绑定成功", esopCust, custId);
        return R.success("ESOP户与个人户绑定成功");
    }

    @Override
    public void changeEsopAccountPwdByCustId(Long custId, String password) {
        if (null == custId) {
            return;
        }
        userClient.updatePwdByCustId(custId, password);
    }

    @Override
    public CustInfoEntity changeBindCustPwd(Long bindCustId, String hexPassWord) {
        if (null == bindCustId) {
            return null;
        }
        if (StringUtil.isBlank(hexPassWord)) {
            return null;
        }

        //账号状态不等于3
        LambdaQueryWrapper<CustInfoEntity> queryWrapper =
                Wrappers.<CustInfoEntity>lambdaQuery()
                        .eq(CustInfoEntity::getId, bindCustId)
                        .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                        .ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode());

        CustInfoEntity bindCustInfo = this.getBaseMapper().selectOne(queryWrapper);

        if (null == bindCustInfo) {
            return null;
        }

        bindCustInfo.setPassword(hexPassWord);
        this.updateCustInfoAndCache(bindCustInfo);
        return bindCustInfo;
    }

    @Override
    public void updatePwdByCustId(Long custId, String passWord, Integer changeType) {

        LambdaQueryWrapper<CustInfoEntity> query =
                Wrappers.<CustInfoEntity>lambdaQuery().eq(CustInfoEntity::getId, custId)
                        .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                        .ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode());
        CustInfoEntity custInfo = this.getBaseMapper().selectOne(query);

        //该方法是ekeeper pc端用户修改或重置密码会调用到该接口，这里查出来的用户只可能是5,如果不是5就是数据有问题
        if (null != custInfo && custInfo.getCustType() == CustEnums.CustType.ESOP.getCode()) {
            custInfo.setPassword(DigestUtil.hex(RSANewUtil.decrypt(passWord)));
            updateCustInfoAndCache(custInfo);
            // 更新存量客户密码表该用户状态为已重新激活登录
            log.info("esop用户在ekkeper端修改密码调用该接口，custId: {},custType{}", custId, custInfo.getCustType());

            LambdaQueryWrapper<CustOldPasswordEntity> updatequery =
                    Wrappers.<CustOldPasswordEntity>lambdaQuery()
                            .eq(CustOldPasswordEntity::getCustId, custId)
                            .eq(CustOldPasswordEntity::getLoginActive, 0);
            CustOldPasswordEntity oldCust = custOldPasswordService.getOne(updatequery);
            if (oldCust != null) {
                oldCust.setLoginActive(1);
                oldCust.setLoginActiveTime(new Date());
                custOldPasswordService.updateById(oldCust);
                log.info("custId: {}:激活存量密码", custId);
            }

            //changtype==2是指通过重置的方式修改密码,saveLog==2是指修改了登录密码
            if (null != changeType) {
                if (changeType == 2) {
                    resetLogService.saveLog(custInfo.getId(), 2);
                }
            }

            //修改esop账户绑定的个人户密码
            Long bindCust = custInfo.getBindCust();
            log.info("该esop用户绑定的bindcust是:{}", bindCust);
            if (bindCust != null) {
                LambdaQueryWrapper<CustInfoEntity> bindCustQueryWrapper =
                        Wrappers.<CustInfoEntity>lambdaQuery().eq(CustInfoEntity::getId, bindCust)
                                .eq(CustInfoEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode())
                                .ne(CustInfoEntity::getStatus, CustEnums.CustStatus.CANCEL.getCode());
                ;
                CustInfoEntity bindCustInfo = this.getBaseMapper().selectOne(bindCustQueryWrapper);

                if (null != bindCustInfo) {
                    bindCustInfo.setPassword(DigestUtil.hex(RSANewUtil.decrypt(passWord)));
                    updateCustInfoAndCache(bindCustInfo);
                    log.info("esop用户在ekkeper端修改密码调用该接口custId: {},custType{},绑定的账号{}也一并修改了密码", custId, custInfo.getCustType(), custInfo.getBindCust());

                    LambdaQueryWrapper<CustOldPasswordEntity> oldBindCustqueryWrapper =
                            Wrappers.<CustOldPasswordEntity>lambdaQuery()
                                    .eq(CustOldPasswordEntity::getCustId, bindCust)
                                    .eq(CustOldPasswordEntity::getLoginActive, 0);
                    CustOldPasswordEntity oldBindCust = custOldPasswordService.getOne(oldBindCustqueryWrapper);
                    if (oldBindCust != null) {
                        oldBindCust.setLoginActive(1);
                        oldBindCust.setLoginActiveTime(new Date());
                        custOldPasswordService.updateById(oldBindCust);
                        log.info("custId: {}:激活存量密码", bindCust);
                    }
                }

            }

        }
    }

    @Override
    public List<CustInfoEntity> selectCustInfoListByIds(List<Long> custIds) {
        return new LambdaQueryChainWrapper<>(baseMapper)
                .in(BaseEntity::getId, custIds)
                .list();
    }

    @Override
    public List<CustInfoEntity> selectCustInfoListByCellPhone(String cellPhone) {
        return new LambdaQueryChainWrapper<>(baseMapper)
                .eq(CustInfoEntity::getCellPhone, cellPhone)
                .list();
    }

    @Override
    public R updateEmailById(Long custId, String email) {
        CustInfoEntity custInfo = this.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery().eq(CustInfoEntity::getId, custId));
        if (null != custInfo) {
            //如果是esop用户
            if (custInfo.getCustType() == CustEnums.CustType.ESOP.getCode()) {
                //准备更新的邮箱在数据库中是否存在,如果更新的这个邮箱是他自己的,就不管
                CustInfoEntity custInfoByUpdateEmail = this.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
                        .eq(CustInfoEntity::getCellEmail, email)
                        .eq(CustInfoEntity::getCustType, CustEnums.CustType.ESOP.getCode())
                        .ne(CustInfoEntity::getId, custId));
                if (custInfoByUpdateEmail == null) {
                    custInfo.setCellEmail(email);
                    int isUpdate = this.getBaseMapper().updateById(custInfo);
                    if (isUpdate == 1) {
                        this.updateCustInfoAndCache(custInfo);
                        return R.success();
                    } else {
                        return R.fail("更新邮箱失败");
                    }

                } else {
                    return R.fail(238003, "邮箱已经被使用");
                }
            } else {
                return R.fail(238002, "该用户非esop用户");
            }
        } else {
            return R.fail(238001, "用户不存在");
        }
    }
}
