package com.minigod.zero.bpm.utils;

import com.minigod.zero.biz.common.utils.ArithmeticUtil;
import com.minigod.zero.core.tool.beans.ImageSize;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;


@Slf4j
public class ImageUtils {

	//private static final String DEFAULT_URL_ENCODING = "UTF-8";

	/**
	 * Base64编码.
	 */
	public static String base64Encode(byte[] input) {
		return new String(Base64.encodeBase64(input));
	}

	/**
	 * Base64解码.
	 */
	public static byte[] base64Decode(String input) {
		return Base64.decodeBase64(input);
	}

	/**
	 * 图片转base64字符串
	 * @param imgFile
	 * @return
	 */
	public static String getImgStr(String imgFile) {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (Exception e) {
			log.error("getImgStr error:",e);
		}
		return "data:image/jpeg;base64," + base64Encode(data);
	}

	public static String getImgStr(byte[] data) {
		// 图片字节数组，并对其进行Base64编码处理
		return "data:image/jpeg;base64," + base64Encode(data);
	}

	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * @param _imgStr
	 */
	public static byte[] base64StringToImage(String _imgStr) {
		String header = "data:image/jpeg;base64,";
		if (_imgStr == null || _imgStr.length() < header.length()) // 图像数据为空
			return null;
		String imgStr = _imgStr.substring(header.length());
		byte[] b = null;

		try {
			b = base64Decode(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
		} catch (Exception e) {
			log.error("base64转字节数组错误", e);
		}
		return b;
	}

	/**
	 * base64转视频
	 * @param base64Str
	 * @return
	 */
	public static byte[] base64StringToVideo(String base64Str) {
		if (StringUtils.isBlank(base64Str))  return null;
		byte[] b = null;
		try {
			b = base64Decode(base64Str);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
		} catch (Exception e) {
			log.error("base64转视频错误", e);
		}
		return b;
	}


	public static byte[] uploadImageFile(ImageSize size,byte[] b) {

		ByteArrayOutputStream bs = null;
		InputStream is = null;
		ImageOutputStream ios = null;
		try {
			is = new ByteArrayInputStream(b);
			BufferedImage bi = ImageIO.read(is);
			// 原图的大小
			int width = bi.getWidth();
			int height = bi.getHeight();
			float quality = 1.0f;
			if (null != size) {
				int w = size.getWidth();
				int h = size.getHeight();

				// 如果原图小于切割的大小,则按照原图来处理
				if (w >= width) {
					w = width;
				}
				if (h >= height) {
					h = height;
				}
				BigDecimal high = new BigDecimal(h);
				Double d = new BigDecimal(w).divide(high, 10, RoundingMode.HALF_UP).multiply(high).doubleValue();

				// 获取压缩后的数据流
				bi = Thumbnails.of(bi).size(w, d.intValue()).outputQuality(quality).asBufferedImage();
				bs = new ByteArrayOutputStream();
				ios = ImageIO.createImageOutputStream(bs);
				if (ios != null) {
					ImageIO.write(bi, "jpg", ios);
					return bs.toByteArray();
				}
			} else {
				return b;
			}
		} catch (Exception e) {
			log.error("上传文件 异常", e);
		} finally {
			try {
				if (bs != null) {
					bs.close();
				}
				if (is != null) {
					is.close();
				}
				if (ios != null) {
					ios.close();
				}
			} catch (IOException e) {
				log.error("上传文件 IO异常", e);
			}
		}
		return b;
	}

	/**
	 * 将图片转为file
	 *
	 * @param url 图片url
	 * @return File
	 */
	public static File getFile(String url) throws Exception {
		//读取图片类型
		String fileName = url.substring(url.lastIndexOf("."),url.length());
		String prefix = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."));
		File file = null;

		URL urlfile;
		InputStream inStream = null;
		OutputStream os = null;
		try {
			file = File.createTempFile(prefix, fileName);
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
			log.error("getFile error",e);
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
				log.error("Stream close error",e);
			}
		}

		return file;
	}
}
