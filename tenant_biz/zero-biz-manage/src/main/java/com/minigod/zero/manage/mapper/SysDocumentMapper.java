package com.minigod.zero.manage.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.SysDocumentEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 平台文档Mapper接口
 *
 * @author jim
 * @date 2020-05-27
 */
public interface SysDocumentMapper
{
    /**
     * 查询平台文档
     *
     * @param id 平台文档ID
     * @return 平台文档
     */
    public SysDocumentEntity selectSysDocumentById(@Param("id") Long id);

    /**
     * 查询平台文档列表
     *
     * @return 平台文档集合
     */
    public List<SysDocumentEntity> selectSysDocumentList( @Param("page")IPage page, @Param("params") SysDocumentEntity sysDocument);

    /**
     * 新增平台文档
     *
     * @param sysDocument 平台文档
     * @return 结果
     */
    public int insertSysDocument(SysDocumentEntity sysDocument);

    /**
     * 修改平台文档
     *
     * @param sysDocument 平台文档
     * @return 结果
     */
    public int updateSysDocument(SysDocumentEntity sysDocument);

    /**
     * 删除平台文档
     *
     * @param id 平台文档ID
     * @return 结果
     */
    public int deleteSysDocumentById(Integer id);

    /**
     * 批量删除平台文档
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysDocumentByIds(Integer[] ids);

	/**
	 * 根据key查询
	 * @param docKey
	 * @return
	 */
	SysDocumentEntity findDocumentByKey(@Param("docKey") String docKey);
}
