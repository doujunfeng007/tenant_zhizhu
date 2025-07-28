
package com.minigod.zero.resource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.resource.vo.AttachVO;
import com.minigod.zero.resource.entity.Attach;

import java.util.List;

/**
 * 附件表 Mapper 接口
 *
 * @author Chill
 */
public interface AttachMapper extends BaseMapper<Attach> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param attach
	 * @return
	 */
	List<AttachVO> selectAttachPage(IPage page, AttachVO attach);

}
