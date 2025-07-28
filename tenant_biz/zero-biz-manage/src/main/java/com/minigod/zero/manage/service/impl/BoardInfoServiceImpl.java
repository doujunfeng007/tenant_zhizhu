package com.minigod.zero.manage.service.impl;

import cn.jiguang.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseEntity;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.entity.BoardInfoEntity;
import com.minigod.zero.manage.mapper.BoardInfoMapper;
import com.minigod.zero.manage.service.IBoardInfoService;
import com.minigod.zero.manage.vo.BoardInfoVO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 公告信息表 服务实现类
 *
 * @author 掌上智珠
 * @since 2023-03-18
 */
@Service
public class BoardInfoServiceImpl extends BaseServiceImpl<BoardInfoMapper, BoardInfoEntity> implements IBoardInfoService {

	@Override
	public IPage<BoardInfoVO> selectBoardInfoPage(IPage<BoardInfoVO> page, BoardInfoVO boardInfo) {
		List<BoardInfoVO> list = baseMapper.selectBoardInfoPage(page, boardInfo);
		return page.setRecords(list);
	}

	@Override
	public List<BoardInfoEntity> findBoardInfo(Integer bType, Integer pCode, Long boardId) {
		Date date = new Date();
		LambdaQueryWrapper<BoardInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.le(BoardInfoEntity::getStartTime, date);
		queryWrapper.eq(BaseEntity::getStatus, true);
		queryWrapper.orderByAsc(BoardInfoEntity::getPriority);
		if (pCode != null){
			queryWrapper.like(BoardInfoEntity::getPositionCode, pCode);
		}
		if (boardId != null){
			queryWrapper.eq(BaseEntity::getId, boardId);
		}

		return baseMapper.selectList(queryWrapper);
	}

	@Override
	public R<Object> submit(BoardInfoEntity boardInfo) {
		if(StringUtils.isEmpty(boardInfo.getContent())){
			return R.fail("正标题不能为空");
		}
		if(null == boardInfo.getStartTime()){
			return R.fail("开始时间不能为空");
		}
		if(null == boardInfo.getEndTime()){
			return R.fail("结束时间不能为空");
		}
		Long count = findBoardInfo(boardInfo);
		if(count>0){
			return R.fail("有效广告条信息优先级重复");
		}
		return R.data(this.saveOrUpdate(boardInfo));
	}

	Long findBoardInfo(BoardInfoEntity boardInfoEntity) {
		Date date = new Date();
		LambdaQueryWrapper<BoardInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(BoardInfoEntity::getIsDeleted, 0)
			.le(BoardInfoEntity::getStartTime, date)
			.ge(BoardInfoEntity::getEndTime, date)
			.eq(BoardInfoEntity::getStatus, true)
			.eq(BoardInfoEntity::getPriority, boardInfoEntity.getPriority())
			.eq(BoardInfoEntity::getTenantId, AuthUtil.getTenantId())
			.like(BoardInfoEntity::getPositionCode, boardInfoEntity.getPositionCode());
		if (null != boardInfoEntity.getId()) {
			queryWrapper.ne(BoardInfoEntity::getId, boardInfoEntity.getId());
		}
		return baseMapper.selectCount(queryWrapper);
	}
}
