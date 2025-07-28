package com.minigod.zero.bpmn.module.account.service;

import com.minigod.zero.bpmn.module.account.bo.OpenImgBo;
import com.minigod.zero.bpmn.module.account.dto.AccountOpenInfoModifyJSonDTO;
import com.minigod.zero.bpmn.module.account.dto.AccountOpenInfoModifyApplyApproveDTO;
import com.minigod.zero.bpmn.module.account.dto.PreviewW8AgreementDTO;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoDetailVO;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoModifyDetailVO;
import com.minigod.zero.bpmn.module.account.vo.OpenImgVo;

import javax.servlet.http.HttpServletResponse;

/**
 * 修改客户开户资料门面服务类
 *
 * @author eric
 * @since 2024-08-05 16:44:05
 */
public interface IAccountOpenInfoModifyFacadeService {
	/**
	 * 获取客户开户资料详情
	 *
	 * @param userId
	 * @return
	 */
	AccountOpenInfoDetailVO getAccountOpenInfoDetail(Long userId);
	/**
	 * 提交客户开户资料修改
	 *
	 * @param accountOpenInfoModifyJSonDTO
	 * @return
	 */
	boolean submitAccountOpenInfoModify(AccountOpenInfoModifyJSonDTO accountOpenInfoModifyJSonDTO);

	/**
	  * 保存用户开户图片数据
	  *
	  * @param params
	  * @return OpenImgVo
	  */
	OpenImgVo saveAccountImg(OpenImgBo params);

	/**
	  * 根据申请记录id查询所有开户详情
	  *
	  * @param
	  * @return
	  */
	AccountOpenInfoModifyDetailVO getAccountOpenModifyDetail(String applyId);

	/**
	 * 审核
	 *
	 * @param
	 * @return
	 */
	Boolean approveByApplyId(AccountOpenInfoModifyApplyApproveDTO modifyApplyApproveDTO);
}
