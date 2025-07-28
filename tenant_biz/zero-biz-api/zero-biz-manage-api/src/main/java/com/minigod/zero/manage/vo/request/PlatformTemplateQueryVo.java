package com.minigod.zero.manage.vo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 掌上智珠
 * @since 2023/9/11 14:58
 */
@Data
public class PlatformTemplateQueryVo implements Serializable {
	private String keyword;

	private Integer busType;

	private Long id;

	private String tempName;

	private Integer msgType;

	private Integer queryType;
}
