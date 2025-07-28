package com.minigod.zero.dbs.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 客户出金申请信息表
 *
 * @author sunline
 * @email aljqiang@163.com
 * @date 2019-04-01 16:23:18
 */
@Data
public class  FundTransferEntity extends DbsBaseRequestVO implements Serializable {

    private static final long serialVersionUID = 1L;

    //自增ID
    private Long id;
    //流水号
    private String applicationId;
    //交易帐号
    private String clientId;
    private Integer bankAccountType;//绑卡来源（1 银行卡 2 fps id 3 fps-手机号码 4  fps-邮箱 5 fps-HKID）
    //资金帐号
    private String fundAccount;
    //资金帐号
    private String bankId;
    //收款人名称
    private String clientNameSpell;
    //提取方式[0-香港银行卡 1-大陆银行卡]
    private Integer withdrawType;
    //收款银行名称
    private String bankName;
    //收款银行帐户
    private String bankNo;
    //收款银行地址
    private String bankAddress;
    //SWIFT代码
    private String swiftCode;
    //收款银行代码
    private String bankCode;
    //联系地址
    private String contactAddress;
    //币种代码[0-人民币 1-美元 2-港币]
    private String moneyType;
    //汇款币种代码[0-人民币 1-美元 2-港币]
    private String debitMoneyType;
    //提取金额
    private double occurBalance;
    //提取手续费
    private double chargeMoney;
    //实际提取金额
    private double actualBalance;
    //冻结资金
    private double frozenBalance;
    //现金余额
    private double currentBalance;
    //反向流水号
    private String revertSerialNo;
    //资金冻结日期
    private Date initDate;
    //当前节点名称
    private String currentNode;
    //审核结果[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
    private Integer approveResult;
    //最后审核人
    private String lastApprovalUser;
    //最后审核意见
    private String approvalOpinion;
    //回调APP端结果状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
    private Integer callbackStatus;
    //业务流程状态[1=草稿 2=审批中 3=结束]
    private String status;
    //流程实例ID
    private String instanceId;
    //流程定义ID
    private String defid;
    //业务流程单据编号
    private String code;
    //流程发起人ID
    private String startUserId;
    //流程发起人ID
    private String createUser;
    //流程更新人ID
    private String updateUser;
    //流程发起时间
    private Date startTime;
    //流程审批结果[1-同意 2-不同意 3-审批中]
    private String actResult;
    //预约申请状态[0-未知 1-初审中 2-复审中 3-汇款中 4-终审中 5-出金成功 6-出金失败 7-已完成 8-已终止]
    private Integer applicationStatus;
    //出金状态[0-未知 1-出金成功 2-出金失败]
    private Integer withdrawStatus;
    //导出状态[0-未知 1-已导出 2-未导出]
    private Integer exportStatus;
    //恒生银行编号
    private String hsBankId;
    //恒生银行帐户
    private String hsBankAccount;
    //恒生银行名称
    private String hsBankName;
    //恒生业务处理步骤[10007-资金取出 10009-资金解冻]
    private Integer hsBusinessStep;
    //汇款人银行区域编码
    private String senderBankCtryCode;
    //收款人银行区域编码
    private String receiveBankCtryCode;
    //指定任务处理人
    private String assignDrafter;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //交易对账流水号
    private String cusRef;

    // 银行打款相关
    private Integer bankState; //银行状态 0未提交 1失败 2成功 3 已提交 4 处理中
    private Integer reqCnt; // 请求次数
    private String txnRefId; // 银行事务ID
    private String rejCode; // 拒绝代码
    private String rejDescription; // 拒绝原因
    private String ayersReversalId; // ayers 冲正单号

    //汇款银行
    private String depositBank;
    //汇款账号
    private String depositNo;
    //汇款账户名称
    private String depositAccount;
    //汇款银行代码
    private String depositBankCode;
    //汇款银行Id
    private String depositBankId;

    private Integer userId;
    private String clientName;
    private Integer sex;
    private String phoneNumber;
    private Integer sourceChannelId;
    private Integer idKind;
    private String idNo;
    private String clientNameEn;

    private String beginTime;
    private String endTime;
    private String fundWithdrawBalance;

    private List<String> currentNodes;

    // 列表展示标识
    private String flag;

    // 汇款方式[0-未知 1-FPS 2-ACT 3-CTS 4-其它]
    private Integer remittanceType;

    //恒生处理状态[0-未知 1-处理成功 2-处理失败]
    private Integer hsDealStatus;

    // 支票本[1-汇丰银行 2-中国银行（香港）]
    private Integer chequeType;

    private String day;
    private String month;
    private String year;

    private List<String> applicationIds;
    //加急处理[0-未加急 1-已加急]
    private Integer fireAid;

    private Integer openAccountType;

    private Integer ayersDisposeSign;//Ayers处理标识 1未处理 2成功 3失败

    // 付款操作类型：[1 自动付款 2 手动打款]
    private Integer paymentType;

}
