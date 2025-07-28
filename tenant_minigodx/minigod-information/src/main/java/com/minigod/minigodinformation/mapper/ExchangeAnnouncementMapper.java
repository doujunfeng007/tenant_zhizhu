package com.minigod.minigodinformation.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.minigodinformation.dto.ClientExchangeAnnListDTO;
import com.minigod.minigodinformation.entity.ExchangeAnnouncement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.minigodinformation.vo.ClientExchangeAnnInfoVO;
import com.minigod.minigodinformation.vo.ClientExchangeAnnListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dell
* @description 针对表【exchange_announcement(交易所公告表)】的数据库操作Mapper
* @createDate 2024-11-07 10:17:24
* @Entity com.minigod.minigodinformation.entity.ExchangeAnnouncement
*/
public interface ExchangeAnnouncementMapper extends BaseMapper<ExchangeAnnouncement> {

	List<String> selFileById(Integer id);

	List<ClientExchangeAnnListVO> selectAnnouncementPage(@Param("page") IPage<ClientExchangeAnnListVO> page, @Param("query") ClientExchangeAnnListDTO clientExchangeAnnListDTO);

	ClientExchangeAnnInfoVO selById(Integer id);
}




