package com.minigod.zero.cust.mapper;

import com.minigod.zero.cust.entity.CustLoginLogEntity;
import com.minigod.zero.cust.apivo.CustLoginLogVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 登陆日志表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
public interface CustLoginLogMapper extends BaseMapper<CustLoginLogEntity> {

	/**
	 * 自定义分页
	 *
	 * @param loginLog
	 * @return
	 */
	List<CustLoginLogEntity> selectCustLoginLogPage(IPage page, @Param("params") CustLoginLogVO loginLog);


}
