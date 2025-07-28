package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.AcctRealnameVerifyEntity;
import com.minigod.zero.bpm.vo.AcctRealnameVerifyVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 用户实名认证表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface AcctRealnameVerifyMapper extends BaseMapper<AcctRealnameVerifyEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctRealnameVerify
	 * @return
	 */
	List<AcctRealnameVerifyVO> selectAcctRealnameVerifyPage(IPage page, AcctRealnameVerifyVO acctRealnameVerify);


}
