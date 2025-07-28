package com.minigod.zero.manage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.UserChannelInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 渠道信息表Mapper接口
 *
 * @author eric
 * @date 2024-12-23 15:40:08
 */
@Mapper
public interface UserChannelInfoMapper extends BaseMapper<UserChannelInfoEntity> {

	/**
	 * 分页查询渠道信息
	 *
	 * @param page 分页参数
	 * @param channelName 渠道名称
	 * @param diversMode 导流方式
	 * @return 分页数据
	 */
	IPage<UserChannelInfoEntity> queryPage(IPage<UserChannelInfoEntity> page,
										   @Param("channelName") String channelName,
										   @Param("diversMode") String diversMode);

	/**
	 * 根据渠道ID查询
	 *
	 * @param channelId 渠道ID
	 * @return 渠道信息
	 */
	UserChannelInfoEntity getByChannelId(@Param("channelId") String channelId);

	/**
	 * 查询有效的渠道列表
	 *
	 * @param accessPoint 接入点
	 * @return 渠道列表
	 */
	List<UserChannelInfoEntity> queryValidChannels(@Param("accessPoint") String accessPoint);

	/**
	 * 更新渠道状态
	 *
	 * @param channelId 渠道ID
	 * @param status 状态
	 * @return 影响行数
	 */
	int updateStatus(@Param("channelId") String channelId, @Param("status") Integer status);
}
