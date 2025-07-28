package com.minigod.zero.auth.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.common.utils.RSAUtil
 * @Date: 2023年02月15日 19:48
 * @Description:
 */
@Slf4j
@Component
public class RSANewUtil {

	private static String PUBLIC_KEY;
	private static String PRIVATE_KEY;
	private static final String PADDING = "RSA/None/OAEPWithSHA-256AndMGF1Padding";
	//公司户默认密码/存量用户默认密码
	public final static String AUTHOR_PWD = "12345678";

	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	@Value("${zero.trade.privateKey:''}")
	public void setDatabase(String privateKey) {
		PRIVATE_KEY = privateKey;
	}

	/**
	 * RSA 公钥加密
	 */
	@SneakyThrows
	public static String encrypt( String str) {
		//base64编码的公钥
		byte[] decoded = Base64Utils.decodeFromString(PUBLIC_KEY);
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		//RSA加密
		Cipher cipher = Cipher.getInstance(PADDING);
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		return Base64Utils.encodeToString(cipher.doFinal(str.trim().getBytes("UTF-8")));
	}

	/**
	 * RSA 私钥解密
	 */
	@SneakyThrows
	public static String decrypt(String str) {
		if(AUTHOR_PWD.equals(str)){
			return str;
		}
		//64位解码加密后的字符串
		byte[] inputByte = Base64Utils.decodeFromString(str.trim());
		//base64编码的私钥
		byte[] decoded = Base64Utils.decodeFromString(PRIVATE_KEY);
		RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
		//RSA解密
		Cipher cipher = Cipher.getInstance(PADDING);
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		return new String(cipher.doFinal(inputByte));
	}

	/*
	public static void main(String[] args) {
		String password = encrypt("123456");
		System.out.println("密文：" + password);
	}

	 */

	/**
	 * 随机生成密钥对
	 */
	/*
	public static void genKeyPair() throws NoSuchAlgorithmException {
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		// 初始化密钥对生成器，密钥大小为2048位
		keyPairGen.initialize(2048,new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		String publicKeyString = Base64Utils.encodeToString(publicKey.getEncoded());
		String privateKeyString = Base64Utils.encodeToString(privateKey.getEncoded());
		System.out.println("publicKey:" + publicKeyString);
		System.out.println("privateKey:" + privateKeyString);
	}

	public static void main(String[] args) throws Exception {
		//genKeyPair();
		//加密字符串
		String password = "123456";
		System.out.println("加密前:" + password);
		String passwordEn = encrypt(password);
		System.out.println("加密后:" + passwordEn);
		String passwordDe = decrypt(passwordEn);
		System.out.println("解密后:" + passwordDe);
	}
	*/

}
