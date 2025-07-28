package com.minigod.zero.cust.mapper;

import com.minigod.zero.cust.entity.CustIcbcaInfoEntity;
import com.minigod.zero.cust.vo.CustIcbcaInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 工银客户信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-28
 */
public interface CustIcbcaInfoMapper extends BaseMapper<CustIcbcaInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param icbcaInfo
	 * @return
	 */
	List<CustIcbcaInfoVO> selectCustIcbcaInfoPage(IPage page, CustIcbcaInfoVO icbcaInfo);


}
