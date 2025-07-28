package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.biz.common.enums.CommonEnums;
import com.minigod.zero.biz.common.utils.SensitiveWordFilter;
import com.minigod.zero.manage.entity.SensitiveWordEntity;
import com.minigod.zero.manage.entity.SensitiveWordLogEntity;
import com.minigod.zero.manage.enums.SensitiveWordScopeEnum;
import com.minigod.zero.manage.enums.SensitiveWordStatusEnum;
import com.minigod.zero.manage.enums.SensitiveWordTimeScopeEnum;
import com.minigod.zero.manage.mapper.SensitiveWordLogMapper;
import com.minigod.zero.manage.mapper.SensitiveWordMapper;
import com.minigod.zero.manage.service.IAbPositionService;
import com.minigod.zero.manage.service.ICustInfoService;
import com.minigod.zero.manage.service.ISensitiveWordService;
import com.minigod.zero.manage.vo.request.SensitiveWordEditVo;
import com.minigod.zero.manage.vo.request.SensitiveWordRequestVo;
import com.minigod.zero.manage.vo.response.GpSensitiveWordDto;
import com.minigod.zero.manage.vo.response.SensitiveFilterHistoryVo;
import com.minigod.zero.manage.vo.response.SensitiveWordLogRespDto;
import com.minigod.zero.manage.vo.response.SensitiveWordRespDto;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.user.entity.User;
import com.minigod.zero.user.feign.IUserClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 掌上智珠
 * @since 2023/7/17 18:10
 */
@Service
public class SensitiveWordServiceImpl extends ServiceImpl<SensitiveWordMapper, SensitiveWordEntity>  implements ISensitiveWordService {
	@Resource
	SensitiveWordMapper sensitiveWordMapper;
	@Resource
	SensitiveWordLogMapper sensitiveWordLogMapper;
	@Resource
	IUserClient userClient;
	@Resource
	ZeroRedis zeroRedis;
	@Resource
	private IAbPositionService abPositionService;
	@Resource
	private ICustInfoService custInfoService;


	@Override
	public List<String> getEnableLexicon(Integer scope) {

		if (null == scope) {
			return Collections.emptyList();
		}

		/**
		 * 查找指定作用域下已经生效的敏感词
		 */
		LambdaQueryWrapper<SensitiveWordEntity> query = Wrappers.<SensitiveWordEntity>lambdaQuery()
			.eq(SensitiveWordEntity::getScope, scope)
			.eq(SensitiveWordEntity::getStatus, SensitiveWordStatusEnum.ENABLE.getCode())
			.eq(SensitiveWordEntity::getIsDeleted, 0);

		return sensitiveWordMapper.selectList(query)
			.stream()
			.map(SensitiveWordEntity::getWord)
			.collect(Collectors.toList());
	}

	@Override
	public String filterWord(String checkWord, Integer scope, Character replace) {
		List<String> enableLexicon = this.getEnableLexicon(scope);

		/**
		 * 使用静态类的话在修改数据后root无法更新,需要添加逻辑,后续可优化
		 */
		SensitiveWordFilter sensitiveWordFilter = new SensitiveWordFilter();
		sensitiveWordFilter.addSensitiveWord(enableLexicon);
		return sensitiveWordFilter.filter(checkWord,replace);
	}

	@Override
	public List<GpSensitiveWordDto> getGpSensitiveWordList(Integer scope, String source) {

		//获取智珠的敏感词表

		LambdaQueryWrapper<SensitiveWordEntity> query = Wrappers.<SensitiveWordEntity>lambdaQuery()
			.eq(SensitiveWordEntity::getScope, scope)
			.eq(SensitiveWordEntity::getStatus, SensitiveWordStatusEnum.ENABLE.getCode())
			.eq(SensitiveWordEntity::getIsDeleted, 0);

		//如果指定来源就按指定来源,如果没有指定来源,那么就全局生效
		if (StringUtil.isNotBlank(source)){
			query.eq(SensitiveWordEntity::getEffectiveSource,source);
		}

		List<SensitiveWordEntity> sensitiveWordEntities = sensitiveWordMapper.selectList(query);

		List<GpSensitiveWordDto> gpSensitiveWordDtoList = new ArrayList<>();
		//没有指定来源的也一并返回,默认全局生效
		gpSensitiveWordDtoList = sensitiveWordEntities.stream()
			.flatMap(sensitiveWord -> { //没有配置股票列表的给一个虚拟的key存放在map中
				if (StringUtil.isBlank(sensitiveWord.getAssetId())) {
					return Arrays.stream(new GpSensitiveWordDto[]{
						new GpSensitiveWordDto(sensitiveWord.getWord(), "emptyCode")
					});
				} else {
					String[] stocks = sensitiveWord.getAssetId().split(",");
					return Arrays.stream(stocks)
						.map(stock -> new GpSensitiveWordDto(sensitiveWord.getWord(), stock.trim()));
				}
			})
			.collect(Collectors.toList());

		return gpSensitiveWordDtoList;
	}

