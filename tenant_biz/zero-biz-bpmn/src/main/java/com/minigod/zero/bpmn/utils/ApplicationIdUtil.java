package com.minigod.zero.bpmn.utils;

import cn.hutool.core.date.DateUtil;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.utils.SpringUtil;

import java.text.DecimalFormat;
import java.util.Date;

import static com.minigod.zero.bpmn.utils.ApplicationIdUtil.Business.*;

/**
 * @ClassName: ApplicationIdUtil
 * @Description: 预约号工具类
 * @Author chenyu
 * @Date 2022/7/22
 * @Version 1.0
 */
public class ApplicationIdUtil {

	enum Business {

		COMMON("ID:COMMON", "00"),
		AGREEMENT("ID:AGREEMENT", "02"),
		OPEN_ONLINE("ID:OPEN:ONLINE", "01"),
		FUND_DEPOSIT("ID:FUND:DEPOSIT", "03"),
		MARGIN_CREDIT("ID:MARGIN:CREDIT", "04"),
		BANK_CARD("ID:FUND:BANKCARD", "05"),
		WITHDRAW("ID:FUND:WITHDRAW", "06"),
		REFUND("ID:FUND:REFUND", "07"),
		DBS("ID:DBS", "99"),
		CURRENCY_EXCHANGE("ID:CURRENCY:EXCHANGE", "08"),
		PROFESSIONAL_INVESTOR("ID:PROFESSIONAL:INVESTOR", "09"),
		FUND_TRANS("ID:FUND:TRANS", "10"),
		ORGAUDIT("ID:ORGACCOUNT:AUDIT", "10"),
		;


		Business(String key, String value) {
			this.key = key;
			this.value = value;
		}

		private String key;
		private String value;
	}

	/**
	 * 开户预约号
	 *
	 * @return
	 */
	public static String generateOpenOnlineId(String tenantId) {
		return generateId(OPEN_ONLINE.key, OPEN_ONLINE.value, tenantId);
	}

	/**
	 * 信用额度
	 *
	 * @param tenantId
	 * @return
	 */
	public static String generateMarginCreditId(String tenantId) {
		return generateId(MARGIN_CREDIT.key, MARGIN_CREDIT.value, tenantId);
	}

	/**
	 * 货币兑换
	 *
	 * @param tenantId
	 * @return
	 */
	public static String generateCurrencyExchangeId(String tenantId) {
		return generateId(CURRENCY_EXCHANGE.key, CURRENCY_EXCHANGE.value, tenantId);
	}

	/**
	 * 全局预约号
	 *
	 * @return
	 */
	public static String generateCommonId(String tenantId) {
		return generateId(COMMON.key, COMMON.value, tenantId);
	}

	/**
	 * 协议编号
	 *
	 * @return
	 */
	public static String generateAgreementId(String tenantId) {
		return generateId(AGREEMENT.key, AGREEMENT.value, tenantId);
	}

	/**
	 * 入金预约号
	 *
	 * @param tenantId
	 * @return
	 */
	public static String generateDepositId(String tenantId) {
		return generateId(FUND_DEPOSIT.key, FUND_DEPOSIT.value, tenantId);
	}

	/**
	 * PI申请编号
	 *
	 * @param tenantId
	 * @return
	 */
	public static String generateProfessionalInvestorId(String tenantId) {
		return generateId(PROFESSIONAL_INVESTOR.key, PROFESSIONAL_INVESTOR.value, tenantId);
	}
	/**
	 * 机构户审核流水号
	 */
	public static String generateOrgAuditId(String tenantId) {
		return generateId(ORGAUDIT.key, ORGAUDIT.value, tenantId);
	}

	/**
	 * 银行卡审核流水号
	 *
	 * @param tenantId
	 * @return
	 */
	public static String generateBankCardId(String tenantId) {
		return generateId(BANK_CARD.key, BANK_CARD.value, tenantId);
	}

	/**
	 * 出金审批流水
	 *
	 * @param tenantId
	 * @return
	 */
	public static String generateWithdrawApplicationId(String tenantId) {
		return generateId(WITHDRAW.key, WITHDRAW.value, tenantId);
	}

	/**
	 * DBS请求流水
	 *
	 * @param tenantId
	 * @return
	 */
	public static String generateDbsMsgId(String tenantId) {
		return generateId(DBS.key, DBS.value, tenantId);
	}

	/**
	 * 退款审批流水
	 *
	 * @param tenantId
	 * @return
	 */
	public static String generateRefundApplicationId(String tenantId) {
		return generateId(REFUND.key, REFUND.value, tenantId);
	}

	public static String generateFundTransId(String tenantId) {
		return generateId(FUND_TRANS.key, FUND_TRANS.value, tenantId);
	}

	private static String generateId(String key, String businessNo, String tenantId) {
		String date = DateUtil.format(new Date(), "yyyyMMdd");
		String dateKey = key + ":" + tenantId + ":" + date;
		ZeroRedis zeroRedis = SpringUtil.getBean(ZeroRedis.class);
		long id = zeroRedis.incr(dateKey);
		zeroRedis.expire(dateKey, 60 * 60 * 24);
		DecimalFormat decimalFormat = new DecimalFormat("000");
		String numFormat = decimalFormat.format(id);
		return date + tenantId + businessNo + numFormat;
	}

	public static String generateClientId(String tenantId) {
		String date = DateUtil.format(new Date(), "MMdd");
		String dateKey = "clientId:" + tenantId + ":" + date;
		ZeroRedis zeroRedis = SpringUtil.getBean(ZeroRedis.class);
		long id = zeroRedis.incr(dateKey);
		zeroRedis.expire(dateKey, 60 * 60 * 24);
		DecimalFormat decimalFormat = new DecimalFormat("0000");
		String numFormat = decimalFormat.format(id);
		return date + numFormat;
	}
}
