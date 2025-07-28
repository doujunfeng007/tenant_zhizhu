package com.minigod.zero.manage.service;

import com.minigod.zero.user.entity.User;

import java.util.List;
import java.util.Map;

public interface ICustInfoService {
	Map<Long, User> userInfoByIds(List<Long> updateUserIds);
}
