package com.minigod.zero.manage.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.minigod.zero.manage.entity.SnCouponExchangeCodeDataEntity;
import com.minigod.zero.manage.entity.SnCouponExchangeCodeEntity;
import com.minigod.zero.manage.entity.SnCouponManageEntity;
import com.minigod.zero.manage.entity.SnCouponManageMatchEntity;
import com.minigod.zero.manage.service.*;
import com.minigod.zero.manage.vo.ExchangeCodeRespVO;
import com.minigod.zero.manage.vo.SnCouponManageMatchVO;
import com.minigod.zero.manage.vo.SnCouponManageVO;
import com.minigod.zero.manage.vo.request.CouponManageVO;
import com.minigod.zero.manage.vo.request.ExchangeBulkListReqVO;
import com.minigod.zero.manage.vo.request.ExchangeCodeReqVO;
import com.minigod.zero.manage.mapper.SnCouponExchangeCodeMapper;
import com.minigod.zero.manage.mapper.SnCouponManageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 优惠券管理服务实现
 *
 * @author eric
 * @since 2024-12-26 09:36:01
 */
@Slf4j
@Service
public class CouponServiceImpl implements ICouponService {
	private final SnCouponManageMapper snCouponManageMapper;
	private final SnCouponExchangeCodeMapper snCouponExchangeCodeMapper;
	private final ISnCouponExchangeCodeService snCouponExchangeCodeService;
	private final ISnCouponExchangeCodeDataService snCouponExchangeCodeDataService;
	private final ISnCouponManageService snCouponManageService;
	private final ISnCouponManageMatchService snCouponManageMatchService;

	public CouponServiceImpl(SnCouponManageMapper snCouponManageMapper,
							 SnCouponExchangeCodeMapper snCouponExchangeCodeMapper,
							 ISnCouponExchangeCodeService snCouponExchangeCodeService,
							 ISnCouponExchangeCodeDataService snCouponExchangeCodeDataService,
							 ISnCouponManageService snCouponManageService,
							 ISnCouponManageMatchService snCouponManageMatchService) {
		this.snCouponManageMapper = snCouponManageMapper;
		this.snCouponExchangeCodeMapper = snCouponExchangeCodeMapper;
		this.snCouponExchangeCodeService = snCouponExchangeCodeService;
		this.snCouponExchangeCodeDataService = snCouponExchangeCodeDataService;
		this.snCouponManageService = snCouponManageService;
		this.snCouponManageMatchService = snCouponManageMatchService;
	}

	/**
	 * 分页查询优惠券列表
	 *
	 * @param page
	 * @param exchangeCodeReqVo
	 * @return
	 */
	@Override
	public IPage<ExchangeCodeRespVO> findExchangeCodePage(IPage<ExchangeCodeRespVO> page, ExchangeCodeReqVO exchangeCodeReqVo) {
		return snCouponExchangeCodeService.findExchangeCodePage(page, exchangeCodeReqVo);
	}

	/**
	 * 查询优惠券管理列表
	 *
	 * @param page
	 * @return
	 */
	@Override
	public IPage<SnCouponManageEntity> findCouponManagePage(IPage<SnCouponManageEntity> page) {
		return snCouponManageService.findCouponManagePage(page);
	}

	/**
	 * 查询优惠券管理详情
	 *
	 * @param manageId
	 * @return
	 */
	@Override
	public SnCouponManageEntity findCouponManage(Long manageId) {
		return snCouponManageService.findCouponManage(manageId);
	}

	/**
	 * 查询优惠券兑换码列表
	 *
	 * @param manageId 优惠券管理ID
	 * @return
	 */
	@Override
	public List<SnCouponExchangeCodeEntity> findCouponExchangeCodeList(Long manageId) {
		return snCouponExchangeCodeService.findCouponExchangeCodeList(manageId);
	}

	/**
	 * 分页查询优惠券兑换码数据统计
	 *
	 * @param page
	 * @param exchangeCodeReqVo
	 * @return
	 */
	@Override
	public IPage<SnCouponExchangeCodeDataEntity> findCouponExchangeCodeDataPage(IPage<SnCouponExchangeCodeDataEntity> page, ExchangeCodeReqVO exchangeCodeReqVo) {
		return snCouponExchangeCodeDataService.findCouponExchangeCodeDataPage(page, exchangeCodeReqVo);
	}

