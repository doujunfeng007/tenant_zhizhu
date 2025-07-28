package com.minigod.zero.bpm.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpm.entity.BpmOtherBankInfoEntity;
import com.minigod.zero.bpm.vo.BpmOtherBankInfoVO;
import java.util.Objects;

/**
 * 区域入金银行列表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public class BpmOtherBankInfoWrapper extends BaseEntityWrapper<BpmOtherBankInfoEntity, BpmOtherBankInfoVO>  {

	public static BpmOtherBankInfoWrapper build() {
		return new BpmOtherBankInfoWrapper();
 	}

	@Override
	public BpmOtherBankInfoVO entityVO(BpmOtherBankInfoEntity bpmOtherBankInfo) {
	    BpmOtherBankInfoVO bpmOtherBankInfoVO = new BpmOtherBankInfoVO();
    	if (bpmOtherBankInfo != null) {
		    bpmOtherBankInfoVO = Objects.requireNonNull(BeanUtil.copy(bpmOtherBankInfo, BpmOtherBankInfoVO.class));

		    //User createUser = UserCache.getUser(bpmOtherBankInfo.getCreateUser());
		    //User updateUser = UserCache.getUser(bpmOtherBankInfo.getUpdateUser());
		    //bpmOtherBankInfoVO.setCreateUserName(createUser.getName());
		    //bpmOtherBankInfoVO.setUpdateUserName(updateUser.getName());
        }
		return bpmOtherBankInfoVO;
	}


}
