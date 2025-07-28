package com.minigod.zero.manage.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhe.Xiao
 * 通知管理 发送页面读取文件
 */
@Slf4j
public class FileReadUtil {

    public static List<Long> getUserIdsFromFile(MultipartFile file) throws Exception {
        List<Long> lstUserIds = new ArrayList<>();
        // 读取xls文件内容
        InputStream input = file.getInputStream();
        XSSFWorkbook workBook = new XSSFWorkbook(input);
        XSSFSheet sheet = workBook.getSheetAt(0);
        if (sheet != null) {
            int rows = sheet.getPhysicalNumberOfRows();
            for (int i = 1; i < rows; i++) {
                XSSFRow row = sheet.getRow(i);
                if (row != null) {

                    for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                        try {
                            XSSFCell cell = row.getCell(j);
                            if (cell != null) {
                                cell.setCellType(Cell.CELL_TYPE_STRING);
                                if (!StringUtils.isEmpty(cell.toString())) {
									Long e = Long.valueOf(cell.toString().trim());
									log.info("读取到的用户ID："+e);
									lstUserIds.add(e);
                                }
                            }
                        } catch (Exception e) {
                            log.error("读取列值异常：", e);
                        }
                    }
                }

            }
        }
        return lstUserIds;
    }

    public static List<String> getCellPhoneOrEmailFromFile(MultipartFile file) {
        List<String> lstphones = new ArrayList<String>();
        // 读取xls文件内容
        try {
            InputStream input = file.getInputStream();
            XSSFWorkbook workBook = new XSSFWorkbook(input);
            XSSFSheet sheet = workBook.getSheetAt(0);
            if (sheet != null) {
                int rows = sheet.getPhysicalNumberOfRows();
                for (int i = 1; i < rows; i++) {
                    XSSFRow row = sheet.getRow(i);
                    if (row != null) {

                        for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                            try {
                                XSSFCell cell = row.getCell(j);
                                if (cell != null) {
                                    cell.setCellType(Cell.CELL_TYPE_STRING);
                                    if (!StringUtils.isEmpty(cell.toString())) {
                                        lstphones.add(cell.toString().trim());
                                    }
                                }
                            } catch (Exception e) {
                                log.error("读取列值异常：", e);
                            }
                        }
                    }

                }
            }
        } catch (Exception e) {
            // 如果出现异常则丢空的回去
            lstphones.clear();
        }
        return lstphones;
    }

}
