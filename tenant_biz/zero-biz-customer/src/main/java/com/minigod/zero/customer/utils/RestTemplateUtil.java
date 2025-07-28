package com.minigod.zero.customer.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minigod.zero.core.launch.constant.ESourceType;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.SignUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.*;

@Slf4j
@Component
public class RestTemplateUtil {

	@Resource
	private RestTemplate restTemplate;

	private RestTemplateUtil() {
	}

	public <T extends Serializable> T postSend(String url, Class<T> clazz, Object params) {
		try {
			HttpEntity<Object> requestEntity = new HttpEntity<>(params, getHeaders(params));
			R<T> result = restTemplate.postForObject(url, requestEntity, R.class);
			if (result == null) {
				return null;
			}
			if (result.getCode() == R.success().getCode() && result.getData() != null) {
				return new ObjectMapper().convertValue(result.getData(), clazz);
			}
			log.warn(url + result.getMsg());
		} catch (Exception e) {
			log.error(url + "远程调用异常", e);
		}
		return null;
	}

	public <T extends Serializable> T postSend(String url, MultiValueMap httpHeader, Class<T> clazz, Object params) {
		try {
			HttpEntity<Object> requestEntity = new HttpEntity<>(params, httpHeader);
			R<T> result = restTemplate.postForObject(url, requestEntity, R.class);
			if (result == null) {
				return null;
			}
			if (result.getCode() == R.success().getCode() && result.getData() != null) {
				return new ObjectMapper().convertValue(result.getData(), clazz);
			}
			log.warn(url + result.getMsg());
		} catch (Exception e) {
			log.error(url + "远程调用异常", e);
		}
		return null;
	}

	public <T extends Serializable> T getSend(String url, Class<T> clazz, Object params) {
		try {
			HttpEntity<Object> requestEntity = new HttpEntity<>(params, getHeaders(params));
			R<T> result = restTemplate.getForObject(url, R.class, requestEntity);
			if (result == null) {
				return null;
			}
			if (result.getCode() == R.success().getCode() && result.getData() != null) {
				return new ObjectMapper().convertValue(result.getData(), clazz);
			}
			log.warn(url + result.getMsg());
		} catch (Exception e) {
			log.error(url + "远程调用异常", e);
		}
		return null;
	}

	public <T extends Serializable> List<T> postSends(String url, Class<T> clazz, Object params) {
		try {
			HttpEntity<Object> requestEntity = new HttpEntity<>(params, getHeaders(params));
			R<List<T>> result = restTemplate.postForObject(url, requestEntity, R.class);
			if (null == result) {
				return Collections.emptyList();
			}
			if (result.getCode() != R.success().getCode()) {
				log.error(url + result.getMsg());
				return Collections.emptyList();
			}
			if (CollectionUtil.isNotEmpty(result.getData())) {
				List<T> list = new ArrayList<>();
				for (T data : result.getData()) {
					list.add(new ObjectMapper().convertValue(data, clazz));
				}
				return list;
			}
		} catch (Exception e) {
			log.error(url + "远程调用异常", e);
		}
		return Collections.emptyList();
	}

	public <T extends Serializable> Map<String,T> postSendMap(String url, Class<T> clazz, Object params) {
		Map<String, T> resMap = new HashMap<>();
		try {
			HttpEntity<Object> requestEntity = new HttpEntity<>(params, getHeaders(params));
			R<Map<String,T>> result = restTemplate.postForObject(url, requestEntity, R.class);
			if (null == result) {
				return resMap;
			}
			if (result.getCode() != R.success().getCode()) {
				log.error(url + result.getMsg());
				return resMap;
			}
			if (result.getData() != null && StringUtils.isNotBlank(result.getData().toString())) {
				Map<String, Object> map = JSONObject.parseObject(JSON.toJSONString(result.getData()));
				for (String key : map.keySet()) {
					resMap.put(key,new ObjectMapper().convertValue(map.get(key), clazz));
				}
				return resMap;
			}
		} catch (Exception e) {
			log.error(url + "远程调用异常", e);
		}
		return resMap;
	}

