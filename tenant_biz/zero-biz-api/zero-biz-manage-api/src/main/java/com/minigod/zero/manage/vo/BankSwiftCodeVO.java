package com.minigod.zero.manage.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName: BankSwiftCodeRequest
 * @Description:
 * @Author chenyu
 * @Date 2024/3/28
 * @Version 1.0
 */
@Data
public class BankSwiftCodeVO {
    private String bankCode;
    private String swiftCode;
    private String bankId;
	private String free;
}
