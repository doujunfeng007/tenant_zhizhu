//package com.minigod.zero.biz.task.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.minigod.zero.biz.task.mapper.NewsHkNoticeMapper;
//import com.minigod.zero.biz.task.service.INewsHkNoticeService;
//import com.minigod.zero.core.mp.base.BaseEntity;
//import com.minigod.zero.oms.entity.cms.NewsHkNoticeEntity;
//import org.springframework.stereotype.Service;
//
///**
// * 公告表 服务实现类
// *
// * @author 掌上智珠
// * @since 2023-01-16
// */
//@Service
//public class NewsHkNoticeServiceImpl extends ServiceImpl<NewsHkNoticeMapper, NewsHkNoticeEntity> implements INewsHkNoticeService {
//
//
//	@Override
//	public NewsHkNoticeEntity findLastNewsHkNotice() {
//		LambdaQueryWrapper<NewsHkNoticeEntity> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.eq(BaseEntity::getStatus, Boolean.TRUE);
//		queryWrapper.orderByDesc(NewsHkNoticeEntity::getLogId);
//		queryWrapper.last("limit 1");
//		return baseMapper.selectOne(queryWrapper);
//	}
//
//}
