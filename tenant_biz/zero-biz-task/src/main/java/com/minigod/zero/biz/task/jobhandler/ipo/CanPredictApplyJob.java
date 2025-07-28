package com.minigod.zero.biz.task.jobhandler.ipo;

import cn.hutool.http.HttpUtil;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.biz.common.mkt.cache.ApplyCanVO;
import com.minigod.zero.biz.common.mkt.cache.ApplyCanVOCache;
import com.minigod.zero.biz.common.utils.DateUtil;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.biz.task.vo.CompanyProfileInfoResp;
import com.minigod.zero.biz.task.vo.MakeNewResp;
import com.minigod.zero.biz.task.vo.RjhRespVO;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.trade.feign.IIpoClient;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 可认购列表定时刷新到redis
 */
@Slf4j
@Component
public class CanPredictApplyJob {

    @Resource
    private ZeroRedis zeroRedis;
    @Resource
    private IIpoClient iIpoClient;

    @Value("${ipo.base_url}")
    private String baseUrl;

	private static final double ipo_commision = 0.01; // 佣金
	private static final double ipo_levy = 0.00005; // 联交所交易征费
	private static final double ipo_tranlevy = 0.000027; // 证监会交易征费

    /**
     * 可认购列表
     */
    private String CAN_APPLY = "/makeNew/makeNewList";
    private String COMPANY_INFO = "/offeringDetails/v1/getCompanyProfile";
    private String SHIELD_STRING = "特殊目的收购公司 [特殊目的收购公司]";
    private String COMPANYINFO_BYCODE = "/offeringDetails/getCompanyInfoByCode";

    @XxlJob("CanPredictApplyJob")
    public void execute() {
        try {
            log.info(">>> CanPredictApplyJob start");
            getPredictApplyCanVoList();
            XxlJobHelper.handleSuccess();
        } catch (Exception e) {
            log.error("CanPredictApplyJob error", e);
            XxlJobHelper.handleFail();
        }
    }

    private void getPredictApplyCanVoList() {

        ApplyCanVOCache cache = new ApplyCanVOCache();
        Map<String, Object> reqParams = new HashMap<>();
        RjhRespVO respVO = doBiz(reqParams, CAN_APPLY);
        if (null != respVO && respVO.isSuccess()) {
            List<MakeNewResp> list = JSONUtil.getList(respVO.getBody().toString(), MakeNewResp.class);
//            list = dealCanApplyData(list);
            List<ApplyCanVO> canApplyList = new ArrayList<>();
            for (MakeNewResp makeNewResp : list) {
                ApplyCanVO applyCanVO = new ApplyCanVO();

                applyCanVO.setShares(makeNewResp.getOneLotShare());
                applyCanVO.setLink(makeNewResp.getProspectus());
                applyCanVO.setAssetId(makeNewResp.getSymbol()); // 00700.hk 格式
                if (null == makeNewResp.getIssuePriceC()) {
                    applyCanVO.setPriceCeiling(makeNewResp.getIssuePrice());
                } else {
                    applyCanVO.setPriceCeiling(makeNewResp.getIssuePriceC().toString());
                }
				if (null == makeNewResp.getIssuePriceF()) {
					applyCanVO.setPriceFloor(makeNewResp.getIssuePrice());
				} else {
					applyCanVO.setPriceFloor(makeNewResp.getIssuePriceF().toString());
				}
				if(StringUtils.isNotBlank(makeNewResp.getListedDate())){
					Date listedDate = DateUtil.parseDate(makeNewResp.getListedDate(), "yyyyMMdd");
					applyCanVO.setListedDate(listedDate); // 上市日期
				}
				if(StringUtils.isNotBlank(makeNewResp.getPubResultDate())){
					applyCanVO.setResultDate(DateUtil.parseDate(makeNewResp.getPubResultDate(), "yyyyMMdd"));// 公告日期
				}
				if (makeNewResp.getAdmissionFee() != null) {
					applyCanVO.setEntranceMin(makeNewResp.getAdmissionFee().setScale(2, RoundingMode.HALF_UP).toString());// 最低入场费
				}
				// 最低入场金额
				if (StringUtils.isBlank(applyCanVO.getEntranceMin())) {
					// 计算最低入场金额
					Integer shares = makeNewResp.getOneLotShare(); // 每手股数
					double s = new BigDecimal(applyCanVO.getPriceFloor()).doubleValue() * shares;
					String entranceMin = String.valueOf(round(s * (ipo_commision + ipo_levy + ipo_tranlevy + 1)));
					applyCanVO.setEntranceMin(entranceMin);
				}
				applyCanVO.setStkName(makeNewResp.getStockName());
				applyCanVO.setSubscribed(makeNewResp.getSubMultiple().toString());
				applyCanVO.setEndDate(makeNewResp.getSubEndDate());
				canApplyList.add(applyCanVO);

            }

			log.info("getApplyCanVoList.applyCanVOS={}", JSON.toJSONString(canApplyList));
			if (CollectionUtils.isNotEmpty(canApplyList)) {
				Collections.sort(canApplyList, new TimeAscComparator()); // endDate时间升序排列
			}
			//写入redis缓存
			cache.setCanApplyList(canApplyList);
			zeroRedis.protoSet(ApplyCanVOCache.class.getSimpleName(), cache);
        }
    }