	@Override
	public IPage<SensitiveWordLogRespDto> detailPage(SensitiveWordRequestVo requestVo, Query query) {
		//查询记录
		IPage<SensitiveWordLogRespDto> sensitiveWordLogePage = sensitiveWordLogMapper.selectSensitiveWordLogePage(Condition.getPage(query), requestVo);

		/**
		 * 根据查询出来的记录在word词库中查找相关信息,并且封装成map
		 */
		HashSet<Integer> scopeSet = new HashSet<>();
		HashSet<String> wordSet = new HashSet<>();
		for (SensitiveWordLogRespDto record : sensitiveWordLogePage.getRecords()) {
			//根据word和scope查找word相关数据
			scopeSet.add(record.getScope());
			wordSet.add(record.getWord());
		}
		List<SensitiveWordEntity> sensitiveWordEntities=new ArrayList<>();
		HashMap<String, SensitiveWordEntity> entryMap = new HashMap<>();
		if (wordSet.size()>0&&scopeSet.size()>0){
			LambdaQueryWrapper<SensitiveWordEntity> queryWrapper = new LambdaQueryWrapper<>();
			sensitiveWordEntities = this.getBaseMapper().selectList(
				queryWrapper.in(SensitiveWordEntity::getWord, wordSet).in(SensitiveWordEntity::getScope, scopeSet)
			);

			//用@@区分
			for (SensitiveWordEntity sensitiveWordEntity : sensitiveWordEntities) {
				if (StringUtil.isNotBlank(sensitiveWordEntity.getWord())&&sensitiveWordEntity.getScope()!=null){
					entryMap.put(sensitiveWordEntity.getWord()+"@@"+ sensitiveWordEntity.getScope(),sensitiveWordEntity);
				}
			}
		}

		//获取敏感词创建人信息
		HashSet<Long> userIdSet = new HashSet<>();
		Map<Long, String> userIdAndNameMap =new HashMap<>();
		for (SensitiveWordEntity sensitiveWordEntity : sensitiveWordEntities) {
			userIdSet.add(sensitiveWordEntity.getCreateUser());
		}
		if (!userIdSet.isEmpty()){
			userIdAndNameMap = userClient.userInfoByIds(new ArrayList<>(userIdSet)).stream()
				.collect(Collectors.toMap(User::getId, user -> StringUtil.isNotBlank(user.getName()) ? user.getName() : ""));
		}


		//封装数据
		Map<Long, String> finalUserIdAndNameMap = userIdAndNameMap;
		sensitiveWordLogePage.convert(log->{
			//设置股票列表
			if(StringUtil.isNotBlank(log.getStocks())){
				List<String> assetIdList =
					Arrays.stream(log.getStocks().split(",")).filter(Objects::nonNull).collect(Collectors.toList());
				log.setAssetId(assetIdList);
			}
			if (StringUtil.isNotBlank(log.getWord())&&log.getScope()!=null){
				SensitiveWordEntity word = entryMap.get(log.getWord() + "@@" + log.getScope());
				if (null!=word){
					log.setId(word.getId());
					log.setStatus(word.getStatus());

					if (log.getScope()==0){
						log.setTimeScope(0);
					}else {
						log.setTimeScope(DateTransTimeScope(word.getEffectiveTime(), word.getCreateTime()));
					}

					log.setEffectiveTime(setReturnEffectiveTime(word.getStatus(),word.getScope(),word.getEffectiveTime(),word.getUpdateTime()));
					log.setCreateUserName(finalUserIdAndNameMap.getOrDefault(word.getCreateUser(),"管理员"));
				}
			}
			return log;
		});
		//封装数据
		return sensitiveWordLogePage;
	}

