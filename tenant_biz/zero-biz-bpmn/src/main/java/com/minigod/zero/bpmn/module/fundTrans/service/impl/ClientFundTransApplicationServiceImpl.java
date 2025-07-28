package com.minigod.zero.bpmn.module.fundTrans.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.biz.common.factorys.ClaimThreadFactory;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardReviewInfo;
import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import com.minigod.zero.bpmn.module.fundTrans.domain.bo.ClientFundTransApplicationQuery;
import com.minigod.zero.bpmn.module.fundTrans.domain.vo.ClientFundTransApplicationVO;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.redis.lock.LockType;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.flow.core.common.enums.FlowComment;
import com.minigod.zero.flow.core.feign.IFlowClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransApplication;
import com.minigod.zero.bpmn.module.fundTrans.mapper.ClientFundTransApplicationMapper;
import com.minigod.zero.bpmn.module.fundTrans.service.ClientFundTransApplicationService;

import java.util.*;
import java.util.concurrent.*;

/**
*@ClassName: ClientFundTransApplicationServiceImpl
*@Description: ${description}
*@Author chenyu
*@Date 2024/12/11
*@Version 1.0
*
*/
@Slf4j
@Service
@AllArgsConstructor
public class ClientFundTransApplicationServiceImpl extends ServiceImpl<ClientFundTransApplicationMapper,ClientFundTransApplication> implements ClientFundTransApplicationService{
    private final RedisLockClient redisLockClient ;
    private final IFlowClient flowClient ;
    private final ClientFundTransApplicationMapper baseMapper;

    private final ExecutorService claimExecutorService = new ThreadPoolExecutor(5, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(), new ClaimThreadFactory());

    @Override
    public ClientFundTransApplicationVO queryByApplicationId(String applicationId) {
        ClientFundTransApplicationVO applicationVO = baseMapper.queryApplicationId(applicationId);
        return applicationVO;
    }

    @Override
    public IPage<ClientFundTransApplicationVO> selectFundTransApplicationPage(IPage<ClientFundTransApplicationVO> page, ClientFundTransApplicationQuery applicationQuery) {
        if (StringUtil.isBlank(applicationQuery.getTenantId()) && !"000000".equals(AuthUtil.getTenantId())) {
            applicationQuery.setTenantId(AuthUtil.getTenantId());
        }
        IPage<ClientFundTransApplicationVO> pages = baseMapper.selectFundTransApplicationPage(page, applicationQuery);
        pages.getRecords().stream().forEach(s -> {
            if (StringUtils.isBlank(s.getAssignDrafter())) {
                s.setDealPermissions(1);
            } else {
                if (s.getAssignDrafter().equals(String.valueOf(AuthUtil.getUserId()))) {
                    s.setDealPermissions(1);
                } else {
                    s.setDealPermissions(0);
                }
            }
        });
        return pages;
    }

    @Override
    public IPage<ClientFundTransApplicationVO> selectFundTransApplicationPage(IPage<ClientFundTransApplicationVO> page) {
        ClientFundTransApplicationQuery query = new ClientFundTransApplicationQuery();
        query.setUserId(AuthUtil.getTenantCustId());
        return baseMapper.selectFundTransApplicationPage(page, query);
    }

    private final static String CLAIM_LOCK_KEY = "LOCK:FUNDTRANS:CLAIM:%s";

