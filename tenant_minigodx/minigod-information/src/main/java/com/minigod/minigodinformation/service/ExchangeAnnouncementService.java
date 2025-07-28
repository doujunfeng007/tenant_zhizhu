package com.minigod.minigodinformation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.minigodinformation.dto.ClientExchangeAnnListDTO;
import com.minigod.minigodinformation.entity.ExchangeAnnouncement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.minigodinformation.vo.ClientExchangeAnnInfoVO;
import com.minigod.minigodinformation.vo.ClientExchangeAnnListVO;
import com.minigod.zero.core.tool.api.R;

/**
* @author dell
* @description 针对表【exchange_announcement(交易所公告表)】的数据库操作Service
* @createDate 2024-11-07 10:17:24
*/
public interface ExchangeAnnouncementService extends IService<ExchangeAnnouncement> {
	IPage<ClientExchangeAnnListVO> queryClientPageList(IPage<ClientExchangeAnnListVO> page, ClientExchangeAnnListDTO clientExchangeAnnListDTO);
	R<ClientExchangeAnnInfoVO> getClientInfo(Integer id);

}
