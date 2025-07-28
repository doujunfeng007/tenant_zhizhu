package com.minigod.zero.customer.utils;

import cn.hutool.core.io.FileUtil;
import com.alibaba.cloud.commons.io.IOUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * 文件处理工具类
 *
 * @author zsdp
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils extends FileUtil {

    // 最大文件大小2MB
    public final static Integer MAX_FILE_SIZE = 1024 * 1024 * 15;

    //10KB
    public final static Integer MIN_FILE_SIZE = 10;

    private final static List<String> SUPPORT_FORMATS = new ArrayList<>(Arrays.asList("doc,docx,xls,xlsx,ppt,pptx,pdf,txt,wmv,mp4,html,xml,jpg,jpeg,png".split(",")));
    private final static List<String> SUPPORT_FILE_FORMATS = new ArrayList<>(Arrays.asList("doc,docx,xls,xlsx,ppt,pptx,pdf,txt,wmv,mp4,html,xml".split(",")));
    private final static List<String> SUPPORT_IMAGE_FORMATS = new ArrayList<>(Arrays.asList("jpg,jpeg,png,gif,heic".split(",")));

    public static boolean checkFormats(String fileFullName) {
        String suffix = fileFullName.substring(fileFullName.lastIndexOf(".") + 1).toLowerCase();
        return SUPPORT_FORMATS.stream().anyMatch(suffix::contains);
    }

    public static boolean checkFileFormats(String fileFullName) {
        String suffix = fileFullName.substring(fileFullName.lastIndexOf(".") + 1).toLowerCase();
        return SUPPORT_FILE_FORMATS.stream().anyMatch(suffix::contains);
    }

    public static boolean checkImageFormats(String fileFullName) {
        String suffix = fileFullName.substring(fileFullName.lastIndexOf(".") + 1).toLowerCase();
        return SUPPORT_IMAGE_FORMATS.stream().anyMatch(suffix::contains);
    }

    public static boolean checkFileSize(MultipartFile file, long maxSize, long minSize) {
        //KB
        long size = file.getSize();
        //判断图片大小 单位KB
        if (size > maxSize || size < minSize) {
            return false;
        }
        return true;
    }

    public static boolean isImageBase64(String base64Str) {
        try {
            // 解码Base64字符串
            byte[] imageBytes = Base64.getDecoder().decode(base64Str);
            // 创建字节数组输入流
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            // 通过ImageIO读取图片
            BufferedImage image = ImageIO.read(bis);
            // 如果image不为null，说明是图片格式
            return image != null;
        } catch (Exception e) {
            // 解码过程中出现异常，不是有效的Base64图片格式
            return false;
        }
    }

    public static boolean getBase64ImageSize(String base64Image, long maxSize, long minSize) {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        // 获取图片的大小，即字节数组的长度
        long size = imageBytes.length;
        if (size > maxSize || size < minSize) {
            return false;
        }
        return true;
    }

    /**
     * 下载文件名重新编码
     *
     * @param response     响应对象
     * @param realFileName 真实文件名
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) throws UnsupportedEncodingException {
        String percentEncodedFileName = percentEncode(realFileName);

        StringBuilder contentDispositionValue = new StringBuilder();
        contentDispositionValue.append("attachment; filename=")
                .append(percentEncodedFileName)
                .append(";")
                .append("filename*=")
                .append("utf-8''")
                .append(percentEncodedFileName);

        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
        response.setHeader("Content-disposition", contentDispositionValue.toString());
        response.setHeader("download-filename", percentEncodedFileName);
    }

    /**
     * 百分号编码工具方法
     *
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        return encode.replaceAll("\\+", "%20");
    }

	public static MultipartFile getMultipartFile(File file) {
		FileItem item = new DiskFileItemFactory().createItem("file", MediaType.MULTIPART_FORM_DATA_VALUE,true,file.getName());
		try (InputStream input = Files.newInputStream(file.toPath()); OutputStream os = item.getOutputStream()){
			IOUtils.copyLarge(input,os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new CommonsMultipartFile(item);
	}
}
