package com.minigod.zero.bpm.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpm.entity.AcctRiskQuestionEntity;
import com.minigod.zero.bpm.vo.AcctRiskQuestionVO;
import java.util.Objects;

/**
 * 风险评测题库 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public class AcctRiskQuestionWrapper extends BaseEntityWrapper<AcctRiskQuestionEntity, AcctRiskQuestionVO>  {

	public static AcctRiskQuestionWrapper build() {
		return new AcctRiskQuestionWrapper();
 	}

	@Override
	public AcctRiskQuestionVO entityVO(AcctRiskQuestionEntity acctRiskQuestion) {
	    AcctRiskQuestionVO acctRiskQuestionVO = new AcctRiskQuestionVO();
    	if (acctRiskQuestion != null) {
		    acctRiskQuestionVO = Objects.requireNonNull(BeanUtil.copy(acctRiskQuestion, AcctRiskQuestionVO.class));

		    //User createUser = UserCache.getUser(acctRiskQuestion.getCreateUser());
		    //User updateUser = UserCache.getUser(acctRiskQuestion.getUpdateUser());
		    //acctRiskQuestionVO.setCreateUserName(createUser.getName());
		    //acctRiskQuestionVO.setUpdateUserName(updateUser.getName());
        }
		return acctRiskQuestionVO;
	}


}
