package com.minigod.zero.bpm.utils;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class OCRUtils {


	/**
	 * face++ ocr
	 * @param url
	 * @param imagePath
	 * @param apiKey
	 * @param apiSecret
	 * @return
	 */
	public static final String faceidOcr(String url, String imagePath,String apiKey,String apiSecret){
		long start = System.currentTimeMillis();
		log.info("faceidOcr start imagePath: " + imagePath);

		String result = null;
		try {
			Map<String,Object> params = new HashMap<>();
			params.put("Content-Encoding","UTF-8");
			params.put("api_key",apiKey);
			params.put("api_secret",apiSecret);
			File file = ImageUtils.getFile(imagePath);
			if(null != file && file.isFile()) {
				params.put("image",file);
				result = HttpUtil.post(url,params);
				log.info("faceidOcr resp: " + result);
			}

		} catch (Exception e) {
			log.error("faceidOcr error:"+e.getMessage(),e);
		}
		log.info("faceidOcr end cost : " + (System.currentTimeMillis() - start));
		return result;
	}

}
