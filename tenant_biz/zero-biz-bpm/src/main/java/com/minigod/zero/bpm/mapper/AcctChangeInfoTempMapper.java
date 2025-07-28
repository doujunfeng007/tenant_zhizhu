package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.AcctChangeInfoTempEntity;
import com.minigod.zero.bpm.vo.AcctChangeInfoTempVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户证券资料修改缓存表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface AcctChangeInfoTempMapper extends BaseMapper<AcctChangeInfoTempEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctChangeInfoTemp
	 * @return
	 */
	List<AcctChangeInfoTempVO> selectAcctChangeInfoTempPage(IPage page, AcctChangeInfoTempVO acctChangeInfoTemp);


}
