package com.minigod.zero.cust.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.AppServiceImpl;
import com.minigod.zero.cust.entity.CustSerialNumberEntity;
import com.minigod.zero.cust.mapper.CustSerialNumberMapper;
import com.minigod.zero.cust.service.ICustSerialNumberService;
import com.minigod.zero.cust.vo.CustSerialNumberVO;
import org.springframework.stereotype.Service;

/**
 * 序列号管理 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-28
 */
@Service
public class CustSerialNumberServiceImpl extends AppServiceImpl<CustSerialNumberMapper, CustSerialNumberEntity> implements ICustSerialNumberService {

	@Override
	public IPage<CustSerialNumberVO> selectCustSerialNumberManagePage(IPage<CustSerialNumberVO> page, CustSerialNumberVO serialNumberManage) {
		return page.setRecords(baseMapper.selectCustSerialNumberManagePage(page, serialNumberManage));
	}

	/*@Override
	@Transactional(rollbackFor = Exception.class)
	public Long getNewCustId() {
		LambdaQueryWrapper<CustSerialNumberManageEntity> custSerialNumberQueryWrapper = new LambdaQueryWrapper<>();
		custSerialNumberQueryWrapper.eq(CustSerialNumberManageEntity::getTableName, "cust_info");
		CustSerialNumberManageEntity custSerialNumber = baseMapper.selectOne(custSerialNumberQueryWrapper);
		if(custSerialNumber == null){
			throw new ZeroException("不存在cust_info表");
		}
		if(custSerialNumber.getNowNumber() < custSerialNumber.getStartNumber() || custSerialNumber.getNowNumber() > custSerialNumber.getEndNumber()){
			throw new ZeroException("超出序号配置范围");
		}
		Long custId = custSerialNumber.getNowNumber();
		custSerialNumber.setNowNumber(custSerialNumber.getNowNumber() + 1);
		baseMapper.updateById(custSerialNumber);
		return custId;
	}*/

}
