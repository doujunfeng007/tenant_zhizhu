
package com.minigod.zero.resource.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.resource.vo.AttachVO;
import com.minigod.zero.resource.entity.Attach;

/**
 * 附件表 服务类
 *
 * @author Chill
 */
public interface IAttachService extends BaseService<Attach> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param attach
	 * @return
	 */
	IPage<AttachVO> selectAttachPage(IPage<AttachVO> page, AttachVO attach);

}
