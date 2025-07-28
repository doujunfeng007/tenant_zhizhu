package com.minigod.zero.biz.common.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

/**
 * @description: 文件操作工具类
 * @author:  sunline
 * @date: 2020/10/23 10:48
 * @version: v1.0
 */

public class FileOperaterUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileOperaterUtil.class);

    public static String downloadFileByPath(String filePath) {
        String fileData = null;
        try {
            File imageFile = new File(filePath);
            if (imageFile.exists() && imageFile.isFile()) {
                FileInputStream imageFileRead = new FileInputStream(imageFile);
                int fileSize = imageFileRead.available();
                byte[] fileBytes = new byte[fileSize];

                int iHasRead = 0;
                int iOneRead = 0;
                while (iHasRead < fileSize) {
                    iOneRead = imageFileRead.read(fileBytes, iHasRead, fileSize - iHasRead);
                    // 已读取的字节数和要读的字节数相等或者已读取完成（=-1），则跳出
                    if (iOneRead < 0) {
                        break;
                    }

                    iHasRead += iOneRead;
                }

                // 注意：BASE64Encoder加密时，每76个字符就会加上回车换车符\r\n，
                // 但BASE64Decoder解密时会自动处理，故如果成对使用他们，则不需要处理回车换行符
                fileData = Base64.getEncoder().encodeToString(fileBytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileData;

    }

    public static String downloadFileByUrl(String fileUrl, String outPutPath) {
        try {
            String fileSuffixName = getFileExtendName(fileUrl);
            URL url = new URL(fileUrl);
            String fileName = "";
            if (null != fileUrl && fileUrl.indexOf("/") != -1) {
                fileName = url.getFile().substring(url.getFile().lastIndexOf("/") + 1, url.getFile().length() - (url.getFile().substring(url.getFile().lastIndexOf(".") + 1).length() + 1));
            }
            fileUpload(fileName + "." + fileSuffixName, outPutPath, url.openConnection().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean downloadFileByUrl(String fileUrl, String outPutPath, String fileName) {
        try {
            String fileSuffixName = getFileExtendName(fileUrl);
            URL url = new URL(fileUrl);
            return fileUpload(fileName + "." + fileSuffixName, outPutPath, url.openConnection().getInputStream());
        } catch (Exception e) {
            logger.error("上传图片[" + fileUrl + "]异常", e);
            return false;
        }
    }


    public static boolean fileUpload(String fileSavePath, byte[] fileDataByte) {
        File fileDir = new File(fileSavePath);
        if (!fileDir.getParentFile().exists()) {
            fileDir.getParentFile().mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileSavePath);
            fos.write(fileDataByte);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean fileUpload(String filePath, InputStream stream) {
        String fileSavePath = filePath;
        String fileName = "";
        if (null != filePath && filePath.indexOf("/") != -1) {
            fileSavePath = filePath.substring(0, filePath.lastIndexOf("/"));
            fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        }
        logger.info("fileSavePath:" + fileSavePath + "fileName:" + fileName);
        return fileUpload(fileName, fileSavePath, stream);
    }

    public static boolean fileUpload(String fileName, String fileSavePath, InputStream stream) {
        File fileDir = new File(fileSavePath);

        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        FileOutputStream fos = null;
        try {
            String outFileSavePath = fileDir.getPath() + "/" + fileName;
            fos = new FileOutputStream(outFileSavePath);
            // 每次读取的字节长度
            int n = 0;
            // 存储每次读取的内容
            byte[] bytes = new byte[1024];
            while ((n = stream.read(bytes)) != -1) {
                // 将读取的内容，写入到输出流当中
                fos.write(bytes, 0, n);
            }
            fos.close();// 关闭输入输出流
            stream.close();
        } catch (Exception e) {
            logger.error("上传文件异常", e);
            return false;
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (Exception e) {
                    logger.error("关闭流异常", e);
                }
            }
        }

        return true;
    }

    public static boolean fileUpload(String fileSavePath, MultipartFile multipartFile) {
        File fileDir = new File(fileSavePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        try {
            multipartFile.transferTo(fileDir);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean deleteFile(String fileSavePath) {
        File fileDir = new File(fileSavePath);
        if (fileDir.exists()) {
            return fileDir.delete();
        }
        return true;
    }

    public static String getFileExtendName(String fileFullName) {
        String extendName = "";
        if (null != fileFullName && fileFullName.indexOf(".") != -1) {
            extendName = fileFullName.substring(fileFullName.lastIndexOf(".") + 1);
        }

        return extendName;
    }


    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();

        return bos.toByteArray();
    }

    /**
     * url转变为MultipartFile对象
     *
     * @param url
     * @param fileName
     * @return
     * @throws Exception
     */
    public static MultipartFile createFileItem(String url, String fileName) {

        FileItem item = null;

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            // 设置应用程序要从网络连接读取数据
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();

                FileItemFactory factory = new DiskFileItemFactory(16, null);
                String textFieldName = "uploadfile";
                item = factory.createItem(textFieldName, ContentType.APPLICATION_OCTET_STREAM.toString(), false, fileName);
                OutputStream os = item.getOutputStream();

                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }

                os.close();
                is.close();
            }
        } catch (IOException e) {
            logger.error("文件下载失败", e);
        }

        return new CommonsMultipartFile(item);
    }

    /**
     * 通过图片url返回图片Bitmap
     *
     * @param path
     * @return
     */
    public static InputStream returnBitMap(String path) {
        URL url = null;
        InputStream is = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            // 利用HttpURLConnection对象,我们可以从网络中获取网页数据
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            // 得到网络返回的输入流
            is = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }
}
