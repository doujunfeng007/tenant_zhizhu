package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.AcctOpenImageEntity;
import com.minigod.zero.bpm.vo.AcctOpenImageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户开户影像表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface AcctOpenImageMapper extends BaseMapper<AcctOpenImageEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctOpenImage
	 * @return
	 */
	List<AcctOpenImageVO> selectAcctOpenImagePage(IPage page, AcctOpenImageVO acctOpenImage);


}
