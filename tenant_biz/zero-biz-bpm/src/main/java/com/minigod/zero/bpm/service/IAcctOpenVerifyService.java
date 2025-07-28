package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.entity.AcctOpenVerifyEntity;
import com.minigod.zero.bpm.vo.AcctOpenVerifyVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户认证记录表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface IAcctOpenVerifyService extends IService<AcctOpenVerifyEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctOpenVerify
	 * @return
	 */
	IPage<AcctOpenVerifyVO> selectAcctOpenVerifyPage(IPage<AcctOpenVerifyVO> page, AcctOpenVerifyVO acctOpenVerify);


	boolean saveOrUpdateByCustId(AcctOpenVerifyEntity entity);


}
