package com.minigod.zero.cust.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.cust.entity.CustLoginLogEntity;
import com.minigod.zero.cust.apivo.CustLoginLogVO;

import java.util.List;

/**
 * 登陆日志表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
public interface ICustLoginLogService extends IService<CustLoginLogEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param loginLog
	 * @return
	 */
	IPage<CustLoginLogEntity> selectCustLoginLogPage(IPage<CustLoginLogEntity> page, CustLoginLogVO loginLog);

	/**
	 * 查询不同type最新的登入登录信息记录
	 *
	 * @return
	 */
	CustLoginLogEntity findLastestCustLoginLog(Long custId, List<Integer> typeList, Integer action);
}
