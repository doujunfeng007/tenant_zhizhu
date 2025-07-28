package com.minigod.zero.bpmn.module.stocktransfer.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.stocktransfer.service.ISecSharesInfoService;
import com.minigod.zero.bpmn.module.stocktransfer.service.ISecTransferredStockService;
import com.minigod.zero.bpmn.module.common.entity.FileUploadInfoEntity;
import com.minigod.zero.bpmn.module.common.enums.UploadBusinessType;
import com.minigod.zero.bpmn.module.common.service.IFileUploadInfoService;
import com.minigod.zero.bpmn.module.stocktransfer.entity.SecSharesInfoEntity;
import com.minigod.zero.bpmn.module.stocktransfer.entity.SecTransferredStockEntity;
import com.minigod.zero.bpmn.module.stocktransfer.vo.SecTransferredStockExcel;
import com.minigod.zero.bpmn.module.stocktransfer.vo.SecTransferredStockVO;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.excel.util.ExcelUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tenant.annotation.NonDS;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author chen
 * @ClassName SecTransferredStockController.java
 * @Description TODO
 * @createTime 2024年03月07日 11:20:00
 */
@NonDS
@ApiIgnore
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/stockTransferred")
@Api(value = "股票转入转出管理", tags = "接口")
public class SecTransferredStockController extends ZeroController {

	@Autowired
	private ISecTransferredStockService secTransferredStockService;

	@Autowired
	private ISecSharesInfoService secSharesInfoService;

	@Autowired
	private IFileUploadInfoService fileUploadInfoService;

	/**
	 * 股票转入转出信息表详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入secTransferredStock")
	//@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<SecTransferredStockVO> detail(SecTransferredStockEntity secTransferredStockEntity) {
		SecTransferredStockEntity detail = secTransferredStockService.getOne(Condition.getQueryWrapper(secTransferredStockEntity));
		LambdaQueryWrapper<SecSharesInfoEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SecSharesInfoEntity::getStockId, detail.getId());
		List<SecSharesInfoEntity> secSharesInfoEntities = secSharesInfoService.getBaseMapper().selectList(wrapper);
		SecTransferredStockVO secTransferredStockVO = Objects.requireNonNull(BeanUtil.copy(detail, SecTransferredStockVO.class));
		secTransferredStockVO.setSharesData(secSharesInfoEntities);

		List<FileUploadInfoEntity> files = fileUploadInfoService.selectListByBusinessType(detail.getId(), UploadBusinessType.STOCK_TRANSFERRED_CERTIFICATE.getBusinessType());
		secTransferredStockVO.setFileList(files);

		return R.data(secTransferredStockVO);
	}

	/**
	 * 股票转入转出信息表分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入secTransferredStock")
	//@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
	public R<IPage<SecTransferredStockVO>> list(SecTransferredStockEntity secTransferredStockEntity, Query query) {

		secTransferredStockEntity.setTenantId(AuthUtil.getTenantId());
		QueryWrapper<SecTransferredStockEntity> wrapper = Condition.getQueryWrapper(secTransferredStockEntity);
		wrapper.orderByDesc("create_time");
		IPage<SecTransferredStockEntity> pages = secTransferredStockService.page(Condition.getPage(query), wrapper);
		IPage<SecTransferredStockVO> resultPage = new Page<>();
		resultPage.setRecords(BeanUtil.copy(pages.getRecords(), SecTransferredStockVO.class));
		resultPage.setTotal(pages.getTotal());
		resultPage.setCurrent(pages.getCurrent());
		resultPage.setSize(pages.getSize());
		resultPage.setPages(pages.getPages());
		return R.data(resultPage);
	}

	@PostMapping("/update")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "修改", notes = "传入secTransferredStock")
	public R update(@Valid @RequestBody SecTransferredStockVO transferredStockVO) {
		transferredStockVO.setUpdateTime(new Date());
		return R.status(secTransferredStockService.updateAndStock(transferredStockVO));
	}

	@GetMapping("export")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "导出股票转入转出数据", notes = "传入cust")
	public void export(SecTransferredStockEntity secTransferredStockEntity, Query query, HttpServletResponse response) {
		secTransferredStockEntity.setTenantId(AuthUtil.getTenantId());
		IPage<SecTransferredStockEntity> pages = secTransferredStockService.page(Condition.getPage(query), Condition.getQueryWrapper(secTransferredStockEntity));
		List<SecTransferredStockEntity> records = pages.getRecords();
		List<SecTransferredStockExcel> excelList = new ArrayList<>();
		for (SecTransferredStockEntity record : records) {
			SecTransferredStockExcel excel = BeanUtil.copy(record, SecTransferredStockExcel.class);
			if (record.getTransferState() != null) {
				switch (record.getTransferState()) {
					case 0:
						excel.setTransferStateName("未转入");
						break;
					case 1:
						excel.setTransferStateName("已转入");
						break;
					default:
						excel.setTransferStateName("未知");
				}
			}
			if (record.getIsShares() != null) {
				switch (record.getIsShares()) {
					case 1:
						excel.setIsSharesName("港股");
						break;
					case 2:
						excel.setIsSharesName("美股");
						break;
					default:
						excel.setIsSharesName("未知");
				}
			}
			if (record.getRegulationType() != null) {
				switch (record.getRegulationType()) {
					case 1:
						excel.setRegulationTypeName("转入记录");
						break;
					case 2:
						excel.setRegulationTypeName("转出记录");
						break;
					default:
						excel.setRegulationTypeName("未知");
				}
			}
			excelList.add(excel);
		}
		ExcelUtil.export(response, "股票转入转出", "股票转入转出", excelList, SecTransferredStockExcel.class);
	}

	@PostMapping("/updateStatus")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改状态", notes = "传入secTransferredStock")
	public R updateStatus(@Valid @RequestBody SecTransferredStockVO transferredStockVO) {
		SecTransferredStockEntity update = secTransferredStockService.getBaseMapper().selectById(transferredStockVO.getId());
		update.setStatus(transferredStockVO.getStatus());
		update.setBackReason(transferredStockVO.getBackReason());
		update.setUpdateTime(new Date());
		return R.status(secTransferredStockService.getBaseMapper().updateById(update) > 0);
	}
}
