package com.minigod.zero.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.trade.entity.IpoLoanInfoEntity;
import com.minigod.zero.trade.vo.IpoLoanInfoVO;

import java.util.List;

/**
 * ipo垫资记录 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
public interface IpoLoanInfoMapper extends BaseMapper<IpoLoanInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipoLoanInfo
	 * @return
	 */
	List<IpoLoanInfoVO> selectIpoLoanInfoPage(IPage page, IpoLoanInfoVO ipoLoanInfo);


}
