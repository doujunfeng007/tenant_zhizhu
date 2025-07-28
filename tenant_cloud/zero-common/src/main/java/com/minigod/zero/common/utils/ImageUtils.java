package com.minigod.zero.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class ImageUtils {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    public static byte[] generateImage(String _imgStr) { // 对字节数组字符串进行Base64解码并生成图片
        String header = "data:image/jpeg;base64,";
        if (_imgStr == null || _imgStr.length() < header.length()) // 图像数据为空
            return null;
        String imgStr = _imgStr.substring(header.length());
        Base64.Decoder base64decoder = Base64.getDecoder();
//        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = null;
        try {
            // Base64解码
            b = base64decoder.decode(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }

        } catch (Exception e) {
            logger.error("base64转字节数组错误", e);
        }
        return b;
    }


    public static String loadImgBase64ByUrl(String imageUrl) {
        String header = "";
//        String header = "data:image/jpeg;base64,";
        URL url = null;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpUrl = null;

        try {
            url = new URL(imageUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            httpUrl.getInputStream();
            is = httpUrl.getInputStream();

            outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = is.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            // 对字节数组Base64编码
            String base = Base64.getEncoder().encodeToString(outStream.toByteArray());
            return header + base;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
