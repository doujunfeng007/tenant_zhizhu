package com.minigod.zero.bpmn.module.fundTrans.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransApplication;
import com.minigod.zero.bpmn.module.fundTrans.domain.bo.ClientFundTransApplicationQuery;
import com.minigod.zero.bpmn.module.fundTrans.domain.vo.ClientFundTransApplicationVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName: ClientFundTransApplicationMapper
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/12/11
 * @Version 1.0
 */
@Mapper
public interface ClientFundTransApplicationMapper extends BaseMapper<ClientFundTransApplication> {
    IPage<ClientFundTransApplicationVO> selectFundTransApplicationPage(IPage<ClientFundTransApplicationVO> page, @Param("bo") ClientFundTransApplicationQuery query);

    ClientFundTransApplicationVO queryApplicationId(String applicationId);
}
