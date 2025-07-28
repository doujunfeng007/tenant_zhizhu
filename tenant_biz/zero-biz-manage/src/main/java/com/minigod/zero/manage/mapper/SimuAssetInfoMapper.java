package com.minigod.zero.manage.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.minigod.zero.manage.entity.SimuAssetInfoEntity;

@DS("simulate")
public interface SimuAssetInfoMapper {

	SimuAssetInfoEntity findAsset(String assetId);
}
