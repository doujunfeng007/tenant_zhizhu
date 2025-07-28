package com.minigod.zero.customer.back.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.customer.emuns.StatementEnums;
import com.minigod.zero.customer.entity.CustomerFile;
import com.minigod.zero.customer.back.service.CustomerFileService;
import com.minigod.zero.customer.enums.StatementEnum;
import com.minigod.zero.customer.mapper.CustomerFileMapper;
import com.minigod.zero.customer.dto.StatementPageDTO;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author dell
* @description 针对表【customer_file】的数据库操作Service实现
* @createDate 2024-05-31 14:57:06
*/
@Service
public class CustomerFileServiceImpl extends ServiceImpl<CustomerFileMapper, CustomerFile>
    implements CustomerFileService{


	/**
	 * 获取报表url
	 * @param statementPageDTO
	 * @return
	 */
	@Override
	public R reports(StatementPageDTO statementPageDTO) {
		IPage<CustomerFile>	page = new Page<>(statementPageDTO.getStart(), statementPageDTO.getCount());

		LambdaQueryWrapper<CustomerFile> customerFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
		customerFileLambdaQueryWrapper.eq(CustomerFile::getCustId,statementPageDTO.getCustId());
		customerFileLambdaQueryWrapper.eq(CustomerFile::getType,statementPageDTO.getType());
		if (ObjectUtil.isEmpty(statementPageDTO.getStatus())) {
			customerFileLambdaQueryWrapper.eq(CustomerFile::getStatus, StatementEnums.FileSendStatus.SEND_SUCCESS.getCode());
		}else {
			customerFileLambdaQueryWrapper.eq(CustomerFile::getStatus,statementPageDTO.getStatus());
		}
		if (ObjectUtil.isNotEmpty(statementPageDTO.getEndDate()) && ObjectUtil.isNotEmpty(statementPageDTO.getStartDate())) {
			customerFileLambdaQueryWrapper.ge(CustomerFile::getDate, DateUtil.beginOfDay(statementPageDTO.getStartDate()));
			customerFileLambdaQueryWrapper.le(CustomerFile::getDate, DateUtil.endOfDay(statementPageDTO.getEndDate()));
		}else {
			if (statementPageDTO.getType() == StatementEnum.MONTH.getCode()) {
				customerFileLambdaQueryWrapper.eq(CustomerFile::getDate, DateUtil.beginOfMonth(new Date()));
			}else if(statementPageDTO.getType() == StatementEnum.DAY.getCode()){
				customerFileLambdaQueryWrapper.eq(CustomerFile::getDate, DateUtil.beginOfDay(new Date()));
			}
		}
		customerFileLambdaQueryWrapper.orderByDesc(CustomerFile::getDate);

		IPage<CustomerFile> result = baseMapper.selectPage(page, customerFileLambdaQueryWrapper);

		return R.data(result);
	}
}




