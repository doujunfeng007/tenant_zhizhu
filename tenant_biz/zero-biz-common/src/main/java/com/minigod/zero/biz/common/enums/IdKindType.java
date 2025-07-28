package com.minigod.zero.biz.common.enums;

import com.minigod.zero.core.tool.utils.StringUtil;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/13 21:47
 * @description：
 */
public enum IdKindType {
	DEFAULT(-1,"","","未知"),
	ID_CARD_FRONT(1,ImageLocation.IdCardFront.locationKey(),ImageLocation.IdCardFront.locationType(),"大陆身份证"),
	PERMIT_ID_CARD(2,ImageLocation.HKIdCard.locationKey(),ImageLocation.HKIdCard.locationType(),"港澳通行证"),
	HK_ID_CARD(3,ImageLocation.HKIdCard.locationKey(),ImageLocation.HKIdCard.locationType(),"香港居民身份证"),
	PASSPORT(4,ImageLocation.Passport.locationKey(),ImageLocation.Passport.locationType(),"护照");

	private Integer idKind;
	private String locationKey;
	private String locationType;
	private String  desc;


	IdKindType(Integer idKind, String locationKey, String locationType, String  desc){
		this.idKind = idKind;
		this.desc = desc;
		this.locationType =locationType;
		this.locationKey = locationKey;
	}

	public Integer getIdKind(){
		return idKind;
	}
	public String locationType(){
		return this.locationType;
	}
	public String locationKey(){
		return this.locationKey;
	}
	public String getDesc(){
		return this.desc;
	}

	public static IdKindType getByIdKind(Integer idKind) {
		if (idKind == null){
			return DEFAULT;
		}
		for (IdKindType type : IdKindType.values()) {
			if (idKind.equals(type.getIdKind())) {
				return type;
			}
		}
		return null;
	}

	public static IdKindType getByIdKind(String idKind) {
		if (StringUtil.isBlank(idKind)){
			return DEFAULT;
		}
		for (IdKindType type : IdKindType.values()) {
			if (Integer.valueOf(idKind).equals(type.getIdKind())) {
				return type;
			}
		}
		return null;
	}
}
