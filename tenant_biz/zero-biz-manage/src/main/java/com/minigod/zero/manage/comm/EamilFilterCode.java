package com.minigod.zero.manage.comm;

import com.minigod.zero.platform.enums.EmailTemplate;

/**
 * @ClassName: com.minigod.zero.oms.comm.EamilFilterCode
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/12/10 14:06
 * @Version: 1.0
 */
public class EamilFilterCode {
	public static final String[] EAMIL_FILTER_CODE = {
		String.valueOf(EmailTemplate.OPEN_WEALTH_MANAGEMENT_ACCOUNT.getCode()),
		String.valueOf(EmailTemplate.RESET_TRANSACTION_PASSWORD.getCode()),
		String.valueOf(EmailTemplate.OPEN_SUB_ACCOUNT.getCode())
	};
}
