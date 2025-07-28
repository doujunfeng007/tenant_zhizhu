package com.minigod.zero.bpm.feign;

import com.minigod.zero.bpm.service.IBpmAccountInfoService;
import com.minigod.zero.bpm.service.IBpmCapitalInfoService;
import com.minigod.zero.bpm.service.IBpmSecuritiesInfoService;
import com.minigod.zero.bpm.entity.BpmAccountInfoEntity;
import com.minigod.zero.bpm.entity.BpmCapitalInfoEntity;
import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.core.tool.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
public class BpmSecuritiesInfoClient implements IBpmSecuritiesInfoClient {

	@Resource
	private IBpmSecuritiesInfoService securitiesInfoService;
	@Resource
	private IBpmCapitalInfoService iBpmCapitalInfoService;
	@Resource
	private IBpmAccountInfoService iBpmAccountInfoService;

	@Override
	public List<BpmSecuritiesInfoEntity> securitiesInfoByCustIds(List<Long> custIds) {
		return securitiesInfoService.selectBpmSecuritiesInfoByCustIds(custIds);
	}

	@Override
	public List<BpmSecuritiesInfoEntity> securitiesInfoByCustName(String custName) {
		return securitiesInfoService.selectBpmSecuritiesInfoByCustName(custName);
	}

	@Override
	public BpmSecuritiesInfoEntity securitiesInfoByCustId(Long custId) {
		return securitiesInfoService.securitiesInfoByCustId(custId);
	}

	@Override
	public List<BpmSecuritiesInfoEntity> securitiesInfoLikeCustName(String custName) {
		return securitiesInfoService.securitiesInfoLikeCustName(custName);
	}

	@Override
	public BpmSecuritiesInfoEntity securitiesInfoByPhone(String area, String phone) {
		return securitiesInfoService.securitiesInfoByPhone(area, phone);
	}

	@Override
	@Transactional
	public R securitiesInfoPlace(BpmSecuritiesInfoEntity bpmSecuritiesInfoEntity, String tradeAccount, Integer accountType) {
//		bpmSecuritiesInfoEntity.setCreateTime(new Date());
//		bpmSecuritiesInfoEntity.setUpdateTime(new Date());
//		securitiesInfoService.save(bpmSecuritiesInfoEntity);
		BpmAccountInfoEntity bpmAccountInfoEntity = new BpmAccountInfoEntity();
		bpmAccountInfoEntity.setAcctType(1);
		bpmAccountInfoEntity.setCustId(bpmSecuritiesInfoEntity.getCustId());
		bpmAccountInfoEntity.setTradeAccount(tradeAccount);
		bpmAccountInfoEntity.setIsCurrent(1);
		bpmAccountInfoEntity.setStatus("0");
		bpmAccountInfoEntity.setAuthorStatus(1);
		bpmAccountInfoEntity.setCreateTime(new Date());
		bpmAccountInfoEntity.setUpdateTime(new Date());
		bpmAccountInfoEntity.setIsDeleted(0);
		bpmAccountInfoEntity.setAuthorEmail(bpmSecuritiesInfoEntity.getEmail());
		iBpmAccountInfoService.save(bpmAccountInfoEntity);
		BpmCapitalInfoEntity bpmCapitalInfoEntity = new BpmCapitalInfoEntity();
		bpmCapitalInfoEntity.setCreateTime(new Date());
		bpmCapitalInfoEntity.setUpdateTime(new Date());
		bpmCapitalInfoEntity.setIsDeleted(0);
		bpmCapitalInfoEntity.setTradeAccount(tradeAccount);
		bpmCapitalInfoEntity.setCapitalAccount(tradeAccount);
		bpmCapitalInfoEntity.setIsCurrent(1);
		bpmCapitalInfoEntity.setStatus(1);
		bpmCapitalInfoEntity.setAccountType(accountType == 1 ? "0" : "M");
		iBpmCapitalInfoService.save(bpmCapitalInfoEntity);
		return R.success();
	}

	@Override
	public R securitiesInfoClientLevelUpdate(Integer clientLevel, Long custId) {
		int rows = securitiesInfoService.updateClientLevelByCustId(clientLevel, custId);
		if (rows > 0) {
			return R.success("更新客户:【" + custId + "】级别为：【" + clientLevel + "】成功!");
		} else {
			return R.fail("更新客户:【" + custId + "】级别为：【" + clientLevel + "】失败!");
		}
	}
}
