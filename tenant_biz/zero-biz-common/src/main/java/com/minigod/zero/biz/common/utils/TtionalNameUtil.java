package com.minigod.zero.biz.common.utils;

import com.minigod.zero.core.tool.beans.QuotationVO;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.core.tool.constant.MktConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Desc: 简体和繁体查询
 * author:sunline
 * Date:2020/5/26 17:14
 */
@Service
public class TtionalNameUtil {

    private static final String BIG_ALPHA = "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ１２３４５６７８９０";
    private static final String LITTLE_ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

	public static String getStkNameFiled(StkInfo stkInfo, QuotationVO oQuotVO, String lang) {
		String oResult;
		if (lang != null && MktConstants.LANGUAGE_TRADITIONAL.equals(lang) && (stkInfo != null || oQuotVO != null)) {
			if (null != stkInfo && StringUtils.isNotBlank(stkInfo.getTraditionalNameLong())) {
				return replaceStr(stkInfo.getTraditionalNameLong());
			} else if (oQuotVO != null && StringUtils.isNotBlank(oQuotVO.getStkTraditionalNameLong())) {
				return replaceStr(oQuotVO.getStkTraditionalNameLong());
			}
		}
		if (lang != null && MktConstants.LANGUAGE_ENGLISH.equals(lang) && (stkInfo != null || oQuotVO != null)) {
			if (null != stkInfo && StringUtils.isNotBlank(stkInfo.getEngName())) {
				return replaceStr(stkInfo.getEngName());
			} else if (oQuotVO != null && StringUtils.isNotBlank(oQuotVO.getEngName())) {
                return replaceStr(oQuotVO.getEngName());
            }
		}

		if (null != stkInfo) {
			oResult = stkInfo.getStkName();
		} else {
			oResult = oQuotVO.getStkName();
		}
		return oResult;
	}

    public static String getStkNameOrTtional(String stkName, String stkTtionalName, String lang) {
        if (lang != null && MktConstants.LANGUAGE_TRADITIONAL.equals(lang) && StringUtils.isNotBlank(replaceStr(stkTtionalName))) {
            return replaceStr(stkTtionalName);
        } else {
            return stkName;
        }
    }
    public static String getStkNameOfLang(StkInfo stkInfo, String lang) {
        if (lang != null && MktConstants.LANGUAGE_TRADITIONAL.equals(lang) && StringUtils.isNotBlank(replaceStr(stkInfo.getTraditionalName()))) {
            return replaceStr(stkInfo.getTraditionalName());
        } else if (lang != null && MktConstants.LANGUAGE_ENGLISH.equals(lang) && StringUtils.isNotBlank(replaceStr(stkInfo.getEngName()))) {
            return stkInfo.getEngName();
        }else{
            return stkInfo.getStkName();
        }
    }

    public static String getStkNameOfLang(String stkName, String stkTtionalName, String engName, String lang) {
        if (lang != null && MktConstants.LANGUAGE_TRADITIONAL.equals(lang) && StringUtils.isNotBlank(replaceStr(stkTtionalName))) {
            return replaceStr(stkTtionalName);
        } else if (lang != null && MktConstants.LANGUAGE_ENGLISH.equals(lang) && StringUtils.isNotBlank(replaceStr(engName))) {
            return engName;
        }else{
            return stkName;
        }
    }

    private static String replaceStr(String stkName) {
        stkName = trimRightBigSpace(stkName);
        stkName = convertBigAlpha(stkName);
        return stkName;
    }

    /**
     * 移除末尾全角空格
     *
     * @param securityNameGB
     * @return
     */
    private static String trimRightBigSpace(String securityNameGB) {
        if (securityNameGB == null || securityNameGB.length() <= 0) {
            return securityNameGB;
        }
        final char SPACE = '　';
        int spaceLen = 0;
        for (int i = securityNameGB.length() - 1; i >= 0; i--) {
            if (securityNameGB.charAt(i) != SPACE) {
                break;
            } else {
                spaceLen++;
            }
        }
        return securityNameGB.substring(0, securityNameGB.length() - spaceLen);
    }

    private static String convertBigAlpha(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            int idx = BIG_ALPHA.indexOf(ch);
            if (idx >= 0 && idx < LITTLE_ALPHA.length()) {
                ch = LITTLE_ALPHA.charAt(idx);
            }
            sb.append(ch);
        }
        return sb.toString();
    }
}
