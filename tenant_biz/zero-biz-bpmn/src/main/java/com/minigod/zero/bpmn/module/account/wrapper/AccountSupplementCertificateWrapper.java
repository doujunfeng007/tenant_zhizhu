package com.minigod.zero.bpmn.module.account.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.bpmn.module.account.entity.AccountSupplementCertificateEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountSupplementCertificateVO;

/**
 *  包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class AccountSupplementCertificateWrapper extends BaseEntityWrapper<AccountSupplementCertificateEntity, AccountSupplementCertificateVO>  {

	public static AccountSupplementCertificateWrapper build() {
		return new AccountSupplementCertificateWrapper();
 	}

	@Override
	public AccountSupplementCertificateVO entityVO(AccountSupplementCertificateEntity account_supplement_certificate) {
		AccountSupplementCertificateVO account_supplement_certificateVO = BeanUtil.copy(account_supplement_certificate, AccountSupplementCertificateVO.class);

		//User createUser = UserCache.getUser(account_supplement_certificate.getCreateUser());
		//User updateUser = UserCache.getUser(account_supplement_certificate.getUpdateUser());
		//account_supplement_certificateVO.setCreateUserName(createUser.getName());
		//account_supplement_certificateVO.setUpdateUserName(updateUser.getName());

		return account_supplement_certificateVO;
	}

}
