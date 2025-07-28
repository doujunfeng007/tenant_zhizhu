
package com.minigod.zero.flow.engine.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 运行实体类
 *
 * @author Chill
 */
@Data
public class FlowExecution implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String startUserId;
	private String startUser;
	private Date startTime;
	private String taskDefinitionId;
	private String taskDefinitionKey;
	private String category;
	private String categoryName;
	private String processInstanceId;
	private String processDefinitionId;
	private String processDefinitionKey;
	private String activityId;
	private int suspensionState;
	private String executionId;

}
