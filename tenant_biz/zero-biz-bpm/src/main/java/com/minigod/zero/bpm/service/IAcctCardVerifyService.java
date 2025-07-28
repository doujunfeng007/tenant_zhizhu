package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.entity.AcctCardVerifyEntity;
import com.minigod.zero.bpm.vo.AcctCardVerifyVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 银行卡四要素验证信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface IAcctCardVerifyService extends IService<AcctCardVerifyEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctCardVerify
	 * @return
	 */
	IPage<AcctCardVerifyVO> selectAcctCardVerifyPage(IPage<AcctCardVerifyVO> page, AcctCardVerifyVO acctCardVerify);


}
