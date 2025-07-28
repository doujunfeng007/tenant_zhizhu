package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.entity.CashAccessImageEntity;
import com.minigod.zero.bpm.vo.CashAccessImageVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 存取资金图片表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface ICashAccessImageService extends IService<CashAccessImageEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param cashAccessImage
	 * @return
	 */
	IPage<CashAccessImageVO> selectCashAccessImagePage(IPage<CashAccessImageVO> page, CashAccessImageVO cashAccessImage);


}
