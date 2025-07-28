package com.minigod.zero.bpmn.module.paycategory.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.bpmn.module.constant.EDDAMessageConstant;
import com.minigod.zero.bpmn.module.constant.PayCategoryMessageConstant;
import com.minigod.zero.bpmn.module.paycategory.bo.PayCategoryQueryBO;
import com.minigod.zero.bpmn.module.paycategory.bo.PayeeCategorySubmitBO;
import com.minigod.zero.bpmn.module.paycategory.bo.PayeeSequenceNoSubmitBO;
import com.minigod.zero.bpmn.module.paycategory.entity.PayeeCategoryEntity;
import com.minigod.zero.bpmn.module.paycategory.entity.PayeeCategoryImgEntity;
import com.minigod.zero.bpmn.module.paycategory.enums.PayeeCategoryStatusEnum;
import com.minigod.zero.bpmn.module.paycategory.mapper.PayeeCategoryImgMapper;
import com.minigod.zero.bpmn.module.paycategory.mapper.PayeeCategoryMapper;
import com.minigod.zero.bpmn.module.paycategory.service.IPayeeCategoryService;
import com.minigod.zero.bpmn.module.paycategory.vo.PayCategoryQueryVO;
import com.minigod.zero.bpmn.module.paycategory.vo.PayeeCategoryEntityVO;
import com.minigod.zero.bpmn.utils.ApplicationIdUtil;
import com.minigod.zero.core.i18n.utils.I18nUtil;
import com.minigod.zero.core.secure.ZeroUser;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.feign.IAccountClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: com.minigod.zero.bpmn.module.paycategory.service.impl.IPayeeCategoryServiceImpl
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/30 13:29
 * @Version: 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class IPayeeCategoryServiceImpl extends ServiceImpl<PayeeCategoryMapper, PayeeCategoryEntity> implements IPayeeCategoryService {
	@Resource
	private PayeeCategoryImgMapper payeeCategoryImgMapper;

	@Resource
	private IAccountClient accountClient;

	@Override
	public IPage<PayCategoryQueryVO> selectPayCategoryPage(IPage<PayCategoryQueryVO> page, PayCategoryQueryBO queryBO) {
		//默认去成功提交订单
		IPage<PayCategoryQueryVO> result = baseMapper.queryPageList(page, queryBO);
		return result;
	}

	@Override
	public R<PayeeCategoryEntityVO> payOrderInfo(Integer id) {
		PayeeCategoryEntity payeeCategoryEntity = baseMapper.selectById(id);
		PayeeCategoryEntityVO payeeCategoryEntityVO = new PayeeCategoryEntityVO();
		BeanUtils.copyProperties(payeeCategoryEntity, payeeCategoryEntityVO);
		LambdaQueryWrapper<PayeeCategoryImgEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(PayeeCategoryImgEntity::getTransactId, payeeCategoryEntity.getId());
		List<PayeeCategoryImgEntity> payeeCategoryImgEntities = payeeCategoryImgMapper.selectList(wrapper);
		payeeCategoryEntityVO.setImageList(payeeCategoryImgEntities);
		return R.data(payeeCategoryEntityVO);
	}

	/**
	 * 支付记录-h5接口
	 *
	 * @param page
	 * @return
	 */
	@Override
	public IPage<PayeeCategoryEntity> payList(IPage<PayeeCategoryEntity> page) {
		ZeroUser user = AuthUtil.getUser();

		LambdaQueryWrapper<PayeeCategoryEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(PayeeCategoryEntity::getCustId, user.getCustId());
		queryWrapper.eq(PayeeCategoryEntity::getAccountId, user.getAccount());
		queryWrapper.orderByDesc(PayeeCategoryEntity::getCreateTime);
		return baseMapper.selectPage(page, queryWrapper);

	}


	@Override
	public R submit(PayeeCategorySubmitBO payeeCategorySubmitBO) {
		String tenantId = AuthUtil.getTenantId();
		ZeroUser user = AuthUtil.getUser();
		if (payeeCategorySubmitBO.getPayWay() == PayeeCategoryStatusEnum.PayWay.HLD.getIndex()) {
			if (ObjectUtil.isEmpty(payeeCategorySubmitBO.getProductName())) {
				return R.fail(I18nUtil.getMessage(PayCategoryMessageConstant.PAY_CATEGORY_PRODUCT_NAME_CANNOT_BE_EMPTY));
			}
			if (ObjectUtil.isEmpty(payeeCategorySubmitBO.getProductCode())) {
				return R.fail(I18nUtil.getMessage(PayCategoryMessageConstant.PAY_CATEGORY_PRODUCT_CODE_CANNOT_BE_EMPTY));
			}
			if (ObjectUtil.isEmpty(payeeCategorySubmitBO.getSellingAmount())) {
				return R.fail(I18nUtil.getMessage(PayCategoryMessageConstant.PAY_CATEGORY_SELL_MONEY_CANNOT_BE_EMPTY));
			}
		}
		PayeeCategoryEntity payeeCategoryEntity = new PayeeCategoryEntity();
		BeanUtils.copyProperties(payeeCategorySubmitBO, payeeCategoryEntity);
		payeeCategoryEntity.setOrderStatus(PayeeCategoryStatusEnum.OrderStatus.UNCOMM.getIndex());
		if (payeeCategorySubmitBO.getPayWay() == PayeeCategoryStatusEnum.PayWay.CASH.getIndex()) {
			payeeCategoryEntity.setOrderStatus(PayeeCategoryStatusEnum.OrderStatus.SUBMITTED.getIndex());
			if (ObjectUtil.isEmpty(payeeCategorySubmitBO.getCash())) {
				return R.fail(I18nUtil.getMessage(PayCategoryMessageConstant.PAY_CATEGORY_AVAILABLE_CASH_CANNOT_BE_EMPTY));
			}
		}

		payeeCategoryEntity.setSequenceNo(ApplicationIdUtil.generateCommonId(tenantId));
		payeeCategoryEntity.setCustId(user.getCustId());
		payeeCategoryEntity.setAccountId(user.getAccount());


		payeeCategoryEntity.setPayStatus(PayeeCategoryStatusEnum.PayStatus.PAY_UN.getIndex());
		payeeCategoryEntity.setCreateTime(new Date());
		payeeCategoryEntity.setUpdateTime(new Date());
		int insert = baseMapper.insert(payeeCategoryEntity);
		ZeroUser finalUser = user;
		payeeCategorySubmitBO.getImageList().forEach(image -> {
			PayeeCategoryImgEntity payeeCategoryImg = new PayeeCategoryImgEntity();
			payeeCategoryImg.setTransactId(payeeCategoryEntity.getId());
			payeeCategoryImg.setImgPath(image);
			payeeCategoryImg.setCreateTime(new Date());
			payeeCategoryImg.setCustId(finalUser.getCustId());
			payeeCategoryImg.setAccountId(finalUser.getAccount());
			payeeCategoryImg.setTenantId(AuthUtil.getTenantId());
			payeeCategoryImgMapper.insert(payeeCategoryImg);
		});
		if (insert > 0) {
			return R.data(payeeCategoryEntity);
		}
		return R.fail(I18nUtil.getMessage(PayCategoryMessageConstant.PAY_CATEGORY_SUBMIT_FAIL));
	}

	@Override
	public R payOrder(Integer id) {
		PayeeCategoryEntity payeeCategoryEntity = baseMapper.selectById(id);
		if (ObjectUtil.isEmpty(payeeCategoryEntity)) {
			return R.fail(I18nUtil.getMessage(PayCategoryMessageConstant.PAY_CATEGORY_PAY_ORDER_NOT_EXIST));
		}
		if (payeeCategoryEntity.getPayStatus() == PayeeCategoryStatusEnum.PayStatus.PAY_SUCCEED.getIndex()) {
			return R.fail(I18nUtil.getMessage(PayCategoryMessageConstant.PAY_CATEGORY_ORDER_PAID));
		}
		if (payeeCategoryEntity.getPayWay() == PayeeCategoryStatusEnum.PayWay.HLD.getIndex()) {
			if (payeeCategoryEntity.getOrderStatus() == PayeeCategoryStatusEnum.OrderStatus.UNCOMM.getIndex()) {
				return R.fail(I18nUtil.getMessage(PayCategoryMessageConstant.PAY_CATEGORY_SECURITY_ORDER_USER_UNCOMMITTED));
			}
			if (payeeCategoryEntity.getPayStatus() == PayeeCategoryStatusEnum.PayStatus.PAY_UN.getIndex()) {
				R r = accountClient.selectOrderInfo(payeeCategoryEntity.getOrderSequenceNo());
				if (r.isSuccess()) {
					log.info("基金订单详情获取结果:{}", r.getData());
					JSONObject jsonObject = JSONObject.parseObject(r.getData().toString());
					Integer status = jsonObject.getInteger("status");
					//基金订单状态
					if (status != 300) {
						return R.fail(I18nUtil.getMessage(PayCategoryMessageConstant.PAY_CATEGORY_ORDER_NOT_ALL_COMMITTED));
					}
				} else {
					return R.fail(String.format(I18nUtil.getMessage(PayCategoryMessageConstant.PAY_CATEGORY_FUND_ORDER_DETAIL_GET_FAIL), r.getMsg()));
				}
			}
		}
		payeeCategoryEntity.setPayStatus(PayeeCategoryStatusEnum.PayStatus.PAY_SUCCEED.getIndex());
		payeeCategoryEntity.setUpdateTime(new Date());
		int update = baseMapper.updateById(payeeCategoryEntity);
		if (update > 0) {
			return R.success();
		}
		return R.fail(I18nUtil.getMessage(PayCategoryMessageConstant.PAY_CATEGORY_PAY_FAIL));
	}


	@Override
	public R sequenceNoSubmit(PayeeSequenceNoSubmitBO payeeSequenceNoSubmitBO) {
		PayeeCategoryEntity payeeCategoryEntity = baseMapper.selectById(payeeSequenceNoSubmitBO.getId());
		if (ObjectUtil.isEmpty(payeeCategoryEntity)) {
			return R.fail(I18nUtil.getMessage(PayCategoryMessageConstant.PAY_CATEGORY_PAY_ORDER_NOT_EXIST));
		}

		if (ObjectUtil.isNotEmpty(payeeCategoryEntity.getOrderSequenceNo())) {
			return R.fail(I18nUtil.getMessage(PayCategoryMessageConstant.PAY_CATEGORY_ORDER_HAS_SERIAL_NUMBER));
		}
		if (payeeCategoryEntity.getPayStatus() == PayeeCategoryStatusEnum.PayStatus.PAY_SUCCEED.getIndex()) {
			return R.fail(I18nUtil.getMessage(PayCategoryMessageConstant.PAY_CATEGORY_ORDER_PAID));
		}
		payeeCategoryEntity.setUpdateTime(new Date());
		payeeCategoryEntity.setOrderSequenceNo(payeeSequenceNoSubmitBO.getOrderSequenceNo());
		payeeCategoryEntity.setOrderStatus(PayeeCategoryStatusEnum.OrderStatus.SUBMITTED.getIndex());
		int i = baseMapper.updateById(payeeCategoryEntity);
		if (i > 0) {
			return R.success();
		}
		return R.fail(I18nUtil.getMessage(PayCategoryMessageConstant.PAY_CATEGORY_UPDATE_FAIL));
	}


}
