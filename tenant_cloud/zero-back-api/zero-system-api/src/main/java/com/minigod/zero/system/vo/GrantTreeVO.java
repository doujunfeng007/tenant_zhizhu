
package com.minigod.zero.system.vo;

import lombok.Data;
import com.minigod.zero.core.tool.node.TreeNode;

import java.io.Serializable;
import java.util.List;

/**
 * GrantTreeVO
 *
 * @author Chill
 */
@Data
public class GrantTreeVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<TreeNode> menu;

	private List<TreeNode> dataScope;

	private List<TreeNode> apiScope;

}
