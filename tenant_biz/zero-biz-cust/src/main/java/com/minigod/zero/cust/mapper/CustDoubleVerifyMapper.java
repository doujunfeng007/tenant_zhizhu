package com.minigod.zero.cust.mapper;

import com.minigod.zero.cust.entity.CustDoubleVerifyEntity;
import com.minigod.zero.cust.vo.CustDoubleVerifyVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 二重认证信息 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-02-08
 */
public interface CustDoubleVerifyMapper extends BaseMapper<CustDoubleVerifyEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param custDoubleVerify
	 * @return
	 */
	List<CustDoubleVerifyVO> selectCustDoubleVerifyPage(IPage page, CustDoubleVerifyVO custDoubleVerify);


}
