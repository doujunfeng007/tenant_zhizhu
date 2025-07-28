package com.minigod.zero.bpmn.module.derivative.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.derivative.entity.DerivativeAssessmentEntity;
import com.minigod.zero.bpmn.module.derivative.query.DerivativeAssessmentQuery;
import com.minigod.zero.bpmn.module.derivative.query.DerivativeUserRecordQuery;
import com.minigod.zero.bpmn.module.derivative.vo.DerivativeUserRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dell
 * @description 针对表【derivative_knowledge_assessment(衍生品及结构性投资产品的认识评估)】的数据库操作Mapper
 * @createDate 2024-06-20 17:23:24
 * @Entity DerivativeAssessmentEntity
 */
@Mapper
public interface DerivativeAssessmentMapper {
	int deleteByPrimaryKey(Long id);

	int deleteByCustId(Long custId);

	int insert(DerivativeAssessmentEntity assessmentEntity);

	int insertSelective(DerivativeAssessmentEntity assessmentEntity);

	int batchInsert(List<DerivativeAssessmentEntity> assessmentEntities);

	DerivativeAssessmentEntity selectByPrimaryKey(Long id);

	List<DerivativeAssessmentEntity> selectByCustId(Long custId);

	IPage<DerivativeAssessmentEntity> selectByPage(IPage page, @Param("query") DerivativeAssessmentQuery assessmentQuery);

	IPage<DerivativeUserRecordVO> selectDerivativeUserRecordByPage(IPage page, @Param("query") DerivativeUserRecordQuery recordQuery);

	int updateByPrimaryKeySelective(DerivativeAssessmentEntity assessmentEntity);

	int updateByPrimaryKey(DerivativeAssessmentEntity assessmentEntity);
}
