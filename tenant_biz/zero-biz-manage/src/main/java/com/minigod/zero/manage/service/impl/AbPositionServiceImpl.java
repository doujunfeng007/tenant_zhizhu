package com.minigod.zero.manage.service.impl;

import com.minigod.zero.manage.service.IAbPositionService;
import com.minigod.zero.core.secure.ZeroUser;
import com.minigod.zero.core.secure.utils.AuthUtil;
import org.springframework.stereotype.Service;

/**
 * @author 掌上智珠
 * @since 2023/9/8 13:45
 */
@Service
public class AbPositionServiceImpl implements IAbPositionService {
	/**
	 * true是触发了ab机制,false是没问题
	 * 代码都是一致的,防止后面该需求,可单独修改
	 * @param type
	 * @param lastUpdateUserId
	 * @return
	 */
	@Override
	public boolean newsAbPosition(Integer type, Long lastUpdateUserId) {
		return baseAbPosition(type,lastUpdateUserId);
	}

	@Override
	public boolean topicAbPosition(Integer type, Long lastUpdateUserId) {
		return baseAbPosition(type,lastUpdateUserId);
	}

	@Override
	public boolean sensitiveWordAbPosition(Integer type, Long lastUpdateUserId) {
		return baseAbPosition(type,lastUpdateUserId);
	}

	@Override
	public boolean simpleAndComplicatedAbPosition(Integer type, Long lastUpdateUserId) {
		return baseAbPosition(type,lastUpdateUserId);
	}

	@Override
	public boolean baseAbPosition(Integer type, Long lastUpdateUserId) {
		Long userId = getUserId();
		//下架状态,false
		if (type==0){
			return false;
		}

		//最后更新人未知,false
		if (null==lastUpdateUserId){
			return false;
		}

		//拿不到当前用户,无法判定,返回错误
		if (null==userId){
			return true;
		}
		return userId.equals(lastUpdateUserId);
	}


	private Long getUserId(){
		ZeroUser user = AuthUtil.getUser();
		if (user == null || user.getUserId() == null) {
			return null;
		}
		return user.getUserId();
	}
}
