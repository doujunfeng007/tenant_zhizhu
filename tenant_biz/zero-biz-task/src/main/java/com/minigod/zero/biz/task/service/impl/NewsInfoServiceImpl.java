//package com.minigod.zero.biz.task.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.minigod.zero.biz.common.enums.ENewsEnums;
//import com.minigod.zero.biz.common.utils.DateUtil;
//import com.minigod.zero.biz.task.mapper.NewsInfoMapper;
//import com.minigod.zero.biz.task.service.INewsInfoService;
//import com.minigod.zero.core.mp.base.BaseEntity;
//import com.minigod.zero.oms.entity.cms.NewsInfoEntity;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//
//
///**
// * 资讯新闻信息表 服务实现类
// *
// * @author 掌上智珠
// * @since 2022-11-24
// */
//@Slf4j
//@Service
//public class NewsInfoServiceImpl extends ServiceImpl<NewsInfoMapper, NewsInfoEntity> implements INewsInfoService {
//
//	@Override
//	public List<NewsInfoEntity> findIndexMarketNews(int count) {
//		LambdaQueryWrapper<NewsInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.eq(BaseEntity::getStatus, true);
//		queryWrapper.eq(NewsInfoEntity::getTag, ENewsEnums.INDEX_NEWS.getTypeValue());
//		queryWrapper.orderByDesc(NewsInfoEntity::getIssueTime);
//		queryWrapper.last(new StringBuffer("limit ").append(count).toString());
//		return baseMapper.selectList(queryWrapper);
//	}
//
//	@Override
//	public List<NewsInfoEntity> findImportantMarketNews(int count) {
//		LambdaQueryWrapper<NewsInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.eq(BaseEntity::getStatus, true);
//		queryWrapper.eq(NewsInfoEntity::getIsImportant, true);
//		queryWrapper.orderByDesc(NewsInfoEntity::getIssueTime);
//		queryWrapper.last(new StringBuffer("limit ").append(count).toString());
//		return baseMapper.selectList(queryWrapper);
//	}
//
//	@Override
//	public List<NewsInfoEntity> findLiveMarketNews(int count) {
//		LambdaQueryWrapper<NewsInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.eq(BaseEntity::getStatus, true);
//		queryWrapper.eq(NewsInfoEntity::getIsLive, true);
//		queryWrapper.eq(NewsInfoEntity::getInfotreeid, ENewsEnums.LIVE_NEWS.getTypeValue());
//		queryWrapper.orderByDesc(NewsInfoEntity::getIssueTime);
//		queryWrapper.last(new StringBuffer("limit ").append(count).toString());
//		return baseMapper.selectList(queryWrapper);
//	}
//
//	@Override
//	public List<NewsInfoEntity> findHeadlineMarketNews(int count) {
//		LambdaQueryWrapper<NewsInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.eq(BaseEntity::getStatus, true);
//		queryWrapper.eq(NewsInfoEntity::getTag, ENewsEnums.HEADLINE_NEWS.getTypeValue());
//		queryWrapper.orderByDesc(NewsInfoEntity::getIssueTime);
//		queryWrapper.last(new StringBuffer("limit ").append(count).toString());
//		return baseMapper.selectList(queryWrapper);
//	}
//
//	@Override
//	public List<NewsInfoEntity> findChoiceMarketNews(int count) {
//		LambdaQueryWrapper<NewsInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.eq(BaseEntity::getStatus, true);
//		queryWrapper.eq(NewsInfoEntity::getTag, ENewsEnums.CHOICE_NEWS.getTypeValue());
//		queryWrapper.ge(NewsInfoEntity::getIssueTime, DateUtil.getDateByDay(-1));
//		queryWrapper.orderByDesc(NewsInfoEntity::getIssueTime);
//		queryWrapper.last(new StringBuffer("limit ").append(count).toString());
//		return baseMapper.selectList(queryWrapper);
//	}
//
//	@Override
//	public List<NewsInfoEntity> findColumnMarketNews(int count) {
//		LambdaQueryWrapper<NewsInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.eq(BaseEntity::getStatus, true);
//		queryWrapper.eq(NewsInfoEntity::getInfotreeid, ENewsEnums.COLUMN_NEWS.getTypeValue());
//		queryWrapper.orderByDesc(NewsInfoEntity::getIssueTime);
//		queryWrapper.last(new StringBuffer("limit ").append(count).toString());
//		return baseMapper.selectList(queryWrapper);
//	}
//
//	@Override
//	public List<NewsInfoEntity> findPhoenixMarketNews(int count) {
//		LambdaQueryWrapper<NewsInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.eq(BaseEntity::getStatus, true);
//		queryWrapper.eq(NewsInfoEntity::getInfotreeid, ENewsEnums.PHOENIX_NEWS.getTypeValue());
//		queryWrapper.orderByDesc(NewsInfoEntity::getIssueTime);
//		queryWrapper.last(new StringBuffer("limit ").append(count).toString());
//		return baseMapper.selectList(queryWrapper);
//	}
//
//	@Override
//	public List<NewsInfoEntity> findLastNews(Date lastMins) {
//		LambdaQueryWrapper<NewsInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.eq(BaseEntity::getStatus, true);
//		queryWrapper.gt(NewsInfoEntity::getIssueTime, lastMins);
//		queryWrapper.orderByDesc(NewsInfoEntity::getIssueTime);
//		return baseMapper.selectList(queryWrapper);
//	}
//
//	@Override
//	public NewsInfoEntity findNewsById(Long newsId) {
//		LambdaQueryWrapper<NewsInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.eq(BaseEntity::getStatus, true);
//		queryWrapper.eq(BaseEntity::getId, newsId);
//		return baseMapper.selectOne(queryWrapper);
//	}
//
//	@Override
//	public NewsInfoEntity findLastHeadLineNews() {
//		LambdaQueryWrapper<NewsInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.eq(BaseEntity::getStatus, true);
//		queryWrapper.eq(NewsInfoEntity::getTag, ENewsEnums.HEADLINE_NEWS.getTypeValue());
//		queryWrapper.orderByDesc(NewsInfoEntity::getUpdateTime);
//		queryWrapper.last("limit 1");
//		return baseMapper.selectOne(queryWrapper);
//	}
//
//	@Override
//	public List<NewsInfoEntity> findNewsByIdAndCount(Long newsId, Integer count) {
//		LambdaQueryWrapper<NewsInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.eq(BaseEntity::getStatus, true);
//		queryWrapper.gt(BaseEntity::getId, newsId);
//		queryWrapper.isNotNull(NewsInfoEntity::getGp);
//		queryWrapper.orderByDesc(NewsInfoEntity::getIssueTime);
//		queryWrapper.last("limit " + count);
//		return baseMapper.selectList(queryWrapper);
//	}
//
//	@Override
//	public NewsInfoEntity findLastNews() {
//		LambdaQueryWrapper<NewsInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.eq(BaseEntity::getStatus, true);
//		queryWrapper.orderByDesc(NewsInfoEntity::getLogId);
//		queryWrapper.last("limit 1");
//		return baseMapper.selectOne(queryWrapper);
//	}
//}
