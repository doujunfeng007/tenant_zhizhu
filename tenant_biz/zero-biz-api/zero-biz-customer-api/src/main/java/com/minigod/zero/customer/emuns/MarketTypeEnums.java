package com.minigod.zero.customer.emuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 市场类型
 */
@AllArgsConstructor
@Getter
public enum MarketTypeEnums {
	ML("ML","中华通"),
	HK("HK","港股"),
	US("US","美股"),
	BOND("BOND","债券账户"),
	HLD("HLD","活利得"),
	FUND("FUND","基金"),
	OPT("OPT","期权账户"),
    ;


    private String marketType;
    private String maketName;
}
