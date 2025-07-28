package com.minigod.zero.bpmn.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;


/**
 * @ClassName: ImageToPDF
 * @Description:
 * @Author chenyu
 * @Date 2022/9/24
 * @Version 1.0
 */
@Slf4j
public class ImageToPDF {

    /**
     * 图片列表转pdf
     * @param imageUrlList 图片目录
     * @param mOutputPdfFileName pdf文件路径
     * @return
     */
    public static File imageListToPdf(String imageUrlList, String mOutputPdfFileName) {
        mkdirPath(mOutputPdfFileName);
        Document doc = new Document(PageSize.A4, 0, 0, 0, 0); //new一个pdf文档
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(mOutputPdfFileName)); //pdf写入
            doc.open();//打开文档
            File file = new File(imageUrlList);
            File[] files = file.listFiles();
            for (File file1 : files) {  //循环图片List，将图片加入到pdf中
                if (file1.getName().endsWith(".png") || file1.getName().endsWith(".jpg") || file1.getName().endsWith(".gif")
                    || file1.getName().endsWith(".jpeg") || file1.getName().endsWith(".tif")) {
                    doc.newPage();  //在pdf创建一页
                    Image png1 = Image.getInstance(file1.getAbsolutePath()); //通过文件路径获取image
                    float heigth = png1.getHeight();
                    float width = png1.getWidth();
                    int percent = getPercent2(heigth, width);
                    png1.setAlignment(Image.MIDDLE);
                    png1.scalePercent(percent + 3);// 表示是原来图像的比例;
                    doc.add(png1);
                }
            }
            doc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File mOutputPdfFile = new File(mOutputPdfFileName);  //输出流
        return mOutputPdfFile; //反回文件输出流
    }

    private static void mkdirPath(String filePath){
        String fileSavePath = filePath;
        if (null != fileSavePath && fileSavePath.indexOf("/") != -1) {
            fileSavePath = fileSavePath.substring(0, fileSavePath.lastIndexOf("/"));
            File file = new File(fileSavePath);
            if(!file.exists()){
                file.mkdirs();
            }
        }
    }
    /**
     * 图片转pdf
     * @param imagePath 图片路径
     * @param mOutputPdfFileName pdf路径
     * @return
     */
    public static File imageToPdf(String imagePath, String mOutputPdfFileName) {
        mkdirPath(mOutputPdfFileName);
        Document doc = new Document(PageSize.A4, 0, 0, 0, 0); //new一个pdf文档
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(mOutputPdfFileName)); //pdf写入
            doc.open();//打开文档
            doc.newPage();  //在pdf创建一页
            Image png1 = Image.getInstance(imagePath); //通过文件路径获取image
            float heigth = png1.getHeight();
            float width = png1.getWidth();
            int percent = getPercent2(heigth, width);
            png1.setAlignment(Image.MIDDLE);
            png1.scalePercent(percent + 3);// 表示是原来图像的比例;
            doc.add(png1);
            doc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File mOutputPdfFile = new File(mOutputPdfFileName);  //输出流
        return mOutputPdfFile; //反回文件输出流
    }

    public static File imageUrlToPdf(String url, String mOutputPdfFileName) {
        mkdirPath(mOutputPdfFileName);
        Document doc = new Document(PageSize.A4, 0, 0, 0, 0); //new一个pdf文档
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(mOutputPdfFileName)); //pdf写入
            doc.open();//打开文档
            doc.newPage();  //在pdf创建一页
            URL url1 = new URL(url);
            Image png1 = Image.getInstance(url1); //通过文件路径获取image
            float heigth = png1.getHeight();
            float width = png1.getWidth();
            int percent = getPercent2(heigth, width);
            png1.setAlignment(Image.MIDDLE);
            png1.scalePercent(percent + 3);// 表示是原来图像的比例;
            doc.add(png1);
            doc.close();
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException",e);
        } catch (DocumentException e) {
            log.error("DocumentException",e);
        } catch (IOException e) {
            log.error("IOException",e);
        }
        File mOutputPdfFile = new File(mOutputPdfFileName);  //输出流
        return mOutputPdfFile; //反回文件输出流
    }
    public static int getPercent(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        if (h > w) {
            p2 = 297 / h * 100;
        } else {
            p2 = 210 / w * 100;
        }
        p = Math.round(p2);
        return p;
    }

    public static int getPercent2(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        p2 = 530 / w * 100;
        p = Math.round(p2);
        return p;
    }


//    public static void main(String[] args) {
//        imageListToPdf("/data/apps/report/plate/20220924", "/data/apps/report/plate/20220924/20220924.pdf");//生成pdf
//    }

}
