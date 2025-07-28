package com.minigod.zero.dbs.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import com.minigod.zero.dbs.config.DbsBankConfig;
import com.minigod.zero.dbs.config.DbsBankIccConfig;
import com.minigod.zero.dbs.util.pgp.BCPGPDecryptor;
import com.minigod.zero.dbs.util.pgp.BCPGPEncryptor;
import com.minigod.zero.dbs.util.pgp.Decrypt;
import com.minigod.zero.dbs.util.pgp.Encrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbsCommManageUtils {
    private static final Logger logger = LoggerFactory.getLogger(DbsCommManageUtils.class);

    /**
     * 通用请求发送
     *
     * @param url 当前业务请求url
     * @return
     * @Param request 请求信息
     * @Param keyId 业务请求keyId
     * @Param business 业务标识
     */
    public static String send(String url, String request, String business, String msgId) {
        String result = null;

        try {

            DbsBankConfig dbsBankConfig = DbsBeanUtils.getBean(DbsBankConfig.class);
            String reqUrl = dbsBankConfig.getUrl() + url;
            String hostname = dbsBankConfig.getHostName();
            String orgId = dbsBankConfig.getOrgId();
            String keyId = dbsBankConfig.getKeyId();
            // 超时时间
            Integer timeOut = dbsBankConfig.getConnectionTimeout();
            if (timeOut == null) {
                timeOut =  20000;
            }

            logger.info("DBS Interface Request Url:{} keyId:{} orgId:{} timeout:{}", reqUrl, keyId, orgId, timeOut);
            logger.info("DBS Interface Request Business：{}, msgId：{} ", business, msgId);
            //logger.info("DBS Interface Request Info：" + request);

            //开始发送请求 (调试不同接口需要修改 accountUrl/KeyId)
            HttpResponse httpResponse = null;
            if (!business.equals("RefundAPI")) {
                httpResponse = HttpRequest.post(reqUrl).
                    header("x-api-key", keyId).
                    header("Host", hostname).
                    header("X-DBS-ORG_ID", orgId).
                    header("Accept", "text/plain").
                    header("Accept-Encoding","UTF-8").
                    header("Content-Type", "text/plain;charset=UTF-8").
                    header("Connection", "keep-alive").
                    body(request).
                    setConnectionTimeout(timeOut).
                    execute();
            } else {
                httpResponse = HttpRequest.post(reqUrl).
                    header("x-api-key", keyId).
                    header("Host", hostname).
                    header("ORG_ID", orgId).
                    header("X-DBS-KeyId", keyId).
                    header("X-DBS-ORG_ID", orgId).
                    header("Accept", "text/plain").
                    header("Accept-Encoding","UTF-8").
                    header("Content-Type", "text/plain;charset=UTF-8").
                    header("Connection", "keep-alive").
                    body(request).
                    setConnectionTimeout(timeOut).
                    execute();
            }
            if (null != httpResponse) {
                if (httpResponse.getStatus() == 200 || httpResponse.body()!=null) {
                    result = httpResponse.body();
                }
                logger.info("BDS Interface Response http status: {}  Info: {} ", httpResponse.getStatus(), result);
            }
            return result;

        } catch (Exception e) {
			e.printStackTrace();
            logger.error("请求星展银行接口异常", e);
        }

        return null;
    }

    public static String sendDelete(String url, String request, String business, String msgId) {
        String result = null;

        try {
            DbsBankConfig dbsBankConfig = DbsBeanUtils.getBean(DbsBankConfig.class);
            String reqUrl = dbsBankConfig.getUrl() + url;
            String hostname = dbsBankConfig.getHostName();
            String orgId = dbsBankConfig.getOrgId();
            String keyId = dbsBankConfig.getKeyId();
            // 超时时间
            Integer timeOut = dbsBankConfig.getConnectionTimeout();
            if (timeOut == null) {
                timeOut =  20000;
            }

            logger.info("DBS Interface Request Url:{} keyId:{} orgId:{} timeout:{}", reqUrl, keyId, orgId, timeOut);
            logger.info("DBS Interface Request Business：{}, msgId：{} ", business, msgId);

            //开始发送请求 (调试不同接口需要修改 accountUrl/KeyId)
            HttpResponse httpResponse = null;
            if (!business.equals("RefundAPI")) {
                httpResponse = HttpRequest.delete(reqUrl).
                    header("x-api-key", keyId).
                    header("Host", hostname).
                    header("X-DBS-ORG_ID", orgId).
                    header("Accept", "text/plain").
                    header("Content-Type", "text/plain").
                    header("Connection", "keep-alive").
                    body(request).
                    setConnectionTimeout(timeOut).
                    execute();
            } else {
                httpResponse = HttpRequest.delete(reqUrl).
                    header("x-api-key", keyId).
                    header("Host", hostname).
                    header("ORG_ID", orgId).
                    header("X-DBS-KeyId", keyId).
                    header("X-DBS-ORG_ID", orgId).
                    header("Accept", "text/plain").
                    header("Content-Type", "text/plain").
                    header("Connection", "keep-alive").
                    body(request).
                    setConnectionTimeout(timeOut).
                    execute();
            }
            if (null != httpResponse) {
                if (httpResponse.getStatus() == 200) {
                    result = httpResponse.body();
                }
                logger.info("BDS Interface Response http status: {}  Info: {} ", httpResponse.getStatus(), result);
            }
            return result;

        } catch (Exception e) {
			e.printStackTrace();
            logger.error("请求星展银行接口异常", e);
        }

        return null;
    }


    /**
     * 请求明文信息加密
     *
     * @param inputStr 明文信息
     * @param business 请求业务
     * @param msgId    请求流水号
     * @return
     */
    public static String encrypt(String inputStr, String business, String msgId) {
        try {
            DbsBankConfig dbsBankConfig = DbsBeanUtils.getBean(DbsBankConfig.class);
            logger.info("DBS Encrypt Before Business：{} ，msgId：{} ，inputStr：" + inputStr, business, msgId, inputStr);
            String path = DbsCommManageUtils.class.getResource("/").getPath() + "pgp/";
            //DBS PGP public Key
            //String publicKey = path + dbsBankConfig.getPubKey();
            //Customer PGP private Key
            //String privateKey = path + dbsBankConfig.getSecKey();
            //Customer PGP private Key if any     需要dbs官方提供
			String publicKey =  dbsBankConfig.getPubKey();
			String privateKey =  dbsBankConfig.getSecKey();
            String privayeKeyPassword = dbsBankConfig.getPrivayeKeyPassword();

            // Temp print encrypt
            logger.info("publicKey: {}", publicKey);
            logger.info("privateKey: {}", privateKey);

            //Constructing PGP encryption and signed message
            Encrypt encrypt = new Encrypt();
            encrypt.setArmored(true);
            encrypt.setCheckIntegrity(true);
            encrypt.setPublicKeyFilePath(publicKey);
            encrypt.setSigning(true);
            encrypt.setPrivateKeyFilePath(privateKey);
            encrypt.setPrivateKeyPassword(privayeKeyPassword);
            BCPGPEncryptor bcpgpEnryptor = new BCPGPEncryptor(encrypt);

            String encryptedMessage = bcpgpEnryptor.encryptMessage(inputStr);
            logger.info("DBS Encrypt After Business：{}，msgId：{}，encryptedMessage：{}", business, msgId, encryptedMessage);
            return encryptedMessage;

        } catch (Exception e) {

			e.printStackTrace();
            logger.error("当前业务：" + business + "，msgId：" + msgId + "，生成星展报文加密异常" + e);
        }
        return null;
    }

    /**
     * 解密加密
     *
     * @param response
     * @return
     */
    public static String decrypt(String response, String business, String msgId) {
        try {
            DbsBankConfig dbsBankConfig = DbsBeanUtils.getBean(DbsBankConfig.class);
            logger.info("DBS Response Decrypt Before Business：{}，msgId：{}，encryptedMessage：" + response, business, msgId, response);
            String path = DbsCommManageUtils.class.getResource("/").getPath() + "pgp/";
            //DBS PGP public Key
            //String publicKey = path + dbsBankConfig.getPubKey();
            //Customer PGP private Key
            //String privateKey = path + dbsBankConfig.getSecKey();
            //Customer PGP private Key if any

			String publicKey =  dbsBankConfig.getPubKey();
			String privateKey =  dbsBankConfig.getSecKey();
            String privayeKeyPassword = dbsBankConfig.getPrivayeKeyPassword();

            // Temp print encrypt
            logger.info("publicKey: {}", publicKey);
            logger.info("privateKey: {}", privateKey);
            logger.info("privayeKeyPassword: {}", privayeKeyPassword);

            //Decrypting the api response using PGP decryption and verify
            Decrypt decrypt = new Decrypt();
            decrypt.setPublicKeyFilePath(publicKey);
            decrypt.setVerify(true);
            decrypt.setPrivateKeyFilePath(privateKey);
            decrypt.setPrivateKeyPassword(privayeKeyPassword);
            BCPGPDecryptor bcpgpDecryptor = new BCPGPDecryptor(decrypt);

            String decryptedMessage = bcpgpDecryptor.decryptMessage(response);
            logger.info("DBS Response Decrypt After Business：{}，msgId：{}，decryptedMessage：" + decryptedMessage, business, msgId, decryptedMessage);
            return decryptedMessage;
        } catch (Exception e) {
			e.printStackTrace();
            logger.error("当前业务：" + business + "，msgId：" + msgId + "，DBS响应报文解密异常" + e);
        }
        return null;
    }

    /**
     * 验签解密
     *
     * @param response 加密内容
     * @param business 消息类型
     * @return
     */
    public static String decrypt(String response, String business) {
        return decrypt(response, business, null);
    }
    /**
     * icc验签解密
     *
     * @param response 加密内容
     * @param business 消息类型
     * @return
     */
    public static String iccDecrypt(String response, String business) {
        return iccDecrypt(response, business, null);
    }

	/**
	 * icc解密加密
	 *
	 * @param response
	 * @return
	 */
	public static String iccDecrypt(String response, String business, String msgId) {
		try {
			DbsBankIccConfig dbsBankConfig = DbsBeanUtils.getBean(DbsBankIccConfig.class);
			logger.info("DBS Response Decrypt Before Business：{}，msgId：{}，encryptedMessage：" + response, business, msgId, response);
			String path = DbsCommManageUtils.class.getResource("/").getPath() + "pgp/";
			//DBS PGP public Key
			//String publicKey = path + dbsBankConfig.getPubKey();
			//Customer PGP private Key
			//String privateKey = path + dbsBankConfig.getSecKey();

			String publicKey =  dbsBankConfig.getPubKey();
			String privateKey =  dbsBankConfig.getSecKey();

			//Customer PGP private Key if any
			String privayeKeyPassword = dbsBankConfig.getPrivayeKeyPassword();

			// Temp print encrypt
			logger.info("publicKey: {}", publicKey);
			logger.info("privateKey: {}", privateKey);
			logger.info("privayeKeyPassword: {}", privayeKeyPassword);

			//Decrypting the api response using PGP decryption and verify
			Decrypt decrypt = new Decrypt();
			decrypt.setPublicKeyFilePath(publicKey);
			decrypt.setVerify(true);
			decrypt.setPrivateKeyFilePath(privateKey);
			decrypt.setPrivateKeyPassword(privayeKeyPassword);
			BCPGPDecryptor bcpgpDecryptor = new BCPGPDecryptor(decrypt);

			String decryptedMessage = bcpgpDecryptor.decryptMessage(response);
			logger.info("DBS Response Decrypt After Business：{}，msgId：{}，decryptedMessage：" + decryptedMessage, business, msgId, decryptedMessage);
			return decryptedMessage;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("当前业务：" + business + "，msgId：" + msgId + "，DBS响应报文解密异常" + e);
		}
		return null;
	}


	/**
	 * 请求明文信息加密
	 *
	 * @param inputStr 明文信息
	 * @param business 请求业务
	 * @param msgId    请求流水号
	 * @return
	 */
	public static String iccEncrypt(String inputStr, String business, String msgId) {
		try {
			DbsBankConfig dbsBankConfig = DbsBeanUtils.getBean(DbsBankConfig.class);
			logger.info("DBS Encrypt Before Business：{} ，msgId：{} ，inputStr：" + inputStr, business, msgId, inputStr);
			String path = DbsCommManageUtils.class.getResource("/").getPath() + "pgp/";
			//DBS PGP public Key
			//String publicKey = path + dbsBankConfig.getPubKey();
			//Customer PGP private Key
			//String privateKey = path + dbsBankConfig.getSecKey();
			//Customer PGP private Key if any     需要dbs官方提供
			String publicKey =  dbsBankConfig.getPubKey();
			String privateKey =  dbsBankConfig.getSecKey();
			String privayeKeyPassword = dbsBankConfig.getPrivayeKeyPassword();

			// Temp print encrypt
			logger.info("publicKey: {}", publicKey);
			logger.info("privateKey: {}", privateKey);

			//Constructing PGP encryption and signed message
			Encrypt encrypt = new Encrypt();
			encrypt.setArmored(true);
			encrypt.setCheckIntegrity(true);
			encrypt.setPublicKeyFilePath(publicKey);
			encrypt.setSigning(true);
			encrypt.setPrivateKeyFilePath(privateKey);
			encrypt.setPrivateKeyPassword(privayeKeyPassword);
			BCPGPEncryptor bcpgpEnryptor = new BCPGPEncryptor(encrypt);

			String encryptedMessage = bcpgpEnryptor.encryptMessage(inputStr);
			logger.info("DBS Encrypt After Business：{}，msgId：{}，encryptedMessage：{}", business, msgId, encryptedMessage);
			return encryptedMessage;

		} catch (Exception e) {

			e.printStackTrace();
			logger.error("当前业务：" + business + "，msgId：" + msgId + "，生成星展报文加密异常" + e);
		}
		return null;
	}

}
