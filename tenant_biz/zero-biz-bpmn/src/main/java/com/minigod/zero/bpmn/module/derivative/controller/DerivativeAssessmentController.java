package com.minigod.zero.bpmn.module.derivative.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.derivative.dto.DerivativeAssessmentDTO;
import com.minigod.zero.bpmn.module.derivative.query.DerivativeUserRecordQuery;
import com.minigod.zero.bpmn.module.derivative.service.IDerivativeAssessmentService;
import com.minigod.zero.bpmn.module.derivative.vo.DerivativeAssessmentVO;
import com.minigod.zero.bpmn.module.derivative.vo.DerivativeUserRecordVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 衍生品及结构性投资产品的认识评估结果
 *
 * @author eric
 * @since 2024-06-20 18:10:00
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/derivative-assessment")
@Api(value = "衍生品及结构性投资产品的认识评估结果", tags = "衍生品及结构性投资产品的认识评估结果接口")
public class DerivativeAssessmentController {
	private final IDerivativeAssessmentService derivativeAssessmentService;

	@ApiOperation("保存衍生品及结构性投资产品的认识评估结果")
	@PostMapping("/save")
	public R<Void> insertSelective(@RequestBody DerivativeAssessmentDTO assessmentDTO) {
		int rows = derivativeAssessmentService.insertSelective(assessmentDTO);
		if (rows > 0) {
			return R.success("保存衍生品及结构性投资产品的认识评估结果成功!");
		} else {
			return R.fail("保存衍生品及结构性投资产品的认识评估结果失败!");
		}
	}

	@ApiOperation("删除衍生品及结构性投资产品的认识评估结果")
	@DeleteMapping("/remove")
	public R<Void> deleteByPrimaryKey(@RequestParam("id") Long id) {
		int rows = derivativeAssessmentService.deleteByPrimaryKey(id);
		if (rows > 0) {
			return R.success("删除衍生品及结构性投资产品的认识评估结果成功!");
		} else {
			return R.fail("删除衍生品及结构性投资产品的认识评估结果失败!");
		}
	}

	@ApiOperation("更新衍生品及结构性投资产品的认识评估结果")
	@PutMapping("/update")
	public R<Void> updateByPrimaryKeySelective(@RequestBody DerivativeAssessmentDTO assessmentDTO) {
		int rows = derivativeAssessmentService.updateByPrimaryKeySelective(assessmentDTO);
		if (rows > 0) {
			return R.success("更新衍生品及结构性投资产品的认识评估结果成功!");
		} else {
			return R.fail("更新衍生品及结构性投资产品的认识评估结果失败!");
		}
	}

	@ApiOperation("查询衍生品及结构性投资产品的认识评估结果")
	@GetMapping("/select")
	public R<DerivativeAssessmentVO> selectByPrimaryKey(@RequestParam("id") Long id) {
		DerivativeAssessmentVO assessmentVO = derivativeAssessmentService.selectByPrimaryKey(id);
		return R.data(assessmentVO);
	}

	@ApiOperation("查询衍生品及结构性投资产品的认识评估结果")
	@GetMapping("/select/cust-id")
	public R<List<DerivativeAssessmentVO>> selectByCustId(@RequestParam("custId") Long custId) {
		List<DerivativeAssessmentVO> assessmentVOList = derivativeAssessmentService.selectByCustId(custId);
		return R.data(assessmentVOList);
	}

	@ApiOperation("分页查询衍生品及结构性投资产品的认识评估结果")
	@GetMapping("/select/list-by-page")
	public R<IPage<DerivativeUserRecordVO>> selectByPage(DerivativeUserRecordQuery recordQuery, Query query) {
		return R.data(derivativeAssessmentService.selectByPage(Condition.getPage(query), recordQuery));
	}

	@ApiOperation("批量保存衍生品及结构性投资产品的认识评估结果")
	@PostMapping("/batch-save")
	public R<Void> batchInsert(@RequestBody List<DerivativeAssessmentDTO> assessmentDTOS) {
		int rows = derivativeAssessmentService.batchInsert(assessmentDTOS);
		if (rows > 0) {
			return R.success("批量保存衍生品及结构性投资产品的认识评估结果成功!");
		} else {
			return R.fail("批量保存衍生品及结构性投资产品的认识评估结果失败!");
		}
	}

	@ApiOperation("删除指定用户衍生品及结构性投资产品的认识评估结果")
	@DeleteMapping("/remove/cust-id")
	public R<Void> deleteByCustId(@RequestParam("custId") Long custId) {
		int rows = derivativeAssessmentService.deleteByCustId(custId);
		if (rows > 0) {
			return R.success("删除衍生品及结构性投资产品的认识评估结果成功!");
		} else {
			return R.fail("删除衍生品及结构性投资产品的认识评估结果失败!");
		}
	}
}
