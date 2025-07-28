package com.minigod.zero.manage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.SnCouponManageMatchEntity;
import com.minigod.zero.manage.vo.SnCouponManageMatchVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 兑换码匹配表Mapper接口
 *
 * @author eric
 * @date 2024-12-23 17:10:08
 */
@Mapper
public interface SnCouponManageMatchMapper extends BaseMapper<SnCouponManageMatchEntity> {

	/**
	 * 分页查询匹配记录
	 *
	 * @param page 分页参数
	 * @param code 激活码
	 * @param userId 用户ID
	 * @param channelId 渠道ID
	 * @return 分页数据
	 */
	IPage<SnCouponManageMatchEntity> queryPage(IPage<SnCouponManageMatchEntity> page,
											   @Param("code") String code,
											   @Param("userId") Integer userId,
											   @Param("channelId") String channelId);
	/**
	 * 根据序列号查询匹配表数据
	 *
	 * @param serialNumList
	 * @return
	 */
	List<SnCouponManageMatchVO> findSnCouponManageMatchSerialNumData(@Param("serialNumList") String[] serialNumList);

	/**
	 * 根据兑换码查询匹配表数据
	 *
	 * @param codeList
	 * @return
	 */
	List<SnCouponManageMatchVO> findSnCouponManageMatchCodeData(@Param("codeList") String[] codeList);

	/**
	 * 查询用户的匹配记录
	 *
	 * @param userId 用户ID
	 * @return 匹配记录列表
	 */
	List<SnCouponManageMatchEntity> queryByUserId(@Param("userId") Integer userId);

	/**
	 * 根据激活码查询匹配记录
	 *
	 * @param code 激活码
	 * @return 匹配记录
	 */
	SnCouponManageMatchEntity getByCode(@Param("code") String code);

	/**
	 * 更新用户开户入金信息
	 *
	 * @param entity 匹配记录实体
	 * @return 影响行数
	 */
	int updateAccountInfo(SnCouponManageMatchEntity entity);
}
