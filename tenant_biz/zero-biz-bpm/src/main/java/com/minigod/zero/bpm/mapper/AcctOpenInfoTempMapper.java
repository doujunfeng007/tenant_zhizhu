package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.AcctOpenInfoTempEntity;
import com.minigod.zero.bpm.vo.AcctOpenInfoTempVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 开户申请-客户填写信息缓存表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface AcctOpenInfoTempMapper extends BaseMapper<AcctOpenInfoTempEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctOpenInfoTemp
	 * @return
	 */
	List<AcctOpenInfoTempVO> selectAcctOpenInfoTempPage(IPage page, AcctOpenInfoTempVO acctOpenInfoTemp);


}
