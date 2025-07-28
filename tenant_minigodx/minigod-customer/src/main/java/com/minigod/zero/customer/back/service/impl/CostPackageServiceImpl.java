package com.minigod.zero.customer.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.core.tool.exception.ZeroException;
import com.minigod.zero.core.tool.utils.CollectionUtil;
import com.minigod.zero.customer.back.service.CostPackageService;
import com.minigod.zero.customer.entity.CostPackage;
import com.minigod.zero.customer.entity.CostPackageDetail;
import com.minigod.zero.customer.entity.CustomerPackage;
import com.minigod.zero.customer.enums.CurrencyType;
import com.minigod.zero.customer.enums.PackageChargeType;
import com.minigod.zero.customer.enums.PackageRoundingType;
import com.minigod.zero.customer.enums.PackageStatus;
import com.minigod.zero.customer.mapper.CostPackageDetailMapper;
import com.minigod.zero.customer.mapper.CostPackageMapper;
import com.minigod.zero.customer.mapper.CustomerPackageMapper;
import com.minigod.zero.customer.vo.CostPackageDetailVO;
import com.minigod.zero.customer.vo.CostPackageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/9/11 21:07
 * @description：
 */
@Service
public class CostPackageServiceImpl implements CostPackageService {
	@Autowired
	private CostPackageMapper costPackageMapper;

	@Autowired
	private CustomerPackageMapper customerPackageMapper;

	@Autowired
	private CostPackageDetailMapper costPackageDetailMapper;

	@Override
	public R costPackage(String number, String packageName,Integer pageIndex,Integer pageSize) {
		Query query = new Query();
		query.setSize(pageSize);
		query.setCurrent(pageIndex);
		IPage<CostPackage> page = Condition.getPage(query);
		List<CostPackage> list = costPackageMapper.queryPage(page,number,packageName);
		return R.data(page.setRecords(list));
	}

	@Override
	public R saveCostPackage(CostPackage costPackage) {
		String number = costPackage.getNumber();
		String packageName = costPackage.getPackageName();
		if (StringUtils.isEmpty(number)){
			throw new ZeroException("添加失败，套餐编号不能为空!");
		}
		if (StringUtils.isEmpty(packageName)){
			throw new ZeroException("添加失败，套餐名字不能为空!");
		}
		CostPackage oldPackage = costPackageMapper.selectByNumber(number);
		if (oldPackage != null){
			throw new ZeroException("添加失败，套餐编号已存在");
		}
		oldPackage = costPackageMapper.selectByPackageName(packageName);
		if (oldPackage != null){
			throw new ZeroException("添加失败，套餐名称号已存在");
		}
		//1是默认套餐
		if (costPackage.getIsDefault() == 1){
			CostPackage defaultPackage = costPackageMapper.getDefaultPackage();
			if (defaultPackage != null){
				throw new ZeroException("添加失败，已设置了默认套餐");
			}
		}
		costPackage.setTenantId(AuthUtil.getTenantId());
		costPackage.setCreaterId(AuthUtil.getUserId());
		costPackage.setCreaterName(AuthUtil.getUserName());
		costPackage.setCreateTime(new Date());
		costPackageMapper.insertSelective(costPackage);
		return R.success();
	}

	@Override
	public R editCostPackage(CostPackage costPackage) {
		if (costPackage.getId() == null){
			throw new ZeroException("修改失败，套餐id不能为空!");
		}
		//1是默认套餐
		if (costPackage.getIsDefault() == 1){
			CostPackage defaultPackage = costPackageMapper.getDefaultPackage();
			if (defaultPackage != null && costPackage.getId() != defaultPackage.getId()){
				throw new ZeroException("修改失败，已设置了默认套餐");
			}
		}
		costPackage.setUpdaterId(AuthUtil.getUserId());
		costPackage.setUpdaterName(AuthUtil.getUserName());
		costPackage.setUpdateTime(new Date());
		costPackageMapper.updateByPrimaryKeySelective(costPackage);
		return R.success();
	}

	@Override
	public R costPackage(Long id) {
		if (id == null){
			throw new ZeroException("查询失败，套餐id不能为空");
		}
		CostPackage costPackage = costPackageMapper.selectByPrimaryKey(id);
		if (costPackage == null){
			return R.success();
		}
		CostPackageVO costPackageVO = new CostPackageVO();
		BeanUtils.copyProperties(costPackage,costPackageVO);
		costPackageVO.setStatusName(PackageStatus.getByCode(costPackage.getStatus()));
		List<CostPackageDetail> costPackageDetails = costPackageDetailMapper.selectByPackageId(id);
		if (CollectionUtil.isEmpty(costPackageDetails)){
			return R.data(costPackageVO);
		}
		List<CostPackageDetailVO> hkdDetail = new ArrayList<>();
		List<CostPackageDetailVO> usdDetail = new ArrayList<>();
		List<CostPackageDetailVO> cnyDetail = new ArrayList<>();

		costPackageDetails.stream().forEach(detail->{
			String currency = detail.getCurrency();
			CostPackageDetailVO detailVO = new CostPackageDetailVO(detail);
			detailVO.setChargeTypeName(PackageChargeType.getByCode(detail.getChargeType()));
			detailVO.setRoundingTypeName(PackageRoundingType.getByCode(detail.getRoundingType()));

			if (CurrencyType.HKD.getCode().equals(currency)){
				hkdDetail.add(detailVO);
			}
			if (CurrencyType.USD.getCode().equals(currency)){
				usdDetail.add(detailVO);
			}
			if (CurrencyType.CNY.getCode().equals(currency)){
				cnyDetail.add(detailVO);
			}
		});
		costPackageVO.setHkdDetail(hkdDetail);
		costPackageVO.setUsdDetail(usdDetail);
		costPackageVO.setCnyDetail(cnyDetail);
		return R.data(costPackageVO);
	}

