package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.manage.dto.InvestMsgSearchReqDTO;
import com.minigod.zero.manage.dto.SendInvestMsgReqDTO;
import com.minigod.zero.platform.entity.PlatformInvestMsgEntity;

/**
 * 推送记录表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-11
 */
public interface IPlatformInvestMsgService extends BaseService<PlatformInvestMsgEntity> {

	void saveUpdateAndSendMsg(SendInvestMsgReqDTO sendInvestMsgReqDTO);

	IPage<PlatformInvestMsgEntity> getInvestMsgList(IPage page, InvestMsgSearchReqDTO dto);
}
