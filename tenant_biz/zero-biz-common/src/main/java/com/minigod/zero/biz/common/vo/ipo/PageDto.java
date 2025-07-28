package com.minigod.zero.biz.common.vo.ipo;

import lombok.Data;

import java.util.List;

/**分页对象*/
@Data
public class PageDto<T> {
	private int totalCount;//总记录数
	private int totalPages;//总页数
	private List<T> list;

}
