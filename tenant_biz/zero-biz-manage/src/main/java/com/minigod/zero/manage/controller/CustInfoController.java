package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.biz.common.enums.CustLoginEnum;
import com.minigod.zero.manage.excel.CustomCustInfoExcel;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.excel.util.ExcelUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.core.tool.utils.DateUtil;
import com.minigod.zero.cust.apivo.CustLoginLogVO;
import com.minigod.zero.cust.apivo.excel.CustInfoExcel;
import com.minigod.zero.cust.apivo.req.CustInfoQueryReq;
import com.minigod.zero.cust.apivo.resp.CustInfoResp;
import com.minigod.zero.cust.feign.ICustBackClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/custInfo")
@Api(value = "获取客户列表", tags = "获取客户列表")
public class CustInfoController extends ZeroController {

	@Resource
	private ICustBackClient custBackClient;

	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入custInfo")
	public R<Page<CustInfoResp>> page(CustInfoQueryReq custInfoQueryReq, Query query) {
		Page<CustInfoResp> pages = custBackClient.queryPageCustInfo(custInfoQueryReq, query);
		return R.data(pages);
	}

	@GetMapping("export")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "导出客户", notes = "传入cust")
	public void exportCust(CustInfoQueryReq custInfoQueryReq, HttpServletResponse response) {

		List<CustInfoExcel> custInfoExcelList = custBackClient.exportCust(custInfoQueryReq);

		HashMap<Integer, String> sourceTypeMap = new HashMap<>();
		sourceTypeMap.put(1,"web");
		sourceTypeMap.put(2,"ios");
		sourceTypeMap.put(3,"aos");
		sourceTypeMap.put(4,"pc");
		sourceTypeMap.put(5,"h5");

		HashMap<Integer, String> regChannelMap = new HashMap<>();
		regChannelMap.put(0,"web");
		regChannelMap.put(1,"ios");
		regChannelMap.put(2,"aos");
		regChannelMap.put(3,"pc");

		List<CustomCustInfoExcel> resp = custInfoExcelList.stream().map(custInfoExcel -> {
			CustomCustInfoExcel customCustInfoExcel = new CustomCustInfoExcel();
			BeanUtil.copy(custInfoExcel, customCustInfoExcel);

			if (custInfoExcel.getGender() != null) {
				customCustInfoExcel.setGender(custInfoExcel.getGender() == -1 ? "" : (custInfoExcel.getGender() == 1 ? "男" : "女"));
			}
			if (custInfoExcel.getAcceptAgreement()!=null){
				customCustInfoExcel.setAcceptAgreement(custInfoExcel.getAcceptAgreement() ==- 1 ? "" :(custInfoExcel.getAcceptAgreement() == 1 ? "是" : "否"));
			}
			if (custInfoExcel.getIsWritePhone()!=null){
				customCustInfoExcel.setIsWritePhone(custInfoExcel.getIsWritePhone() ==- 1 ? "" :(custInfoExcel.getIsWritePhone() == 1 ? "是" : "否"));
			}
			if (custInfoExcel.getIsAgree()!=null){
				customCustInfoExcel.setIsAgree(custInfoExcel.getIsAgree() ==- 1 ? "" :(custInfoExcel.getIsAgree() == 1 ? "是" : "否"));
			}
			customCustInfoExcel.setRegChannel(regChannelMap.getOrDefault(custInfoExcel.getRegChannel(), "未知"));
			customCustInfoExcel.setRegSourceType(sourceTypeMap.getOrDefault(custInfoExcel.getRegSourceType(), "未知"));
			return customCustInfoExcel;
		}).collect(Collectors.toList());
		ExcelUtil.export(response, "用户数据" + DateUtil.time(), "用户数据表", resp, CustomCustInfoExcel.class);
	}

	/**
	 * 登陆日志表 自定义分页
	 */
	@GetMapping("/loginLog")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入loginLog")
	public R<IPage<CustLoginLogVO>> loginLog(CustLoginLogVO loginLog, Query query) {
		loginLog.setTypeList(List.of(CustLoginEnum.type.LOGIN.getCode(), CustLoginEnum.type.IP_OFFSITE.getCode()));
		loginLog.setAction(CustLoginEnum.action.IN.getCode());
		IPage<CustLoginLogVO> pages = custBackClient.getPageLoginLog(loginLog, query);
		return R.data(pages);
	}

}
