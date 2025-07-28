package com.minigod.zero.cust.service.impl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.biz.common.CommonParams;
import com.minigod.zero.biz.common.constant.CommonConstant;
import com.minigod.zero.biz.common.constant.OpenApiConstant;
import com.minigod.zero.biz.common.enums.StockCommon;
import com.minigod.zero.biz.common.utils.RestTemplateUtil;
import com.minigod.zero.biz.common.utils.Util;
import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.cms.entity.oms.OmsParamsEntity;
import com.minigod.zero.cms.feign.oms.IOmsParamsClient;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.ZeroUser;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.cust.apivo.resp.QuotLevelInfoResp;
import com.minigod.zero.cust.entity.CustInvestorAnswerEntity;
import com.minigod.zero.cust.entity.CustInvestorStmtEntity;
import com.minigod.zero.cust.mapper.CustInvestorStmtMapper;
import com.minigod.zero.cust.mapper.SecuritiesInfoMapper;
import com.minigod.zero.cust.service.ICustInvestorAnswerService;
import com.minigod.zero.cust.service.ICustInvestorStmtService;
import com.minigod.zero.cust.vo.ConfigTemp;
import com.minigod.zero.cust.vo.request.UserInvestorStmtReqVo;
import com.minigod.zero.cust.vo.request.UserInvestorStmtVo;

