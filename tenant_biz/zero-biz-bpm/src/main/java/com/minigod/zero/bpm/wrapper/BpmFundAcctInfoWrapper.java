package com.minigod.zero.bpm.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpm.entity.BpmFundAcctInfoEntity;
import com.minigod.zero.bpm.vo.BpmFundAcctInfoVO;
import java.util.Objects;

/**
 * 基金账户信息表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public class BpmFundAcctInfoWrapper extends BaseEntityWrapper<BpmFundAcctInfoEntity, BpmFundAcctInfoVO>  {

	public static BpmFundAcctInfoWrapper build() {
		return new BpmFundAcctInfoWrapper();
 	}

	@Override
	public BpmFundAcctInfoVO entityVO(BpmFundAcctInfoEntity bpmFundAcctInfo) {
	    BpmFundAcctInfoVO bpmFundAcctInfoVO = new BpmFundAcctInfoVO();
    	if (bpmFundAcctInfo != null) {
		    bpmFundAcctInfoVO = Objects.requireNonNull(BeanUtil.copy(bpmFundAcctInfo, BpmFundAcctInfoVO.class));

		    //User createUser = UserCache.getUser(bpmFundAcctInfo.getCreateUser());
		    //User updateUser = UserCache.getUser(bpmFundAcctInfo.getUpdateUser());
		    //bpmFundAcctInfoVO.setCreateUserName(createUser.getName());
		    //bpmFundAcctInfoVO.setUpdateUserName(updateUser.getName());
        }
		return bpmFundAcctInfoVO;
	}


}
