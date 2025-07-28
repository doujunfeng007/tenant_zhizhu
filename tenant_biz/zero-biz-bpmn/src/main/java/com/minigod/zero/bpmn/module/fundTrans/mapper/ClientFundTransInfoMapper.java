package com.minigod.zero.bpmn.module.fundTrans.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.fundTrans.domain.ClientFundTransInfo;
import com.minigod.zero.bpmn.module.fundTrans.domain.bo.FundTransQuery;
import com.minigod.zero.bpmn.module.fundTrans.domain.vo.ClientFundTransInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName: ClientFundTransInfoMapper
 * @Description: ${description}
 * @Author chenyu
 * @Date 2024/12/11
 * @Version 1.0
 */
@Mapper
public interface ClientFundTransInfoMapper extends BaseMapper<ClientFundTransInfo> {

    IPage<ClientFundTransInfoVO> queryInfoPage(IPage<ClientFundTransInfoVO> page, @Param("bo") FundTransQuery bo);
}
