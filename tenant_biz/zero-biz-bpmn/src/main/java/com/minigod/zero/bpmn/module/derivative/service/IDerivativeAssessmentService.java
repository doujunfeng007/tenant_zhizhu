package com.minigod.zero.bpmn.module.derivative.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.derivative.dto.DerivativeAssessmentDTO;
import com.minigod.zero.bpmn.module.derivative.query.DerivativeAssessmentQuery;
import com.minigod.zero.bpmn.module.derivative.query.DerivativeUserRecordQuery;
import com.minigod.zero.bpmn.module.derivative.vo.DerivativeAssessmentVO;
import com.minigod.zero.bpmn.module.derivative.vo.DerivativeUserRecordVO;

import java.util.List;

/**
 * 衍生品及结构性投资产品的认识评估服务
 *
 * @author eric
 * @since 2024-06-20 17:46:55
 */
public interface IDerivativeAssessmentService {
	/**
	 * 批量新增
	 *
	 * @param assessmentDTOS
	 * @return
	 */
	int batchInsert(List<DerivativeAssessmentDTO> assessmentDTOS);

	/**
	 * 根据客户号删除衍生品及结构性投资产品的认识评估结果
	 *
	 * @param custId
	 * @return
	 */
	int deleteByCustId(Long custId);

	/**
	 * 根据客户号查询衍生品及结构性投资产品的认识评估结果
	 *
	 * @param custId
	 * @return
	 */
	List<DerivativeAssessmentVO> selectByCustId(Long custId);

	/**
	 * 根据主键查询
	 *
	 * @param id
	 * @return
	 */
	DerivativeAssessmentVO selectByPrimaryKey(Long id);

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param assessmentQuery
	 * @return
	 */
	IPage<DerivativeAssessmentVO> selectByPage(IPage<DerivativeAssessmentVO> page, DerivativeAssessmentQuery assessmentQuery);

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param recordQuery
	 * @return
	 */
	IPage<DerivativeUserRecordVO> selectByPage(IPage<DerivativeUserRecordVO> page, DerivativeUserRecordQuery recordQuery);

	/**
	 * 根据主键更新
	 *
	 * @param assessmentDTO
	 * @return
	 */
	int updateByPrimaryKeySelective(DerivativeAssessmentDTO assessmentDTO);

	/**
	 * 根据主键更新
	 *
	 * @param assessmentDTO
	 * @return
	 */
	int updateByPrimaryKey(DerivativeAssessmentDTO assessmentDTO);

	/**
	 * 根据主键删除
	 *
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 新增
	 *
	 * @param assessmentDTO
	 * @return
	 */
	int insert(DerivativeAssessmentDTO assessmentDTO);

	/**
	 * 根据主键新增
	 *
	 * @param assessmentDTO
	 * @return
	 */
	int insertSelective(DerivativeAssessmentDTO assessmentDTO);
}
