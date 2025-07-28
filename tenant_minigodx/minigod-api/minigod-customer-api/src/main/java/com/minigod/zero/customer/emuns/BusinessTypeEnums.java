package com.minigod.zero.customer.emuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务类型
 */
@AllArgsConstructor
@Getter
public enum BusinessTypeEnums {
	SEC("SEC","股票账户"),
	OPT("OPT","期权账户"),
	FUND("FUND","基金账户"),
	BOND("BOND","债券账户"),
	HLD("HLD","活利得账户"),
    ;


    private String businessType;
    private String businessName;
}
