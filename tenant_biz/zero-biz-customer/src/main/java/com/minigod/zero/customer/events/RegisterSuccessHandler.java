package com.minigod.zero.customer.events;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.dto.CustBrokerDTO;
import com.minigod.zero.customer.entity.CostPackage;
import com.minigod.zero.customer.entity.CustomerPackage;
import com.minigod.zero.customer.fegin.IBrokerClient;
import com.minigod.zero.customer.mapper.CostPackageMapper;
import com.minigod.zero.customer.mapper.CustomerPackageMapper;
import com.minigod.zero.customer.vo.CustomerInfoVO;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/12 17:15
 * @description：
 */
@Slf4j
@Component
public class RegisterSuccessHandler implements MiniGodEventHandler{

	@Autowired
	private CostPackageMapper costPackageMapper;
	@Autowired
	private IBrokerClient brokerClient;

	@Autowired
	private IDictBizClient dictBizClient;
	@Autowired
	private CustomerPackageMapper customerPackageMapper;

	@Override
	public void hand(Object param) {
		log.info("注册事件处理，入参：{}",param);
		if (param == null){
			return;
		}
		JSONObject paramObj = (JSONObject) param;
		if (paramObj == null){
			return;
		}
		//用户信息
		CustomerInfoVO customerInfo = paramObj.getObject("custInfo",CustomerInfoVO.class);
		if (customerInfo == null){
			log.info("注册成功事件处理失败，用户信息为null");
			return;
		}
		Long custId = customerInfo.getId();
		//其他参数
		JSONObject otherParam = JSONObject.parseObject(paramObj.getString("param"));
		if (otherParam == null){
			log.info("注册成功事件处理失败，参数为null");
			return;
		}
		//套餐id
		String packageNum = otherParam.getString("packageNum");
		CostPackage costPackage = costPackageMapper.selectByNumber(packageNum);
		if (costPackage == null){
			log.info("用户{}注册套餐不存在，分配默认套餐",custId);
			costPackage = costPackageMapper.getDefaultPackage();
		}
		if (costPackage == null){
			log.info("用户{}绑定套餐失败，未找到套餐",custId);
			return;
		}
		Long packageId = costPackage.getId();
		CustomerPackage customerPackage = new CustomerPackage();
		customerPackage.setPackageId(costPackage.getId());
		customerPackage.setCustId(custId);
		customerPackage.setStatus(0);
		customerPackage.setCreateTime(new Date());
		customerPackage.setTenantId(customerInfo.getTenantId());
		customerPackageMapper.insertSelective(customerPackage);
		log.info("用户{}绑定套餐{}成功",custId,packageId);
		try {
			if(!brokerEvent(otherParam,customerInfo)){
				log.info("用户{}注册成功，触发broker事件失败",custId);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean brokerEvent(JSONObject otherParam,CustomerInfoVO customerInfo) {
		if (otherParam == null){
			log.info("注册成功事件处理失败，参数为null");
			return false;
		}
		//其他参数
		Long brokerId = otherParam.getLong("brokerId");
		if (brokerId == null){
			R result = dictBizClient.getValue("top_manager", "brokerId");
			if (result.isSuccess()){
				String brokerStr = (String) result.getData();
				brokerId = Long.valueOf(brokerStr);
			}else{
				log.info("未获取到brokerId,绑定经理人信息失败");
				return false;
			}
		}
		//默认注册成功,绑定经理人信息
		CustBrokerDTO custBrokerDTO = new CustBrokerDTO();
		custBrokerDTO.setCustId(customerInfo.getId());
		custBrokerDTO.setBrokerId(brokerId);
		R r = brokerClient.bindCustBroker(custBrokerDTO);
		if (!r.isSuccess()){
			log.error("绑定经理人信息失败 {}",r.getMsg());
		}

		return true;
	}

	@Override
	public String eventType() {
		return EventType.REGISTER_SUCCESS.getCode();
	}
}
