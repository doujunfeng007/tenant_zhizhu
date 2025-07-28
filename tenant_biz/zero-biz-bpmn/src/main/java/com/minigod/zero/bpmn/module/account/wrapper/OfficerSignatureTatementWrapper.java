package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.OfficerSignatureTatementEntity;
import com.minigod.zero.bpmn.module.account.vo.OfficerSignatureTatementVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class OfficerSignatureTatementWrapper extends BaseEntityWrapper<OfficerSignatureTatementEntity, OfficerSignatureTatementVO>  {

	public static OfficerSignatureTatementWrapper build() {
		return new OfficerSignatureTatementWrapper();
 	}

	@Override
	public OfficerSignatureTatementVO entityVO(OfficerSignatureTatementEntity officer_signature_tatement) {
		OfficerSignatureTatementVO officer_signature_tatementVO = BeanUtil.copy(officer_signature_tatement, OfficerSignatureTatementVO.class);

		//User createUser = UserCache.getUser(officer_signature_tatement.getCreateUser());
		//User updateUser = UserCache.getUser(officer_signature_tatement.getUpdateUser());
		//officer_signature_tatementVO.setCreateUserName(createUser.getName());
		//officer_signature_tatementVO.setUpdateUserName(updateUser.getName());

		return officer_signature_tatementVO;
	}

}
