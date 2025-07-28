package com.minigod.zero.log.excel;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import org.apache.poi.ss.usermodel.*;

/**
 * Excel样式配置
 *
 * @author eric
 * @since 2024-08-30 17:07:10
 */
public class StyleUtils {
	/**
	 * 标题样式
	 *
	 * @return
	 */
	public static WriteCellStyle getHeadStyle() {
		// 头的策略
		WriteCellStyle headWriteCellStyle = new WriteCellStyle();
		// 背景颜色
		headWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		headWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);

		// 字体
		WriteFont headWriteFont = new WriteFont();
		headWriteFont.setFontName("宋体");//设置字体名字
		headWriteFont.setFontHeightInPoints((short) 10);//设置字体大小
		headWriteFont.setBold(true);//字体加粗
		headWriteCellStyle.setWriteFont(headWriteFont); //在样式用应用设置的字体;

		// 样式
		headWriteCellStyle.setBorderBottom(BorderStyle.THIN);//设置底边框;
		headWriteCellStyle.setBottomBorderColor((short) 0);//设置底边框颜色;
		headWriteCellStyle.setBorderLeft(BorderStyle.THIN);  //设置左边框;
		headWriteCellStyle.setLeftBorderColor((short) 0);//设置左边框颜色;
		headWriteCellStyle.setBorderRight(BorderStyle.THIN);//设置右边框;
		headWriteCellStyle.setRightBorderColor((short) 0);//设置右边框颜色;
		headWriteCellStyle.setBorderTop(BorderStyle.THIN);//设置顶边框;
		headWriteCellStyle.setTopBorderColor((short) 0); //设置顶边框颜色;

		headWriteCellStyle.setWrapped(true);  //设置自动换行;

		headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);//设置水平对齐的样式为居中对齐;
		headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直对齐的样式为居中对齐;
		headWriteCellStyle.setShrinkToFit(true);//设置文本收缩至合适

		return headWriteCellStyle;
	}

	/**
	 * 内容样式
	 *
	 * @return
	 */
	public static WriteCellStyle getContentStyle() {
		// 内容的策略
		WriteCellStyle contentWriteCellStyle = new WriteCellStyle();

		// 设置字体
		WriteFont contentWriteFont = new WriteFont();
		contentWriteFont.setFontHeightInPoints((short) 9);//设置字体大小
		contentWriteFont.setFontName("宋体"); //设置字体名字
		contentWriteCellStyle.setWriteFont(contentWriteFont);//在样式用应用设置的字体;

		//设置样式;
		contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);//设置底边框;
		contentWriteCellStyle.setBottomBorderColor((short) 0);//设置底边框颜色;
		contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);  //设置左边框;
		contentWriteCellStyle.setLeftBorderColor((short) 0);//设置左边框颜色;
		contentWriteCellStyle.setBorderRight(BorderStyle.THIN);//设置右边框;
		contentWriteCellStyle.setRightBorderColor((short) 0);//设置右边框颜色;
		contentWriteCellStyle.setBorderTop(BorderStyle.THIN);//设置顶边框;
		contentWriteCellStyle.setTopBorderColor((short) 0); ///设置顶边框颜色;

		contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);// 水平居中
		contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
		contentWriteCellStyle.setWrapped(true); //设置自动换行;

		contentWriteCellStyle.setDataFormat((short) 49);//设置单元格格式是：文本格式，方式长数字文本科学计数法

		contentWriteCellStyle.setShrinkToFit(true);//设置文本收缩至合适

		return contentWriteCellStyle;
	}
}
