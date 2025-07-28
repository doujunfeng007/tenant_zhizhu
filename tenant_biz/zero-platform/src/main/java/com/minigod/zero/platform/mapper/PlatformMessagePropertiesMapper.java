package com.minigod.zero.platform.mapper;


import com.minigod.zero.platform.entity.PlatformMessageProperties;
import org.apache.ibatis.annotations.Param;

/**
* @author dell
* @description 针对表【zero_message_properties(消息平台配置参数)】的数据库操作Mapper
* @createDate 2024-12-11 14:58:58
* @Entity generator.domain.ZeroMessageProperties
*/
public interface PlatformMessagePropertiesMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PlatformMessageProperties record);

    int insertSelective(PlatformMessageProperties record);

    PlatformMessageProperties selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PlatformMessageProperties record);

    int updateByPrimaryKey(PlatformMessageProperties record);

	PlatformMessageProperties messageProperties(@Param("tenantId") String tenantId,
												@Param("channelType") Integer channelType);
}
