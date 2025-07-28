package com.minigod.zero.manage.utils;

import com.google.common.collect.Lists;
import com.minigod.zero.core.tool.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import static com.minigod.zero.core.tool.utils.DateUtil.PATTERN_DATE;

public class CSVoperater {

	@SuppressWarnings("resource")
	public static List<Object> parseWorkBook2(InputStream inputStream, File file, int start) throws IOException,
		Exception {

		//InputStream is = new FileInputStream(file);
		Workbook workbook = null;
		try {
			// workbook = WorkbookFactory.create(is);
			if (inputStream != null) {
				workbook = new XSSFWorkbook(inputStream);
			} else {
				workbook = new XSSFWorkbook(new FileInputStream(file));
			}
		} catch (Exception ex) {
			if (inputStream != null) {
				workbook = new HSSFWorkbook(inputStream);
			} else {
				workbook = new HSSFWorkbook(new FileInputStream(file));
			}
		}
		List<Object> list = Lists.newArrayList();
		if (workbook != null) {
			Sheet sheet = workbook.getSheetAt(0);
			if (sheet != null) {
				int rows = sheet.getPhysicalNumberOfRows();
				for (int i = 0; i < rows; i++) {
					Row row = sheet.getRow(i);
					if (row != null) {

						for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
							try {
								Cell cell = row.getCell(j);
								if (cell != null) {
									cell.getCellType();
									cell.setCellType(Cell.CELL_TYPE_STRING);
									if (!StringUtils.isEmpty(cell.toString())) {
										list.add(cell.toString().trim());
									}
								}
							} catch (Exception e) {

							}
						}
					}

				}
			}
		}
		return list;

	}

	/**
	 * 根据Excel路径，解析得到数据
	 *
	 * @return List<Map < String, Object>> list：行，map为列
	 * @throws IOException
	 */
	public static List<Map<String, Object>> parseWorkBook(InputStream inputStream, File file, int start) throws IOException,
		Exception {

//        InputStream is = new FileInputStream(file);
		Workbook workbook = null;
		try {
//            workbook = WorkbookFactory.create(is);
			if (inputStream != null) {
				workbook = new XSSFWorkbook(inputStream);
			} else {
				workbook = new XSSFWorkbook(new FileInputStream(file));
			}
		} catch (Exception ex) {
			if (inputStream != null) {
				workbook = new HSSFWorkbook(inputStream);
			} else {
				workbook = new HSSFWorkbook(new FileInputStream(file));
			}
		}

		// 存放返回值
		List<Map<String, Object>> retValueList = new ArrayList<Map<String, Object>>();
		// 存放column的英文名称
		List<String> columnNameList = new ArrayList<String>();
		if (workbook != null) {
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				Row row = iterator.next();
				if (i < start) {
					i++;
					continue;
				}

				/** 增强FOR会忽略空格 **/
				//                Iterator<Cell> cellIterator = row.iterator();
				/** end by wangqy **/

				int j = 0;
				Map<String, Object> map = new LinkedHashMap<String, Object>();

				if (i == start) {

					/** 增强FOR会忽略空格 **/
					//                    while (cellIterator.hasNext()) {
					//                        Cell cell = cellIterator.next();
					/** end by wangqy **/

					for (int n = row.getFirstCellNum(); n < row.getLastCellNum(); n++) {
						Cell cell = row.getCell(n, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
						//                        Cell cell = cellIterator.next();
						Object columnName = getCellValue(cell, workbook);
						if (columnName != null) {
							columnNameList.add(columnName.toString());
						}
					}
				} else if (i > start) {

					/** 增强FOR会忽略空格 **/
					//                    while (cellIterator.hasNext()) {
					//                        Cell cell = cellIterator.next();
					/** end by wangqy **/

					for (int n = row.getFirstCellNum(); n < row.getLastCellNum(); n++) {
						Cell cell = row.getCell(n, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

						Object columnValue = getCellValue(cell, workbook);

						//                     if (j != cell.getColumnIndex()) {
						if (j != n) {
							j++;
						}

						// 可能会有多余的数据，如表头为空，但下面有数据的
						if (columnNameList != null && columnNameList.size() > 0
							&& j < columnNameList.size()) {
							String columnName = columnNameList.get(j);

							/** 允许空字符  **/
							if (columnName != null
								&& columnValue != null) {

								map.put(columnName, columnValue);

							} else {
								map.put(columnName, "");
							}
						}
						j++;
					}

					if (map != null && !map.isEmpty()) {
						retValueList.add(map);
					}
				}
				i++;
			}
		}

		return retValueList;
	}

	/**
	 * 取得excel中单元格的值 <br>
	 * 数字类型的数据都被转成BigDecimal，该类型可以很方便的转成long,int,double，而且其toString方法不会有多余的0，
	 * 也不会被科学计数法表示
	 */
	public static Object getCellValue(Cell cell, Workbook workbook) {
		Object val = null;

		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

		if (cell != null) {
			int cellValue = evaluator.evaluateInCell(cell).getCellType();
			//switch (cell.getCellType()) {
			switch (cellValue) {
				case HSSFCell.CELL_TYPE_BLANK:
				case HSSFCell.CELL_TYPE_ERROR:
				case HSSFCell.CELL_TYPE_FORMULA:
				case HSSFCell.ENCODING_UNCHANGED:
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					val = Boolean.valueOf(cell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					//                    double dbVal = cell.getNumericCellValue();
					//                    DecimalFormat df = new DecimalFormat("0");
					//                    val = String.valueOf(df.format(dbVal));
					//val = new BigDecimal(dbVal);


					if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
//                    	Date date = org.apache.poi.ss.usermodel.DateUtil
//                    			.getJavaDate(cell.getNumericCellValue());
						Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
						val = DateUtil.format(date, PATTERN_DATE);
					} else {
						val = cell.getNumericCellValue();
						if (val != null && val.toString().indexOf("E") != -1) {
							BigDecimal g = new BigDecimal(val.toString());
							val = String.valueOf(g.toPlainString());
						} else if (val != null && val.toString().endsWith(".0")) {
							double dbVal = cell.getNumericCellValue();
							DecimalFormat df = new DecimalFormat("0");
							val = String.valueOf(df.format(dbVal));
						}
					}
					break;
				case HSSFCell.CELL_TYPE_STRING:
					val = cell.getStringCellValue();
					break;
				default:
					val = cell.getStringCellValue();
			}
		}
		return val;
	}

	/**
	 * 根据一个字段的名称，用反射设置字段的值
	 *
	 * @param target    目标对象
	 * @param fieldName 目标对象字段名称
	 * @param value     目标对象字段值
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws NoSuchFieldException
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static void setMethodValue(Object target, String fieldName, Object value)
		throws IllegalAccessException,
		InvocationTargetException,
		NoSuchMethodException,
		NoSuchFieldException {
		// 得到方法名称
		if (!StringUtils.isNotEmpty(fieldName)) {
			String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
			// 得到set方法
			try {
				// 得到字段类型
				Field targetField = target.getClass().getDeclaredField(fieldName);
				Method setMethod = target.getClass().getDeclaredMethod(setMethodName,
					targetField.getType());
				// 数据类型转换
				Class typeStr = targetField.getType();
				if (typeStr == Short.class) {
					value = Short.valueOf(value.toString());
				}
				if (typeStr == Long.class) {
					value = Long.valueOf(value.toString());
				}
				// 设置值
				if (value != null) {
					setMethod.invoke(target, String.valueOf(value));
				}
			} catch (SecurityException e) {
				throw e;
				// e.printStackTrace();
			} catch (NoSuchMethodException e) {
				throw e;
				// e.printStackTrace();
			} catch (IllegalArgumentException e) {
				throw e;
				// e.printStackTrace();
			} catch (NoSuchFieldException e) {
				throw e;
				// e.printStackTrace();
			}
		}
	}

}
