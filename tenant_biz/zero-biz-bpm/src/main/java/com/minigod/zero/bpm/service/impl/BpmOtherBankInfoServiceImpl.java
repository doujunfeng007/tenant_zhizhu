package com.minigod.zero.bpm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpm.entity.BpmOtherBankInfoEntity;
import com.minigod.zero.bpm.mapper.BpmOtherBankInfoMapper;
import com.minigod.zero.bpm.service.IBpmOtherBankInfoService;
import com.minigod.zero.bpm.vo.BankInfoVO;
import com.minigod.zero.bpm.vo.BpmOtherBankInfoVO;
import com.minigod.zero.bpm.vo.request.CashDepositBankQueryReq;
import com.minigod.zero.bpm.vo.request.OtherBankInfoQueryReq;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 区域入金银行列表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
public class BpmOtherBankInfoServiceImpl extends ServiceImpl<BpmOtherBankInfoMapper, BpmOtherBankInfoEntity> implements IBpmOtherBankInfoService {

	@Override
	public IPage<BpmOtherBankInfoVO> selectBpmOtherBankInfoPage(IPage<BpmOtherBankInfoVO> page, BpmOtherBankInfoVO bpmOtherBankInfo) {
		return page.setRecords(baseMapper.selectBpmOtherBankInfoPage(page, bpmOtherBankInfo));
	}

	@Override
	public List<BankInfoVO> findDepositBankList(CashDepositBankQueryReq cashDepositBankQueryReq) {
		BankInfoVO bankInfoVO = new BankInfoVO();
		bankInfoVO.setName("常用");
		LambdaQueryWrapper<BpmOtherBankInfoEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		// 热门银行
		lambdaQueryWrapper.eq(BpmOtherBankInfoEntity::getIsHot, 1);
		lambdaQueryWrapper.eq(BpmOtherBankInfoEntity::getBankType, cashDepositBankQueryReq.getBankType());
		List<BpmOtherBankInfoEntity> hotList = list(lambdaQueryWrapper);
		bankInfoVO.setItems(hotList);
		// 所有银行
		List<BankInfoVO> list = selectBankInfo(cashDepositBankQueryReq);
		list.add(0, bankInfoVO);
		return list;
	}

	@Override
	public String[] otherBankInfo(OtherBankInfoQueryReq otherBankInfoQueryReq) {
		// todo 简繁转换
		/*if (StrUtil.isNotBlank(otherBankInfoQueryReq.getBankName())) {
			otherBankInfoQueryReq.setBankName(ChineseConverterHelper.convert(otherBankInfoQueryReq.getBankName().trim(), ChineseConverterHelper.SIMPLE));
		}*/

		LambdaQueryWrapper<BpmOtherBankInfoEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(otherBankInfoQueryReq.getBankName())){
			lambdaQueryWrapper.eq(BpmOtherBankInfoEntity::getBankName, otherBankInfoQueryReq.getBankName());
		}
		if (StringUtils.isNotBlank(otherBankInfoQueryReq.getSwiftCode())) {
			lambdaQueryWrapper.eq(BpmOtherBankInfoEntity::getSwiftCode, otherBankInfoQueryReq.getSwiftCode());
		}
		List<BpmOtherBankInfoEntity> otherBankInfoEntityList = list(lambdaQueryWrapper);

		ArrayList<String> resultList = CollectionUtil.newArrayList();
		if (otherBankInfoEntityList != null) {
			for (BpmOtherBankInfoEntity e : otherBankInfoEntityList) {
				String[] bankIds = e.getBankId().split(",");
				resultList.addAll(Arrays.asList(bankIds));
			}
		}
		return resultList.toArray(new String[resultList.size()]);
	}

	@Override
	public List<BpmOtherBankInfoEntity> otherBankInfoList(OtherBankInfoQueryReq otherBankInfoQueryReq) {
		LambdaQueryWrapper<BpmOtherBankInfoEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		// 是否入金银行
		lambdaQueryWrapper.in(BpmOtherBankInfoEntity::getIsAmount, otherBankInfoQueryReq.getIsAmount());
		List<BpmOtherBankInfoEntity> otherBankInfoEntityList = list(lambdaQueryWrapper);
		return otherBankInfoEntityList;
	}

	@Override
	public List<BankInfoVO> selectBankInfo(CashDepositBankQueryReq cashDepositBankQueryReq) {
		return baseMapper.selectBankInfo(cashDepositBankQueryReq);
	}

	@Override
	public List<BpmOtherBankInfoEntity> queryByBankId(String bankId) {
		return new LambdaQueryChainWrapper<>(baseMapper)
			.like(BpmOtherBankInfoEntity::getBankId, bankId)
			.list();
	}

}
