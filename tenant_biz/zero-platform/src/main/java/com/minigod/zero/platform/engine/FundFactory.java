package com.minigod.zero.platform.engine;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.http.util.HttpUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.config.FundParameterConfig;
import com.minigod.zero.platform.constants.Constants;
import com.minigod.zero.platform.dto.OpenAccountDTO;
import com.minigod.zero.platform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/17 19:21
 * @description：基金相关
 */
@Slf4j
@Component
public class FundFactory {
	@Autowired
	private FundParameterConfig fundParameter;

	/**
	 * 基金开户
	 * @param fundOpenAccount
	 * @return
	 */
	public FundAccountVO fundOpenAccount(OpenAccountDTO fundOpenAccount, Integer type){
		String url = fundParameter.getFundOpenAccountUrl() + Constants.FUND_OPEN_ACCOUNT_INTERFACE_URL;
		String jsonParam= JSONObject.toJSONString(fundOpenAccount);
		log.info("基金平台请求参数：{}",jsonParam);
		String dataBody = null;

		if (type == 0){
			dataBody = HttpUtil.postJson(url,jsonParam);
			if (dataBody == null){
				throw new ServiceException("基金开户请求失败！");
			}
		}else{
			try {
				dataBody = put(url+"/"+fundOpenAccount.getAccountId(),jsonParam,null);
			} catch (IOException e) {
				log.error("基金平台请求异常：", e.getMessage());
			}
		}
		log.info("基金平台返回参数：{}",dataBody);
		JSONObject result = JSONObject.parseObject(dataBody);
		Integer code = result.getInteger("code");
		if (code == 0){
			JSONObject data = result.getJSONObject("data");
			return JSONObject.parseObject(data.toJSONString(),FundAccountVO.class);
		}else{
			log.error("基金开户失败：{}",result);
			throw new ServiceException(result.getString("msg"));
		}
	}

	/**
	 * 账余额查询
	 */

	public List<AccountBalanceVO> getSubAccountBalance(List<String> subAccounts){
		if (CollectionUtil.isEmpty(subAccounts)) {
			return new ArrayList<>();
		}
		String url = fundParameter.getFundOpenAccountUrl() + Constants.FUND_CASH_BALANCE;
		log.info("查询账户：{}",JSONObject.toJSONString(subAccounts));

		List<AccountBalanceVO> accountBalanceList= new ArrayList<>();
		for (String subAccount : subAccounts){
			url = url.replace(Constants.REPLACE_SUB_ACCOUNT,subAccount);
			String dataBody = HttpUtil.get(url,null);
			if (dataBody == null){
				log.warn("基金账号{}获取账户金额失败！",subAccount);
				continue;
			}
			log.info("基金账号{}获取账户金额返回参数：{}",dataBody);
			AccountBalanceVO accountBalance = JSONObject.parseObject(dataBody,AccountBalanceVO.class);
			accountBalance.setSubAccount(subAccount);
			accountBalanceList.add(accountBalance);
		}
		return accountBalanceList;
	}


	public JSONObject selectExchangeRate(){
		String url = fundParameter.getSelectExchangeRateUrl() + Constants.EXCHANGE_RATE;
		String dataBody  = HttpUtil.get(url,null);
		if (dataBody == null){
			throw new ServiceException("查询币种汇率请求失败！");
		}
		log.info("查询汇率返回结果：{}",dataBody);
		JSONObject result = JSONObject.parseObject(dataBody);
		JSONObject data = result.getJSONObject("data");
		if (data != null){
			return data.getJSONObject("exchmap");
		}
		return null;
	}


	public JSONObject selectMarketValue(String accountId){
		String url = fundParameter.getSelectMarketValueUrl() + Constants.MARKET_VALUE + accountId;
		String dataBody  = HttpUtil.get(url,null);
		if (dataBody == null){
			log.error("账户{}查询总市值失败！",accountId);
			throw new ServiceException("查询总市值失败！");
		}
		log.info("{}查询市值返回结果：{}",accountId,dataBody);
		JSONObject result = JSONObject.parseObject(dataBody);

		if (result != null && result.getIntValue("code") != 0){
			throw new ServiceException("查询市值接口失败！");
		}
		JSONObject data = result.getJSONObject("data");
		return data;
	}


