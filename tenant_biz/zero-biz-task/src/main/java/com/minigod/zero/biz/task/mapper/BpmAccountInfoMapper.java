package com.minigod.zero.biz.task.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.biz.task.vo.BpmAccountInfoVo;
import com.minigod.zero.bpm.entity.BpmAccountInfoEntity;
import org.apache.ibatis.annotations.Param;


/**
 * 交易账户信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
@DS("cust")
public interface BpmAccountInfoMapper extends BaseMapper<BpmAccountInfoEntity> {

	/**
	 * 根据clintId 查询用户信息
	 * @param clientId
	 * @return
	 */
	BpmAccountInfoVo findOneByClientId(@Param("clientId") String clientId);
}
