package com.minigod.zero.customer.utils;

import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/25 10:16
 * @description：
 */
@Component
public class IdGenerateUtils {
	@Resource
	private ZeroRedis miniGodRedis;

	public static final String NICK_NAME = "用户";

	private static final String ORGANIZATION = "B";

	private static final Integer MAX_DIGITS = 7;

	private static final String DEFAULT_PASSWORD = "123456";

	private static final String NICK_NAME_CACHE_KEY = "generate:nick_name:";

	private static final String ACCOUNT_CACHE_KEY = "generate:account:";
	private static final String OPTION_ACCOUNT_CACHE_KEY = "generate:option:";
	private static final String STOCK_ACCOUNT_CACHE_KEY = "generate:stock:";

	private static final String SUB_ACCOUNT_CACHE_KEY = "generate:sub_account:%s:%s";

	private static final String ORGANIZATION_ACCOUNT_CACHE_KEY = "generate:organization:account:";


	public static Integer getRandom(int n) {
		int min = (int) Math.pow(10, n - 1); // 最小值是10^(n-1)
		int max = (int) Math.pow(10, n) - 1; // 最大值是10^n - 1
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min + 1)) + min; // 生成n位的随机数
		return randomNum;
	}

	/**
	 * 获取默认昵称
	 *
	 * @return
	 */
	public static String getNickName() {
		String language = WebUtil.getLanguage();
		String nickName = NICK_NAME;
		switch (language) {
			case "zh-hans":
				nickName = "用户";
				break;
			case "zh-hant":
				nickName = "用戶";
				break;
			case "en":
				nickName = "User";
				break;
		}
		return nickName;
	}

	/**
	 * B0000001
	 * 机构账号生成
	 *
	 * @return
	 */
	public String generateOrganizationId() {
		Long account = miniGodRedis.incrBy(ORGANIZATION_ACCOUNT_CACHE_KEY, 1);
		int digits = countDigits(account.intValue());
		int size = MAX_DIGITS;
		if (digits >= MAX_DIGITS) {
			size = digits + 1;
		}
		StringBuffer organizationId = new StringBuffer(ORGANIZATION);
		for (int i = 0; i < size - digits; i++) {
			organizationId.append("0");
		}
		organizationId.append(account);
		return organizationId.toString();
	}

	/**
	 * 个人账号生成customer_bond_trading_account
	 *
	 * @return
	 */
	public String generateId() {
		Long account = null;
		if (!miniGodRedis.exists(ACCOUNT_CACHE_KEY)) {
			account = miniGodRedis.incrBy(ACCOUNT_CACHE_KEY, 100000L);
		} else {
			account = miniGodRedis.incrBy(ACCOUNT_CACHE_KEY, 1);
		}
		return String.valueOf(account);
	}

	public  String generateOptionId(){
		Long account = null;
		if (!miniGodRedis.exists(OPTION_ACCOUNT_CACHE_KEY)){
			account = miniGodRedis.incrBy(OPTION_ACCOUNT_CACHE_KEY,100000L);
		}else {
			account = miniGodRedis.incrBy(OPTION_ACCOUNT_CACHE_KEY,1);
		}
		return String.valueOf(account);
	}

	public String generateSubAccount(String accountId,String prefix){
		String key = String.format(SUB_ACCOUNT_CACHE_KEY,accountId,prefix);
		Long num = miniGodRedis.incr(key);
		return String.valueOf(num);
	}

	/**
	 * 用户默认昵称
	 *
	 * @return
	 */
	public String generateNickName() {
		Integer number = getRandom(8);
		boolean flag = miniGodRedis.getSetOps().isMember(NICK_NAME_CACHE_KEY, number);
		if (flag) {
			return generateNickName();
		} else {
			miniGodRedis.getSetOps().add(NICK_NAME_CACHE_KEY, number);
			return getNickName() + number;
		}
	}

	/**
	 * 初始密码
	 *
	 * @return
	 */
	public String generatePassword() {
		return String.valueOf(getRandom(6));
	}

	/**
	 * 校验数字是几位数
	 *
	 * @param number
	 * @return
	 */
	public static int countDigits(int number) {
		if (number == 0) {
			return 1; // 0被认为是一位数
		}
		int count = 0;
		while (number != 0) {
			number /= 10;
			count++;
		}
		return count;
	}

	/**
	 * 身份证后六位
	 *
	 * @param idCard
	 * @return
	 */
	public static String lastSixDigitsOfTheIdCard(String idCard) {
		if (StringUtil.isBlank(idCard)) {
			return DEFAULT_PASSWORD;
		}
		idCard = replaceNonDigitsWithZeroRegex(idCard);
		if (idCard.indexOf(")") >= 0) {
			idCard = idCard.replace(")", "");
		}
		if (idCard.indexOf("(") >= 0) {
			idCard = idCard.replace("(", "");
		}
		return idCard.substring(idCard.length() - 6, idCard.length());
	}

	/**
	 * 使用正则表达式将字符串中的非数字字符替换为'0'
	 *
	 * @param input 输入的字符串
	 * @return 处理后的字符串
	 */
	public static String replaceNonDigitsWithZeroRegex(String input) {
		return input.replaceAll("[^\\d]", "0"); // 使用正则表达式替换非数字字符为'0'
	}

	public String generateStockId() {
		Long account = null;
		if (!miniGodRedis.exists(STOCK_ACCOUNT_CACHE_KEY)){
			account = miniGodRedis.incrBy(STOCK_ACCOUNT_CACHE_KEY,100000L);
		}else {
			account = miniGodRedis.incrBy(STOCK_ACCOUNT_CACHE_KEY,1);
		}
		return "ZS"+String.valueOf(account);
	}
}
