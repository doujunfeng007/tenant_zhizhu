package com.minigod.zero.cust.feign;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.cust.feign.ICustBackClient;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.cust.apivo.CustLoginLogVO;
import com.minigod.zero.cust.apivo.excel.CustInfoExcel;
import com.minigod.zero.cust.apivo.req.CustInfoQueryReq;
import com.minigod.zero.cust.apivo.resp.CustInfoResp;
import com.minigod.zero.cust.apivo.resp.TenantCustInfoVO;
import com.minigod.zero.cust.entity.CustInfoEntity;
import com.minigod.zero.cust.entity.CustLoginLogEntity;
import com.minigod.zero.cust.feign.ICustBackClient;
import com.minigod.zero.cust.service.ICustInfoService;
import com.minigod.zero.cust.service.ICustLoginLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 客户信息表 后台 Feign实现类
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
@Slf4j
@ApiIgnore()
@RestController
@AllArgsConstructor
public class CustBackClient implements ICustBackClient {

	private final ICustInfoService custInfoService;

	private final ICustLoginLogService custLoginLogService;

	@Override
	public Page<CustInfoResp> queryPageCustInfo(CustInfoQueryReq custInfo, Query query) {
		IPage<CustInfoEntity> page = custInfoService.selectCustInfoPage(Condition.getPage(query), custInfo);
		IPage<CustInfoResp> result = page.convert(custInfoEntity -> {
			CustInfoResp vo = Objects.requireNonNull(BeanUtil.copy(custInfoEntity, CustInfoResp.class));
			return vo;
		});
		return new Page<CustInfoResp>(result.getCurrent(), result.getSize(), result.getTotal()).setRecords(result.getRecords());
	}

	@Override
	public List<CustInfoExcel> exportCust(CustInfoQueryReq custInfoQueryReq) {

		LambdaQueryWrapper<CustInfoEntity> queryWrapper = Wrappers.lambdaQuery();
		if (StringUtils.isNotEmpty(custInfoQueryReq.getStartTime())) {
			queryWrapper.ge(CustInfoEntity::getCreateTime, custInfoQueryReq.getStartTime());
		}
		if (StringUtils.isNotEmpty(custInfoQueryReq.getPhoneNumber())) {
			queryWrapper.eq(CustInfoEntity::getCellPhone, custInfoQueryReq.getPhoneNumber());
		}
		if (StringUtils.isNotEmpty(custInfoQueryReq.getEndTime())) {
			queryWrapper.le(CustInfoEntity::getCreateTime, custInfoQueryReq.getEndTime());
		}
		if (null != custInfoQueryReq.getCustId()) {
			queryWrapper.eq(CustInfoEntity::getId, custInfoQueryReq.getCustId());
		}
		if (null!=custInfoQueryReq.getRegSourceType()) {
			queryWrapper.eq(CustInfoEntity::getRegSourceType, custInfoQueryReq.getRegSourceType());
		}
		if (null!=custInfoQueryReq.getRegChannel()) {
			queryWrapper.eq(CustInfoEntity::getRegChannel, custInfoQueryReq.getRegChannel());
		}
		queryWrapper.orderByDesc(CustInfoEntity::getCreateTime);
		List<CustInfoEntity> userList = new ArrayList<>();
		if (custInfoQueryReq.getCurrent() != null && custInfoQueryReq.getSize() != null) {
			userList.addAll(custInfoService.getBaseMapper().selectPage(new Page<>(custInfoQueryReq.getCurrent(), custInfoQueryReq.getSize()), queryWrapper).getRecords());
		} else {
			userList.addAll(custInfoService.getBaseMapper().selectList(queryWrapper));
		}

		userList.forEach(custInfoEntity -> {
			if (StringUtils.isNotEmpty(custInfoEntity.getCellPhone())) {
				custInfoEntity.setCellPhone(custInfoEntity.getCellPhone());
			}

		});
		List<CustInfoExcel> result = BeanUtil.copy(userList, CustInfoExcel.class);
		return result;
	}

	@Override
	public Page<CustLoginLogVO> getPageLoginLog(CustLoginLogVO loginLog, Query query) {
		if (StringUtils.isNotEmpty(loginLog.getCreateTimeEnd())) {
			loginLog.setCreateTimeEnd(DateUtil.endOfDay(DateUtil.parseDateTime(loginLog.getCreateTimeEnd())).toStringDefaultTimeZone());
		}
		if (StringUtils.isNotEmpty(loginLog.getCreateTimeStart())) {
			loginLog.setCreateTimeStart(DateUtil.beginOfDay(DateUtil.parseDateTime(loginLog.getCreateTimeStart())).toStringDefaultTimeZone());
		}
		IPage<CustLoginLogEntity> page = custLoginLogService.selectCustLoginLogPage(Condition.getPage(query), loginLog);
		IPage<CustLoginLogVO> result = page.convert(custLoginLogEntity -> {
			CustLoginLogVO vo = Objects.requireNonNull(BeanUtil.copy(custLoginLogEntity, CustLoginLogVO.class));
			return vo;
		});
		return new Page<CustLoginLogVO>(result.getCurrent(), result.getSize(), result.getTotal()).setRecords(result.getRecords());
	}

	@Override
	public TenantCustInfoVO queryTenantCustInfo() {
		TenantCustInfoVO tenantCustInfoVO =new TenantCustInfoVO();
		long assignedCount =custInfoService.getBaseMapper().selectCount(null);
		tenantCustInfoVO.setAssignedCount(assignedCount);
		return tenantCustInfoVO;
	}

}
