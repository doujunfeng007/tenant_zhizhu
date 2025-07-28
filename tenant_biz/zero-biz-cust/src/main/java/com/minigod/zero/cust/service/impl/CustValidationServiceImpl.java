package com.minigod.zero.cust.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.cust.service.ICustInfoService;
import com.minigod.zero.cust.service.ICustValidationService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.enums.CustStaticType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author: chen
 * @Project: zero
 * @Pcakage: com.minigod.zero.cust.service.impl.CustValidationServiceImpl
 * @Date: 2023年02月15日 20:32
 * @Description:  校验参数或者状态等等
 */
@Service
@Slf4j
public class CustValidationServiceImpl implements ICustValidationService {


	@Resource
	private ICustInfoService custInfoService;

	/*@Override
	public R validationRegPwd(String pwd,String key) {
		try {
			pwd = ProtocolUtils.getPwd(pwd.trim(), key);
		} catch (Exception e) {
			log.info("密码解析异常:" + pwd + ">>>" + key);
			return R.fail(CustStaticType.CodeType.PWD_ERROR.getCode(), CustStaticType.CodeType.PWD_ERROR.getMessage());
		}
		*//**
		 * 校验密码长度，长度[6,16]
		 *//*
		if (pwd.length() > 16 || pwd.length() < 6) {
			return R.fail(CustStaticType.CodeType.REGISTER_PHONE_LENGTH_ERROR.getCode(), CustStaticType.CodeType.REGISTER_PHONE_LENGTH_ERROR.getMessage());
		}
		*//**
		 * 密码不能全是空格
		 *//*
		if (StringUtils.isBlank(pwd)) {
			return R.fail(CustStaticType.CodeType.REGISTER_PHONE_LENGTH_ERROR.getCode(), CustStaticType.CodeType.REGISTER_PHONE_LENGTH_ERROR.getMessage());
		}
		return R.data(pwd);
	}*/


	@Override
	public R validatePhone(String certCode) {
		try {
			/**
			 * 验证是否为数字给前端提示用户名密码错误
			 */
			Pattern pattern = Pattern.compile("\\d+");
			Matcher matcher = pattern.matcher(certCode);
			if (!matcher.matches()) {
				return R.fail(CustStaticType.CodeType.PHONE_FORMAT_ERROR.getCode(), CustStaticType.CodeType.PHONE_FORMAT_ERROR.getMessage());
			}
		} catch (NumberFormatException e) {
			return R.fail(CustStaticType.CodeType.PHONE_FORMAT_ERROR.getCode(), CustStaticType.CodeType.PHONE_FORMAT_ERROR.getMessage());
		}
		return R.success();
	}

	@Override
	public boolean checkEmailFormat(String email) {
		Pattern pattern = Pattern.compile(
			"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	@Override
	public R validationPhoneStatus(Integer custType, String areaCode, String phoneNum) {
		CustInfoEntity custInfo = custInfoService.getBaseMapper().selectOne(Wrappers.<CustInfoEntity>lambdaQuery()
			.eq(CustInfoEntity::getCustType, custType)
			.eq(CustInfoEntity::getAreaCode, areaCode)
			.eq(CustInfoEntity::getCellPhone, phoneNum)
			.eq(CustInfoEntity::getIsDeleted, 0)
			.ne(CustInfoEntity::getStatus, 3));

		if (custInfo == null) {
			return R.fail(CustStaticType.CodeType.PHONE_NO_REG_ERROR.getCode(), CustStaticType.CodeType.PHONE_NO_REG_ERROR.getMessage());
		}
		return R.success();
	}

}
