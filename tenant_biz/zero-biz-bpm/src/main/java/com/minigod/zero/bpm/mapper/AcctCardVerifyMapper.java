package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.AcctCardVerifyEntity;
import com.minigod.zero.bpm.vo.AcctCardVerifyVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 银行卡四要素验证信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface AcctCardVerifyMapper extends BaseMapper<AcctCardVerifyEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctCardVerify
	 * @return
	 */
	List<AcctCardVerifyVO> selectAcctCardVerifyPage(IPage page, AcctCardVerifyVO acctCardVerify);


}
