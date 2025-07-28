package com.minigod.zero.trade.afe.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/15 20:09
 * @description：柜台相关枚举
 */
public interface AfeCounterEnum {

	interface Enums{
		String getCode();
		String getDesc();
	}
	/**
	 * 婚姻状态
	 */
	enum MARITAL_STATUS implements Enums{
		DIVORCED("D","离异"),
		MARRIED("M","已婚"),
		UNMARRIED("U","未婚"),
		WIDOWED("W","喪偶");
		private String code;
		private String desc;

		MARITAL_STATUS(String code, String desc){
			this.code = code;
			this.desc = desc;
		}
		@Override
		public String getCode(){
			return this.code;
		}

		@Override
		public String getDesc() {
			return this.desc;
		}
	}

	/**
	 * 证件类型
	 */
	enum ID_TYPE  implements Enums{
		BR("B","商業登記證號碼"),
		CI("C","公司註冊號碼"),
		ID("I","身份證"),
		PASSPORT("P","護照");
		private String code;
		private String desc;

		ID_TYPE(String code, String desc){
			this.code = code;
			this.desc = desc;
		}
		public String getCode(){
			return this.code;
		}

		@Override
		public String getDesc() {
			return this.desc;
		}
	}

	/**
	 * 性别
	 */
	enum GENDER implements Enums{
		MALE("M","男"),
		FEMALE("F","女");
		private String code;
		private String desc;

		GENDER(String code, String desc){
			this.code = code;
			this.desc = desc;
		}
		public String getCode(){
			return this.code;
		}

		@Override
		public String getDesc() {
			return this.desc;
		}
	}

	/**
	 * 开户类型  0-个人1-联合账户5-机构
	 */
	enum REGISTRATION_TYPE implements Enums{
		PERSONAL_ACCOUNT("0","个人"),
		JOINT_ACCOUNT("1","联合账户"),
		INSTITUTIONAL_ACCOUNTS("5","机构");
		private String code;
		private String desc;

		REGISTRATION_TYPE(String code, String desc){
			this.code = code;
			this.desc = desc;
		}
		public String getCode(){
			return this.code;
		}

		@Override
		public String getDesc() {
			return this.desc;
		}
	}

	/**
	 * 就業狀況
	 */
	enum EMPLOYMENT_STATUS implements Enums{
		Employed("E","在職"),
		SELF_EMPLOYMENT("M","自僱"),
		UNEMPLOYED("U","待業"),
		OTHER("O","其他");
		private String code;
		private String desc;

		EMPLOYMENT_STATUS(String code, String desc){
			this.code = code;
			this.desc = desc;
		}
		public String getCode(){
			return this.code;
		}

		@Override
		public String getDesc() {
			return this.desc;
		}
	}

	/**
	 * 年收入范围
	 */
	enum ANNUAL_SALARY implements Enums{
		LEVEL_ONE("0","≤ $150,000"),
		LEVEL_TWO("1","$150,001 - $500,000"),
		LEVEL_THREE("2","$500,001 - $1,000,000"),
		LEVEL_FOUR("3","$1,000,001 - $5,000,000"),
		LEVEL_FIVE("4",">$5,000,001");
		private String code;
		private String desc;

		ANNUAL_SALARY(String code, String desc){
			this.code = code;
			this.desc = desc;
		}
		public String getCode(){
			return this.code;
		}

		@Override
		public String getDesc() {
			return this.desc;
		}
	}

	/**
	 * 总资产
	 */
	enum TOTAL_ASSET_VALUE implements Enums{
		LEVEL_ONE("0","≤ HKD 250,000"),
		LEVEL_TWO("1","HKD 250,001 - 1,000,000"),
		LEVEL_THREE("2","HKD 1,000,001 - 5,000,000"),
		LEVEL_FOUR("3","HKD 5,000,001 - 20,000,000"),
		LEVEL_FIVE("4","More than HKD 20,000,001");
		private String code;
		private String desc;

