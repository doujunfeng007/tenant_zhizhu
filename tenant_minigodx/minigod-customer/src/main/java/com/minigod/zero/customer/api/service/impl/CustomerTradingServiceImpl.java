package com.minigod.zero.customer.api.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.customer.api.service.CustomerTradingService;
import com.minigod.zero.customer.dto.CustomerBondTradingBackListDTO;
import com.minigod.zero.customer.dto.CustomerFundTradingBackListDTO;
import com.minigod.zero.customer.dto.CustomerHldTradingBackListDTO;
import com.minigod.zero.customer.mapper.*;
import com.minigod.zero.customer.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: com.minigod.zero.customer.api.service.impl.CustomerTradingServiceImpl
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/5/7 9:05
 * @Version: 1.0
 */
@Slf4j
@Service
public class CustomerTradingServiceImpl implements CustomerTradingService {
	@Autowired
	private CustomerFundTradingRecordsMapper customerFundTradingRecordsMapper;
	@Autowired
	private CustomerFundHoldingRecordsMapper customerFundHoldingRecordsMapper;
	@Autowired
	private CustomerBondTradingRecordsMapper customerBondTradingRecordsMapper;
	@Autowired
	private CustomerBondHoldingRecordsMapper customerBondHoldingRecordsMapper;
	@Autowired
	private CustomerHldTradingRecordsMapper customerHldTradingRecordsMapper;
	@Autowired
	private CustomerHldHoldingRecordsMapper customerHldHoldingRecordsMapper;

	/**
	 * 获取基金交易流水
	 * @param vo
	 * @return
	 */
	@Override
	public R fundTradingList(CustomerFundTradingRecordsListVO vo) {

		if (!Func.isNotEmpty(vo.getCurrent()) || !Func.isNotEmpty(vo.getSize())) {
			PageHelper.startPage(1, 10);
		}else {
			PageHelper.startPage(vo.getCurrent(), vo.getSize());
		}

		List<CustomerFundTradingBackListDTO> list = customerFundTradingRecordsMapper.selectTradingBackList(vo);
		PageInfo<CustomerFundTradingBackListDTO> pageInfo = new PageInfo<>(list);

		return R.data(pageInfo);
	}

	/**
	 * 基金持仓
	 * @param vo
	 * @return
	 */
	@Override
	public R fundHoldingList(CustomerFundHoldingListVO vo) {
		if (!Func.isNotEmpty(vo.getCurrent()) || !Func.isNotEmpty(vo.getSize())) {
			PageHelper.startPage(1, 10);
		}else {
			PageHelper.startPage(vo.getCurrent(), vo.getSize());
		}
		List<CustomerFundHoldingRecordsEntityListVO> list = customerFundHoldingRecordsMapper.fundHoldingList(vo);
		PageInfo<CustomerFundHoldingRecordsEntityListVO> pageInfo = new PageInfo<>(list);

		return R.data(pageInfo);
	}

	/**
	 *  债券交易流水
	 * @param vo
	 * @return
	 */
	@Override
	public R bondTradingList(CustomerBondTradingRecordsListVO vo) {
		if (!Func.isNotEmpty(vo.getCurrent()) || !Func.isNotEmpty(vo.getSize())) {
			PageHelper.startPage(1, 10);
		}else {
			PageHelper.startPage(vo.getCurrent(), vo.getSize());
		}

		List<CustomerBondTradingBackListDTO> list = customerBondTradingRecordsMapper.selectBondTradingBackList(vo);
		PageInfo<CustomerBondTradingBackListDTO> pageInfo = new PageInfo<>(list);

		return R.data(pageInfo);
	}


	/**
	 * 债券持仓
	 * @param vo
	 * @return
	 */
	@Override
	public R bondHoldingList(CustomerBondHoldingListVO vo) {
		if (!Func.isNotEmpty(vo.getCurrent()) || !Func.isNotEmpty(vo.getSize())) {
			PageHelper.startPage(1, 10);
		}else {
			PageHelper.startPage(vo.getCurrent(), vo.getSize());
		}
		List<CustomerBondHoldingRecordsListVO> list = customerBondHoldingRecordsMapper.bondHoldingList(vo);
		PageInfo<CustomerBondHoldingRecordsListVO> pageInfo = new PageInfo<>(list);

		return R.data(pageInfo);
	}

	/**
	 * 活利得持仓
	 * @param vo
	 * @return
	 */
	@Override
	public R hldHoldingList(CustomerHldHoldingListVO vo) {
		if (!Func.isNotEmpty(vo.getCurrent()) || !Func.isNotEmpty(vo.getSize())) {
			PageHelper.startPage(1, 10);
		}else {
			PageHelper.startPage(vo.getCurrent(), vo.getSize());
		}
		List<CustomerHldHoldingRecordsListVO> list = customerHldHoldingRecordsMapper.hldHoldingList(vo);
		PageInfo<CustomerHldHoldingRecordsListVO> pageInfo = new PageInfo<>(list);

		return R.data(pageInfo);

	}

	/**
	 * 活利得 交易流水
	 * @param vo
	 * @return
	 */
	@Override
	public R hldTradingList(CustomerHldTradingRecordsListVO vo) {
		if (!Func.isNotEmpty(vo.getCurrent()) || !Func.isNotEmpty(vo.getSize())) {
			PageHelper.startPage(1, 10);
		}else {
			PageHelper.startPage(vo.getCurrent(), vo.getSize());
		}

		List<CustomerHldTradingBackListDTO> list = customerHldTradingRecordsMapper.selectHldTradingBackList(vo);
		PageInfo<CustomerHldTradingBackListDTO> pageInfo = new PageInfo<>(list);

		return R.data(pageInfo);
	}

}
