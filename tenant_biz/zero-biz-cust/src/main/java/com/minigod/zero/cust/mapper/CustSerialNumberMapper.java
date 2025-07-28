package com.minigod.zero.cust.mapper;

import com.minigod.zero.cust.entity.CustSerialNumberEntity;
import com.minigod.zero.cust.vo.CustSerialNumberVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 序列号管理 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-28
 */
public interface CustSerialNumberMapper extends BaseMapper<CustSerialNumberEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param serialNumberManage
	 * @return
	 */
	List<CustSerialNumberVO> selectCustSerialNumberManagePage(IPage page, CustSerialNumberVO serialNumberManage);


}
