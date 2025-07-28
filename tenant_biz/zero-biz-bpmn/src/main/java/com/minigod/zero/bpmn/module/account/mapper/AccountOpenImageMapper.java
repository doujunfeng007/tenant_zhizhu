package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenImageEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenImageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Chill
 */
public interface AccountOpenImageMapper extends BaseMapper<AccountOpenImageEntity> {
    List<AccountOpenImageVO> queryListByApplicationId(@Param("applicationId") String applicationId, @Param("imageLocation") Integer imageLocation, @Param("imageLocationType") Integer imageLocationType);
    AccountOpenImageVO queryOneByApplicationId(@Param("applicationId") String applicationId,@Param("imageLocation") Integer imageLocation, @Param("imageLocationType") Integer imageLocationType);

    void deleteByApplicationId(String applicationId);
}
