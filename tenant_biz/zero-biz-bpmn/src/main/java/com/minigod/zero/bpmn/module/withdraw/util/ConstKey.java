package com.minigod.zero.bpmn.module.withdraw.util;

import java.util.Date;


public interface ConstKey {
	String YES = "1";
	String NO = "0";

	String OK = "OK";

	String QUOT_DICT_TYPE_AREA = "1";
	String QUOT_DICT_TYPE_PROD = "2";
	String QUOT_DICT_TYPE_PERIOD = "3";
	String QUOT_DICT_TYPE_STATUS = "4";

    String BANK_CODE_ICBC = "ICBC";//工行代码
    String BANK_CODE_CMB = "CMB";//招行代码
    String BANK_CODE_CCBA = "CCBA";//建银代码
    String BANK_CODE_CMBC = "CMBC";//民生代码
    String BANK_CODE_DBS = "DBS";//星展代码

	String QUOT_PURCHASED_STATUS_1 = "1";
	String QUOT_PURCHASED_STATUS_1_DISPLAY = "开通成功";
	//String QUOT_PURCHASED_STATUS_2 = "2";
	//String QUOT_PURCHASED_STATUS_2_DISPLAY = "开通成功、未扣费";
	String QUOT_PURCHASED_STATUS_3 = "3";
	String QUOT_PURCHASED_STATUS_3_DISPLAY = "扣费成功、未开通";
	String QUOT_PURCHASED_STATUS_4 = "4";
	String QUOT_PURCHASED_STATUS_4_DISPLAY = "取消续期";
	String QUOT_PURCHASED_STATUS_5 = "5";
	String QUOT_PURCHASED_STATUS_5_DISPLAY = "已续期";
	String QUOT_PURCHASED_STATUS_6 = "6";
	String QUOT_PURCHASED_STATUS_6_DISPLAY = "已受理";
	String QUOT_PURCHASED_STATUS_7 = "7";
	String QUOT_PURCHASED_STATUS_7_DISPLAY = "已取消";

	String ACCT_TYPE = "CUST";
	String MARKET = "HKG";
	String CCY = "HKD";
	String BANK = "";
	String MARKET_ALL = "All";

	Integer QUOT_DICT_AREA_CN = 1;//中国内地
	String AASTOCKS_AC_OPEN_SUCCESS = "001";
	String QUOT_ACCOUNT_PREFIX = "gxhk";

	String STATUS = "APP";
	String REFERENCE_NO = "";
	String BANK_NAME = "Bank HKD";
	String VOUCHER_TYPE = "";
	String COMMENT = "";
	String USERID = "";
	String PRINT_CHQ = "No";
	Integer CHEQUE_NO = 0;
	Date CHEQUE_DATE = null;
	String REMARK = "";
	Integer NEW_SEQNO = 0;
	String MANUAL_INPUT = "No";
	String GLCODE = "220202";
	String BANK_GLCODE = "10020201";
	String SOURCE = "";
	String ACCOUNT_NAME = "";
	String ACCOUNT_NUMBER = "";
	String BANK_ACCOUNT = "";
	String VOUCHER_TRANSFER_TYPE = "CAD";
	String TXN_TYPE_CRMONEY = "CRMONEY";
	String TXN_TYPE_DRMONEY = "DRMONEY";

	String TXN_TYPE_APIDC = "APIDC";//入资
	String TXN_TYPE_APIDD = "APIDD";//扣费

	//SMS
	String CREATOR_ID = "5";
	Integer SM_SENDED_NUM = 0;
	String OPERATION_TYPE = "WAS";
	Short SEND_TYPE = 1;
	String ORGADDR = "106575555232";
	Short NEED_STATE_REPORT = 1;
	String SERVICEID = "EIE";
	String FEE_TYPE = "01";
	String FEE_CODE = "0";
	Short SM_TYPE = 0;
	String MESSAGEID = "";
	Short DEST_ADDR_TYPE = 0;
	Short TASK_STATUS = 0;
	Short SEND_LEVEL = 0;
	Short SENDSTATE = 0;
	Short TRYTIMES = 3;

