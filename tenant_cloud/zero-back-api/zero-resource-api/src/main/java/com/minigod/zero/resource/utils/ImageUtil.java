package com.minigod.zero.resource.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author sunline
 * @date 2016/9/10 18:19
 */
@Service
@Slf4j
public class ImageUtil {

	private static final String SZ_DOWNLOAD_DIR = "/data/temp/";

	private static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}

	private static final String getDatePath(String dateFormat) {
		SimpleDateFormat date = new SimpleDateFormat(dateFormat);
		return date.format(new Date());
	}

	/**
	 * 将图片转为file
	 *
	 * @param url 图片url
	 * @return File
	 */
	public static File getFile(String url) throws Exception {
		//读取图片类型
		String fileName = "";
		String prefix = "";
		int index1 = url.lastIndexOf("/") + 1;
		int index2 = url.lastIndexOf(".");
		if (index1 >= index2 || index2 == -1) {
			fileName = url.substring(index1);
		} else {
			fileName = url.substring(index1, index2);
			prefix = url.substring(url.lastIndexOf("."), url.length());
		}
		if (url.contains("filename=")) {
			fileName = url.substring(url.lastIndexOf("=") + 1, url.lastIndexOf("."));
			url = url.substring(0, url.lastIndexOf("=")) + URLEncoder.encode(fileName, "UTF-8") + prefix;
		}
		File file = null;

		URL urlfile;
		InputStream inStream = null;
		OutputStream os = null;
		try {
			fileName = URLDecoder.decode(fileName, "UTF-8");
			file = new File(SZ_DOWNLOAD_DIR + fileName + prefix);
			File pf = file.getParentFile();
			if (!pf.exists()) {
				pf.mkdirs();
			}
			//获取文件
			urlfile = new URL(url);
			inStream = urlfile.openStream();
			os = new FileOutputStream(file);

			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			log.error("getFile error", e);
		} finally {
			try {
				if (null != os) {
					os.close();
				}
				if (null != inStream) {
					inStream.close();
				}
				file.deleteOnExit();
			} catch (Exception e) {
				log.error("Stream close error", e);
			}
		}
		return file;
	}

}
