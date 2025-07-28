package com.minigod.zero.biz.common.utils;

import com.minigod.zero.core.tool.utils.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;


import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * sunline
 */
@Slf4j
public class UrlReader {

	public static <T> List<T> readHtmlTable(String path, Class<T> clazz, Map<String, String> headMap, int offsetRows, int tailRows, String charSet) throws Exception {
		return readHtmlTable(path, clazz, headMap, offsetRows, tailRows, charSet, null);
	}

	public static <T> List<T> readHtmlTable(String path, Class<T> clazz, Map<String, String> headMap, int offsetRows, int tailRows, String charSet, Map<String, String> tableFilter) throws Exception {
		List<T> rtnList = new ArrayList<>();
		List<String> tableFields = new ArrayList<>();
		if (StringUtils.isNotEmpty(path) && null != clazz && MapUtils.isNotEmpty(headMap)) {
			if (offsetRows < 0 || tailRows < 0) {
				throw new IllegalArgumentException();
			} else {
				String fullPath = URLDecoder.decode(UrlReader.class.getResource(path).getPath());
				File file = new File(fullPath);
				BufferedReader buffReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charSet));
				rtnList = readHtmlTableFromInputStream(new FileInputStream(file), clazz, headMap, offsetRows, tailRows, charSet, tableFilter);
			}
		}
		log.debug("解析HTML表结构数据完成，总记录数" + rtnList.size());
		return rtnList;
	}

	public static <T> List<T> readHtmlTableFromUrl(String strUrl, Class<T> clazz, Map<String, String> headMap,
												   int offsetRows, int tailRows, Map<String, String> tableFilter, String charset) throws Exception {
		List<T> rtnList = new ArrayList<>();
		List<String> tableFields = new ArrayList<>();
		if (StringUtils.isNotEmpty(strUrl) && null != clazz) {
			if (offsetRows < 0 || tailRows < 0) {
				throw new IllegalArgumentException();
			} else {
				//创建HttpClientBuilder
				HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
				//HttpClient
				CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
				InputStream is = readUrl(closeableHttpClient,strUrl);
				if (null != is) {
					rtnList = readHtmlTableFromInputStream(is, clazz, headMap, offsetRows, tailRows, charset, tableFilter);
				}
				closeableHttpClient.close();
			}
		}
		log.debug("解析HTML表结构数据完成，总记录数" + rtnList.size());
		return rtnList;
	}

	public static NodeList getHtmlPraser(String strUrl, String charSet, final Map<String,String> filterS , final Class<? extends TagNode> tagClazz){

		if (StringUtils.isNotEmpty(strUrl)) {
			try {
				//创建HttpClientBuilder
				HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
				//HttpClient
				CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
				InputStream is = readNewsUrl(closeableHttpClient,strUrl);
				BufferedReader buffReader = new BufferedReader(new InputStreamReader(is, charSet));
				StringBuffer strBuff = new StringBuffer();
				String line;
				while ((line = buffReader.readLine()) != null) {
					strBuff.append(line.replaceAll("<tbody>", "").replaceAll("</tbody>", "").replaceAll("<b>","").replaceAll("</b>",""));
				}
				log.debug("读取HTML数据完成，开始解析Html数据表结构");
				Parser parser = new Parser();
				parser.setEncoding(charSet);
				parser.setInputHTML(strBuff.toString());
				closeableHttpClient.close();
				return parser.parse(new NodeFilter() {
					@Override
					public boolean accept(Node node) {
						if (node.getClass().getSimpleName().equals(tagClazz.getSimpleName())) {
							TagNode tagNode = (TagNode) node;
							if (MapUtils.isNotEmpty(filterS)) {
								Set<Map.Entry<String, String>> entrySet = filterS.entrySet();
								Iterator<Map.Entry<String, String>> it = entrySet.iterator();
								Map.Entry<String, String> entry;
								while (it.hasNext()) {
									entry = it.next();
									if (tagNode.getAttribute(entry.getKey()) != null)
										if (entry.getValue().equals(tagNode.getAttribute(entry.getKey())))
											return true;
								}
							} else {
								return true;
							}
						}
						return false;
					}
				});
			}catch (Exception e) {
				log.error("读取URL失败！"+strUrl,e);
			}
		}
		return null;
	}

	/**
	 * 发送post请求
	 * @param strUrl url地址
	 * @param headerList http头
	 * @param params post参数
	 * @return
	 */
	public static String post(CloseableHttpClient httpClient, String strUrl, List<Header> headerList, List<? extends NameValuePair> params) {
//        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		try {
			HttpPost post = new HttpPost(strUrl);
			post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
			// Header
			if (headerList != null) {
				for (Header header : headerList) {
					post.addHeader(header);
				}
			}
			// parameters
			if (params != null) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
				post.setEntity(entity);
			}

			HttpResponse httpResponse = httpClient.execute(post);
			HttpEntity entity = httpResponse.getEntity();
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(entity, "UTF-8");
				return content;
			} else {
				EntityUtils.consumeQuietly(entity);
			}
		}catch (Exception e){
			log.error("读取URL失败！"+strUrl, e);
		}
		return null;
	}

	public static List<BasicNameValuePair> buildParams(String paramStr) {
		if (paramStr == null) {
			return null;
		}
		List<BasicNameValuePair> list = new ArrayList<>();
		//
		String[] kvArr = paramStr.split("&");
		for (String kv : kvArr) {
			if (kv == null)
				continue;
			kv = kv.trim();
			if (kv.length() <= 0)
				continue;
			String[] ss = kv.split("=");
			String key = ss[0];
			String value = "";
			if (ss.length > 1) {
				value = ss[1];
			}
			//
			BasicNameValuePair nvp = new BasicNameValuePair(key, value);
			list.add(nvp);
		}
		return list;
	}

	public static boolean getStream(CloseableHttpClient httpClient, String strUrl, List<Header> headerList, OutputStream output) {
		try {
			HttpGet get = new HttpGet(strUrl);
			get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
			if (headerList != null) {
				for (Header header : headerList) {
					get.addHeader(header);
				}
			}
			HttpResponse httpResponse = httpClient.execute(get);
			HttpEntity entity = httpResponse.getEntity();
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 200
				InputStream is = entity.getContent();
				try {
					while (true) {
						int b = is.read();
						if (b == -1) {
							break;
						}
						output.write(b);
					}
				} finally {
					EntityUtils.consumeQuietly(entity);
				}
				return true;
			} else {
				EntityUtils.consumeQuietly(entity);
			}
		} catch (Exception e){
			log.error("读取URL失败！"+strUrl, e);
		}
		return false;
	}

	public static String get(CloseableHttpClient httpClient, String strUrl, List<Header> headerList) {
//      CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		try {
			HttpGet get = new HttpGet(strUrl);
			get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
			// Header
			if (headerList != null) {
				for (Header header : headerList) {
					get.addHeader(header);
				}
			}
			HttpResponse httpResponse = httpClient.execute(get);
			HttpEntity entity = httpResponse.getEntity();
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(entity, "UTF-8");
				return content;
			} else {
				EntityUtils.consumeQuietly(entity);
			}
		}catch (Exception e){
			log.error("读取URL失败！"+strUrl, e);
		}
		return null;
	}

	public static  InputStream readUrl(CloseableHttpClient closeableHttpClient,String strUrl){
		try {
			HttpGet get = new HttpGet(strUrl);
			HttpResponse httpResponse = closeableHttpClient.execute(get);
			HttpEntity entity = httpResponse.getEntity();
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				return entity.getContent();
			}
		}catch (Exception e){
			log.error("读取URL失败！",e);
		}
		return null;
	}

	/************by joy start*******************/
	public static  InputStream readNewsUrl(CloseableHttpClient closeableHttpClient,String strUrl){
		HttpGet get = null;
		CloseableHttpResponse httpResponse = null;
		try {
			get = new HttpGet(strUrl);
			setHttpGet(get);
			httpResponse = closeableHttpClient.execute(get);
			HttpEntity entity = httpResponse.getEntity();
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				return entity.getContent();
			}
		}catch (Exception e){
			log.error("读取URL失败！"+strUrl,e);
		}finally{
//        	releaseResource(closeableHttpClient,httpResponse);
//        	get.releaseConnection();
		}
		return null;
	}

	/**
	 * 设置HttpGet超时参数
	 * @param httpGet
	 * @return
	 */
	private static HttpGet setHttpGet (HttpGet httpGet) {

		RequestConfig requestConfig = RequestConfig.custom()
			//连接上一个url，获取response的返回等待时间
			.setSocketTimeout(20000)
			//连接一个url的连接等待时间
			.setConnectTimeout(20000)
			.setConnectionRequestTimeout(20000).build();
		httpGet.setConfig(requestConfig);
		return httpGet;
	}

	/**
	 * 释放资源
	 * @param httpclient
	 * @param response
	 */
	private static void releaseResource (CloseableHttpClient httpclient,CloseableHttpResponse response) {
		//关闭连接,释放资源
		if ( null != response ) {
			try {
				response.close();
			} catch (Exception e) {
				log.error("response.close() occur exception.", e);
			}
		}
		if ( null != httpclient ) {
			try {
				httpclient.close();
			} catch (Exception e) {
				log.error("httpclient.close() occur exception.", e);
			}
		}
	}
	/************by joy end*******************/

	public static  InputStream readUrl(String strUrl){
		try {
			HttpResponse httpResponse = readAndGetResponse(strUrl);
			if(httpResponse != null){
				HttpEntity entity = httpResponse.getEntity();
				String content = null;
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					return entity.getContent();
				}
			}
		}catch (Exception e){
			log.error("读取URL失败！",e);
		}
		return null;
	}

	public static HttpResponse readAndGetResponse(String strUrl){
		try{
			HttpGet get = new HttpGet(strUrl);
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
			//HttpClient
			CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
			return closeableHttpClient.execute(get);
		}catch (Exception e){
			log.error("读取URL失败！url="+strUrl,e);
		}
		return null;
	}

	public static HttpResponse getHttpResponse(String strUrl){
		CloseableHttpClient httpclient;
		try {
			SSLContext sslContext = SSLContext.getDefault();
			SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(
				sslContext,
				new String[]{"TLSv1.2", "TLSv1"},
//		        new String[]{"TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256"},
				null,
				NoopHostnameVerifier.INSTANCE);
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", sslConnectionFactory)
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.build();

			HttpClientConnectionManager ccm = new BasicHttpClientConnectionManager(registry);
			httpclient = HttpClientBuilder.create()
				.setSSLSocketFactory(sslConnectionFactory)
				.setConnectionManager(ccm)
				.build();
//		httpclient = getHttpClient();

			HttpPost httpPost = new HttpPost(strUrl);
			CloseableHttpResponse resp = httpclient.execute(httpPost);
			return resp;
		} catch (Exception e) {
			log.error("读取URL失败！url="+strUrl,e);
		}
		return null;
	}

	public static String readStringFromUrl(String strUrl, String charSet) {
		if (StringUtils.isNotEmpty(strUrl)) {
			BufferedReader buffReader = null;
			try {
				//HttpClient
				CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
				InputStream inputStream = readUrl(closeableHttpClient,strUrl);
				if (null != inputStream) {
					buffReader = new BufferedReader(new InputStreamReader(inputStream, charSet));
					StringBuffer strBuff = new StringBuffer();
					String line;
					while ((line = buffReader.readLine()) != null) {
						strBuff.append(line);
					}
					if (null != strBuff && strBuff.length() > 0) {
						return strBuff.toString();
					}
				}
				closeableHttpClient.close();
			} catch (Exception e) {
				log.error("readStringFromUrl error", e);
			} finally {
				if (null != buffReader) {
					try {
						buffReader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}


	private static <T> List<T> readHtmlTableFromInputStream(InputStream inputStream, Class<T> clazz,
															Map<String, String> headMap, int offsetRows, int tailRows, String charSet,
															final Map<String, String> tableFilter) throws Exception {
		List<T> rtnList = new ArrayList<>();
		List<String> tableFields = new ArrayList<>();
		log.debug("开始读取html数据");
		BufferedReader buffReader = null;
		try {
			buffReader = new BufferedReader(new InputStreamReader(inputStream, charSet));
			StringBuffer strBuff = new StringBuffer();
			String line;
			while ((line = buffReader.readLine()) != null) {
				strBuff.append(line.replaceAll("<tbody>", "").replaceAll("</tbody>", "").replaceAll("<b>","").replaceAll("</b>",""));
			}
			log.debug("读取HTML数据完成，开始解析Html数据表结构");
			Parser parser = new Parser();
			parser.setEncoding(charSet);
			parser.setInputHTML(strBuff.toString());
			NodeList tables = parser.parse(new NodeFilter() {
				@Override
				public boolean accept(Node node) {
					boolean isValid = false;
					if (node instanceof TableTag) {
						TableTag tableTag = (TableTag) node;
						if (MapUtils.isNotEmpty(tableFilter)) {
							Set<Map.Entry<String, String>> entrySet = tableFilter.entrySet();
							Iterator<Map.Entry<String, String>> it = entrySet.iterator();
							Map.Entry<String, String> entry;
							while (it.hasNext()) {
								entry = it.next();
								if (tableTag.getAttribute(entry.getKey()) != null)
									if ((tableTag.getAttribute(entry.getKey()).equals(entry.getValue())))
										isValid = true;
							}
						} else {
							isValid = true;
						}
					}
					return isValid;
				}
			});

			for (int i = 0; i < tables.size(); i++) {
				TableTag table = (TableTag) tables.elementAt(i);
				TableRow[] rows = table.getRows();
				int columnLen = rows[0].getColumnCount();
				if(columnLen < headMap.size()){
					continue;
				}
				for (int rowId = offsetRows; rowId < rows.length - tailRows; rowId++) {
					TableRow row = rows[rowId];
					TableColumn[] columns = row.getColumns();
					int colSize = null == headMap ? columnLen : headMap.size();
					T obj = clazz.newInstance();

					//读取表头
					if (rowId == 0) {
						if (MapUtils.isNotEmpty(headMap)) {
							for (int col = 0; col < colSize; col++) {
								tableFields.add(HtmlParserHelper.getText(columns[col]));
                                /*NodeList tdChildren = columns[col].getChildren();
                                for (int tdi = 0; tdi < tdChildren.size(); tdi++) {
                                    if (tdChildren.elementAt(tdi) instanceof TextNode) {
                                        tableFields.add(tdChildren.elementAt(tdi).getText().replaceAll(" ", ""));
                                    }
                                }*/
							}
						} else {
							for (int col = 0; col < colSize; col++) {
								tableFields.add("col" + col);
							}
						}

					} else {
						for (int col = 0; col < colSize; col++) {
							NodeList tdChildren = columns[col].getChildren();
							if (tdChildren != null) {
								String fieldVal = null;
								String fieldName = headMap != null ? headMap.get(tableFields.get(col)) : tableFields.get(col);
								Method setMethod = ReflectUtils.getSetMethod(clazz, clazz.getDeclaredField(fieldName));
								fieldVal = "";
								for (int tdi = 0; tdi < tdChildren.size(); tdi++) {
									fieldVal = fieldVal + HtmlParserHelper.getText(tdChildren.elementAt(tdi));
                                    /*if (tdChildren.elementAt(tdi) instanceof TextNode) {
                                        if (tdChildren.elementAt(tdi).getText() != null) {
                                            fieldVal = fieldVal + tdChildren.elementAt(tdi).getText().replaceAll("&nbsp;", "").replaceAll("&amp;","&");
                                        }
                                    } else if (tdChildren.elementAt(tdi) instanceof LinkTag) {
                                        if (tdChildren.elementAt(tdi).getFirstChild().getText() != null) {
                                            fieldVal = fieldVal + tdChildren.elementAt(tdi).getFirstChild().getText().replaceAll("&nbsp;", "").replaceAll("&amp;","&");
                                        }
                                    }*/
									setMethod.invoke(obj, fieldVal);
								}
							}

						}
						rtnList.add(obj);
					}

				}
			}
		} finally {
			if (null != buffReader) {
				buffReader.close();
			}
		}
		log.debug("解析Html数据表结构完成，总共获取数据条数：" + rtnList);
		return rtnList;
	}

	public static <T> List<T> readObjectListFromTextFile(String path, Class<T> clazz, Map<String, String> headMap,
														 int offsetRows, String charSet, String spliter) throws Exception {
		List<T> rtnList = new ArrayList<>();
		List<String> tableFields = new ArrayList<>();
		if (StringUtils.isNotEmpty(path) && null != clazz && MapUtils.isNotEmpty(headMap)) {
			if (offsetRows < 0) {
				throw new IllegalArgumentException();
			} else {
				String fullPath = URLDecoder.decode(UrlReader.class.getResource(path).getPath());
				File file = new File(fullPath);
				log.debug("开始读取纯文本文件：" + file.getAbsolutePath());
				BufferedReader buffReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charSet));
				String line;
				int lineNum = 0;
				while ((line = buffReader.readLine()) != null
					&& lineNum >= offsetRows
					&& line.trim().length() > 0) {
					//读取表头
					if (lineNum == offsetRows) {
						String[] fields = line.split(spliter);
						for (String field : fields) {
							tableFields.add(headMap.get(field.replaceAll(" ", "").trim()));
						}
					} else {
						String[] values = line.split(spliter);
						T obj = clazz.newInstance();
						for (int i = 0; i < tableFields.size(); i++) {
							String fieldName = tableFields.get(i);
							Method setMethod = ReflectUtils.getSetMethod(clazz, clazz.getDeclaredField(fieldName));
							setMethod.invoke(obj, values[i]);
						}
						rtnList.add(obj);
					}
					lineNum++;
				}
				buffReader.close();
				log.debug("读取纯文本文件完成，开始解析纯文本文件表结构");
			}
		}
		return rtnList;
	}

	public static CloseableHttpClient getHttpClient(){
		SSLContext sslcontext;
		try {
			sslcontext = SSLContexts.custom()
				// .loadTrustMaterial(new File("my.keystore"),
				// "nopassword".toCharArray(),
				// new TrustSelfSignedStrategy())
				.build();
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext, new String[] { "TLSv1.2", "TLSv1" }, null,
				SSLConnectionSocketFactory.getDefaultHostnameVerifier());
			CloseableHttpClient closeableHttpClient = HttpClients.custom()
				.setSSLSocketFactory(sslsf).build();
			return closeableHttpClient;
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient closeableHttpClient2 = httpClientBuilder.build();
		return closeableHttpClient2;

	}

	public class SSLClient extends DefaultHttpClient {
		public SSLClient() throws Exception {
			super();
			SSLContext ctx = SSLContext.getInstance("TLSv1.2");
			X509TrustManager tm = new X509TrustManager() {
				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(X509Certificate[] chain,
											   String authType) throws CertificateException {

				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			org.apache.http.conn.ssl.SSLSocketFactory ssf = new org.apache.http.conn.ssl.SSLSocketFactory(ctx,
				org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = this.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", 443, ssf));
		}
	}


}
