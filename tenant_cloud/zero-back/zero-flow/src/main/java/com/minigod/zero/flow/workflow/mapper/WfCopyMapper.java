package com.minigod.zero.flow.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.flow.workflow.domain.WfCopy;
import com.minigod.zero.flow.workflow.domain.bo.WfCopyBo;
import com.minigod.zero.flow.workflow.domain.vo.WfCopyVo;

import java.util.List;

/**
 * 流程抄送Mapper接口
 *
 * @author zsdp
 * @date 2022-05-19
 */
public interface WfCopyMapper extends BaseMapper<WfCopy> {

    Boolean insertBatch(List<WfCopy> copyList);

    Page<WfCopyVo> selectVoPage(IPage pageQuery, WfCopyBo bo);

    WfCopyVo selectVoById(Long id);

    List<WfCopyVo> selectVoList(WfCopyBo bo);
}
