package com.minigod.zero.manage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.HqPackageInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 行情套餐信息表Mapper接口
 *
 * @author eric
 * @date 2024-12-23 15:20:08
 */
@Mapper
public interface HqPackageInfoMapper extends BaseMapper<HqPackageInfoEntity> {

	/**
	 * 分页查询套餐信息
	 *
	 * @param page 分页参数
	 * @param packageName 套餐名称
	 * @param marketId 市场ID
	 * @return 分页数据
	 */
	IPage<HqPackageInfoEntity> queryPage(IPage<HqPackageInfoEntity> page,
										 @Param("packageName") String packageName,
										 @Param("marketId") Long marketId);

	/**
	 * 根据套餐编码查询
	 *
	 * @param packageCode 套餐编码
	 * @return 套餐信息
	 */
	HqPackageInfoEntity getByPackageCode(@Param("packageCode") String packageCode);

	/**
	 * 查询有效的套餐列表
	 *
	 * @param marketId 市场ID
	 * @return 套餐列表
	 */
	List<HqPackageInfoEntity> queryValidPackages(@Param("marketId") Long marketId);

	/**
	 * 更新套餐库存
	 *
	 * @param packageId 套餐ID
	 * @param count 更新数量(正数增加，负数减少)
	 * @return 影响行数
	 */
	int updateRestNum(@Param("packageId") Long packageId, @Param("count") Integer count);
}
