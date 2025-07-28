package com.minigod.zero.platform.utils;

import com.minigod.zero.core.launch.constant.ESourceType;
import com.minigod.zero.core.launch.constant.TokenConstant;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.core.tool.utils.StringPool;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.platform.enums.PlatformOsTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class PlatformUtil {

	public static String getMsgStr(String content, List<String> list) {
		if (StringUtils.isEmpty(content) || list == null) {
			return null;
		}
		String message = null;
		Object[] objArg = list.toArray(new String[]{});
		message = MessageFormat.format(content, objArg);
		return message;
	}

	public static String formatString(String formatString, List<String> params) {
		formatString = formatString.replace("%i", "%s");
		Pattern p = Pattern.compile("%s");
		Matcher m = p.matcher(formatString);
		int count = 0;
		while (m.find()) {
			count++;
		}

		String[] values = null;
		if (CollectionUtil.isNotEmpty(params)) {
			values = params.toArray(new String[params.size()]);
		} else {
			values = new String[count];
		}

		int numPlaceholders = 0;
		int index = 0;
		while (index < formatString.length()) {
			index = formatString.indexOf("%", index);
			if (index == -1) {
				break;
			}
			numPlaceholders++;
			index++;
		}
		int numValues = values.length;

		if (numPlaceholders > numValues) {
			String[] extendedValues = new String[numPlaceholders];
			System.arraycopy(values, 0, extendedValues, 0, numValues);
			return String.format(formatString, extendedValues);
		} else {
			return String.format(formatString, values);
		}
	}

	public static String getSourceType() {
		try {
			String sourceType = WebUtil.getHeader(TokenConstant.SOURCE_TYPE);
			if (StringUtils.isBlank(sourceType)) {
				log.info("getSourceType blank");
			}
			return sourceType;
		} catch (Exception e) {
			log.error("getSourceType error:{}", e.getMessage(), e);
			return null;
		}
	}

	public static Integer getClientType() {
		String sourceType = getSourceType();
		Integer clientType = PlatformOsTypeEnum.OS_ALL.getTypeValue();
		if (StringUtils.isNotBlank(sourceType)) {
			if (sourceType.equals(ESourceType.AOS.getName())) {
				clientType = PlatformOsTypeEnum.OS_ANDROID.getTypeValue();
			}
			if (sourceType.equals(ESourceType.IOS.getName())) {
				clientType = PlatformOsTypeEnum.OS_IOS.getTypeValue();
			}
		}
		return clientType;
	}

	public static String getAuthorization(int osType, String iosAppKey, String iosMasterSecret, String aosAppKey, String aosMasterSecret) {
		String appKey = null;
		String masterSecret = null;
		if (PlatformOsTypeEnum.OS_IOS.getTypeValue() == osType) {
			appKey = iosAppKey;
			masterSecret = iosMasterSecret;
		} else {
			appKey = aosAppKey;
			masterSecret = aosMasterSecret;
		}
		return Base64.getEncoder().encodeToString(appKey.concat(StringPool.COLON).concat(masterSecret).getBytes());
	}

	public static String getDataMaskContent(String content) {
		String patternString = "<data_mask>(.*?)</data_mask>";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(content);
		StringBuffer output = new StringBuffer();
		while (matcher.find()) {
			String match = matcher.group(1);
			String replacement = "*".repeat(match.length());
			matcher.appendReplacement(output, replacement);
		}
		matcher.appendTail(output);
		return output.toString();
	}
}