	@Override
	public IPage<SensitiveWordRespDto> page(SensitiveWordRequestVo requestVo, Query query) {
		LambdaQueryWrapper<SensitiveWordEntity> queryWrapper = Wrappers.lambdaQuery();
		if (StringUtils.isNotEmpty(requestVo.getWord())) {
			queryWrapper.like(SensitiveWordEntity::getWord, requestVo.getWord());
		}
		if (null != requestVo.getScope()) {
			queryWrapper.eq(SensitiveWordEntity::getScope, requestVo.getScope());
		}
		if (null != requestVo.getStatus()) {
			queryWrapper.eq(SensitiveWordEntity::getStatus, requestVo.getStatus());
		}
		if (null != requestVo.getEffectiveTime()) {
			queryWrapper.ge(SensitiveWordEntity::getEffectiveTime, requestVo.getEffectiveTime());
		}
		if (StringUtil.isNotBlank(requestVo.getEffectiveSource())) {
			queryWrapper.eq(SensitiveWordEntity::getEffectiveSource, requestVo.getEffectiveSource());
		}
		queryWrapper.orderByDesc(SensitiveWordEntity::getCreateTime);
		Page<SensitiveWordEntity> sensitiveWordEntityPage = sensitiveWordMapper.selectPage(new Page<>(query.getCurrent(), query.getSize()), queryWrapper);

		//获取更新人idlist
		List<Long> updateUserIdList = sensitiveWordEntityPage.getRecords().stream().map(SensitiveWordEntity::getUpdateUser).filter(Objects::nonNull).collect(Collectors.toList());
		Map<Long, User> updateUserMap =new HashMap<>();
		if (CollectionUtils.isNotEmpty(updateUserIdList)){
			updateUserMap = custInfoService.userInfoByIds(updateUserIdList);
		}

		Map<Long, User> finalUpdateUserMap = (null==updateUserMap)?new HashMap<>():updateUserMap;

		return sensitiveWordEntityPage.convert(word -> {
				SensitiveWordRespDto sensitiveWordRespDto = new SensitiveWordRespDto();

				BeanUtil.copyNonNull(word, sensitiveWordRespDto);
				//获取作用的股票列表
				if(StringUtil.isNotBlank(word.getAssetId())){
					List<String> assetIdList =
						Arrays.stream(word.getAssetId().split(",")).filter(Objects::nonNull).collect(Collectors.toList());
					sensitiveWordRespDto.setAssetId(assetIdList);
				}
				//设置作用范围
				sensitiveWordRespDto.setTimeScope(DateTransTimeScope(word.getEffectiveTime(), word.getCreateTime()));
				sensitiveWordRespDto.setEffectiveTime(setReturnEffectiveTime(word.getStatus(),word.getScope(),word.getEffectiveTime(),word.getUpdateTime()));
				User user = finalUpdateUserMap.get(word.getUpdateUser());
				if (null!=user){
					sensitiveWordRespDto.setUpdateUserName(user.getName());
				}

				//公共词库导入的时候没有创建时间和更新时间,在这里写死，sit和生产的导入时间都是这个
				if (word.getScope()==0&&word.getUpdateTime()==null){
					sensitiveWordRespDto.setUpdateTime(DateUtil.parse("2023-07-19 18:51:25","yyyy-MM-dd HH:mm:ss"));
				}
				if (word.getScope()==0&&word.getCreateTime()==null){
					sensitiveWordRespDto.setCreateTime(DateUtil.parse("2023-07-19 18:51:25","yyyy-MM-dd HH:mm:ss"));
				}
				return sensitiveWordRespDto;
			});

	}

