package com.minigod.zero.cust.service.impl;

import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.cust.service.ICustResetLogService;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.cust.entity.CustResetLogEntity;
import com.minigod.zero.cust.mapper.CustResetLogMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 客户密码重置日志 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Service
public class CustResetLogServiceImpl extends BaseServiceImpl<CustResetLogMapper, CustResetLogEntity> implements ICustResetLogService {

	/**
	 * 保存客户密码重置日志
	 *
	 * @param custId   用户号
	 * @param restType 0-暂不处理 1-交易密码 2-登录密码
	 */
	@Override
	public void saveLog(Long custId, Integer restType) {
		Date now = new Date();
		CustResetLogEntity resetLogEntity = new CustResetLogEntity();
		resetLogEntity.setCustId(custId);
		resetLogEntity.setRestTime(now);
		resetLogEntity.setRestType(restType);
		resetLogEntity.setCreateUser(custId);
		resetLogEntity.setCreateTime(now);
		resetLogEntity.setUpdateTime(now);
		resetLogEntity.setIsDeleted(CommonEnums.StatusEnum.NO.getCode());
		baseMapper.insert(resetLogEntity);
	}

}
