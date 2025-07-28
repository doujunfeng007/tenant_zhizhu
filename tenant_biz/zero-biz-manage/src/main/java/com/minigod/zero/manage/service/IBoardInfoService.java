package com.minigod.zero.manage.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.entity.BoardInfoEntity;
import com.minigod.zero.manage.vo.BoardInfoVO;
import com.minigod.zero.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 公告信息表 服务类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public interface IBoardInfoService extends BaseService<BoardInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param boardInfo
	 * @return
	 */
	IPage<BoardInfoVO> selectBoardInfoPage(IPage<BoardInfoVO> page, BoardInfoVO boardInfo);

	/**
	 * 获取公告条信息
	 * @param bType 类型
	 * @param pCode 位置
	 * @return
	 */
	List<BoardInfoEntity> findBoardInfo(Integer bType, Integer pCode, Long boardId);

	/**
	 * 新增或修改
	 * @param boardInfo
	 * @return
	 */
	R<Object> submit(BoardInfoEntity boardInfo);
}