	public JSONObject accumulatedInterest(String accountId){
		String url = fundParameter.getSelectMarketValueUrl() + Constants.ACCUMULATED_INTEREST;
		Map<String, Object> queries=new HashMap<>();
		queries.put("extAccountId",accountId);
		String dataBody  = HttpUtil.get(url,queries);
		if (dataBody == null){
			log.error("账户{}查询币种利息失败！",accountId);
			throw new ServiceException("查询总市值失败！");
		}
		log.info("{}查询币种利息返回结果：{}",accountId,dataBody);
		JSONObject result = JSONObject.parseObject(dataBody);

		if (result != null && result.getIntValue("code") != 0){
			throw new ServiceException("查询币种利息接口失败！");
		}
		JSONObject data = result.getJSONObject("data");
		return data;
	}

	/**
	 * 子账户金额
	 * @param accountId
	 * @return
	 */
	public JSONObject accountCapital(String accountId){
		String url = fundParameter.getSelectMarketValueUrl() + Constants.ACCOUNT_CAPITAL;
		Map<String, Object> queries=new HashMap<>();
		queries.put("extAccountId",accountId);
		String dataBody  = HttpUtil.get(url,queries);
		if (dataBody == null){
			log.error("账户{}查询子账户余额失败！",accountId);
			throw new ServiceException("查询总市值失败！");
		}
		log.info("{}查询子账户余额返回结果：{}",accountId,dataBody);
		JSONObject result = JSONObject.parseObject(dataBody);

		if (result != null && result.getIntValue("code") != 0){
			throw new ServiceException("查询子账户余额接口失败！");
		}
		JSONObject data = result.getJSONObject("data");
		return data;
	}


	public JSONObject accountAssetAll(String accountId,String outCurrency){
		String url = fundParameter.getSelectMarketValueUrl() + Constants.ACCOUNT_ASSET_ALL+"?extAccountId="+accountId+"&outCurrency="+outCurrency;
		String dataBody  = HttpUtil.post(url,null);
		if (dataBody == null){
			log.error("账户{}查询子账户余额失败！",accountId);
			throw new ServiceException("查询总市值失败！");
		}
		log.info("{}查询资产信息返回结果：{}",accountId,dataBody);
		JSONObject result = JSONObject.parseObject(dataBody);

		if (result != null && result.getIntValue("code") != 0){
			throw new ServiceException("查询资产信息接口失败！");
		}
		JSONObject data = result.getJSONObject("data");
		return data;
	}

	/**
	 * 持仓列表
	 * @return
	 */
	public PageVO customerPositionList(String extacctid, Integer start, Integer count, Integer busiType){
		String url = fundParameter.getSelectMarketValueUrl() + Constants.CUSTOMER_POSITION_LIST;
		url = String.format(url,extacctid,(start-1) * count,count,"createTime","desc");
		Map<String,String> head = new HashMap<>();
		head.put("busiType",busiType.toString());
		String dataBody  = HttpUtil.get(url,head,null);
		if (dataBody == null){
			log.error("账户{}获取持仓列表失败！",extacctid);
			throw new ServiceException("获取持仓列表失败！");
		}
		log.info("{}获取持仓列表返回结果：{}",extacctid,dataBody);
		JSONObject result = JSONObject.parseObject(dataBody);

		if (result != null && result.getIntValue("code") != 0){
			throw new ServiceException("获取持仓列表失败！");
		}
		JSONObject data = result.getJSONObject("data");
		Integer total = data.getInteger("total");
		JSONArray dataList = data.getJSONArray("dataList");
		PageVO page = new PageVO();
		page.setCurrent(start);
		page.setSize(count);
		page.setTotal(total);
		page.setPages(getPages(total,count));
		List<CustomerPositionsDetailVO> positionsList = new ArrayList<>();
		if (!CollectionUtil.isEmpty(dataList)){
			for (Object obj : dataList){
				if (obj != null) {
					positionsList.add(new CustomerPositionsDetailVO((JSONObject) obj));
				}
			}
		}
		page.setRecords(positionsList);
		return page;
	}


