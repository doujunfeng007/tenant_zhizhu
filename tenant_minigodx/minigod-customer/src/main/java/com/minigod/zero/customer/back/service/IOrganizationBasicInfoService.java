package com.minigod.zero.customer.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.customer.bo.OrganizationInfoQueryBO;
import com.minigod.zero.customer.entity.OrganizationBasicInfo;
import com.minigod.zero.customer.vo.OrganizationBasicInfoVO;
import com.minigod.zero.customer.vo.OrganizationDetailVO;

/**
 * @ClassName: com.minigod.zero.customer.back.service.IOrganizationBasicInfoService
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/23 10:34
 * @Version: 1.0
 */
public interface IOrganizationBasicInfoService {

	OrganizationDetailVO detail(Long id);

	IPage<OrganizationBasicInfoVO> list(IPage<OrganizationBasicInfo> page, OrganizationInfoQueryBO queryBO);
}
