package com.minigod.zero.bpmn.module.edda.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.customer.vo.FundDepositEddaVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/9 18:03
 * @Version: 1.0
 */
@Data
public class FundDepositEddaAccountListVO {


	private Long id;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 状态 0未提交 1授权中 2授权失败 3已授权 4 撤销授权
	 */
	private Integer eddaState;

	/**
	 * 存入账户类型:1 港币账户; 2 综合多币种账户
	 */
	private Integer depositAccountType;

	/**
	 * 存入银行账户
	 */
	private String depositAccount;

	/**
	 * 币种 usd hkd
	 */
	private String amtCcy;

	/**
	 * 流水号
	 */
	private String applicationId;


}
