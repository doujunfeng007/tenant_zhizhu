package com.minigod.zero.bpmn.module.withdraw.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.minigod.zero.bpmn.module.withdraw.enums.BpmCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 取款信息报表数据对象
 *
 * @author chenyu
 * @title WithdrawInfoReportDto
 * @date 2023-04-14 22:17
 * @description
 */
@Data
@ToString
public class WithdrawInfoReportVo implements Serializable {

    /**
     * 流水号
     */
	@ExcelProperty(value = "流水号")
    private String applicationId;

    /**
     * 资金帐号
     */
	@ExcelProperty(value = "账户号码")
    private String fundAccount;

    /**
     * 中文名
     */
		@ExcelProperty(value = "客户姓名")
    private String custName;


    /**
     * 英文名
     */
		@ExcelProperty(value = "英文名")
    private String custEname;


    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
	@ExcelProperty(value = "币种")
    private String ccy;



    /**
     * 提取金额
     */
		@ExcelProperty(value = "提取金额")
    private BigDecimal withdrawAmount;


    /**
     * 实际提取金额
     */
		@ExcelProperty(value = "实际提取金额")
    private BigDecimal actualAmount;


    /**
     * 手续费
     */
		@ExcelProperty(value = "手续费")
    private BigDecimal chargeFee;


    /**
     * 可提余额
     */
		@ExcelProperty(value = "可提资金金额")
    private BigDecimal drawableBalance;


    /**
     * 取款后余额
     */
		@ExcelProperty(value = "取款后余额")
    private BigDecimal remainBalance;


    public BigDecimal getRemainBalance(){
        if(ObjectUtil.isNotEmpty(drawableBalance)){
            return drawableBalance.subtract(actualAmount);
        }
        return null;
    }

    /**
     * 取款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行特快转账 4-支票]
     */
	@ExcelIgnore
    private Integer transferType;
	@ExcelProperty(value = "取款方式")
    private String transferTypeName;


    /**
     * 手续费扣除方式[1-从提取金额中扣除,2-从账户余额中扣除]
     */
	@ExcelProperty
    private Integer deductWay;
	@ExcelProperty(value = "手续费方式")
	private String deductWayName;

	public void setDeductWayName(String deductWayName) {
		this.deductWayName = deductWayName;
	}

	public String getDeductWayName(){
		if(deductWay != null){
			String name = SystemCommonEnum.DeductWay.getName(deductWay);
			deductWayName = name;
			if(name != null){
				return name;
			}
		}
		return null;
	}
    /**
     * 国际[0-中国大陆 2-海外]
     */
	@ExcelIgnore
    private String nationality;

    /**
     * 收款银行省份
     */
	@ExcelIgnore
    private String provinceName;


    /**
     * 收款银行城市名称
     */
	@ExcelIgnore
    private String cityName;


    /**
     * 收款银行名称
     */
	@ExcelIgnore
    private String recvBankName;


    /**
     * 收款银行帐户名称
     */
	@ExcelIgnore
    private String recvBankAcctName;


    /**
     * 收款银行帐户
     */
	@ExcelIgnore
    private String recvBankAcct;


    /**
     * 收款SWIFT代码
     */
	@ExcelIgnore
    private String recvSwiftCode;


    /**
     * 付款银行名称
     */
		@ExcelProperty(value = "付款银行名称")
    private String payBankName;


    /**
     * 付款银行账户号码
     */
		@ExcelProperty(value = "付款银行账户号码")
    private String payBankAcct;

	@ExcelProperty(value = "收款bankId")
	@ApiModelProperty("收款bankId")
	private String recvBankId;


	//@ExcelProperty(value = "业务流程状态[1=草稿 2=审批中 3=结束]")
	@ApiModelProperty("业务流程状态[1=草稿 2=审批中 3=结束]")
	@ExcelIgnore
	private Integer status;
    /**
     * 预约申请状态
     */
	@ExcelIgnore
    private Integer applicationStatus;
	@ExcelProperty(value = "状态")
	private String applicationStatusName;

	public void setApplicationStatusName(String applicationStatusName) {
		this.applicationStatusName = applicationStatusName;
	}

	public String getApplicationStatusName(){
		if(status != null){
			BpmCommonEnum.FundWithdrawStatus fundWithdrawStatus = BpmCommonEnum.FundWithdrawStatus.valueOf(status);
			if(fundWithdrawStatus != null){
				applicationStatusName = fundWithdrawStatus.getDesc();
				return fundWithdrawStatus.getDesc();
			}
		}
		return null;
	}

    /**
     * 性别[0-男 1-女 2-其它]
     */
	@ExcelIgnore
    private String sex;

    /**
     * 手机号码
     */
	@ExcelIgnore
    private String mobile;


    /**
     * 客服备注
     */
	@ExcelIgnore
    private String custRemark;


    /**
     * 已退款金额
     */
	@ExcelIgnore
    private BigDecimal refundedAmount;


    /**
     * 已退款日期
     */
	@ExcelIgnore
    private Date refundedDate;


    /**
     * 汇款编号
     */
	@ExcelIgnore
    private String remittanceId;


    /**
     * 备注
     */
	@ExcelIgnore
    private String remark;


    /**
     * 申请来源[1-网上交易  2-网上营业厅 3-综合后台录入 4-手机证券]
     */
	@ExcelIgnore
    private Integer applySource;

    /**
     * 地址
     */
	@ExcelIgnore
	private String address;


    /**
     * 导出时间
     */
	@ExcelIgnore
    private Date exportDate;
	@ExcelIgnore
    private String exportDateStr;

    // 见证人
	@ExcelIgnore
    private String witnessName;
    // 介绍渠道
	@ExcelIgnore
    private String orgName;
    // 地址
	@ExcelIgnore
    private String familyAddr;
    // 银行状态名称
	@ExcelIgnore
    private String bankStateName;
    // 柜台状态名称
	@ExcelIgnore
    private String gtDealStatusName;

}
