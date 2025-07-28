package com.minigod.zero.manage.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * oms使用save或者edit就会将该对象存储到redis中,并且定时任务会读取该对象并且进行处理
 * @author 掌上智珠
 * @since 2023/7/26 21:29
 */
@Data
public class SensitiveFilterHistoryVo implements Serializable {
	private String word;
	private Date updateDate;
	private String assetId;
	private String source;
	private Integer timeScope;
}