	@Override
	public SensitiveWordEntity getOneWord(String word, Integer scope) {
		return sensitiveWordMapper.selectOne(
			Wrappers.<SensitiveWordEntity>lambdaQuery()
			.eq(SensitiveWordEntity::getWord,word)
			.eq(SensitiveWordEntity::getScope,scope)
			.eq(SensitiveWordEntity::getIsDeleted, CommonEnums.StatusEnum.NO.getCode()));
	}

	@Override
	public R save(SensitiveWordRequestVo sensitiveWordRequestVo) {
		SensitiveWordEntity word = getOneWord(sensitiveWordRequestVo.getWord(), sensitiveWordRequestVo.getScope());
		if (null!=word){
			return R.fail("该敏感词已存在");
		}
		SensitiveWordEntity sensitiveWordEntity = new SensitiveWordEntity();
		BeanUtil.copyNonNull(sensitiveWordRequestVo,sensitiveWordEntity);

		//编辑生效时间
		Integer timeScope=sensitiveWordRequestVo.getTimeScope();
		if (null!=sensitiveWordRequestVo.getTimeScope()){
			if (Objects.equals(timeScope, SensitiveWordTimeScopeEnum.AFTEREFFECTIVETIME.getCode())
				|| Objects.equals(timeScope, SensitiveWordTimeScopeEnum.LASTMONTH.getCode())
				|| Objects.equals(timeScope, SensitiveWordTimeScopeEnum.ALL.getCode())
			){
				//生成生效时间
				Date effectiveTime = timeScopeTransDate(sensitiveWordRequestVo.getTimeScope());
				//如果是生效时间后执行的,那么将createTime==effectiveTime,便于判断
				if (Objects.equals(sensitiveWordRequestVo.getTimeScope(), SensitiveWordTimeScopeEnum.AFTEREFFECTIVETIME.getCode())){
					sensitiveWordEntity.setCreateTime(effectiveTime);
				}
				sensitiveWordEntity.setEffectiveTime(effectiveTime);
			}else {
				return R.fail("不存在该生效范围");
			}
		}

		//添加时如果没有status为它添加默认值
		if (null==sensitiveWordRequestVo.getStatus()){
			sensitiveWordEntity.setStatus(SensitiveWordStatusEnum.ENABLE.getCode());
		}

		//添加其他信息
		Long userId = AuthUtil.getUserId();

		sensitiveWordEntity.setCreateUser(userId);
		sensitiveWordEntity.setUpdateUser(userId);
		sensitiveWordEntity.setCreateDept(StringUtil.isNotBlank(AuthUtil.getDeptId())?Long.valueOf(AuthUtil.getDeptId()):null);
		sensitiveWordEntity.setCreateTime(new Date());
		sensitiveWordEntity.setUpdateTime(new Date());

		boolean save = this.save(sensitiveWordEntity);
		if (save){
			saveToRedisForTaskFilter(sensitiveWordEntity,timeScope);
		}
		return R.status(save);
	}

