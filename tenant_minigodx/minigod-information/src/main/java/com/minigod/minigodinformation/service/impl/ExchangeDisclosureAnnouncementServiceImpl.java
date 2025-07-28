package com.minigod.minigodinformation.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.minigodinformation.dto.ClientAnnListDTO;
import com.minigod.minigodinformation.dto.ClientInfAnnListDTO;
import com.minigod.minigodinformation.entity.ExchangeDisclosureAnnouncement;
import com.minigod.minigodinformation.mapper.ExchangeAnnouncementMapper;
import com.minigod.minigodinformation.service.ExchangeDisclosureAnnouncementService;
import com.minigod.minigodinformation.mapper.ExchangeDisclosureAnnouncementMapper;
import com.minigod.minigodinformation.vo.ClientAnnInfoVO;
import com.minigod.minigodinformation.vo.ClientAnnListVO;
import com.minigod.minigodinformation.vo.ClientAnnouncementInfoVO;
import com.minigod.minigodinformation.vo.ClientInfAnnListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author dell
* @description 针对表【exchange_disclosure_announcement(披露公告表)】的数据库操作Service实现
* @createDate 2024-11-07 10:17:25
*/
@Service
public class ExchangeDisclosureAnnouncementServiceImpl extends ServiceImpl<ExchangeDisclosureAnnouncementMapper, ExchangeDisclosureAnnouncement>
    implements ExchangeDisclosureAnnouncementService {
	@Autowired
	private ExchangeAnnouncementMapper exchangeAnnouncementMapper;
	/**
	 * 对外接口 列表查询
	 * @param page
	 * @param clientInfAnnListDTO
	 * @return
	 */
	@Override
	public IPage<ClientInfAnnListVO> getClientPageList(IPage<ClientInfAnnListVO> page, ClientInfAnnListDTO clientInfAnnListDTO) {
		/**
		 * 通知 公告
		 */
		List<ClientInfAnnListVO> pages = baseMapper.getClientPageList(page, clientInfAnnListDTO);
		for (ClientInfAnnListVO clientInfAnnListVO : pages) {
			if (clientInfAnnListVO.getAnnouncementType() == 1) {
				List<String> list = exchangeAnnouncementMapper.selFileById(clientInfAnnListVO.getId());
				clientInfAnnListVO.setFiles(list);
			} else {
				clientInfAnnListVO.setProductList(baseMapper.selProductById(clientInfAnnListVO.getId()));
				clientInfAnnListVO.setFiles(baseMapper.selFileById(clientInfAnnListVO.getId()));
			}
		}
		return page.setRecords(pages);
	}

	@Override
	public ClientAnnInfoVO getClientInfoById(Integer id) {
		ClientAnnInfoVO clientAnnInfoVO = baseMapper.getClientInfoById(id);

		return clientAnnInfoVO;
	}

	@Override
	public IPage<ClientAnnListVO> getClientAnnouncementPageList(IPage<ClientAnnListVO> page, ClientAnnListDTO clientAnnListDTO) {
		List<ClientAnnListVO> pages = baseMapper.getClientAnnouncementPageList(page, clientAnnListDTO);
		return page.setRecords(pages);
	}

	@Override
	public ClientAnnouncementInfoVO queryById(Integer id) {
		ClientAnnouncementInfoVO clientAnnouncementInfoVO = baseMapper.selById(id);

		return clientAnnouncementInfoVO;	}
}




