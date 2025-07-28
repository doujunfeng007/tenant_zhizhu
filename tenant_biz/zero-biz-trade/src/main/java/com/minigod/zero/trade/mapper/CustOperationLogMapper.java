package com.minigod.zero.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.trade.entity.CustOperationLogEntity;
import com.minigod.zero.trade.vo.CustOperationLogVO;

import java.util.List;

/**
 * 客户操作日志 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-07-06
 */
public interface CustOperationLogMapper extends BaseMapper<CustOperationLogEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param custOperationLog
	 * @return
	 */
	List<CustOperationLogVO> selectCustOperationLogPage(IPage page, CustOperationLogVO custOperationLog);


}
