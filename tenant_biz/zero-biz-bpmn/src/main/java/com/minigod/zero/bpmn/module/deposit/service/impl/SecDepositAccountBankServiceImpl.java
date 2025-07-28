package com.minigod.zero.bpmn.module.deposit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.bpmn.module.deposit.entity.SecDepositAccountBankEntity;
import com.minigod.zero.bpmn.module.deposit.vo.SecDepositAccountBankVO;
import com.minigod.zero.bpmn.module.deposit.mapper.SecDepositAccountBankMapper;
import com.minigod.zero.bpmn.module.deposit.service.ISecDepositAccountBankService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.core.secure.utils.AuthUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户银行卡记录 服务实现类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
@Service
public class SecDepositAccountBankServiceImpl extends ServiceImpl<SecDepositAccountBankMapper, SecDepositAccountBankEntity> implements ISecDepositAccountBankService {

	@Override
	public IPage<SecDepositAccountBankVO> selectSecDepositAccountBankPage(IPage<SecDepositAccountBankVO> page, SecDepositAccountBankVO secDepositAccountBankVO) {
		return page.setRecords(baseMapper.selectSecDepositAccountBankPage(page, secDepositAccountBankVO));
	}

	@Override
	public List<SecDepositAccountBankVO> findAccountDepositBankInfo(Integer bankCount, Integer bankType) {
		LambdaQueryWrapper<SecDepositAccountBankEntity> queryWrapper= new LambdaQueryWrapper<>();
		queryWrapper.eq(AuthUtil.getTenantId()!=null,SecDepositAccountBankEntity::getTenantId, AuthUtil.getTenantId());
		queryWrapper.eq(AuthUtil.getClientId()!=null,SecDepositAccountBankEntity::getClientId, AuthUtil.getClientId());
		queryWrapper.eq(bankType!=0,SecDepositAccountBankEntity::getBankType,bankType);
		queryWrapper.eq(bankType!=0,SecDepositAccountBankEntity::getRegStatus,Boolean.TRUE);
		queryWrapper.orderByDesc(SecDepositAccountBankEntity::getRegTime);
		queryWrapper.last(bankType!=0," limit " +bankCount);
		queryWrapper.groupBy(SecDepositAccountBankEntity::getBankName);
		return baseMapper.selectList(queryWrapper).stream().map(entity -> {
					SecDepositAccountBankVO vo = new SecDepositAccountBankVO();
					BeanUtils.copyProperties(entity,vo);
					return vo;
				}).collect(Collectors.toList());
	}


}
