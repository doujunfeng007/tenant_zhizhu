package com.minigod.zero.cust.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.cust.service.ICustOldPasswordService;
import com.minigod.zero.core.tool.utils.PBKDF2Util;
import com.minigod.zero.cust.entity.CustOldPasswordEntity;
import com.minigod.zero.cust.mapper.CustOldPasswordMapper;
import com.minigod.zero.cust.utils.RSANewUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 存量客户密码表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-05-20
 */
@Slf4j
@Service
public class CustOldPasswordServiceImpl extends ServiceImpl<CustOldPasswordMapper, CustOldPasswordEntity> implements ICustOldPasswordService {


	@Override
	public boolean checkOldLoginPwd(Long custId, String password) {
			// 根据custId查询存量客户密码表中未重新激活登录用户，存在则需进行PBKDF2校验
		List<CustOldPasswordEntity> oldCustList = baseMapper.selectList(Wrappers.<CustOldPasswordEntity>lambdaQuery()
				.eq(CustOldPasswordEntity::getCustId, custId).eq(CustOldPasswordEntity::getLoginActive, 0));
		if (CollectionUtils.isEmpty(oldCustList)) {
			return false;
		}
		for (CustOldPasswordEntity oldCust : oldCustList) {
			if (oldCust != null && oldCust.getLoginPwd() != null) {
				log.info("存量客户登录密码校验，custId: {}", custId);
				if (oldCust.getLoginPwd().equals(PBKDF2Util.calcS2(custId.intValue(), password, oldCust.getLoginSalt()))) {
					// 更新存量客户密码表该用户状态为已重新激活登录
					oldCust.setLoginActive(1);
					oldCust.setLoginActiveTime(new Date());
					baseMapper.updateById(oldCust);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void activeLoginPwd(Long custId) {
		log.warn("激活存量客户登录密码，custId: {}", custId);
		// 更新存量客户密码表该用户状态为已重新激活登录
		CustOldPasswordEntity oldCust = new CustOldPasswordEntity();
		oldCust.setLoginActive(1);
		oldCust.setLoginActiveTime(new Date());
		baseMapper.update(oldCust, Wrappers.<CustOldPasswordEntity>lambdaQuery().eq(CustOldPasswordEntity::getCustId, custId).eq(CustOldPasswordEntity::getLoginActive, 0));
	}

	@Override
	public boolean checkOldTradePwd(Long custId, String tradeAccount, String password) {
		// 根据tradeAccount查询存量客户密码表中未重新激活交易用户，存在则需进行PBKDF2校验
		LambdaQueryWrapper<CustOldPasswordEntity> queryWrapper = Wrappers.lambdaQuery();
		if (custId != null) {
			queryWrapper.ge(CustOldPasswordEntity::getCustId, custId);
		}
		queryWrapper.eq(CustOldPasswordEntity::getTradeAccount, tradeAccount).eq(CustOldPasswordEntity::getTradeActive, 0);
		List<CustOldPasswordEntity> oldCustList = baseMapper.selectList(queryWrapper);
		if (CollectionUtils.isEmpty(oldCustList)) {
			return false;
		}
		for (CustOldPasswordEntity oldCust : oldCustList) {
			if (oldCust != null && oldCust.getTradePwd() != null) {
				custId = oldCust.getCustId();
				log.info("存量客户交易密码校验，custId: {}, customerId: {}, tradeAccount: {}", custId, oldCust.getCustomerId(), tradeAccount);
				// 由于交易密码智珠没有落库维护，激活交易需放到修改或重置交易密码环节
				int encryptId = 0;
				if ((oldCust.getRelationShip() == null || oldCust.getRelationShip() != 1) && custId != null) {
					encryptId = custId.intValue();
				} else if (oldCust.getCustomerId() != null) {
					encryptId = oldCust.getCustomerId();
				}
				String pbkdf2Pwd = PBKDF2Util.calcS2(encryptId, RSANewUtil.decrypt(password), oldCust.getTradeSalt());
				if (pbkdf2Pwd.equals(oldCust.getTradePwd())) {
					return true;
				}
			}
		}
		return false;
	}

	/*public static void main(String[] args) {
		String trade = PBKDF2Util.calcS2(10001246, "123456", "739E087B3005EF78CFF7D1DBDE7348442FD44B785E1E201692D307543410C6A8");
		System.out.println(trade);

		String login = PBKDF2Util.calcS2(1450441, "aa123456", "6134A4E2A7CD96DF2AED611C9586E98E228A342A86F404B5B26B3D1798CDFA83");
		System.out.println(login);
	}*/

	@Override
	public void activeTradePwd(Long custId, String tradeAccount) {
		log.info("激活存量客户交易密码，custId:{}, tradeAccount:{}", custId, tradeAccount);
		// 更新存量客户密码表该用户状态为已重新激活交易
		CustOldPasswordEntity oldCust = new CustOldPasswordEntity();
		oldCust.setTradeActive(1);
		oldCust.setTradeActiveTime(new Date());
		if(custId != null){
			baseMapper.update(oldCust, Wrappers.<CustOldPasswordEntity>lambdaQuery()
				.eq(CustOldPasswordEntity::getCustId, custId).eq(CustOldPasswordEntity::getTradeAccount, tradeAccount).eq(CustOldPasswordEntity::getTradeActive, 0));
		}else{
			baseMapper.update(oldCust, Wrappers.<CustOldPasswordEntity>lambdaQuery()
				.eq(CustOldPasswordEntity::getTradeAccount, tradeAccount).eq(CustOldPasswordEntity::getTradeActive, 0));
		}
	}

}
