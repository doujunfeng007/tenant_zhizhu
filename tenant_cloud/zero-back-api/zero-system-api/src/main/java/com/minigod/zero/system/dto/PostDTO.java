
package com.minigod.zero.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.minigod.zero.system.entity.Post;

/**
 * 岗位表数据传输对象实体类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostDTO extends Post {
	private static final long serialVersionUID = 1L;

}
