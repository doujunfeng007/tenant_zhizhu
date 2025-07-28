package com.minigod.zero.bpmn.module.deposit.mapper;

import com.minigod.zero.bpmn.module.deposit.entity.SecAccImgEntity;
import com.minigod.zero.bpmn.module.deposit.vo.SecAccImgVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 存取资金图片表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
public interface SecAccImgMapper extends BaseMapper<SecAccImgEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param secAccImgVO
	 * @return
	 */
	List<SecAccImgVO> selectSecAccImgPage(IPage page, SecAccImgVO secAccImgVO);


}