	String ERR_SVMG_PARAM_INVALID = "ERR_PARAM_INVALID";
	String ERR_SVMG_ACCT_INVALID = "ERR_ACCT_INVALID";
    String WARN_BLACKLIST_ACCT = "WARN_BLACKLIST_ACCT";
	String ERR_SVMG_OPEN_ACCOUNT_FAIL = "ERR_SVMG_OPEN_ACCOUNT_FAIL";
	String ERR_SVMG_AUTO_RENEWAL_FAIL = "ERR_SVMG_AUTO_RENEWAL_FAIL";
	String ERR_SVMG_PURCHASEDQUOT_STATUS_INVALID = "ERR_SVMG_PURCHASEDQUOT_STATUS_INVALID";
	String ERR_SVMG_PURCHASEDID_NOT_EXIST = "ERR_SVMG_PURCHASEDID_NOT_EXIST";
	String ERR_ASMG_CANCEL_FAIL = "ERR_ASMG_CANCEL_FAIL";
	String ERR_ASMG_AUTO_RENEWAL_TIME = "ERR_ASMG_AUTO_RENEWAL_TIME";//该时段不允许自动续期

	Integer AUTO_RENEWAL_DAYS = 5;

	String ERR_ASMG_FUND_INSUFFICIENT = "ERR_ASMG_FUND_INSUFFICIENT";//可取资金不足
	String ERR_ASMG_TRANS_FAIL = "ERR_ASMG_TRANS_FAIL";//转账失败
	String ERR_ASMG_AMOUNT_INVALID = "ERR_ASMG_AMOUNT_INVALID";//金额不正确
	String ERR_ASMG_CLEARING_TIME = "ERR_ASMG_CLEARING_TIME";//清算时段，不能转账
	String ERR_ASMG_NOT_SAME_CUST = "ERR_ASMG_NOT_SAME_CUST";//转入账户和转出账户非同一客户账户
	String ERR_ASMG_SAME_ACCT = "ERR_ASMG_SAME_ACCT";//转入账户和转出账户相同
	String ERR_ASMG_NOT_CURRENT_ACCT = "ERR_ASMG_NOT_CURRENT_ACCT";//转入转出账户必须有一个为当前账户
	String ERR_ASMG_SAME_CCY = "ERR_ASMG_SAME_CCY"; //买卖币种相同
	String ERR_ASMG_QTY_INVALID = "ERR_ASMG_QTY_INVALID";//数量不正确
	String ERR_ASMG_QTY_LACK = "ERR_ASMG_QTY_LACK";//数量不足
	String ERR_ASMG_SUBAC_NOT_WITHDRAWL = "ERR_ASMG_SUBAC_NOT_WITHDRAWL";//取款账户不能为子账户
	String ERR_ASMG_MARGIN_INVALID = "ERR_ASMG_MARGIN_INVALID";//负债
	String ERR_ASMG_SHARE_INVALID = "ERR_ASMG_SHARE_INVALID";//负债

	String ERR_ASMG_ID_NOT_EXISTS = "ERR_ASMG_ID_NOT_EXISTS";//流水不存在
	String ERR_ASMG_STATUS_INVALID = "ERR_ASMG_STATUS_INVALID";//流水的状态不能取消
	String ERR_ASMG_ACCT_NOT_ALLOW = "ERR_ASMG_ACCT_NOT_ALLOW";//转入或转出账户不允许
	String ERR_ASMG_CCY_NOT_ALLOW = "ERR_ASMG_CCY_NOT_ALLOW";//转入币种不允许
	String ERR_ASMG_OVER_BUY_CCY_LIMIT = "ERR_ASMG_OVER_BUY_CCY_LIMIT"; //超出购买限额
	String ERR_ASMG_LESS_AMOUNT = "ERR_ASMG_LESS_AMOUNT";//卖出金额应超过港币10,000元或等值货币
	String ERR_ASMG_FUT_SERVER_TIME_PASSED = "ERR_ASMG_FUT_SERVER_TIME_PASSED";//期货转账的时间已过
	String ERR_ASMG_ACCT_NOT_SUPPORT = "ERR_ASMG_ACCT_NOT_SUPPORT";//投资移民账户不支持网上取款申请

	String ERR_ASMG_DEDUCT_FAIL = "ERR_ASMG_DEDUCT_FAIL";//转出扣款失败
	String ERR_ASMG_ADD_FAIL = "";//转入入资失败

	String SUCCESS = "SUCCESS";
	String FAIL = "FAIL";
	String INIT = "INIT";
	String CANCEL = "CANCEL";
	String REJ = "REJ";
	String ACCEPT = "ACCEPT";

