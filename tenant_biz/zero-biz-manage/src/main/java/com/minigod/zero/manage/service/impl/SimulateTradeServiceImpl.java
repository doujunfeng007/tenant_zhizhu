//package com.minigod.zero.oms.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.google.common.collect.Lists;
//import com.minigod.zero.biz.common.utils.AESUtil;
//import com.minigod.zero.biz.common.utils.DateUtil;
//import com.minigod.zero.biz.common.utils.DateUtils;
//import com.minigod.zero.biz.common.utils.HttpClientUtils;
//import com.minigod.zero.core.secure.utils.AuthUtil;
//import com.minigod.zero.core.tool.api.R;
//import com.minigod.zero.core.tool.api.ResultCode;
//import com.minigod.zero.core.tool.utils.BeanUtil;
//import com.minigod.zero.manage.entity.*;
//import com.minigod.zero.manage.enums.CompetitionFilterTypeEnum;
//import com.minigod.zero.manage.enums.SimulateAccountStateEnum;
//import com.minigod.zero.manage.enums.TradeEntrustActionEnum;
//import com.minigod.zero.manage.enums.TradeOrderStateEnum;
//import com.minigod.zero.manage.vo.simulatetrade.*;
//import com.minigod.zero.oms.excel.*;
//import com.minigod.zero.oms.mapper.SimuAssetInfoMapper;
//import com.minigod.zero.oms.mapper.SimulateTradeMapper;
//import com.minigod.zero.oms.proxy.CustomerProxy;
//import com.minigod.zero.oms.service.SimulateTradeService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.io.InputStream;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.nio.charset.StandardCharsets;
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//
///**
// * 模拟交易
// */
//@Slf4j
//@Service
//public class SimulateTradeServiceImpl implements SimulateTradeService {
//
//	private static final BigDecimal HUNDRED = new BigDecimal(100);
//	@Value("${simulate.trade.api.url}")
//	private String simulateTradeApiUrl;
//	// 模拟交易服务接口的url
//	private static String addEntrustOrderUrl;
//	// 模拟交易服务接口的密钥
//	@Value("${simulate.trade.api.secret}")
//	private String simulateTradeApiSecret;
//	// 港股收盘时间
//	@Value("${hk.ts.pm.end.time}")
//	private String hkTradeCloseTime;
//
//	@Resource
//	SimulateTradeMapper simulateTradeMapper;
//	@Resource
//	SimuAssetInfoMapper simuAssetInfoMapper;
//	@Resource
//	CustomerProxy customerProxy;
//
//	@PostConstruct
//	public void init() {
//		addEntrustOrderUrl = simulateTradeApiUrl + "/open/tradeApi/buyByWebOms";
//	}
//
//	/**
//	 * 获取模拟比赛列表
//	 *
//	 * @param param 搜索参数
//	 * @return SNPage<SimulateCompetitionVO>
//	 */
//	@Override
//	public IPage<SimulateCompetitionEntity> listCompetition(IPage<SimulateCompetitionEntity> page, SimulateCompetitionSearchVO param) {
//		return page.setRecords(simulateTradeMapper.listSimulateCompetition(page, param));
//	}
//
//	/**
//	 * 获取模拟比赛列表的id与名称
//	 *
//	 * @return List<SimulateCompetitionVO>
//	 */
//	@Override
//	public List<SimulateCompetitionVO> listCompetitionIdName() {
//		return simulateTradeMapper.listSimulateCompetitionIdName();
//	}
//
//	/**
//	 * 更新模拟比赛
//	 *
//	 * @param param 请求参数
//	 * @return boolean
//	 */
//	@Override
//	public boolean updateSimulateCompetition(SimulateCompetitionVO param) {
//		SimulateCompetitionEntity simulateCompetition = simulateTradeMapper.getSimulateCompetition(param.getId());
//		if (simulateCompetition == null) {
//			log.error("获取比赛信息失败，id:{}", param.getId());
//			return false;
//		}
//		//更新初始模拟资金
//		param.setOpenMarketFlag(simulateCompetition.getOpenMarketFlag());
//		if (!updateInitAmount(param)) {
//			return false;
//		}
//		Date endDate = DateUtils.stringToDate(param.getEndTime() + hkTradeCloseTime, "yyyy-MM-dd HH:mm:ss");
//		//更新比赛信息
//		Date startTime = DateUtils.getStartDate(param.getStartTime());
//		SimulateCompetitionEntity data = new SimulateCompetitionEntity();
//		data.setId(param.getId());
//		data.setCompetitionName(param.getCompetitionName());
//		data.setConditionDescription(param.getConditionDescription());
//		data.setRegisterStartTime(DateUtils.getStartDate(param.getRegisterStartTime()));
//		data.setRegisterEndTime(DateUtils.getEndDate(param.getRegisterEndTime()));
//		data.setStartTime(startTime);
//		data.setEndTime(endDate);
//		data.setPromulgateTime(DateUtils.getDateByStr(param.getPromulgateTime()));
//		data.setAwardTime(DateUtils.getDateByStr(param.getAwardTime()));
//		data.setIsEffective(param.getIsEffective());
//		data.setPriority(param.getPriority());
//		data.setContentDescription(param.getContentDescription());
//		data.setAttentionDescription(param.getAttentionDescription());
//		data.setAwardDescription(param.getAwardDescription());
//		data.setUpdateTime(new Date());
//		data.setOpenMarketFlag(simulateCompetition.getOpenMarketFlag());
//		data.setFilterType(simulateCompetition.getFilterType());
//		data.setInviteCode(param.getInviteCode());
//		data.setIsInvite(param.getIsInvite());
//		boolean flag = simulateTradeMapper.updateSimulateCompetition(data);
//		//更新
//		if (startTime.getTime() > System.currentTimeMillis()) {
//			flag = simulateTradeMapper.updateSimulateAccountTradeCount(Long.valueOf(param.getId()), param.getTradeCount());
//		}
//		return flag;
//	}
//
//	/**
//	 * 更新模拟比赛的初始资金
//	 *
//	 * @param param 请求参数
//	 * @return boolean
//	 */
//	private boolean updateInitAmount(SimulateCompetitionVO param) {
//		String config = simulateTradeMapper.getSysConfigKeyVal("account", param.getId() + "_init");
//		JSONObject initConfig = JSON.parseObject(config);
//		String initConfigKeyValue = null;
//		long initAmount = param.getInitAmount() * 10000; //前端提交的数值单位：万
//		switch (param.getOpenMarketFlag()) {
//			case 1:
//				if (initConfig.getLong("hk_amount") != initAmount || initConfig.getInteger("hk_trade_count") != param.getTradeCount().intValue()) {
//					initConfig.put("hk_amount", initAmount);
//					initConfig.put("hk_trade_count", param.getTradeCount());
//					initConfigKeyValue = JSON.toJSONString(initConfig);
//				}
//				break;
//			case 2:
//				if (initConfig.getLong("us_amount") != initAmount || initConfig.getInteger("us_trade_count") != param.getTradeCount().intValue()) {
//					initConfig.put("us_amount", initAmount);
//					initConfig.put("us_trade_count", param.getTradeCount());
//					initConfigKeyValue = JSON.toJSONString(initConfig);
//				}
//				break;
//			default:
//				log.error("更新模拟比赛异常：openMarketFlag不正确：{}", param.getOpenMarketFlag());
//				return false;
//		}
//		if (initConfigKeyValue != null) {
//			int i = simulateTradeMapper.updateSysConfigKeyVal("account", param.getId() + "_init", initConfigKeyValue);
//			if (i == 0) {
//				log.error("更新模拟比赛的初始资金出现异常，initConfigKeyValue:{}， param:{}", initConfigKeyValue, JSON.toJSONString(param));
//				return false;
//			}
//		}
//		return true;
//	}
//
//	/**
//	 * 保存模拟比赛
//	 *
//	 * @param param 请求参数
//	 * @return boolean
//	 */
//	@Override
//	public R saveSimulateCompetition(SimulateCompetitionVO param) {
//		try {
//			//保存比赛信息
//			param.setPromulgateTime(param.getPromulgateTime() + DATETIME_BEGIN);
//			param.setAwardTime(param.getAwardTime() + DATETIME_BEGIN);
//			param.setRegisterStartTime(param.getRegisterStartTime() + DATETIME_BEGIN);
//			param.setRegisterEndTime(param.getRegisterEndTime() + DateUtils.DATETIME_END);
//			param.setStartTime(param.getStartTime() + DATETIME_BEGIN);
//			param.setEndTime(param.getEndTime() + hkTradeCloseTime);
//			param.setFilterType(CompetitionFilterTypeEnum.NONE.getType());
//			SimulateCompetitionEntity simulateCompetitionEntity = BeanUtil.copy(param, SimulateCompetitionEntity.class);
//			simulateCompetitionEntity.setRegisterStartTime(DateUtil.parseDate(param.getRegisterStartTime()));
//			simulateCompetitionEntity.setRegisterEndTime(DateUtil.parseDate(param.getRegisterEndTime()));
//			simulateCompetitionEntity.setStartTime(DateUtil.parseDate(param.getStartTime()));
//			simulateCompetitionEntity.setEndTime(DateUtil.parseDate(param.getEndTime()));
//			simulateCompetitionEntity.setPromulgateTime(DateUtil.parseDate(param.getPromulgateTime()));
//			simulateCompetitionEntity.setAwardTime(DateUtil.parseDate(param.getAwardTime()));
//			simulateCompetitionEntity.setCreateTime(new Date());
//			simulateCompetitionEntity.setUpdateTime(new Date());
//			simulateCompetitionEntity.setOpenMarketFlag(1);// 暂时用不上
//			simulateCompetitionEntity.setTenantId(AuthUtil.getTenantId());
//			simulateTradeMapper.addSimulateCompetition(simulateCompetitionEntity);
//			Long id = simulateTradeMapper.selectSimulateCompetitionLastId();
//
//			//保存初始化资金
//			long initAmount = param.getInitAmount() * 10000; //前端提交的数值单位：万
//			JSONObject amount = new JSONObject();
//			amount.put("hk_amount", initAmount);
//			amount.put("hk_trade_count", param.getTradeCount());
//			amount.put("us_amount", initAmount);
//			amount.put("us_trade_count", param.getTradeCount());
//			Date now = new Date();
//			SimuSysConfigEntity simuSysConfigEntity = new SimuSysConfigEntity();
//			simuSysConfigEntity.setModuleName("account");
//			simuSysConfigEntity.setKeyName(id + "_init");
//			simuSysConfigEntity.setRemark("初始化资金");
//			simuSysConfigEntity.setKeyValue(JSON.toJSONString(amount));
//			simuSysConfigEntity.setIsStatus(1);
//			simuSysConfigEntity.setCreateTime(now);
//			simuSysConfigEntity.setUpdateTime(now);
//			simuSysConfigEntity.setUpdVersion(1L);
//			List<SimuSysConfigEntity> simuSysConfigEntities = Arrays.asList(simuSysConfigEntity);
//			simulateTradeMapper.addSysConfigKeyVal(simuSysConfigEntities);
//			return R.success();
//		} catch (Exception e) {
//			log.error("新增比赛的出现异常", e);
//			return R.fail();
//		}
//	}
//
//	/**
//	 * 排行榜列表（按实际排行榜名次先后排序）
//	 *
//	 * @param param 请求参数
//	 * @return SNPage<CompetitionRankDataVO>
//	 */
//	@Override
//	public IPage<CompetitionRankDataVO> listRankData(IPage<CompetitionRankDataVO> page, SimulateCompetitionSearchVO param) {
//		List<CompetitionRankDataVO> rankList = null;
//		try {
//			rankList = simulateTradeMapper.listRankData(page, param);
//			if (CollectionUtils.isNotEmpty(rankList)) {
//				List<Long> userIdList = rankList.stream().map(CompetitionRankDataVO::getUserId).distinct().collect(Collectors.toList());
//				Map<Long, CustomerInfoEntity> customerInfoMap = getCustomerInfoMap(userIdList);
//				for (CompetitionRankDataVO data : rankList) {
//					BigDecimal marketValue = data.getMarketValue() != null ? formatNum(data.getMarketValue()) : BigDecimal.ZERO;
//					BigDecimal todayProfit = data.getTodayProfit() != null ? formatNum(data.getTodayProfit()) : BigDecimal.ZERO;
//					BigDecimal totalProfit = data.getTotalProfit() != null ? formatNum(data.getTotalProfit()) : BigDecimal.ZERO;
//					long competitionDays = data.getCompetitionDays() != null ? data.getCompetitionDays() : 0;
//					int tradeCount = data.getTradeCount() != null ? data.getTradeCount() : 0;
//					data.setTradeCount(tradeCount); //已交易次数
//					data.setCompetitionDays(competitionDays);
//					data.setEnableFund(formatNum(data.getEnableFund()));
//					data.setMarketValue(marketValue);
//					data.setTodayProfit(todayProfit);
//					data.setTotalProfit(totalProfit);
//					data.setStatus(data.getEndTime() != null ? data.getEndTime().getTime() < System.currentTimeMillis() ? "已结束" : "进行中" : "");
//					data.setProfitRate(formatProfitRate(data.getProfitRate()));
//					CustomerInfoEntity customerInfoEntity = customerInfoMap.get(data.getUserId());
//					data.setNickName(customerInfoEntity != null ? customerInfoEntity.getNickName() : "N/A");
//					if (StringUtils.isBlank(data.getPhoneNumber())) {
//						data.setPhoneNumber(getPhone(data.getUserId(), data.getPhoneNumber(), customerInfoMap));
//					}
//				}
//			}
//		} catch (Exception e) {
//			log.error("获取排行榜列表有异常：", e);
//		}
//		return page.setRecords(rankList);
//	}
//
//	/**
//	 * 格式化收益率
//	 *
//	 * @param profitRate 收益率
//	 * @return BigDecimal
//	 */
//	private BigDecimal formatProfitRate(BigDecimal profitRate) {
//		return profitRate == null ? null : profitRate.multiply(HUNDRED).setScale(2, RoundingMode.UP).stripTrailingZeros();
//	}
//
//	private Map<Long, CustomerInfoEntity> getCustomerInfoMap(List<Long> userIdList) {
//		List<CustomerInfoEntity> customerInfoEntities = customerProxy.selectCustomerByCustIds(userIdList).getData();
//		return customerInfoEntities.stream().collect(Collectors.toMap(CustomerInfoEntity::getId, Function.identity()));
//	}
//
//	private Map<Long, CustomerInfoEntity> getCustomerInfoMap(String searchCondition) {
//		List<CustomerInfoEntity> customerInfoEntities = customerProxy.selectCustomerByPhoneOrCustId(searchCondition).getData();
//		return customerInfoEntities.stream().collect(Collectors.toMap(CustomerInfoEntity::getId, Function.identity()));
//	}
//
//	private BigDecimal formatNum(BigDecimal num) {
//		return num.setScale(3, RoundingMode.UP).stripTrailingZeros();
//	}
//
//	@Override
//	public List<SimRankDataExcel> rankDataExport(SimulateCompetitionSearchVO param) {
//		try {
//			List<CompetitionRankDataVO> rankList = simulateTradeMapper.listRankData(null, param);
//			if (CollectionUtils.isEmpty(rankList)) {
//				return null;
//			}
//			List<Long> userIdList = rankList.stream().map(CompetitionRankDataVO::getUserId).distinct().collect(Collectors.toList());
//			Map<Long, CustomerInfoEntity> customerInfoMap = getCustomerInfoMap(userIdList);
//			List<SimRankDataExcel> dataList = Lists.newLinkedList();
//			for (CompetitionRankDataVO data : rankList) {
//				String profitRate = data.getProfitRate() == null ? "N/A" : formatProfitRate(data.getProfitRate()) + "%";
//				long competitionDays = data.getCompetitionDays() != null ? data.getCompetitionDays() : 0;
//				int tradeCount = data.getTradeCount() != null ? data.getTradeCount() : 0;
//				SimRankDataExcel row = new SimRankDataExcel();
////				row.setOpenAccountTime(formatDatetime(data.getOpenAccountTime()));
//				row.setOpenDate(formatDatetime(data.getOpenDate()));
//				row.setStartTime(formatDate(data.getStartTime()));
//				row.setEndTime(formatDate(data.getEndTime()));
//				row.setCompetitionStatus(data.getEndTime() != null ? data.getEndTime().getTime() < System.currentTimeMillis() ? "已结束" : "进行中" : "");
//				row.setUserId(data.getUserId().toString());
////				row.setTradeAccount(StringUtils.isNotBlank(data.getTradeAccount()) ? data.getTradeAccount() : "N/A");
////				row.setClientName(StringUtils.isNotBlank(data.getClientName()) ? data.getClientName() : "N/A");
//				row.setNickName(StringUtils.isNotBlank(data.getNickName()) ? data.getNickName() : "N/A");
////				row.setClientNameSpell(StringUtils.isNotBlank(data.getClientNameSpell()) ? data.getClientNameSpell() : "N/A");
//				row.setPhoneNumber(getPhone(data.getUserId(), data.getPhoneNumber(), customerInfoMap));
//				row.setCompetitionName(data.getCompetitionName());
//				row.setMarketValue(data.getMarketValue());
//				row.setHoldProfit(data.getHoldProfit());
//				row.setEnableFund(data.getEnableFund());
//				row.setTotalProfit(data.getTotalProfit());
//				row.setTodayProfit(data.getTodayProfit());
//				row.setTradeCount(tradeCount); //已交易次数
//				row.setCompetitionDays(competitionDays);
//				row.setProfitRate(profitRate);
//				row.setRank(data.getRank() != 0 ? data.getRank().toString() : "-");
//				dataList.add(row);
//			}
//			return dataList;
//		} catch (Exception e) {
//			log.error("导出模拟比赛排行榜数据时出现异常：", e);
//			return null;
//		}
//	}
//
//	private String formatDatetime(Date date) {
//		return date != null ? DateUtils.dateToString(date, DateUtils.TimeFormatter.YYYY_MM_DD_HH_MM_SS) : "N/A";
//	}
//
//	private String formatDate(Date date) {
//		return date != null ? DateUtils.dateToString(date, DateUtils.TimeFormatter.YYYY_MM_DD) : "N/A";
//	}
//
//	private String getPhone(Long userId, String phone, Map<Long, CustomerInfoEntity> customerInfoMap) {
//		if (StringUtils.isNotBlank(phone)) {
//			return phone;
//		} else {
//			CustomerInfoEntity customerInfoEntity = customerInfoMap.get(userId);
//			if (customerInfoEntity != null) {
//				return customerInfoEntity.getAreaCode() + "-" + customerInfoEntity.getCellPhone();
//			}
//		}
//		return "N/A";
//	}
//
//	/**
//	 * 持仓数据列表
//	 *
//	 * @param param 请求参数
//	 * @return String
//	 */
//	@Override
//	public IPage<CompetitionPositionDataVO> listCompetitionPositionData(IPage<CompetitionPositionDataVO> page, SimulateCompetitionSearchVO param) {
//		List<CompetitionPositionDataVO> list = null;
//		try {
//			list = simulateTradeMapper.listPositionData(page, param);
//			if (CollectionUtils.isNotEmpty(list)) {
//				List<Long> userIdList = list.stream().map(CompetitionPositionDataVO::getUserId).distinct().collect(Collectors.toList());
//				Map<Long, CustomerInfoEntity> customerInfoMap = getCustomerInfoMap(userIdList);
//				for (CompetitionPositionDataVO data : list) {
//					SimuAssetInfoEntity asset = simuAssetInfoMapper.findAsset(data.getAssetId());
//					data.setStockName(asset != null ? asset.getStkName() : "N/A");
//					data.setPhoneNumber(getPhone(data.getUserId(), data.getPhoneNumber(), customerInfoMap));
//				}
//			}
//		} catch (Exception e) {
//			log.error("获取比赛持仓数据有异常：", e);
//		}
//		return page.setRecords(list);
//	}
//
//	/**
//	 * 导出模拟比赛持仓数据
//	 */
//	@Override
//	public List<SimPositionDataExcel> positionDataExport(List<Long> ids) {
//		try {
//			List<CompetitionPositionDataVO> list = simulateTradeMapper.listPositionDataByIds(ids);
//			if (CollectionUtils.isEmpty(list)) {
//				return null;
//			}
//			List<Long> userIdList = list.stream().map(CompetitionPositionDataVO::getUserId).distinct().collect(Collectors.toList());
//			Map<Long, CustomerInfoEntity> customerInfoMap = getCustomerInfoMap(userIdList);
//			List<SimPositionDataExcel> dataList = Lists.newLinkedList();
//			for (CompetitionPositionDataVO data : list) {
//				boolean isExist = false;
//				for (Long id : ids) {
//					if (id.compareTo(Long.valueOf(data.getId())) == 0) {
//						isExist = true;
//						break;
//					}
//				}
//				if (!isExist) {
//					continue;
//				}
//				SimuAssetInfoEntity asset = simuAssetInfoMapper.findAsset(data.getAssetId());
//				SimPositionDataExcel row = new SimPositionDataExcel();
//				row.setId(data.getId().toString());
//				row.setOpenDate(formatDatetime(data.getOpenDate()));
////				row.setTradeAccount(data.getTradeAccount() != null ? data.getTradeAccount() : "N/A");
////				row.setClientName(data.getClientName() != null ? data.getClientName() : "N/A");
////				row.setClientNameSpell(data.getClientNameSpell() != null ? data.getClientNameSpell() : "N/A");
//				row.setPhoneNumber(getPhone(data.getUserId(), data.getPhoneNumber(), customerInfoMap));
//				row.setCompetitionName(data.getCompetitionName());
//				row.setCompetitionId(data.getCompetitionId());
//				row.setStkName(asset != null ? asset.getStkName() : "N/A");
//				row.setAssetId(data.getAssetId());
//				row.setQty(data.getQty());
//				row.setCostPrice(data.getCostPrice());
//				dataList.add(row);
//			}
//			return dataList;
//		} catch (Exception e) {
//			log.error("导出模拟比赛持仓数据时出现异常：", e);
//			return null;
//		}
//	}
//
//	@Override
//	public R uploadPositionData(MultipartFile file) {
//		String filename = file.getOriginalFilename();
//		if (!filename.endsWith(".xlsx")) {
//			return R.fail("文件上传格式错误，请重新上传");
//		}
//		Object listObj = readPositionXls(file);
//		if (listObj instanceof R) {
//			return (R) listObj;
//		}
//		List<CompetitionPositionDataVO> positionDataList = (List<CompetitionPositionDataVO>) listObj;
//		if (CollectionUtils.isEmpty(positionDataList)) {
//			return R.fail("持仓数据excel无数据，或解析持仓数据excel出现异常");
//		}
//
//		try {
//			int count = 0; //数据量
//			int maxCount = 2000; //每次提交的最大数据量
//			List<List<CompetitionPositionDataVO>> list = Lists.newArrayList();
//			List<CompetitionPositionDataVO> updateList = Lists.newArrayList();
//			for (CompetitionPositionDataVO data : positionDataList) {
//				//把数据分成n个分组进行依次更新，防止单条sql过长
//				if (count++ > maxCount) {
//					list.add(updateList);
//					updateList = Lists.newArrayList();
//				}
//				updateList.add(data);
//			}
//			if (updateList.size() > 0) {
//				list.add(updateList);
//			}
//
//			for (List<CompetitionPositionDataVO> dataList : list) {
//				simulateTradeMapper.updateHoldStock(dataList, dataList.size());
//			}
//			return R.success("更新模拟比赛持仓数据成功");
//		} catch (Exception e) {
//			log.error("更新比赛持仓数据出现异常：", e);
//			return R.fail("更新模拟比赛持仓数据失败");
//		}
//	}
//
//	/**
//	 * 从持仓数据excel文件中读取数据
//	 *
//	 * @param file excel文件
//	 * @return List<CompetitionPositionDataVO>
//	 */
//	public Object readPositionXls(MultipartFile file) {
//		try {
//			List<CompetitionPositionDataVO> list = new ArrayList<>();
//			InputStream inputStream = file.getInputStream();
//			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//			//读取第一张sheet
//			XSSFSheet sheet = workbook.getSheetAt(0);
//			if (sheet.getLastRowNum() < 4) {
//				log.error("无数据或数据格式不正确");
//				return null;
//			}
//			int idIndex = 0;
//			int qtyIndex = 10;
//			int costPriceIndex = 11;
//			//遍历每一行Excel获取内容
//			for (int rowNum = 3; rowNum <= sheet.getLastRowNum(); rowNum++) {
//				XSSFRow row = sheet.getRow(rowNum);
//				if (row == null) {
//					continue;
//				}
//				CompetitionPositionDataVO data = new CompetitionPositionDataVO();
//				Object id = getlong(row, idIndex, 0, "持仓数据的“ID”不是正整数：");
//				if (id instanceof R) {
//					return id;
//				}
//				if (id == null) {
//					continue;
//				}
//				Object qty = getlong(row, qtyIndex, 0, "持仓数据的“持有股数”不是正整数：");
//				if (qty instanceof R) {
//					return qty;
//				}
//				if (qty == null) {
//					continue;
//				}
//				Object costPrice = getDouble(row, costPriceIndex, 0D, "持仓数据的“成本价”不是大于0的数值：");
//				if (costPrice instanceof R) {
//					return costPrice;
//				}
//				if (costPrice == null) {
//					continue;
//				}
//				data.setId((int) id);
//				data.setQty((int) qty);
//				data.setCostPrice(new BigDecimal(costPrice.toString()));
//				list.add(data);
//			}
//			return list;
//		} catch (IOException e) {
//			log.error("解析持仓数据excel出现异常：", e);
//			return null;
//		}
//	}
//
//	/**
//	 * 获取int类型数值
//	 *
//	 * @param row      excel行数据
//	 * @param index    excel行数据的第几列（从0开始）
//	 * @param errorMsg 异常提示
//	 * @return Integer
//	 */
//	private Object getlong(XSSFRow row, int index, Integer min, String errorMsg) {
//		try {
//			XSSFCell cell = row.getCell(index);
//			if (cell == null) {
//				String msg = errorMsg + "没有值，行:" + row.getRowNum() + "，列:" + index;
//				log.error(msg);
//				return R.fail(msg);
//			}
//			return Double.valueOf(cell.toString()).longValue();
//		} catch (Exception e) {
//			String tips = "从excel中获取int数据时出错，行:" + row.getRowNum() + "，列:" + index;
//			log.error(tips, e);
//			return R.fail(tips + "<br/>" + e.getMessage());
//		}
//	}
//
//	/**
//	 * 获取double类型数值
//	 *
//	 * @param row      excel行数据
//	 * @param index    excel行数据的第几列（从0开始）
//	 * @param errorMsg 异常提示
//	 * @return Double
//	 */
//	private Object getDouble(XSSFRow row, int index, Double min, String errorMsg) {
//		try {
//			XSSFCell cell = row.getCell(index);
//			if (cell == null) {
//				String msg = errorMsg + "没有值，行:" + row.getRowNum() + "，列:" + index;
//				log.error(msg);
//				return R.fail(msg);
//			}
//			return Double.valueOf(cell.toString());
//		} catch (Exception e) {
//			String tips = "从excel中获取double数据时出错，行:" + row.getRowNum() + "，列:" + index;
//			log.error(tips, e);
//			return R.fail(tips + "<br/>" + e.getMessage());
//		}
//	}
//
//	@Override
//	public IPage<CompetitionRankDataVO> listRankHistoryData(IPage<CompetitionRankDataVO> page, SimulateCompetitionSearchVO param) {
//		List<CompetitionRankDataVO> rankList = null;
//		try {
//			rankList = simulateTradeMapper.listRankHistoryData(page, param);
//			if (CollectionUtils.isNotEmpty(rankList)) {
//				List<Long> userIdList = rankList.stream().map(CompetitionRankDataVO::getUserId).distinct().collect(Collectors.toList());
//				Map<Long, CustomerInfoEntity> customerInfoMap = getCustomerInfoMap(userIdList);
//				for (CompetitionRankDataVO data : rankList) {
//					long competitionDays = data.getCompetitionDays() != null ? data.getCompetitionDays() : 0;
//					int tradeCount = data.getTradeCount() != null ? data.getTradeCount() : 0;
//					data.setCompetitionDays(competitionDays);
//					data.setTradeCount(tradeCount); //已交易次数
//					data.setEnableFund(formatNum(data.getEnableFund()));
//					if (data.getMarketValue() != null) {
//						data.setMarketValue(formatNum(data.getMarketValue()));
//					}
//					if (data.getTotalProfit() != null) {
//						data.setTotalProfit(formatNum(data.getTotalProfit()));
//					}
//					if (StringUtils.isBlank(data.getPhoneNumber())) {
//						data.setPhoneNumber(getPhone(data.getUserId(), data.getPhoneNumber(), customerInfoMap));
//					}
//					if (data.getProfitRate() != null) {
//						data.setProfitRate(formatProfitRate(data.getProfitRate()));
//					}
//				}
//			}
//		} catch (Exception e) {
//			log.error("获取比赛历史数据有异常：", e);
//		}
//		return page.setRecords(rankList);
//	}
//
//	@Override
//	public List<SimRankHisDataExcel> rankHistoryDataExport(SimulateCompetitionSearchVO param) {
//		try {
//			List<CompetitionRankDataVO> rankList = simulateTradeMapper.listRankHistoryData(null, param);
//			if (CollectionUtils.isEmpty(rankList)) {
//				return null;
//			}
//
//			List<Long> userIdList = rankList.stream().map(CompetitionRankDataVO::getUserId).distinct().collect(Collectors.toList());
//			Map<Long, CustomerInfoEntity> customerInfoMap = getCustomerInfoMap(userIdList);
//			List<Long> idList = param.getIds();
//			List<SimRankHisDataExcel> dataList = Lists.newLinkedList();
//			for (CompetitionRankDataVO data : rankList) {
//				boolean isExist = false;
//				for (Long id : idList) {
//					if (id.compareTo(Long.valueOf(data.getId())) == 0) {
//						isExist = true;
//						break;
//					}
//				}
//				if (!isExist) {
//					continue;
//				}
//				String profitRate = data.getProfitRate() == null ? "N/A" : formatProfitRate(data.getProfitRate()) + "%";
//				long competitionDays = data.getCompetitionDays() != null ? data.getCompetitionDays() : 0;
//				int tradeCount = data.getTradeCount() != null ? data.getTradeCount() : 0;
//				SimRankHisDataExcel row = new SimRankHisDataExcel();
////				row.setOpenAccountTime(formatDatetime(data.getOpenAccountTime()));
//				row.setOpenDate(formatDatetime(data.getOpenDate()));
//				row.setStartTime(formatDate(data.getStartTime()));
//				row.setEndTime(formatDate(data.getEndTime()));
//				row.setCompetitionStatus(data.getEndTime() != null ? data.getEndTime().getTime() < System.currentTimeMillis() ? "已结束" : "进行中" : "");
//				row.setUserId(data.getUserId().toString());
////				row.setTradeAccount(StringUtils.isNotBlank(data.getTradeAccount()) ? data.getTradeAccount() : "N/A");
////				row.setClientName(StringUtils.isNotBlank(data.getClientName()) ? data.getClientName() : "N/A");
//				row.setNickName(StringUtils.isNotBlank(data.getNickName()) ? data.getNickName() : "N/A");
////				row.setClientNameSpell(StringUtils.isNotBlank(data.getClientNameSpell()) ? data.getClientNameSpell() : "N/A");
//				row.setPhoneNumber(getPhone(data.getUserId(), data.getPhoneNumber(), customerInfoMap));
//				row.setCompetitionName(data.getCompetitionName());
//				row.setMarketValue(data.getMarketValue() != null ? data.getMarketValue() : BigDecimal.ZERO);
//				row.setHoldProfit(data.getHoldProfit() != null ? data.getHoldProfit() : BigDecimal.ZERO);
//				row.setEnableFund(data.getEnableFund());
//				row.setTotalProfit(data.getTotalProfit() != null ? data.getTotalProfit() : BigDecimal.ZERO);
//				row.setTradeCount(tradeCount); //已交易次数
//				row.setCompetitionDays(competitionDays);
//				row.setProfitRate(profitRate);
//				row.setRank(data.getRank() != 0 ? data.getRank().toString() : "-");
//				dataList.add(row);
//			}
//			return dataList;
//		} catch (Exception e) {
//			log.error("导出模拟比赛历史数据时出现异常：", e);
//			return null;
//		}
//	}
//
//	@Override
//	public IPage<CompetitionEntrustOrderVO> listEntrustOrder(IPage<CompetitionEntrustOrderVO> page, SimulateCompetitionSearchVO param) {
//		List<CompetitionEntrustOrderVO> list = null;
//		try {
//			if (param.getCompetitionId() != null || StringUtils.isNotBlank(param.getTradeAccount()) || param.getUserId() != null || param.getOrderState() != null
//				|| StringUtils.isNotBlank(param.getAssetId()) || param.getOrderAction() != null
//				|| StringUtils.isNotBlank(param.getStartTime()) || StringUtils.isNotBlank(param.getEndTime())) {
//				list = simulateTradeMapper.listEntrustOrder(page, param);
//				for (CompetitionEntrustOrderVO data : list) {
//					SimuAssetInfoEntity asset = simuAssetInfoMapper.findAsset(data.getAssetId());
//					if (asset != null) {
//						data.setStockName(asset.getStkName());
//					}
//					data.setStatus(data.getEndTime().getTime() < System.currentTimeMillis() ? "已结束" : "进行中");
//					//设置交易类型名称:
//					if (data.getEntrustType() == TradeEntrustActionEnum.BUY_SPLIT.getType().intValue()) {
//						data.setOrderActionName(TradeEntrustActionEnum.BUY_SPLIT.getName());
//					} else if (data.getEntrustType() == TradeEntrustActionEnum.BUY_PRESENT.getType().intValue()) {
//						data.setOrderActionName(TradeEntrustActionEnum.BUY_PRESENT.getName());
//					} else {
//						data.setOrderActionName(TradeEntrustActionEnum.getNameByAction(data.getOrderAction()));
//					}
//					data.setOrderStateName(TradeOrderStateEnum.getNameByState(Integer.valueOf(data.getOrderState())));
//				}
//			}
//		} catch (Exception e) {
//			log.error("获取比赛交易委托有异常：", e);
//		}
//		return page.setRecords(list);
//	}
//
//	@Override
//	public List<EntrustOrderExcel> entrustOrderExport(List<Long> ids) {
//		try {
//			List<CompetitionEntrustOrderVO> list = simulateTradeMapper.listEntrustOrderByIds(ids);
//			if (CollectionUtils.isEmpty(list)) {
//				return null;
//			}
//			List<EntrustOrderExcel> dataList = Lists.newLinkedList();
//			for (CompetitionEntrustOrderVO data : list) {
//				boolean isExist = false;
//				for (Long id : ids) {
//					if (id.compareTo(Long.valueOf(data.getId())) == 0) {
//						isExist = true;
//						break;
//					}
//				}
//				if (!isExist) {
//					continue;
//				}
//				SimuAssetInfoEntity asset = simuAssetInfoMapper.findAsset(data.getAssetId());
//				EntrustOrderExcel row = new EntrustOrderExcel();
//				row.setCompetitionId(data.getCompetitionId().toString());
//				row.setCompetitionName(data.getCompetitionName());
//				row.setCompetitionStatus(data.getEndTime().getTime() < System.currentTimeMillis() ? "已结束" : "进行中");
//				row.setUserId(data.getUserId().toString());
////				row.setTradeAccount(data.getTradeAccount() != null ? data.getTradeAccount() : "N/A");
////				row.setClientName(data.getClientName() != null ? data.getClientName() : "N/A");
////				row.setClientNameSpell(data.getClientNameSpell() != null ? data.getClientNameSpell() : "N/A");
//				row.setOrderDate(formatDatetime(data.getOrderTime())); //订单日期
//				//设置交易类型名称:
//				if (data.getEntrustType() == TradeEntrustActionEnum.BUY_SPLIT.getType().intValue()) {
//					row.setTradeType(TradeEntrustActionEnum.BUY_SPLIT.getName());
//				} else if (data.getEntrustType() == TradeEntrustActionEnum.BUY_PRESENT.getType().intValue()) {
//					row.setTradeType(TradeEntrustActionEnum.BUY_PRESENT.getName());
//				} else {
//					row.setTradeType(TradeEntrustActionEnum.getNameByAction(data.getOrderAction()));
//				}
//				row.setOrderState(TradeOrderStateEnum.getNameByState(Integer.valueOf(data.getOrderState()))); //订单状态
//				row.setStkName(asset != null ? asset.getStkName() : "N/A");
//				row.setAssetId(data.getAssetId());
//				row.setOrderQty(data.getOrderQty());
//				row.setOrderPrice(data.getOrderPrice());
//				row.setTradeQty(data.getTradeQty());
//				row.setTradePrice(data.getTradePrice());
//				dataList.add(row);
//			}
//			return dataList;
//		} catch (Exception e) {
//			log.error("导出模拟比赛交易委托数据时出现异常：", e);
//			return null;
//		}
//	}
//
//	@Override
//	public R uploadEntrustOrder(MultipartFile file) {
//		String filename = file.getOriginalFilename();
//		if (!filename.endsWith(".xlsx")) {
//			return R.fail("模拟比赛交易委托excel文件上传格式错误，请重新上传");
//		}
//		Object listObj = readEntrustOrderXls(file);
//		if (listObj instanceof R) {
//			return (R) listObj;
//		}
//		List<CompetitionEntrustOrderVO> list = (List<CompetitionEntrustOrderVO>) listObj;
//		if (CollectionUtils.isEmpty(list)) {
//			return R.fail("模拟比赛交易委托excel无数据，或解析交易委托excel出现异常");
//		}
//		return addEntrustOrder(list);
//	}
//
//	/**
//	 * 从交易委托excel文件中读取数据
//	 *
//	 * @param file excel文件
//	 * @return List<CompetitionPositionDataVO>
//	 */
//	public Object readEntrustOrderXls(MultipartFile file) {
//		try {
//			List<CompetitionEntrustOrderVO> list = new ArrayList<>();
//			InputStream inputStream = file.getInputStream();
//			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//			//读取第一张sheet
//			int rowMin = 1;
//			XSSFSheet sheet = workbook.getSheetAt(0);
//			if (sheet.getLastRowNum() < rowMin) {
//				String tips = "上传的模拟比赛交易委托excel中无数据或数据格式不正确，lastRowNum:" + sheet.getLastRowNum();
//				log.error(tips);
//				return R.fail(tips);
//			}
//			int competitionIndex = 0; //比赛ID位置
//			int userIdIndex = 3; //用户ID位置
//			int orderActionNameIndex = 5; //交易类型位置
//			int assetIdIndex = 8; //资产ID位置
//			int orderQtyIndex = 9; //委托数量位置
//			int orderPriceIndex = 10; //委托价格位置
//			//遍历每一行Excel获取内容
//			for (int rowNum = rowMin; rowNum <= sheet.getLastRowNum(); rowNum++) {
//				XSSFRow row = sheet.getRow(rowNum);
//				if (row == null) {
//					continue;
//				}
//				CompetitionEntrustOrderVO data = new CompetitionEntrustOrderVO();
//				Object competitionId = getlong(row, competitionIndex, 0, "上传的模拟比赛交易委托excel中的“比赛ID”不是正整数");
//				if (competitionId instanceof R) {
//					return competitionId;
//				}
//				if (competitionId == null) {
//					continue;
//				}
//				Object userId = getlong(row, userIdIndex, 0, "上传的模拟比赛交易委托excel中的“用户ID”不是数字");
//				if (userId instanceof R) {
//					return userId;
//				}
//				if (userId == null) {
//					continue;
//				}
//				TradeEntrustActionEnum entrustActionEnum = TradeEntrustActionEnum.getEntrustAction(String.valueOf(row.getCell(orderActionNameIndex)));
//				if (entrustActionEnum == null) {
//					String tips = String.format("上传的模拟比赛交易委托excel中的“交易类型”有误：%s，rowNum:%s  columnIndex:%s",
//						row.getCell(orderActionNameIndex), row.getRowNum(), row.getCell(orderActionNameIndex).getColumnIndex());
//					log.error(tips);
//					return R.fail(tips);
//				}
//				String assetId = String.valueOf(row.getCell(assetIdIndex));
//				if (StringUtils.isBlank(assetId)) {
//					String tips = String.format("上传的模拟比赛交易委托excel中的“股票名称”有误：%s，rowNum:%s  columnIndex:%s",
//						row.getCell(assetIdIndex), row.getRowNum(), row.getCell(assetIdIndex).getColumnIndex());
//					log.error(tips);
//					return R.fail(tips);
//				}
//				Object orderQty = getlong(row, orderQtyIndex, 0, "上传的模拟比赛交易委托excel中的“数量”不是数字 或 小于等于0");
//				if (orderQty instanceof R) {
//					return orderQty;
//				}
//				if (orderQty == null) {
//					continue;
//				}
//				Object orderPrice = getDouble(row, orderPriceIndex, 0D, "上传的模拟比赛交易委托excel中的“价格”不是数字 或 小于等于0");
//				if (orderPrice instanceof R) {
//					return orderPrice;
//				}
//				if (orderPrice == null) {
//					continue;
//				}
//				data.setCompetitionId((long) competitionId);
//				data.setUserId((long) userId);
//				data.setOrderAction(entrustActionEnum.getAction());
//				data.setOrderQty((long) orderQty);
//				data.setOrderPrice(new BigDecimal(orderPrice.toString()));
//				data.setAssetId(assetId);
//				list.add(data);
//			}
//			return list;
//		} catch (IOException e) {
//			String tips = "解析上传的模拟比赛交易委托excel出现异常";
//			log.error(tips, e);
//			return R.fail(tips);
//		}
//	}
//
//	private R addEntrustOrder(List<CompetitionEntrustOrderVO> list) {
//		try {
//			for (CompetitionEntrustOrderVO order : list) {
//				//判断比赛是否已结束
//				SimulateCompetitionEntity competition = simulateTradeMapper.getSimulateCompetition(order.getCompetitionId());
//				if (competition == null) {
//					String tips = String.format("获取不到模拟比赛信息，CompetitionId:%s", order.getCompetitionId());
//					log.error(tips);
//					return R.fail(tips);
//				}
//				if (competition.getEndTime().getTime() < System.currentTimeMillis()) {
//					String tips = String.format("模拟比赛已结束，不能提交委托，CompetitionId:%s", order.getCompetitionId());
//					log.error(tips);
//					return R.fail(tips);
//				}
//			}
//			for (CompetitionEntrustOrderVO order : list) {
//				Long simulateAccountId = simulateTradeMapper.getSimulateAccoundId(order.getCompetitionId(), order.getUserId());
//				if (simulateAccountId == null) {
//					String tips = String.format("此用户未报名参加此比赛。 模拟比赛ID:%s，用户ID:%s", order.getCompetitionId(), order.getUserId());
//					log.error(tips);
//					return R.fail(tips);
//				}
//				//生成交易委托请求参数：
//				long time = System.currentTimeMillis();
//				String key = simulateAccountId.toString() + time;
//				String sign = AESUtil.encrypt(key, simulateTradeApiSecret);
//				EntrustOrderVo entrustOrder = new EntrustOrderVo();
//				entrustOrder.setTimestamp(time);
//				entrustOrder.setSign(sign);
//				entrustOrder.setUserId(order.getUserId());
//				entrustOrder.setSimuAccountId(simulateAccountId);
//				entrustOrder.setAssetId(order.getAssetId());
//				entrustOrder.setOrderQty(order.getOrderQty());
//				entrustOrder.setOrderPrice(order.getOrderPrice().doubleValue());
//				entrustOrder.setOrderAction(order.getOrderAction());
//				String entrustOrderJson = JSON.toJSONString(entrustOrder);
//				String jsonResult = HttpClientUtils.postJson(addEntrustOrderUrl, entrustOrderJson, StandardCharsets.UTF_8, true);
//				if (StringUtils.isBlank(jsonResult)) {
//					return R.fail("调模拟交易服务去新增委托时返回空值");
//				}
//				R result = JSON.parseObject(jsonResult, R.class);
//				if (result == null) {
//					return R.fail("调模拟交易服务去新增委托时的返回值转为对象时出现异常");
//				}
//				if (result.getCode() != ResultCode.SUCCESS.getCode()) {
//					String tips = "调模拟交易服务去新增委托时，模拟交易接口返回异常消息：" + result.getMsg();
//					log.error(tips);
//					log.error("请求参数：{}", entrustOrderJson);
//					log.error("excel orderList：{}", JSON.toJSONString(list));
//					return R.fail("调模拟交易服务去新增委托时，模拟交易接口返回异常消息：" + result.getMsg());
//				}
//				log.info("交易委托提交成功，entrustOrder：{}", entrustOrderJson);
//			}
//			return R.success("提交模拟比赛交易委托成功");
//		} catch (Exception e) {
//			log.error("提交模拟比赛交易委托出现异常：", e);
//			return R.fail("提交模拟比赛交易委托出现异常：" + e.getMessage());
//		}
//	}
//
//	@Override
//	public R saveEntrustOrder(CompetitionEntrustOrderVO order) {
//		List<CompetitionEntrustOrderVO> list = Lists.newArrayList();
//		list.add(order);
//		return addEntrustOrder(list);
//	}
//
//	@Override
//	public R uploadSimulateAccountFilter(MultipartFile file, Long competitionId, Integer type) {
//		try {
//			SimulateCompetitionEntity competition = simulateTradeMapper.getSimulateCompetition(competitionId);
//			if (competition == null) {
//				return R.fail("获取不到模拟比赛信息，competitionId:" + competitionId);
//			}
//			String filename = file.getOriginalFilename();
//			if (!filename.endsWith(".xlsx")) {
//				return R.fail("模拟比赛黑/白名单excel文件上传格式错误，请重新上传");
//			}
//			Object listObj = readSimulateAccountFilterXls(file);
//			if (listObj instanceof R) {
//				return (R) listObj;
//			}
//			List<Long> userIds = (List<Long>) listObj;
//			simulateTradeMapper.deleteSimulateCompetitionFilterById(competitionId);
//			boolean flag = Boolean.FALSE;
//			if (CollectionUtils.isEmpty(userIds)) {
//				flag = Boolean.TRUE;
//			} else {
//				List<SimulateCompetitionFilterEntity> simulateCompetitionFilterEntities = new ArrayList<>();
//				for (Long userId : userIds) {
//					SimulateCompetitionFilterEntity simulateCompetitionFilterEntity = new SimulateCompetitionFilterEntity();
//					simulateCompetitionFilterEntity.setId(competitionId);
//					simulateCompetitionFilterEntity.setUserId(userId);
//					simulateCompetitionFilterEntity.setCreateTime(new Date());
//					simulateCompetitionFilterEntity.setUpdateTime(new Date());
//					simulateCompetitionFilterEntities.add(simulateCompetitionFilterEntity);
//				}
//				flag = simulateTradeMapper.saveSimulateCompetitionFilter(simulateCompetitionFilterEntities);
//			}
//			if (!flag) {
//				String typeName = type == CompetitionFilterTypeEnum.WHITE_LIST.getType() ? "白名单" : "黑名单";
//				return R.success("保存模拟比赛" + typeName + "失败");
//			}
//			if (competition.getFilterType().intValue() != type) {
//				//名单类型有变更
//				if (CollectionUtils.isEmpty(userIds)) {
//					//名单为空
//					int state = type == CompetitionFilterTypeEnum.WHITE_LIST.getType() ? SimulateAccountStateEnum.disable.getState() : SimulateAccountStateEnum.enable.getState();
//					simulateTradeMapper.updateSimulateAccountState(competitionId, state);
//				} else if (type == CompetitionFilterTypeEnum.WHITE_LIST.getType()) {
//					//白名单
//					simulateTradeMapper.updateSimulateAccountStateInUserIds(competitionId, userIds, SimulateAccountStateEnum.enable.getState());
//					simulateTradeMapper.updateSimulateAccountStateNotInUserIds(competitionId, userIds, SimulateAccountStateEnum.disable.getState());
//				} else {
//					//黑名单
//					simulateTradeMapper.updateSimulateAccountStateInUserIds(competitionId, userIds, SimulateAccountStateEnum.disable.getState());
//					simulateTradeMapper.updateSimulateAccountStateNotInUserIds(competitionId, userIds, SimulateAccountStateEnum.enable.getState());
//				}
////				if (!flag) {
////					String ids = userIds.stream().map(Object::toString).collect(Collectors.joining(","));
////					return R.fail("修改比赛帐号状态失败，competitionId:" + competitionId + "  type:" + type + "  userIds:" + ids);
////				}
//				competition.setFilterType(type);
//				flag = simulateTradeMapper.updateSimulateCompetition(competition);
//			}
//			if (flag) {
//				return R.success("上传成功");
//			}
//			log.error("保存模拟比赛黑/白名单出现异常，competitionId:{}, type:{}, list:{}", competitionId, type, JSON.toJSONString(userIds));
//			return R.fail("保存名单信息失败");
//		} catch (Exception e) {
//			log.error("上传模拟比赛交易委托excel出现异常", e);
//			return R.fail("上传模拟比赛交易委托excel出现异常，请联系管理员");
//		}
//	}
//
//	public Object readSimulateAccountFilterXls(MultipartFile file) {
//		try {
//			List<Long> list = new ArrayList<>();
//			InputStream inputStream = file.getInputStream();
//			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//			//读取第一张sheet
//			int rowMin = 1;
//			XSSFSheet sheet = workbook.getSheetAt(0);
//			if (sheet.getLastRowNum() < rowMin) {
//				return Collections.emptyList();
//			}
//			int userIdIndex = 0; //用户ID位置
//			//遍历每一行Excel获取内容
//			for (int rowNum = rowMin; rowNum <= sheet.getLastRowNum(); rowNum++) {
//				XSSFRow row = sheet.getRow(rowNum);
//				if (row == null) {
//					continue;
//				}
//				Object userId = getlong(row, userIdIndex, 0, "上传的模拟比赛白名单excel中的“用户ID”不是数字");
//				if (userId instanceof R) {
//					return userId;
//				}
//				if (userId == null) {
//					continue;
//				}
//				list.add(Double.valueOf(userId.toString()).longValue());
//			}
//			return list;
//		} catch (IOException e) {
//			String tips = "解析上传的模拟比赛白名单excel出现异常";
//			log.error(tips, e);
//			return R.fail(tips);
//		}
//	}
//
//	public Object readSimulateAccountFilterUpload(MultipartFile file) {
//		try {
//			List<Long> list = new ArrayList<>();
//			InputStream inputStream = file.getInputStream();
//			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//			//读取第一张sheet
//			int rowMin = 1;
//			XSSFSheet sheet = workbook.getSheetAt(0);
//			if (sheet.getLastRowNum() < rowMin) {
//				return Collections.emptyList();
//			}
//			int userIdIndex = 0; //用户ID位置
//			//遍历每一行Excel获取内容
//			for (int rowNum = rowMin; rowNum <= sheet.getLastRowNum(); rowNum++) {
//				XSSFRow row = sheet.getRow(rowNum);
//				if (row == null) {
//					continue;
//				}
//				Object userId = getlong(row, userIdIndex, 0, "上传的模拟比赛黑名单excel中的“用户ID”不是数字");
//				if (userId instanceof R) {
//					return userId;
//				}
//				if (userId == null) {
//					continue;
//				}
//				list.add(Double.valueOf(userId.toString()).longValue());
//			}
//			return list;
//		} catch (IOException e) {
//			String tips = "解析上传的模拟比赛黑名单excel出现异常";
//			log.error(tips, e);
//			return R.fail(tips);
//		}
//	}
//
//
//	/**
//	 * 模拟比赛黑白名单列表
//	 */
//	@Override
//	public IPage<SimulateAccountFilterVO> listSimulateAccountFilter(IPage<SimulateAccountFilterVO> page, SimulateCompetitionSearchVO param) {
//		List<SimulateAccountFilterVO> list = null;
//		try {
//			list = simulateTradeMapper.listSimulateAccountFilter(page, param);
//			if (CollectionUtils.isNotEmpty(list)) {
//				List<Long> userIdList = list.stream().map(SimulateAccountFilterVO::getUserId).distinct().collect(Collectors.toList());
//				Map<Long, CustomerInfoEntity> customerInfoMap = getCustomerInfoMap(userIdList);
//				for (SimulateAccountFilterVO data : list) {
//					CustomerInfoEntity customerInfoEntity = customerInfoMap.get(data.getUserId());
//					data.setNickName(customerInfoEntity != null ? customerInfoEntity.getNickName() : "N/A");
//				}
//			}
//		} catch (Exception e) {
//			log.error("模拟比赛黑白名单列表异常", e);
//		}
//		return page.setRecords(list);
//	}
//
//	@Override
//	public R competitionEdit(Long id) {
//		String config = simulateTradeMapper.getSysConfigKeyVal("account", id + "_init");
//		JSONObject initConfig = JSON.parseObject(config);
//		SimulateCompetitionEntity competition = simulateTradeMapper.getSimulateCompetition(id);
//		long date = System.currentTimeMillis();
//		long hkAmount = initConfig.getLong("hk_amount") / 10000; //管理页面输入框的单位：万
//		Map<String, Object> map = new HashMap<>();
//		competition.setInitAmount(hkAmount);
//		competition.setTradeCount(initConfig.getInteger("hk_trade_count"));
//		competition.setEditable(competition.getRegisterStartTime().getTime() < date);
//		competition.setTradeCountEditable(competition.getStartTime().getTime() < date);
//		map.put("info", competition);
//		return R.data(map);
//	}
//
//	@Override
//	public IPage<SimulateAccountFilterEntity> simulateAccountFilterList(IPage<SimulateAccountFilterEntity> page, SimulateCompetitionSearchVO param) {
//		List<SimulateAccountFilterEntity> list = null;
//		try {
//			Map<Long, CustomerInfoEntity> customerInfoMap = null;
//			String searchCondition = param.getSearchCondition();
//			if (StringUtils.isNotBlank(searchCondition)){
//				customerInfoMap = getCustomerInfoMap(searchCondition);
//				if (customerInfoMap == null || customerInfoMap.isEmpty()){
//					return page.setRecords(Collections.EMPTY_LIST);
//				}
//				List<Long> userIds = new ArrayList<>(customerInfoMap.keySet());
//				param.setUserIds(userIds);
//			}
//
//			list = simulateTradeMapper.simulateAccountFilterList(page, param);
//			if (CollectionUtils.isNotEmpty(list)) {
//				if (customerInfoMap == null || customerInfoMap.isEmpty()) {
//					List<Long> userIdList = list.stream().map(SimulateAccountFilterEntity::getUserId).distinct().collect(Collectors.toList());
//					customerInfoMap = getCustomerInfoMap(userIdList);
//				}
//				for (SimulateAccountFilterEntity data : list) {
//					CustomerInfoEntity customerInfoEntity = customerInfoMap.get(data.getUserId());
//					data.setNickName(customerInfoEntity != null ? customerInfoEntity.getNickName() : "N/A");
//					data.setPhoneNumber(getPhone(data.getUserId(), data.getPhoneNumber(), customerInfoMap));
//
//				}
//			}
//		} catch (Exception e) {
//			log.error("查询黑名单列表异常", e);
//		}
//		return page.setRecords(list);
//	}
//
//	@Override
//	public List<simulateAccountFilterExcel> simulateAccountFilterExport(List<Long> ids) {
//		try {
//			List<simulateAccountFilterExcel> dataList = Lists.newLinkedList();
//			List<SimulateAccountFilterEntity> list = simulateTradeMapper.simulateAccountFilterListByIds(ids);
//			if (CollectionUtils.isNotEmpty(list)) {
//				List<Long> userIdList = list.stream().map(SimulateAccountFilterEntity::getUserId).distinct().collect(Collectors.toList());
//				Map<Long, CustomerInfoEntity> customerInfoMap = getCustomerInfoMap(userIdList);
//				for (SimulateAccountFilterEntity data : list) {
//					simulateAccountFilterExcel row = new simulateAccountFilterExcel();
//					row.setUserId(data.getUserId().toString());
//					row.setCreateTime(DateUtils.dateToString(data.getCreateTime(), DateUtils.TimeFormatter.YYYY_MM_DD_HH_MM_SS));
//					row.setStatus(data.getStatus() == 1 ? "有效" : "无效");
//					CustomerInfoEntity customerInfoEntity = customerInfoMap.get(data.getUserId());
//					row.setNickName(customerInfoEntity != null ? customerInfoEntity.getNickName() : "N/A");
//					row.setPhoneNumber(getPhone(data.getUserId(), data.getPhoneNumber(), customerInfoMap));
//					dataList.add(row);
//				}
//			}
//			return dataList;
//		} catch (Exception e) {
//			log.error("炒股大赛黑名单出现异常：", e);
//			return null;
//		}
//	}
//
//	@Override
//	public R deleteSimulateAccountFilter(List<Long> ids) {
//		simulateTradeMapper.deleteSimulateAccountFilter(ids);
//		return R.success();
//	}
//
//	@Override
//	public R simulateAccountFilterUpload(MultipartFile file) {
//		try {
//			String filename = file.getOriginalFilename();
//			if (!filename.endsWith(".xlsx")) {
//				return R.fail("黑名单导入模板上传格式错误，请重新上传");
//			}
//			Object listObj = readSimulateAccountFilterUpload(file);
//			if (listObj instanceof R) {
//				return (R) listObj;
//			}
//			Date date = new Date();
//			List<Long> userIds = (List<Long>) listObj;
//			List<SimulateAccountFilterEntity> simulateAccountFilterEntities = simulateTradeMapper.simulateAccountFilterListByUserIds(userIds);
//			List<Long> invalidUserIdList = simulateAccountFilterEntities.stream().filter(e -> userIds.contains(e.getUserId()) && e.getStatus() == 0).map(SimulateAccountFilterEntity::getUserId).collect(Collectors.toList());
//			if (CollectionUtils.isNotEmpty(invalidUserIdList)) {
//				//无效的改有效的
//				simulateTradeMapper.updateSimulateAccountFilterValid(invalidUserIdList);
//			}
//			//有效用户
//			List<Long> validUserIdList = simulateAccountFilterEntities.stream().filter(e -> userIds.contains(e.getUserId()) && e.getStatus() == 1).map(SimulateAccountFilterEntity::getUserId).collect(Collectors.toList());
//			//过滤有效用户，新增黑名单
//			userIds.removeAll(invalidUserIdList);
//			userIds.removeAll(validUserIdList);
//			if (CollectionUtils.isNotEmpty(userIds)) {
//				List<SimulateAccountFilterEntity> list = userIds.stream()
//					.map(userId -> {
//						SimulateAccountFilterEntity entity = new SimulateAccountFilterEntity();
//						entity.setUserId(userId);
//						entity.setCreateTime(date);
//						entity.setUpdateTime(date);
//						entity.setStatus(1);
//						return entity;
//					})
//					.collect(Collectors.toList());
//				simulateTradeMapper.saveSimulateAccountFilter(list);
//			}
//			return R.success("上传成功");
//		} catch (Exception e) {
//			log.error("上传模拟比赛黑名单出现异常", e);
//		}
//		return R.fail("上传模拟比赛黑名单出现异常，请联系管理员");
//	}
//}
