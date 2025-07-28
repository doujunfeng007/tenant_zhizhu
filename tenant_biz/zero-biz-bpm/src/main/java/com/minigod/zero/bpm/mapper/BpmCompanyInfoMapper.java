package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.BpmCompanyInfoEntity;
import com.minigod.zero.bpm.vo.BpmCompanyInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 公司户详细资料表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface BpmCompanyInfoMapper extends BaseMapper<BpmCompanyInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param BpmCompanyInfo
	 * @return
	 */
	List<BpmCompanyInfoVO> selectBpmCompanyInfoPage(IPage page, BpmCompanyInfoVO BpmCompanyInfo);


}
