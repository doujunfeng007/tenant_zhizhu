package com.minigod.zero.cust.dto;

import com.minigod.zero.cust.entity.FeedbackEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户意见反馈表 数据传输对象实体类
 *
 * @author 掌上智珠
 * @since 2023-03-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FeedbackDTO extends FeedbackEntity {
	private static final long serialVersionUID = 1L;

}
