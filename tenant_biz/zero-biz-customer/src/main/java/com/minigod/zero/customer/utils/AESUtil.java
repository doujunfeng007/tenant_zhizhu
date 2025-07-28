package com.minigod.zero.customer.utils;

import cn.hutool.core.codec.Base64;
import com.minigod.zero.core.tool.utils.IDTransUtil;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.common.utils.AESUtil
 * @Date: 2023年02月15日 19:54
 * @Description:
 */
@Slf4j
public class AESUtil {

	private static byte[] iv = null;
	public static final String Key = "1234567890123456";
	private static final String ALGORITHM = "AES";
	private static final String CHARSET = "UTF-8";

	public AESUtil() {
	}

	public static String encrypt(String data, String key) {
		try {
			if (isBlank(data)) {
				throw new Exception("AES encrypt parameter error.");
			} else if (!isBlank(key) && key.length() == 16) {
				byte[] iv = SecurityKey.AES_IV.getBytes("UTF-8");
				SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
				IvParameterSpec ivSpec = new IvParameterSpec(iv);
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(1, keySpec, ivSpec);
				return new String(Base64.encode(cipher.doFinal(data.getBytes("UTF-8"))));
			} else {
				throw new Exception("AES encrypt key length: 16 bytes.");
			}
		} catch (Exception var6) {
			throw new RuntimeException("AES encrypt error.", var6);
		}
	}

	public static String encrypt(byte[] data, byte[] key, byte[] ivKey) throws Exception {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(ivKey);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(1, keySpec, ivSpec);
			return new String(Base64.encode(cipher.doFinal(data)));
		} catch (Exception var6) {
			throw new Exception("AES encrypt error.", var6);
		}
	}

	public static String decrypt(String data, String key) {
		try {
			if (isBlank(data)) {
				throw new Exception("AES decrypt parameter error.");
			} else if (!isBlank(key) && key.length() == 16) {
				if (iv == null) {
					iv = SecurityKey.AES_IV.getBytes("UTF-8");
				}

				SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
				IvParameterSpec ivSpec = new IvParameterSpec(iv);
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(2, keySpec, ivSpec);
				return new String(cipher.doFinal(Base64.decode(data)), "UTF-8");
			} else {
				throw new Exception("AES decrypt key length: 16 bytes.");
			}
		} catch (Exception var5) {
			log.error("AES decrypt error, e={}", var5);
			throw new RuntimeException("AES decrypt error.", var5);
		}
	}

	public static String decrypt(byte[] data, byte[] key, byte[] ivKey) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(ivKey);
			cipher.init(2, keySpec, ivSpec);
			return new String(cipher.doFinal(Base64.decode(data)), "UTF-8");
		} catch (Exception var6) {
			log.error("AES decrypt error, e={}", var6);
			throw new Exception("AES decrypt error.", var6);
		}
	}

	private static String getPwdKey(Integer userId) {
		String key = (long)userId + IDTransUtil.encodeId((long)userId, (long)SecurityKey.getIdKey()) + String.valueOf(userId);
		return key.substring(2, 18);
	}

	public static String getEncryptPhone(String phone) throws Exception {
		return encrypt(phone, SecurityKey.MOBILE_PHONE_KEY);
	}

	public static String getDecryptPhone(String phone) throws Exception {
		return decrypt(phone, SecurityKey.MOBILE_PHONE_KEY);
	}

	public static String encrypt(String data) {
		try{
			SecretKeySpec secretKeySpec = new SecretKeySpec(Key.getBytes(CHARSET), ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] encrypted = cipher.doFinal(data.getBytes(CHARSET));
			return org.apache.commons.codec.binary.Base64.encodeBase64String(encrypted);
		}catch (Exception e){
			return data;
		}
	}

	public static String decrypt(String encryptedData){
		try{
			SecretKeySpec secretKeySpec = new SecretKeySpec(Key.getBytes(CHARSET), ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] decrypted = cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(encryptedData));
			return new String(decrypted, CHARSET);
		}catch (Exception e){
			return encryptedData;
		}
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str != null && (strLen = str.length()) != 0) {
			for(int i = 0; i < strLen; ++i) {
				if (!Character.isWhitespace(str.charAt(i))) {
					return false;
				}
			}

			return true;
		} else {
			return true;
		}
	}

}
