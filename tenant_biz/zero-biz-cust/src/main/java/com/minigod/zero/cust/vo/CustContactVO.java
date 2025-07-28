package com.minigod.zero.cust.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 客户联系方式
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Data
public class CustContactVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long custId;

	private String phoneArea;

	private String phoneNumber;

	private String email;

	private Long bindCust;

}
