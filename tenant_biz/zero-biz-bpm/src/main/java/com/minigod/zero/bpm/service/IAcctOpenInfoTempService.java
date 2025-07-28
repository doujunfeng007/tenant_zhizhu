package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.entity.AcctOpenInfoTempEntity;
import com.minigod.zero.bpm.vo.AcctOpenInfoTempVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 开户申请-客户填写信息缓存表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface IAcctOpenInfoTempService extends IService<AcctOpenInfoTempEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctOpenInfoTemp
	 * @return
	 */
	IPage<AcctOpenInfoTempVO> selectAcctOpenInfoTempPage(IPage<AcctOpenInfoTempVO> page, AcctOpenInfoTempVO acctOpenInfoTemp);


}
