package com.minigod.minigodinformation.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.minigodinformation.dto.http.*;
import com.minigod.minigodinformation.entity.*;
import com.minigod.minigodinformation.mapper.*;
import com.minigod.minigodinformation.service.MinigodInformationClientService;
import com.minigod.minigodinformation.vo.AnnouncementProductVO;
import com.minigod.minigodinformation.vo.ClientExchangeAnnInfoVO;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @ClassName: com.minigod.minigodinformation.service.impl.MinigodInformationClientServiceImpl
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/11/7 14:22
 * @Version: 1.0
 */
@Slf4j
@Service
public class MinigodInformationClientServiceImpl implements MinigodInformationClientService {
	@Autowired
	private ExchangeAnnouncementMapper exchangeAnnouncementMapper;

	@Autowired
	private ExchangeAnnouncementFileMapper exchangeAnnouncementFileMapper;

	@Autowired
	private ExchangeDisclosureAnnouncementMapper exchangeDisclosureAnnouncementMapper;

	@Autowired
	private ExchangeDisclosureAnnouncementFileMapper exchangeDisclosureAnnouncementFileMapper;

	@Autowired
	private ExchangeDisclosureAnnouncementProductMapper exchangeDisclosureAnnouncementProductMapper;

	@Autowired
	private IDictBizClient dictBizClient;


	private void fetchAndProcessAnnouncements(String url, Date startTime, Date endTime,
											  BiConsumer<String, String> processFunc) {
		if (startTime == null || endTime == null) {
			startTime = new Date();
			endTime = new Date();
		}

		String startStrTime = DateUtil.format(startTime, "yyyy-MM-dd");
		String endStrTime = DateUtil.format(endTime, "yyyy-MM-dd");

		int current = 1;
		HashMap<String, Object> map = new HashMap<>();
		//map.put("Zero-Auth", "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0ZW5hbnRfaWQiOiIwMDAwMDAiLCJ1c2VyX25hbWUiOiI5ODk4NzMyMSIsIm5pY2tOYW1lIjoi55So5oi3NzMyMSIsImlzV2hpdGVMaXN0IjpmYWxzZSwiY2xpZW50X2lkIjoidXNlcl9jbGllbnQiLCJyZWFsTmFtZSI6IiIsInVzZXJfaWQiOiIxMDAwMTQxOSIsInJvbGVfaWQiOi0xLCJzY29wZSI6WyJhbGwiXSwiY3VzdElkIjoiMTAwMDE0MTkiLCJleHAiOjE3MzI2NzMyMTIsImV4cGlyZXNfaW4iOjI1OTIwMDAsImp0aSI6ImVjNjBmYzlhLTY3MGMtNGMzNi05OTg2LTUxMWRmMGM5YTYxYyIsImFjY291bnQiOiIxMzI4MzkifQ.LPniMbodPcP2mFXP8ov_B8oKBrQUvUrIzfPtJRc6LvI");
		url = url + "?size=10&current=" + current + "&startTime=" + startStrTime + "&endTime=" + endStrTime;
		while (true) {
			String dataBody = HttpUtil.get(url);
			log.info("请求接口,url={}, 返回数据：{}", url, dataBody);

			if (dataBody == null) {
				log.warn("请求接口失败，返回数据为空");
				return;
			}

			JSONObject jsonObject = JSON.parseObject(dataBody);
			if (!jsonObject.getInteger("code").equals(200)) {
				log.warn("请求接口失败，返回状态码不为200，code={}", jsonObject.getInteger("code"));
				return;
			}

			Integer pages = jsonObject.getJSONObject("data").getInteger("pages");
			processFunc.accept(jsonObject.getJSONObject("data").getJSONArray("records").toJSONString(), url);

			// 检查是否还有下一页
			if (current >= pages) {
				break;
			}
			current++;
		}
	}

	private void processExchangeAnnouncementData(String jsonArrayStr) {
		List<OpenExchangeAnnListVO> openExchangeAnnList = JSON.parseArray(jsonArrayStr, OpenExchangeAnnListVO.class);
		for (OpenExchangeAnnListVO openExchangeAnnListVO : openExchangeAnnList) {
			ExchangeAnnouncement exchangeAnnouncement = exchangeAnnouncementMapper.selectById(openExchangeAnnListVO.getId());
			if (exchangeAnnouncement != null) {
				BeanUtil.copy(openExchangeAnnListVO, exchangeAnnouncement);
				exchangeAnnouncementMapper.updateById(exchangeAnnouncement);
				exchangeAnnouncementFileMapper.delete(Wrappers.<ExchangeAnnouncementFile>lambdaQuery()
					.eq(ExchangeAnnouncementFile::getAnnouncementId, openExchangeAnnListVO.getId()));
			} else {
				exchangeAnnouncement = BeanUtil.copy(openExchangeAnnListVO, ExchangeAnnouncement.class);
				exchangeAnnouncementMapper.insert(exchangeAnnouncement);
			}
			if (openExchangeAnnListVO.getFiles()!=null) {

				// 保存附件
				for (String file : openExchangeAnnListVO.getFiles()) {
					ExchangeAnnouncementFile exchangeAnnouncementFile = new ExchangeAnnouncementFile();
					exchangeAnnouncementFile.setAnnouncementId(openExchangeAnnListVO.getId());
					exchangeAnnouncementFile.setFileUrl(file);
					exchangeAnnouncementFile.setCreateTime(new Date());
					exchangeAnnouncementFile.setUpdateTime(new Date());
					exchangeAnnouncementFileMapper.insert(exchangeAnnouncementFile);
				}
			}
		}
	}

