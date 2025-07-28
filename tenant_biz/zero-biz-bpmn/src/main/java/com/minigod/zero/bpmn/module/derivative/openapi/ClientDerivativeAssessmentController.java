package com.minigod.zero.bpmn.module.derivative.openapi;

import com.minigod.zero.bpmn.module.common.service.impl.SysItemConfigService;
import com.minigod.zero.bpmn.module.common.vo.SysItemsAndSubItemConfigVO;
import com.minigod.zero.bpmn.module.derivative.dto.DerivativeAssessmentDTO;
import com.minigod.zero.bpmn.module.derivative.service.IDerivativeAssessmentService;
import com.minigod.zero.bpmn.module.derivative.vo.DerivativeAssessmentVO;
import com.minigod.zero.bpmn.module.feign.IDerivativeAssessmentClient;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 衍生品及结构性投资产品的认识评估结果
 * @Author eric
 * @Date 2024/06/20 19:34:15
 */
@RestController
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/open-api/derivative")
@Validated
@Slf4j
@AllArgsConstructor
@Api(value = "衍生品及结构性投资产品的认识评估结果接口", tags = "衍生品及结构性投资产品的认识评估结果接口")
public class ClientDerivativeAssessmentController extends ZeroController {

	private final IDerivativeAssessmentService derivativeAssessmentService;
	private final SysItemConfigService sysItemConfigService;
	private final IDerivativeAssessmentClient derivativeAssessmentClient;

	@ApiOperation("根据配置项类型查询衍生品及结构性投资产品的认识评估配置项和配置子项")
	@GetMapping("/select/items-subitems/type-lang")
	public R<SysItemsAndSubItemConfigVO> selectItemsAndSubItemByItemType(@RequestParam("itemType") String itemType) {
		String language = WebUtil.getLanguage();
		return R.data(sysItemConfigService.selectItemsAndSubItemByItemType(itemType, language));
	}

	@ApiOperation("查询衍生品及结构性投资产品的认识评估结果")
	@GetMapping("/select/cust-id")
	public R<List<DerivativeAssessmentVO>> selectByCustId(@RequestParam("custId") Long custId) {
		List<DerivativeAssessmentVO> assessmentVOList = derivativeAssessmentService.selectByCustId(custId);
		return R.data(assessmentVOList);
	}

	@ApiOperation("批量保存衍生品及结构性投资产品的认识评估结果")
	@PostMapping("/batch-save")
	public R<Void> batchInsert(@RequestBody List<DerivativeAssessmentDTO> assessmentDTOS) {
		if (assessmentDTOS.size() == 0) {
			return R.fail("评估结果参数不能为空!");
		}
		List<DerivativeAssessmentVO> assessmentVOList = derivativeAssessmentService.selectByCustId(AuthUtil.getTenantCustId());
		if (assessmentVOList.size() > 0) {
			return R.fail("您的衍生品及结构性投资产品的认识评估结果已存在,不能重复提交!");
		}
		int rows = derivativeAssessmentService.batchInsert(assessmentDTOS);
		if (rows > 0) {
			R result = derivativeAssessmentClient.updateCustomerDerivative(AuthUtil.getTenantCustId());
			if (R.isSuccess(result)) {
				log.info("-->用户:{}批量保存衍生品及结构性投资产品的认识评估结果成功,同时更新客户成功!", AuthUtil.getTenantCustId());
				return R.success("批量保存衍生品及结构性投资产品的认识评估结果,同时更新客户成功!");
			} else {
				log.error("-->用户:{}批量保存衍生品及结构性投资产品的认识评估结果成功,但更新客户衍生品及结构性投资产品的认识评估结果失败!", AuthUtil.getTenantCustId());
				return R.fail("批量保存衍生品及结构性投资产品的认识评估结果成功,但更新客户衍生品及结构性投资产品的认识评估结果失败!");
			}
		} else {
			log.error("-->用户:{}批量保存衍生品及结构性投资产品的认识评估结果失败!", AuthUtil.getTenantCustId());
			return R.fail("批量保存衍生品及结构性投资产品的认识评估结果失败!");
		}
	}

	@ApiOperation("是否已经提交结果")
	@GetMapping("/status")
	public R<Integer> status() {
		List<DerivativeAssessmentVO> assessmentVOList = derivativeAssessmentService.selectByCustId(AuthUtil.getTenantCustId());
		int assessmentVO = assessmentVOList.size();
		return R.data(assessmentVO);
	}

	@ApiOperation("删除指定用户衍生品及结构性投资产品的认识评估结果")
	@DeleteMapping("/remove/cust-id")
	public R<Void> deleteByCustId() {
		int rows = derivativeAssessmentService.deleteByCustId(AuthUtil.getTenantCustId());
		if (rows > 0) {
			return R.success("删除衍生品及结构性投资产品的认识评估结果成功!");
		} else {
			return R.fail("删除衍生品及结构性投资产品的认识评估结果失败!");
		}
	}
}
