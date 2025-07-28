package com.minigod.zero.manage.wrapper;

import com.minigod.zero.core.mp.support.BaseEntityWrapper;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.manage.entity.BoardInfoEntity;
import com.minigod.zero.manage.vo.BoardInfoVO;
import java.util.Objects;

/**
 * 公告信息表 包装类,返回视图层所需的字段
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public class BoardInfoWrapper extends BaseEntityWrapper<BoardInfoEntity, BoardInfoVO>  {

	public static BoardInfoWrapper build() {
		return new BoardInfoWrapper();
 	}

	@Override
	public BoardInfoVO entityVO(BoardInfoEntity boardInfo) {
	    BoardInfoVO boardInfoVO = new BoardInfoVO();
    	if (boardInfo != null) {
		    boardInfoVO = Objects.requireNonNull(BeanUtil.copy(boardInfo, BoardInfoVO.class));

		    //User createUser = UserCache.getUser(boardInfo.getCreateUser());
		    //User updateUser = UserCache.getUser(boardInfo.getUpdateUser());
		    //boardInfoVO.setCreateUserName(createUser.getName());
		    //boardInfoVO.setUpdateUserName(updateUser.getName());
        }
		return boardInfoVO;
	}


}
