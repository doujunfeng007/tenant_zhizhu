package com.minigod.zero.manage.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class StkVersionVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer version;
	private Integer currentPage; // 当前页，第一页为1
	private Integer pageSize; // 每页大小
}
