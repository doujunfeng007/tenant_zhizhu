package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.AcctOpenInfoEntity;
import com.minigod.zero.bpm.vo.AcctOpenInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 开户申请信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface AcctOpenInfoMapper extends BaseMapper<AcctOpenInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctOpenInfo
	 * @return
	 */
	List<AcctOpenInfoVO> selectAcctOpenInfoPage(IPage page, AcctOpenInfoVO acctOpenInfo);


}
