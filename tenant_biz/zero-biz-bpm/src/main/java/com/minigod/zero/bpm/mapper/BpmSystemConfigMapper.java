package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.BpmSystemConfigEntity;
import com.minigod.zero.bpm.vo.BpmSystemConfigVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 系统配置信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
public interface BpmSystemConfigMapper extends BaseMapper<BpmSystemConfigEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param bpmSystemConfig
	 * @return
	 */
	List<BpmSystemConfigVO> selectBpmSystemConfigPage(IPage page, BpmSystemConfigVO bpmSystemConfig);

}
