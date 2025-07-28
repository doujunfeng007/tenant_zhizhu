package com.minigod.zero.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.trade.entity.IpoLoanConfigEntity;
import com.minigod.zero.trade.entity.IpoLoanInfoEntity;
import com.minigod.zero.trade.mapper.IpoLoanConfigMapper;
import com.minigod.zero.trade.service.IIpoLoanConfigService;
import com.minigod.zero.trade.service.IIpoLoanInfoService;
import com.minigod.zero.trade.vo.IpoLoanConfigVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ipo垫资配置 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
@Service
public class IpoLoanConfigServiceImpl extends ServiceImpl<IpoLoanConfigMapper, IpoLoanConfigEntity> implements IIpoLoanConfigService {

	@Resource
	private IIpoLoanInfoService ipoLoanInfoService;
	@Override
	public IPage<IpoLoanConfigVO> selectIpoLoanConfigPage(IPage<IpoLoanConfigVO> page, IpoLoanConfigVO ipoLoanConfig) {
		return page.setRecords(baseMapper.selectIpoLoanConfigPage(page, ipoLoanConfig));
	}

	@Override
	public IpoLoanConfigEntity findIpoLoanConfig() {
		List<IpoLoanConfigEntity> list = new LambdaQueryChainWrapper<>(baseMapper).list();
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public IpoLoanConfigEntity updateAndGetIpoLoanConfig() {
		IpoLoanConfigEntity ipoLoanConfig = baseMapper.selectOne(new LambdaQueryWrapper<IpoLoanConfigEntity>().eq(IpoLoanConfigEntity::getStatus,1));
		if (ipoLoanConfig != null) {
			List<IpoLoanInfoEntity> todayWaitBackList = ipoLoanInfoService.findTodayWaitBackIpoLoanInfo();
			if(CollectionUtils.isNotEmpty(todayWaitBackList)){
				// 今日待回滚记录，多条的情况
				BigDecimal remainAmount = ipoLoanConfig.getRemainAmount();
				Date now = new Date();
				List<IpoLoanInfoEntity> updateList = new ArrayList<>();
				boolean updateRemainAmount = false;
				for(IpoLoanInfoEntity ipoLoanInfo: todayWaitBackList){
					if (System.currentTimeMillis() >= ipoLoanInfo.getBizTime().getTime()) {
						//今日待回滚记录的回滚时间已过,却未回滚的情况，累加金额
						remainAmount = remainAmount.add(ipoLoanInfo.getLoanAmount());
						updateList.add(ipoLoanInfo);
						updateRemainAmount=true;
					}
				}
				for(IpoLoanInfoEntity ipoLoanInfo: updateList){
					//更新待回滚记录
					ipoLoanInfo.setIsBack(1);
					ipoLoanInfo.setUpdateTime(now);
					ipoLoanInfo.setRemainAmount(remainAmount);
					ipoLoanInfoService.updateIpoLoanInfo(ipoLoanInfo);
				}
				if(updateRemainAmount){
					//更新可用垫资额度
					ipoLoanConfig.setRemainAmount(remainAmount);
					ipoLoanConfig.setUpdateTime(now);
					baseMapper.updateById(ipoLoanConfig);
				}
			}
		}
		return ipoLoanConfig;
	}

	@Override
	public void updateIpoLoanConfig(IpoLoanConfigEntity loanConfig) {
		baseMapper.updateById(loanConfig);
	}

	@Override
	public double getIpoLoanAmount() {
		double remainAmount = 0;
		IpoLoanConfigEntity ipoLoanConfig = this.updateAndGetIpoLoanConfig();
		if (ipoLoanConfig != null) {
			remainAmount = ipoLoanConfig.getRemainAmount().doubleValue();
			if (remainAmount < 0) {
				remainAmount = 0;
			}
		}
		return remainAmount;
	}
}
