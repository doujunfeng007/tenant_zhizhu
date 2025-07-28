package com.minigod.zero.bpmn.module.account.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.account.bo.OrganizationOpenInfoBo;
import com.minigod.zero.bpmn.module.account.bo.query.OrganizationOpenInfoQuery;
import com.minigod.zero.bpmn.module.account.entity.OrganizationOpenInfoEntity;
import com.minigod.zero.bpmn.module.account.enums.OrganizationOpenApproveEnum;
import com.minigod.zero.bpmn.module.account.vo.OrganizationOpenInfoVO;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.support.Kv;
import org.springframework.web.multipart.MultipartFile;

/**
 * 机构开户接口
 *
 * @author eric
 * @since 2024-05-31 14:51:05
 */
public interface IOrganizationOpenAccountOnlineService {

	/**
	 * 上传文件
	 *
	 * @param type
	 * @param file
	 * @return
	 */
	R<Kv> uploadFile(Integer type, MultipartFile file);

	/**
	 * 检测手机号是否已开户
	 *
	 * @param phoneNumber
	 * @param phoneAreaCode
	 * @return
	 */
	Boolean isOpenAccount(String phoneNumber, String phoneAreaCode);

	/**
	 * 提交机构开户资料
	 *
	 * @param organizationOpenInfoBo
	 */
	R<String> submitOrganizationOpenAccount(OrganizationOpenInfoBo organizationOpenInfoBo);

	/**
	 * 根据主键ID获取机构开户信息
	 *
	 * @param id
	 * @return
	 */
	OrganizationOpenInfoVO queryOrganizationOpenAccountById(Long id);

	/**
	 * 根据CustId获取机构开户信息
	 *
	 * @param custId
	 * @return
	 */
	OrganizationOpenInfoEntity queryOrganizationOpenAccountByCustId(Long custId);

	/**
	 * 提交机构开户审核结果
	 *
	 * @param id
	 * @param approveResult
	 * @param reason
	 * @return
	 */
	R<String> submitApproveResult(Long id, OrganizationOpenApproveEnum approveResult, String reason);


	/**
	 * 查询审核状态
	 *
	 * @return
	 */
	Integer queryApproveStatus();


	/**
	 * 机构开户信息分页查询
	 *
	 * @param page
	 * @param openInfoQuery
	 * @return
	 */
	IPage<OrganizationOpenInfoVO> selectOrganizationOpenInfoPage(IPage<OrganizationOpenInfoVO> page, OrganizationOpenInfoQuery openInfoQuery);

}