    @Override
    public String batchClaim(Integer applicationStatus, List<String> applicationIds) {
        Long userId = AuthUtil.getUserId();
        StringBuilder stringBuilder = new StringBuilder("您申领的流程号:" + applicationIds + "<br/>");
        List<String> failClaim = Collections.synchronizedList(new ArrayList<>());
        List<String> notFindClaim = Collections.synchronizedList(new ArrayList<>());
        List<String> suceessClaim = Collections.synchronizedList(new ArrayList<>());
        LambdaQueryWrapper<ClientFundTransApplication> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ClientFundTransApplication::getApplicationId, applicationIds);
        queryWrapper.eq(ObjectUtil.isNotEmpty(applicationStatus), ClientFundTransApplication::getApplicationStatus, applicationStatus);
        List<ClientFundTransApplication> applications = baseMapper.selectList(queryWrapper);
        if (applications.size() == 0) {
            throw new ServiceException("所选任务已全被申领");
        }
        List<Future<Boolean>> futures = new ArrayList<>();
        for (ClientFundTransApplication application : applications) {
            Future<Boolean> future = claimExecutorService.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    String lockKey = String.format(CLAIM_LOCK_KEY, application.getApplicationId());
                    Boolean lockStatus = false;
                    try {
                        lockStatus = redisLockClient.tryLock(lockKey, LockType.REENTRANT, 1, 1, TimeUnit.SECONDS);
                        if (lockStatus) {
                            if (application.getAssignDrafter() == null) {
                                try {
                                    R r = flowClient.taskClaim(application.getTaskId());
                                    if (r.isSuccess()) {
                                        LambdaUpdateWrapper<ClientFundTransApplication> updateWrapper = new LambdaUpdateWrapper<>();
                                        updateWrapper.set(ClientFundTransApplication::getAssignDrafter, userId);
                                        updateWrapper.eq(ClientFundTransApplication::getApplicationId, application.getApplicationId());
                                        baseMapper.update(null, updateWrapper);
                                        log.info("成功认领:" + application.getApplicationId() + "<br/>" + "任务ID:" + application.getTaskId());
                                        suceessClaim.add(application.getApplicationId());
                                    } else {
                                        log.error(String.format("流程:%s 认领任务失败,原因: %s", application.getApplicationId(), r.getMsg()));
                                        failClaim.add(application.getApplicationId());
                                    }
                                } catch (Exception e) {
                                    log.error(String.format("流程:%s 认领任务异常,原因: %s", application.getApplicationId(), e.getMessage()), e);
                                    failClaim.add(application.getApplicationId());
                                }
                            } else if (!application.getApplicationStatus().equals(applicationStatus)) {
                                log.error(String.format("流程:%s 认领任务失败,原因: 任务状态和当前状态不匹配,任务状态：%s, 当前申领状态：%s", application.getApplicationId(), application.getApplicationStatus(), applicationStatus));
                                notFindClaim.add(application.getApplicationId());
                            } else {
                                log.error(String.format("流程:%s 认领任务失败,原因: 不满足认领条件!", application.getApplicationId()));
                                failClaim.add(application.getApplicationId());
                            }
                        } else {
                            log.error(String.format("流程:%s 认领任务失败,原因: 当前锁还没释放!", application.getApplicationId()));
                            failClaim.add(application.getApplicationId());
                        }
                    } catch (InterruptedException e) {
                        log.error(String.format("租户:%s 用户:%s 流程记录:%s 加锁失败, 原因:%s", AuthUtil.getTenantId(), AuthUtil.getUserId(), application.getApplicationId(), e.getMessage()), e);
                    } catch (Exception e) {
                        log.error(String.format("流程:%s 认领任务异常,原因: %s", application.getApplicationId(), e.getMessage()), e);
                    } finally {
                        if (lockStatus) {
                            redisLockClient.unLock(lockKey, LockType.REENTRANT);
                        }
                    }
                    return Boolean.TRUE;
                }
            });
            futures.add(future);
        }
        for (Future future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        stringBuilder.append("成功认领:" + suceessClaim + "<br/>");
        stringBuilder.append("失败认领:" + failClaim + "<br/>");
        stringBuilder.append("任务不存在:" + notFindClaim + "<br/>");
        return stringBuilder.toString();
    }

    @Override
    public String batchUnClaim(List<String> applicationIds) {
        LambdaQueryWrapper<ClientFundTransApplication> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ClientFundTransApplication::getApplicationId, applicationIds);
        List<ClientFundTransApplication> applications = baseMapper.selectList(queryWrapper);
        Map<String, Future<Boolean>> applicationFuture = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder("您取消申领的流程号:" + applicationIds + "<br/>");
        List<String> failClaim = Collections.synchronizedList(new ArrayList<>());
        List<String> successClaim = Collections.synchronizedList(new ArrayList<>());
        for (ClientFundTransApplication application : applications) {
            Future<Boolean> unclaimFuture = claimExecutorService.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() {
                    LambdaUpdateWrapper<ClientFundTransApplication> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.set(ClientFundTransApplication::getAssignDrafter, null);
                    updateWrapper.eq(ClientFundTransApplication::getApplicationId, application.getApplicationId());
                    baseMapper.update(null, updateWrapper);
                    R r = flowClient.taskUnclaim(application.getTaskId());
                    if (!r.isSuccess()) {
                        log.error(String.format("流程：%s 取消认领失败: %s", application.getApplicationId(), r.getMsg()));
                        failClaim.add(application.getApplicationId());
                        return Boolean.FALSE;
                    } else {
                        successClaim.add(application.getApplicationId());
                        log.info(String.format("流程：%s 取消认领成功", application.getApplicationId()));
                        return Boolean.TRUE;
                    }
                }
            });
            applicationFuture.put(application.getApplicationId(), unclaimFuture);
        }
        Map<String, Boolean> unclaimResult = new HashMap<>();
        for (String applicationId : applicationFuture.keySet()) {
            try {
                unclaimResult.put(applicationId, applicationFuture.get(applicationId).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        stringBuilder.append("成功取消认领:" + successClaim + "<br/>");
        stringBuilder.append("失败取消认领:" + failClaim + "<br/>");
        return stringBuilder.toString();

    }

    @Override
    public void rejectNode(String applicationId, String instanceId, String reason) {
        R r = flowClient.taskFinish(instanceId, FlowComment.REBACK.getType(), reason);
        if (r.isSuccess()) {
            LambdaUpdateWrapper<ClientFundTransApplication> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(ClientFundTransApplication::getApplicationId, applicationId);
            updateWrapper.set(ClientFundTransApplication::getStatus, DepositStatusEnum.FundTransStatus.APPROVED.getCode());
            updateWrapper.set(ClientFundTransApplication::getApprovalOpinion, reason);
            updateWrapper.set(ClientFundTransApplication::getLastApprovalUser, AuthUtil.getUserName());
            updateWrapper.set(ClientFundTransApplication::getUpdateTime, new Date());
            baseMapper.update(null, updateWrapper);
        } else {
            throw new ServiceException(r.getMsg());
        }
    }

    @Override
    public void passNode(String applicationId, String taskId, String reason) {
        R r = flowClient.completeTask(taskId, reason, new HashMap<>());
        if (r.isSuccess()) {
            LambdaUpdateWrapper<ClientFundTransApplication> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(ClientFundTransApplication::getApplicationId, applicationId);
            updateWrapper.set(ClientFundTransApplication::getApprovalOpinion, reason);
            updateWrapper.set(ClientFundTransApplication::getStatus, DepositStatusEnum.FundTransStatus.PENDING_AUDIT.getCode());
            updateWrapper.set(ClientFundTransApplication::getLastApprovalUser, AuthUtil.getUserName());
            updateWrapper.set(ClientFundTransApplication::getUpdateTime, new Date());
            baseMapper.update(null, updateWrapper);
        } else {
            throw new ServiceException(r.getMsg());
        }
    }

    @Override
    public List<ClientFundTransApplication> queryListByNode(String node) {
        LambdaQueryWrapper<ClientFundTransApplication> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ClientFundTransApplication::getCurrentNode, node);
        return baseMapper.selectList(lambdaQueryWrapper);
    }

}
