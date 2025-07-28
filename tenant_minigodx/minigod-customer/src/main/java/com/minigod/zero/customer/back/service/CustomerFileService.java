package com.minigod.zero.customer.back.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.entity.CustomerFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.customer.dto.StatementPageDTO;

/**
* @author dell
* @description 针对表【customer_file】的数据库操作Service
* @createDate 2024-05-31 14:57:06
*/
public interface CustomerFileService extends IService<CustomerFile> {

	R reports(StatementPageDTO statementPageDTO);
}