	public PageVO orderList(String extAccountId,Integer start,Integer count,Integer busiType,Integer status){
		String url = fundParameter.getSelectMarketValueUrl() + Constants.CUSTOMER_ORDER;
		Map<String,Object> param = new HashMap<>();
		param.put("extAccountId",extAccountId);
		param.put("start",(start-1) * count);
		param.put("count",count);
		param.put("busiType",busiType);
		param.put("tabType",status);
		String dataBody = HttpUtil.postJson(url,JSONObject.toJSONString(param));
		log.info("{}获取订单列表返回结果：{}",extAccountId,dataBody);
		JSONObject result = JSONObject.parseObject(dataBody);
		if (result != null && result.getIntValue("code") != 0){
			throw new ServiceException("获取订单列表失败！");
		}
		JSONObject data = result.getJSONObject("data");
		Integer total = data.getInteger("total");
		JSONArray dataList = data.getJSONArray("dataList");
		PageVO page = new PageVO();
		page.setCurrent(start);
		page.setSize(count);
		page.setTotal(total);
		page.setPages(getPages(total,count));
		List<CustomerOrderVO> positionsList = new ArrayList<>();
		if (!CollectionUtil.isEmpty(dataList)){
			for (Object obj : dataList){
				if (obj != null) {
					positionsList.add(new CustomerOrderVO((JSONObject) obj));
				}
			}
		}
		page.setRecords(positionsList);
		return page;
	}

	public PageVO customerHistoryOrder(String extacctid, Integer start, Integer count, Integer busiType){
		String url = fundParameter.getSelectMarketValueUrl() + Constants.CUSTOMER_HISTORY_ORDER;
		url = String.format(url,extacctid,(start-1) * count,count,busiType);
		String dataBody  = HttpUtil.get(url,null);
		if (dataBody == null){
			log.error("账户{}获取历史订单列表失败！",extacctid);
			throw new ServiceException("获取历史订单列表失败！");
		}
		log.info("{}获取历史订单列表返回结果：{}",extacctid,dataBody);
		JSONObject result = JSONObject.parseObject(dataBody);

		if (result != null && result.getIntValue("code") != 0){
			throw new ServiceException("获取历史订单列表失败！");
		}
		JSONObject data = result.getJSONObject("data");
		Integer total = data.getInteger("total");
		JSONArray dataList = data.getJSONArray("orders");
		PageVO page = new PageVO();
		page.setCurrent(start);
		page.setSize(count);
		page.setTotal(total);
		page.setPages(getPages(total,count));
		List<CustomerHistoryOrderVO> positionsList = new ArrayList<>();
		if (!CollectionUtil.isEmpty(dataList)){
			for (Object obj : dataList){
				if (obj != null) {
					positionsList.add(new CustomerHistoryOrderVO((JSONObject) obj));
				}
			}
		}
		page.setRecords(positionsList);
		return page;
	}


	public PageVO distributionRecords(String extAccountId, Integer start, Integer count, Integer busiType){
		String url = fundParameter.getSelectMarketValueUrl() + Constants.DIVIDEND_LIST;
		Map<String,Object> param = new HashMap<>();
		param.put("extAccountId",extAccountId);
		param.put("start",(start-1) * count);
		param.put("count",count);
		param.put("busiType",busiType);
		String dataBody = HttpUtil.postJson(url,JSONObject.toJSONString(param));
		JSONObject result = JSONObject.parseObject(dataBody);
		log.info("{}获取派息列表返回结果：{}",extAccountId,JSONObject.toJSONString(result));
		if (result != null && result.getIntValue("code") != 0){
			throw new ServiceException("获取派息列表失败！");
		}
		JSONObject data = result.getJSONObject("data");
		if (CollectionUtil.isEmpty(data)){
			return new PageVO();
		}
		Integer total = data.getInteger("total");
		JSONArray dataList = data.getJSONArray("dataList");
		PageVO page = new PageVO();
		page.setCurrent(start);
		page.setSize(count);
		page.setTotal(total);
		page.setPages(getPages(total,count));
		List<DividendDistributionRecords> positionsList = new ArrayList<>();
		if (!CollectionUtil.isEmpty(dataList)){
			for (Object obj : dataList){
				if (obj != null){
					positionsList.add(new DividendDistributionRecords((JSONObject) obj));
				}
			}
		}
		page.setRecords(positionsList);
		return page;
	}

