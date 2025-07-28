
package com.minigod.zero.flow.engine.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import com.minigod.zero.flow.engine.utils.FlowCache;

import java.io.Serializable;
import java.util.Date;

/**
 * FlowProcess
 *
 * @author Chill
 */
@Data
@NoArgsConstructor
public class FlowProcess implements Serializable {

	private String id;
	private String tenantId;
	private String name;
	private String key;
	private String category;
	private String categoryName;
	private Integer version;
	private String deploymentId;
	private String resourceName;
	private String diagramResourceName;
	private Integer suspensionState;
	private Date deploymentTime;

	public FlowProcess(ProcessDefinitionEntityImpl entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.tenantId = entity.getTenantId();
			this.name = entity.getName();
			this.key = entity.getKey();
			this.category = entity.getCategory();
			this.categoryName = FlowCache.getCategoryName(entity.getCategory());
			this.version = entity.getVersion();
			this.deploymentId = entity.getDeploymentId();
			this.resourceName = entity.getResourceName();
			this.diagramResourceName = entity.getDiagramResourceName();
			this.suspensionState = entity.getSuspensionState();
		}
	}

}
