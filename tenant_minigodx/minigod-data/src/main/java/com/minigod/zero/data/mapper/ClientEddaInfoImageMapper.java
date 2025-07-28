package com.minigod.zero.data.mapper;

import com.minigod.zero.data.entity.ClientEddaInfoImage;

/**
* @author dell
* @description 针对表【client_edda_info_image(客户edda申请图片信息表)】的数据库操作Mapper
* @createDate 2024-09-26 16:13:17
* @Entity com.minigod.zero.report.entity.ClientEddaInfoImage
*/
public interface ClientEddaInfoImageMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ClientEddaInfoImage record);

    int insertSelective(ClientEddaInfoImage record);

    ClientEddaInfoImage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClientEddaInfoImage record);

    int updateByPrimaryKey(ClientEddaInfoImage record);

}
