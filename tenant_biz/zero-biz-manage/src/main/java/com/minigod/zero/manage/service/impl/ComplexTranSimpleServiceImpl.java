package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.minigod.zero.manage.entity.ComplexSimpleLogEntity;
import com.minigod.zero.manage.mapper.ComplexSimpleLogMapper;
import com.minigod.zero.manage.service.IAbPositionService;
import com.minigod.zero.manage.service.IComplexTranSimpleService;
import com.minigod.zero.manage.service.ICustInfoService;
import com.minigod.zero.manage.vo.request.ComplexTranSimpleRequestVo;
import com.minigod.zero.manage.vo.response.ComplexTranSimpleResponseDto;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.ZeroUser;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.user.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author 掌上智珠
 * @since 2023/7/28 17:46
 */
@Service
public class ComplexTranSimpleServiceImpl extends ServiceImpl<ComplexSimpleLogMapper, ComplexSimpleLogEntity> implements IComplexTranSimpleService {
	@Resource
	ComplexSimpleLogMapper complexSimpleLogMapper;
	@Resource
	IAbPositionService abPositionService;
	@Resource
	private ICustInfoService custInfoService;

	@Override
	public IPage<ComplexTranSimpleResponseDto> page(ComplexTranSimpleRequestVo requestVo, Query query) {
		LambdaQueryWrapper<ComplexSimpleLogEntity> queryWrapper = Wrappers.lambdaQuery();
		if (StringUtils.isNotEmpty(requestVo.getBeforeReplace())) {
			queryWrapper.like(ComplexSimpleLogEntity::getBeforeReplace, requestVo.getBeforeReplace());
		}
		if (StringUtils.isNotEmpty(requestVo.getAfterReplace())) {
			queryWrapper.like(ComplexSimpleLogEntity::getAfterReplace, requestVo.getAfterReplace());
		}
		if (StringUtils.isNotEmpty(requestVo.getCreateUserName())) {
			queryWrapper.eq(ComplexSimpleLogEntity::getCreateUserName, requestVo.getCreateUserName());
		}
		if (null != requestVo.getStatus()) {
			queryWrapper.eq(ComplexSimpleLogEntity::getStatus, requestVo.getStatus());
		}

		queryWrapper.orderByDesc(ComplexSimpleLogEntity::getStatus).orderByDesc(ComplexSimpleLogEntity::getUpdateTime);


		Page<ComplexSimpleLogEntity> complexSimpleLogEntityPage = complexSimpleLogMapper.selectPage(new Page<>(query.getCurrent(), query.getSize()), queryWrapper);
		List<Long> updateUserIdList = complexSimpleLogEntityPage.getRecords().stream().map(ComplexSimpleLogEntity::getUpdateUser).filter(Objects::nonNull).collect(Collectors.toList());

		Map<Long, User> updateUserMap =new HashMap<>();
		if (CollectionUtils.isNotEmpty(updateUserIdList)){
			updateUserMap = custInfoService.userInfoByIds(updateUserIdList);
		}
		Map<Long, User> finalUpdateUserMap = (null==updateUserMap)?new HashMap<>():updateUserMap;


		return complexSimpleLogEntityPage.convert(word -> {
				ComplexTranSimpleResponseDto complexTranSimpleResponseDto = new ComplexTranSimpleResponseDto();
				BeanUtil.copyNonNull(word, complexTranSimpleResponseDto);
				User user = finalUpdateUserMap.get(word.getUpdateUser());
				if (null!=user){
					complexTranSimpleResponseDto.setUpdateUserName(user.getName());
				}
				//获取作用的股票列表
				return complexTranSimpleResponseDto;
			});
	}

	@Override
	public R save(ComplexTranSimpleRequestVo complexTranSimpleRequestVo) {
		boolean check = check(complexTranSimpleRequestVo.getBeforeReplace(), complexTranSimpleRequestVo.getAfterReplace());
		if (check){
			return R.fail("存在重复的词");
		}

		ComplexSimpleLogEntity complexSimpleLogEntity = new ComplexSimpleLogEntity();
		BeanUtil.copyNonNull(complexTranSimpleRequestVo,complexSimpleLogEntity);

		Date now = new Date();
		complexSimpleLogEntity.setCreateTime(now);
		complexSimpleLogEntity.setUpdateTime(now);

		ZeroUser user = AuthUtil.getUser();
		if (null!=user){
			complexSimpleLogEntity.setCreateUserName(user.getUserName());
			complexSimpleLogEntity.setCreateUser(user.getUserId());
			complexSimpleLogEntity.setUpdateUser(user.getUserId());
			complexSimpleLogEntity.setCreateDept(StringUtils.isNotEmpty(user.getDeptId())?Long.valueOf(user.getDeptId()):null);
		}
		boolean save = this.save(complexSimpleLogEntity);
		return R.status(save);
	}

