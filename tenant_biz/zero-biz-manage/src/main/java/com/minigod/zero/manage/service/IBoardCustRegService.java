package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.manage.entity.BoardCustRegEntity;
import com.minigod.zero.manage.vo.BoardCustRegVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 公告用户信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public interface IBoardCustRegService extends IService<BoardCustRegEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param boardCustReg
	 * @return
	 */
	IPage<BoardCustRegVO> selectBoardCustRegPage(IPage<BoardCustRegVO> page, BoardCustRegVO boardCustReg);

	/**
	 * 获取公告条用户信息
	 * @param userId
	 * @param boardId
	 * @return
	 */
	BoardCustRegEntity findUserBoardReg(Long userId, Long boardId);
}
