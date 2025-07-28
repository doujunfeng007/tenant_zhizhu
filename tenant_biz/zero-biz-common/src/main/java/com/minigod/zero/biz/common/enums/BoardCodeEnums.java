package com.minigod.zero.biz.common.enums;

public enum BoardCodeEnums {

	MARKET_CODE("市场", "1001"),
	OPTION_CODE("自选", "1002"),
	STOCK_DETAIL("个股详情", "1003"),
	PC_ALTER("pc弹窗", "1004"),
	TRADE_CODE("交易","1005"),
	NEW_CODE("资讯","1006"),
	FINANCING_CODE("理财","1007"),
	MINE_CODE("我的","1008"),
	;

	private String codeName;
	private String codeValue;

	private BoardCodeEnums(String codeName, String codeValue) {
		this.codeName = codeName;
		this.codeValue = codeValue;
	}

	public static String getCodeName(String code) {
		for (BoardCodeEnums eAdPosCode : BoardCodeEnums.values()) {
			if (eAdPosCode.getCodeValue().equals(code)) {
				return eAdPosCode.getCodeName();
			}
		}
		return null;
	}

	public String getCodeName() {
		return codeName;
	}


	public String getCodeValue() {
		return codeValue;
	}

}