	@Override
	public R changeStatus(ComplexTranSimpleRequestVo complexTranSimpleRequestVo) {
		//根据id修改状态
		ComplexSimpleLogEntity complexSimpleLogEntity = this.baseMapper.selectById(complexTranSimpleRequestVo.getId());
		if (null==complexSimpleLogEntity){
			return R.fail("数据不存在");
		}

		//保证上架匹配的记录是唯一的
		if (complexTranSimpleRequestVo.getStatus()==1&&complexSimpleLogEntity.getStatus()!=1){
			boolean check = check(complexSimpleLogEntity.getBeforeReplace(), complexSimpleLogEntity.getAfterReplace());
			if (check){
				return R.fail("已存在上架的相同数据");
			}
		}
		//ab检测
		boolean isCheck = abPositionService.simpleAndComplicatedAbPosition(complexTranSimpleRequestVo.getStatus(), complexSimpleLogEntity.getUpdateUser());
		if (isCheck){
			return R.fail("上架和编辑操作人员不能相同！请更换登录账号后操作!");
		}

		complexSimpleLogEntity.setStatus(complexTranSimpleRequestVo.getStatus());
		complexSimpleLogEntity.setUpdateTime(new Date());

		ZeroUser user = AuthUtil.getUser();
		if (null!=user){
			complexSimpleLogEntity.setUpdateUser(user.getUserId());
		}

		int updateSuccess = this.baseMapper.updateById(complexSimpleLogEntity);
		return R.status(updateSuccess==1);
	}

	@Override
	public R edit(ComplexTranSimpleRequestVo complexTranSimpleRequestVo) {
		ComplexSimpleLogEntity complexSimpleLogEntity = this.baseMapper.selectById(complexTranSimpleRequestVo.getId());
		if (null==complexSimpleLogEntity){
			return R.fail("数据不存在");
		}

		boolean check = check(complexTranSimpleRequestVo.getBeforeReplace(), complexTranSimpleRequestVo.getAfterReplace());
		if (check){
			return R.fail("存在重复的词");
		}

		BeanUtil.copyNonNull(complexTranSimpleRequestVo,complexSimpleLogEntity);

		complexSimpleLogEntity.setUpdateTime(new Date());
		ZeroUser user = AuthUtil.getUser();
		if (null!=user){
			complexSimpleLogEntity.setUpdateUser(user.getUserId());
		}
		int updateSuccess = this.baseMapper.updateById(complexSimpleLogEntity);
		return R.status(updateSuccess==1);
	}

	@Override
	public String trans(Integer type, String txt) {
		//查找状态为1的单词
		LambdaQueryWrapper<ComplexSimpleLogEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(ComplexSimpleLogEntity::getStatus,1);
		Map<String, String> storeMap = this.baseMapper.selectList(queryWrapper).stream().
			collect(Collectors.toMap(ComplexSimpleLogEntity::getBeforeReplace, ComplexSimpleLogEntity::getAfterReplace,(existing, replacement) -> existing));

		String trans;
		//0是繁转简,1简转繁
		if (type==0){
			trans=ZhConverterUtil.convertToSimple(txt);
		}else {
			trans=ZhConverterUtil.convertToTraditional(txt);
		}

		if (storeMap.size()>0){
			//先匹配,再转换
			for (Map.Entry<String, String> e : storeMap.entrySet()) {
				if (trans.contains(e.getKey())){
					trans = trans.replaceAll(e.getKey(), e.getValue());
				}
			}
		}
		return trans;
	}

	@Override
	public List<ComplexSimpleLogEntity> findAll() {
		LambdaQueryWrapper<ComplexSimpleLogEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(ComplexSimpleLogEntity::getStatus,1);
		return this.baseMapper.selectList(queryWrapper);
	}

	/**
	 * 保证一对一
	 * @param beforeWord
	 * @param afterWord
	 * @return
	 */
	private boolean  check(String beforeWord, String afterWord) {
		//查找状态为1的单词
		LambdaQueryWrapper<ComplexSimpleLogEntity> queryWrapper = Wrappers.lambdaQuery();
		queryWrapper.eq(ComplexSimpleLogEntity::getStatus,1);
		List<ComplexSimpleLogEntity> complexSimpleLogEntities = this.baseMapper.selectList(queryWrapper);
		boolean flag=false;
		for (ComplexSimpleLogEntity e : complexSimpleLogEntities) {
			if (e.getBeforeReplace().equals(beforeWord)
				||e.getBeforeReplace().equals(afterWord)
				||e.getAfterReplace().equals(beforeWord)
				||e.getAfterReplace().equals(afterWord)
			){
				flag=true;
			}
		}
		return flag;
	}

	@Override
	public String configWordReplace(String txt) {
		if (StringUtils.isEmpty(txt)){
			return txt;
		}

		List<ComplexSimpleLogEntity> wordList = this.findAll();

		Map<String, String> storeMap = wordList.stream().
			collect(Collectors.toMap(ComplexSimpleLogEntity::getBeforeReplace,
				ComplexSimpleLogEntity::getAfterReplace,
				(existing, replacement) -> existing)
			);


		for (Map.Entry<String, String> entry : storeMap.entrySet()) {
			if (txt.contains(entry.getKey())){
				txt=txt.replaceAll(entry.getKey(),entry.getValue());
			}
		}
		return txt;
	}


}
