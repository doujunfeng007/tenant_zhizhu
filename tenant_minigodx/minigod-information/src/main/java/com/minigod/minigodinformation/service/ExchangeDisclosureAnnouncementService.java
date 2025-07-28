package com.minigod.minigodinformation.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.minigodinformation.dto.ClientAnnListDTO;
import com.minigod.minigodinformation.dto.ClientInfAnnListDTO;
import com.minigod.minigodinformation.entity.ExchangeDisclosureAnnouncement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.minigodinformation.vo.ClientAnnInfoVO;
import com.minigod.minigodinformation.vo.ClientAnnListVO;
import com.minigod.minigodinformation.vo.ClientAnnouncementInfoVO;
import com.minigod.minigodinformation.vo.ClientInfAnnListVO;

/**
* @author dell
* @description 针对表【exchange_disclosure_announcement(披露公告表)】的数据库操作Service
* @createDate 2024-11-07 10:17:25
*/
public interface ExchangeDisclosureAnnouncementService extends IService<ExchangeDisclosureAnnouncement> {
	IPage<ClientInfAnnListVO> getClientPageList(IPage<ClientInfAnnListVO> page, ClientInfAnnListDTO clientInfAnnListDTO);
	ClientAnnInfoVO getClientInfoById(Integer id);
	IPage<ClientAnnListVO> getClientAnnouncementPageList(IPage<ClientAnnListVO> page, ClientAnnListDTO clientAnnListDTO);
	ClientAnnouncementInfoVO queryById(Integer id);

}
