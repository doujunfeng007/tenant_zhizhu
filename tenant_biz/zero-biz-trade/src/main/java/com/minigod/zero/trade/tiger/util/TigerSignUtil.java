package com.minigod.zero.trade.tiger.util;

import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public class TigerSignUtil {

    static {
        // 准备加密库
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String createSignature(String data, String privateKey) {
        // 生成签名
        try {
            Ed25519Signer signer = new Ed25519Signer();
            signer.init(true, deserializePrivateKey(privateKey));
            signer.update(data.getBytes(StandardCharsets.UTF_8), 0, data.length());
            byte[] signature = signer.generateSignature();
            return Base64.getEncoder().encodeToString(signature);
        } catch (Exception e) {
            // 处理异常，这里简单打印堆栈信息，实际应用中可根据需求进行处理
            e.printStackTrace();
            return null;
        }
    }

    public static Ed25519PrivateKeyParameters deserializePrivateKey(String encoded) {
        // 反序列化私钥
        return new Ed25519PrivateKeyParameters(Base64.getDecoder().decode(encoded), 0);
    }

    public static String generateSortData(Map<String, Object> params, String appSecret) {
        // 准备排序
        SortedMap<String, Object> sortedParams = new TreeMap<>(params);
        StringBuilder data = new StringBuilder();
        for (Entry<String, Object> entry : sortedParams.entrySet()) {
            data.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        // 拼接 appSecret
        data.append("appSecret=").append(appSecret);
        return data.toString();
    }

    public static String generateSortSignData(Map<String, Object> params) {
        // 准备排序
        SortedMap<String, Object> sortedParams = new TreeMap<>(params);
        StringBuilder data = new StringBuilder();
        for (Entry<String, Object> entry : sortedParams.entrySet()) {
            data.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        if (data.length() > 0) {
            data.deleteCharAt(data.length() - 1);
        }
        return data.toString();
    }

    public static boolean verifySignature(String data, String sign, String publicKeyStr) throws Exception {
        // 创建 Signature 对象并指定签名算法
        PublicKey publicKey = getPublicKey(publicKeyStr);

        Signature signature = Signature.getInstance("SHA256withRSA");
        // 初始化 Signature 对象为验证模式，并传入公钥
        signature.initVerify(publicKey);
        // 更新待验证的数据
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        // 解码签名信息
        byte[] signBytes = Base64.getDecoder().decode(sign);
        // 验证签名
        return signature.verify(signBytes);
    }

    public static PublicKey getPublicKey(String publicKeyStr) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static String encryptJsonWithPublicKey(String jsonData, String publicKeyStr) throws Exception {
        // 获取公钥
        PublicKey publicKey = getPublicKey(publicKeyStr);
        // 创建 Cipher 对象并指定加密算法
        Cipher cipher = Cipher.getInstance("RSA");
        // 初始化 Cipher 对象为加密模式，并传入公钥
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 加密 JSON 数据
        byte[] encryptedBytes = cipher.doFinal(jsonData.getBytes(StandardCharsets.UTF_8));
        // 使用 Base64 对加密后的字节数组进行编码
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}
