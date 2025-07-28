package com.minigod.zero.flow.workflow.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.flow.workflow.domain.WfCopy;
import com.minigod.zero.flow.workflow.domain.bo.WfCopyBo;
import com.minigod.zero.flow.workflow.domain.bo.WfTaskBo;
import com.minigod.zero.flow.workflow.domain.vo.WfCopyVo;
import com.minigod.zero.flow.workflow.mapper.WfCopyMapper;
import com.minigod.zero.flow.workflow.service.IWfCopyService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程抄送Service业务层处理
 *
 * @author zsdp
 * @date 2022-05-19
 */
@RequiredArgsConstructor
@Service
public class WfCopyServiceImpl implements IWfCopyService {

    private final WfCopyMapper baseMapper;

    private final HistoryService historyService;

    /**
     * 查询流程抄送
     *
     * @param copyId 流程抄送主键
     * @return 流程抄送
     */
    @Override
    public WfCopyVo queryById(Long copyId){
        return baseMapper.selectVoById(copyId);
    }

    /**
     * 查询流程抄送列表
     *
     * @param bo 流程抄送
     * @return 流程抄送
     */
    @Override
    public IPage<WfCopyVo> queryPageList(WfCopyBo bo, IPage pageQuery) {

        Page<WfCopyVo> result = baseMapper.selectVoPage(pageQuery, bo);
        return result;
    }

    /**
     * 查询流程抄送列表
     *
     * @param bo 流程抄送
     * @return 流程抄送
     */
    @Override
    public List<WfCopyVo> queryList(WfCopyBo bo) {
        return baseMapper.selectVoList(bo);
    }



    @Override
    public Boolean makeCopy(WfTaskBo taskBo) {
        if (StringUtils.isBlank(taskBo.getCopyUserIds())) {
            // 若抄送用户为空，则不需要处理，返回成功
            return true;
        }
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
            .processInstanceId(taskBo.getInstanceId()).singleResult();
        String[] ids = taskBo.getCopyUserIds().split(",");
        List<WfCopy> copyList = new ArrayList<>(ids.length);
        Long originatorId = AuthUtil.getUserId();
        String originatorName = AuthUtil.getNickName();
        String title = historicProcessInstance.getProcessDefinitionName() + "-" + taskBo.getTaskName();
        for (String id : ids) {
            Long userId = Long.valueOf(id);
            WfCopy copy = new WfCopy();
            copy.setTitle(title);
            copy.setProcessId(historicProcessInstance.getProcessDefinitionId());
            copy.setProcessName(historicProcessInstance.getProcessDefinitionName());
            copy.setDeploymentId(historicProcessInstance.getDeploymentId());
            copy.setInstanceId(taskBo.getInstanceId());
            copy.setTaskId(taskBo.getTaskId());
            copy.setUserId(userId);
            copy.setOriginatorId(originatorId);
            copy.setOriginatorName(originatorName);
			copy.setTenantId(AuthUtil.getTenantId());
            copyList.add(copy);
        }
        return baseMapper.insertBatch(copyList);
    }
}
