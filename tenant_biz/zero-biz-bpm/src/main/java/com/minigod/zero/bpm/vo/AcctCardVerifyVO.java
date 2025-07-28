package com.minigod.zero.bpm.vo;

import com.minigod.zero.bpm.entity.AcctCardVerifyEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 银行卡四要素验证信息表 视图实体类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AcctCardVerifyVO extends AcctCardVerifyEntity {
	private static final long serialVersionUID = 1L;

}