	String PENDING = "PENDING";//待处理
	String DEDUCT = "DEDUCT";//已扣款

	String SHARES_TRANS_STKDEP = "STKDEP";			//股份存入
	String SHARES_TRANS_STKWITH = "STKWITH";		//股份取出

	String ACCT_TYPE_MARGIN = "66";					//保证金账户
	String ACCT_TYPE_CASH = "88";					//现金账户
	String ACCT_TYPE_FUT = "99";					//期货
	String ACCT_TYPE_INVEST = "89";					//投资移民

	String DEPOT_CODE_CASH = "03";					//03现金客户股份仓
	String DEPOT_CODE_MARGIN = "04";				//04保证金客户股份仓

	String ACCT_PREFIX = "8800";

	String IFT_BANK_GLCODE_SEC = "10020201";
	String IFT_BANK_GLCODE_FUT = "10020211";

	String STAFF_TRADE = "staffTrade";//员工交易报表类型，记录日志时使用
	String SPECIAL_TRADE = "specTrade";//特殊员工交易报表类型
	String REPORT_ACTION_AUTO="0";//报表动作，0表示自动生成
	String REPORT_ACTION_HAND="1";//报表动作，1表示手工
	String REPORT_OPT_TYPE_GEN="GENERATE";//报表操作类型，生成报表
	String REPORT_OPT_TYPE_SEND="SEND";//报表操作类型，发送报表

	String RET_RECEIVE_TIME_OUT = "ERR_GATE_RECEIVE_TIME_OUT";

	String SYNC_REF_TARGET = "Target";

	String TXN_TYPE_IFTDC = "IFTDC";//资金内转存入
	String TXN_TYPE_IFTDD = "IFTDD";//资金内转取出

	//取款优化需求
	String WITHDRAWL_REQUEST_TYPE_INTERNET = "1";//网上提交
	String WITHDRAWL_REQUEST_TYPE_FORM = "2";//表格提交
	String OCTO_ACCT_TYPE = "CUST";
	String OCTO_ACCT_TYPE_MRGN = "MRGN";
	String WITHDRAWL_YES = "1";
	String WITHDRAWL_NO = "0";
	String BANK_ACCT_STATUS_APPLY = "APPLY";//客户已申请
	String WITHDRAWL_REFERENCE_NO = "";
	String WITHDRAWL_STATUS_APP = "APP";
	String WITHDRAWL_COMMENT = "";
	String WITHDRAWL_TXN_TYPE_APIDD = "APIDD";//扣费
	String WITHDRAWL_BANK = "";
	String WITHDRAWL_ACCOUNT_NAME = "";
	String WITHDRAWL_ACCOUNT_NUMBER = "";
	String WITHDRAWL_VOUCHER_TRANSFER_TYPE = "CAD";
	String BANK_ACCT_TYPE_RESERVED = "RESERVED";//预留银行账户
	String BANK_ACCT_TYPE_TRANSAC = "TRANSAC";//银证转账账户
	String BANK_ACCT_TYPE_SUBAC = "SUBAC";//子账户

	String TRANSFER_TYPE_TT = "1";
	String TRANSFER_TYPE_COMMON_TRD = "2";
	String TRANSFER_TYPE_QUICK_TRD = "3";
	String TRANSFER_TYPE_CQD = "4";

	String DEDUCT_WAY_INCLUDE = "1";
	String DEDUCT_WAY_BALANCE = "2";

	String WITHDRAWL_STATUS_IREJ = "0";//内部拒绝
	String WITHDRAWL_STATUS_REQ = "1";//已提交
	String WITHDRAWL_STATUS_ACC = "2";//已受理
	String WITHDRAWL_STATUS_RET = "3";//已退回
	String WITHDRAWL_STATUS_SUC = "4";//成功
	String WITHDRAWL_STATUS_CAN = "5";//已取消
	String WITHDRAWL_STATUS_REJ = "6";//已拒绝
	String WITHDRAWL_STATUS_DH_CHECK = "7";//电汇已复核
	String WITHDRAWL_STATUS_DISHONOR = "8";//退款申请提交
	String WITHDRAWL_STATUS_DISHONOR_SUC = "9";//退款成功
	String WITHDRAWL_STATUS_GSP_CHECK = "A";//后台录入已复核