	@Override
	public R changeStatus(SensitiveWordEditVo sensitiveWordEditVo) {
		//判断该敏感词是否存在
		SensitiveWordEntity sensitiveWordEntity = sensitiveWordMapper.selectById(sensitiveWordEditVo.getId());
		if (null==sensitiveWordEntity){
			return R.fail("该敏感词不存在,无法编辑");
		}
		Integer status = sensitiveWordEditVo.getStatus();
		Integer scope = sensitiveWordEntity.getScope();

		//ab岗位机制判定
		boolean isCheck = abPositionService.sensitiveWordAbPosition(status, sensitiveWordEntity.getUpdateUser());
		if (isCheck){
			return R.fail("上架和编辑操作人员不能相同！请更换登录账号后操作!");
		}

		//如果是全局敏感词,只允许对上架词做下架操作,下架词做上架操作
		if (Objects.equals(scope, SensitiveWordScopeEnum.ALL.getCode())){

			if (
				//数据库为上架,修改状态为下架
				Objects.equals(sensitiveWordEntity.getStatus(), SensitiveWordStatusEnum.ENABLE.getCode()) &&
				Objects.equals(status, SensitiveWordStatusEnum.DISABLE.getCode())){
				sensitiveWordEntity.setStatus(status);
			}else if (
				//数据库为下架,修改状态为上架
				Objects.equals(sensitiveWordEntity.getStatus(), SensitiveWordStatusEnum.DISABLE.getCode()) &&
				Objects.equals(status, SensitiveWordStatusEnum.ENABLE.getCode())) {
				sensitiveWordEntity.setStatus(status);
			}else {
				return R.fail("无法使用该状态进行修改");
			}

		}

		//智珠的敏感词
		else if(Objects.equals(scope, SensitiveWordScopeEnum.ZHIZHU.getCode())){

//			//智珠修改只有下架的状态,而且只能对上架的词语进行下架操作,上架是save的时候默认保存的
//			if (!Objects.equals(status, SensitiveWordStatusEnum.DISABLE.getCode())){
//				return R.fail("无法使用该状态进行修改");
//			}
//			if (!Objects.equals(sensitiveWordEntity.getStatus(), SensitiveWordStatusEnum.ENABLE.getCode())){
//				return R.fail("无法对该词语进行修改");
//			}
			sensitiveWordEntity.setStatus(status);
		}

		//设置修改人和修改时间
		Long userId = AuthUtil.getUserId();
		sensitiveWordEntity.setUpdateUser(userId);
		sensitiveWordEntity.setUpdateTime(DateUtil.now());

		boolean isSuccess = this.updateById(sensitiveWordEntity);
		if (isSuccess){
			SensitiveWordLogEntity sensitiveWordLogEntity = new SensitiveWordLogEntity();
			sensitiveWordLogEntity.setStatus(status);
			sensitiveWordLogMapper.update(
				sensitiveWordLogEntity,
				Wrappers.<SensitiveWordLogEntity>update().lambda()
					.eq(SensitiveWordLogEntity::getWord,sensitiveWordEntity.getWord())
					.eq(SensitiveWordLogEntity::getScope,sensitiveWordEntity.getScope()));
		}
		return R.status(isSuccess);
	}

	@Override
	public R editWord(SensitiveWordEditVo sensitiveWordEditVo) {
		//判断该敏感词是否存在,不允许对公共词库进行修改
		SensitiveWordEntity sensitiveWordEntity = sensitiveWordMapper.selectById(sensitiveWordEditVo.getId());
		if (null==sensitiveWordEntity){
			return R.fail("该敏感词不存在,无法编辑");
		}

		Integer scope = sensitiveWordEntity.getScope();
		if (Objects.equals(sensitiveWordEntity.getScope(), SensitiveWordScopeEnum.ALL.getCode())){
			return R.fail("不允许对公共词库进行修改");
		}

		//修改该敏感词,判断该作用域内是否存在
		if (StringUtil.isNotBlank(sensitiveWordEditVo.getWord())){
			SensitiveWordEntity oneWord = getOneWord(sensitiveWordEditVo.getWord(), scope);
			if (null!=oneWord&& !Objects.equals(sensitiveWordEditVo.getWord(), oneWord.getWord())){
				return R.fail("该词语已经在敏感词库中");
			}

			sensitiveWordEntity.setWord(sensitiveWordEditVo.getWord());
		}

		//编辑生效时间
		Integer timeScope = sensitiveWordEditVo.getTimeScope();
		if (null!=timeScope){
			if (Objects.equals(timeScope, SensitiveWordTimeScopeEnum.AFTEREFFECTIVETIME.getCode())
				|| Objects.equals(timeScope, SensitiveWordTimeScopeEnum.LASTMONTH.getCode())
				|| Objects.equals(timeScope, SensitiveWordTimeScopeEnum.ALL.getCode())
			){
				Date effectiveTime = timeScopeTransDate(sensitiveWordEditVo.getTimeScope());

				if (Objects.equals(sensitiveWordEditVo.getTimeScope(), SensitiveWordTimeScopeEnum.AFTEREFFECTIVETIME.getCode())){
					effectiveTime=sensitiveWordEntity.getCreateTime();
				}
				sensitiveWordEntity.setEffectiveTime(effectiveTime);
			}else {
				return R.fail("不存在该生效范围");
			}
		}

		//编辑股票列表
		sensitiveWordEntity.setAssetId(sensitiveWordEditVo.getAssetId());
		//编辑来源
		sensitiveWordEntity.setEffectiveSource(sensitiveWordEditVo.getEffectiveSource());
		//设置更新时间
		sensitiveWordEntity.setUpdateTime(new Date());
		//设置状态
		Integer status = sensitiveWordEditVo.getStatus();
		sensitiveWordEntity.setStatus(status);
		//设置更新人
		Long userId = AuthUtil.getUserId();
		sensitiveWordEntity.setUpdateUser(userId);

		//同步更新敏感词记录的状态
		boolean update = this.updateById(sensitiveWordEntity);
		if (update){
			SensitiveWordLogEntity sensitiveWordLogEntity = new SensitiveWordLogEntity();
			sensitiveWordLogEntity.setStatus(status);
			sensitiveWordLogMapper.update(
				sensitiveWordLogEntity,
				Wrappers.<SensitiveWordLogEntity>update().lambda()
					.eq(SensitiveWordLogEntity::getWord,sensitiveWordEntity.getWord())
					.eq(SensitiveWordLogEntity::getScope,sensitiveWordEntity.getScope()));
			if (status==1){
				saveToRedisForTaskFilter(sensitiveWordEntity,timeScope);
			}

		}
		return R.status(update);
	}


