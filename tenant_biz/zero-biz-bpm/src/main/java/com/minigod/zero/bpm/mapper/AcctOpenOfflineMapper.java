package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.AcctOpenOfflineEntity;
import com.minigod.zero.bpm.vo.AcctOpenOfflineVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 线下开户-BPM回调记录表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface AcctOpenOfflineMapper extends BaseMapper<AcctOpenOfflineEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctOpenOffline
	 * @return
	 */
	List<AcctOpenOfflineVO> selectAcctOpenOfflinePage(IPage page, AcctOpenOfflineVO acctOpenOffline);


}