	private void processExchangeDisclosureData(String jsonArrayStr) {
		List<ClientAnnListVO> clientAnnList = JSON.parseArray(jsonArrayStr, ClientAnnListVO.class);
		for (ClientAnnListVO clientAnnListVO : clientAnnList) {
			ExchangeDisclosureAnnouncement exchangeDisclosureAnnouncement = exchangeDisclosureAnnouncementMapper.selectById(clientAnnListVO.getId());
			if (exchangeDisclosureAnnouncement != null) {
				BeanUtil.copy(clientAnnListVO, exchangeDisclosureAnnouncement);
				exchangeDisclosureAnnouncementMapper.updateById(exchangeDisclosureAnnouncement);
				exchangeDisclosureAnnouncementFileMapper.delete(Wrappers.<ExchangeDisclosureAnnouncementFile>lambdaQuery()
					.eq(ExchangeDisclosureAnnouncementFile::getAnnouncementId, clientAnnListVO.getId()));
				exchangeDisclosureAnnouncementProductMapper.delete(Wrappers.<ExchangeDisclosureAnnouncementProduct>lambdaQuery()
					.eq(ExchangeDisclosureAnnouncementProduct::getAnnouncementId, clientAnnListVO.getId()));
			} else {
				exchangeDisclosureAnnouncement = BeanUtil.copy(clientAnnListVO, ExchangeDisclosureAnnouncement.class);
				exchangeDisclosureAnnouncementMapper.insert(exchangeDisclosureAnnouncement);
			}

			if (clientAnnListVO.getFiles()!=null) {

				// 保存附件
				for (String file : clientAnnListVO.getFiles()) {
					ExchangeDisclosureAnnouncementFile exchangeDisclosureAnnouncementFile = new ExchangeDisclosureAnnouncementFile();
					exchangeDisclosureAnnouncementFile.setAnnouncementId(clientAnnListVO.getId());
					exchangeDisclosureAnnouncementFile.setFileUrl(file);
					exchangeDisclosureAnnouncementFile.setCreateTime(new Date());
					exchangeDisclosureAnnouncementFile.setUpdateTime(new Date());
					exchangeDisclosureAnnouncementFileMapper.insert(exchangeDisclosureAnnouncementFile);
				}
			}
			if (clientAnnListVO.getProductList()!=null) {


				// 保存产品
				for (AnnouncementProductVO clientAnnProductVO : clientAnnListVO.getProductList()) {
					ExchangeDisclosureAnnouncementProduct exchangeDisclosureAnnouncementProduct = new ExchangeDisclosureAnnouncementProduct();
					exchangeDisclosureAnnouncementProduct.setAnnouncementId(clientAnnListVO.getId());
					exchangeDisclosureAnnouncementProduct.setProductName(clientAnnProductVO.getProductName());
					exchangeDisclosureAnnouncementProduct.setProductIsin(clientAnnProductVO.getProductIsin());
					exchangeDisclosureAnnouncementProduct.setProductId(clientAnnProductVO.getProductId());
					exchangeDisclosureAnnouncementProduct.setCreateTime(new Date());
					exchangeDisclosureAnnouncementProduct.setUpdateTime(new Date());
					exchangeDisclosureAnnouncementProductMapper.insert(exchangeDisclosureAnnouncementProduct);
				}
			}
		}
	}

	@Override
	public void syExchangeAnnouncement(Date startTime, Date endTime) {
		String url = "http://exchange.ifund.live:31075/openapi/zero-biz-bpmn/client/disclosure_announcement/page";
		fetchAndProcessAnnouncements(url, startTime, endTime, (jsonArrayStr, apiUrl) -> processExchangeAnnouncementData(jsonArrayStr));
	}

	@Override
	public void syExchangeDisclosureAnnouncement(Date startTime, Date endTime) {
		String url = "http://exchange.ifund.live:31075/openapi/zero-biz-bpmn/client/disclosure_announcement/announcement/page";
		fetchAndProcessAnnouncements(url, startTime, endTime, (jsonArrayStr, apiUrl) -> processExchangeDisclosureData(jsonArrayStr));
	}


}
