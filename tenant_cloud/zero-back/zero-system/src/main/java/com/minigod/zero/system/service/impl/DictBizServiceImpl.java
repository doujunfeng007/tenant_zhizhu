package com.minigod.zero.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.*;
import com.minigod.zero.system.cache.LanguageUtils;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.entity.I18nLanguageEntity;
import com.minigod.zero.system.service.IDictBizService;
import com.minigod.zero.common.constant.CommonConstant;
import com.minigod.zero.core.cache.utils.CacheUtil;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.constant.ZeroConstant;
import com.minigod.zero.core.tool.node.ForestNodeMerger;
import com.minigod.zero.system.cache.DictBizCache;
import com.minigod.zero.system.mapper.DictBizMapper;
import com.minigod.zero.system.vo.DictBizVO;
import com.minigod.zero.system.wrapper.DictBizWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.minigod.zero.core.cache.constant.CacheConstant.DICT_CACHE;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Slf4j
@Service
public class DictBizServiceImpl extends ServiceImpl<DictBizMapper, DictBiz> implements IDictBizService {

	@Override
	public List<DictBizVO> tree() {
		return ForestNodeMerger.merge(baseMapper.tree(LanguageUtils.getLanguage()));
	}

	@Override
	public List<DictBizVO> parentTree() {
		return ForestNodeMerger.merge(baseMapper.parentTree(LanguageUtils.getLanguage()));
	}

	@Override
	public String getValue(String code, String dictKey) {
		return Func.toStr(baseMapper.getValue(code, dictKey, LanguageUtils.getLanguage()), StringPool.EMPTY);
	}

	@Override
	public String getValue(String code, String dictKey, String lang) {
		if (Func.isEmpty(lang)) {
			lang = LanguageUtils.getLanguage();
		}else {
			lang = LanguageUtils.localeMap.get(lang);
		}
		return Func.toStr(baseMapper.getValue(code, dictKey, lang), StringPool.EMPTY);
	}

	@Override
	public String getLabel(String code, String dictLabel) {
		return Func.toStr(baseMapper.getLabel(code, dictLabel, LanguageUtils.getLanguage()), StringPool.EMPTY);
	}

	@Override
	public List<DictBiz> getAll() {
		return baseMapper.getAll(LanguageUtils.getLanguage());
	}

	@Override
	public List<DictBiz> getListByTenantId(String tenantId, String code) {
		List<DictBiz> list = baseMapper.getListByTenantId(tenantId, code, LanguageUtils.getLanguage());
		log.info("字典数据：{}", JSONObject.toJSONString(list));
		if (CollectionUtil.isNotEmpty(list)) {
			list = list.stream().filter(dictBiz -> {
					if (dictBiz.getSort() == null) {
						dictBiz.setSort(0);
					}
					return true;
				})
				.sorted(Comparator.comparing(DictBiz::getSort).reversed())
				.collect(java.util.stream.Collectors.toList());
		}
		log.info("字典数据返回：{}", JSONObject.toJSONString(list));
		return list;
	}

	@Override
	public R<List<I18nLanguageEntity>> getLanguageByModelName(String modelName) {

		return null;
	}

	@Override
	public void batchInsert(List<DictBiz> dict) {
		baseMapper.insertBatch(dict);
	}

	@Override
	public List<DictBiz> getList(String code) {
		List<DictBiz> list = baseMapper.getList(code, LanguageUtils.getLanguage());
		if (CollectionUtil.isNotEmpty(list)) {
			list = list.stream().filter(dictBiz -> {
					if (dictBiz.getSort() == null) {
						dictBiz.setSort(0);
					}
					return true;
				})
				.sorted(Comparator.comparing(DictBiz::getSort).reversed())
				.collect(java.util.stream.Collectors.toList());
		}
		return list;
	}

	@Override
	public boolean submit(DictBiz dict) {
		LambdaQueryWrapper<DictBiz> lqw = Wrappers.<DictBiz>query().lambda().eq(DictBiz::getCode, dict.getCode()).eq(DictBiz::getDictKey, dict.getDictKey());
		Long cnt = baseMapper.selectCount((Func.isEmpty(dict.getId())) ? lqw : lqw.notIn(DictBiz::getId, dict.getId()));
		if (cnt > 0L) {
			throw new ServiceException("当前字典键值已存在!");
		}
		// 修改顶级字典后同步更新下属字典的编号
		if (Func.isNotEmpty(dict.getId()) && dict.getParentId().longValue() == ZeroConstant.TOP_PARENT_ID) {
			DictBiz parent = DictBizCache.getById(dict.getId());
			this.update(Wrappers.<DictBiz>update().lambda().set(DictBiz::getCode, dict.getCode()).eq(DictBiz::getCode, parent.getCode()).ne(DictBiz::getParentId, ZeroConstant.TOP_PARENT_ID));
		}
		if (Func.isEmpty(dict.getParentId())) {
			dict.setParentId(ZeroConstant.TOP_PARENT_ID);
		}
		dict.setIsDeleted(ZeroConstant.DB_NOT_DELETED);
		CacheUtil.clear(DICT_CACHE);
		return saveOrUpdate(dict);
	}

	@Override
	public boolean removeDict(String ids) {
		Long cnt = baseMapper.selectCount(Wrappers.<DictBiz>query().lambda().in(DictBiz::getParentId, Func.toLongList(ids)));
		if (cnt > 0L) {
			throw new ServiceException("请先删除子节点!");
		}
		return removeByIds(Func.toLongList(ids));
	}

	@Override
	public IPage<DictBizVO> parentList(Map<String, Object> dict, Query query) {
		IPage<DictBiz> page = this.page(Condition.getPage(query), Condition.getQueryWrapper(dict, DictBiz.class).lambda().eq(DictBiz::getParentId, CommonConstant.TOP_PARENT_ID).orderByAsc(DictBiz::getSort));
		return DictBizWrapper.build().pageVO(page);
	}

	@Override
	public List<DictBizVO> childList(Map<String, Object> dict, Long parentId) {
		if (parentId < 0) {
			return new ArrayList<>();
		}
		dict.remove("parentId");
		DictBiz parentDict = DictBizCache.getById(parentId);
		List<DictBiz> list = this.list(Condition.getQueryWrapper(dict, DictBiz.class).lambda().ne(DictBiz::getId, parentId).eq(DictBiz::getCode, parentDict.getCode()).orderByAsc(DictBiz::getSort));
		return DictBizWrapper.build().listNodeVO(list);
	}

	/**
	 * 清除字典缓存
	 */
	@Override
	public void resetCache() {
		CacheUtil.clear(DICT_CACHE);
	}
}
