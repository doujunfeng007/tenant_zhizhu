package com.minigod.zero.manage.vo.response;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 掌上智珠
 * @since 2023/7/24 19:29
 */
@Data
@AllArgsConstructor
public class GpSensitiveWordDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String word;

	private String gpCode;


}
