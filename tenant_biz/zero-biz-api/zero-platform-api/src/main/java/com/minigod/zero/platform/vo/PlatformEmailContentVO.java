package com.minigod.zero.platform.vo;

import com.minigod.zero.platform.entity.PlatformEmailContentEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author 掌上智珠
 * @since 2023-03-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PlatformEmailContentVO extends PlatformEmailContentEntity {
	private static final long serialVersionUID = 1L;

	private String sendDate;
	private Date sendDateStart;
	private Date sendDateEnd;

}
