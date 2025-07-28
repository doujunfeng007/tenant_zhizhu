package com.minigod.zero.bpm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpm.constant.ClientLevelEnum;
import com.minigod.zero.bpm.entity.BpmSecuritiesInfoEntity;
import com.minigod.zero.bpm.mapper.BpmSecuritiesInfoMapper;
import com.minigod.zero.bpm.service.IBpmSecuritiesInfoService;
import com.minigod.zero.bpm.vo.BpmSecuritiesInfoVO;
import com.minigod.zero.core.tool.enums.CustEnums;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 证券客户资料表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Service
public class BpmSecuritiesInfoServiceImpl extends ServiceImpl<BpmSecuritiesInfoMapper, BpmSecuritiesInfoEntity> implements IBpmSecuritiesInfoService {

	@Override
	public IPage<BpmSecuritiesInfoVO> selectBpmSecuritiesInfoPage(IPage<BpmSecuritiesInfoVO> page, BpmSecuritiesInfoVO bpmSecuritiesInfo) {
		return page.setRecords(baseMapper.selectBpmSecuritiesInfoPage(page, bpmSecuritiesInfo));
	}

	@Override
	public BpmSecuritiesInfoEntity selectBpmSecuritiesInfo(BpmSecuritiesInfoVO bpmSecuritiesInfo) {
		List<BpmSecuritiesInfoEntity> bpmSecuritiesInfoVOS = baseMapper.selectBpmSecuritiesInfo(bpmSecuritiesInfo);
		if (CollectionUtil.isEmpty(bpmSecuritiesInfoVOS)) {
			return null;
		} else {
			return bpmSecuritiesInfoVOS.get(0);
		}
	}

	@Override
	public List<BpmSecuritiesInfoEntity> selectBpmSecuritiesInfoList(BpmSecuritiesInfoVO bpmSecuritiesInfo) {
		return baseMapper.selectBpmSecuritiesInfo(bpmSecuritiesInfo);
	}

	@Override
	public List<BpmSecuritiesInfoEntity> selectBpmSecuritiesInfoByCustIds(List<Long> custIds) {
		LambdaQueryWrapper<BpmSecuritiesInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(BpmSecuritiesInfoEntity::getCustId, custIds);
		queryWrapper.groupBy(BpmSecuritiesInfoEntity::getCustId);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public List<BpmSecuritiesInfoEntity> selectBpmSecuritiesInfoByCustName(String custName) {
		LambdaQueryWrapper<BpmSecuritiesInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(BpmSecuritiesInfoEntity::getCustName, custName);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public BpmSecuritiesInfoEntity securitiesInfoByCustId(Long custId) {
		LambdaQueryWrapper<BpmSecuritiesInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(BpmSecuritiesInfoEntity::getCustId, custId);
		return baseMapper.selectOne(queryWrapper);
	}

	@Override
	public List<BpmSecuritiesInfoEntity> securitiesInfoLikeCustName(String custName) {
		LambdaQueryWrapper<BpmSecuritiesInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.like(BpmSecuritiesInfoEntity::getCustName, custName);
		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public BpmSecuritiesInfoEntity securitiesInfoByPhone(String area, String phone) {
		LambdaQueryWrapper<BpmSecuritiesInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(BpmSecuritiesInfoEntity::getPhoneArea, area);
		queryWrapper.eq(BpmSecuritiesInfoEntity::getPhoneNumber, phone);
		queryWrapper.ne(BpmSecuritiesInfoEntity::getClientStatus, CustEnums.CustStatus.CANCEL.getCode());
		return baseMapper.selectOne(queryWrapper);
	}

	/**
	 * 更新客户级别
	 * 客户级别, 包括:1-Common 2-Important 3-VIP 4-PI 5-Capital
	 *
	 * @param clientLevel
	 * @param custId
	 */
	@Override
	public int updateClientLevelByCustId(Integer clientLevel, Long custId) {
		LambdaUpdateWrapper<BpmSecuritiesInfoEntity> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(BpmSecuritiesInfoEntity::getCustId, custId);
		ClientLevelEnum clientLevelEnum = ClientLevelEnum.fromLevel(clientLevel);
		if (clientLevelEnum != null) {
			updateWrapper.set(BpmSecuritiesInfoEntity::getRoomCode, clientLevelEnum.getLevel());
			updateWrapper.set(BpmSecuritiesInfoEntity::getUpdateTime, new Date());
			return baseMapper.update(null, updateWrapper);
		} else {
			return -1;
		}
	}
}
