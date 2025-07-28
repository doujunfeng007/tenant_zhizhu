package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.manage.entity.BoardCustRegEntity;
import com.minigod.zero.manage.vo.BoardCustRegVO;
import com.minigod.zero.manage.mapper.BoardCustRegMapper;
import com.minigod.zero.manage.service.IBoardCustRegService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 公告用户信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@Service
public class BoardCustRegServiceImpl extends ServiceImpl<BoardCustRegMapper, BoardCustRegEntity> implements IBoardCustRegService {

	@Override
	public IPage<BoardCustRegVO> selectBoardCustRegPage(IPage<BoardCustRegVO> page, BoardCustRegVO boardCustReg) {
		return page.setRecords(baseMapper.selectBoardCustRegPage(page, boardCustReg));
	}

	@Override
	public BoardCustRegEntity findUserBoardReg(Long userId, Long boardId) {
		return new LambdaQueryChainWrapper<>(baseMapper)
			.eq(BoardCustRegEntity::getUserId, userId)
			.eq(BoardCustRegEntity::getBoardId, boardId)
			.one();
	}
}
