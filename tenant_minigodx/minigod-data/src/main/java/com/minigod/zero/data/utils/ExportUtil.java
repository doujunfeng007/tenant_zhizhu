package com.minigod.zero.data.utils;

import cn.afterturn.easypoi.word.WordExportUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * 导出工具类
 *
 * @author zxq
 * @date 2024/5/22
 **/
@Slf4j
public class ExportUtil {
	/**
	 * 导出模板word
	 *
	 * @param templateFileCode 模板编码
	 * @param saveDir          保存地址
	 * @param fileName         保存文件名称
	 * @param params           模板参数
	 * @return
	 */
	public static String exportTemplateWord(String templateFileCode, String saveDir, String fileName, Map<String, Object> params) {
		if (!saveDir.endsWith("/")) {
			saveDir = saveDir + File.separator;
		}

		File dir = new File(saveDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String savePath = saveDir + fileName;
		try {
			XWPFDocument doc = WordExportUtil.exportWord07(templateFileCode, params);
			FileOutputStream fos = new FileOutputStream(savePath);
			doc.write(fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return savePath;
	}
}