    private List<MakeNewResp> dealCanApplyData(List<MakeNewResp> list) {

        List<MakeNewResp> newList = new ArrayList<>();

        for (MakeNewResp makeNewResp : list) {
            Map<String, Object> reqParams = new HashMap<>();
            reqParams.put("code", makeNewResp.getSymbol());
            // 屏蔽新股类型为spac
            RjhRespVO companyInfoResult = doBiz(reqParams, COMPANY_INFO);
            if (null != companyInfoResult && companyInfoResult.isSuccess()) {
                CompanyProfileInfoResp companyInfo =
                    JSONUtil.fromJson(companyInfoResult.getBody().toString(), CompanyProfileInfoResp.class);
                CompanyProfileInfoResp.CompanyProfile companyProfile = companyInfo.getCompanyProfile();
                if (StringUtils.isNotEmpty(companyProfile.getIndustryName())
                    && companyProfile.getIndustryName().equals(SHIELD_STRING)) {
                    continue;
                }
            }
            makeNewResp.setSymbol(makeNewResp.getSymbol().toUpperCase());
            makeNewResp.setSysDate(DateUtils.format(new Date(), "yyyyMMdd"));
            // 最高招股价/最低招股价为空时，取配售结果定价
            if (makeNewResp.getIssuePriceC() == null || makeNewResp.getIssuePriceF() == null) {
                // 配售结果基本信息
                RjhRespVO result = doBiz(reqParams, COMPANYINFO_BYCODE);
                if (result != null) {
                    JSONObject jsonObject = JSONObject.parseObject(result.getBody().toString());
                    makeNewResp.setIssuePrice(jsonObject.get("issuePrice").toString());
                }
            }

            newList.add(makeNewResp);
        }
        return newList;
    }