		TOTAL_ASSET_VALUE(String code, String desc){
			this.code = code;
			this.desc = desc;
		}
		public String getCode(){
			return this.code;
		}

		@Override
		public String getDesc() {
			return this.desc;
		}
	}

	/**
	 * 收入来源
	 */
	enum SOURCE_OF_INCOME implements Enums{
		BUSINESS_INCOME("B","營商收益"),
		INHERITANCE_GIFT("N","遺產/餽贈"),
		INVESTMENT_RETURN("I","投資收益"),
		SALARY("A","薪酬"),
		PENSION("P","退休金"),
		SAVING("S","儲蓄"),
		RENTAL_INCOME("R","租金收入"),
		OTHERS("T","其他");
		private String code;
		private String desc;

		SOURCE_OF_INCOME(String code, String desc){
			this.code = code;
			this.desc = desc;
		}
		public String getCode(){
			return this.code;
		}

		@Override
		public String getDesc() {
			return this.desc;
		}
	}

	/**
	 * 住宅所有权
	 */
	enum OWNERSHIP_OF_RESIDENCE implements Enums{
		MORTGAGE("M","按揭/每月供款 (港元)"),
		OWNED("O","自置"),
		RENTAL("R","租用"),
		WITH_PARENTS("P","與父母同住"),
		OTHERS("T","其他");
		private String code;
		private String desc;

		OWNERSHIP_OF_RESIDENCE(String code, String desc){
			this.code = code;
			this.desc = desc;
		}
		public String getCode(){
			return this.code;
		}

		@Override
		public String getDesc() {
			return this.desc;
		}
	}

	/**
	 * 投资目标
	 */
	enum INVESTMENT_TARGET implements Enums{
		AGGRESSIVE("A","進取"),
		GROWTH("G","增長"),
		CONSERVATIVE("C","保守"),
		OTHERS("T","其他");
		private String code;
		private String desc;

		INVESTMENT_TARGET(String code, String desc){
			this.code = code;
			this.desc = desc;
		}
		public String getCode(){
			return this.code;
		}

		@Override
		public String getDesc() {
			return this.desc;
		}
	}

	/**
	 * 每月交易频率
	 */
	enum INVESTMENT_FREQUENCY implements Enums{
		LEVEL_ONE("0","0-5"),
		LEVEL_TWO("1","6-30"),
		LEVEL_THREE("2","31-100"),
		LEVEL_FOUR("3",">100");
		private String code;
		private String desc;

		INVESTMENT_FREQUENCY(String code, String desc){
			this.code = code;
			this.desc = desc;
		}
		public String getCode(){
			return this.code;
		}

		@Override
		public String getDesc() {
			return this.desc;
		}
	}

	/**
	 * 接口返回
	 */
	enum ResultType implements Enums{
		SUCCESS("SUCCESS","处理成功"),
		ERROR_VALUE_IS_NOT_OPTIONAL("ERROR_VALUE_IS_NOT_OPTIONAL","强制填字段中缺少值"),
		ERROR_VALUE_IS_NOT_IN_LOOKUP_LIST("ERROR_VALUE_IS_NOT_IN_LOOKUP_LIST","提交的值不在特定字段的查找列表中"),
		ERROR_VALUE_IS_NOT_IN_REQUIRED_FORMAT("ERROR_VALUE_IS_NOT_IN_REQUIRED_FORMAT","提交的值不满足特定字段的要求格式"),
		ERROR_UNKNOWN("ERROR_UNKNOWN","系统异常");
		private String code;
		private String desc;

		ResultType(String code, String desc){
			this.code = code;
			this.desc = desc;
		}
		public String getCode(){
			return this.code;
		}

		@Override
		public String getDesc() {
			return this.desc;
		}

		public static String getDescByCode(String code){
			for (ResultType type: ResultType.values()){
				if (type.getCode().equals(code)){
					return type.getDesc();
				}
			}
			return null;
		}
	}
}
