package com.minigod.zero.flow.workflow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.flow.workflow.domain.bo.WfCopyBo;
import com.minigod.zero.flow.workflow.domain.bo.WfTaskBo;
import com.minigod.zero.flow.workflow.domain.vo.WfCopyVo;

import java.util.List;

/**
 * 流程抄送Service接口
 *
 * @author zsdp
 * @date 2022-05-19
 */
public interface IWfCopyService {

    /**
     * 查询流程抄送
     *
     * @param copyId 流程抄送主键
     * @return 流程抄送
     */
    WfCopyVo queryById(Long copyId);

    /**
     * 查询流程抄送列表
     *
     * @param wfCopy 流程抄送
     * @return 流程抄送集合
     */
    IPage<WfCopyVo> queryPageList(WfCopyBo wfCopy, IPage pageQuery);

    /**
     * 查询流程抄送列表
     *
     * @param wfCopy 流程抄送
     * @return 流程抄送集合
     */
    List<WfCopyVo> queryList(WfCopyBo wfCopy);

    /**
     * 抄送
     * @param taskBo
     * @return
     */
    Boolean makeCopy(WfTaskBo taskBo);
}
