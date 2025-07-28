package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.AdCustRegEntity;
import com.minigod.zero.manage.vo.AdCustRegVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 广告客户记录表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public interface AdCustRegMapper extends BaseMapper<AdCustRegEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param adCustReg
	 * @return
	 */
	List<AdCustRegVO> selectAdCustRegPage(IPage page, AdCustRegVO adCustReg);


}
