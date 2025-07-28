package com.minigod.zero.biz.common.enums;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/13 21:56
 * @description：
 */
public enum ImageLocation {
	BankCard("2", "201", "银行卡"),
	IdCardFront("1", "101", "身份证正面"),
	IdCardBack("1", "102", "身份证反面"),
	Permit("1", "108", "港澳通行证"), // 港澳台居住证身份书
	Passport("1", "106", "护照"),
	HKIdCard("1","105","香港身份证"),
	MLIDPassport("1","104","港澳台地区及境外护照"),
	HmtResidentPermit("1","107","港澳台居住证"),
	LivingVideo("1","901","用户视频");


	private String locationKey;
	private String locationType;
	private String message;

	ImageLocation(String locationKey, String locationType, String message) {
		this.locationKey = locationKey;
		this.locationType = locationType;
		this.message = message;
	}

	public String locationType(){
		return this.locationType;
	}
	public String locationKey(){
		return this.locationKey;
	}
}
