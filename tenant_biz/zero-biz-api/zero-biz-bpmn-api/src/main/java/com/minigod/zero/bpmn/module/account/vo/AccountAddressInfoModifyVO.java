package com.minigod.zero.bpmn.module.account.vo;

import com.minigod.zero.bpmn.module.account.dto.AccountAddressInfoModifyDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
  * 开户资料地址修改返回对象
  *
  * @param
  * @return
  */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountAddressInfoModifyVO extends AccountAddressInfoModifyDTO {
	private static final long serialVersionUID = 1L;
}
