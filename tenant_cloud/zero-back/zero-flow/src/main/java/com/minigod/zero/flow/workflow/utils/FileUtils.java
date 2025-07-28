package com.minigod.zero.flow.workflow.utils;

import cn.hutool.core.io.FileUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件处理工具类
 *
 * @author zsdp
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils extends FileUtil {


    private final static List<String> SUPPORT_FORMATS = new ArrayList<>(Arrays.asList("doc,docx,xls,xlsx,ppt,pptx,pdf,txt,wmv,mp4,html,xml,jpg,jpeg,png".split(",")));
    private final static List<String> SUPPORT_FILE_FORMATS = new ArrayList<>(Arrays.asList("doc,docx,xls,xlsx,ppt,pptx,pdf,txt,wmv,mp4,html,xml".split(",")));
    private final static List<String> SUPPORT_IMAGE_FORMATS = new ArrayList<>(Arrays.asList("jpg,jpeg,png".split(",")));

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

}
