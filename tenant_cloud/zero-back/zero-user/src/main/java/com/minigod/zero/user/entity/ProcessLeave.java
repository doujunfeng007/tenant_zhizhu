package com.minigod.zero.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.flow.core.entity.FlowEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 请假流程实体类
 *
 * @author Chill
 */
@Data
@TableName("zero_process_leave")
@EqualsAndHashCode(callSuper = true)
public class ProcessLeave extends FlowEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 流程定义id
	 */
	private String processDefinitionId;
	/**
	 * 流程实例id
	 */
	private String processInstanceId;
	/**
	 * 请假开始时间
	 */
	private Date startTime;
	/**
	 * 请假结束时间
	 */
	private Date endTime;
	/**
	 * 请假理由
	 */
	private String reason;
	/**
	 * 审批人
	 */
	private String taskUser;
	/**
	 * 流程申请时间
	 */
	private Date applyTime;

}
