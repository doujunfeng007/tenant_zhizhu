package com.minigod.zero.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.user.mapper.UserOldPasswordMapper;
import com.minigod.zero.user.service.IUserOldPasswordService;
import com.minigod.zero.core.tool.utils.PBKDF2Util;
import com.minigod.zero.user.entity.UserOldPassword;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 存量客户密码表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-20
 */
@Slf4j
@Service
public class UserOldPasswordServiceImpl extends ServiceImpl<UserOldPasswordMapper, UserOldPassword> implements IUserOldPasswordService {


	@Override
	public boolean checkOldLoginPwd(String account, String password) {
		if (!account.matches("\\d+")) {
			return false;
		}
		Long userId = Long.valueOf(account);
		// 根据custId查询存量客户密码表中未重新激活登录用户，存在则需进行PBKDF2校验
		List<UserOldPassword> oldUserList = baseMapper.selectList(Wrappers.<UserOldPassword>lambdaQuery()
			.eq(UserOldPassword::getUserId, userId).eq(UserOldPassword::getLoginActive, 0));
		if (CollectionUtils.isEmpty(oldUserList)) {
			return false;
		}
		for (UserOldPassword oldUser : oldUserList) {
			if (oldUser != null && oldUser.getLoginPwd() != null) {
				log.info("存量客户登录密码校验，userId: {}", userId);
				if (oldUser.getLoginPwd().equals(PBKDF2Util.calcS2(userId.intValue(), password, oldUser.getLoginSalt()))) {
					// 更新存量客户密码表该用户状态为已重新激活登录
					oldUser.setLoginActive(1);
					oldUser.setLoginActiveTime(new Date());
					baseMapper.updateById(oldUser);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void activeLoginPwd(String account) {
		if (account.matches("\\d+")) {
			log.warn("激活存量客户登录密码，userId: {}", account);
			// 更新存量客户密码表该用户状态为已重新激活登录
			UserOldPassword oldCust = new UserOldPassword();
			oldCust.setLoginActive(1);
			oldCust.setLoginActiveTime(new Date());
			baseMapper.update(oldCust, Wrappers.<UserOldPassword>lambdaQuery().eq(UserOldPassword::getUserId, Long.valueOf(account)).eq(UserOldPassword::getLoginActive, 0));
		}
	}


}
