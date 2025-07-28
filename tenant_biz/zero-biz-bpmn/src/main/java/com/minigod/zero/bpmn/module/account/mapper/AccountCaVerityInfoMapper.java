package com.minigod.zero.bpmn.module.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.account.bo.query.CaQuery;
import com.minigod.zero.bpmn.module.account.entity.AccountCaVerityInfoEntity;
import com.minigod.zero.bpmn.module.account.vo.AccountCaVerityInfoVO;
import org.apache.ibatis.annotations.Param;

/**
 *  Mapper 接口
 *
 * @author Chill
 */
public interface AccountCaVerityInfoMapper extends BaseMapper<AccountCaVerityInfoEntity> {
    AccountCaVerityInfoVO queryByApplicationId(@Param("applicationId") String applicationId);

    IPage<AccountCaVerityInfoVO> queryPageList(IPage<Object> page, @Param("bo") CaQuery caQuery);
}
