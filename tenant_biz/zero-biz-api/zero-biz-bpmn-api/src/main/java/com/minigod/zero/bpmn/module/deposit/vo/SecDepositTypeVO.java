package com.minigod.zero.bpmn.module.deposit.vo;

import lombok.Data;

/**
 * 入金方式管理配置表 视图实体类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
@Data

public class SecDepositTypeVO  {
	private static final long serialVersionUID = 1L;
	private Integer depositType;//入金方式[1-fps 2-网银 3-支票]
	private String descFee;//入金手续费
	private String descFeeRemark;//手续费备注
	private String descTime;//入金到账时间
	private String descTimeRemark;//到账时间备注
	private String bankName; // 银行名称
	private String bankCode; // 银行代码
	private String accountName;//收款户名
	private String accountNameRemark;//收款户名
	private String accountFPS;//FPS识别码
	private DepositBankTipsLinkdsVO tipsLinks;//入金指南链接
	private String depositCertImg;//入金凭证
	private String receiptBankNoFps;//FPS收款银行编码
	private String swiftCode;
	private String receiptBankAddress;
}
