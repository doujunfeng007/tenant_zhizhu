package com.minigod.zero.manage.mapper;

import com.minigod.zero.manage.entity.BoardInfoEntity;
import com.minigod.zero.manage.vo.BoardInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公告信息表 Mapper 接口
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
public interface BoardInfoMapper extends BaseMapper<BoardInfoEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param boardInfo
	 * @return
	 */
	List<BoardInfoVO> selectBoardInfoPage(IPage page, @Param("boardInfo") BoardInfoVO boardInfo);


}
