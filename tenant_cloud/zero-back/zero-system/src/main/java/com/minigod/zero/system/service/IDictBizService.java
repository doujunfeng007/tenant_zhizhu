
package com.minigod.zero.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.entity.I18nLanguageEntity;
import com.minigod.zero.system.vo.DictBizVO;

import java.util.List;
import java.util.Map;

/**
 * 服务类
 *
 * @author Chill
 */
public interface IDictBizService extends IService<DictBiz> {

	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<DictBizVO> tree();

	/**
	 * 树形结构
	 *
	 * @return
	 */
	List<DictBizVO> parentTree();

	/**
	 * 获取字典表对应中文
	 *
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @return
	 */
	String getValue(String code, String dictKey);
	/**
	 * 获取字典表对应中文
	 *
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @param lang    语言
	 * @return
	 */
	String getValue(String code, String dictKey, String lang);

	/**
	 * 获取字典表
	 *
	 * @param code 字典编号
	 * @return
	 */
	List<DictBiz> getList(String code);

	/**
	 * 新增或修改
	 *
	 * @param dict
	 * @return
	 */
	boolean submit(DictBiz dict);

	/**
	 * 删除字典
	 *
	 * @param ids
	 * @return
	 */
	boolean removeDict(String ids);

	/**
	 * 顶级列表
	 *
	 * @param dict
	 * @param query
	 * @return
	 */
	IPage<DictBizVO> parentList(Map<String, Object> dict, Query query);

	/**
	 * 子列表
	 *
	 * @param dict
	 * @param parentId
	 * @return
	 */
	List<DictBizVO> childList(Map<String, Object> dict, Long parentId);

    String getLabel(String code, String dictLabel);
	List<DictBiz> getAll();

	List<DictBiz> getListByTenantId(String tenantId, String code);

	R<List<I18nLanguageEntity>> getLanguageByModelName(String modelName);

	void batchInsert(List<DictBiz> dictList);

	/**
	 * 清除字典缓存
	 */
	void resetCache();

}
