package com.minigod.zero.biz.task.jobhandler.ipo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.biz.common.cache.DeliverableTableVO;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.biz.task.vo.RjhRespVO;
import com.minigod.zero.biz.task.vo.TableDataResp;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chen
 * @ClassName DeliverableTableJob.java
 * @Description ipo 可递表数据存入redis  应用redis
 * @createTime 2024年05月07日 17:06:00
 */
@Slf4j
@Component
public class DeliverableTableJob {

    @Resource
    private ZeroRedis zeroRedis;

    private String APPLICATION_LISTING = "/makeNew/getTableDataByStatus";

    @Value("${ipo.base_url}")
    private String baseUrl;

    @XxlJob("DeliverableTableJob")
    public void execute() {
        try {
            log.info(">>> DeliverableTableJob start");
            DeliverableTable();
            XxlJobHelper.handleSuccess();
        } catch (Exception e) {
            log.error("DeliverableTableJob error", e);
            XxlJobHelper.handleFail();
        }
    }

    private void DeliverableTable() {
        Map<String, Object> reqParams = new HashMap<>();
        reqParams.put("pageNum", 1);
        reqParams.put("pageSize", 500);
        reqParams.put("status", 1);
        /**
         * status 0 正在处理 1 通过聆讯
         */
        DeliverableTableVO deliverableTableVO = new DeliverableTableVO();
        List<DeliverableTableVO.DeliverableTable> deliverableTableVOList = new ArrayList();
        String url = baseUrl + APPLICATION_LISTING;
        try {
//            String result = HttpUtil.post(url, JSON.toJSONString(reqParams), 20000);
			String result ="{\n" +
				"  \"code\": \"200\",\n" +
				"  \"msg\": \"OK\",\n" +
				"  \"body\": {\n" +
				"    \"total\": 6,\n" +
				"    \"list\": [\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"易达云\",\n" +
				"        \"status\": \"1\",\n" +
				"        \"applyDate\": \"20240227\",\n" +
				"        \"passHearingDate\": \"20240506\",\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"汽车街发展有限公司\",\n" +
				"        \"status\": \"1\",\n" +
				"        \"applyDate\": \"20240206\",\n" +
				"        \"passHearingDate\": \"20240418\",\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"盛禾生物\",\n" +
				"        \"status\": \"1\",\n" +
				"        \"applyDate\": \"20240209\",\n" +
				"        \"passHearingDate\": \"20240415\",\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"富景中国控股有限公司\",\n" +
				"        \"status\": \"1\",\n" +
				"        \"applyDate\": \"20240201\",\n" +
				"        \"passHearingDate\": \"20240318\",\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"北京圆心科技集团股份有限公司\",\n" +
				"        \"status\": \"1\",\n" +
				"        \"applyDate\": \"20231218\",\n" +
				"        \"passHearingDate\": \"20240209\",\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"深圳古瑞瓦特科技能源有限责任公司\",\n" +
				"        \"status\": \"1\",\n" +
				"        \"applyDate\": \"20230320\",\n" +
				"        \"passHearingDate\": \"20230514\",\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      }\n" +
				"    ],\n" +
				"    \"pageNum\": 1,\n" +
				"    \"pageSize\": 500,\n" +
				"    \"size\": 6,\n" +
				"    \"startRow\": 1,\n" +
				"    \"endRow\": 6,\n" +
				"    \"pages\": 1,\n" +
				"    \"prePage\": 0,\n" +
				"    \"nextPage\": 0,\n" +
				"    \"isFirstPage\": true,\n" +
				"    \"isLastPage\": true,\n" +
				"    \"hasPreviousPage\": false,\n" +
				"    \"hasNextPage\": false,\n" +
				"    \"navigatePages\": 8,\n" +
				"    \"navigatepageNums\": [\n" +
				"      1\n" +
				"    ],\n" +
				"    \"navigateFirstPage\": 1,\n" +
				"    \"navigateLastPage\": 1\n" +
				"  }\n" +
				"}";
            log.info("荣聚汇通过聆讯返回url==" + url + "返回==" + result);
            List<DeliverableTableVO.DeliverableTable> list1 = deliverableTableVOList = dealIpoData(result); // 通过聆讯

            reqParams.put("status", 0);
//            result = HttpUtil.post(url, JSON.toJSONString(reqParams), 20000);
			result="{\n" +
				"  \"code\": \"200\",\n" +
				"  \"msg\": \"OK\",\n" +
				"  \"body\": {\n" +
				"    \"total\": 137,\n" +
				"    \"list\": [\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"上海卓越睿新数码科技股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240503\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"觅瑞集团\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240430\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"多点数智有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240430\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"上海细胞治疗集团股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240430\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"优迅医学生物科技\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240430\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"上海富友支付服务股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240430\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"博雷顿科技股份公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240430\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"曹操出行有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240429\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"华芢生物科技（青岛）股份有限公司 - B\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240429\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"美的集团股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240429\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"江苏龙蟠科技股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240429\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"佰泽医疗集团\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240426\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"全品文教\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240422\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"宜搜科技控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240422\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"华润饮料（控股）有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240422\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"容大合众(厦门)科技集团股份公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240419\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"喜马拉雅控股 - W\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240412\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"毛戈平化妆品股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240408\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"众淼创新科技（青岛）股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240403\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"梦金园黄金珠宝集团股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240403\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"明基医院集团股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240403\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"傲基科技股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240402\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"拉拉科技控股有限公司 - W\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240402\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"纵目科技(上海)股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240328\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"宜宾市商业银行股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240328\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"深业物业运营集团股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240328\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"BrainAurora Medical Technology Limited - B\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240328\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"瑞昌国际控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240328\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"英矽智能\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240327\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"树兰医疗管理股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240327\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"中赣通信(集团)控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240327\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"元续科技控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240327\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"创业板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"地平线\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240326\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"西安经发物业股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240326\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"如祺出行\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240325\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"优博控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240325\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"创业板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"Black Sesame International Holding Limited - P\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240322\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"聚水潭集团股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240321\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"维升药业 - B\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240321\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"江苏国富氢能技术装备股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240320\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"嘀嗒出行\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240319\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"广联科技控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240318\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"七牛智能科技有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240318\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"云知声智能科技股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240315\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"深圳迅策科技股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240312\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"卡罗特有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240308\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"西锐飞机有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240306\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"武汉有机控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240304\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"趣致集团\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240304\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"百丽时尚集团\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240301\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"上海挚达科技发展股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240229\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"上海重塑能源集团股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240229\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"天聚地合(苏州)科技股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240228\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"方舟云康控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240228\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"中和农信有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240228\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"闪回科技有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240226\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"派格生物医药（苏州）股份有限公司 - B\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240223\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"沪上阿姨（上海）实业股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240214\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"百望股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240209\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"新世好有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240208\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"海南钧达新能源科技股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240206\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"厦门吉宏科技股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240206\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"广东晶科电子股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240201\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"KK Group Company Holdings Limited\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240131\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"陆道培医疗集团\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240131\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"佳鑫国际资源投资有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240130\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"啄木鸟维修国际有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240129\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"北京华昊中天生物医药股份有限公司 - B\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240129\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"浙江太美医疗科技股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240129\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"星阅控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240129\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"北京康乐卫士生物技术股份有限公司 - B\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240129\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"讯飞医疗科技股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240126\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"浙江同源康医药股份有限公司 - B\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240126\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"卡游有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240126\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"杭州九源基因工程股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240122\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"小菜园国际控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240116\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"手回科技有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240112\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"上海声通信息科技股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240112\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"蜜雪冰城股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240102\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"古茗控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20240102\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"白山云\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20231229\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"纽曼思健康食品控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20231229\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"健康之路股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20231229\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"安徽海螺材料科技股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20231228\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"爱维艾夫医院管理集团有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20231222\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"猪八戒股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20231217\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"趣丸集团 (透过与Vision Deal HK Acquisition Corp.的特殊目的收购公司并购交易)\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20231215\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"健康160国际有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20231215\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"广东集信国控检测认证技术服务中心股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20231208\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"创业板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"幂源科技控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20231208\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"云工场科技控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20231205\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"拨云制药\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20231130\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"晶泰科技\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20231130\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"灏辉国际控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20231128\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"江西一脉阳光集团股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20231113\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"老铺黄金股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20231110\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"北京赛目科技股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20231031\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"脑洞极光\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20230808\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"博将控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20230630\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"慧算账\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20230630\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"芝麻智能科技有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20230630\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"哈萨克斯坦石油集团\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20230426\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"汉隆集团股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20230418\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"永丰联集团控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20230330\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"非凡中国控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20230328\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"上海任意门科技有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20230327\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"陆金所控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20230201\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"大麦植发医疗(深圳)集团股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20221230\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"优蓝国际控股股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20221213\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"嘉创房地产控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20221125\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"V3品牌亚洲有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20221118\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"维昇药业有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20221117\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"乐华娱乐集团\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20221116\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"乓乓响（中国）有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20221024\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"看准科技有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20221010\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"奇点国际有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20220930\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"上海格派镍钴材料股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20220926\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"润华智慧健康服务有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20220914\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"粉笔科技有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20220902\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"壹九传媒有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20220829\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"金山云控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20220727\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"富丽宝石国际控股有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20220629\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"畅玩集团\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20220629\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"壹账通金融科技有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20220228\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"上海捍宇医疗科技股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20210414\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"建发物业管理集团有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20200930\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"迈博药业有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20180822\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"Ascentage Pharma Group International\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20180820\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"微盟集团\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20180806\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"上海君实生物医药科技股份有限公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20180806\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"康希诺生物股份公司\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20180717\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"万咖壹联\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20180703\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"AOBiome Therapeutics, Inc.\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20180703\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"Stealth BioTherapeutics Corp\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20180703\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"盟科药业\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20180628\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"信达生物\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20180628\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      },\n" +
				"      {\n" +
				"        \"symbol\": null,\n" +
				"        \"companyName\": \"同程艺龙\",\n" +
				"        \"status\": \"0\",\n" +
				"        \"applyDate\": \"20180621\",\n" +
				"        \"passHearingDate\": null,\n" +
				"        \"boardName\": \"主板\",\n" +
				"        \"notice\": null\n" +
				"      }\n" +
				"    ],\n" +
				"    \"pageNum\": 1,\n" +
				"    \"pageSize\": 500,\n" +
				"    \"size\": 137,\n" +
				"    \"startRow\": 1,\n" +
				"    \"endRow\": 137,\n" +
				"    \"pages\": 1,\n" +
				"    \"prePage\": 0,\n" +
				"    \"nextPage\": 0,\n" +
				"    \"isFirstPage\": true,\n" +
				"    \"isLastPage\": true,\n" +
				"    \"hasPreviousPage\": false,\n" +
				"    \"hasNextPage\": false,\n" +
				"    \"navigatePages\": 8,\n" +
				"    \"navigatepageNums\": [\n" +
				"      1\n" +
				"    ],\n" +
				"    \"navigateFirstPage\": 1,\n" +
				"    \"navigateLastPage\": 1\n" +
				"  }\n" +
				"}";
            log.info("荣聚汇正在处理返回url==" + url + "返回==" + result);
            List<DeliverableTableVO.DeliverableTable> list2 = dealIpoData(result); // 正在处理
            deliverableTableVOList.addAll(list1);
            deliverableTableVOList.addAll(list2);

            deliverableTableVO.setDeliverableTableList(deliverableTableVOList);
            zeroRedis.protoSet(DeliverableTableVO.class.getSimpleName(), deliverableTableVO);
        } catch (Exception e) {
            log.error("fiu获取可递表数据异常", e);
        }

    }

    private List<DeliverableTableVO.DeliverableTable> dealIpoData(String result) {
        List<DeliverableTableVO.DeliverableTable> deliverableTableVOList = new ArrayList();
        RjhRespVO respVO = JSONUtil.fromJson(result, RjhRespVO.class);
        if (null != respVO && respVO.isSuccess()) {
            JSONObject body = JSON.parseObject(respVO.getBody().toString());
            String listJson = body.get("list").toString();
            List<TableDataResp> list = JSONUtil.getList(listJson, TableDataResp.class);
            for (TableDataResp tableDataResp : list) {
                DeliverableTableVO.DeliverableTable vo = new DeliverableTableVO.DeliverableTable();
                vo.setAssetId(tableDataResp.getSymbol());
                vo.setApplicationDate(tableDataResp.getApplyDate());
                vo.setStatus(tableDataResp.getStatus());
                vo.setBoardName(tableDataResp.getBoardName());
                vo.setCompanyName(tableDataResp.getCompanyName());
                deliverableTableVOList.add(vo);
            }
        }
        return deliverableTableVOList;
    }
}
