package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.entity.AcctRealnameVerifyEntity;
import com.minigod.zero.bpm.vo.AcctRealnameVerifyVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 用户实名认证表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface IAcctRealnameVerifyService extends IService<AcctRealnameVerifyEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctRealnameVerify
	 * @return
	 */
	IPage<AcctRealnameVerifyVO> selectAcctRealnameVerifyPage(IPage<AcctRealnameVerifyVO> page, AcctRealnameVerifyVO acctRealnameVerify);


}
