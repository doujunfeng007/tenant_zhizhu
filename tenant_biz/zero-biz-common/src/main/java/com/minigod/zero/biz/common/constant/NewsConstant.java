package com.minigod.zero.biz.common.constant;

import com.minigod.zero.biz.common.enums.ImportantNewsEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class NewsConstant {
	public static double DEGREE = 0.85;
	public static int TITLE_WIDTH = 246;
	public static int TITLE_HEIGHT = 180;
	public static int HEAD_WIDTH = 1242;
	public static int HEAD_HEIGHT = 600;

	public static List<String> importantNewsCategoryList = Arrays.asList(ImportantNewsEnum.HK_STOCK_TRENDS.getCategory()
		, ImportantNewsEnum.OVERSEAS_TRENDS.getCategory()
		, ImportantNewsEnum.INLAND_DEPTH.getCategory()
		, ImportantNewsEnum.OTHER.getCategory()
		, ImportantNewsEnum.COMPREHENSIVE_DEPTH.getCategory()
		, ImportantNewsEnum.COMPREHENSIVE_TRENDS.getCategory()
		, ImportantNewsEnum.OVERSEAS_DEPTH.getCategory()
		, ImportantNewsEnum.HK_AND_MO_DEPTH.getCategory()
		, ImportantNewsEnum.MAINLAND_TRENDS.getCategory()
	);

	public static Boolean isInCategory(String category){
		if (StringUtils.isBlank(category)){
			return Boolean.FALSE;
		}

		if (importantNewsCategoryList.contains(category.trim())){
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}
}
