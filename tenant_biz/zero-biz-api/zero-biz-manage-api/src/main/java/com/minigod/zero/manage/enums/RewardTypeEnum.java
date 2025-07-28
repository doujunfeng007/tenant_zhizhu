package com.minigod.zero.manage.enums;

/**
 * 奖品类型
 *
 * @author eric
 * @since 2024-12-24 13:58:08
 */
public enum RewardTypeEnum {
	COMMISSS_FREE("免佣",1),
	MONEY("现金",2),
	MktINFO_FREE("行情",3),
	HANDSEL_STOCK("赠股" , 4),
	GROUP_POINTS("积分" , 5),
	CASH_DEDUCTION("现金抵扣",6)
	;
	private String name;
	private int vaule;
	RewardTypeEnum(String name, int value) {
		this.name=name;
		this.vaule=value;
	}

	public static String getNameByKey(int key){
		for(RewardTypeEnum rewardTypeEnum : RewardTypeEnum.values()) {
			if(key == rewardTypeEnum.getValue()) {
				return rewardTypeEnum.getName();
			}
		}
		return "";
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return vaule;
	}
}
