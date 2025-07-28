
package com.minigod.zero.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.vo.DictBizVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author Chill
 */
public interface DictBizMapper extends BaseMapper<DictBiz> {

	/**
	 * 获取字典表对应中文
	 *
	 * @param code     字典编号
	 * @param dictKey  字典序号
	 * @return
	 */
	String getValue(String code, String dictKey,String language);

	/**
	 * 获取字典表
	 *
	 * @param code 字典编号
	 * @return
	 */
	List<DictBiz> getList(String code,String language);

	/**
	 * 获取树形节点
	 *
	 * @return
	 */
	List<DictBizVO> tree(String language);

	/**
	 * 获取树形节点
	 *
	 * @return
	 */
	List<DictBizVO> parentTree(String language);

	/**
	 * 获取字典表对应的值
	 *
	 * @param code
	 * @param dictLabel
	 * @return
	 */
	String getLabel(String code, String dictLabel,String language);

	/**
	 * 获取所有可用配置
	 *
	 * @return
	 */
	List<DictBiz> getAll(String language);

    List<DictBiz> getListByTenantId(String tenantId, String code,String language);


	void insertBatch(@Param("dictList") List<DictBiz> dictList);
}
