
package com.minigod.zero.develop.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.minigod.zero.develop.entity.Model;
import com.minigod.zero.develop.entity.ModelPrototype;

import java.util.List;

/**
 * 代码模型DTO
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ModelDTO extends Model {

	private static final long serialVersionUID = 1L;

	/**
	 * 代码建模原型
	 */
	private List<ModelPrototype> prototypes;

}
