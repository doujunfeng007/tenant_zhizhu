//package com.minigod.zero.bpmn.module.withdraw.service.impl;
//
//import com.alibaba.nacos.api.config.ConfigService;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.minigod.zero.biz.common.enums.WithdrawKeyConstants;
//import com.minigod.zero.bpmn.module.withdraw.bo.ClientFundWithdrawApplicationBo;
//import com.minigod.zero.bpmn.module.withdraw.bo.ClientFundWithdrawInfoBo;
//import com.minigod.zero.bpmn.module.withdraw.constant.RedisKeyConstants;
//import com.minigod.zero.bpmn.module.withdraw.entity.ClientFundWithdrawInfo;
//import com.minigod.zero.bpmn.module.withdraw.enums.BpmCommonEnum;
//import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
//import com.minigod.zero.bpmn.module.withdraw.service.*;
//import com.minigod.zero.bpmn.module.withdraw.vo.ClientFundWithdrawApplicationVo;
//import com.minigod.zero.bpmn.module.withdraw.vo.ClientFundWithdrawInfoVo;
//import com.minigod.zero.bpmn.module.withdraw.vo.ClientTeltransferInfoVo;
//import com.minigod.zero.bpmn.module.withdraw.vo.DbsRemitReqLogVo;
//import com.minigod.zero.core.mp.support.Condition;
//import com.minigod.zero.core.mp.support.Query;
//import com.minigod.zero.core.redis.lock.LockType;
//import com.minigod.zero.core.redis.lock.RedisLockClient;
//import com.minigod.zero.core.tool.api.R;
//import com.minigod.zero.dbs.bo.FundTransferEntity;
//import com.minigod.zero.dbs.enums.BankApiFuncTypeEnum;
//import com.minigod.zero.dbs.protocol.AccountBalResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.StringUtils;
//import org.redisson.api.RLock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
///**
// * @ClassName: com.minigod.zero.bpmn.module.withdraw.service.impl.IClientFundWithdrawDbsServiceImpl
// * @Description:
// * @Author: linggr
// * @CreateDate: 2024/6/12 16:23
// * @Version: 1.0
// */
//@RequiredArgsConstructor
//@Service
//@Slf4j
//class ClientFundWithdrawDbsServiceImpl implements IClientFundWithdrawDbsService {
//
//
//	@Autowired
//	private DbsFundBusinessService dbsFundBusinessService;
//	@Autowired
//	private IClientFundWithdrawApplicationService withdrawApplicationService;
//	@Autowired
//	private IClientFundWithdrawInfoService withdrawInfoService;
//	@Autowired
//	private IClientTeltransferInfoService teltransferInfoService;
//	@Autowired
//	private ConfigService configService;
//	private final RedisLockClient redisLockClient;
//
//
//	/**
//	 * DBS银行自动转账调度任务
//	 */
//	@Override
//	public void doDbsAutoTransfer(Map<String, Object> map) {
//		long startTime = System.currentTimeMillis();
//		log.info("DBS自动转账任务-开始");
//
//		// 变量定义
//		RLock rLock = null;
//		String redisLockKey = null;
//		boolean isLock = false;
//
//		ClientFundWithdrawApplicationBo queryWithdrawApplicationBo = new ClientFundWithdrawApplicationBo();
//		// 柜台已执行且业务转台为已受理
//		queryWithdrawApplicationBo.getInfo().setCallbackStatus(SystemCommonEnum.CommonProcessStatus.COMMON_PROCESS_STATUS_SUCCEED_VALUE);
//		queryWithdrawApplicationBo.getInfo().setGtBusinessStep(SystemCommonEnum.FundWithDrawStep.FUND_WITHDRAW_SFUND_CASH_GD_FETCH_VALUE);
//		queryWithdrawApplicationBo.getInfo().setGtDealStatus(SystemCommonEnum.CommonProcessResultStatus.COMMON_PROCESS_STATUS_SUCCEED_VALUE);
//		queryWithdrawApplicationBo.setApplicationStatus(BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_ACCEPT_AUDIT_VALUE);
//		IPage<ClientFundWithdrawApplicationVo> tableDataInfo = withdrawApplicationService.queryPageList(queryWithdrawApplicationBo, Condition.getPage(new Query()));
//		List<ClientFundWithdrawApplicationVo> applicationVoList = null;
//		log.info("DBS自动转账，待处理数据条数：" + tableDataInfo.getTotal());
//		if (null != tableDataInfo && tableDataInfo.getTotal() > 0) {
//			applicationVoList = tableDataInfo.getRecords();
//
//			R result = null;
//			boolean isSuccess = false;
//			ClientFundWithdrawInfoBo updateWithdrawInfoBo = null;
//			ClientFundWithdrawInfoVo fundWithdrawInfoVo = null;
//			DbsRemitReqLogVo dbsRemitReqLogVo = null;
//			for (ClientFundWithdrawApplicationVo withdrawApplicationVo : applicationVoList) {
//				try {
//					redisLockKey = RedisKeyConstants.LOCK_WITHDRAW_DBS_KEY_PREFIX + withdrawApplicationVo.getApplicationId();
//					isLock = redisLockClient.tryLock(redisLockKey, LockType.REENTRANT, 1, 1, TimeUnit.MILLISECONDS);
//
//					log.info("DBS自动转账获取交易锁: {} ,预约号: {}, Result: {}", redisLockKey, withdrawApplicationVo.getApplicationId(), isLock);
//					if (isLock) {
//						// 校验 二次检查
//						result = validateCondition(withdrawApplicationVo);
//						if (result.isSuccess()) {
//							log.info("DBS自动转账开始 预约号: {}", withdrawApplicationVo.getApplicationId());
//							// 设置银行执行状态
//							updateWithdrawInfoBo = new ClientFundWithdrawInfoBo();
//							updateWithdrawInfoBo.setApplicationId(withdrawApplicationVo.getApplicationId());
//							//updateWithdrawInfoBo.setUpdateTime(new Date());
//							updateWithdrawInfoBo.setBankState(SystemCommonEnum.BankStateTypeEnum.COMMITTING.getValue());
//							isSuccess = withdrawInfoService.updateClientFundWithdrawInfo(updateWithdrawInfoBo);
//							if (isSuccess) {
//								// 处理业务
//								doDbsTransfer(withdrawApplicationVo);
//							}
//						} else {
//							// 非DBS银行付款 付款失败
//							if (SystemCommonEnum.CodeType.NONE_DBS_TRANSFER.getCode() == result.getCode()) {
//								ClientFundWithdrawInfo clientFundWithdrawInfo = withdrawInfoService.queryByApplicationId(withdrawApplicationVo.getApplicationId());
//								// 校验失败  DBS打款失败
//								dbsRemitReqLogVo = new DbsRemitReqLogVo();
//								dbsRemitReqLogVo.setRejCode(result.getMsg());
//								dbsRemitReqLogVo.setRejDescription(result.getMsg());
//								updatefundWithdraw(fundWithdrawInfoVo.getApplicationId(), fundWithdrawInfoVo.getPayType(), SystemCommonEnum.BankStateTypeEnum.FAIL.getValue(),
//									dbsRemitReqLogVo);
//								// 业务处理  暂时停止流程  人工打印或退款申请处理
//								withdrawApplicationService.doBusinessAfterPayFailed(clientFundWithdrawInfo);
//							}
//							log.info("DBS自动转账开始，校验条件未通过 预约号: {} result:{}", withdrawApplicationVo.getApplicationId(), result.getMsg());
//						}
//					}
//				} catch (Exception ex) {
//					log.error("DBS自动转账操作异常", ex);
//				} finally {
//					if (null != rLock && isLock) {
//						if (rLock.isHeldByCurrentThread()) {
//							log.info("DBS自动转账获取交易解锁: {} ,预约号: {}, Result: {}", redisLockKey, withdrawApplicationVo.getApplicationId(), isLock);
//							rLock.unlock();
//						}
//					}
//				}
//			}
//		}
//
//		log.info("DBS自动转账任务-结束, 执行：{} ms", System.currentTimeMillis() - startTime);
//	}
//
//	private R validateCondition(ClientFundWithdrawApplicationVo withdrawApplicationVo) {
//		ClientFundWithdrawApplicationVo fundWithdrawApplicationVo = withdrawApplicationService.queryByApplicationId(withdrawApplicationVo.getApplicationId());
//		// 判断状态是否为
//		if (null == fundWithdrawApplicationVo) {
//			log.info("DBS自动转账记录不存在, data:{}", withdrawApplicationVo.getApplicationId());
//			return R.fail(SystemCommonEnum.CodeType.ERROR_UNKNOWN.getCode(), "记录不存在");
//		}
//
//		// 自动转账的流程状态为 已受理
//		if (fundWithdrawApplicationVo.getApplicationStatus() != BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_ACCEPT_AUDIT_VALUE) {
//			log.info("DBS自动转账流程状态异常,非已受理状态, status:{} valid:{}", fundWithdrawApplicationVo.getApplicationStatus(), BpmCommonEnum.FundWithdrawApplicationStatus
//				.FUND_WITHDRAW_APPLY_ACCEPT_AUDIT_VALUE);
//			return R.fail(SystemCommonEnum.CodeType.ERROR_UNKNOWN.getCode(), "非已受理状态");
//		}
//
//		// 查询取款信息
//		ClientFundWithdrawInfo clientFundWithdrawInfo = withdrawInfoService.queryByApplicationId(withdrawApplicationVo.getApplicationId());
//
//		// 柜台解冻扣款成功
//		//if(fundWithdrawInfoVo.getCallbackStatus() != SystemCommonEnum.CommonProcessStatus.COMMON_PROCESS_STATUS_SUCCEED_VALUE
//		//    || fundWithdrawInfoVo.getGtBusinessStep() != SystemCommonEnum.FundWithDrawStep.FUND_WITHDRAW_SFUND_CASH_GD_FETCH_VALUE
//		//    || fundWithdrawInfoVo.getGtDealStatus() !=  SystemCommonEnum.CommonProcessResultStatus.COMMON_PROCESS_STATUS_SUCCEED_VALUE){
//		//    log.info("柜台解冻出账还未成功, applicationId:{} GtBusinessStep:{} getGtDealStatus:{}", fundWithdrawInfoVo.getApplicationId(), fundWithdrawInfoVo.getGtBusinessStep(), fundWithdrawInfoVo.getGtDealStatus());
//		//    return R.fail(SystemCommonEnum.CodeType.GT_WITHDRAW_NONE_SUCCESS.getCode(), SystemCommonEnum.CodeType.GT_WITHDRAW_NONE_SUCCESS.getMessage());
//		//}
//
//		// 非DBS银行付款 直接Dbs付款失败
//		/*if(!BpmConstants.DEFAULT_PAY_BANK.equals(clientFundWithdrawInfo.getPayBankCode())){
//			log.info("非DBS银行付款, applicationId:{} payBankCode:{}", clientFundWithdrawInfo.getApplicationId(), clientFundWithdrawInfo.getPayBankCode());
//			return R.fail(SystemCommonEnum.CodeType.NONE_DBS_TRANSFER.getCode(), SystemCommonEnum.CodeType.NONE_DBS_TRANSFER.getMessage());
//		}
//
//		// 检查银行状态是否已执行
//		if(clientFundWithdrawInfo.getBankState() != SystemCommonEnum.BankStateTypeEnum.UN_COMMNIT.getValue()){
//			log.info("DBS自动转账已执行或正在执行, status:{} valid:{}", clientFundWithdrawInfo.getBankState(), SystemCommonEnum.BankStateTypeEnum.UN_COMMNIT.getValue());
//			return R.fail(SystemCommonEnum.CodeType.ERROR_UNKNOWN.getCode(), "DBS自动转账已执行或正在执行");
//		}
//
//		// 获取限额配置信息
//		String limitCny = configService.getConfigValue(ConfigKeyConstants.FUND_WITHDRAW_LIMIT_CNY);
//		if (StringUtils.isBlank(limitCny)) {
//			log.info("未配置CNY限额配置信息, data:{}", limitCny);
//			return R.fail(SystemCommonEnum.CodeType.ERROR_UNKNOWN.getCode(), "未配置CNY限额配置信息");
//		}
//
//		// 港币限额
//		String limitHkd = configService.getConfigValue(ConfigKeyConstants.FUND_WITHDRAW_LIMIT_HKD);
//		if (StringUtils.isBlank(limitHkd)) {
//			log.info("未配置HKD限额配置信息, data:{}", limitHkd);
//			return R.fail(SystemCommonEnum.CodeType.ERROR_UNKNOWN.getCode(), "未配置HKD限额配置信息");
//		}*/
//
//		// 查询公司账户余额
//		//AccountBalResponse accountBalResponse = dbsFundBusinessService.sendBleQuery(fundWithdrawInfoVo.getPayBankAcct(), fundWithdrawInfoVo.getCcy());
//		AccountBalResponse accountBalResponse = dbsFundBusinessService.sendBleQuery("000000", clientFundWithdrawInfo.getPayBankAcct(), clientFundWithdrawInfo.getCcy());
//		if (null != accountBalResponse) {
//			// 查询付款账户余额
//			log.info("DBS自动出金限额, 付款账户:{} 币种:{} 金额:{}", clientFundWithdrawInfo.getPayBankAcct(), clientFundWithdrawInfo.getCcy(), accountBalResponse.getClsAvailableBal());
//			// 校验实际提取金额
//			if (clientFundWithdrawInfo.getActualAmount().compareTo(accountBalResponse.getClsAvailableBal()) > 0) {
//				log.info("余额不足 付款账户余额:{} 实际提取金额:{} ", accountBalResponse.getClsAvailableBal(), clientFundWithdrawInfo.getActualAmount());
//				return R.fail(SystemCommonEnum.CodeType.ERROR_UNKNOWN.getCode(), "余额不足");
//			}
//		} else {
//			// 查询账户余额失败
//			return R.fail(SystemCommonEnum.CodeType.ERROR_UNKNOWN.getCode(), "查询账户余额失败");
//		}
//
//		return R.success();
//	}
//
//	/**
//	 * DBS转账 同时记录日志流水
//	 * DBS银行自动转账, 支持5种付款方式
//	 * 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇
//	 * 业务付款方式说明：
//	 * 香港本地银行及FPSID：
//	 * (1)FPSID 走PPP （人民币<500W，港币<1000w，无需bankid），仅尝试一次。限额做成配置
//	 * (2)满足（人民币<500W，港币<1000w，有bankid）并且bankid在支持PFS实时转账的列表中的使用GPP 方式（即FPS转账），仅尝试一次。
//	 * (3)美金或者不满足第2点的香港银行取款走RTGS（港币、人民币、美金，SWIFT CODE），仅尝试一次。限额做成配置
//	 * 中国内地、海外银行：
//	 * (4)走TT，仅尝试一次。
//	 *
//	 * @param withdrawApplicationVo
//	 */
//	public void doDbsTransfer(ClientFundWithdrawApplicationVo withdrawApplicationVo) {
//
//		// 获取限额配置信息
//		// 人民币限额
//		//String limitCny = configService.getConfigValue(WithdrawKeyConstants.FUND_WITHDRAW_LIMIT_CNY);
//		String limitCny = "5000000";
//		BigDecimal cnyWithdrawMaxLimit = new BigDecimal(limitCny);
//
//		// 港币限额
//		//String limitHkd = configService.getConfigValue(WithdrawKeyConstants.FUND_WITHDRAW_LIMIT_HKD);
//		String limitHkd = "10000000";
//		BigDecimal hkdWithdrawMaxLimit = new BigDecimal(limitHkd);
//		log.info("DBS自动出金限额, CNY:{} HKD:{}", limitCny, limitHkd);
//
//		// 查询客户取款信息
//		ClientFundWithdrawInfo withdrawInfoVo = withdrawInfoService.queryByApplicationId(withdrawApplicationVo.getApplicationId());
//		// 转账方式 币种 银行编号 提取金额
//		String payType = null;
//		String ccy = withdrawInfoVo.getCcy();
//		// 取自付款方式
//		Integer payWay = withdrawInfoVo.getPayWay();
//		String bankId = withdrawInfoVo.getRecvBankId();
//		BigDecimal withdrawAmount = withdrawInfoVo.getWithdrawAmount();
//
//		// 海外转账
//		/*if (SystemCommonEnum.TransferTypeEnum.OVERSEAS.getType() == payWay) {
//			payType = SystemCommonEnum.PayTypeEnum.DBS_TT.getValue();
//		} else if (SystemCommonEnum.TransferTypeEnum.FPSID.getType() == payWay) {
//			// 人民币 < 500W，港币 < 1000w
//			if (((SystemCommonEnum.MoneyType.CNY.getName().equals(ccy) && withdrawAmount.compareTo(cnyWithdrawMaxLimit) < 0)
//				|| (SystemCommonEnum.MoneyType.HKD.getName().equals(ccy) && withdrawAmount.compareTo(hkdWithdrawMaxLimit) < 0))) {
//				payType = SystemCommonEnum.PayTypeEnum.FPS_PPP.getValue();
//			} else {
//				log.info("FPSID 超过限额, applicationId:{} withdrawAmount:{} cnyLimit:{} hkdLimit:{}", withdrawInfoVo.getApplicationId(), withdrawAmount, cnyWithdrawMaxLimit, hkdWithdrawMaxLimit);
//				// Dbs付款失败
//				updatefundWithdrawUnSuport(withdrawInfoVo.getApplicationId(), SystemCommonEnum.BankStateTypeEnum.FAIL.getValue(),
//					SystemCommonEnum.CodeType.BANK_EXEC_LIMITED.code(), SystemCommonEnum.CodeType.BANK_EXEC_LIMITED.getMessage());
//				return;
//			}
//		} else*/
//		if (SystemCommonEnum.TransferTypeEnum.HK.getType() == payWay || SystemCommonEnum.TransferTypeEnum.HK_LOCAL.getType() == payWay) {
//			// 人民币 < 500W，港币 < 1000w，有bankid
//			if (((SystemCommonEnum.MoneyType.CNY.getName().equals(ccy) && withdrawAmount.compareTo(cnyWithdrawMaxLimit) < 0)
//				|| (SystemCommonEnum.MoneyType.HKD.getName().equals(ccy) && withdrawAmount.compareTo(hkdWithdrawMaxLimit) < 0))
//				&& StringUtils.isNotBlank(bankId)) {
//				payType = SystemCommonEnum.PayTypeEnum.FPS_GPP.getValue();
//			} else {
//				payType = SystemCommonEnum.PayTypeEnum.DBS_RTGS.getValue();
//			}
//		} else {
//			log.info("不支持的付款方式, applicationId:{} payWay:{} ccy:{}", withdrawInfoVo.getApplicationId(), payWay, ccy);
//			// Dbs付款失败
//			updatefundWithdrawUnSuport(withdrawInfoVo.getApplicationId(), SystemCommonEnum.BankStateTypeEnum.FAIL.getValue(),
//				SystemCommonEnum.CodeType.UN_SUPPORTED.code(), SystemCommonEnum.CodeType.UN_SUPPORTED.getMessage());
//			return;
//		}
//		ClientTeltransferInfoVo teltransferInfoVo = null;
//		// DBS执行转账
//		SystemCommonEnum.PayTypeEnum payTypeEnum = SystemCommonEnum.PayTypeEnum.getEnum(payType);
//		// 设置付款类型
//		withdrawInfoVo.setPayType(payTypeEnum.getIndex());
//		switch (payTypeEnum) {
//			case FPS_PPP:
//				doCommonBusiness(BankApiFuncTypeEnum.FPS_PPP, payTypeEnum, withdrawInfoVo);
//				break;
//			case FPS_GPP:
//				doCommonBusiness(BankApiFuncTypeEnum.FPS_GPP, payTypeEnum, withdrawInfoVo);
//				break;
//			case DBS_RTGS:
//				// 查询客户电汇信息
//				teltransferInfoVo = teltransferInfoService.queryByApplicationId(withdrawInfoVo.getApplicationId());
//				if (null != teltransferInfoVo) {
//					withdrawInfoVo.setNationality(teltransferInfoVo.getNationality());
//				}
//				// 设置 银行地址
//				doCommonBusiness(BankApiFuncTypeEnum.CHATS, payTypeEnum, withdrawInfoVo);
//				break;
//			case DBS_ACT:
//				// 暂时无用
//				break;
//			case DBS_TT:
//				// 查询客户电汇信息
//				teltransferInfoVo = teltransferInfoService.queryByApplicationId(withdrawInfoVo.getApplicationId());
//				if (null != teltransferInfoVo) {
//					// 设置 银行地址
//					withdrawInfoVo.setNationality(teltransferInfoVo.getNationality());
//				}
//				doCommonBusiness(BankApiFuncTypeEnum.TT, payTypeEnum, withdrawInfoVo);
//				break;
//			default:
//				break;
//		}
//	}
//
//
//	/**
//	 * 执行付款 FPS PPP GPP RGTS TT
//	 *
//	 * @param fundWithdrawInfoVo
//	 * @return
//	 */
//	public void doCommonBusiness(BankApiFuncTypeEnum funcTypeEnum, SystemCommonEnum.PayTypeEnum payTypeEnum, ClientFundWithdrawInfo fundWithdrawInfoVo) {
//
//		String applicationId = fundWithdrawInfoVo.getApplicationId();
//
//		// 基本付款参数
//		FundTransferEntity fundTransferEntity = new FundTransferEntity();
//		fundTransferEntity.setApplicationId(fundWithdrawInfoVo.getApplicationId());
//
//		fundTransferEntity.setDepositAccount(fundWithdrawInfoVo.getPayAccountName()); // 付款银行账户名称
//		fundTransferEntity.setDepositNo(fundWithdrawInfoVo.getPayBankAcct()); // 付款银行账号
//
//		fundTransferEntity.setClientNameSpell(fundWithdrawInfoVo.getRecvBankAcctName()); // 收款人银行账户名称
//		fundTransferEntity.setBankNo(fundWithdrawInfoVo.getRecvBankAcct()); // 收款人银行账户
//		fundTransferEntity.setActualBalance(fundWithdrawInfoVo.getActualAmount().doubleValue()); // 实际到账金额
//		fundTransferEntity.setMoneyType(SystemCommonEnum.SecMoneyTypeEn.getName(fundWithdrawInfoVo.getCcy())); // 付款币种
//		fundTransferEntity.setDebitMoneyType(SystemCommonEnum.SecMoneyTypeEn.getName(fundWithdrawInfoVo.getCcy())); // 收款币种
//
//		// 香港本地银行 GPP
//		fundTransferEntity.setBankId(fundWithdrawInfoVo.getRecvBankId()); // BankId
//
//		// PPP 支持银行账号类型
//		if (StringUtils.isNotBlank(fundWithdrawInfoVo.getBankAcctType())) {
//			fundTransferEntity.setBankAccountType(Integer.valueOf(fundWithdrawInfoVo.getBankAcctType()));
//		}
//
//		// 汇款人银行区域编码 RGTS/CHATS  TT
//		// 发送人 银行区域
//		// "HK" - for Hong Kong Limited. "HB" - for Hong Kong Branch.
//		fundTransferEntity.setSenderBankCtryCode("HK");
//
//		// 收款人 银行区域
//		// 截取SwiftCode 5-6位
//		String swiftCode = fundWithdrawInfoVo.getRecvSwiftCode();
//		if (StringUtils.isNotBlank(fundWithdrawInfoVo.getRecvSwiftCode())) {
//			fundTransferEntity.setReceiveBankCtryCode(swiftCode.substring(4, 6));
//		}
//
//		// RGTS/CHATS
//		fundTransferEntity.setSwiftCode(fundWithdrawInfoVo.getRecvSwiftCode());
//		// RGTS/CHATS TT 中文字符 限7个 混合字符 35个
//		String recvContactAddress = fundWithdrawInfoVo.getRecvContactAddress();
//		if (StringUtils.isNotBlank(recvContactAddress)) {
//			if (StringUtils.isNumeric(fundWithdrawInfoVo.getRecvContactAddress())) {
//				if (recvContactAddress.length() > 7) {
//					recvContactAddress = recvContactAddress.substring(0, 7);
//				}
//			}
//			if (recvContactAddress.length() > 35) {
//				recvContactAddress = recvContactAddress.substring(0, 35);
//			}
//			fundTransferEntity.setContactAddress(recvContactAddress);
//		} else {
//			// 若地址为空 则使用 区域来设置
//			if (StringUtils.isNotBlank(fundWithdrawInfoVo.getNationality())) {
//				SystemCommonEnum.NationalityTypeEnum nationalityTypeEnum =
//					SystemCommonEnum.NationalityTypeEnum.getEnum(fundWithdrawInfoVo.getNationality());
//				if (null != nationalityTypeEnum) {
//					fundTransferEntity.setContactAddress(nationalityTypeEnum.getName());
//				} else {
//					fundTransferEntity.setContactAddress(SystemCommonEnum.NationalityTypeEnum.HANGKONG.getName());
//				}
//			} else {
//				fundTransferEntity.setContactAddress(SystemCommonEnum.NationalityTypeEnum.HANGKONG.getName());
//			}
//		}
//
//		// 执行付款
//		R<DbsRemitReqLogVo> result = dbsFundBusinessService.sendDbsCommonRemitFund(funcTypeEnum, payTypeEnum, fundTransferEntity);
//		if (result.isSuccess()) {
//
//			// DBS 付款成功
//			DbsRemitReqLogVo dbsRemitReqLogVo = result.getData();
//			updatefundWithdraw(fundWithdrawInfoVo.getApplicationId(), fundWithdrawInfoVo.getPayType(), dbsRemitReqLogVo.getBankState(), dbsRemitReqLogVo);
//
//			// 成功业务处理
//			if (dbsRemitReqLogVo.getBankState() == SystemCommonEnum.BankStateTypeEnum.SUCCESS.getValue()) {
//				String approvalOption = "DBS付款成功";
//				withdrawApplicationService.doCommitNodeByPaySuccess(applicationId, approvalOption);
//			}
//		} else {
//			// Dbs付款失败
//			// 查询流程应用
//			DbsRemitReqLogVo dbsRemitReqLogVo = result.getData();
//			if (dbsRemitReqLogVo != null) {
//				// 银行状态
//				if (dbsRemitReqLogVo.getBankState() == null) {
//					dbsRemitReqLogVo.setBankState(SystemCommonEnum.BankStateTypeEnum.FAIL.getValue());
//				}
//				// 失败原因
//				if (dbsRemitReqLogVo.getRejCode() == null || dbsRemitReqLogVo.getRejDescription() == null) {
//					dbsRemitReqLogVo.setRejCode(result.getCodeStr());
//					dbsRemitReqLogVo.setRejDescription(result.getMsg());
//				}
//			}
//			updatefundWithdraw(fundWithdrawInfoVo.getApplicationId(), fundWithdrawInfoVo.getPayType(), dbsRemitReqLogVo.getBankState(), dbsRemitReqLogVo);
//			// Dbs付款失败 业务处理 完成汇款节点，后续 人工打印或退款申请处理
//			String approvalOption = "DBS付款失败";
//			withdrawApplicationService.doCommitNodeByPayFailed(fundWithdrawInfoVo.getApplicationId(), approvalOption);
//
//			// 发送邮件通知
//			WithdrawNoticeHelper.sendDbsPaySystemEmail(fundWithdrawInfoVo, result.getCodeStr(), result.getMsg());
//		}
//	}
//
//
//	/**
//	 * 业务处理(汇款)
//	 *
//	 * @param applicationId
//	 * @param bankState
//	 * @param resRemitReqLog
//	 * @return
//	 */
//	private void updatefundWithdraw(String applicationId, Integer payType, int bankState, DbsRemitReqLogVo resRemitReqLog) {
//		// 更新出金信息
//		//请求完成更新数据
//		ClientFundWithdrawInfoBo updateBo = new ClientFundWithdrawInfoBo();
//		updateBo.setApplicationId(applicationId);
//		updateBo.setBankState(bankState);
//		updateBo.setPayType(payType);
//		updateBo.setTxnRefId(resRemitReqLog.getTxnRefId());
//		updateBo.setBankReference(resRemitReqLog.getBankReference());
//		updateBo.setBankRtCode(resRemitReqLog.getRejCode());
//		updateBo.setBankMsg(resRemitReqLog.getRejDescription());
//		updateBo.setUpdateTime(new Date());
//		log.info("DBS打款完成 更新银行状态, 流水号：{} bankStata:{} log:{}", applicationId, bankState, resRemitReqLog);
//		withdrawInfoService.updateClientFundWithdrawInfo(updateBo);
//
//		boolean isUpdate = false;
//		ClientFundWithdrawApplicationBo fundWithdrawApplicationBo = new ClientFundWithdrawApplicationBo();
//		fundWithdrawApplicationBo.setApplicationId(applicationId);
//		// 银行执行失败
//		if (bankState == SystemCommonEnum.BankStateTypeEnum.FAIL.getValue() || bankState == SystemCommonEnum.BankStateTypeEnum.UN_COMMNIT.getValue()) {
//			isUpdate = true;
//			fundWithdrawApplicationBo.setApplicationStatus(BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_PAYING_FAILURE_VALUE);
//		} else if (bankState == SystemCommonEnum.BankStateTypeEnum.COMMITTED.getValue()) {
//			// 银行提交成功
//			isUpdate = true;
//			fundWithdrawApplicationBo.setApplicationStatus(BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_PAYING_VALUE);
//		}
//		if (isUpdate) {
//			fundWithdrawApplicationBo.setUpdateTime(new Date());
//			log.info("DBS打款完成 更新流程状态, 流水号：{} bankStata:{} applicationStatus:{}", applicationId, bankState, fundWithdrawApplicationBo.getApplicationStatus());
//			withdrawApplicationService.updateClientFundWithdrawApplication(fundWithdrawApplicationBo);
//		}
//	}
//
//	/**
//	 * 业务处理(不支持的自动付款方式)
//	 *
//	 * @param applicationId
//	 * @param bankState
//	 * @param rejCode
//	 * @param rejDescription
//	 * @return
//	 */
//	private void updatefundWithdrawUnSuport(String applicationId, int bankState, String rejCode, String rejDescription) {
//		// 更新出金信息
//		//请求完成更新数据
//		ClientFundWithdrawInfoBo updateBo = new ClientFundWithdrawInfoBo();
//		updateBo.setApplicationId(applicationId);
//		updateBo.setBankState(bankState);
//		updateBo.setBankRtCode(rejCode);
//		updateBo.setBankMsg(rejDescription);
//		updateBo.setUpdateTime(new Date());
//		log.info("DBS打款完成 更新银行状态, 流水号：{} bankStata:{} log:{}", applicationId, bankState, rejDescription);
//		withdrawInfoService.updateClientFundWithdrawInfo(updateBo);
//
//		ClientFundWithdrawApplicationBo fundWithdrawApplicationBo = new ClientFundWithdrawApplicationBo();
//		fundWithdrawApplicationBo.setApplicationId(applicationId);
//		fundWithdrawApplicationBo.setApplicationStatus(BpmCommonEnum.FundWithdrawApplicationStatus.FUND_WITHDRAW_APPLY_PAYING_FAILURE_VALUE);
//		fundWithdrawApplicationBo.setUpdateTime(new Date());
//		log.info("DBS打款完成 更新流程状态, 流水号：{} bankStata:{} log:{}", applicationId, bankState, rejDescription);
//		withdrawApplicationService.updateClientFundWithdrawApplication(fundWithdrawApplicationBo);
//
//	}
//}
//
