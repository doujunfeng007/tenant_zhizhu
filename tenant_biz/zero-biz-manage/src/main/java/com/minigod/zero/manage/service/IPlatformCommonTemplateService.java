package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.vo.MsgTemplateVO;
import com.minigod.zero.platform.entity.PlatformCommonTemplateEntity;

public interface IPlatformCommonTemplateService extends BaseService<PlatformCommonTemplateEntity> {


	R<IPage<MsgTemplateVO>> getTemplateList(IPage page, String keyword, Integer msgType);

	R<PlatformCommonTemplateEntity> getTemplateDetail(Long id);

	R addTemplate(PlatformCommonTemplateEntity template);

	R updateTemplate(PlatformCommonTemplateEntity template);

	R deleteTemplate(Long id);

	R selectTemplateByMsgType(Integer msgType);

}