	@Override
	public R deleteCostPackageDetail(Long packageId, Long packageDetailId) {
		if (packageId == null){
			throw new ZeroException("删除失败，套餐id不能为空");
		}
		if (packageDetailId == null){
			throw new ZeroException("删除失败，套餐详情id不能为空");
		}
		List<CustomerPackage> list = customerPackageMapper.selectByPackageId(packageId);
		if (!CollectionUtil.isEmpty(list)){
			throw new ZeroException("删除失败，改套餐已有用户绑定，不允许删除");
		}
		costPackageDetailMapper.deleteByPrimaryKey(packageDetailId);
		return R.success();
	}

	@Override
	public R editCostPackageDetail(CostPackageDetail costPackageDetail) {
		if (costPackageDetail.getId() == null){
			throw new ZeroException("修改失败，套餐详情id不能为空!");
		}
		costPackageDetail.setUpdaterId(AuthUtil.getUserId());
		costPackageDetail.setUpdaterName(AuthUtil.getUserName());
		costPackageDetail.setUpdateTime(new Date());
		costPackageDetailMapper.updateByPrimaryKeySelective(costPackageDetail);
		return R.success();
	}

	@Override
	public R saveCostPackageDetail(CostPackageDetail costPackageDetail) {
		if (costPackageDetail.getPackageId() == null){
			throw new ZeroException("修改失败，套餐id不能为空!");
		}
		costPackageDetail.setCreateTime(new Date());
		costPackageDetail.setCreaterId(AuthUtil.getUserId());
		costPackageDetail.setCreaterName(AuthUtil.getUserName());
		costPackageDetailMapper.insertSelective(costPackageDetail);
		return R.success();
	}

	@Override
	public R selectByCustId(Long custId) {
		if (custId == null){
			throw new ZeroException("查询失败，custId不能为空!");
		}
		CostPackage costPackage = costPackageMapper.selectByCustId(custId);
		if (costPackage == null){
			return R.success();
		}
		CostPackageVO costPackageVO = new CostPackageVO();
		BeanUtils.copyProperties(costPackage,costPackageVO);
		costPackageVO.setStatusName(PackageStatus.getByCode(costPackage.getStatus()));
		List<CostPackageDetail> costPackageDetails = costPackageDetailMapper.selectByPackageId(costPackage.getId());
		if (CollectionUtil.isEmpty(costPackageDetails)){
			return R.data(costPackageVO);
		}
		List<CostPackageDetailVO> hkdDetail = new ArrayList<>();
		List<CostPackageDetailVO> usdDetail = new ArrayList<>();
		List<CostPackageDetailVO> cnyDetail = new ArrayList<>();

		costPackageDetails.stream().forEach(detail->{
			String currency = detail.getCurrency();
			CostPackageDetailVO detailVO = new CostPackageDetailVO(detail);
			BeanUtils.copyProperties(detail,detailVO);

			detailVO.setChargeTypeName(PackageChargeType.getByCode(detail.getChargeType()));
			detailVO.setRoundingTypeName(PackageRoundingType.getByCode(detail.getRoundingType()));

			if (CurrencyType.HKD.getCode().equals(currency)){
				hkdDetail.add(detailVO);
			}
			if (CurrencyType.USD.getCode().equals(currency)){
				usdDetail.add(detailVO);
			}
			if (CurrencyType.CNY.getCode().equals(currency)){
				cnyDetail.add(detailVO);
			}
		});
		costPackageVO.setHkdDetail(hkdDetail);
		costPackageVO.setUsdDetail(usdDetail);
		costPackageVO.setCnyDetail(cnyDetail);
		return R.data(costPackageVO);
	}

	@Override
	public R editCustomerPackage(Long custId, Long packageId) {
		CostPackage costPackage = costPackageMapper.selectByPrimaryKey(packageId);
		if (costPackage == null){
			throw new ZeroException("操作失败，套餐不存在！");
		}
		if (costPackage.getStatus() == PackageStatus.DISABLE.getCode()){
			throw new ZeroException("操作失败，套餐停止使用！");
		}
		customerPackageMapper.deleteByCustomerId(custId);

		CustomerPackage customerPackage = new CustomerPackage();
		customerPackage.setPackageId(packageId);
		customerPackage.setCustId(custId);
		customerPackage.setStatus(0);
		customerPackage.setCreateTime(new Date());
		customerPackage.setTenantId(AuthUtil.getTenantId());
		customerPackageMapper.insertSelective(customerPackage);
		return R.success();
	}

	@Override
	public R getAllPackage() {
		return R.data(costPackageMapper.getAllPackage());
	}

	@Override
	public R<CostPackage> selectByPackageNumber(String packageNumber) {
		if (StringUtils.isEmpty(packageNumber)){
			return R.fail(ResultCode.PARAM_MISS);
		}
		return R.data(costPackageMapper.selectByNumber(packageNumber));
	}
}
