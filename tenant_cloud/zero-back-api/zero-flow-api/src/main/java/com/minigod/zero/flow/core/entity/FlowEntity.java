
package com.minigod.zero.flow.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;

/**
 * FlowEntity
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FlowEntity extends BaseEntity {

	@TableField(exist = false)
	private ZeroFlow flow;

	public ZeroFlow getFlow() {
		if (flow == null) {
			flow = new ZeroFlow();
		}
		return flow;
	}

}
