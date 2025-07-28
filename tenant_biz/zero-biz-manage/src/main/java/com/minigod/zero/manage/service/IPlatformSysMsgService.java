package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.platform.dto.SendSysMsgReqDTO;
import com.minigod.zero.platform.dto.SysMsgSearchReqDTO;
import com.minigod.zero.platform.entity.PlatformSysMsgEntity;

/**
 * 系统通知信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-11
 */
public interface IPlatformSysMsgService extends BaseService<PlatformSysMsgEntity> {

	IPage<PlatformSysMsgEntity> getSysMsgList(SysMsgSearchReqDTO sysMsgSearchReqDTO, Query query);

	void saveUpdateAndSendSysMsg(SendSysMsgReqDTO sendSysMsgReqDTO);

	PlatformSysMsgEntity findSysMsg(Long id);
}
