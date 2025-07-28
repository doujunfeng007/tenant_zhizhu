package com.minigod.zero.biz.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.message.BasicHeader;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.*;

public class APIClient {

	public static final String CHARSET = "UTF-8";
	public static final String ZONE_TIME = "EEE, dd MMM yyyy HH:mm:ss z";
	public static final String AUTH_TYPE = "HmacSHA1";

	public static String get(String url) throws Exception {
		return get(url, null, null);
	}

	public static String get(String url, String secretId, String secretKey) throws Exception {

		Request req = Request.Get(url);
		req.addHeader(new BasicHeader("contentType", CHARSET));
		req.addHeader(new BasicHeader("Accept-Charset", CHARSET));
		if (secretId != null && secretKey != null) {
			buildRequestHeader(req, secretId, secretKey);
		}
		Response respon = req.execute();
		return respon.returnContent().asString();

	}

	public static String post(String url) throws Exception {
		return post(url, null, null, null);
	}

	public static String post(String url, Map<String, String> map) throws Exception {
		return post(url, null, null, map);
	}

	public static String post(String url, String secretId, String secretKey, Map<String, String> map) throws Exception {

		Request req = Request.Post(url);
		req.addHeader(new BasicHeader("contentType", CHARSET));
		req.addHeader(new BasicHeader("Accept-Charset", CHARSET));

		if (secretId != null && secretKey != null) {
			buildRequestHeader(req, secretId, secretKey);
		}

		Form form = Form.form();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			form.add(entry.getKey(), entry.getValue());
		}

		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form.build(), CHARSET);
		req.body(entity);

		Response respon = req.execute();
		return respon.returnContent().asString();

	}

	public static void buildRequestHeader(Request req, String secretId, String secretKey) throws Exception {
		Mac hmacSha1 = Mac.getInstance(AUTH_TYPE);
		byte[] keyBytes = secretKey.getBytes();
		hmacSha1.init(new SecretKeySpec(keyBytes, AUTH_TYPE));
		SimpleDateFormat sdf = new SimpleDateFormat(ZONE_TIME, Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
		String dateStr = sdf.format(new Date());
		String singSrc = "date: " + dateStr;
		String singStr = new String(Base64.encode(hmacSha1.doFinal(singSrc.getBytes())));

		String authorization = "hmac username=\"" + secretId
				+ "\", algorithm=\"hmac-sha1\", headers=\"date\", signature=\"" + singStr + "\"";
		req.addHeader(new BasicHeader("date", dateStr));
		req.addHeader(new BasicHeader("Authorization", authorization));
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("appId", "10001");
		params.put("channelCode", "00375");
//		params.put("customId", "15813819958");
		String secretId = "063758ad25b94dbc9ff0622b9ee83473";
		String secretKy = "fcec5f0469db486ea2a5ba67298603cd";
		String result = APIClient.post("http://api.giiso.ai/openapi/standard/news", secretId,secretKy, params);
		System.out.println(result);
		JSONObject jresult = JSON.parseObject(result);
		if(jresult!=null){
			int code = jresult.getIntValue("code");
			if(code == 0){
				JSONObject jdata = jresult.getJSONObject("data");
				JSONArray jnews = jdata.getJSONArray("result");
				if(jnews!=null){
					Iterator<Object> tor=jnews.iterator();
					while(tor.hasNext()) {
						JSONObject jnew = (JSONObject)tor.next();
						String title = jnew.getString("title");
						int imagenum = jnew.getIntValue("imagenum");
						String imageurl = null;
						if(imagenum>=1){
							imageurl = jnew.getJSONArray("image").getString(0);
						}

						JSONArray jtags = jnew.getJSONArray("tags");
						if(jtags!=null){
							Iterator<Object> jtag = jtags.iterator();
							while(jtag.hasNext()){
								String tags = (String) jtag.next();
							}
						}

						String _id = jnew.getString("_id");

//						Map<String, String> params1 = new HashMap<>();
//						params1.put("id", _id);
//						String result1 = APIClient.post("http://api.giiso.ai/openapi/news/detail", secretId,secretKy,params1);
//						System.out.println(result1);
//						JSONObject jresultdetail = JSON.parseObject(result1);
//						JSONObject jdatadetail = jresultdetail.getJSONObject("data");
//
//						String summary = jdatadetail.getString("summary");
//						String textcontent = jdatadetail.getString("textcontent");
//						String article = jdatadetail.getString("article");

					}
				}
			}
		}
	}
}
