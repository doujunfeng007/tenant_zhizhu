package com.minigod.zero.bpm.service;

import com.minigod.zero.bpm.entity.AcctChangeInfoTempEntity;
import com.minigod.zero.bpm.vo.AcctChangeInfoTempVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 客户证券资料修改缓存表 服务类
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface IAcctChangeInfoTempService extends IService<AcctChangeInfoTempEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctChangeInfoTemp
	 * @return
	 */
	IPage<AcctChangeInfoTempVO> selectAcctChangeInfoTempPage(IPage<AcctChangeInfoTempVO> page, AcctChangeInfoTempVO acctChangeInfoTemp);


}
