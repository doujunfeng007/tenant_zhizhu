package com.minigod.zero.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.trade.entity.IpoLoanConfigEntity;
import com.minigod.zero.trade.vo.IpoLoanConfigVO;

import java.util.List;

/**
 * ipo垫资配置 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-01-16
 */
public interface IpoLoanConfigMapper extends BaseMapper<IpoLoanConfigEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipoLoanConfig
	 * @return
	 */
	List<IpoLoanConfigVO> selectIpoLoanConfigPage(IPage page, IpoLoanConfigVO ipoLoanConfig);


}
