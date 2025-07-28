package com.minigod.zero.bpmn.module.withdraw.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.bpmn.module.withdraw.bo.BankPayingBo;
import com.minigod.zero.bpmn.module.withdraw.entity.BankPaying;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.mapper.BankPayingMapper;
import com.minigod.zero.bpmn.module.withdraw.service.IBankPayingService;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.utils.StringUtil;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;

/**
 * 公司付款银行信息Service业务层处理
 *
 * @author chenyu
 * @date 2023-04-06
 */
@Service
@RequiredArgsConstructor
public class BankPayingServiceImpl extends BaseServiceImpl<BankPayingMapper, BankPaying> implements IBankPayingService {

	private final BankPayingMapper baseMapper;


	/**
	 * 查询公司付款银行信息
	 *
	 * @param fundType
	 * @param bankCode
	 * @param ccy
	 * @param tenantId
	 * @param transferType 拓展字段 支付方式
	 * @return
	 */
	@Override
	public BankPaying getPayingBank(String fundType, String bankCode, String ccy, String tenantId, Integer transferType) {
		BankPayingBo bo = new BankPayingBo();
		bo.setBankCode(bankCode);
		bo.setCcy(ccy);
		bo.setServiceType(fundType);
		bo.setTenantId(tenantId);
		bo.setActive(SystemCommonEnum.YesNo.YES.getIndex());
		BankPaying bankPaying = this.queryEntity(bo);
		return bankPaying;

	}

	/**
	 * 查询银行信息记录
	 *
	 * @param bo 银行信息记录
	 * @return 银行信息记录
	 */
	@Override
	public BankPaying queryEntity(BankPayingBo bo) {
		LambdaQueryWrapper<BankPaying> lqw = buildQueryWrapper(bo);
		lqw.last("limit 1");
		return baseMapper.selectOne(lqw);
	}

	/**
	 * 查询公司付款银行列表
	 */
	@Override
	public List<BankPaying> queryBankList(BankPayingBo bo) {
		LambdaQueryWrapper<BankPaying> lqw = buildQueryBankListWrapper(bo);
		return baseMapper.selectList(lqw);
	}


	/**
	 * 查询公司付款银行信息
	 *
	 * @param id 公司付款银行信息主键
	 * @return 公司付款银行信息
	 */
	@Override
	public BankPaying queryById(Long id) {
		return this.getById(id);
	}

	/**
	 * 查询公司付款银行信息列表
	 *
	 * @param query 分页参数
	 * @param bo 公司付款银行信息
	 * @return 公司付款银行信息
	 */
	@Override
	public IPage<BankPaying> queryPageList(Query query, BankPayingBo bo) {
		LambdaQueryWrapper<BankPaying> lqw = buildQueryWrapper(bo);
		IPage<BankPaying> result = this.page(Condition.getPage(query), lqw);;
		return result;
	}

	/**
	 * 查询公司付款银行信息列表
	 *
	 * @param bo 公司付款银行信息
	 * @return 公司付款银行信息
	 */
	@Override
	public List<BankPaying> queryList(BankPayingBo bo) {
		LambdaQueryWrapper<BankPaying> lqw = buildQueryWrapper(bo);
		return baseMapper.selectList(lqw);
	}

	private LambdaQueryWrapper<BankPaying> buildQueryWrapper(BankPayingBo bo) {
		LambdaQueryWrapper<BankPaying> lqw = Wrappers.lambdaQuery();
		lqw.eq(StringUtil.isNotBlank(bo.getBankCode()), BankPaying::getBankCode, bo.getBankCode());
		lqw.like(StringUtil.isNotBlank(bo.getBankName()), BankPaying::getBankName, bo.getBankName());
		lqw.like(StringUtil.isNotBlank(bo.getBankEname()), BankPaying::getBankEname, bo.getBankEname());
		lqw.like(StringUtil.isNotBlank(bo.getBankAcctName()), BankPaying::getBankAcctName, bo.getBankAcctName());
		lqw.eq(StringUtil.isNotBlank(bo.getBankAcct()), BankPaying::getBankAcct, bo.getBankAcct());
		lqw.eq(StringUtil.isNotBlank(bo.getCcy()), BankPaying::getCcy, bo.getCcy());
		lqw.eq(StringUtil.isNotBlank(bo.getBankAcctType()), BankPaying::getBankAcctType, bo.getBankAcctType());
		lqw.eq(StringUtil.isNotBlank(bo.getServiceType()), BankPaying::getServiceType, bo.getServiceType());
		lqw.eq(StringUtil.isNotBlank(bo.getGlCode()), BankPaying::getGlCode, bo.getGlCode());
		lqw.eq(bo.getActive() != null, BankPaying::getActive, bo.getActive());
		return lqw;
	}

	private LambdaQueryWrapper<BankPaying> buildQueryBankListWrapper(BankPayingBo bo) {
		QueryWrapper<BankPaying> queryWrapper = new QueryWrapper<BankPaying>();
		LambdaQueryWrapper<BankPaying> lqw = queryWrapper.select("DISTINCT bank_code, bank_name").lambda();
		lqw.eq(StringUtil.isNotBlank(bo.getBankCode()), BankPaying::getBankCode, bo.getBankCode());
		lqw.like(StringUtil.isNotBlank(bo.getBankName()), BankPaying::getBankName, bo.getBankName());
		lqw.like(StringUtil.isNotBlank(bo.getBankEname()), BankPaying::getBankEname, bo.getBankEname());
		lqw.like(StringUtil.isNotBlank(bo.getBankAcctName()), BankPaying::getBankAcctName, bo.getBankAcctName());
		lqw.eq(StringUtil.isNotBlank(bo.getBankAcct()), BankPaying::getBankAcct, bo.getBankAcct());
		lqw.eq(StringUtil.isNotBlank(bo.getCcy()), BankPaying::getCcy, bo.getCcy());
		lqw.eq(StringUtil.isNotBlank(bo.getBankAcctType()), BankPaying::getBankAcctType, bo.getBankAcctType());
		lqw.eq(StringUtil.isNotBlank(bo.getServiceType()), BankPaying::getServiceType, bo.getServiceType());
		lqw.eq(StringUtil.isNotBlank(bo.getGlCode()), BankPaying::getGlCode, bo.getGlCode());
		lqw.eq(bo.getActive() != null, BankPaying::getActive, bo.getActive());
		lqw.eq(StringUtil.isNotBlank(bo.getTenantId()), BankPaying::getTenantId, bo.getTenantId());
		return lqw;
	}

	/**
	 * 新增公司付款银行信息
	 *
	 * @param bo 公司付款银行信息
	 * @return 结果
	 */
	@Override
	public Boolean insertByBo(BankPayingBo bo) {
		BankPaying bankPaying = BeanUtil.toBean(bo, BankPaying.class);
		boolean flag = this.save(bankPaying);
		if (flag) {
			bo.setId(bankPaying.getId());
		}
		return flag;
	}

	/**
	 * 修改公司付款银行信息
	 *
	 * @param bo 公司付款银行信息
	 * @return 结果
	 */
	@Override
	public Boolean updateUserBo(BankPayingBo bo) {
		BankPaying update = BeanUtil.toBean(bo, BankPaying.class);
		return this.updateById(update);
	}


	/**
	 * 批量删除公司付款银行信息
	 *
	 * @param ids 需要删除的公司付款银行信息主键
	 * @return 结果
	 */
	@Override
	public Boolean deleteWithValidByIds(List<Long> ids, Boolean isValid) {
		if (isValid) {
			//TODO 做一些业务上的校验,判断是否需要校验
		}
		return this.deleteLogic(ids);
	}
}
