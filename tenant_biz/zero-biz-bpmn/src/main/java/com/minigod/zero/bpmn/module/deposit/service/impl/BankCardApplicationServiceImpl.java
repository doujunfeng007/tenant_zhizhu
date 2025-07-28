package com.minigod.zero.bpmn.module.deposit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minigod.zero.biz.common.enums.BankAccountType;
import com.minigod.zero.biz.common.factorys.ClaimThreadFactory;
import com.minigod.zero.bpmn.module.deposit.bo.BankCardApplicationQuery;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardApplication;
import com.minigod.zero.bpmn.module.deposit.entity.BankCardReviewInfo;
import com.minigod.zero.bpmn.module.deposit.enums.DepositStatusEnum;
import com.minigod.zero.bpmn.module.deposit.mapper.BankCardApplicationMapper;
import com.minigod.zero.bpmn.module.deposit.mapper.BankCardReviewInfoMapper;
import com.minigod.zero.bpmn.module.deposit.service.BankCardApplicationService;
import com.minigod.zero.bpmn.module.deposit.vo.BankCardApplicationVO;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.redis.lock.LockType;
import com.minigod.zero.core.redis.lock.RedisLockClient;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.ObjectUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.flow.core.common.enums.FlowComment;
import com.minigod.zero.flow.core.feign.IFlowClient;
import com.minigod.zero.system.entity.DictBiz;
import com.minigod.zero.system.feign.IDictBizClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

