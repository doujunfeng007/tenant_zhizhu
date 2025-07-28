package com.minigod.zero.bpm.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpm.entity.AcctRiskEvaluationEntity;
import com.minigod.zero.bpm.vo.AcctRiskEvaluationVO;
import java.util.Objects;

/**
 * 风险评测记录表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public class AcctRiskEvaluationWrapper extends BaseEntityWrapper<AcctRiskEvaluationEntity, AcctRiskEvaluationVO>  {

	public static AcctRiskEvaluationWrapper build() {
		return new AcctRiskEvaluationWrapper();
 	}

	@Override
	public AcctRiskEvaluationVO entityVO(AcctRiskEvaluationEntity acctRiskEvaluation) {
	    AcctRiskEvaluationVO acctRiskEvaluationVO = new AcctRiskEvaluationVO();
    	if (acctRiskEvaluation != null) {
		    acctRiskEvaluationVO = Objects.requireNonNull(BeanUtil.copy(acctRiskEvaluation, AcctRiskEvaluationVO.class));

		    //User createUser = UserCache.getUser(acctRiskEvaluation.getCreateUser());
		    //User updateUser = UserCache.getUser(acctRiskEvaluation.getUpdateUser());
		    //acctRiskEvaluationVO.setCreateUserName(createUser.getName());
		    //acctRiskEvaluationVO.setUpdateUserName(updateUser.getName());
        }
		return acctRiskEvaluationVO;
	}


}
