package com.minigod.zero.biz.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;

@Slf4j
public class RSAUtil {

	public RSAUtil() {
	}

	public static String decrypt(String data, String key) {
		try {
			if (!SecurityUtil.isBlank(data) && !SecurityUtil.isBlank(key)) {
				byte[] outKey = decrypt(key, SecurityKey.RSA_N, SecurityKey.RSA_E, SecurityKey.RSA_D);
				byte[] aesKey = new byte[16];
				byte[] aesIV = new byte[16];
				System.arraycopy(outKey, 0, aesKey, 0, 16);
				System.arraycopy(outKey, 16, aesIV, 0, 16);
				return AESUtil.decrypt(data.getBytes("UTF-8"), aesKey, aesIV);
			} else {
				throw new RuntimeException("RSA decrypt parameter error.");
			}
		} catch (Exception var5) {
			log.error("RSA decrypt error,", var5);
			throw new RuntimeException("RSA decrypt.", var5);
		}
	}

	private static byte[] decrypt(String rsaKey, String N, String E, String D) {
		try {
			byte[] baseKey = Base64.decode(rsaKey.getBytes("UTF-8"));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			KeySpec keySpec = new RSAPrivateKeySpec(new BigInteger(N), new BigInteger(D));
			Key key = keyFactory.generatePrivate(keySpec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(2, key);
			return cipher.doFinal(baseKey);
		} catch (Exception var9) {
			log.error("RSA decrypt error,", var9);
			throw new RuntimeException("RSA decrypt.", var9);
		}
	}
}
