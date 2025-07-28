package com.minigod.zero.bpm.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpm.entity.AcctRiskQuestionOptionEntity;
import com.minigod.zero.bpm.vo.AcctRiskQuestionOptionVO;
import java.util.Objects;

/**
 * 风险评测题库-选项 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public class AcctRiskQuestionOptionWrapper extends BaseEntityWrapper<AcctRiskQuestionOptionEntity, AcctRiskQuestionOptionVO>  {

	public static AcctRiskQuestionOptionWrapper build() {
		return new AcctRiskQuestionOptionWrapper();
 	}

	@Override
	public AcctRiskQuestionOptionVO entityVO(AcctRiskQuestionOptionEntity acctRiskQuestionOption) {
	    AcctRiskQuestionOptionVO acctRiskQuestionOptionVO = new AcctRiskQuestionOptionVO();
    	if (acctRiskQuestionOption != null) {
		    acctRiskQuestionOptionVO = Objects.requireNonNull(BeanUtil.copy(acctRiskQuestionOption, AcctRiskQuestionOptionVO.class));

		    //User createUser = UserCache.getUser(acctRiskQuestionOption.getCreateUser());
		    //User updateUser = UserCache.getUser(acctRiskQuestionOption.getUpdateUser());
		    //acctRiskQuestionOptionVO.setCreateUserName(createUser.getName());
		    //acctRiskQuestionOptionVO.setUpdateUserName(updateUser.getName());
        }
		return acctRiskQuestionOptionVO;
	}


}
