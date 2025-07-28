package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.entity.CashRecordBusinessTypeEntity;
import com.minigod.zero.bpm.vo.CashRecordBusinessTypeVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 业务查询归属 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface ICashRecordBusinessTypeService extends IService<CashRecordBusinessTypeEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cashRecordBusinessType
	 * @return
	 */
	IPage<CashRecordBusinessTypeVO> selectCashRecordBusinessTypePage(IPage<CashRecordBusinessTypeVO> page, CashRecordBusinessTypeVO cashRecordBusinessType);


}
