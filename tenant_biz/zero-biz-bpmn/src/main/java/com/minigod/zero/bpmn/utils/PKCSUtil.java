package com.minigod.zero.bpmn.utils;

import com.minigod.zero.core.log.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.openssl.MiscPEMGenerator;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.bouncycastle.util.io.pem.PemObjectGenerator;
import org.bouncycastle.util.io.pem.PemWriter;
import org.springframework.util.Base64Utils;

import javax.security.auth.x500.X500Principal;
import java.io.StringWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Signature;

@Slf4j
public class PKCSUtil {
    public static KeyPair generageKeyPair() {
        KeyPair kp = null;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            kp = keyGen.generateKeyPair();
        } catch (Exception e) {
            log.error("生成KeyPair失败!!", e);
            throw new ServiceException("生成KeyPair失败!!");
        }
        return kp;
    }

    public static String parseCertDN(String dn, String type) {
        type = type + "=";
        String[] split = dn.split(",");
        for (String x : split) {
            if (x.contains(type)) {
                x = x.trim();
                return x.substring(type.length());
            }
        }
        return null;
    }

    public static String genereatePkcs10(KeyPair keyPair, String dn) {
        String p10Code = "";
        try {

            // 获取RSA P10数据
            X500Principal subject = new X500Principal(dn);
            PKCS10CertificationRequestBuilder builder = new JcaPKCS10CertificationRequestBuilder(subject, keyPair.getPublic());
            String signatureAlgorithm = "SHA256WITH" + keyPair.getPublic().getAlgorithm();

            ContentSigner signer = new JcaContentSignerBuilder(signatureAlgorithm).build(keyPair.getPrivate());
            PKCS10CertificationRequest csr = builder.build(signer);

            StringWriter string = new StringWriter();
            PemWriter pemWriter = new PemWriter(string);

            PemObjectGenerator objGen = new MiscPEMGenerator(csr);
            pemWriter.writeObject(objGen);
            pemWriter.close();
            String pem = string.toString();
            return pem
                    .replace("-----BEGIN CERTIFICATE REQUEST-----", "")
                    .replace("-----END CERTIFICATE REQUEST-----", "")
                    .replaceAll("\\r\\n", "");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("P10签名失败!!");
        }

        return p10Code;

    }

    public static String genereatePkcs1(KeyPair keyPair, String hash) {
        String p1Code = "";
        try {
            // 获取
            byte[] h = Base64Utils.decodeFromString(hash);
            PrivateKey privateKey = keyPair.getPrivate();
            Signature signet = Signature.getInstance("SHA256WithRSA");
            signet.initSign(privateKey);
            signet.update(h);
            byte[] signed = signet.sign();
            p1Code = Base64Utils.encodeToString(signed);
        } catch (Exception e) {
            log.error("P1签名失败!!");
        }

        return p1Code;

    }
}
