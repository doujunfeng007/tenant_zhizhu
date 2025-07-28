package com.minigod.zero.bpmn.module.margincredit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.margincredit.entity.IncreaseCreditLimitApplicationEntity;
import com.minigod.zero.bpmn.module.margincredit.vo.IncreaseCreditLimitApplicationVO;
import com.minigod.zero.bpmn.module.margincredit.vo.req.IncreaseCreditQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName IncreaseCreditLimitApplicationMapper.java
 * @author chen
 * @Description TODO
 * @createTime 2024年03月09日 17:24:00
 */
public interface IncreaseCreditLimitApplicationMapper extends BaseMapper<IncreaseCreditLimitApplicationEntity> {

	IPage<IncreaseCreditLimitApplicationVO> queryPageList(IPage  page, @Param("query")IncreaseCreditQuery query);

	int addAssignDrafter(@Param("applicationId") String applicationId, @Param("userId") String userId);

	int clearAssignDrafter(@Param("applicationId") String applicationId);

	IncreaseCreditLimitApplicationEntity queryByApplicationId(@Param("applicationId")String applicationId);

    List<IncreaseCreditLimitApplicationEntity> queryListByStatus(@Param("custId") Long custId,
																 @Param("applicationStatus") List<Integer> applicationStatus);
}
