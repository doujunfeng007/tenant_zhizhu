package com.minigod.zero.trade.afe.service;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.trade.afe.req.AfeReq;
import com.minigod.zero.trade.afe.resp.back.BackCommonResponse;
import com.minigod.zero.trade.afe.util.DecodeAndUnzip;
import com.minigod.zero.trade.afe.util.SenderIdUtil;
import com.minigod.zero.trade.afe.util.XmlGeneratorUtil;
import com.minigod.zero.trade.afe.util.ZipAndBase64Encode;
import com.minigod.zero.trade.afe.webservice.generalservice.GeneralService;
import com.minigod.zero.trade.afe.webservice.generalservice.GeneralServicePortType;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.XML;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: com.minigod.zero.trade.afe.service.SendApiServiceImpl
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/4/25 13:34
 * @Version: 1.0
 */
@Service
@Slf4j
public class SendApiServiceImpl implements SendApiService {
    @Value("${afe.companyId:LG}")
    private String companyId;
    @Value("${afe.userId:LG_USER}")
    private String userId;
    @Value("${afe.userPassword:testtest}")
    private String userPassword;

    @Autowired
	@Lazy
    private GeneralService generalService;

    @Override
    public String postGeneralServiceSend(Object bean, String apiName, boolean isCase) {
        String queryResponse;
        String response;
        String requestId = String.valueOf(SenderIdUtil.autoIncrement.incrementAndGet());
        long beginTime = System.currentTimeMillis();
        try {
            // 1.组装xml
            String requestXml = XmlGeneratorUtil.toXml(bean,
                "<REQUEST TYPE=\"" + apiName + "\" ID=\"" + requestId + "\">", "</REQUEST>", isCase);
            log.info("senderId={}, afe.request={}", requestId, requestXml);
            // 2.xml加密/ zip格式加密
            String zipsign = ZipAndBase64Encode.zipAndBase64Encode(requestXml);

            AfeReq afeReq = new AfeReq();
            afeReq.setZip(zipsign);
            // afeReq.setSignature("sign");

            // 再组转xml
            String result = XmlGeneratorUtil.toXmlCase(afeReq,
                "<Request ServiceApp=\"eService\" ID=\"" + requestId + "\">", "</Request>", !isCase);
            log.info("result:" + result);

            // 发送请求
            GeneralServicePortType generalServicePortType = generalService.getGeneralServiceHttpSoap11Endpoint();
            queryResponse = generalServicePortType.messageTransfer(companyId, userId, userPassword, result);
            // log.info("queryResponse:"+queryResponse);

        } catch (IOException e) {
            log.error("组装请求出错, e:{]", e.getMessage());
            throw new RuntimeException(e);
        }

        // 4.解析返回信息
        try {
            String sub =
                StrUtil.sub(queryResponse, queryResponse.lastIndexOf("<ZIP>") + 5, queryResponse.lastIndexOf("</ZIP>"));
            response = DecodeAndUnzip.decodeAndUnzip(sub);
            long endTime = System.currentTimeMillis();
            log.info("senderId={}, afe.response {} spend {} ms", requestId, response, endTime - beginTime);
        } catch (IOException e) {
            log.error("解析结果出错, e:{]", e.getMessage());
            throw new RuntimeException(e);
        }
        return response;
    }

    @Override
    public BackCommonResponse sendMessage(Object req, String apiName, boolean b) {
        String xmlStr = postGeneralServiceSend(req, apiName, b);
        if (StringUtils.isNotEmpty(xmlStr)) {
            JSONObject respJson = XML.toJSONObject(xmlStr);
			String jsonStr =cn.hutool.json.JSONUtil.toJsonStr(respJson);
			BackCommonResponse response = JSONUtil.fromJson(jsonStr, BackCommonResponse.class);
            return response;
        } else {
            return null;
        }
    }
}