/**
 * @ClassName: BankCardApplicationServiceImpl
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/3/11
 * @Version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class BankCardApplicationServiceImpl extends ServiceImpl<BankCardApplicationMapper, BankCardApplication> implements BankCardApplicationService {
	private final IFlowClient flowClient;
	private IDictBizClient iDictBizClient;
	private final RedisLockClient redisLockClient;
	private final BankCardReviewInfoMapper bankCardReviewInfoMapper;
	private final ExecutorService claimExecutorService = new ThreadPoolExecutor(5, 10,
		0L, TimeUnit.MILLISECONDS,
		new LinkedBlockingQueue<Runnable>(), new ClaimThreadFactory());

	@Override
	public int batchInsert(List<BankCardApplication> list) {
		return baseMapper.batchInsert(list);
	}

	@Override
	public BankCardApplicationVO queryByApplicationId(String applicationId) {
		BankCardApplicationVO applicationVO = baseMapper.queryApplicationId(applicationId);
		if (applicationVO != null) {
			if (applicationVO.getApplicationStatus() != null) {
				applicationVO.setApplicationStatusName(DepositStatusEnum.ApplicationStatus.getApplicationStatus(applicationVO.getApplicationStatus()));
			}
			if (applicationVO.getStatus() != null) {
				applicationVO.setStatusName(DepositStatusEnum.BankCardApplicationStatus.getMessage(applicationVO.getStatus()));
			}
			if (applicationVO.getBeforeInfo() != null && applicationVO.getBeforeInfo().getBankAccountCategory() != null) {
				applicationVO.getBeforeInfo().setBankAccountCategoryName(BankAccountType.getByCode(applicationVO.getBeforeInfo().getBankAccountCategory()).getDesc());
			}
			if (applicationVO.getBeforeInfo() != null && applicationVO.getBeforeInfo().getBankType() != null) {
				applicationVO.getBeforeInfo().setBankTypeName(DepositStatusEnum.BankCardType.getBankCardType(applicationVO.getBeforeInfo().getBankType()));
			}
			if (applicationVO.getReviewInfo() != null && applicationVO.getReviewInfo().getBankAccountCategory() != null) {
				applicationVO.getReviewInfo().setBankAccountCategoryName(BankAccountType.getByCode(applicationVO.getReviewInfo().getBankAccountCategory()).getDesc());
			}
			if (applicationVO.getReviewInfo() != null && applicationVO.getReviewInfo().getBankType() != null) {
				applicationVO.getReviewInfo().setBankTypeName(DepositStatusEnum.BankCardType.getBankCardType(applicationVO.getReviewInfo().getBankType()));
			}
		}
		return applicationVO;
	}

	@Override
	public IPage<BankCardApplicationVO> selectBankCardApplicationPage(IPage<BankCardApplicationVO> page, BankCardApplicationQuery applicationQuery) {
		if (StringUtil.isBlank(applicationQuery.getTenantId()) && !"000000".equals(AuthUtil.getTenantId())) {
			applicationQuery.setTenantId(AuthUtil.getTenantId());
		}
		IPage<BankCardApplicationVO> pages = baseMapper.selectBankCardApplicationPage(page, applicationQuery);
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
			if (s.getApplicationStatus() != null) {
				s.setApplicationStatusName(DepositStatusEnum.ApplicationStatus.getApplicationStatus(s.getApplicationStatus()));
			}
			if (s.getStatus() != null) {
				s.setStatusName(DepositStatusEnum.BankCardApplicationStatus.getMessage(s.getStatus()));
			}
			if (s.getBeforeInfo() != null && s.getBeforeInfo().getBankAccountCategory() != null) {
				s.getBeforeInfo().setBankAccountCategoryName(BankAccountType.getByCode(s.getBeforeInfo().getBankAccountCategory()).getDesc());
			}
			if (s.getBeforeInfo() != null && s.getBeforeInfo().getBankType() != null) {
				s.getBeforeInfo().setBankTypeName(DepositStatusEnum.BankCardType.getBankCardType(s.getBeforeInfo().getBankType()));
			}
			if (s.getReviewInfo() != null && s.getReviewInfo().getBankAccountCategory() != null) {
				s.getReviewInfo().setBankAccountCategoryName(BankAccountType.getByCode(s.getReviewInfo().getBankAccountCategory()).getDesc());
			}
			if (s.getReviewInfo() != null && s.getReviewInfo().getBankType() != null) {
				s.getReviewInfo().setBankTypeName(DepositStatusEnum.BankCardType.getBankCardType(s.getReviewInfo().getBankType()));
			}
		});
		return pages;
	}

	@Override
	public IPage<BankCardApplicationVO> selectBankCardApplicationPage(IPage<BankCardApplicationVO> page) {
		BankCardApplicationQuery query = new BankCardApplicationQuery();
		query.setUserId(AuthUtil.getTenantCustId());
		IPage<BankCardApplicationVO> pages = baseMapper.selectBankCardApplicationPage(page, query);
		// 获取银行卡审核状态
		R<List<DictBiz>> dictStatus = iDictBizClient.getList("bank_card_approve_status");
		if (dictStatus.getData() != null && dictStatus.getData().size() > 0) {
			pages.getRecords().stream().forEach(s -> {
				if (s.getStatus() != null) {
					s.setStatusName(dictStatus.getData().stream().filter(d -> d.getDictKey().equals(s.getStatus().toString())).findFirst().get().getDictValue());
				}
			});
		}
		// 银行卡账户类型
		R<List<DictBiz>> dictAccountTypes = iDictBizClient.getList("bank_account_type");
		if (dictAccountTypes.getData() != null && dictAccountTypes.getData().size() > 0) {
			pages.getRecords().stream().forEach(s -> {
				if (s.getBeforeInfo() != null && s.getBeforeInfo().getBankAccountCategory() != null) {
					s.getBeforeInfo().setBankAccountCategoryName(dictAccountTypes.getData().stream().filter(d -> d.getDictKey().equals(s.getBeforeInfo().getBankAccountCategory().toString())).findFirst().get().getDictValue());
				}
				if (s.getReviewInfo() != null && s.getReviewInfo().getBankAccountCategory() != null) {
					s.getReviewInfo().setBankAccountCategoryName(dictAccountTypes.getData().stream().filter(d -> d.getDictKey().equals(s.getReviewInfo().getBankAccountCategory().toString())).findFirst().get().getDictValue());
				}
			});
		}

		// 获取银行卡类型
		R<List<DictBiz>> dictBankCardTypes = iDictBizClient.getList("client_card_bank_type");
		if (dictBankCardTypes.getData() != null && dictBankCardTypes.getData().size() > 0) {
			pages.getRecords().stream().forEach(s -> {
				if (s.getBeforeInfo() != null && s.getBeforeInfo().getBankType() != null) {
					s.getBeforeInfo().setBankTypeName(dictBankCardTypes.getData().stream().filter(d -> d.getDictKey().equals(s.getBeforeInfo().getBankType().toString())).findFirst().get().getDictValue());
				}
				if (s.getReviewInfo() != null && s.getReviewInfo().getBankType() != null) {
					s.getReviewInfo().setBankTypeName(dictBankCardTypes.getData().stream().filter(d -> d.getDictKey().equals(s.getReviewInfo().getBankType().toString())).findFirst().get().getDictValue());
				}
			});
		}
		return pages;
	}

	private final static String CLAIM_LOCK_KEY = "LOCK:BANKCARD:CLAIM:%s";

	@Override
	public String batchClaim(Integer applicationStatus, List<String> applicationIds) {
		Long userId = AuthUtil.getUserId();
		StringBuilder stringBuilder = new StringBuilder("您申领的流程号:" + applicationIds + "<br/>");
		List<String> failClaim = Collections.synchronizedList(new ArrayList<>());
		List<String> notFindClaim = Collections.synchronizedList(new ArrayList<>());
		List<String> suceessClaim = Collections.synchronizedList(new ArrayList<>());
		LambdaQueryWrapper<BankCardApplication> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(BankCardApplication::getApplicationId, applicationIds);
		queryWrapper.eq(ObjectUtil.isNotEmpty(applicationStatus), BankCardApplication::getApplicationStatus, applicationStatus);
		List<BankCardApplication> applications = baseMapper.selectList(queryWrapper);
		if (applications.size() == 0) {
			throw new ServiceException("所选任务已全被申领");
		}
		List<Future<Boolean>> futures = new ArrayList<>();
		for (BankCardApplication application : applications) {
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
										LambdaUpdateWrapper<BankCardApplication> updateWrapper = new LambdaUpdateWrapper<>();
										updateWrapper.set(BankCardApplication::getAssignDrafter, userId);
										updateWrapper.eq(BankCardApplication::getApplicationId, application.getApplicationId());
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
		if (suceessClaim != null && suceessClaim.size() > 0) {
			stringBuilder.append("成功认领:" + suceessClaim + "<br/>");
		}
		if (failClaim != null && failClaim.size() > 0) {
			stringBuilder.append("失败认领:" + failClaim + "<br/>");
		}
		if (notFindClaim != null && notFindClaim.size() > 0) {
			stringBuilder.append("任务不存在:" + notFindClaim + "<br/>");
		}
		return stringBuilder.toString();
	}

	@Override
	public String batchUnClaim(List<String> applicationIds) {
		LambdaQueryWrapper<BankCardApplication> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.in(BankCardApplication::getApplicationId, applicationIds);
		List<BankCardApplication> applications = baseMapper.selectList(queryWrapper);
		Map<String, Future<Boolean>> applicationFuture = new HashMap<>();
		StringBuilder stringBuilder = new StringBuilder("您取消申领的流程号:" + applicationIds + "<br/>");
		List<String> failClaim = Collections.synchronizedList(new ArrayList<>());
		List<String> successClaim = Collections.synchronizedList(new ArrayList<>());
		for (BankCardApplication application : applications) {
			Future<Boolean> unclaimFuture = claimExecutorService.submit(new Callable<Boolean>() {
				@Override
				public Boolean call() {
					LambdaUpdateWrapper<BankCardApplication> updateWrapper = new LambdaUpdateWrapper<>();
					updateWrapper.set(BankCardApplication::getAssignDrafter, null);
					updateWrapper.eq(BankCardApplication::getApplicationId, application.getApplicationId());
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
		if (successClaim != null && successClaim.size() > 0) {
			stringBuilder.append("成功认领:" + successClaim + "<br/>");
		}
		if (failClaim != null && failClaim.size() > 0) {
			stringBuilder.append("失败认领:" + failClaim + "<br/>");
		}
		return stringBuilder.toString();

	}

	@Override
	public void rejectNode(String applicationId, String instanceId, String reason) {
		R r = flowClient.taskFinish(instanceId, FlowComment.REBACK.getType(), reason);
		if (r.isSuccess()) {
			LambdaUpdateWrapper<BankCardApplication> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.eq(BankCardApplication::getApplicationId, applicationId);
			updateWrapper.set(BankCardApplication::getStatus, DepositStatusEnum.BankCardApplicationStatus.APPROVED.getCode());
			updateWrapper.set(BankCardApplication::getApprovalOpinion, reason);
			updateWrapper.set(BankCardApplication::getLastApprovalUser, AuthUtil.getUserName());
			updateWrapper.set(BankCardApplication::getUpdateTime, new Date());
			baseMapper.update(null, updateWrapper);
			LambdaUpdateWrapper<BankCardReviewInfo> reviewInfoUpdateWrapper = new LambdaUpdateWrapper<>();
			reviewInfoUpdateWrapper.eq(BankCardReviewInfo::getApplicationId, applicationId);
			reviewInfoUpdateWrapper.set(BankCardReviewInfo::getIsFinish, 1);
			bankCardReviewInfoMapper.update(null, reviewInfoUpdateWrapper);
		} else {
			throw new ServiceException(r.getMsg());
		}
	}

	@Override
	public void passNode(String applicationId, String taskId, String reason) {
		R r = flowClient.completeTask(taskId, reason, new HashMap<>());
		if (r.isSuccess()) {
			LambdaUpdateWrapper<BankCardApplication> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.eq(BankCardApplication::getApplicationId, applicationId);
			updateWrapper.set(BankCardApplication::getApprovalOpinion, reason);
			updateWrapper.set(BankCardApplication::getLastApprovalUser, AuthUtil.getUserName());
			updateWrapper.set(BankCardApplication::getUpdateTime, new Date());
			baseMapper.update(null, updateWrapper);
		} else {
			throw new ServiceException(r.getMsg());
		}
	}

	@Override
	public List<BankCardApplication> queryListByNode(String node) {
		return baseMapper.queryListByNode(node);
	}
}
