package com.minigod.minigodinformation.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.minigodinformation.dto.ClientExchangeAnnListDTO;
import com.minigod.minigodinformation.entity.ExchangeAnnouncement;
import com.minigod.minigodinformation.enums.AnnouncementStatusEnum;
import com.minigod.minigodinformation.service.ExchangeAnnouncementService;
import com.minigod.minigodinformation.mapper.ExchangeAnnouncementMapper;
import com.minigod.minigodinformation.vo.ClientExchangeAnnInfoVO;
import com.minigod.minigodinformation.vo.ClientExchangeAnnListVO;
import com.minigod.zero.core.tool.api.R;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author dell
* @description 针对表【exchange_announcement(交易所公告表)】的数据库操作Service实现
* @createDate 2024-11-07 10:17:24
*/
@Service
public class ExchangeAnnouncementServiceImpl extends ServiceImpl<ExchangeAnnouncementMapper, ExchangeAnnouncement>
    implements ExchangeAnnouncementService {

	@Override
	public IPage<ClientExchangeAnnListVO> queryClientPageList(IPage<ClientExchangeAnnListVO> page, ClientExchangeAnnListDTO clientExchangeAnnListDTO) {
		ArrayList<Integer> statusList = new ArrayList<>();
		statusList.add(AnnouncementStatusEnum.PUBLISHED.getCode());
		clientExchangeAnnListDTO.setStatusList(statusList);
		List<ClientExchangeAnnListVO> records = baseMapper.selectAnnouncementPage(page, clientExchangeAnnListDTO);
		return page.setRecords(records);
	}

	@Override
	public R<ClientExchangeAnnInfoVO> getClientInfo(Integer id) {
		return R.data(baseMapper.selById(id));
	}
}




