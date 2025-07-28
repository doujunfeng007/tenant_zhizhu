package com.minigod.zero.trade.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.trade.entity.IpoSmsZqInfoEntity;
import com.minigod.zero.trade.vo.IpoSmsZqInfoVO;

import java.util.List;

/**
 * IPO已中签短信通知 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-02-02
 */
public interface IpoSmsZqInfoMapper extends BaseMapper<IpoSmsZqInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipoSmsZqInfo
	 * @return
	 */
	List<IpoSmsZqInfoVO> selectIpoSmsZqInfoPage(IPage page, IpoSmsZqInfoVO ipoSmsZqInfo);


}
