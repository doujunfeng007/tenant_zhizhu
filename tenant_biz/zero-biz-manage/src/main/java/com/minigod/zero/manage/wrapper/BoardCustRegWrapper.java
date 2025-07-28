package com.minigod.zero.manage.wrapper;

import com.minigod.zero.manage.entity.BoardCustRegEntity;
import com.minigod.zero.manage.vo.BoardCustRegVO;
import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;

import java.util.Objects;

/**
 * 公告用户信息表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public class BoardCustRegWrapper extends BaseEntityWrapper<BoardCustRegEntity, BoardCustRegVO>  {

	public static BoardCustRegWrapper build() {
		return new BoardCustRegWrapper();
 	}

	@Override
	public BoardCustRegVO entityVO(BoardCustRegEntity boardCustReg) {
	    BoardCustRegVO boardCustRegVO = new BoardCustRegVO();
    	if (boardCustReg != null) {
		    boardCustRegVO = Objects.requireNonNull(BeanUtil.copy(boardCustReg, BoardCustRegVO.class));

		    //User createUser = UserCache.getUser(boardCustReg.getCreateUser());
		    //User updateUser = UserCache.getUser(boardCustReg.getUpdateUser());
		    //boardCustRegVO.setCreateUserName(createUser.getName());
		    //boardCustRegVO.setUpdateUserName(updateUser.getName());
        }
		return boardCustRegVO;
	}


}
