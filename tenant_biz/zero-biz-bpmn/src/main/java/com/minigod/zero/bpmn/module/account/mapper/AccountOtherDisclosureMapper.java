package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minigod.zero.bpmn.module.account.entity.AccountOtherDisclosureEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountOtherDisclosureVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author Chill
 */
public interface AccountOtherDisclosureMapper extends BaseMapper<AccountOtherDisclosureEntity> {
    List<AccountOtherDisclosureVO> queryListByApplicationId(@Param("applicationId") String applicationId);

}
