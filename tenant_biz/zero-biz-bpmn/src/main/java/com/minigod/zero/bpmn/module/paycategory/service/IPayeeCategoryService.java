package com.minigod.zero.bpmn.module.paycategory.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.paycategory.bo.PayCategoryQueryBO;
import com.minigod.zero.bpmn.module.paycategory.bo.PayeeCategorySubmitBO;
import com.minigod.zero.bpmn.module.paycategory.bo.PayeeSequenceNoSubmitBO;
import com.minigod.zero.bpmn.module.paycategory.entity.PayeeCategoryEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.bpmn.module.paycategory.vo.PayCategoryListVO;
import com.minigod.zero.bpmn.module.paycategory.vo.PayCategoryQueryVO;
import com.minigod.zero.bpmn.module.paycategory.vo.PayeeCategoryEntityVO;
import com.minigod.zero.core.tool.api.R;

/**
* @author dell
* @description 针对表【payee_category(收款人类别管理表/线上支付记录表)】的数据库操作Service
* @createDate 2024-07-30 10:36:48
*/
public interface IPayeeCategoryService extends IService<PayeeCategoryEntity> {
	/**
	 * 收款人类别列表分页接口
	 * @param page
	 * @param queryBO
	 * @return
	 */
	IPage<PayCategoryQueryVO> selectPayCategoryPage(IPage<PayCategoryQueryVO> page, PayCategoryQueryBO queryBO);

	/**
	 * 支付详情
	 * @param id
	 * @return
	 */
	R<PayeeCategoryEntityVO> payOrderInfo(Integer id);

	/**
	 * 支付记录-h5接口
	 * @param page
	 * @return
	 */
	IPage<PayeeCategoryEntity> payList(IPage<PayeeCategoryEntity> page);

	/**
	 * 提交线上支付订单
	 * @param payeeCategorySubmitBO
	 * @return
	 */
	R submit(PayeeCategorySubmitBO payeeCategorySubmitBO);

	/**
	 * 后台支付订单
	 * @param id
	 * @return
	 */
	R payOrder(Integer id);

	R sequenceNoSubmit(PayeeSequenceNoSubmitBO payeeSequenceNoSubmitBO);
}
