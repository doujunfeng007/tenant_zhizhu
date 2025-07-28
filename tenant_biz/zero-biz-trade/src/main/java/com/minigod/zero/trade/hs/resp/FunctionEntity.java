package com.minigod.zero.trade.hs.resp;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sunline on 2016/4/20 10:25.
 * sunline
 */
@Data
public class FunctionEntity {

	/**
	 * 功能号
	 */
    protected int miFuncNo = 0;

	/**
	 * 功能号描述
	 */
    protected String funDesc = "";

	/**
	 * 请求字段
	 */
	public String[] asRequestField = null;

	/**
	 * 响应字段
	 */
    protected String[] asResponseField = null;

	/**
	 * 中文字段
	 */
    protected Set<String> asChtField = new HashSet();
}