	/**
	 * 查询成交确认书
	 * @param orderId
	 * @return
	 */
	public List<Map<String,String>>  selectConfirmation(String orderId){
		String url = fundParameter.getDownloadConfirmationUrl() + String.format(Constants.CONFIRMLETTER,orderId);
		Map<String, String> header = new HashMap<>();
		header.put("x-channel",fundParameter.getChannel().toString());
		header.put("x-company-code",fundParameter.getCompanyCode());
		log.info("查询成交确认书参数：{}，请求头参数：{}",url,JSONObject.toJSONString(header));
		String dataBody  = HttpUtil.get(url,header,null);
		if (dataBody == null){
			log.error("订单{}获取成交确认书失败！",orderId);
			throw new ServiceException("获取成交确认书失败！");
		}
		log.info("{}获取成交确认书返回结果：{}",orderId,dataBody);
		JSONObject result = JSONObject.parseObject(dataBody);

		if (result != null && result.getIntValue("code") != 0){
			throw new ServiceException("获取成交确认书失败！");
		}
		JSONArray data = result.getJSONArray("data");
		if (data == null){
			return new ArrayList<>();
		}
		List<Map<String,String>> fileList = new ArrayList<>();
		for (Object obj : data){
			if (obj == null){
				continue;
			}
			JSONObject fileObj = (JSONObject) obj;
			Map<String,String> file = new HashMap<>();
			file.put("name",fileObj.getString("name"));
			file.put("path",fundParameter.getDownloadConfirmationUrl()+fileObj.getString("path"));
			fileList.add(file);
		}
		return fileList;
	}



	private static Integer getPages(Integer total,Integer size){
		if (total % size > 0){
			return total / size + 1;
		}
		if (total % size < 0){
			return 1;
		}
		if (total % size == 0){
			return total / size;
		}
		return 0;
	}

	public AccountBalanceVO getSubAccountBalance(String subAccounts){
		List<AccountBalanceVO> accountBalanceList = getSubAccountBalance(Arrays.asList(subAccounts));
		if (accountBalanceList.size() > 0){
			return accountBalanceList.get(0);
		}
		return null;
	}


	public  static String put(String url,String data,Map<String,String> heads) throws IOException {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpPut httpPut = new HttpPut(url);
			if(heads!=null){
				Set<String> keySet=heads.keySet();
				for(String s:keySet){
					httpPut.addHeader(s,heads.get(s));
				}
			}
			StringEntity stringEntity = new StringEntity(data, ContentType.APPLICATION_JSON);
			httpPut.setEntity(stringEntity);

			System.out.println("Executing request " + httpPut.getRequestLine());

			// Create a custom response handler
			ResponseHandler< String > responseHandler = response -> {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			};
			String responseBody = httpclient.execute(httpPut, responseHandler);
			return  responseBody;
		}
	}


	public R<JSONObject> selectOrderInfo(String orderId) {
		String url = fundParameter.getSelectMarketValueUrl() + Constants.USER_ORDER;
		Map<String,Object> param = new HashMap<>();
		param.put("orderId",orderId);
		String dataBody = HttpUtil.get(url,param);
		log.info("{}获取订单列表返回结果：{}",orderId,dataBody);
		JSONObject result = JSONObject.parseObject(dataBody);
		if (result != null && result.getIntValue("code") != 0){
			return R.fail("获取基金数据失败 msg:{}"+result.getString("msg"));
		}
		return R.data(result.getJSONObject("data"));
	}
}
