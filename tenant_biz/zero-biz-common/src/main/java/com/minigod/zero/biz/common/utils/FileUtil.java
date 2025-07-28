package com.minigod.zero.biz.common.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.alibaba.cloud.commons.io.IOUtils;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.minigod.zero.biz.common.constant.NewsConstant;
import com.minigod.zero.core.tool.beans.ImageSize;
import com.minigod.zero.core.oss.model.ZeroFile;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.resource.feign.IOssClient;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;

@Slf4j
@Component
public class FileUtil {

	static {
		Logger logger = (Logger) LoggerFactory.getLogger("com.itextpdf");
		logger.setLevel(Level.OFF);
	}

	@Resource
	private IOssClient ossClient;

	public List<ZeroFile> uploadFile(List<ImageSize> whs, MultipartFile... files) {

		List<MultipartFile> multipartFiles = Arrays.asList(files);
		ByteArrayOutputStream bs = null;
		InputStream is = null;
		ImageOutputStream ios = null;
		List<ZeroFile> zeroFiles = new ArrayList<>();
		try {

			for (MultipartFile entry : multipartFiles) {

				String fileName = entry.getName();
				int index = fileName.lastIndexOf(".");
				if (!(index > -1)) {
					String conType = entry.getContentType();
					index = conType.lastIndexOf("/");
					if (index > -1) {
						fileName = fileName + "." + conType.substring(index + 1, conType.length());
					}
				}

				String suffix = fileName.indexOf(".") != -1 ? fileName
					.substring(fileName.lastIndexOf("."), fileName.length())
					: null;
				String extType = suffix.replace(".", "");
				String uuid32 = getUUID();

				InputStream in = new ByteArrayInputStream(entry.getBytes());
				BufferedImage bi = ImageIO.read(in);

				// 原图的大小
				int width = bi.getWidth();
				int height = bi.getHeight();

				String realFileName = uuid32 + "__" + width + "x" + height + suffix;

				float quality = 1.0f;
				if (null != whs) {
					for (ImageSize size : whs) {
						int w = size.getWidth();
						int h = size.getHeight();

						// 如果原图小于切割的大小,则按照原图来处理
						if (w >= width) {
							w = width;
						}
						if (h >= height) {
							h = height;
						}

						realFileName = uuid32 + "__" + w + "x" + h + suffix;
						Double d = ArithmeticUtil.div(w, h, 10) * h;

						// 获取压缩后的数据流
						bi = Thumbnails.of(bi).size(w, d.intValue()).outputQuality(quality).asBufferedImage();
						bs = new ByteArrayOutputStream();
						ios = ImageIO.createImageOutputStream(bs);
						if (ios != null) {
							ImageIO.write(bi, extType, ios);
						}

						MultipartFile multipartFile = this.getMultipartFile(realFileName, bs.toByteArray());
						R<ZeroFile> r = ossClient.uploadQiniuFile(multipartFile, realFileName);
						ZeroFile zeroFile = r.getData();
						if (r.isSuccess() && zeroFile != null) {
							zeroFiles.add(zeroFile);
						}
					}
				} else {
					MultipartFile multipartFile = this.getMultipartFile(realFileName, bs.toByteArray());
					R<ZeroFile> r = ossClient.uploadQiniuFile(multipartFile, realFileName);
					ZeroFile zeroFile = r.getData();
					if (r.isSuccess() && zeroFile != null) {
						zeroFiles.add(zeroFile);
					}
				}
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
		return zeroFiles;
	}

	public List<ZeroFile> saveImage(String path) {
		List<ZeroFile> qnFiles = new ArrayList<>();
		HttpResponse httpResponse = UrlReader.readAndGetResponse(path);
		if (null != httpResponse) {
			String contentType = httpResponse.getHeaders("Content-Type")[0].getValue();
			if (StringUtils.isNotEmpty(contentType)) {
				String extType = contentType.substring(contentType.indexOf("/") + 1);
				String name = String.valueOf(new Date().getTime());
				if ("jpg,jpeg,gif,png".contains(extType)) {
					InputStream inputStream = null;
					ByteArrayOutputStream byteStream = null;
					try {
						inputStream = httpResponse.getEntity().getContent();
						long imgSize = httpResponse.getEntity().getContentLength();
						if (null != inputStream) {
							byteStream = new ByteArrayOutputStream();
							byte[] buff = new byte[1024];
							int rc = 0;
							while ((rc = inputStream.read(buff, 0, 1024)) > 0) {
								byteStream.write(buff, 0, rc);
							}
							byte[] in2b = byteStream.toByteArray();
							Map maps = new HashMap();
							maps.put(name + "." + extType, in2b);
							return uploadImage(maps, imgSize);
						}
					} catch (Exception e) {
						log.error("保存图片失败，url=" + path, e);
					} finally {
						try {
							if (null != inputStream) {
								inputStream.close();
							}
							if (null != byteStream) {
								byteStream.close();
							}
						} catch (IOException e) {
							log.error("上传文件 IO异常", e);
						}
					}
				}
			}
		}
		return qnFiles;
	}

	public List<ZeroFile> uploadImage(Map<String, byte[]> maps, long imgSize) {

		ByteArrayOutputStream bs = null;
		InputStream is = null;
		ImageOutputStream ios = null;
		List<ZeroFile> qfs = new ArrayList<>();
		try {
			for (Map.Entry<String, byte[]> entry : maps.entrySet()) {

				String fileName = entry.getKey();
				InputStream in = new ByteArrayInputStream(entry.getValue());

				String suffix = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()).toLowerCase() : ".jpg";
				BufferedImage bi = ImageIO.read(in);
				// 含原图的大小
				String realFileName = getUUID() + "__" + bi.getWidth() + "x" + bi.getHeight() + suffix;

				String extType = suffix.replace(".", "");
				double scale = 1.0d;
				if (imgSize >= 100 * 1024) {
					scale = ArithmeticUtil.div(100 * 1024, imgSize, 2);
					if (suffix.contains("png")) {
						extType = "gif";
						bi = Thumbnails.of(bi).size(NewsConstant.TITLE_WIDTH, NewsConstant.TITLE_HEIGHT).outputFormat(extType).outputQuality(scale).asBufferedImage();
					} else {
						bi = Thumbnails.of(bi).size(NewsConstant.TITLE_WIDTH, NewsConstant.TITLE_HEIGHT).outputQuality(scale).asBufferedImage();
					}

					bs = new ByteArrayOutputStream();
					ios = ImageIO.createImageOutputStream(bs);
					if (ios != null) {
						boolean write = ImageIO.write(bi, extType, ios);
						if (!write){
							boolean png = ImageIO.write(bi, "png", ios);
							if (png) {
								realFileName = realFileName.substring(0, realFileName.lastIndexOf(".")) + ".png";
							}
						}
					}
				} else {
					bs = new ByteArrayOutputStream();
					ios = ImageIO.createImageOutputStream(bs);
					if (bi == null || ios == null) {
						break;
					}
					boolean write = ImageIO.write(bi, extType, ios);
					if (!write){
						boolean png = ImageIO.write(bi, "png", ios);
						if (png) {
							realFileName = realFileName.substring(0, realFileName.lastIndexOf(".")) + ".png";
						}
					}
				}

				MultipartFile multipartFile = this.getMultipartFile(realFileName, bs.toByteArray());
				R<ZeroFile> r = ossClient.uploadQiniuFile(multipartFile, realFileName);
				ZeroFile zeroFile = r.getData();
				if (r.isSuccess() && zeroFile != null) {
					qfs.add(zeroFile);
				}
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
		return qfs;
	}

	public List<ZeroFile> saveImageByTls2(String path) {
		List<ZeroFile> qnFiles = new ArrayList<>();
		HttpResponse httpResponse = UrlReader.getHttpResponse(path);
		if (null != httpResponse) {
			String contentType = httpResponse.getHeaders("Content-Type")[0].getValue();
			if (StringUtils.isNotEmpty(contentType)) {
				String extType = contentType.substring(contentType.indexOf("/") + 1);
				String name = String.valueOf(new Date().getTime());
				if ("jpg,jpeg,gif,png".contains(extType)) {
					InputStream inputStream = null;
					ByteArrayOutputStream byteStream = null;
					try {
						inputStream = httpResponse.getEntity().getContent();
						long imgSize = httpResponse.getEntity().getContentLength();
						if (null != inputStream) {
							byteStream = new ByteArrayOutputStream();
							byte[] buff = new byte[1024];
							int rc = 0;
							while ((rc = inputStream.read(buff, 0, 1024)) > 0) {
								byteStream.write(buff, 0, rc);
							}
							byte[] in2b = byteStream.toByteArray();
							Map maps = new HashMap();
							maps.put(name + "." + extType, in2b);
							return uploadImage(maps, imgSize);
						}
					} catch (Exception e) {
						log.error("保存图片失败，url=" + path, e);
					} finally {
						if (null != inputStream) {
							try {
								inputStream.close();
							} catch (IOException e) {
								log.error("上传文件 IO异常", e);
							}
						}

						if (null != byteStream) {
							try {
								byteStream.close();
							} catch (IOException e) {
								log.error("上传文件 IO异常", e);
							}
						}
					}
				}
			}
		}
		return qnFiles;
	}

	public ZeroFile uploadHtml2Pdf(String path) {
		ZeroFile zeroFile = null;
		InputStream is = null;
		try {
			URL website = new URL(path);
			is = website.openStream();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			PdfWriter writer = new PdfWriter(outputStream);
			PdfDocument pdfDoc = new PdfDocument(writer);

			ConverterProperties converterProperties = new ConverterProperties();
			HtmlConverter.convertToPdf(is, pdfDoc, converterProperties);

			String fileName = getUUID() + ".pdf";
			byte[] bytes = outputStream.toByteArray();
			MultipartFile multipartFile = getMultipartFile(fileName, bytes);

			R<ZeroFile> r = ossClient.uploadQiniuFile(multipartFile, fileName);
			if (r.isSuccess() && r.getData() != null) {
				zeroFile = r.getData();
			}
		} catch (Exception e) {
			// 异常不提示
		}finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				log.error("上传文件 IO异常", e);
			}
		}
		return zeroFile;
	}

	private static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}


	//二进制文件转换MultipartFile
	public static MultipartFile getMultipartFile(String fileName, byte[] bytes) {
		MultipartFile mfile = null;
		ByteArrayInputStream in = null;
		try {
			in = new ByteArrayInputStream(bytes);

			FileItemFactory factory = new DiskFileItemFactory(16, null);
			String textFieldName = "file";
			FileItem fileItem = factory.createItem(textFieldName, MediaType.MULTIPART_FORM_DATA_VALUE, true, fileName);
			IOUtils.copyLarge(new ByteArrayInputStream(bytes), fileItem.getOutputStream());
			mfile = new CommonsMultipartFile(fileItem);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mfile;
	}

	public static MultipartFile getMultipartFile(File file) {
		FileItem item = new DiskFileItemFactory().createItem("file",MediaType.MULTIPART_FORM_DATA_VALUE,true,file.getName());
		try (InputStream input = new FileInputStream(file);OutputStream os = item.getOutputStream()){
			IOUtils.copyLarge(input,os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new CommonsMultipartFile(item);
	}
}
