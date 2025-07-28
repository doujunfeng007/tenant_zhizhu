package com.minigod.zero.bpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpm.entity.BpmCapitalInfoEntity;
import com.minigod.zero.bpm.vo.BpmCapitalInfoVO;

import java.util.List;

/**
 * 客户资金账号信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-27
 */
public interface BpmCapitalInfoMapper extends BaseMapper<BpmCapitalInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param capitalInfo
	 * @return
	 */
	List<BpmCapitalInfoVO> selectBpmCapitalInfoPage(IPage page, BpmCapitalInfoVO capitalInfo);


}
