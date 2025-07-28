
package com.minigod.zero.resource.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.resource.service.IAttachService;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.resource.entity.Attach;
import com.minigod.zero.resource.mapper.AttachMapper;
import com.minigod.zero.resource.vo.AttachVO;
import org.springframework.stereotype.Service;

/**
 * 附件表 服务实现类
 *
 * @author Chill
 */
@Service
public class AttachServiceImpl extends BaseServiceImpl<AttachMapper, Attach> implements IAttachService {

	@Override
	public IPage<AttachVO> selectAttachPage(IPage<AttachVO> page, AttachVO attach) {
		return page.setRecords(baseMapper.selectAttachPage(page, attach));
	}

}
