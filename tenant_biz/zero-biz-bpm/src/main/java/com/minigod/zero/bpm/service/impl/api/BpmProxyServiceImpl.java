package com.minigod.zero.bpm.service.impl.api;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.bpm.dto.acct.resp.SecuritiesCacheDto;
import com.minigod.zero.bpm.dto.acct.resp.YfundInfoCacheDto;
import com.minigod.zero.bpm.entity.BpmFundAcctInfoEntity;
import com.minigod.zero.bpm.mapper.BpmSecuritiesInfoMapper;
import com.minigod.zero.bpm.service.IBpmFundAcctInfoService;
import com.minigod.zero.bpm.vo.CacheAcctInfoVO;
import com.minigod.zero.bpm.service.api.IBpmProxyService;
import com.minigod.zero.core.tool.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BpmProxyServiceImpl implements IBpmProxyService {

	@Resource
	private IBpmFundAcctInfoService bpmFundAcctInfoService;
	@Resource
	private BpmSecuritiesInfoMapper bpmSecuritiesInfoMapper;

	@Override
	public R<List<YfundInfoCacheDto>> yfundInfo(List<String> fundAccounts) {
		if (fundAccounts.size() > 1000) {
			return R.fail("单次查询不能超过1000条记录");
		}
		LambdaQueryWrapper<BpmFundAcctInfoEntity> bpmFundWrapper = new LambdaQueryWrapper<>();
		bpmFundWrapper.in(BpmFundAcctInfoEntity::getFundAccount, fundAccounts);
		List<BpmFundAcctInfoEntity> bpmFundList = bpmFundAcctInfoService.getBaseMapper().selectList(bpmFundWrapper);
		if (null != bpmFundList) {
			List<YfundInfoCacheDto> fundInfoCacheList = new ArrayList<>();
			for (BpmFundAcctInfoEntity entity : bpmFundList) {
				YfundInfoCacheDto obj = new YfundInfoCacheDto();
				BeanUtil.copyProperties(entity, obj);
				fundInfoCacheList.add(obj);
			}
			return R.data(fundInfoCacheList);
		}
		return R.fail("未获取到有效数据");
	}

	@Override
	public R<SecuritiesCacheDto> getSecUserInfo(Long custId) {
		CacheAcctInfoVO custAcctInfo = bpmSecuritiesInfoMapper.selectCustAcctInfo(custId);
		if (null != custAcctInfo) {
			SecuritiesCacheDto securitiesCacheDto = new SecuritiesCacheDto();
			securitiesCacheDto.setCnName(custAcctInfo.getCustName());
			securitiesCacheDto.setEnName(custAcctInfo.getCustNameSpell());
			securitiesCacheDto.setCustId(custAcctInfo.getCustId());
			securitiesCacheDto.setIdKind(custAcctInfo.getIdKind());
			securitiesCacheDto.setIdCard(custAcctInfo.getIdCard());
			securitiesCacheDto.setTradeAccount(custAcctInfo.getTradeAccount());
			securitiesCacheDto.setCapitalAccount(custAcctInfo.getCapitalAccount());
			securitiesCacheDto.setFundAccount(custAcctInfo.getFundAccount());
			return R.data(securitiesCacheDto);
		}
		return R.fail("未获取到有效数据");
	}

}
