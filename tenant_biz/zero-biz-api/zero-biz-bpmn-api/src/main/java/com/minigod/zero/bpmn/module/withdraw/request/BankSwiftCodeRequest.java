package com.minigod.zero.bpmn.module.withdraw.request;

import java.io.Serializable;

import lombok.Data;

/**
 * @ClassName: BankSwiftCodeRequest
 * @Description:
 * @Author chenyu
 * @Date 2024/3/28
 * @Version 1.0
 */
@Data
public class BankSwiftCodeRequest implements Serializable {
    private String bankCode;
    private String swiftCode;
    private String bankId;
	private String free;
}
