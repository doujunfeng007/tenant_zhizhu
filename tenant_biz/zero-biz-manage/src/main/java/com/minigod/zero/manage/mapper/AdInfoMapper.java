package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.AdInfoEntity;
import com.minigod.zero.manage.vo.AdInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 广告信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public interface AdInfoMapper extends BaseMapper<AdInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param adInfo
	 * @return
	 */
	List<AdInfoVO> selectAdInfoPage(IPage page, @Param("adInfo") AdInfoVO adInfo);


}
