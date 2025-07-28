package com.minigod.zero.trade.afe.util;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chen
 * @ClassName SenderIdUtil.java
 * @Description TODO
 * @createTime 2024年06月24日 17:13:00
 */
public class SenderIdUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	/**生成唯一请求id*/
	public static AtomicLong autoIncrement = new AtomicLong(0);
}
