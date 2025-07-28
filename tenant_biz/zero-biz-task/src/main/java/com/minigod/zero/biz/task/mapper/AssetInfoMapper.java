package com.minigod.zero.biz.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.mkt.entity.AssetInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 股票码表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2022-11-17
 */
public interface AssetInfoMapper extends BaseMapper<AssetInfoEntity> {
	List<AssetInfoEntity> findAssetInfoList(@Param("market") String market);

	@Select({
		"SELECT max(VERSION) as VERSION FROM stk_asset_info"
	})
	Integer findMaxVersion();

	Integer delObj(@Param("id") Long id);

	/**
	 * 删除指定股票集合
	 *
	 * @param assetIds
	 * @return
	 */
	Long batchDeleteByAssetId(List<String> assetIds);
}
