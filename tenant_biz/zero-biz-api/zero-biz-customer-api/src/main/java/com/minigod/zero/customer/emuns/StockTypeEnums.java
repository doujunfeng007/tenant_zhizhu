package com.minigod.zero.customer.emuns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: AccountTypeEnums
 * @Description: 账户类型
 * @Author chenyu
 * @Date 2024/9/24
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum StockTypeEnums {
    STOCK("1","ST","股票"),
    US_STOCK_OPTION("2","USSO","美股期权"),
    HK_STOCK_OPTION("3","HKSO","港股期权"),
    FUND("4","FU","基金"),
    ;


    private String value;
    private String prefix;
    private String desc;
}
