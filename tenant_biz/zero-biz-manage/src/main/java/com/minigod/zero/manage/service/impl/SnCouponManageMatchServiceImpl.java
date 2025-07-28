package com.minigod.zero.manage.service.impl;

import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.manage.entity.SnCouponManageMatchEntity;
import com.minigod.zero.manage.mapper.SnCouponManageMatchMapper;
import com.minigod.zero.manage.service.ISnCouponManageMatchService;
import com.minigod.zero.manage.vo.SnCouponManageMatchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 兑换码匹配表维护服务接口实现
 *
 * @author eric
 * @since 2024-12-26 16:01:08
 */
@Slf4j
@Service
public class SnCouponManageMatchServiceImpl extends BaseServiceImpl<SnCouponManageMatchMapper, SnCouponManageMatchEntity> implements ISnCouponManageMatchService {
	private final SnCouponManageMatchMapper snCouponManageMatchMapper;

	public SnCouponManageMatchServiceImpl(SnCouponManageMatchMapper snCouponManageMatchMapper) {
		this.snCouponManageMatchMapper = snCouponManageMatchMapper;
	}

	/**
	 * 查询兑换码匹配表维护列表
	 *
	 * @return
	 */
	@Override
	public List<SnCouponManageMatchEntity> findSnCouponManageMatchList() {
		return this.list();
	}

	/**
	 * 删除兑换码匹配表维护列表
	 *
	 * @param list
	 */
	@Override
	public void deleteSnCouponManageMatchList(List<SnCouponManageMatchEntity> list) {
		this.deleteLogic(list.stream().map(SnCouponManageMatchEntity::getId).collect(Collectors.toList()));
	}

	/**
	 * 保存兑换码匹配表维护列表
	 *
	 * @param list
	 * @return
	 */
	@Override
	public boolean saveSnCouponManageMatchList(List<SnCouponManageMatchEntity> list) {
		boolean result = this.saveBatch(list, DEFAULT_BATCH_SIZE);
		if (result) {
			log.info("-->兑换码匹配表维护批量插入成功,成功条数:{}条!", list.size());
		} else {
			log.info("-->兑换码匹配表维护批量插入失败,失败条数:{}条!", list.size());
		}
		return result;
	}

	/**
	 * 根据序列号查询匹配表数据
	 *
	 * @param serialNumList
	 * @return
	 */
	@Override
	public List<SnCouponManageMatchVO> findSnCouponManageMatchSerialNumData(String[] serialNumList) {
		return snCouponManageMatchMapper.findSnCouponManageMatchSerialNumData(serialNumList);
	}

	/**
	 * 根据兑换码查询匹配表数据
	 *
	 * @param codeList
	 * @return
	 */
	@Override
	public List<SnCouponManageMatchVO> findSnCouponManageMatchCodeData(String[] codeList) {
		return snCouponManageMatchMapper.findSnCouponManageMatchCodeData(codeList);
	}
}
