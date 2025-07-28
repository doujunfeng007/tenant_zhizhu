package com.minigod.zero.manage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.SnActivityUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 活动用户表Mapper接口
 *
 * @author eric
 * @date 2024-12-23 15:10:08
 */
@Mapper
public interface SnActivityUserMapper extends BaseMapper<SnActivityUserEntity> {

	/**
	 * 分页查询活动用户
	 *
	 * @param page 分页参数
	 * @param channel 注册渠道
	 * @param utmSource 注册来源
	 * @return 分页数据
	 */
	IPage<SnActivityUserEntity> queryPage(IPage<SnActivityUserEntity> page,
										  @Param("channel") String channel,
										  @Param("utmSource") String utmSource);

	/**
	 * 根据用户ID查询用户信息
	 *
	 * @param userId 用户ID
	 * @return 用户信息
	 */
	SnActivityUserEntity getByUserId(@Param("userId") Integer userId);

	/**
	 * 查询邀请人的下级用户列表
	 *
	 * @param inviteIdentify 邀请人ID
	 * @return 用户列表
	 */
	List<SnActivityUserEntity> queryInvitedUsers(@Param("inviteIdentify") Integer inviteIdentify);

	/**
	 * 更新用户信息
	 *
	 * @param entity 用户信息
	 * @return 影响行数
	 */
	int updateUserInfo(SnActivityUserEntity entity);
}