	/**
	 * 查询优惠券兑换码数据统计列表
	 *
	 * @param exchangeCodeReqVo
	 * @return
	 */
	@Override
	public List<SnCouponExchangeCodeDataEntity> findCouponExchangeCodeDataList(ExchangeCodeReqVO exchangeCodeReqVo) {
		return snCouponExchangeCodeDataService.findCouponExchangeCodeDataList(exchangeCodeReqVo);
	}

	/**
	 * 保存优惠券管理
	 *
	 * @param couponManageVo
	 */
	@Override
	public void saveCouponManage(CouponManageVO couponManageVo) {
		if (couponManageVo.getCreateNumber() % couponManageVo.getReciveNumber() != 0) {
			return;
		}

		//如果到帐张数>1 则拆分为多条配置记录 且生成一个12位的兑换码
		boolean needBatchCreate = false;
		Integer createNumber = 0;   //每批次卡券个数
		int batchCreateCount = 1;//批量创建的次数
		if (couponManageVo.getReciveNumber() > 1) {
			needBatchCreate = true;
			createNumber = couponManageVo.getReciveNumber();
			batchCreateCount = couponManageVo.getCreateNumber() / createNumber;
		} else {
			needBatchCreate = false;
			createNumber = couponManageVo.getCreateNumber();
			batchCreateCount = 1;
		}
		for (int i = 0; i < batchCreateCount; i++) {
			SnCouponManageEntity snCouponManage = new SnCouponManageEntity();
			snCouponManage.setCardType(couponManageVo.getCardType());
			snCouponManage.setCardName(couponManageVo.getCardName());
			snCouponManage.setCardId(couponManageVo.getCardId());
			snCouponManage.setOprName(couponManageVo.getOprName());
			snCouponManage.setCreateTime(DateUtil.parse(couponManageVo.getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
			snCouponManage.setExpiredTime(DateUtil.parse(couponManageVo.getExpiredTime(), "yyyy/MM/dd HH:mm:ss").toLocalDateTime());
			snCouponManage.setCreateNumber(createNumber);
			if (needBatchCreate) {
				snCouponManage.setCode(buildExchangeCode(10));
				snCouponManage.setUseStatus(0);
			}

			snCouponManage.setUseValidityDay(couponManageVo.getUseValidityDay());
			snCouponManage.setUseType(couponManageVo.getUseType());
			snCouponManage.setRewardCondition(couponManageVo.getRewardCondition());
			snCouponManage.setAmount(couponManageVo.getAmount());

			snCouponManage.setExchangeMaxNumber(needBatchCreate ? createNumber : couponManageVo.getExchangeMaxNumber());
			snCouponManage.setRemark(couponManageVo.getRemark());
			snCouponManage.setSendMobileMsg(couponManageVo.getSendMobileMsg());
			snCouponManage.setSendMobileMsgContent(couponManageVo.getSendMobileMsgContent());
			String channelId = couponManageVo.getChannelId();
			if (couponManageVo.getChannelId().equalsIgnoreCase("不限")) {
				channelId = "-1";
			}
			snCouponManage.setChannelId(channelId);
			//保存优惠券管理
			int rows = snCouponManageMapper.insert(snCouponManage);
			if (rows > 0) {
				List<SnCouponExchangeCodeEntity> exchangeCodeList = Lists.newArrayList();
				//生成激活码
				int createNumber2 = needBatchCreate ? createNumber : couponManageVo.getCreateNumber();
				for (int j = 0; j < createNumber2; j++) {
					SnCouponExchangeCodeEntity exchangeCode = new SnCouponExchangeCodeEntity();
					exchangeCode.setManageId(snCouponManage.getId());
					exchangeCode.setCode(buildExchangeCode(1));
					Date date = new Date();
					String year = String.valueOf(DateUtil.year(date));
					String manageId = String.format("%02d", snCouponManage.getId());
					String num = String.format("%02d", j + 1);
					exchangeCode.setSerialNum(year + manageId + num);
					exchangeCode.setUseStatus(0);
					exchangeCode.setRecordStatus(0);
					exchangeCode.setExpiredTime(snCouponManage.getExpiredTime());
					exchangeCode.setCreateTime(new Date());
					exchangeCodeList.add(exchangeCode);
				}
				snCouponExchangeCodeService.batchInsert(exchangeCodeList);

				// 统计数据
				SnCouponExchangeCodeDataEntity snCouponExchangeCodeData = new SnCouponExchangeCodeDataEntity();
				snCouponExchangeCodeData.setChannelId(channelId);
				snCouponExchangeCodeData.setManageId(snCouponManage.getId());
				snCouponExchangeCodeData.setCardType(snCouponManage.getCardType());
				snCouponExchangeCodeData.setCardName(snCouponManage.getCardName());
				snCouponExchangeCodeDataService.saveCouponExchangeCodeData(snCouponExchangeCodeData);
			}
		}
	}

	/**
	 * 查询优惠券兑换码列表
	 *
	 * @param exchangeCodeReqVo
	 * @return
	 */
	@Override
	public List<ExchangeCodeRespVO> findExchangeCodeList(ExchangeCodeReqVO exchangeCodeReqVo) {
		return snCouponExchangeCodeService.findExchangeCodeList(exchangeCodeReqVo);
	}

	/**
	 * 查询兑换码匹配表维护列表
	 *
	 * @return
	 */
	@Override
	public List<SnCouponManageMatchEntity> findSnCouponManageMatchList() {
		return snCouponManageMatchService.findSnCouponManageMatchList();
	}

	/**
	 * 根据序列号查询匹配表数据
	 *
	 * @param serialNumList
	 * @return
	 */
	@Override
	public List<SnCouponManageMatchVO> findSnCouponManageMatchSerialNumData(String[] serialNumList) {
		return snCouponManageMatchService.findSnCouponManageMatchSerialNumData(serialNumList);
	}

	/**
	 * 根据兑换码查询匹配表数据
	 *
	 * @param codeList
	 * @return
	 */
	@Override
	public List<SnCouponManageMatchVO> findSnCouponManageMatchCodeData(String[] codeList) {
		return snCouponManageMatchService.findSnCouponManageMatchCodeData(codeList);
	}

	/**
	 * 分页查询优惠券兑换码批量列表
	 *
	 * @param page
	 * @param exchangeBulkListReqVo
	 * @return
	 */
	@Override
	public IPage<SnCouponManageVO> findExchangeBulkPage(IPage<SnCouponManageEntity> page, ExchangeBulkListReqVO exchangeBulkListReqVo) {
		return snCouponManageService.findExchangeBulkPage(page, exchangeBulkListReqVo);
	}

	/**
	 * 删除兑换码匹配表维护列表
	 *
	 * @param list
	 */
	@Override
	public void deleteSnCouponManageMatchList(List<SnCouponManageMatchEntity> list) {
		snCouponManageMatchService.deleteSnCouponManageMatchList(list);
	}

	/**
	 * 保存兑换码匹配表维护列表
	 *
	 * @param list
	 * @return
	 */
	@Override
	public boolean saveSnCouponManageMatchList(List<SnCouponManageMatchEntity> list) {
		return snCouponManageMatchService.saveSnCouponManageMatchList(list);
	}

	/**
	 * 更新优惠券兑换码状态
	 *
	 * @param id
	 */
	@Override
	public void updateExchangeCode(Long id) {
		snCouponExchangeCodeService.updateExchangeCode(id);
	}

	/**
	 * 更新优惠券兑换码状态延长天数
	 *
	 * @param id
	 * @param recordStatus
	 * @param days
	 */
	@Override
	public void updateExchangeCode(Long id, Integer recordStatus, Integer days) {
		snCouponExchangeCodeService.updateExchangeCode(id, recordStatus, days);
	}

	private String buildExchangeCode(int multiple) {
		String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		Random r = new Random();

		String uuid = UUID.randomUUID().toString().replace("-", "");
		StringBuffer shortBuffer = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		String code = shortBuffer.toString() + (r.nextInt(899 * multiple) + 100 * multiple);
		return code.toUpperCase();
	}
}
