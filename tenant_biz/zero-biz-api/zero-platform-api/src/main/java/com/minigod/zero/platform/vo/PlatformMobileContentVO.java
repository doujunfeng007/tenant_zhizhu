package com.minigod.zero.platform.vo;

import com.minigod.zero.platform.entity.PlatformMobileContentEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author 掌上智珠
 * @since 2023-03-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlatformMobileContentVO extends PlatformMobileContentEntity {
	private static final long serialVersionUID = 1L;

	private String sendDate;
	private Date sendDateStart;
	private Date sendDateEnd;
	private Long userId;

}
