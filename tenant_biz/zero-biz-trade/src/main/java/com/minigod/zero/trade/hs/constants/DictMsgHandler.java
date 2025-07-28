package com.minigod.zero.trade.hs.constants;

import com.minigod.zero.core.tool.constant.MktConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 加载业务数据字典
 * @author sunline
 */
public class DictMsgHandler {
    public static Logger log = LoggerFactory.getLogger(DictMsgHandler.class);
    public static Map<String, DictItem> dictMap = new HashMap<String, DictItem>();

    static {
        loadDictionaryData();
    }

    private static void loadDictionaryData() {
        InputStream in = null;
        try {
            long beginMills = System.currentTimeMillis();
            String dictionaryFile = "/dictFile/data_dictionary.xlsx";
            in = DictMsgHandler.class.getResourceAsStream(dictionaryFile);
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            Sheet sheet = workbook.getSheetAt(0);
            for (int rowIndex = 1; rowIndex <= sheet.getPhysicalNumberOfRows(); rowIndex++) {
                // 从第一行开始
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                String dictCode = StringUtils.trim(row.getCell(0).getStringCellValue());
                String valueCode = StringUtils.trim(row.getCell(1).getStringCellValue());
                String bizKey = buildBizKey(dictCode, valueCode);
                DictItem item = new DictItem();
                item.setSc(StringUtils.trim(row.getCell(2).getStringCellValue()));
                item.setEn(StringUtils.trim(row.getCell(3).getStringCellValue()));
                item.setCantonese(StringUtils.trim(row.getCell(4).getStringCellValue()));
                dictMap.put(bizKey, item);
            }
            long endMills = System.currentTimeMillis();
//            log.info("dictMap.size {} byte", JSON.toJSONString(dictMap).getBytes().length);
            log.info("loadDictionaryData spend {} ms", endMills-beginMills);
        } catch (Exception e) {
            log.error("loadDictionaryData error", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * 构建缓存业务key
     */
    private static String buildBizKey(String dictCode, String valueCode) {
        return dictCode + "_" + valueCode;
    }

    public static String getValueName(String dictCode, String valueCode, String lang, String defaultVal) {
        String message;
        String bizKey = buildBizKey(dictCode, valueCode);
        if (dictMap.containsKey(bizKey)) {
            DictItem dictItem = dictMap.get(bizKey);
            if (MktConstants.LANGUAGE_TRADITIONAL.equals(lang)) {
                message = dictItem.getCantonese();
            } else if (MktConstants.LANGUAGE_ENGLISH.equals(lang)) {
                message = dictItem.getEn();
            } else {
                message = dictItem.getSc();
            }
        } else {
            message = defaultVal;
        }
        return message;
    }

    public static String getValueCode(String dictCode, String valueName, String lang) {
        return null;
    }

}
