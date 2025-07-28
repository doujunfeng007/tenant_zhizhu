package com.minigod.auth.utils;

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

	//private static String PUBLIC_KEY= "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCT4IM4X+0B5O6Ww3LP9gmDRawmgr7xkFevQdCWFTKWUTGC39E5qQvATPAvUfFIWQ4d+zxBxAkBnuAN6hEOo7XiCOSnsAD4AUxyGArnw5Tw8YeAOGEUAN8n3txGg5E1iwp53Gl2QxdYKAYrzCddRoG84NkD59CfIBjKi4YWYNBEgwIDAQAB";
	private static String PUBLIC_KEY="";
	private static String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJPggzhf7QHk7pbDcs/2CYNFrCaCvvGQV69B0JYVMpZRMYLf0TmpC8BM8C9R8UhZDh37PEHECQGe4A3qEQ6jteII5KewAPgBTHIYCufDlPDxh4A4YRQA3yfe3EaDkTWLCnncaXZDF1goBivMJ11Ggbzg2QPn0J8gGMqLhhZg0ESDAgMBAAECgYAXZhUdXIBiJlliXbbTFTlYHjQpO9L+4jodrypUNTJ1+o79CHFQ0GDdos72jd7B9eV39QMJ3P0X1o3ZZcohKOJkjiDtQ2fS6qwu40KPorUdDp0OODxCnGCZx/jQJNCX1a51jpVH746FsKuQBzlpa2bbq1OKidBVi0KRXKRoYinOMQJBAMyOGTbZtcx/4d6b6n81O76J46xzD+1RdpCPWT40kX51D87xiJlpwyX6JUkI2giE2yG9pDXgTZ/LnfMsJqKiFacCQQC5EU0Lqoiy66FFl8yBIj52oRDJ4Zo0W1P59Nea9PDqm44rBbciVbdkRMmQ9R89yJG1QHTXvLCanVdF512I5u3FAkBN4WxILevByI6+qq5FcIcmrGk6pzRRLsrtjyZzQKCEfR1ftSZFAreCJhM8E1qwE6U623FB8fyb8nJYu16aXQvRAkBnFFrcj5zaQ2ItWBKhLoiqHKY0PANe/Ct3AsgAWEPD+Oaj0egiKKogICxBIDbJe+ikkwVBkKixRcfxeJ/he/adAkEApoeWX3S7yHEzL/F4/fvRNMNQbyXAk+tmr9WlHLQ6D7ZKCSM2A4dznzAzDmT0mZGWcHRPrwonp6wODyAUNXl/fg==" ;
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
		byte[] inputByte = Base64Utils.decodeFromString(str.replaceAll(" ","+").trim());
		//base64编码的私钥
		byte[] decoded = Base64Utils.decodeFromString(PRIVATE_KEY);
		RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
		//RSA解密
		Cipher cipher = Cipher.getInstance(PADDING);
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		return new String(cipher.doFinal(inputByte));
	}
}
