package com.minigod.zero.resource.utils.upush;

import com.minigod.zero.resource.dto.UpushDTO;
import com.minigod.zero.resource.enums.OsTypeEnum;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Zhe.Xiao
 */
public class UpushUtil {

	public static R validCommonParams(UpushDTO upushDTO) {
		if (upushDTO.getOsType() == null) {
			return R.fail("osType is null");
		}
		if (!OsTypeEnum.OS_ANDROID.getTypeValue().equals(upushDTO.getOsType()) && !OsTypeEnum.OS_IOS.getTypeValue().equals(upushDTO.getOsType())) {
			return R.fail("unknown osType");
		}
		if (StringUtils.isBlank(upushDTO.getAuthorization())) {
			return R.fail("authorization is blank");
		}
		if (StringUtil.isBlank(upushDTO.getTitle())) {
			return R.fail("title is blank");
		}
		if (StringUtil.isBlank(upushDTO.getText())) {
			return R.fail("text is blank");
		}
		return R.success();
	}
}
