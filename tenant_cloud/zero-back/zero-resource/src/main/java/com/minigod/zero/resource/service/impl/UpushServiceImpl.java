package com.minigod.zero.resource.service.impl;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.StringPool;
import com.minigod.zero.resource.dto.UpushDTO;
import com.minigod.zero.resource.enums.OsTypeEnum;
import com.minigod.zero.resource.service.IUpushService;
import com.minigod.zero.resource.utils.upush.UmPushTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Base64;

/**
 * @author Zhe.Xiao
 */
@Service
@Slf4j
public class UpushServiceImpl implements IUpushService {

	@Resource
	private UmPushTemplate umPushTemplate;

	@Override
	public R sendByDevice(UpushDTO upushDTO) {
		try {
			if (upushDTO.getDeviceTokenList().size() == 1) {
				return sendUnicast(upushDTO);
			} else {
				return sendListcast(upushDTO);
			}
		} catch (Exception e) {
			log.error("upush osType:{} sendByDevice error:{}", upushDTO.getOsType(), e.getMessage(), e);
			return R.fail(e.getMessage());
		}
	}

	@Override
	public R sendByApp(UpushDTO upushDTO) {
		try {
			if (upushDTO.getOsType() == null) {
				upushDTO.setOsType(OsTypeEnum.OS_ALL.getTypeValue());
			}
			return sendBroadcast(upushDTO);
		} catch (Exception e) {
			log.error("upush osType:{} sendByApp error:{}", upushDTO.getOsType(), e.getMessage(), e);
			return R.fail(e.getMessage());
		}
	}

	@Override
	public R sendByTags(UpushDTO upushDTO) {
		try {
			if (upushDTO.getOsType() == null) {
				upushDTO.setOsType(OsTypeEnum.OS_ALL.getTypeValue());
			}
			return sendGroupcast(upushDTO);
		} catch (Exception e) {
			log.error("upush osType:{} sendByTags error:{}", upushDTO.getOsType(), e.getMessage(), e);
			return R.fail(e.getMessage());
		}
	}


	private R sendUnicast(UpushDTO upushDTO) {
		String deviceToken = upushDTO.getDeviceTokenList().get(0);

		String[] temp = decodeAuthorization(upushDTO.getAuthorization());
		if (temp == null) {
			return R.fail("authorization error");
		}
		String appKey = temp[0];
		String masterSecret = temp[1];

		boolean success;
		if (OsTypeEnum.OS_ANDROID.getTypeValue().equals(upushDTO.getOsType())) {
			success = umPushTemplate.sendAndroidUnicast(deviceToken, upushDTO.getTitle(), upushDTO.getText(), upushDTO.getExtra(), appKey, masterSecret, upushDTO.getProdMode());
		} else {
			success = umPushTemplate.sendIOSUnicast(deviceToken, upushDTO.getTitle(), "", upushDTO.getText(), upushDTO.getExtra(), appKey, masterSecret, upushDTO.getProdMode());
		}
		return success ? R.success() : R.fail();
	}

	private R sendListcast(UpushDTO upushDTO) {
		String deviceToken = String.join(",", upushDTO.getDeviceTokenList());

		String[] temp = decodeAuthorization(upushDTO.getAuthorization());
		if (temp == null) {
			return R.fail("authorization error");
		}
		String appKey = temp[0];
		String masterSecret = temp[1];

		boolean success;
		if (OsTypeEnum.OS_ANDROID.getTypeValue().equals(upushDTO.getOsType())) {
			success = umPushTemplate.sendAndroidListcast(deviceToken, upushDTO.getTitle(), upushDTO.getText(), upushDTO.getExtra(), appKey, masterSecret, upushDTO.getProdMode());
		} else {
			success = umPushTemplate.sendIOSListcast(deviceToken, upushDTO.getTitle(), "", upushDTO.getText(), upushDTO.getExtra(), appKey, masterSecret, upushDTO.getProdMode());
		}
		return success ? R.success() : R.fail();
	}

	private R sendBroadcast(UpushDTO upushDTO) {
		String[] temp = decodeAuthorization(upushDTO.getAuthorization());
		if (temp == null) {
			return R.fail("authorization error");
		}
		String appKey = temp[0];
		String masterSecret = temp[1];

		boolean success;
		if (OsTypeEnum.OS_ANDROID.getTypeValue().equals(upushDTO.getOsType())) {
			success = umPushTemplate.sendAndroidBroadcast(upushDTO.getTitle(), upushDTO.getText(), upushDTO.getExtra(), appKey, masterSecret, upushDTO.getProdMode());
		} else if (OsTypeEnum.OS_IOS.getTypeValue().equals(upushDTO.getOsType())) {
			success = umPushTemplate.sendIOSBroadcast(upushDTO.getTitle(), "", upushDTO.getText(), upushDTO.getExtra(), appKey, masterSecret, upushDTO.getProdMode());
		} else {
			success = umPushTemplate.sendAndroidBroadcast(upushDTO.getTitle(), upushDTO.getText(), upushDTO.getExtra(), appKey, masterSecret, upushDTO.getProdMode());
			if (!success) return R.fail();
			success = umPushTemplate.sendIOSBroadcast(upushDTO.getTitle(), "", upushDTO.getText(), upushDTO.getExtra(), appKey, masterSecret, upushDTO.getProdMode());
		}
		return success ? R.success() : R.fail();
	}

	private R sendGroupcast(UpushDTO upushDTO) {
		String[] temp = decodeAuthorization(upushDTO.getAuthorization());
		if (temp == null) {
			return R.fail("authorization error");
		}
		String appKey = temp[0];
		String masterSecret = temp[1];

		boolean success;
		if (OsTypeEnum.OS_ANDROID.getTypeValue().equals(upushDTO.getOsType())) {
			success = umPushTemplate.sendAndroidGroupcast(upushDTO.getTagList(), upushDTO.getTitle(), upushDTO.getText(), upushDTO.getExtra(), appKey, masterSecret, upushDTO.getProdMode());
		} else if (OsTypeEnum.OS_IOS.getTypeValue().equals(upushDTO.getOsType())) {
			success = umPushTemplate.sendIOSGroupcast(upushDTO.getTagList(), upushDTO.getTitle(), "", upushDTO.getText(), upushDTO.getExtra(), appKey, masterSecret, upushDTO.getProdMode());
		} else {
			success = umPushTemplate.sendAndroidGroupcast(upushDTO.getTagList(), upushDTO.getTitle(), upushDTO.getText(), upushDTO.getExtra(), appKey, masterSecret, upushDTO.getProdMode());
			if (!success) return R.fail();
			success = umPushTemplate.sendIOSGroupcast(upushDTO.getTagList(), upushDTO.getTitle(), "", upushDTO.getText(), upushDTO.getExtra(), appKey, masterSecret, upushDTO.getProdMode());
		}
		return success ? R.success() : R.fail();
	}

	private String[] decodeAuthorization(String authorization) {
		try {
			String decodeStr = new String(Base64.getDecoder().decode(authorization));
			String[] temp = decodeStr.split(StringPool.COLON);
			return temp;
		} catch (Exception e) {
			log.error("upush decode authorization error:{}", e.getMessage(), e);
			return null;
		}
	}

}
