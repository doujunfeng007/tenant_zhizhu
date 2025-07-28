package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Chill
 */
public interface AccountOpenInfoMapper extends BaseMapper<AccountOpenInfoEntity> {

    AccountOpenInfoVO queryByApplicationId(@Param("applicationId") String applicationId);

	AccountOpenInfoVO queryByEmail(@Param("email") String email, @Param("userId") Long userId);

	AccountOpenInfoVO queryByIdNo(@Param("idNo") String idNo, @Param("userId") Long userId);

	AccountOpenInfoVO queryByClientId(@Param("clientId") String clientId);

    AccountOpenInfoVO queryByUserId(@Param("userId") Long userId);

    void updateClientId(@Param("applicationId") String applicationId,@Param("clientId") String clientId);

    List<String> selectByClientByIdKind(@Param("idKind") Integer idKind);

    void deleteByApplicationId(String applicationId);

	List<AccountOpenInfoEntity> queryList(IPage page, @Param("keyword")String keyword,
										  @Param("startTime")String startTime,
										  @Param("endTime")String endTime);

	AccountOpenInfoEntity openAccountUserDetail(Long userId);

	int updateOpenAccountSuccess(@Param("applicationId") String applicationId,@Param("clientId") String clientId);

	List<String> queryW8ApplicationId(@Param("fileType") Integer fileType);


	List<AccountOpenInfoEntity> selW8ConfirmList(@Param("year") int w8Year);

	List<AccountOpenInfoEntity> selSelfConfirmList(@Param("year") int selfYear);

	AccountOpenInfoVO queryByAppId(@Param("applicationId") String applicationId);
}
