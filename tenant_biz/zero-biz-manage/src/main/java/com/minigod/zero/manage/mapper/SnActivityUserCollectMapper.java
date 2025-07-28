package com.minigod.zero.manage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.SnActivityUserCollectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 活动用户信息采集表Mapper接口
 *
 * @author eric
 * @date 2024-12-23 17:00:08
 */
@Mapper
public interface SnActivityUserCollectMapper extends BaseMapper<SnActivityUserCollectEntity> {

	/**
	 * 分页查询用户信息
	 *
	 * @param page 分页参数
	 * @param clientName 客户姓名
	 * @param clientPhoneNumber 手机号码
	 * @param accountOpenStatus 开户状态
	 * @param tradeStatus 交易状态
	 * @return 分页数据
	 */
	IPage<SnActivityUserCollectEntity> queryPage(IPage<SnActivityUserCollectEntity> page,
												 @Param("clientName") String clientName,
												 @Param("clientPhoneNumber") String clientPhoneNumber,
												 @Param("accountOpenStatus") String accountOpenStatus,
												 @Param("tradeStatus") Integer tradeStatus);

	/**
	 * 根据用户ID查询信息
	 *
	 * @param userId 用户ID
	 * @return 用户信息
	 */
	SnActivityUserCollectEntity getByUserId(@Param("userId") Integer userId);

	/**
	 * 更新用户开户信息
	 *
	 * @param entity 用户信息实体
	 * @return 影响行数
	 */
	int updateAccountInfo(SnActivityUserCollectEntity entity);

	/**
	 * 更新用户交易信息
	 *
	 * @param entity 用户信息实体
	 * @return 影响行数
	 */
	int updateTradeInfo(SnActivityUserCollectEntity entity);
}