	String VOUCHER_TYPE_WR = "WITHD";
	String VOUCHER_TYPE_FX = "FXCHD";
	//业务类型
	String TXN_TYPE_WRCHDD = "WRCHDD";//取款资金扣除
	String TXN_TYPE_WRCMDD = "WRCMDD";//取款手续费扣除

	String TXN_TYPE_WRCHDC = "WRCHDC";//取款资金还款

	String CCY_EXCHANGE_SOURCE_INTERNET = "1";//网上营业厅申请
	String CCY_EXCHANGE_SOURCE_BACK = "2";//后台录入申请

	//重置期货账户密码
	String ERR_USER_ID_EMP = "ERR_USER_ID_EMP";//用户ID不能为空
	String ERR_LOGIN_PSW_EMP = "ERR_LOGIN_PSW_EMP";//初始密码不能为空
	String ERR_USER_NOT_EXIST = "ERR_USER_NOT_EXIST";//期货SP系统没有此用户
	String ERR_DATA = "ERR_DATA";//数据处理异常

	//客户资料年审
	String SEND_STATUS_0 = "0";
	String SEND_STATUS_1 = "1";
	String SEND_STATUS_9 = "9";

	//受限账户类型
	String ACCOUNT_LIMIT_FUND = "FUND";
	String ACCOUNT_LIMIT_TRADE = "TRADE";

	String MSG_SEND_TYPE_1="1";//即时
	String MSG_SEND_TYPE_2="2";//定时

	String MSG_SEND_STATUS_1="1";//待发送
	String MSG_SEND_STATUS_2="2";//已发送
	String MSG_SEND_STATUS_3="3";//发送成功
	String MSG_SEND_STATUS_4="4";//发送失败

	String MSG_TYPE_1 = "1";//短信

	String CAM_SVC = "CAM";//账户系统
	String GETCUSTCUACCTINFO = "GetCustCuacctInfo";//账户系统获取客户列表
	String ERR_ASMG_ACCT_PLATFORM = "ERR_ASMG_ACCT_PLATFORM";//获取期货账号所属平台失败

	//level2行情权限状态
	String LV2_AUTH_STATUS_Y = "0";//有权限
	String LV2_AUTH_STATUS_N = "1";//无权限

	//level2行情领用状态
	String USE_STATUS_0 = "0";//已领取
	String USE_STATUS_1 = "1";//未领取
	String USE_STATUS_2 = "2";//未开户
	String USE_STATUS_3 = "3";//资产未达标

	//level2行情领取类型
	String USETYPE_0 = "0";//注册礼
	String USETYPE_1 = "1";//开户礼
	String USETYPE_2 = "2";//有效户礼

	//收款银行登记状态
	String WITHDRAWL_BANK_INFO_STATUS_0 = "0";//待上传银行凭证
	String WITHDRAWL_BANK_INFO_STATUS_1 = "1";//待系统核实
	String WITHDRAWL_BANK_INFO_STATUS_2 = "2";//已核实通过
	String WITHDRAWL_BANK_INFO_STATUS_3 = "3";//无需核实
	String WITHDRAWL_BANK_INFO_STATUS_4 = "4";//核实未通过
	String WITHDRAWL_BANK_INFO_STATUS_5 = "5";//已取消

	//收款银行登记操作类型
	String WITHDRAWL_BANK_OPR_TYPE_00 = "00";//客户上传文件
	String WITHDRAWL_BANK_OPR_TYPE_01 = "01";//客户取消
	String WITHDRAWL_BANK_OPR_TYPE_03 = "03";//后台上传
	String WITHDRAWL_BANK_OPR_TYPE_04 = "04";//审核通过
	String WITHDRAWL_BANK_OPR_TYPE_05 = "05";//审核退回
	String WITHDRAWL_BANK_OPR_TYPE_06 = "06";//修改电汇代码
	String WITHDRAWL_BANK_OPR_TYPE_07 = "07";//登记收款银行


	String TRANSFERSHARES_STATUS_REQ = "1";//提交申请
	String TRANSFERSHARES_STATUS_CHECK= "2";//已复核
	String TRANSFERSHARES_STATUS_REJ= "3";//退回
	String TRANSFERSHARES_STATUS_RETAIN = "4";//已受理
	String TRANSFERSHARES_STATUS_CAN = "5";//已取消
	String TRANSFERSHARES_STATUS_APP = "6";//已完成
}
