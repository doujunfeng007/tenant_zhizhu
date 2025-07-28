package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.entity.AcctChangeImageEntity;
import com.minigod.zero.bpm.vo.AcctChangeImageVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户修改资料图片表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface IAcctChangeImageService extends IService<AcctChangeImageEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctChangeImage
	 * @return
	 */
	IPage<AcctChangeImageVO> selectAcctChangeImagePage(IPage<AcctChangeImageVO> page, AcctChangeImageVO acctChangeImage);


}
