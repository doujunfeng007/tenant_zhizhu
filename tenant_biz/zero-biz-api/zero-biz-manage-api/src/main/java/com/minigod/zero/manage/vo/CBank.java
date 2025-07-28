package com.minigod.zero.manage.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: CBank
 * @Description:
 * @Author chenyu
 * @Date 2024/3/28
 * @Version 1.0
 */
@Data
public class CBank implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long withdrawalsId;//出金银行id
    private String hashCode; //hash值
    private String bankName;//银行名称
    private String bankCode;//银行代码
    private String bankId;//银行机构号
	private String swiftCode; // swiftCode
}
