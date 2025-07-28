package com.minigod.zero.bpmn.module.derivative.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minigod.zero.bpmn.module.derivative.dto.DerivativeAssessmentDTO;
import com.minigod.zero.bpmn.module.derivative.entity.DerivativeAssessmentEntity;
import com.minigod.zero.bpmn.module.derivative.mapper.DerivativeAssessmentMapper;
import com.minigod.zero.bpmn.module.derivative.query.DerivativeAssessmentQuery;
import com.minigod.zero.bpmn.module.derivative.query.DerivativeUserRecordQuery;
import com.minigod.zero.bpmn.module.derivative.service.IDerivativeAssessmentService;
import com.minigod.zero.bpmn.module.derivative.vo.DerivativeAssessmentVO;
import com.minigod.zero.bpmn.module.derivative.vo.DerivativeUserRecordVO;
import com.minigod.zero.core.secure.utils.AuthUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 衍生品及结构性投资产品的认识评估
 *
 * @author eric
 * @date 2024-06-20 17:48:25
 */
@AllArgsConstructor
@Slf4j
@Service
public class DerivativeAssessmentServiceImpl implements IDerivativeAssessmentService {
	private final DerivativeAssessmentMapper derivativeAssessmentMapper;

	/**
	 * 批量新增
	 *
	 * @param assessmentDTOS
	 * @return
	 */
	@Override
	public int batchInsert(List<DerivativeAssessmentDTO> assessmentDTOS) {
		List<DerivativeAssessmentEntity> assessmentEntities = new ArrayList<>();
		assessmentDTOS.forEach(assessmentDTO -> {
			DerivativeAssessmentEntity assessmentEntity = new DerivativeAssessmentEntity();
			assessmentEntity.setCustId(AuthUtil.getTenantCustId());
			assessmentEntity.setTenantId(AuthUtil.getTenantId());
			assessmentEntity.setOptionItem(assessmentDTO.getOptionItem());
			assessmentEntity.setOptionValue(assessmentDTO.getOptionValue());
			assessmentEntity.setCreateTime(new Date());
			assessmentEntity.setUpdateTime(new Date());
			assessmentEntities.add(assessmentEntity);
		});
		return derivativeAssessmentMapper.batchInsert(assessmentEntities);
	}

	/**
	 * 根据客户号删除衍生品及结构性投资产品的认识评估结果
	 *
	 * @param custId
	 * @return
	 */
	@Override
	public int deleteByCustId(Long custId) {
		return derivativeAssessmentMapper.deleteByCustId(custId);
	}

	/**
	 * 根据客户号查询衍生品及结构性投资产品的认识评估结果
	 *
	 * @param custId
	 * @return
	 */
	@Override
	public List<DerivativeAssessmentVO> selectByCustId(Long custId) {
		List<DerivativeAssessmentVO> assessmentVOS = new ArrayList<>();
		List<DerivativeAssessmentEntity> assessmentEntities = derivativeAssessmentMapper.selectByCustId(custId);
		if (assessmentEntities != null) {
			assessmentEntities.forEach(assessmentEntity -> {
				DerivativeAssessmentVO assessmentVO = new DerivativeAssessmentVO();
				assessmentVO.setId(assessmentEntity.getId());
				assessmentVO.setCustId(assessmentEntity.getCustId());
				assessmentVO.setTenantId(assessmentEntity.getTenantId());
				assessmentVO.setOptionItem(assessmentEntity.getOptionItem());
				assessmentVO.setOptionValue(assessmentVO.getOptionValue());
				assessmentVO.setCreateTime(assessmentEntity.getCreateTime());
				assessmentVO.setUpdateTime(assessmentEntity.getUpdateTime());
				assessmentVOS.add(assessmentVO);
			});
		}
		return assessmentVOS;
	}

	/**
	 * 根据主键更新
	 *
	 * @param assessmentDTO
	 * @return
	 */
	@Override
	public int updateByPrimaryKeySelective(DerivativeAssessmentDTO assessmentDTO) {
		DerivativeAssessmentEntity assessmentEntity = new DerivativeAssessmentEntity();
		assessmentEntity.setId(assessmentDTO.getId());
		assessmentEntity.setCustId(AuthUtil.getTenantCustId());
		assessmentEntity.setTenantId(AuthUtil.getTenantId());
		assessmentEntity.setOptionItem(assessmentDTO.getOptionItem());
		assessmentEntity.setOptionValue(assessmentDTO.getOptionValue());
		assessmentEntity.setUpdateTime(new Date());
		return derivativeAssessmentMapper.updateByPrimaryKeySelective(assessmentEntity);
	}

	/**
	 * 根据主键更新
	 *
	 * @param assessmentDTO
	 * @return
	 */
	@Override
	public int updateByPrimaryKey(DerivativeAssessmentDTO assessmentDTO) {
		DerivativeAssessmentEntity assessmentEntity = new DerivativeAssessmentEntity();
		assessmentEntity.setId(assessmentDTO.getId());
		assessmentEntity.setCustId(AuthUtil.getTenantCustId());
		assessmentEntity.setTenantId(AuthUtil.getTenantId());
		assessmentEntity.setOptionItem(assessmentDTO.getOptionItem());
		assessmentEntity.setOptionValue(assessmentDTO.getOptionValue());
		assessmentEntity.setUpdateTime(new Date());
		return derivativeAssessmentMapper.updateByPrimaryKey(assessmentEntity);
	}

