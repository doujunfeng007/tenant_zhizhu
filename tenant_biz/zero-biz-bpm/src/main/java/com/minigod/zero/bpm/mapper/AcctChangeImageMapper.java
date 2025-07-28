package com.minigod.zero.bpm.mapper;

import com.minigod.zero.bpm.entity.AcctChangeImageEntity;
import com.minigod.zero.bpm.vo.AcctChangeImageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户修改资料图片表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-05-17
 */
public interface AcctChangeImageMapper extends BaseMapper<AcctChangeImageEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param acctChangeImage
	 * @return
	 */
	List<AcctChangeImageVO> selectAcctChangeImagePage(IPage page, AcctChangeImageVO acctChangeImage);


}
