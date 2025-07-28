package com.minigod.zero.trade.service.options;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.entity.options.OptionsOrderInfo;
import com.minigod.zero.trade.entity.options.OptionsPositionsInfo;
import com.minigod.zero.trade.mapper.options.OptionsPositionsInfoMapper;
import com.minigod.zero.trade.vo.sjmb.EntrustBsEnum;

/**
 * <p>
 * 期权持仓信息表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2024-08-27 18:54:12
 */
@Service
public class OptionsPositionsInfoServiceImpl extends ServiceImpl<OptionsPositionsInfoMapper, OptionsPositionsInfo> implements IOptionsPositionsInfoService {


	@Autowired
	private OptionsPositionsInfoMapper optionsPositionsInfoMapper;
	@Override
	public R updatePositions(OptionsOrderInfo optionsOrderInfo) {

		String optionsCode = optionsOrderInfo.getOptionsCode();
		Long custId = optionsOrderInfo.getCustId();
		Long qty = optionsOrderInfo.getEntrustQty();
		BigDecimal price = optionsOrderInfo.getEntrustPrice();

		LambdaQueryWrapper<OptionsPositionsInfo> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(OptionsPositionsInfo::getOptionsCode, optionsCode);
		queryWrapper.eq(OptionsPositionsInfo::getCustId, custId);
		queryWrapper.eq(OptionsPositionsInfo::getIsDelete, 0);
		OptionsPositionsInfo optionsPositionsInfo =optionsPositionsInfoMapper.selectOne(queryWrapper);

		if (EntrustBsEnum.BUY.getEntrustBs().equals(optionsOrderInfo.getEntrustBs())) {
			// 买入
			if(optionsPositionsInfo ==null){
				optionsPositionsInfo =new OptionsPositionsInfo();
				optionsPositionsInfo.setOptionsAccount(optionsOrderInfo.getOptionsAccount());
				optionsPositionsInfo.setMarket("USOP");
				optionsPositionsInfo.setOptionsCode(optionsOrderInfo.getOptionsCode());
				optionsPositionsInfo.setCurrentAmount(qty);
				optionsPositionsInfo.setCostPrice(price);
				optionsPositionsInfo.setAvailableQty(qty);
				optionsPositionsInfo.setCustId(custId);
				optionsPositionsInfo.setOptionsName(optionsPositionsInfo.getOptionsAccount());
				optionsPositionsInfo.setIsDelete(0);
				optionsPositionsInfoMapper.insert(optionsPositionsInfo);
			}else{
				Long currentAmount =optionsPositionsInfo.getCurrentAmount();
				optionsPositionsInfo.setCurrentAmount(currentAmount+qty);
				//成本价
				BigDecimal costPrice = (optionsPositionsInfo.getCostPrice().multiply(new BigDecimal(currentAmount)).add(
					price.multiply(new BigDecimal(qty))
				)).divide(new BigDecimal(optionsPositionsInfo.getCurrentAmount()+qty)).setScale(4, BigDecimal.ROUND_HALF_UP);
				optionsPositionsInfo.setCostPrice(costPrice);
				optionsPositionsInfoMapper.updateById(optionsPositionsInfo);
			}

		}else{
			Long currentAmount =optionsPositionsInfo.getCurrentAmount();
			optionsPositionsInfo.setCurrentAmount(currentAmount+qty);
			if(currentAmount.equals(qty)){
				optionsPositionsInfo.setIsDelete(1);
				optionsPositionsInfo.setCostPrice(new BigDecimal(0));
				optionsPositionsInfo.setCurrentAmount(0L);
			}else{
				//成本价
				BigDecimal costPrice = (optionsPositionsInfo.getCostPrice().multiply(new BigDecimal(currentAmount)).subtract(
					price.multiply(new BigDecimal(qty))
				)).divide(new BigDecimal(optionsPositionsInfo.getCurrentAmount()+qty)).setScale(4, BigDecimal.ROUND_HALF_UP);
				optionsPositionsInfo.setCostPrice(costPrice);
				optionsPositionsInfo.setCurrentAmount(currentAmount-qty);
			}
			optionsPositionsInfoMapper.updateById(optionsPositionsInfo);
		}
		return R.success();
	}
}
