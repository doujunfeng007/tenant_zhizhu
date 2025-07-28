package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.entity.AcctOpenImageEntity;
import com.minigod.zero.bpm.vo.AcctOpenImageVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户开户影像表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface IAcctOpenImageService extends IService<AcctOpenImageEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctOpenImage
	 * @return
	 */
	IPage<AcctOpenImageVO> selectAcctOpenImagePage(IPage<AcctOpenImageVO> page, AcctOpenImageVO acctOpenImage);


}
