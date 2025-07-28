package com.minigod.zero.cust.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.cust.entity.IcbcBankInfoEntity;
import com.minigod.zero.cust.vo.IcbcBankInfoVO;

import java.util.List;

/**
 * 客户银行账户信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-28
 */
public interface IcbcBankInfoMapper extends BaseMapper<IcbcBankInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bankInfo
	 * @return
	 */
	List<IcbcBankInfoVO> selectIcbcBankInfoPage(IPage page, IcbcBankInfoVO bankInfo);

}
