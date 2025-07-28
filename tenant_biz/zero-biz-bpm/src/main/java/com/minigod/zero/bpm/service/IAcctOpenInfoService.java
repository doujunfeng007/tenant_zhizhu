package com.minigod.zero.bpm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpm.entity.AcctOpenInfoEntity;
import com.minigod.zero.bpm.vo.AcctOpenInfoVO;

/**
 * 开户申请信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface IAcctOpenInfoService extends IService<AcctOpenInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctOpenInfo
	 * @return
	 */
	IPage<AcctOpenInfoVO> selectAcctOpenInfoPage(IPage<AcctOpenInfoVO> page, AcctOpenInfoVO acctOpenInfo);

	boolean saveOrUpdateByCustId(AcctOpenInfoEntity entity);
}
