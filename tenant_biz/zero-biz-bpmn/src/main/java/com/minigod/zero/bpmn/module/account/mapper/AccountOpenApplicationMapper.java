package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.account.bo.query.ApplicationQuery;
import com.minigod.zero.bpmn.module.account.bo.query.BackApplicationQuery;
import com.minigod.zero.bpmn.module.account.bo.query.CABankVerifyQuery;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenApplicationEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountBackInfoVO;
import com.minigod.zero.bpmn.module.account.vo.AccountCABankVerifyInfoVO;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenApplicationVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Chill
 */
public interface AccountOpenApplicationMapper extends BaseMapper<AccountOpenApplicationEntity> {

    IPage<AccountOpenApplicationVO> queryPageList(IPage  page, @Param("query") ApplicationQuery query);

	IPage<AccountBackInfoVO> queryBackPageList(IPage  page, @Param("query") BackApplicationQuery query);

	IPage<AccountCABankVerifyInfoVO> queryCABankVerifyInfoPageList(IPage  page, @Param("query") CABankVerifyQuery query);

    AccountOpenApplicationVO queryByApplicationId(String applicationId);

    List<AccountOpenApplicationEntity> queryListByNode(String node);

    List<AccountOpenApplicationEntity> selectApplicationIds(@Param("applicationIds") List<String> applicationIds, @Param("applicationStatus") Integer applicationStatus);

    int clearAssignDrafter(@Param("applicationId") String applicationId);

    int addAssignDrafter(@Param("applicationId") String applicationId, @Param("userId") String userId);

    void updateRefKeyByApplicationId(@Param("applicationId") String applicationId, @Param("refKey") String refKey, @Param("status") String status);

    List<AccountOpenApplicationEntity> queryAmlChecking();

    int updateBlackListStatus(@Param("applicationId") String applicationId, @Param("blacklist") Integer blacklist, @Param("reason") String reason);

}
