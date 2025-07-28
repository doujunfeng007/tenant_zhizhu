//package com.minigod.zero.biz.task.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.minigod.zero.biz.task.mapper.NewsTpoicRelMapper;
//import com.minigod.zero.biz.task.service.INewsTpoicRelService;
//import com.minigod.zero.core.mp.base.BaseEntity;
//import com.minigod.zero.oms.entity.cms.NewsTpoicRelEntity;
//import org.springframework.stereotype.Service;
//
///**
// * 新闻与专题关联表 服务实现类
// *
// * @author 掌上智珠
// * @since 2022-11-24
// */
//@Service
//public class NewsTpoicRelServiceImpl extends ServiceImpl<NewsTpoicRelMapper, NewsTpoicRelEntity> implements INewsTpoicRelService {
//
//	@Override
//	public NewsTpoicRelEntity findLastNewsTpoicRel() {
//		LambdaQueryWrapper<NewsTpoicRelEntity> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.eq(BaseEntity::getStatus, true);
//		queryWrapper.orderByDesc(BaseEntity::getId);
//		queryWrapper.last("limit 1");
//		return baseMapper.selectOne(queryWrapper);
//	}
//}
