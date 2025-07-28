package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.AcctOpenScheduleTempEntity;
import com.minigod.zero.bpm.vo.AcctOpenScheduleTempVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 前端开户进度缓存表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface AcctOpenScheduleTempMapper extends BaseMapper<AcctOpenScheduleTempEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctOpenScheduleTemp
	 * @return
	 */
	List<AcctOpenScheduleTempVO> selectAcctOpenScheduleTempPage(IPage page, AcctOpenScheduleTempVO acctOpenScheduleTemp);


}
