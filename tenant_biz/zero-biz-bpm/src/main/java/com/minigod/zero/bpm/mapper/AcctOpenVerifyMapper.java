package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.AcctOpenVerifyEntity;
import com.minigod.zero.bpm.vo.AcctOpenVerifyVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户认证记录表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface AcctOpenVerifyMapper extends BaseMapper<AcctOpenVerifyEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctOpenVerify
	 * @return
	 */
	List<AcctOpenVerifyVO> selectAcctOpenVerifyPage(IPage page, AcctOpenVerifyVO acctOpenVerify);


}
