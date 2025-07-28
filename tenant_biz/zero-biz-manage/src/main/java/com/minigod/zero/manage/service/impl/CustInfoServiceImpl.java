package com.minigod.zero.manage.service.impl;

import com.minigod.zero.user.entity.User;
import com.minigod.zero.manage.service.ICustInfoService;
import com.minigod.zero.user.feign.IUserClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustInfoServiceImpl implements ICustInfoService {
	@Resource
	private IUserClient userClient;

	@Override
	public Map<Long, User> userInfoByIds(List<Long> updateUserIds) {
		Map<Long, User> updateUserMap = null;
		try {
			List<User> list = userClient.userInfoByIds(updateUserIds);
			if (null != list && list.size()>0) {
				updateUserMap = list.stream().collect(Collectors.toMap(User::getId, Function.identity()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return updateUserMap;
	}
}
