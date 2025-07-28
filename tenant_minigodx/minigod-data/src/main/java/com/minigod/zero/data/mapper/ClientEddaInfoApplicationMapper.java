package com.minigod.zero.data.mapper;

import com.minigod.zero.data.entity.ClientEddaInfoApplication;

/**
* @author dell
* @description 针对表【client_edda_info_application(DBS edda授权申请表)】的数据库操作Mapper
* @createDate 2024-09-26 16:13:17
* @Entity com.minigod.zero.report.entity.ClientEddaInfoApplication
*/
public interface ClientEddaInfoApplicationMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ClientEddaInfoApplication record);

    int insertSelective(ClientEddaInfoApplication record);

    ClientEddaInfoApplication selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClientEddaInfoApplication record);

    int updateByPrimaryKey(ClientEddaInfoApplication record);

}
