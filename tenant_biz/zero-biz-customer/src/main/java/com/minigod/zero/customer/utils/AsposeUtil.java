package com.minigod.zero.customer.utils;

import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import com.aspose.words.*;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author zxq
 * @since 2024/5/22 15:07
 */
@Slf4j
public class AsposeUtil {

    /**
     * word是否加载了license
     */
    private static boolean wordLicense = false;

    /**
     * excel是否加载了license
     */
    private static boolean excelLicense = false;

    /**
     * 加载license
     *
     * @return
     */
    public static boolean initWordLicense() {
        if (wordLicense) {
            return true;
        }
        try {
            String buffer = "<License>" +
                    "  <Data>" +
                    "    <Products>" +
                    "      <Product>Aspose.Total for Java</Product>" +
                    "      <Product>Aspose.Words for Java</Product>" +
                    "    </Products>" +
                    "    <EditionType>Enterprise</EditionType>" +
                    "    <SubscriptionExpiry>20991231</SubscriptionExpiry>" +
                    "    <LicenseExpiry>20991231</LicenseExpiry>" +
                    "    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>" +
                    "  </Data>" +
                    "  <Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>" +
                    "</License>";
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.getBytes());
            License license = new License();
            license.setLicense(byteArrayInputStream);
            byteArrayInputStream.close();
            wordLicense = true;
        } catch (Exception e) {
            log.error("Word2PdfAsposeUtil License 加载失败！", e);
        }
        return wordLicense;
    }

    /**
     * 加载license
     *
     * @return
     */
    public static boolean initExcelLicense() {
        if (excelLicense) {
            return true;
        }
        try {
            String buffer = "<License>" +
                    "    <Data>" +
                    "        <Products>" +
                    "            <Product>Aspose.Total for Java</Product>" +
                    "            <Product>Aspose.Words for Java</Product>" +
                    "        </Products>" +
                    "        <EditionType>Enterprise</EditionType>" +
                    "        <SubscriptionExpiry>20991231</SubscriptionExpiry>" +
                    "        <LicenseExpiry>20991231</LicenseExpiry>" +
                    "        <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>" +
                    "    </Data>" +
                    "    <Signature>" +
                    "        sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=" +
                    "    </Signature>" +
                    "</License>";
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.getBytes());
            com.aspose.cells.License license = new com.aspose.cells.License();
            license.setLicense(byteArrayInputStream);
            byteArrayInputStream.close();
            excelLicense = true;
        } catch (Exception e) {
            log.error("Word2PdfAsposeUtil License 加载失败！", e);
        }
        return excelLicense;
    }

    public static boolean doc2pdf(String inPath, String outPath) {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!initWordLicense()) {
            return false;
        }
        FileOutputStream os = null;
        try {
            OsInfo info = SystemUtil.getOsInfo();
            if (info.isLinux()) {
                FontSettings fontSettings = FontSettings.getDefaultInstance();
                fontSettings.setFontsFolder("/usr/share/fonts/chinese/", true);
            }

            long old = System.currentTimeMillis();
            // 新建一个空白pdf文档
            File file = new File(outPath);
            os = new FileOutputStream(file);
            // Address是将要被转化的word文档
            Document doc = new Document(inPath);
            // 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            doc.save(os, SaveFormat.PDF);
            long now = System.currentTimeMillis();
            // 转化用时
            log.info("pdf转换成功，共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * excel 转为pdf 输出。
     *
     * @param sourceFilePath excel文件
     * @param desFilePath    pad 输出文件目录
     */
    public static void excel2pdf(String sourceFilePath, String desFilePath) {
        // 验证License 若不验证则转化出的pdf文档会有水印产生
        if (!initExcelLicense()) {
            return;
        }
        FileOutputStream fileOS = null;
        try {
            long old = System.currentTimeMillis();
            // 原始excel路径
            Workbook wb = new Workbook(sourceFilePath);

            fileOS = new FileOutputStream(desFilePath);
            PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
            // pdfSaveOptions.setOnePagePerSheet(true);
            int[] autoDrawSheets = {3};
            //当excel中对应的sheet页宽度太大时，在PDF中会拆断并分页。此处等比缩放。
            autoDraw(wb, autoDrawSheets);
            wb.save(fileOS, pdfSaveOptions);
            long now = System.currentTimeMillis();
            // 转化用时
            log.info("pdf转换成功，共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOS != null) {
                try {
                    fileOS.flush();
                    fileOS.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置打印的sheet 自动拉伸比例
     *
     * @param wb
     * @param page 自动拉伸的页的sheet数组
     */
    public static void autoDraw(Workbook wb, int[] page) {
        if (null != page && page.length > 0) {
            for (int i = 0; i < page.length; i++) {
                wb.getWorksheets().get(i).getHorizontalPageBreaks().clear();
                wb.getWorksheets().get(i).getVerticalPageBreaks().clear();
            }
        }
    }

}
