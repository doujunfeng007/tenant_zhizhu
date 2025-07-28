package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.AdFrequencyInfoEntity;
import com.minigod.zero.manage.vo.AdFrequencyInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 *  广告频率表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public interface AdFrequencyInfoMapper extends BaseMapper<AdFrequencyInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param adFrequencyInfo
	 * @return
	 */
	List<AdFrequencyInfoVO> selectAdFrequencyInfoPage(IPage page, AdFrequencyInfoVO adFrequencyInfo);


}
