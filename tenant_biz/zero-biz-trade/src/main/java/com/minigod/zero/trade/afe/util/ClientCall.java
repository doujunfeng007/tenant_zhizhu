package com.minigod.zero.trade.afe.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.trade.afe.webservice.createaccount.CreateAccount;
import com.minigod.zero.trade.afe.webservice.createaccount.CreateAccountPortType;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/15 18:42
 * @description：
 */
@Slf4j
public class ClientCall {
	/**
	 * 缓存密钥
	 */
	public static Map<String,Object> ENCRYPT_MAP = new HashMap<>();
	/**
	 * xml根节点
	 */
	private static final String ROOT_NODE = "ENCRYPTION";

	private static final String ENCRYPTION_KEY = "KEY";

	private static final String ENCRYPTION_KEY_SIZE = "KEYSIZE";

	/**
	 * 加密
	 * @param rawText
	 * @param publicKey
	 * @param keySize
	 * @param transformation
	 * @param encoding
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public static String encrypt(String rawText, byte[] publicKey, int keySize,
								 String transformation, String encoding) throws IOException, GeneralSecurityException {
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
		Cipher cipher = Cipher.getInstance(transformation != null ?
			transformation : "RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE,
			KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec));
		byte[] rawTextInBytes = rawText.getBytes(encoding);
		ByteArrayOutputStream encryptedByteArrayOutputStream = new
			ByteArrayOutputStream();
		int maximumEncodedLength = (keySize / 8) - 11;
		for (int encodeIndex = 0; encodeIndex < rawTextInBytes.length;)
		{
			int encodeLength = (encodeIndex + maximumEncodedLength <
				rawTextInBytes.length ? encodeIndex + maximumEncodedLength : rawTextInBytes.length) -
				encodeIndex;
			encryptedByteArrayOutputStream.write(cipher.doFinal(rawTextInBytes,
				encodeIndex, encodeLength));
			encodeIndex += encodeLength;
		}
		String returnValue =Base64.getEncoder().encodeToString(encryptedByteArrayOutputStream.toByteArray());
		return returnValue;
	}

	/**
	 * 获取加密密钥
	 * @param xmlString
	 * @return
	 */
	public static Map<String,Object> getEncryptMessage(String xmlString) {
		Map<String, Object> parameter = XmlGeneratorUtil.parseXml(xmlString);
		if (parameter.containsKey(ROOT_NODE)){
			Map<String, Object> childNode = (Map<String, Object>) parameter.get(ROOT_NODE);
			ENCRYPT_MAP = childNode;
			return ENCRYPT_MAP ;
		}
		return null;
	}

	/**
	 * 开户参数加密
	 * @param companyId
	 * @param userId
	 * @param password
	 * @param param
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public static String encryptParam(String companyId,String userId,String password,String param) throws IOException, GeneralSecurityException {
		Map<String,Object> encryptMap = null;
		try {
			if (ENCRYPT_MAP.isEmpty()){
				CreateAccount account = new CreateAccount();
				CreateAccountPortType portType = account.getCreateAccountHttpSoap12Endpoint();
				String queryResponse = portType.queryParameters(companyId,userId,password);
				log.info("开户查询参数返回参数：{}", queryResponse);
				if (StringUtil.isBlank(queryResponse)){
					return null;
				}
				encryptMap = getEncryptMessage(queryResponse);
			}else{
				encryptMap = ENCRYPT_MAP;
			}
			if (encryptMap == null){
				return null;
			}
			String encryptionKey = (String) encryptMap.get(ENCRYPTION_KEY);
			encryptionKey = encryptionKey.replaceAll("\r\n", "").replaceAll("\n", "");
			Integer encryptionKeySize = Integer.valueOf((String) encryptMap.get(ENCRYPTION_KEY_SIZE));
			byte[] encryptKeyByte = Base64.getDecoder().decode(encryptionKey);
			String paramEncrypt = encrypt(param,encryptKeyByte, encryptionKeySize, null, "UTF-8");
			return paramEncrypt;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
