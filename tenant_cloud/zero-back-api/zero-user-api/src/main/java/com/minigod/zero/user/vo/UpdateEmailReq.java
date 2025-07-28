package com.minigod.zero.user.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 掌上智珠
 * @since 2023/9/19 18:28
 */
@Data
public class UpdateEmailReq implements Serializable {
	private String loginEmail;

	private String email;

	private Integer userType;
}