	/**
	 * 返回的data部分需要是json字符串
	 *
	 * @param url
	 * @param clazz
	 * @param params
	 * @param <T>
	 * @return
	 */
	public <T extends Serializable> List<T> getSends(String url, Class<T> clazz, Map<String, String> params) {
		try {
			HttpEntity<Object> requestEntity = new HttpEntity<>(params, getHeaders(params));
			String paramsStr = "";
			if (!CollectionUtil.isEmpty(params)) {
				paramsStr = "?" + URLEncodedUtils.format(getNameValuePairsFromMap(params), Charset.forName("UTF-8"));
			}
			if (StringUtils.isNotBlank(paramsStr)) {
				url = url + paramsStr;
			}
			R<String> result = restTemplate.getForObject(url, R.class, requestEntity);
			if (null == result) {
				return Collections.emptyList();
			}
			String jsonData = result.getData();
			if (StringUtils.isNotBlank(jsonData)) {
				List<T> rList = JSONArray.parseArray(jsonData, clazz);
				return rList;
			} else {
				return Collections.emptyList();
			}

		} catch (Exception e) {
			log.error(url + "远程调用异常", e.getMessage());
		}
		return Collections.emptyList();
	}

	public String getSend(String url) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add(TokenConstant.HEADER, WebUtil.getHeader(TokenConstant.HEADER));
			headers.add(TokenConstant.LANGUAGE, WebUtil.getHeader(TokenConstant.LANGUAGE));
			headers.add("Accept-Charset", "UTF-8");
			HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<>(headers);
			ResponseEntity<String> result4 = restTemplate.exchange(url, HttpMethod.GET, formEntity, String.class);
			return result4.getBody();
		} catch (Exception e) {
			log.error(url + "远程调用异常", e);
		}
		return null;
	}

	public String postSend(String url, JSONObject params) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add(TokenConstant.HEADER, WebUtil.getHeader(TokenConstant.HEADER));
			headers.add("Accept-Charset", "UTF-8");
			HttpEntity<Object> formEntity = new HttpEntity<>(params, headers);
			ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, formEntity, String.class);
			return result.getBody();
		} catch (Exception e) {
			log.error(url + "远程调用异常", e);
		}
		return null;
	}

	public String putSend(String url, JSONObject params) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add(TokenConstant.HEADER, WebUtil.getHeader(TokenConstant.HEADER));
			headers.add("Accept-Charset", "UTF-8");
			HttpEntity<Object> formEntity = new HttpEntity<>(params, headers);
			ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.PUT, formEntity, String.class);
			return result.getBody();
		} catch (Exception e) {
			log.error(url + "远程调用异常", e);
		}
		return null;
	}

	public MultiValueMap getHeaders(Object params) {
		HttpServletRequest request = WebUtil.getRequest();
		HttpHeaders requestHeaders = new HttpHeaders();
		if (request != null) {
			String sourceType = request.getHeader(TokenConstant.SOURCE_TYPE);
			String deviceCode = request.getHeader(TokenConstant.DEVICE_CODE);
			String authToken = request.getHeader(TokenConstant.HEADER);
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);
			requestHeaders.add(TokenConstant.SOURCE_TYPE, sourceType);
			requestHeaders.add(TokenConstant.REAL_IP, WebUtil.getIP());
			requestHeaders.add(TokenConstant.LANGUAGE, WebUtil.getLanguage());
			requestHeaders.add(TokenConstant.DEVICE_CODE, deviceCode);
			requestHeaders.add(TokenConstant.HEADER, authToken);
			requestHeaders.add(TokenConstant.QUOT_TOKEN, WebUtil.getHeader(TokenConstant.QUOT_TOKEN));
			Long ts = System.currentTimeMillis();
			String sign = SignUtil.sign(JSONObject.toJSONString(params), ts.toString());
			requestHeaders.add("TS", ts.toString());
			requestHeaders.add("Sign", sign);
		} else {
			requestHeaders.add(TokenConstant.SOURCE_TYPE, ESourceType.WEB.getName());
		}
		return requestHeaders;
	}

	public HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		Enumeration<String> enumeration = WebUtil.getHeaderNames();
		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();
			String value = WebUtil.getHeader(name);
			headers.add(name, value);
		}
		return headers;
	}


	/**
	 * 设置参数
	 *
	 * @param params
	 * @return
	 */
	private static final List<NameValuePair> getNameValuePairsFromMap(Map<String, String> params) {
		List<NameValuePair> pairs = new ArrayList<>();
		if (!CollectionUtil.isEmpty(params)) {
			for (Map.Entry<String, String> e : params.entrySet()) {
				pairs.add(new BasicNameValuePair(e.getKey(), e.getValue()));
			}
		}
		return pairs;
	}

	/**
	 * 封装reqVO参数
	 *
	 * @param params
	 * @return
	 */
	public static Map<String, Object> getRequest(Map<String, Object> params) {
		Map<String, Object> request = new HashMap<>();
		request.put("params", params);
		return request;
	}

}
