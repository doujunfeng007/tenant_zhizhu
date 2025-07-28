package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.CashAccessImageEntity;
import com.minigod.zero.bpm.vo.CashAccessImageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 存取资金图片表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface CashAccessImageMapper extends BaseMapper<CashAccessImageEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cashAccessImage
	 * @return
	 */
	List<CashAccessImageVO> selectCashAccessImagePage(IPage page, CashAccessImageVO cashAccessImage);


}
