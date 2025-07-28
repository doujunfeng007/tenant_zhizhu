package com.minigod.zero.bpmn.module.account.vo;

import com.minigod.zero.bpmn.module.account.entity.AccountOpenImageModifyEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 修改开户图片信息返回对象
 *
 * @author zxq
 * @date  2024/8/14
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountOpenImageModifyVO extends AccountOpenImageModifyEntity {
	private static final long serialVersionUID = 1L;
}
