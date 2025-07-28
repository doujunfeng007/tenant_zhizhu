package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.CashRecordBusinessTypeEntity;
import com.minigod.zero.bpm.vo.CashRecordBusinessTypeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 业务查询归属 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface CashRecordBusinessTypeMapper extends BaseMapper<CashRecordBusinessTypeEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cashRecordBusinessType
	 * @return
	 */
	List<CashRecordBusinessTypeVO> selectCashRecordBusinessTypePage(IPage page, CashRecordBusinessTypeVO cashRecordBusinessType);


}
