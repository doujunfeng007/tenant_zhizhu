package com.minigod.minigodinformation.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.minigodinformation.dto.ClientAnnListDTO;
import com.minigod.minigodinformation.dto.ClientInfAnnListDTO;
import com.minigod.minigodinformation.entity.ExchangeDisclosureAnnouncement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.minigodinformation.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dell
* @description 针对表【exchange_disclosure_announcement(披露公告表)】的数据库操作Mapper
* @createDate 2024-11-07 10:17:25
* @Entity com.minigod.minigodinformation.entity.ExchangeDisclosureAnnouncement
*/
public interface ExchangeDisclosureAnnouncementMapper extends BaseMapper<ExchangeDisclosureAnnouncement> {

	List<ClientInfAnnListVO> getClientPageList(@Param("page") IPage<ClientInfAnnListVO> page, @Param("query") ClientInfAnnListDTO clientInfAnnListDTO);

	List<AnnouncementProductVO> selProductById(Integer id);

	List<String> selFileById(Integer id);

	ClientAnnInfoVO getClientInfoById(Integer id);

	List<ClientAnnListVO> getClientAnnouncementPageList(@Param("page") IPage<ClientAnnListVO> page, @Param("query") ClientAnnListDTO clientAnnListDTO);

	ClientAnnouncementInfoVO selById(Integer id);
}




