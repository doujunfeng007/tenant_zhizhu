package com.minigod.zero.bpmn.module.fundTrans.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.core.log.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.minigod.zero.bpmn.module.fundTrans.domain.bo.ClientFundTransQuotaBo;
import com.minigod.zero.bpmn.module.fundTrans.domain.vo.ClientFundTransQuotaVo;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransQuota;
import com.minigod.zero.bpmn.module.fundTrans.mapper.ClientFundTransQuotaMapper;
import com.minigod.zero.bpmn.module.fundTrans.service.IClientFundTransQuotaService;

import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 划拨额度Service业务层处理
 *
 * @author zsdp
 * @date 2024-12-25
 */
@RequiredArgsConstructor
@Service
public class ClientFundTransQuotaServiceImpl extends ServiceImpl<ClientFundTransQuotaMapper, ClientFundTransQuota> implements IClientFundTransQuotaService {


	/**
	 * 查询 划拨额度
	 *
	 * @param id 划拨额度主键
	 * @return 划拨额度
	 */
	@Override
	public ClientFundTransQuotaVo queryById(Long id) {
		ClientFundTransQuota quota = baseMapper.selectById(id);
		return BeanUtil.copyProperties(quota, ClientFundTransQuotaVo.class);
	}

	/**
	 * 查询 划拨额度列表
	 *
	 * @param bo 划拨额度
	 * @return 划拨额度
	 */
	@Override
	public IPage<ClientFundTransQuotaVo> queryPageList(ClientFundTransQuotaBo bo, IPage pageQuery) {
		LambdaQueryWrapper<ClientFundTransQuota> lqw = buildQueryWrapper(bo);
		IPage<ClientFundTransQuotaVo> result = baseMapper.selectPage(pageQuery, lqw);
		return result;
	}


	/**
	 * 查询 划拨额度列表
	 *
	 * @param bo 划拨额度
	 * @return 划拨额度
	 */
	@Override
	public List<ClientFundTransQuotaVo> queryList(ClientFundTransQuotaBo bo) {
		LambdaQueryWrapper<ClientFundTransQuota> lqw = buildQueryWrapper(bo);
		return baseMapper.selectList(lqw).stream().map(a -> BeanUtil.copyProperties(a, ClientFundTransQuotaVo.class)).collect(Collectors.toList());
	}

	private LambdaQueryWrapper<ClientFundTransQuota> buildQueryWrapper(ClientFundTransQuotaBo bo) {
		LambdaQueryWrapper<ClientFundTransQuota> lqw = Wrappers.lambdaQuery();
		lqw.eq(StringUtils.isNotBlank(bo.getWithdrawBusinessType()), ClientFundTransQuota::getWithdrawBusinessType, bo.getWithdrawBusinessType());
		lqw.eq(StringUtils.isNotBlank(bo.getDepositBusinessType()), ClientFundTransQuota::getDepositBusinessType, bo.getDepositBusinessType());
		lqw.eq(bo.getQuota() != null, ClientFundTransQuota::getQuota, bo.getQuota());
		lqw.eq(StringUtils.isNotBlank(bo.getCurrency()), ClientFundTransQuota::getCurrency, bo.getCurrency());
		lqw.eq(bo.getStatus() != null, ClientFundTransQuota::getStatus, bo.getStatus());
		return lqw;
	}

	/**
	 * 新增 划拨额度
	 *
	 * @param bo 划拨额度
	 * @return 结果
	 */
	@Override
	public Boolean insertByBo(ClientFundTransQuotaBo bo) {
		ClientFundTransQuota add = BeanUtil.toBean(bo, ClientFundTransQuota.class);
		validEntityBeforeSave(add);
		boolean flag = false;
		try {
			flag = baseMapper.insert(add) > 0;
		} catch (DuplicateKeyException e) {
			throw new ServiceException("已存在相同配置");
		}
		if (flag) {
			bo.setId(add.getId());
		}
		return flag;
	}

	/**
	 * 修改 划拨额度
	 *
	 * @param bo 划拨额度
	 * @return 结果
	 */
	@Override
	public Boolean updateByBo(ClientFundTransQuotaBo bo) {
		ClientFundTransQuota update = BeanUtil.toBean(bo, ClientFundTransQuota.class);
		validEntityBeforeSave(update);
		return baseMapper.updateById(update) > 0;
	}

	/**
	 * 保存前的数据校验
	 *
	 * @param entity 实体类数据
	 */
	private void validEntityBeforeSave(ClientFundTransQuota entity) {
		//TODO 做一些数据校验,如唯一约束
	}

	/**
	 * 批量删除 划拨额度
	 *
	 * @param ids 需要删除的 划拨额度主键
	 * @return 结果
	 */
	@Override
	public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
		if (isValid) {
			//TODO 做一些业务上的校验,判断是否需要校验
		}
		return baseMapper.deleteBatchIds(ids) > 0;
	}

	@Override
	public BigDecimal queryQuota(String withdrawBusinessType, String depositBusinessType, String currency) {
		LambdaQueryWrapper<ClientFundTransQuota> lqw = Wrappers.lambdaQuery();
		lqw.eq(ClientFundTransQuota::getWithdrawBusinessType, withdrawBusinessType);
		lqw.eq(ClientFundTransQuota::getDepositBusinessType, depositBusinessType);
		lqw.eq(ClientFundTransQuota::getCurrency, currency);
		lqw.eq(ClientFundTransQuota::getStatus, 1);
		lqw.eq(ClientFundTransQuota::getDelFlag, 0);
		lqw.last("limit 1");
		ClientFundTransQuota quota = baseMapper.selectOne(lqw);
		if (quota != null) {
			return quota.getQuota();
		} else {
			return null;
		}
	}
}
