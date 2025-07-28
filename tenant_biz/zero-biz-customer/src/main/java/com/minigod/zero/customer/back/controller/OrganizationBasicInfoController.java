package com.minigod.zero.customer.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.back.service.IOrganizationBasicInfoService;
import com.minigod.zero.customer.bo.OrganizationInfoQueryBO;
import com.minigod.zero.customer.vo.OrganizationBasicInfoVO;
import com.minigod.zero.customer.vo.OrganizationDetailVO;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: com.minigod.zero.customer.back.controller.OrganizationBasicInfoController
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/23 10:32
 * @Version: 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/organizationInfo")
@Api(value = "机构开户控制器", tags = "机构开户表接口")
public class OrganizationBasicInfoController {
	private final IOrganizationBasicInfoService organizationBasicInfoService;

	/**
	 * 机构开户 详情
	 */
	@GetMapping("/detail")
	public R<OrganizationDetailVO> detail(Long id) {
		return R.data(organizationBasicInfoService.detail(id));
	}

	/**
	 * 机构开户 列表
	 */
	@GetMapping("/list")
	public R<IPage<OrganizationBasicInfoVO>> list(OrganizationInfoQueryBO queryBO, Query query) {
		return R.data(organizationBasicInfoService.list(Condition.getPage(query), queryBO));
	}
}
