package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.AdCustActInfoEntity;
import com.minigod.zero.manage.vo.AdCustActInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 广告用户活动信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public interface AdCustActInfoMapper extends BaseMapper<AdCustActInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param adCustActInfo
	 * @return
	 */
	List<AdCustActInfoVO> selectAdCustActInfoPage(IPage page, AdCustActInfoVO adCustActInfo);


}