/**
 * 投资者声明信息（美股） 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Service
public class CustInvestorStmtServiceImpl extends BaseServiceImpl<CustInvestorStmtMapper, CustInvestorStmtEntity> implements ICustInvestorStmtService {

	@Value("${us.ts.start.time:21}")
	public String US_TS_START_TIME_KEY;
	@Value("${us.ts.end.time:04:00:00}")
	private String US_TS_END_TIME_KEY;
	@Value("${hk.ts.am.start.time:09:30:00}")
	private String HK_TS_AM_START_TIME_KEY;
	@Value("${hk.ts.am.end.time:12:00:00}")
	private String HK_TS_AM_END_TIME_KEY;
	@Value("${hk.ts.pm.start.time:13:00:00}")
	private String HK_TS_PM_START_TIME_KEY;
	@Value("${hk.ts.pm.end.time:16:00:00}")
	private String HK_TS_PM_END_TIME_KEY;
	@Value("${us.region.timezone:EDT}")
	private String US_REGION_TIMEZONE_KEY;
	@Value("${zhizhu.oauth.tenantId}")
	public String tenantId; //租户ID

	@Resource
	private ZeroRedis zeroRedis;
	@Resource
	private IOmsParamsClient omsParamsClient;
	@Resource
	private SecuritiesInfoMapper securitiesInfoMapper;
	@Resource
	private ICustInvestorAnswerService custInvestorAnswerService;
	@Resource
	private RestTemplateUtil restTemplateUtil;

	private Date plusHours(Date setDate) {
		if (!isEDT()) { //冬令时
			DateTime dateTime = new DateTime(setDate);
			return dateTime.plusHours(1).toDate();
		}
		return setDate;
	}

	protected boolean isEDT() {
		if ("EDT".equals(US_REGION_TIMEZONE_KEY)) { //夏令时
			return true;
		} else { //冬令时
			return false;
		}
	}

	private Date minusDays(Date setDate) {
		//判断是否减去一天
		DateTime dateTime = new DateTime(setDate);
		int hourOfDay = dateTime.getHourOfDay();
		if (hourOfDay >= 0 && hourOfDay < 12) { //代表美国时间是第二天
			return dateTime.plusDays(-1).toDate();
		}
		return dateTime.toDate();
	}

	private Date addDays(Date setDate) {
		//判断是否减去一天
		DateTime dateTime = new DateTime(setDate);
		int hourOfDay = dateTime.getHourOfDay();
		if (hourOfDay >= 12 && hourOfDay < 24) {
			return dateTime.plusDays(1).toDate();
		}
		return dateTime.toDate();
	}

	@Override
	public List<ConfigTemp> findQuestions() {
		String lang = WebUtil.getHeader(CommonConstant.LANG);
		if (StringUtils.isBlank(lang)) {
			lang = CommonConstant.ZH_CN;
		}

		Map<String, OmsParamsEntity> investorStmtQuestion = omsParamsClient.findAllKeyVal("investor_stmt_question_" + lang);
		List<ConfigTemp> configTemps = this.setConfigTemp(investorStmtQuestion.entrySet());
		Util.sort(configTemps, new String[]{"keyName"}, new int[]{0});
		return configTemps;
	}

	private List<ConfigTemp> setConfigTemp(Set<Map.Entry<String, OmsParamsEntity>> oldSet) {
		List<ConfigTemp> newConfigList = new ArrayList<>();
		for (Map.Entry<String, OmsParamsEntity> entry : oldSet) {
			OmsParamsEntity param = entry.getValue();
			String keyName = param.getKeyName();
			String keyValue = param.getKeyValue();

			ConfigTemp configTemp = new ConfigTemp();
			configTemp.setKeyName(Integer.valueOf(keyName));
			configTemp.setKeyValue(keyValue);
			newConfigList.add(configTemp);
		}
		return newConfigList;
	}

	@Override
	public boolean isInvestorStmt(Integer investorStmtType) {
		boolean setTimeContainCurDate = true;
		try {
			//1、判断当前是否存在记录，存在直接返回
			if (!isInvestorBySessionId()) {
				return false;
			}
			if (investorStmtType == null) { //表示是旧版本
				setTimeContainCurDate = isSetTimeContainCurDate();
			}
		} catch (Exception e) {
			log.error("isInvestorStmt method eror", e);
			return false;
		}
		return setTimeContainCurDate;
	}

	private boolean isInvestorBySessionId() {
		try {
			//1、判断当前是否用户登录
			ZeroUser user = AuthUtil.getUser();
			if (user == null) {
				return false;
			}
			Long custId = user.getCustId();
			CustInvestorStmtEntity investorStmt = zeroRedis.protoGet(custId.toString(), CustInvestorStmtEntity.class);
			if (investorStmt == null) {
				investorStmt = this.findUserInvestorStmt(custId);
				saveUserInvestorStmt(investorStmt);
			}
			if (investorStmt != null) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	//保存到redis中
	private void saveUserInvestorStmt(CustInvestorStmtEntity investorStmt) {
		if (investorStmt != null) {
			try {
				zeroRedis.protoSet(investorStmt.getCustId().toString(), investorStmt);
			} catch (Exception e) {
				log.error("UserInvestorStmt save error", e);
			}
		}
	}

	@Override
	public CustInvestorStmtEntity findUserInvestorStmt(Long custId) {
		LambdaQueryWrapper<CustInvestorStmtEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CustInvestorStmtEntity::getCustId, custId);
		queryWrapper.orderByDesc(BaseEntity::getCreateTime);
		queryWrapper.last("limit 1");
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public boolean isSetTimeContainCurDate() {
		//港股时间
		Date dateTime = new DateTime().toDate();
		Date hkAmDateStartTime = dateAutoCompletion(HK_TS_AM_START_TIME_KEY);
		Date hkAmDateEndTime = dateAutoCompletion(HK_TS_AM_END_TIME_KEY);
		Date hkPmDateStartTime = dateAutoCompletion(HK_TS_PM_START_TIME_KEY);
		Date hkPmDateEndTime = dateAutoCompletion(HK_TS_PM_END_TIME_KEY);
		if (isContainDate(dateTime, hkAmDateStartTime) && !isContainDate(dateTime, hkAmDateEndTime)) {//港股上午--当前时间>开始时间,并且当前时间<结束时间
			return false;
		}
		if (isContainDate(dateTime, hkPmDateStartTime) && !isContainDate(dateTime, hkPmDateEndTime)) {//港股下午--当前时间>开始时间,并且当前时间<结束时间
			return false;
		}

		//美股时间
		Date usDateStartTime = plusHours(dateAutoCompletion(minusDays(dateTime), US_TS_START_TIME_KEY));
		Date usDateEndTime = plusHours(dateAutoCompletion(addDays(dateTime), US_TS_END_TIME_KEY));
		if (isContainDate(dateTime, usDateStartTime) && !isContainDate(dateTime, usDateEndTime)) {//美股当前时间>开始时间,并且当前时间<结束时间
			return false;
		}
		return true;
	}

	//判断是否在日期之内
	private boolean isContainDate(Date nowDate, Date setDate) {
		if (nowDate != null && setDate != null) {
			if (nowDate.compareTo(setDate) > 0) {
				return true;
			}
		}
		return false;
	}

	//日期自动补全
	private Date dateAutoCompletion(String dateTimeStr) {
		return dateAutoCompletion(new Date(), dateTimeStr);
	}

	//日期自动补全
	private Date dateAutoCompletion(Date now, String dateTimeStr) {
		if (StringUtils.isNotBlank(dateTimeStr)) {
			String fullDateTimeStr = DateUtil.format(now, "yyyy-MM-dd ") + dateTimeStr;
			return DateUtil.parseDate(fullDateTimeStr);
		}
		return null;
	}

	@Override
	public boolean isIsInvestor() {
		try {
			//1、判断当前是否用户登录
			ZeroUser user = AuthUtil.getUser();
			if (user == null) {
				return false;
			}
			Long custId = user.getCustId();
			CustInvestorStmtEntity investorStmt = zeroRedis.protoGet(custId.toString(), CustInvestorStmtEntity.class);
			if (investorStmt == null) {
				investorStmt = this.findUserInvestorStmt(custId);
				saveUserInvestorStmt(investorStmt);
			}
			if (investorStmt != null && investorStmt.getInvestorType() == 0) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	@Override
	public Integer isInvestorPackage() {
		try {
			//1、判断当前是否用户登录
			ZeroUser user = AuthUtil.getUser();
			if (user == null) {
				return -1;
			}
			Long custId = user.getCustId();
			CustInvestorStmtEntity investorStmt = zeroRedis.protoGet(custId.toString(), CustInvestorStmtEntity.class);
			if (investorStmt == null) {
				investorStmt = this.findUserInvestorStmt(custId);
				saveUserInvestorStmt(investorStmt);
			}
			if (investorStmt == null) {
				return -1;
			}
			if (investorStmt != null && investorStmt.getInvestorType() == 0) {
				return 1;
			}

			return 0;
		} catch (Exception e) {
			log.error("获取投资者声明标识异常", e);
			return -1;
		}
	}

	@Override
	public R isInvestorStmt(UserInvestorStmtReqVo reqVo) {
		Map<String, Object> returnMap = new HashMap<>();
		Integer investorStmtType = reqVo.getParams().getInvestorStmtType();
		returnMap.put("isInvestorStmt", this.isInvestorStmt(investorStmtType));
		returnMap.put("isInvestor", this.isIsInvestor());
		Integer isDoInvestor = this.isInvestorPackage();
		returnMap.put("isDoInvestor", isDoInvestor != null && isDoInvestor != -1);
		returnMap.put("questions", this.findQuestions());
		return R.data(returnMap);
	}

	@Override
	public R findInvestorStmt(UserInvestorStmtReqVo investorStmtType) {
		try {
			Map<String, Object> returnMap = new HashedMap();
			LambdaQueryWrapper<BpmSecuritiesInfoEntity> custOpenQueryWrapper = new LambdaQueryWrapper<>();
			custOpenQueryWrapper.eq(BpmSecuritiesInfoEntity::getCustId, AuthUtil.getCustId());
			BpmSecuritiesInfoEntity custSecuritiesInfoEntity = securitiesInfoMapper.selectOne(custOpenQueryWrapper);
			CustInvestorStmtEntity userInvestorStmt = null;
			if (custSecuritiesInfoEntity != null) {
				userInvestorStmt = this.buildUserInvestorStmt(custSecuritiesInfoEntity);
			}
			returnMap.put("default", userInvestorStmt);
			returnMap.putAll(this.findInvestorStmtConf());
			return R.data(returnMap);
		} catch (Exception e) {
			log.error("ICustInvestorStmtService findInvestorStmt error", e);
			return R.fail();
		}
	}

	@Override
	public R saveInvestorStmt(UserInvestorStmtReqVo reqVo) {
		try {
			UserInvestorStmtVo params = reqVo.getParams();
			CustInvestorStmtEntity userInvestorStmt = new CustInvestorStmtEntity();
			userInvestorStmt.setInvestorType(1);
			userInvestorStmt.setCustId(AuthUtil.getCustId());
			BeanUtils.copyProperties(userInvestorStmt, params);
			this.saveInvestorStmt(userInvestorStmt);
			String answerReq = params.getAnswer();
			boolean isYes = saveUserInvestorStmtAnswer(answerReq, userInvestorStmt);
			if (isYes) {//如果选择是,那么就是专业投资者
				userInvestorStmt.setInvestorType(0);
				this.updateInvestorStmt(userInvestorStmt);
			}
			// 并且做行情推送
			this.handelUserPermission(userInvestorStmt.getInvestorType());
			boolean isInvestor = this.isIsInvestor();
			Map<String, Boolean> map = new HashMap<>();
			map.put("isInvestor", isInvestor);
			return R.data(map);
		} catch (Exception e) {
			log.error("copyProperties error", e);
			return R.fail();
		}
	}

	private void handelUserPermission(Integer investorType) {
		// 并且做行情推送
		HashMap<String, Object> reqParams = new HashMap<>();
		reqParams.put("custId", AuthUtil.getCustId());
		reqParams.put("tenantId", tenantId);
		reqParams.put("terminalId", StockCommon.varietiesDeviceEnum.APP.getCode());// 目前只有APP场景
		reqParams.put("clientId", AuthUtil.getClientId());
		reqParams.put("ip", WebUtil.getIP());
		reqParams.put("isInvestor", investorType != null && investorType == 0 ? Boolean.TRUE : Boolean.FALSE);
		restTemplateUtil.postSend(OpenApiConstant.HANDEL_USER_PROFESSION, QuotLevelInfoResp.class, reqParams);
	}

	@Override
	public void updateInvestorStmt(CustInvestorStmtEntity userInvestorStmt) {
		try {
			userInvestorStmt.setUpdateUser(CommonParams.ADMIN_ACCOUNT_ID);
			this.updateById(userInvestorStmt);
			zeroRedis.hDel(CustInvestorStmtEntity.class, userInvestorStmt.getCustId().toString());
			saveUserInvestorStmt(userInvestorStmt);
		} catch (Exception e) {
			log.error("保存专业投资者错误", e);
		}
	}

	private boolean saveUserInvestorStmtAnswer(String answerReq, CustInvestorStmtEntity userInvestorStmt) {
		boolean isYes = false;
		if (StringUtils.isNotBlank(answerReq)) {
			String[] answerArray = answerReq.split("-");
			if (answerArray != null || answerArray.length > 0) {
				for (int i = 0; i < answerArray.length; i++) {
					CustInvestorAnswerEntity userInvestorStmtAnswer = new CustInvestorAnswerEntity();
					String answer = answerArray[i];
					if ("1".equals(answer)) {
						isYes = true;
					}
					userInvestorStmtAnswer.setAnswerIndex(i);
					userInvestorStmtAnswer.setAnswerResult(Integer.valueOf(answer));
					userInvestorStmtAnswer.setInvestorId(userInvestorStmt.getId());
					userInvestorStmtAnswer.setCreateUser(CommonParams.ADMIN_ACCOUNT_ID);
					userInvestorStmtAnswer.setUpdateUser(CommonParams.ADMIN_ACCOUNT_ID);
					custInvestorAnswerService.save(userInvestorStmtAnswer);
				}
			}
		}
		return isYes;
	}

	private void saveInvestorStmt(CustInvestorStmtEntity userInvestorStmt) {
		try {
			clearUnnecessaryData(userInvestorStmt);
			this.save(userInvestorStmt);
			zeroRedis.hDel(CustInvestorStmtEntity.class, userInvestorStmt.getCustId().toString());
			saveUserInvestorStmt(userInvestorStmt);
		} catch (Exception e) {
			log.error("ICustInvestorStmtService saveInvestorStmt eror", e);
		}
	}

	private void clearUnnecessaryData(CustInvestorStmtEntity userInvestorStmt) {
		Integer postType = userInvestorStmt.getPostType();
		if (postType != null) {
			int individual = 2;//自营
			int student = 3; //学生
			if (postType == individual) {
				userInvestorStmt.setPostLevel(null);
			} else if (postType == student) {
				userInvestorStmt.setCompanyName(null);
				userInvestorStmt.setVocation(null);
				userInvestorStmt.setPostLevel(null);
			}
		}
	}

	private CustInvestorStmtEntity buildUserInvestorStmt(BpmSecuritiesInfoEntity securitiesUserFullInfo) {
		CustInvestorStmtEntity userInvestorStmt = new CustInvestorStmtEntity();
		try {
			Integer professionCode = securitiesUserFullInfo.getProfessionCode();
			if (professionCode != null) {
				userInvestorStmt.setPostType(professionCode);//职位类型
			}
			String companyName = securitiesUserFullInfo.getCompanyName();
			if (StringUtils.isNotBlank(companyName)) {
				userInvestorStmt.setCompanyName(companyName);//公司名称
			}
			Integer professionType = securitiesUserFullInfo.getProfessionType();
			if (professionType != null) {
				userInvestorStmt.setVocation(professionType);//行业
			}
			String jobPosition = securitiesUserFullInfo.getJobPosition();
			if (StringUtils.isNotBlank(jobPosition)) {
				userInvestorStmt.setPostLevel(Integer.valueOf(jobPosition));//职位级别
			}
			userInvestorStmt.setInvestorType(null);
			userInvestorStmt.setCreateTime(null);
			userInvestorStmt.setUpdateTime(null);
		} catch (Exception e) {
			//忽略
		}
		return userInvestorStmt;
	}

	@Override
	public Map<String, List<ConfigTemp>> findInvestorStmtConf() {
		String lang = WebUtil.getHeader(CommonConstant.LANG);
		if (StringUtils.isBlank(lang)) {
			lang = CommonConstant.ZH_CN;
		}

		Map<String, List<ConfigTemp>> confMap = new HashedMap();
		Map<String, OmsParamsEntity> userProfessionCode = omsParamsClient.findAllKeyVal("user_profession_code_" + lang);//职位类型
		Map<String, OmsParamsEntity> userOccupationType = omsParamsClient.findAllKeyVal("user_occupation_type_" + lang);//行业
		Map<String, OmsParamsEntity> userJobPosition = omsParamsClient.findAllKeyVal("user_job_position_" + lang);//职位级别

		confMap.put("postTypeList", this.sort(this.setConfigTemp(userProfessionCode.entrySet())));
		confMap.put("vocationList", this.sort(this.setConfigTemp(userOccupationType.entrySet())));
		confMap.put("postLevelList", this.sort(this.setConfigTemp(userJobPosition.entrySet())));
		return confMap;
	}

	private List<ConfigTemp> sort(List<ConfigTemp> configTemps) {
		Util.sort(configTemps, new String[]{"keyName"}, new int[]{0});
		return configTemps;
	}
}
