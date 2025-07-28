package com.minigod.zero.biz.common.utils;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.Map;

/**
 * @author  sunline
 * @createDate 2017/3/6
 * @description
 * @email justbelyf@gmail.com
 */

public class PdfOperater {
	private final static int FIELD_TYPE_SIGNATURE = 7;

    /*private final static int FIELD_TYPE_FIELD_TYPE_NONE = 0;
    private final static int FIELD_TYPE_BUTTON = 1;
    private final static int FIELD_TYPE_CHECK_BOX = 2;
    private final static int FIELD_TYPE_RADIO_BUTTON = 3;
    private final static int FIELD_TYPE_TEXT_FIELD = 4;
    private final static int FIELD_TYPE_LIST_BOX = 5;
    private final static int FIELD_TYPE_COMBO_BOX = 6;

    private static BaseFont defaultFont;

    static {
        try {
            defaultFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static boolean fillFile(String pdfFilePath, String outputPath, Map<String, Object> data) {
        Resource resource = new ClassPathResource(pdfFilePath);
        InputStream pdfInputStream = null;
        try {
            pdfInputStream = resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PdfReader pdfReader = null;
        try {
            pdfReader = new PdfReader(pdfInputStream);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfStamper pdfStamper = new PdfStamper(pdfReader, bos);
            AcroFields fields = pdfStamper.getAcroFields();
            Map<String, AcroFields.Item> fieldsMap = fields.getFields();
            fields.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
            for (Map.Entry<String, AcroFields.Item> entry : fieldsMap.entrySet()) {
                if (null != fields.getField(entry.getKey()) && StringUtils.isNoneBlank(fields.getField(entry.getKey()))) {
                    continue;
                }

                if (null != data.get(entry.getKey()) && StringUtils.isNoneBlank(data.get(entry.getKey()).toString())) {
                    if (FIELD_TYPE_SIGNATURE == fields.getFieldType(entry.getKey())) {
                        signatureProcess(pdfStamper, entry.getKey(), data.get(entry.getKey()).toString());
                        continue;
                    }

                    fields.setField(entry.getKey(), data.get(entry.getKey()).toString(), true);
                }
            }

            pdfStamper.setFormFlattening(true);
            pdfStamper.close();
            pdfReader.close();
            if (!FileOperaterUtil.fileUpload(outputPath, bos.toByteArray())) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (pdfReader != null) {
                pdfReader.close();
            }
        }

        return true;
    }

    private static void signatureProcess(PdfStamper stamper, String fieldName, String value) throws Exception {
        AcroFields form = stamper.getAcroFields();

        // 通过域名获取所在页和坐标，左下角为起点
        AcroFields.FieldPosition fieldPosition = form.getFieldPositions(fieldName).get(0);

        // 读图片
        Image image = Image.getInstance(value);
        // 获取操作的页面
        PdfContentByte under = stamper.getOverContent(fieldPosition.page);
        // 根据域的大小缩放图片
        image.scaleToFit(fieldPosition.position.getWidth(), fieldPosition.position.getHeight());
        // 添加图片
        image.setAbsolutePosition(fieldPosition.position.getLeft(), fieldPosition.position.getBottom());
        under.addImage(image);

    }

    /**
     * pdf合成图片
     *
     * @param urlImg 图片路径
     * @param urlPdf pdf文件路径
     * @throws Exception
     */
    public static void imagePdf(String urlImg, String urlPdf, String outPath, String fileName) throws Exception {
//        // 获取去除后缀的文件路径
//        String fileName = urlPdf.substring(0, urlPdf.lastIndexOf("."));
//        // 把截取的路径，后面追加四位随机数
//        String pdfUrl = fileName + (int) ((Math.random() * 9 + 1) * 1000) + ".pdf";

        // 检查路径是否存在
        File filePath = new File(outPath);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        // 要加水印的原pdf文件路径
        PdfReader reader = new PdfReader(urlPdf, "PDF".getBytes());
        // 加了水印后要输出的路径
//        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(pdfUrl));
        String outputFile = outPath + fileName;
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(outputFile));
        // 插入水印
        Image img = Image.getInstance(urlImg);
        // 原pdf文件的总页数
        int pageSize = reader.getNumberOfPages();
        // 印章位置
        img.setAbsolutePosition(100, 100);
        // 印章大小
        img.scalePercent(20, 20);
        for (int i = 1; i <= pageSize; i++) {

            if (4 == i || 6 == i || 7 == i || 9 == i) {

                //背景被覆盖
                PdfContentByte under = stamp.getUnderContent(i);
                // 文字被覆盖
//                PdfContentByte under = stamp.getOverContent(i);
                // 添加电子印章
                under.addImage(img);
            }
        }
        // 关闭
        stamp.close();
        reader.close();
    }
}
