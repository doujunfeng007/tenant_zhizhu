package com.minigod.zero.resource.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;

public class QiNiuUtil {

	//外链域名地址
    public static String qiniu_img_url_pre = "http://act-qa.xxxx.cn/";

	//AccessKey
    public static String ACCESS_KEY = "U8VGSrOjjPpoPFEG-qHUJrywNL743V2PsrMjW6WM";

	//SecretKey
    public static String SECRET_KEY = "ZcWIywOvp1NaaUNMU0o3ml8OTC-kk8TG_5e_T0kN";

	//存储空间名称
    public static String bucketname = "bpm-img-qa";



    /**
     * 上传文件
     */
    public static String upload2Qiniu(FileInputStream file, String uploadFileName) {
        //构造一个带指定Zone对象的配置类,Zone.zone0()代表华东地区
        //zone2() 华南
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = uploadFileName;
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(bucketname);
        try {
            Response response = uploadManager.put(file, key, upToken, null, null);
            //解析上传成功的结果
            String bodyString = response.bodyString();
			System.out.println(bodyString);
			String hash = (String) JSONObject.parseObject(bodyString).get("hash");
            if (StringUtils.isNotEmpty(hash)) {
				// 访问路径
                return qiniu_img_url_pre + uploadFileName;
            }
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
                return null;
            } catch (QiniuException ex2) {
                ex2.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /**
     * 下载文件
     * @param url 文件在七牛云服务器上的地址
     * @return
     */
    public static void downloadFromQNY(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            okhttp3.Response resp = client.newCall(request).execute();
            if (resp.isSuccessful()) {
                ResponseBody body = resp.body();
                InputStream inputStream = body.byteStream();
				File file = new File("F:\\Test\\fengjing2.jpg");
                FileOutputStream writer = new FileOutputStream(file);
                byte[] buff = new byte[1024 * 2];
                int len = 0;
                try {
                    while ((len = inputStream.read(buff)) != -1) {
                        writer.write(buff, 0, len);
                    }
					writer.close();
                    inputStream.close();
					System.out.println("下载成功");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
					writer.close();
					inputStream.close();
				}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/*
	public static void main(String[] args) throws FileNotFoundException {
        File file = new File("F:\\Test\\fengjing.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        String fileUrl = upload2Qiniu(fileInputStream, file.getName());
		System.out.println(fileUrl);

		downloadFromQNY(qiniu_img_url_pre + file.getName());
    }*/

}
