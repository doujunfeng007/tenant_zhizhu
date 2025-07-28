package com.minigod.zero.bpmn.module.stocktransfer.feign;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.bpmn.module.stocktransfer.service.ISecSharesInfoService;
import com.minigod.zero.bpmn.module.stocktransfer.service.ISecTransferredStockService;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.bpmn.module.stocktransfer.dto.req.CashTransferredStockDto;
import com.minigod.zero.bpmn.module.stocktransfer.dto.req.StockTransferDto;
import com.minigod.zero.bpmn.module.stocktransfer.dto.resp.CashSharesRespDto;
import com.minigod.zero.bpmn.module.stocktransfer.dto.resp.StockTransferRespDto;
import com.minigod.zero.bpmn.module.stocktransfer.entity.SecSharesInfoEntity;
import com.minigod.zero.bpmn.module.stocktransfer.entity.SecTransferredStockEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chen
 * @ClassName StockTransferClient.java
 * @Description TODO
 * @createTime 2024年03月06日 18:18:00
 */
@Slf4j
@NonDS
@RestController
@RequiredArgsConstructor
public class StockTransferClient implements IStockTransferClient {

	@Autowired
	private ISecSharesInfoService secSharesInfoService;

	@Autowired
	private ISecTransferredStockService secTransferredStockService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R saveStockTransfer(CashTransferredStockDto cashTransferredStockDto) {
		SecTransferredStockEntity secTransferredStockEntity = new SecTransferredStockEntity();
		secTransferredStockEntity.setSecName(cashTransferredStockDto.getCashName());
		secTransferredStockEntity.setAccountName(cashTransferredStockDto.getAccountName());
		secTransferredStockEntity.setAccountNumber(cashTransferredStockDto.getAccountNumber());
		secTransferredStockEntity.setReceiveSec(cashTransferredStockDto.getReceiveSec());
		secTransferredStockEntity.setReceiveAccount(cashTransferredStockDto.getReceiveAccount());
		secTransferredStockEntity.setInviter(cashTransferredStockDto.getInviter());
		secTransferredStockEntity.setCustId(cashTransferredStockDto.getCustId());
		secTransferredStockEntity.setCcass(cashTransferredStockDto.getCcass());
		secTransferredStockEntity.setIsShares(cashTransferredStockDto.getIsShares());
		secTransferredStockEntity.setTransferState(0);
		secTransferredStockEntity.setCreateTime(new Date());
		secTransferredStockEntity.setStatus(2);
		if (null != cashTransferredStockDto.getAccImgId()) {
			secTransferredStockEntity.setAccImg(cashTransferredStockDto.getAccImgId().toString());
		}
		secTransferredStockEntity.setClientId(cashTransferredStockDto.getTradeAccount());
		secTransferredStockEntity.setRolloutContacts(cashTransferredStockDto.getRolloutContacts());
		secTransferredStockEntity.setContactsPhone(cashTransferredStockDto.getContactsPhone());
		secTransferredStockEntity.setRegulationType(cashTransferredStockDto.getTransferType());
		secTransferredStockEntity.setClearingBankName(cashTransferredStockDto.getClearingBankName());
		secTransferredStockEntity.setClearingBankAccount(cashTransferredStockDto.getClearingBankAccount());
		secTransferredStockEntity.setContactsEmail(cashTransferredStockDto.getContactsEmail());
		secTransferredStockEntity.setDtcno(cashTransferredStockDto.getDtcno());

		secTransferredStockService.getBaseMapper().insert(secTransferredStockEntity);
		cashTransferredStockDto.getSharesData().forEach((secSharesInfo -> {
			SecSharesInfoEntity secSharesInfoEntity = new SecSharesInfoEntity();
			secSharesInfoEntity.setSharesCode(secSharesInfo.getSharesCode());
			secSharesInfoEntity.setCustId(cashTransferredStockDto.getCustId());
			secSharesInfoEntity.setSharesName(secSharesInfo.getSharesName());
			secSharesInfoEntity.setSharesCode(secSharesInfo.getSharesCode());
			secSharesInfoEntity.setSharesNum(secSharesInfo.getSharesNum());
			secSharesInfoEntity.setSharesType(secSharesInfo.getSharesType());
			secSharesInfoEntity.setIsFind(secSharesInfo.getIsFind());
			secSharesInfoEntity.setStockId(secTransferredStockEntity.getId());
			secSharesInfoEntity.setCreateTime(new Date());
			secSharesInfoEntity.setStatus(1);
			secSharesInfoService.getBaseMapper().insert(secSharesInfoEntity);
		}));
		return R.success();
	}

	@Override
	public R<List<StockTransferRespDto>> findTransferredStock(StockTransferDto dto) {

		List<StockTransferRespDto> result = new ArrayList<>();
		LambdaQueryWrapper<SecTransferredStockEntity> queryWrapper = new LambdaQueryWrapper<>();

		queryWrapper.eq(SecTransferredStockEntity::getCustId, dto.getCustId());
		if (null != dto.getIsShares()) {
			queryWrapper.eq(SecTransferredStockEntity::getIsShares, dto.getIsShares());
		}
		if (null != dto.getStatus()) {
			queryWrapper.eq(SecTransferredStockEntity::getStatus, dto.getStatus());
		}
		if (null != dto.getTransferType()) {
			queryWrapper.eq(SecTransferredStockEntity::getRegulationType, dto.getTransferType());
		}
		if (null != dto.getCustId()) {
			queryWrapper.eq(SecTransferredStockEntity::getCustId, dto.getCustId());
		}
		queryWrapper.orderByDesc(SecTransferredStockEntity::getCreateTime);
		List<SecTransferredStockEntity> secTransferredStockEntities = secTransferredStockService.list(queryWrapper);
		for (SecTransferredStockEntity secTransferredStockEntity : secTransferredStockEntities) {
			StockTransferRespDto stockTransferRespDto = new StockTransferRespDto();
			BeanUtil.copy(secTransferredStockEntity, stockTransferRespDto);
			stockTransferRespDto.setTransferType(secTransferredStockEntity.getRegulationType());
			stockTransferRespDto.setCreatedTime(DateUtil.formatDateTime(secTransferredStockEntity.getCreateTime()));
			stockTransferRespDto.setCashName(secTransferredStockEntity.getSecName());
			List<SecSharesInfoEntity> secSharesInfoEntities = secSharesInfoService.selectListByTransferredStockId(secTransferredStockEntity.getId());
			List<CashSharesRespDto> sharesData = secSharesInfoEntities.stream().map(entity -> BeanUtil.copy(entity, CashSharesRespDto.class)).collect(Collectors.toList());
			stockTransferRespDto.setSharesData(sharesData);
			result.add(stockTransferRespDto);
		}
		return R.data(result);
	}
}
