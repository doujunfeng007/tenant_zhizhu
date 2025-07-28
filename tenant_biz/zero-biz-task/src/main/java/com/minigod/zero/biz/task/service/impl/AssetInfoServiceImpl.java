//package com.minigod.zero.biz.task.service.impl;
//
//import com.alibaba.fastjson2.JSONObject;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.minigod.zero.biz.common.constant.OpenApiConstant;
//import com.minigod.zero.biz.common.utils.CommonUtil;
//import com.minigod.zero.biz.common.utils.GlobalExecutorService;
//import com.minigod.zero.biz.common.utils.RestTemplateUtil;
//import com.minigod.zero.biz.task.mapper.AssetInfoMapper;
//import com.minigod.zero.biz.task.service.IAssetInfoService;
//import com.minigod.zero.biz.task.vo.FundInfoDataVO;
//import com.minigod.zero.core.mp.base.BaseServiceImpl;
//import com.minigod.zero.core.redis.cache.Pair;
//import com.minigod.zero.core.redis.cache.ZeroRedis;
//import com.minigod.zero.core.tool.beans.StkInfo;
//import com.minigod.zero.core.tool.exception.ZeroException;
//import com.minigod.zero.core.tool.utils.BeanUtil;
//import com.minigod.zero.core.tool.utils.DateUtil;
//import com.minigod.zero.mkt.entity.AssetInfoEntity;
//import com.minigod.zero.search.entity.AssetInfoDocument;
//import com.minigod.zero.search.feign.IAssetInfoDocumentClient;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.StringUtils;
//import org.jsoup.Jsoup;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service
//public class AssetInfoServiceImpl extends BaseServiceImpl<AssetInfoMapper, AssetInfoEntity> implements IAssetInfoService {
//	private static final String accountId = "DF2";
//	private static final String subacctId = "DF2-001";
//	private static final String rankingkey = "youyuscore";
//	private static final String defaultrankingkey = "youyuscore";
//	private static final String FUND_THIRD_URL = "http://uatcfund.ifund.mobi:81/fundapi/v1/wm/securities/ranking";
//	private static final String FUND_THIRD_URL_PARAM = "?accountid={%s}&subacctid={%s}&lang=zh-hans&start={%s}&count={%s}&sortdirection=0&rankingkey={%s}&defaultrankingkey={%s}";
//	@Resource
//	private AssetInfoMapper assetInfoMapper;
//	@Resource
//	private ZeroRedis zeroRedis;
//	@Resource
//	private IAssetInfoDocumentClient assetInfoDocumentClient;
//
//	@Resource
//	private RestTemplateUtil restTemplateUtil;
//
//	@Override
//	public void loadStkInfoToRedis() {
//		String[] markets = new String[]{"HK", "US", "SH", "SZ", "FUND"};
//		for (String market : markets) {
//			List<AssetInfoEntity> entities = assetInfoMapper.findAssetInfoList(market);
//			if (CollectionUtils.isEmpty(entities)) {
//				continue;
//			}
//			List<Pair<String>> pairs = new ArrayList<>();
//			for (AssetInfoEntity assetInfo : entities) {
//				StkInfo stkInfo = convertToStkInfo(assetInfo);
//				pairs.add(new Pair<>(stkInfo.getAssetId(), stkInfo));
//			}
//			try {
//				if (null != pairs && pairs.size() > 0) {
//					zeroRedis.protoBatchSet(StkInfo.class, pairs);
//				}
//				log.info("-->市场:{},加载码表到Redis完成,共{}条!", market, pairs.size());
//			} catch (Exception e) {
//				log.error("-->市场:{},加载码表Redis异常:{}", market, e);
//			}
//		}
//	}
//
//	@Override
//	public void loadAssetInfoToEs() {
//		String[] markets = new String[]{"HK", "US", "SH", "SZ", "FUND"};
//		assetInfoDocumentClient.delAll();
//		for (String market : markets) {
//			List<AssetInfoEntity> entities = assetInfoMapper.findAssetInfoList(market);
//			log.info("-->市场:{},加载码表到ES开始,共{}条, AssetInfoEntity数据包:{}!", market, entities.size(), JSONObject.toJSONString(entities));
//			if (CollectionUtils.isEmpty(entities)) {
//				continue;
//			}
//			List<AssetInfoDocument> docs = BeanUtil.copy(entities, AssetInfoDocument.class);
//			try {
//				List<List<AssetInfoDocument>> list = CommonUtil.splitList(docs, 2000);
//				for (List<AssetInfoDocument> arr : list) {
//					assetInfoDocumentClient.batchLoad(arr);
//				}
//				log.info("-->市场:{},加载码表到ES完成,共{}条, AssetInfoDocument数据包:{}!", market, list.size(), JSONObject.toJSONString(list));
//			} catch (Exception e) {
//				log.error("-->写入[" + market + "]市场数据至ES异常:", e);
//			}
//		}
//	}
//
//	@Override
//	public void loadStkInfoToDB() {
//		int maxVersion = assetInfoMapper.findMaxVersion();
//		try {
//			Map<String, Object> params = new HashMap<>();
//			params.put("version", maxVersion);
//			List<AssetInfoEntity> list = restTemplateUtil.postSends(OpenApiConstant.SYNC_ASSET_INFO, AssetInfoEntity.class, params);
//			if (null != list && list.size() > 0) {
//				for (AssetInfoEntity entity : list) {
//					AssetInfoEntity old = assetInfoMapper.selectOne(new LambdaQueryWrapper<AssetInfoEntity>().eq(AssetInfoEntity::getAssetId, entity.getAssetId()));
//					if (null != old) {
//						assetInfoMapper.delObj(old.getId());
//					}
//					entity.setId(null);
//					assetInfoMapper.insert(entity);
//				}
//				log.info("-->增量同步码表到DB完成,共{}条!", list.size());
//			}
//		} catch (Exception e) {
//			log.error("-->增量同步码表异常:", e);
//		}
//	}
//
//	/**
//	 * 从第三方获取基金信息并插入到码表
//	 */
//	@Override
//	public void syncFundInfoData() {
//		int page = 0;
//		int count = 100;
//		boolean hasData = true;
//
//		while (hasData) {
//			try {
//				FundInfoDataVO fundInfoDataVO = parseJson(sendUrl(getUrl(page * count, count)));
//				if (null == fundInfoDataVO || null == fundInfoDataVO.getData() || CollectionUtils.isEmpty(fundInfoDataVO.getData().getPages())) {
//					hasData = false;
//					log.warn("-->获取到数据为空,结束循环分页取数据操作!");
//				} else {
//					logInfo(fundInfoDataVO.getData());
//					GlobalExecutorService.getExecutor().execute(() -> {
//						log.info("-->开始异步持久化第三方获取基金信息数据到Asset_Info-DB, 当前开始记录数:{},当前数据条数:{},总共条数:{}", fundInfoDataVO.getData().getStart(), fundInfoDataVO.getData().getCount(), fundInfoDataVO.getData().getTotal());
//						List<AssetInfoEntity> assetInfoEntities = wrapAssetInfoEntity(fundInfoDataVO.getData().getPages());
//						List<String> assetIds = assetInfoEntities.stream().map(AssetInfoEntity::getAssetId).collect(Collectors.toList());
//						Long counts = assetInfoMapper.batchDeleteByAssetId(assetIds);
//						if (counts > 0) {
//							log.info("-->异步持久化第三方获取基金信息数据到Asset_Info-DB,先删除已经存在的【{}】条数据,基金ProductID:{}", counts, JSONObject.toJSONString(assetIds));
//						} else {
//							log.info("-->异步持久化第三方获取基金信息数据到Asset_Info-DB,没有需要删除的数据,基金ProductID:{}", JSONObject.toJSONString(assetIds));
//						}
//						boolean result = this.saveBatch(assetInfoEntities);
//						if (result) {
//							log.info("-->异步持久化第三方获取基金信息数据到Asset_Info-DB,成功插入【{}】条数据,基金ProductID:{}", assetInfoEntities.size(), JSONObject.toJSONString(assetIds));
//						} else {
//							log.error("-->异步持久化第三方获取基金信息数据到Asset_Info-DB,插入【{}】条数据失败,基金ProductID:{}", assetInfoEntities.size(), JSONObject.toJSONString(assetIds));
//						}
//					});
//				}
//				page++;
//			} catch (Exception e) {
//				logError("从第三方获取基金信息并插入到Asset_Info-DB异常", e);
//			}
//		}
//	}
//
//	private void logInfo(FundInfoDataVO.FundInfoData data) {
//		log.info("-->获取到数据,准备同步到DB, 当前开始记录数:{},当前数据条数:{},总共条数:{}", data.getStart(), data.getCount(), data.getTotal());
//	}
//
//	private void logResult(boolean result) {
//		if (result) {
//			log.info("-->异步持久化第三方获取基金信息数据到DB成功!");
//		} else {
//			log.error("-->异步持久化第三方获取基金信息数据到DB失败!");
//		}
//	}
//
//	private void logError(String message, Exception e) {
//		log.error(message + ": " + e.getMessage(), e);
//	}
//
//	/**
//	 * 组装分页查询的URL
//	 *
//	 * @param page
//	 * @param count
//	 * @return
//	 */
//	private static String getUrl(int page, int count) {
//		String tempBaseUrl = FUND_THIRD_URL + FUND_THIRD_URL_PARAM;
//		tempBaseUrl = tempBaseUrl.replaceFirst("\\{%s\\}", accountId);
//		tempBaseUrl = tempBaseUrl.replaceFirst("\\{%s\\}", subacctId);
//		tempBaseUrl = tempBaseUrl.replaceFirst("\\{%s\\}", String.valueOf(page));
//		tempBaseUrl = tempBaseUrl.replaceFirst("\\{%s\\}", String.valueOf(count));
//		tempBaseUrl = tempBaseUrl.replaceFirst("\\{%s\\}", rankingkey);
//		tempBaseUrl = tempBaseUrl.replaceFirst("\\{%s\\}", defaultrankingkey);
//		return tempBaseUrl;
//	}
//
//	/**
//	 * 请求第三方数据
//	 *
//	 * @param url
//	 * @return
//	 */
//	private static String sendUrl(String url) {
//		String json;
//		try {
//			json = Jsoup.connect(url)
//				.userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36")
//				.header("X-Requested-With", "XMLHttpRequest")
//				.header("X-Channel", "100")
//				.ignoreContentType(true)
//				.timeout(60000)
//				.execute().body();
//		} catch (IOException e) {
//			log.error("-->请求第三方数据接口异常:", e);
//			throw new ZeroException("-->请求第三方数据接口异常:" + e.getMessage());
//		}
//		return json;
//	}
//
//	/**
//	 * 解析json
//	 *
//	 * @param json
//	 * @return
//	 */
//	private static FundInfoDataVO parseJson(String json) {
//		FundInfoDataVO fundInfoDataVO = new FundInfoDataVO();
//		try {
//			if (StringUtils.isNotBlank(json)) {
//				fundInfoDataVO = JSONObject.parseObject(json, FundInfoDataVO.class);
//			}
//		} catch (Exception e) {
//			throw new ZeroException("-->基金数据同步第三方平台数据,从JSon中解析异常:", e);
//		}
//		return fundInfoDataVO;
//	}
//
//	private static List<AssetInfoEntity> wrapAssetInfoEntity(List<FundInfoDataVO.FundInfoPage> list) {
//		List<AssetInfoEntity> dataList = new ArrayList();
//		if (null != list && list.size() > 0) {
//			for (FundInfoDataVO.FundInfoPage data : list) {
//				AssetInfoEntity entity = new AssetInfoEntity();
//				entity.setAssetId(data.getProductId().toString());
//				entity.setEngName(data.getEnglishname());
//				entity.setMktCode("FUND");
//				entity.setStkCode(data.getProductCode());
//				entity.setSecType(Integer.parseInt(data.getAssetClassId().toString()));
//				entity.setSecStype(Integer.parseInt(data.getSubAssetClassId().toString()));
//				entity.setStkName(data.getName());
//				entity.setStkNameLong(data.getReoChineseName());
//				entity.setTraditionalLong(data.getReoChineseName());
//				entity.setTraditionalName(data.getReoChineseName());
//				entity.setSpelling(data.getEnglishname());
//				entity.setSpellingAbbr(data.getEnglishname());
//				entity.setCcyType(data.getCurrency());
//				entity.setVersion(10001);
//				entity.setAddVersion(10001);
//				entity.setIsInvest(true);
//				entity.setIsStatus(true);
//				entity.setIsConfirm(1);
//				entity.setExtTime(new Date());
//				entity.setIsRevise(1);
//				entity.setCreateTime(new Date());
//				entity.setIsSimuInvest(1);
//				dataList.add(entity);
//			}
//		}
//		return dataList;
//	}
//
//
//	public StkInfo convertToStkInfo(AssetInfoEntity assetInfo) {
//		StkInfo stkinfo = new StkInfo();
//		stkinfo.setAssetId(assetInfo.getAssetId());
//		stkinfo.setStkCode(assetInfo.getStkCode());
//		stkinfo.setMarket(assetInfo.getMktCode());
//		stkinfo.setIsStatus("1".equals(assetInfo.getStatus()) ? true : false);
//		if (null != assetInfo.getCorpId()) {
//			stkinfo.setCorpCode(assetInfo.getCorpId());
//		}
//		stkinfo.setSpelling(assetInfo.getSpelling());
//		stkinfo.setSpellingAbbr(assetInfo.getSpellingAbbr());
//		stkinfo.setStkName(assetInfo.getStkName());
//		stkinfo.setStkNameLong(assetInfo.getStkNameLong());
//		stkinfo.setEngName(assetInfo.getEngName());
//		stkinfo.setSecType(assetInfo.getSecType());
//		stkinfo.setSecSType(assetInfo.getSecStype());
//		if (null != assetInfo.getBoardCode()) {
//			stkinfo.setBoardCode(assetInfo.getBoardCode());
//		}
//		if (null != assetInfo.getLotSize()) {
//			stkinfo.setLotSize(assetInfo.getLotSize());
//		}
//		stkinfo.setListingDate(null == assetInfo.getListingDate() ? null : com.minigod.zero.core.tool.utils.DateUtil.format(assetInfo.getListingDate(), com.minigod.zero.core.tool.utils.DateUtil.PATTERN_DATE));
//		stkinfo.setVersion(assetInfo.getVersion());
//		stkinfo.setDelistDate(null == assetInfo.getDelistDate() ? null : com.minigod.zero.core.tool.utils.DateUtil.format(assetInfo.getDelistDate(), DateUtil.PATTERN_DATE));
//		stkinfo.setCcyType(assetInfo.getCcyType());
//		stkinfo.setExchange(assetInfo.getExchange());
//		stkinfo.setSpreadTableCode(assetInfo.getSpreadTableCode());
//		stkinfo.setAddVersion(assetInfo.getAddVersion());
//		stkinfo.setTraditionalName(assetInfo.getTraditionalName());
//		stkinfo.setTraditionalNameLong(assetInfo.getTraditionalLong());
//		stkinfo.setSpellingShort(assetInfo.getSpellingShort());
//		return stkinfo;
//	}
//}
