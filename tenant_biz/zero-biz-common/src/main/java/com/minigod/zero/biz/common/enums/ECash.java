package com.minigod.zero.biz.common.enums;

/**
 * @author huangwei
 * @Description 现金枚举类
 * @mail h549866023@qq.com
 * @date 2022/11/24 14:54
 **/
public enum ECash {
	RMB_CHN("人民币", "RMB.CHN");

	private String typeName;
	private String typeValue;

	private ECash(String typeName, String typeValue) {
		this.typeName = typeName;
		this.typeValue = typeValue;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public String getTypeValue() {
		return this.typeValue;
	}

	/**
	 * 检查是否现金
	 *
	 * @param assetId
	 * @return
	 */
	public static boolean isCash(String assetId) {
		for (ECash cash : ECash.values()) {
			if (cash.getTypeValue().equals(assetId)) {
				return true;
			}
		}
		return false;
	}

}
