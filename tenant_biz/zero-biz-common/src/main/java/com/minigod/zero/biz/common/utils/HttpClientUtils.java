//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.minigod.zero.biz.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
    private static final ThreadLocal<Map<String, SNHttpClient>> threadLocal = new ThreadLocal();
    private static final long expiredTime = 900000L;
    private static long lastCloseTime = 0L;
    private static long interval = 60000L;

    public HttpClientUtils() {
    }

    public static final String get(String url, Charset charset, boolean... releaseConnnection) {
        String res = get((String)url, (Map)null, (Map)null, charset, releaseConnnection);
        return res;
    }

    public static final String get(HttpHost proxy, String url, Charset charset, boolean... releaseConnnection) {
        String res = get(proxy, url, (Map)null, (Map)null, charset, releaseConnnection);
        return res;
    }

    public static final String get(String url, Map<String, String> params, Charset charset, boolean... releaseConnnection) {
        String res = get((String)url, (Map)params, (Map)null, charset, releaseConnnection);
        return res;
    }

    public static final String get(HttpHost proxy, String url, Map<String, String> params, Charset charset, boolean... releaseConnnection) {
        String res = get(proxy, url, params, (Map)null, charset, releaseConnnection);
        return res;
    }

    public static final String get(String url, Map<String, String> params, Map<String, String> headers, Charset charset, boolean... releaseConnnection) {
        HttpResponse response = null;
        String content = null;

        try {
            response = get4Response(url, params, headers, charset);
            content = EntityUtils.toString(response.getEntity(), charset);
        } catch (IOException var11) {
            closeHttpClient(url);
            throw handlerException("http get for html(url=" + url + ",params=" + params + ",headers=" + headers + ")", var11);
        } finally {
            closeResponse(response);
            if (releaseConnnection.length == 0 || releaseConnnection[0]) {
                closeHttpClient(url);
            }

        }

        if (logger.isDebugEnabled()) {
            logger.debug("url=" + url + ",res=" + content);
        }

        return content;
    }

    public static final String get(HttpHost proxy, String url, Map<String, String> params, Map<String, String> headers, Charset charset, boolean... releaseConnnection) {
        HttpResponse response = null;
        String content = null;

        try {
            response = get4Response(proxy, url, params, headers, charset);
            content = EntityUtils.toString(response.getEntity(), charset);
        } catch (IOException var12) {
            closeHttpClient(url);
            throw handlerException("http get for html(proxy=" + proxy + ",url=" + url + ",params=" + params + ",headers=" + headers + ")", var12);
        } finally {
            closeResponse(response);
            if (releaseConnnection.length == 0 || releaseConnnection[0]) {
                closeHttpClient(url);
            }

        }

        if (logger.isDebugEnabled()) {
            logger.debug("url=" + url + ",res=" + content);
        }

        return content;
    }

    private static final boolean isEmptyCollection(Map<?, ?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static final HttpResponse get4Response(HttpHost proxy, String url, Map<String, String> params, Map<String, String> headers, Charset charset) throws IOException {
        HttpResponse response = null;
        HttpGet get = null;

        try {
            HttpClient client = getHttpClient(url);
            if (proxy != null) {
                client.getParams().setParameter("http.route.default-proxy", proxy);
            }

            if (!isEmptyCollection(params)) {
                String paramsStr = "?" + URLEncodedUtils.format(getNameValuePairsFromMap(params), charset);
                get = new HttpGet(url + paramsStr);
                if (logger.isDebugEnabled()) {
                    logger.debug(url + paramsStr);
                }
            } else {
                get = new HttpGet(url);
            }

            if (headers != null) {
                Iterator var15 = headers.entrySet().iterator();

                while(var15.hasNext()) {
                    Entry<String, String> header = (Entry)var15.next();
                    get.addHeader((String)header.getKey(), (String)header.getValue());
                }
            }

            response = client.execute(get);
        } catch (Exception var13) {
            closeResponse(response);
            closeHttpClient(url);
            if (get != null) {
                get.releaseConnection();
            }

            throw new RuntimeException("close response.", var13);
        } finally {
            ;
        }

        if (response.getStatusLine().getStatusCode() != 200) {
            logger.error("http get status: " + response.getStatusLine().getStatusCode() + ",reason:" + response.getStatusLine().getReasonPhrase());
        }

        if (logger.isDebugEnabled()) {
            logger.debug("url=" + url + ",response=" + response);
        }

        return response;
    }

    public static final HttpResponse get4Response(String url, Map<String, String> params, Map<String, String> headers, Charset charset) throws IOException {
        return get4Response((HttpHost)null, url, params, headers, charset);
    }

    public static final HttpResponse get4Response(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        return get4Response((HttpHost)null, url, params, headers, Charset.forName("UTF-8"));
    }

    public static final String post(String url, Charset charset, boolean... releaseConnnection) {
        String res = post(url, (Map)null, (Map)null, charset, releaseConnnection);
        return res;
    }

    public static final String post(String url, boolean... releaseConnnection) {
        String res = post(url, (Map)null, (Map)null, Charset.forName("UTF-8"), releaseConnnection);
        return res;
    }

    public static final String post(String url, Map<String, String> params, Charset charset, boolean... releaseConnnection) {
        String res = post(url, params, (Map)null, charset, releaseConnnection);
        return res;
    }

    public static final String post(String url, Map<String, String> params, boolean... releaseConnnection) {
        String res = post(url, params, (Map)null, Charset.forName("UTF-8"), releaseConnnection);
        return res;
    }

    public static final String postJson(String url, String json, Charset charset, boolean... releaseConnnection) {
        String res = postJson(url, json, (Map)null, charset, releaseConnnection);
        return res;
    }

    public static final String postXml(String url, String xml, Charset charset, boolean... releaseConnnection) {
        String res = postXml(url, xml, (Map)null, charset, releaseConnnection);
        return res;
    }

    public static final String postJson(String url, String json, boolean... releaseConnnection) {
        String res = postJson(url, json, (Map)null, Charset.forName("UTF-8"), releaseConnnection);
        return res;
    }

    public static final String postXml(String url, String xml, boolean... releaseConnnection) {
        String res = postXml(url, xml, (Map)null, Charset.forName("UTF-8"), releaseConnnection);
        return res;
    }

    public static final String post(String url, Map<String, String> params, Map<String, String> headers, boolean... releaseConnnection) {
        return post(url, params, Charset.forName("UTF-8"), releaseConnnection);
    }

    public static final String post(String url, Map<String, String> params, Map<String, String> headers, Charset charset, boolean... releaseConnnection) {
        String res = post((HttpHost)null, url, params, headers, charset, releaseConnnection);
        return res;
    }

    public static final String postJson(String url, String json, Map<String, String> headers, Charset charset, boolean... releaseConnnection) {
        String res = postJson((HttpHost)null, url, json, headers, charset, releaseConnnection);
        return res;
    }

    public static final String postXml(String url, String xml, Map<String, String> headers, Charset charset, boolean... releaseConnnection) {
        String res = postXml((HttpHost)null, url, xml, headers, charset, releaseConnnection);
        return res;
    }

    public static final String postJson(String url, String json, Map<String, String> headers, boolean... releaseConnnection) {
        String res = postJson((HttpHost)null, url, json, headers, Charset.forName("UTF-8"), releaseConnnection);
        return res;
    }

    public static final String postXml(String url, String xml, Map<String, String> headers, boolean... releaseConnnection) {
        String res = postXml((HttpHost)null, url, xml, headers, Charset.forName("UTF-8"), releaseConnnection);
        return res;
    }

    public static final String post(HttpHost proxy, String url, Map<String, String> params, Map<String, String> headers, Charset charset, boolean... releaseConnnection) {
        HttpResponse response = null;
        String content = null;

        try {
            response = post4Response(proxy, url, params, headers, charset);
            content = EntityUtils.toString(response.getEntity(), charset);
        } catch (IOException var12) {
            closeHttpClient(url);
            throw handlerException("http post for html(proxy=" + proxy + ",url=" + url + ",params=" + params + ",headers=" + headers + ")", var12);
        } finally {
            closeResponse(response);
            if (releaseConnnection.length == 0 || releaseConnnection[0]) {
                closeHttpClient(url);
            }

        }

        if (logger.isDebugEnabled()) {
            logger.debug("url=" + url + ",res=" + content);
        }

        return content;
    }

    public static final String postJson(HttpHost proxy, String url, String json, Map<String, String> headers, Charset charset, boolean... releaseConnnection) {
        HttpResponse response = null;

        String var8;
        try {
            response = postJsonResponse(proxy, url, json, headers, charset);
            String content = EntityUtils.toString(response.getEntity(), charset);
            if (logger.isDebugEnabled()) {
                logger.debug("url=" + url + ",res=" + content);
            }

            var8 = content;
        } catch (Exception var12) {
            closeHttpClient(url);
            throw handlerException("post json(proxy=" + proxy + ",url=" + url + ",params=" + json + ",headers=" + headers + ")", var12);
        } finally {
            closeResponse(response);
            if (releaseConnnection.length == 0 || releaseConnnection[0]) {
                closeHttpClient(url);
            }

        }

        return var8;
    }

    public static final String postXml(HttpHost proxy, String url, String xml, Map<String, String> headers, Charset charset, boolean... releaseConnnection) {
        HttpResponse response = null;
        String content = null;

        try {
            response = postXmlResponse(proxy, url, xml, headers, charset);
            content = EntityUtils.toString(response.getEntity(), charset);
        } catch (IOException var12) {
            closeHttpClient(url);
            throw handlerException("post xml(proxy=" + proxy + ",url=" + url + ",params=" + xml + ",headers=" + headers + ")", var12);
        } finally {
            closeResponse(response);
            if (releaseConnnection.length == 0 || releaseConnnection[0]) {
                closeHttpClient(url);
            }

        }

        if (logger.isDebugEnabled()) {
            logger.debug("url=" + url + ",res=" + content);
        }

        return content;
    }

    public static final HttpResponse post4Response(HttpHost proxy, String url, Map<String, String> params, Map<String, String> headers, Charset charset) {
        HttpResponse response = null;

        try {
            HttpClient client = getHttpClient(url);
            if (proxy != null) {
                client.getParams().setParameter("http.route.default-proxy", proxy);
            }

            HttpPost post = new HttpPost(url);
            if (!isEmptyCollection(params)) {
                post.setEntity(new UrlEncodedFormEntity(getNameValuePairsFromMap(params), charset));
            }

            if (headers != null) {
                Iterator var8 = headers.entrySet().iterator();

                while(var8.hasNext()) {
                    Entry<String, String> header = (Entry)var8.next();
                    post.setHeader((String)header.getKey(), (String)header.getValue());
                }
            }

            response = client.execute(post);
            if (logger.isDebugEnabled()) {
                logger.debug("url=" + url + ",response=" + response);
            }

            return response;
        } catch (IOException var10) {
            closeHttpClient(url);
            closeResponse(response);
            throw handlerException("post to response(proxy=" + proxy + ",url=" + url + ",params=" + params + ",headers=" + headers + ")", var10);
        }
    }

    public static final HttpResponse postJsonResponse(HttpHost proxy, String url, String json, Map<String, String> headers, Charset charset) {
        HttpResponse response = null;

        try {
            DefaultHttpClient client = getHttpClient(url);
            if (proxy != null) {
                client.getParams().setParameter("http.route.default-proxy", proxy);
            }

            HttpPost post = new HttpPost(url);
            if (StringUtils.isNotEmpty(json)) {
                StringEntity se = new StringEntity(json, charset);
                se.setContentType(ContentType.APPLICATION_JSON.getMimeType());
                post.setEntity(se);
            }

            if (headers != null) {
                Iterator var11 = headers.entrySet().iterator();

                while(var11.hasNext()) {
                    Entry<String, String> header = (Entry)var11.next();
                    post.setHeader((String)header.getKey(), (String)header.getValue());
                }
            }

            response = client.execute(post);
            if (logger.isDebugEnabled()) {
                logger.debug("url=" + url + ",params=" + json + ",response=" + response);
            }

            return response;
        } catch (IOException var10) {
            closeHttpClient(url);
            closeResponse(response);
            throw handlerException("post json response(proxy=" + proxy + ",url=" + url + ",params=" + json + ",headers=" + headers + ")", var10);
        }
    }

    public static final HttpResponse postXmlResponse(HttpHost proxy, String url, String json, Map<String, String> headers, Charset charset) {
        HttpResponse response = null;

        try {
            HttpClient client = getHttpClient(url);
            if (proxy != null) {
                client.getParams().setParameter("http.route.default-proxy", proxy);
            }

            HttpPost post = new HttpPost(url);
            if (StringUtils.isNotEmpty(json)) {
                StringEntity se = new StringEntity(json, charset);
                se.setContentType(ContentType.TEXT_XML.getMimeType());
                post.setEntity(se);
            }

            if (headers != null) {
                Iterator var11 = headers.entrySet().iterator();

                while(var11.hasNext()) {
                    Entry<String, String> header = (Entry)var11.next();
                    post.setHeader((String)header.getKey(), (String)header.getValue());
                }
            }

            response = client.execute(post);
            if (logger.isDebugEnabled()) {
                logger.debug("url=" + url + ",params=" + json + ",response=" + response);
            }

            return response;
        } catch (IOException var10) {
            closeHttpClient(url);
            closeResponse(response);
            throw handlerException("post xml response(proxy=" + proxy + ",url=" + url + ",params=" + json + ",headers=" + headers + ")", var10);
        }
    }

    public static final HttpResponse post4Response(String url, Map<String, String> params, Map<String, String> headers, Charset charset) {
        return post4Response((HttpHost)null, url, params, headers, charset);
    }

    private static final List<NameValuePair> getNameValuePairsFromMap(Map<String, String> params) {
        List<NameValuePair> pairs = new ArrayList();
        if (!isEmptyCollection(params)) {
            Iterator var2 = params.entrySet().iterator();

            while(var2.hasNext()) {
                Entry<String, String> e = (Entry)var2.next();
                pairs.add(new BasicNameValuePair((String)e.getKey(), (String)e.getValue()));
            }
        }

        return pairs;
    }

    public static final String getHostAndPort(String url) {
        String host = url;
        if (url.startsWith("http://")) {
            host = url.substring(7);
        }

        if (host.contains("/")) {
            host = host.substring(0, host.indexOf("/"));
        }

        if (logger.isDebugEnabled()) {
            logger.debug(url + ">>>host>>>" + host);
        }

        return host;
    }

    private static final boolean isExpired(HttpClientUtils.SNHttpClient SNHttpClient) {
        return System.currentTimeMillis() - SNHttpClient.getAccessTime() > 900000L;
    }

    private static final void checkAndProcessConnections() {
        final Map<String, SNHttpClient> clients = (Map)threadLocal.get();
        if (clients != null && System.currentTimeMillis() - lastCloseTime > interval) {
            (new Thread(new Runnable() {
                public void run() {
                    HttpClientUtils.lastCloseTime = System.currentTimeMillis();
                    Iterator var1 = clients.entrySet().iterator();

                    while(var1.hasNext()) {
                        Entry<String, SNHttpClient> en = (Entry)var1.next();
                        HttpClientUtils.SNHttpClient c = (HttpClientUtils.SNHttpClient)en.getValue();
                        c.getHttpClient().getConnectionManager().closeExpiredConnections();
                        if (HttpClientUtils.logger.isDebugEnabled()) {
                            HttpClientUtils.logger.debug("close expired connections.");
                        }
                    }

                }
            })).start();
        }

    }

    private static final RuntimeException handlerException(String msg, Exception exception) {
        return new RuntimeException(msg, exception);
    }

    private static final DefaultHttpClient getHttpClient(String url) {
        checkAndProcessConnections();
        String host = getHostAndPort(url);
        Map<String, SNHttpClient> clients = (Map)threadLocal.get();
        if (clients == null) {
            clients = new ConcurrentHashMap();
            threadLocal.set(clients);
        }

        HttpClientUtils.SNHttpClient snClient = (HttpClientUtils.SNHttpClient)((Map)clients).get(host);
        if (snClient == null) {
            snClient = new HttpClientUtils.SNHttpClient();
        }

        boolean fromCache = true;
        DefaultHttpClient client = snClient.getHttpClient();
        if (client != null && !isExpired(snClient)) {
            client = snClient.getHttpClient();
        } else {
            client = new DefaultHttpClient();
            fromCache = false;
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 50000);
			client.getParams().setParameter(CoreConnectionPNames.SO_KEEPALIVE, true);
			client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
            snClient.setHttpClient(client);
            ((Map)clients).put(host, snClient);
        }

        client.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(HttpResponse response, HttpContext context) throws HttpException, IOException {
                HttpEntity entity = response.getEntity();
                Header ceheader = entity.getContentEncoding();
                if (ceheader != null) {
                    HeaderElement[] codecs = ceheader.getElements();

                    for(int i = 0; i < codecs.length; ++i) {
                        if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                            response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                            return;
                        }
                    }
                }

            }
        });
        snClient.setAccessTime(System.currentTimeMillis());
        if (fromCache) {
            if (logger.isDebugEnabled()) {
                logger.debug("get httpClient[" + client + "] from cache.===current thread cache route's size=" + ((Map)clients).size());
            }
        } else if (logger.isDebugEnabled()) {
            logger.debug("new httpClient[" + client + "].===current thread cache route's size=" + ((Map)clients).size());
        }

        return client;
    }

    public static final void closeHttpClient(String url) {
        String host = getHostAndPort(url);
        Map<String, SNHttpClient> httpClients = (Map)threadLocal.get();
        HttpClient client = null;
        if (httpClients != null) {
            HttpClientUtils.SNHttpClient SNHttpClient = (HttpClientUtils.SNHttpClient)httpClients.get(host);
            if (SNHttpClient != null) {
                client = SNHttpClient.getHttpClient();
                client.getConnectionManager().shutdown();
            }

            httpClients.remove(host);
            if (logger.isDebugEnabled()) {
                logger.debug("shutdown httpClient[" + client + "].===current thread cache route's size=" + httpClients.size());
            }
        }

    }

    public static final void closeResponse(HttpResponse response) {
        if (response != null && response.getEntity() != null) {
            try {
                response.getEntity().getContent().close();
            } catch (Exception var2) {
                logger.error("closeResponse error", var2);
            }
        }

    }

    static class SNHttpClient {
        private DefaultHttpClient httpClient;
        private long accessTime;

        SNHttpClient() {
        }

        public DefaultHttpClient getHttpClient() {
            return this.httpClient;
        }

        public void setHttpClient(DefaultHttpClient httpClient) {
            this.httpClient = httpClient;
        }

        public long getAccessTime() {
            return this.accessTime;
        }

        public void setAccessTime(long accessTime) {
            this.accessTime = accessTime;
        }
    }
}
