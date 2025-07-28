package com.minigod.zero.cust.mq;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.minigod.zero.biz.common.constant.MqTopics;
import com.minigod.zero.biz.common.mq.AddMessageReq;
import com.minigod.zero.biz.common.mq.ConnecterCust;
import com.minigod.zero.biz.common.mq.ConnecterInfo;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.service.ICustInfoService;
import com.minigod.zero.mq.pulsar.annotation.PulsarConsumer;
import com.minigod.zero.mq.pulsar.producer.ProducerCollector;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class CustRegsiterListener {

	@Resource
    private ICustInfoService custInfoService;
	@Resource
	private ProducerCollector producerCollector;

    /**
     * 订阅同步生效的公司户关联人及个人户权限
     *
     * @param message
     */
	@PulsarConsumer(topic = MqTopics.BPM_COMPANY_CONNECTER_INFO, clazz = AddMessageReq.class, namespace = "bpm",
		subscriptionType = SubscriptionType.Shared, subscriptionName = MqTopics.BPM_COMPANY_CONNECTER_INFO)
    public void onMessage(AddMessageReq message) {
        try {
            if (message == null) {
                return;
            }
			log.info("receive connector message: {}", message.getMessage());
			ConnecterInfo connecter = JSON.parseObject(message.getMessage(), ConnecterInfo.class);
			if (connecter == null) {
				log.error("公司户关联人或个人户权限未同步，接收数据为空");
				return;
			}
			if (StringUtils.isNotBlank(connecter.getAuthorPhone()) && connecter.getAuthorPhone().contains("-")) {
				String[] strs = connecter.getAuthorPhone().split("-");
				if (strs.length < 2) {
					log.error("公司户授权人未同步，手机号格式有误");
					return;
				}
				connecter.setAuthorArea(strs[0].replace("+", "").trim());
				connecter.setAuthorPhone(strs[1]);
			}
			// 未授权交易需过滤交易权限
			if (connecter.getTradeAuth() == 0) {
				log.info("未授权交易过滤交易权限：Acct = {}", connecter.getTradeAccount());
				connecter.setAppPermission(connecter.getAppPermission().replaceAll("(,|^)T[^,]*", ""));
			}
			R rt = custInfoService.registerAccount(connecter);
			if (rt.isSuccess()) {
				Long custId = (Long) rt.getData();
				// 推送custId给BPM
				this.pushRegisterCust(connecter, custId);
			}
			log.info("公司户关联人或个人户权限同步成功");
        } catch (Exception e) {
            log.error("async applyCommit error,", e);
        }
    }

	/**
	 * 将公司户授权人注册的custId推送给BPM
	 */
	private boolean pushRegisterCust(ConnecterInfo connecter, Long custId) {
		try {
			ConnecterCust cust = new ConnecterCust();
			cust.setCustId(custId);
			cust.setCustType(connecter.getCustType());
			cust.setAuthorId(connecter.getAuthorId());
			cust.setTradeAccount(connecter.getTradeAccount());
			AddMessageReq message = new AddMessageReq();
			message.setMessage(JSONUtil.toJsonStr(cust));
			producerCollector.getProducer(MqTopics.BPM_COMPANY_AUTHOR_CUST_INFO).sendAsync(message);
			log.info("线下开户注册ID推送BPM成功: {}", JSONObject.toJSONString(cust));
			return true;
		} catch (Exception e) {
			log.error("线下开户注册ID推送BPM异常", e);
			return false;
		}
	}

}
