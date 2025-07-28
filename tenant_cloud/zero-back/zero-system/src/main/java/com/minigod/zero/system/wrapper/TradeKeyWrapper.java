package com.minigod.zero.system.wrapper;


import com.minigod.zero.system.entity.TradeKey;
import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.system.vo.TradeKeyVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class TradeKeyWrapper extends BaseEntityWrapper<TradeKey, TradeKeyVO> {

	public static TradeKeyWrapper build() {
		return new TradeKeyWrapper();
 	}

	@Override
	public TradeKeyVO entityVO(TradeKey trade_key) {
		TradeKeyVO trade_keyVO = BeanUtil.copy(trade_key, TradeKeyVO.class);

		//User createUser = UserCache.getUser(trade_key.getCreateUser());
		//User updateUser = UserCache.getUser(trade_key.getUpdateUser());
		//trade_keyVO.setCreateUserName(createUser.getName());
		//trade_keyVO.setUpdateUserName(updateUser.getName());

		return trade_keyVO;
	}

}
