package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.BoardCustRegEntity;
import com.minigod.zero.manage.vo.BoardCustRegVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 公告用户信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public interface BoardCustRegMapper extends BaseMapper<BoardCustRegEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param boardCustReg
	 * @return
	 */
	List<BoardCustRegVO> selectBoardCustRegPage(IPage page, BoardCustRegVO boardCustReg);


}