    private RjhRespVO doBiz(Map<String, Object> reqParams, String url) {
        String result;
        url = baseUrl + url;
        try {
            log.info("请求荣聚汇ipo开始时间==" + System.currentTimeMillis());
            result = HttpUtil.post(url, JSON.toJSONString(reqParams), 20000);
//			result ="{\n" +
//				"    \"code\": \"200\",\n" +
//				"    \"msg\": \"OK\",\n" +
//				"    \"body\": [\n" +
//				"        {\n" +
//				"            \"stockName\": \"泰德医药\",\n" +
//				"            \"symbol\": \"03880.hk\",\n" +
//				"            \"subMultiple\": 27.003,\n" +
//				"            \"listedDate\": \"20250630\",\n" +
//				"            \"listedCountdown\": \"6.5天\",\n" +
//				"            \"issuePriceF\": 28.40000,\n" +
//				"            \"issuePriceC\": 30.60000,\n" +
//				"            \"admissionFee\": 3090.850,\n" +
//				"            \"pubResultDate\": \"20250627\",\n" +
//				"            \"industryName\": \"制药与生物科技 [生物科技- 制药]\",\n" +
//				"            \"industryCode\": \"050201\",\n" +
//				"            \"cornerstoneInvestor\": \"[Welight Capital L.P., 石药集团有限公司]\",\n" +
//				"            \"sponsor\": \"[中信证券(香港)有限公司, 摩根士丹利亚洲有限公司]\",\n" +
//				"            \"underwriter\": \"[雅利多证券有限公司, 中国光大证券(香港)有限公司, 中信里昂证券有限公司, 摩根士丹利亚洲有限公司, 发利证券有限公司, 东吴证券国际经纪有限公司]\",\n" +
//				"            \"subCountdown\": \"1.5天\",\n" +
//				"            \"subsHot\": \"3\",\n" +
//				"            \"subStartDate\": \"20250620\",\n" +
//				"            \"subEndDate\": \"20250625\",\n" +
//				"            \"prospectus\": null,\n" +
//				"            \"oneLotShare\": 100\n" +
//				"        },\n" +
//				"        {\n" +
//				"            \"stockName\": \"云知声\",\n" +
//				"            \"symbol\": \"09678.hk\",\n" +
//				"            \"subMultiple\": 12.637,\n" +
//				"            \"listedDate\": \"20250630\",\n" +
//				"            \"listedCountdown\": \"6.5天\",\n" +
//				"            \"issuePriceF\": 165.00000,\n" +
//				"            \"issuePriceC\": 205.00000,\n" +
//				"            \"admissionFee\": 4141.350,\n" +
//				"            \"pubResultDate\": \"20250627\",\n" +
//				"            \"industryName\": \"特专科技 [先进硬件及软件]\",\n" +
//				"            \"industryCode\": \"070402\",\n" +
//				"            \"cornerstoneInvestor\": \"[SensePower Management Limited, 润建国际（香港）有限公司, 臻一资产管理有限公司]\",\n" +
//				"            \"sponsor\": \"[中国国际金融香港证券有限公司, 海通国际资本有限公司]\",\n" +
//				"            \"underwriter\": \"[农银国际证券有限公司, 中国国际金融香港证券有限公司, 民银证券有限公司, 海通国际证券有限公司, 东方证券(香港)有限公司, 老虎证券（香港）环球有限公司]\",\n" +
//				"            \"subCountdown\": \"1.5天\",\n" +
//				"            \"subsHot\": \"2\",\n" +
//				"            \"subStartDate\": \"20250620\",\n" +
//				"            \"subEndDate\": \"20250625\",\n" +
//				"            \"prospectus\": null,\n" +
//				"            \"oneLotShare\": 20\n" +
//				"        },\n" +
//				"        {\n" +
//				"            \"stockName\": \"IFBH\",\n" +
//				"            \"symbol\": \"06603.hk\",\n" +
//				"            \"subMultiple\": 248.164,\n" +
//				"            \"listedDate\": \"20250630\",\n" +
//				"            \"listedCountdown\": \"6.5天\",\n" +
//				"            \"issuePriceF\": 25.30000,\n" +
//				"            \"issuePriceC\": 27.80000,\n" +
//				"            \"admissionFee\": 5616.070,\n" +
//				"            \"pubResultDate\": \"20250627\",\n" +
//				"            \"industryName\": \"食品与饮料 [饮品 (非酒精类)]\",\n" +
//				"            \"industryCode\": \"020205\",\n" +
//				"            \"cornerstoneInvestor\": \"[Black Dragon AP SPV1, Enreal China Master Fund and Forreal China Value Fund, Harvest Oriental SP, HCEP Master Fund and HCEP Long Only Master Fund HSG, Jain Global Master Fund, Jane Street Asia Trading Limited, Mega Prime Development Limited, UBS Asset Management (Singapore) Ltd., 南方基金管理股份有限公司, 工银理财有限责任公司, 广发国际资产管理有限公司]\",\n" +
//				"            \"sponsor\": \"[中信证券(香港)有限公司]\",\n" +
//				"            \"underwriter\": \"[中银国际亚洲有限公司, 中信里昂证券有限公司]\",\n" +
//				"            \"subCountdown\": \"1.5天\",\n" +
//				"            \"subsHot\": \"5\",\n" +
//				"            \"subStartDate\": \"20250620\",\n" +
//				"            \"subEndDate\": \"20250625\",\n" +
//				"            \"prospectus\": null,\n" +
//				"            \"oneLotShare\": 200\n" +
//				"        }\n" +
//				"    ]\n" +
//				"}";
            log.info("荣聚汇返回url==" + url + "返回==" + result);
            log.info("请求荣聚汇ipo结束时间==" + System.currentTimeMillis());
        } catch (Exception e) {
            log.info("请求荣聚汇ipo结束时间==" + System.currentTimeMillis());
            log.error("调用荣聚汇ipo查询异常", e);
            return null;
        }
        RjhRespVO respVO = JSONUtil.fromJson(result, RjhRespVO.class);

        return respVO;

    }

	/**
	 * 保留两位小数，四舍五入
	 */
	private Double round(Double d) {
		if (d != null) {
			return BigDecimal.valueOf(d).setScale(2, RoundingMode.HALF_UP).doubleValue();
		} else {
			return 0d;
		}
	}

	/**
	 * 截止时间升序排列
	 */
	private static class TimeAscComparator implements Comparator<ApplyCanVO> {
		@Override
		public int compare(ApplyCanVO o1, ApplyCanVO o2) {
			return o1.getEndDate().compareTo(o2.getEndDate());
		}
	}
}