	/**
	 * 将生效范围转化为时间
	 * @param timeScope
	 * @return
	 */
	public Date timeScopeTransDate(int timeScope){
		//如果是生效时间后,那么他的生效时间就是现在
		if (timeScope== SensitiveWordTimeScopeEnum.AFTEREFFECTIVETIME.getCode()){
			return new Date();
		}
		//如果是所有历史
		else if (timeScope== SensitiveWordTimeScopeEnum.ALL.getCode()){
			return new Date(0);
		}
		//否则是一个月时间
		else{
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			return calendar.getTime();
		}
	}


	public int DateTransTimeScope(Date effectiveTime,Date createTime){
		if (effectiveTime==null||createTime==null){
			return 3;
		}
		if (effectiveTime.equals(createTime)) {
			return 0;
		}else if (effectiveTime.getTime() == 0){
			return 2;
		}else {
			return 1;
		}
	}

	/**
	 * 将对象存放到redis中,task任务读取redis中的敏感词进行存量新闻过滤
	 * 只在save和edit方法被调用时生效
	 * @param sensitiveWordEntity
	 * @param timeScope
	 */
	private void saveToRedisForTaskFilter(SensitiveWordEntity sensitiveWordEntity,Integer timeScope){
		if (Objects.equals(timeScope, SensitiveWordTimeScopeEnum.ALL.getCode())
			|| Objects.equals(timeScope, SensitiveWordTimeScopeEnum.LASTMONTH.getCode())){
			SensitiveFilterHistoryVo sensitiveFilterHistoryVo = new SensitiveFilterHistoryVo();
			sensitiveFilterHistoryVo.setWord(sensitiveWordEntity.getWord());
			sensitiveFilterHistoryVo.setSource(sensitiveWordEntity.getEffectiveSource());
			sensitiveFilterHistoryVo.setUpdateDate(new Date());
			sensitiveFilterHistoryVo.setAssetId(sensitiveWordEntity.getAssetId());
			sensitiveFilterHistoryVo.setTimeScope(timeScope);


			//存放map对象
			HashMap<String, SensitiveFilterHistoryVo> storeMap = new HashMap<>();
			Object filterSensitiveWord = zeroRedis.get("filterSensitiveWord");
			if (null!=filterSensitiveWord){
				storeMap = (HashMap<String, SensitiveFilterHistoryVo>) filterSensitiveWord;
			}
			storeMap.put(sensitiveFilterHistoryVo.getWord(),sensitiveFilterHistoryVo);
			zeroRedis.setEx("filterSensitiveWord",storeMap,60L*60L);
		}
	}

	//根据timeScope设置返回的effectiveTime
	private Date setReturnEffectiveTime(Integer status,Integer scope,Date effectiveTime,Date updateTime){
		if (status==0){
			return new Date(2099,1,1);
		}

		//公共
		if (scope==0){
			return effectiveTime;
		}

		//智珠啥时候更新啥时候生效
		else {
			return updateTime;
		}
	}


}