	/**
	 * 根据主键删除
	 *
	 * @param id
	 * @return
	 */
	@Override
	public int deleteByPrimaryKey(Long id) {
		return derivativeAssessmentMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 新增
	 *
	 * @param assessmentDTO
	 * @return
	 */
	@Override
	public int insert(DerivativeAssessmentDTO assessmentDTO) {
		DerivativeAssessmentEntity assessmentEntity = new DerivativeAssessmentEntity();
		assessmentEntity.setCustId(AuthUtil.getTenantCustId());
		assessmentEntity.setTenantId(AuthUtil.getTenantId());
		assessmentEntity.setOptionItem(assessmentDTO.getOptionItem());
		assessmentEntity.setOptionValue(assessmentDTO.getOptionValue());
		assessmentEntity.setCreateTime(new Date());
		return derivativeAssessmentMapper.insert(assessmentEntity);
	}

	/**
	 * 根据主键新增
	 *
	 * @param assessmentDTO
	 * @return
	 */
	@Override
	public int insertSelective(DerivativeAssessmentDTO assessmentDTO) {
		DerivativeAssessmentEntity assessmentEntity = new DerivativeAssessmentEntity();
		assessmentEntity.setCustId(AuthUtil.getTenantCustId());
		assessmentEntity.setTenantId(AuthUtil.getTenantId());
		assessmentEntity.setOptionItem(assessmentDTO.getOptionItem());
		assessmentEntity.setOptionValue(assessmentDTO.getOptionValue());
		assessmentEntity.setCreateTime(new Date());
		return derivativeAssessmentMapper.insertSelective(assessmentEntity);
	}

	/**
	 * 根据主键查询
	 *
	 * @param id
	 * @return
	 */
	@Override
	public DerivativeAssessmentVO selectByPrimaryKey(Long id) {
		DerivativeAssessmentVO assessmentVO = new DerivativeAssessmentVO();
		DerivativeAssessmentEntity assessmentEntity = derivativeAssessmentMapper.selectByPrimaryKey(id);
		if (assessmentEntity != null) {
			assessmentVO.setId(assessmentEntity.getId());
			assessmentVO.setCustId(assessmentEntity.getCustId());
			assessmentVO.setTenantId(assessmentEntity.getTenantId());
			assessmentVO.setOptionItem(assessmentEntity.getOptionItem());
			assessmentVO.setOptionValue(assessmentVO.getOptionValue());
			assessmentVO.setCreateTime(assessmentEntity.getCreateTime());
			assessmentVO.setUpdateTime(assessmentEntity.getUpdateTime());
		}
		return assessmentVO;
	}

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param assessmentQuery
	 * @return
	 */
	@Override
	public IPage<DerivativeAssessmentVO> selectByPage(IPage<DerivativeAssessmentVO> page, DerivativeAssessmentQuery assessmentQuery) {
		IPage<DerivativeAssessmentVO> pageResult = new Page<>();
		assessmentQuery.setTenantId(AuthUtil.getTenantId());
		IPage<DerivativeAssessmentEntity> result = derivativeAssessmentMapper.selectByPage(page, assessmentQuery);
		if (result != null) {
			List<DerivativeAssessmentEntity> assessmentEntities = result.getRecords();
			if (assessmentEntities != null) {
				List<DerivativeAssessmentVO> assessmentVOS = new ArrayList<>();
				assessmentEntities.forEach(assessmentEntity -> {
					DerivativeAssessmentVO assessmentVO = new DerivativeAssessmentVO();
					assessmentVO.setId(assessmentEntity.getId());
					assessmentVO.setCustId(assessmentEntity.getCustId());
					assessmentVO.setTenantId(assessmentEntity.getTenantId());
					assessmentVO.setOptionItem(assessmentEntity.getOptionItem());
					assessmentVO.setOptionValue(assessmentEntity.getOptionValue());
					assessmentVO.setCreateTime(assessmentEntity.getCreateTime());
					assessmentVO.setUpdateTime(assessmentEntity.getUpdateTime());
					assessmentVOS.add(assessmentVO);
				});
				pageResult.setRecords(assessmentVOS);
				pageResult.setTotal(result.getTotal());
				pageResult.setSize(result.getSize());
				pageResult.setCurrent(result.getCurrent());
				pageResult.setPages(result.getPages());
			}
		}
		return pageResult;
	}

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param recordQuery
	 * @return
	 */
	@Override
	public IPage<DerivativeUserRecordVO> selectByPage(IPage<DerivativeUserRecordVO> page, DerivativeUserRecordQuery recordQuery) {
		recordQuery.setTenantId(AuthUtil.getTenantId());
		IPage<DerivativeUserRecordVO> result = derivativeAssessmentMapper.selectDerivativeUserRecordByPage(page, recordQuery);
		return result;
	}
}
