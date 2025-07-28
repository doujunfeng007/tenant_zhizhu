package com.minigod.zero.flow.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.flow.workflow.domain.WfCategory;
import com.minigod.zero.flow.workflow.domain.bo.WfCategoryBo;
import com.minigod.zero.flow.workflow.domain.vo.WfCategoryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 流程分类Mapper接口
 *
 * @author zsdp
 * @date 2022-01-15
 */
public interface WfCategoryMapper extends BaseMapper<WfCategory> {

    WfCategoryVo selectVoById(Long categoryId);

    Page<WfCategoryVo> selectVoPage(IPage pageQuery,@Param("bo") WfCategoryBo bo);

    List<WfCategoryVo> selectVoList(@Param("bo") WfCategoryBo bo);
}
