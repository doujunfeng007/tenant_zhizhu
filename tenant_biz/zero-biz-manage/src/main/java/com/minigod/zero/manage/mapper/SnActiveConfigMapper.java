package com.minigod.zero.manage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.SnActiveConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 活动配置表Mapper接口
 *
 * @author eric
 * @date 2024-12-23 14:45:08
 */
@Mapper
public interface SnActiveConfigMapper extends BaseMapper<SnActiveConfigEntity> {

	/**
	 * 分页查询活动配置
	 *
	 * @param page 分页参数
	 * @param activeName 活动名称
	 * @param activeType 活动类型
	 * @return 分页数据
	 */
	IPage<SnActiveConfigEntity> queryPage(IPage<SnActiveConfigEntity> page,
										  @Param("activeName") String activeName,
										  @Param("activeType") Integer activeType);

	/**
	 * 查询有效的活动配置
	 *
	 * @param currentTime 当前时间
	 * @return 活动配置列表
	 */
	List<SnActiveConfigEntity> queryValidConfig(@Param("currentTime") LocalDateTime currentTime);

	/**
	 * 更新活动状态
	 *
	 * @param id 活动ID
	 * @param status 状态
	 * @return 影响行数
	 */
	int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
