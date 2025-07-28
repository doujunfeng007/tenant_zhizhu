package com.minigod.zero.trade.utils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author chen
 * @ClassName RSAUtils.java
 * @Description RSA加密解密
 * @createTime 2024年11月26日 17:09:00
 */
public class RSAUtils {
	/**
	 *
	 * @param data
	 * @param key
	 * @return
	 */
	public static String encrypt(String data, String key) {

		String encryptedData = "";
		try {
			byte[] publicKeyBytes = Base64.getDecoder().decode(key);

			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

			Cipher rsaEncrypter = Cipher.getInstance("RSA");
			rsaEncrypter.init(Cipher.ENCRYPT_MODE, publicKey);

			byte[] encryptedByte = rsaEncrypter.doFinal(data.getBytes("UTF-8"));
			encryptedData = Base64.getEncoder().encodeToString(encryptedByte);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return encryptedData;
	}
}
