package com.minigod.zero.manage.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.utils.BeanUtil;
import com.minigod.zero.manage.entity.SnCouponExchangeCodeDataEntity;
import com.minigod.zero.manage.entity.SnCouponExchangeCodeEntity;
import com.minigod.zero.manage.entity.SnCouponManageEntity;
import com.minigod.zero.manage.entity.SnCouponManageMatchEntity;
import com.minigod.zero.manage.service.ICouponService;
import com.minigod.zero.manage.vo.ExchangeCodeRespVO;
import com.minigod.zero.manage.vo.SnCouponManageMatchVO;
import com.minigod.zero.manage.vo.SnCouponManageVO;
import com.minigod.zero.manage.vo.request.CouponManageVO;
import com.minigod.zero.manage.vo.request.ExchangeBulkListReqVO;
import com.minigod.zero.manage.vo.request.ExchangeCodeReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 优惠券管理
 *
 * @author eric
 * @since 2024-12-26 13:46:51
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX +"/coupon")
@Api(value = "优惠券管理", tags = "优惠券管理")
public class CouponController extends ZeroController {

	private final ICouponService couponService;

	/**
	 * 查询优惠券管理列表不带条件
	 *
	 * @param query
	 * @return
	 */
	@GetMapping("/coupon_manage_index")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查询优惠券管理列表不带条件", notes = "传入query分页参数")
	public R<IPage<SnCouponManageEntity>> couponManageIndex(Query query) {
		return R.data(couponService.findCouponManagePage(Condition.getPage(query)));
	}

	/**
	 * 查询优惠券管理记录
	 *
	 * @param manageId
	 * @return
	 */
	@GetMapping("/coupon_manage_detail")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查询优惠券管理记录", notes = "传入manageId")
	public R<SnCouponManageEntity> couponManageDetail(Long manageId) {
		return R.data(couponService.findCouponManage(manageId));
	}

	/**
	 * 保存优惠券管理记录
	 *
	 * @param couponManageVo
	 * @return
	 */
	@PostMapping("/exchange_code_add")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "保存优惠券", notes = "传入couponManageVo")
	public R saveCouponManage(@RequestBody CouponManageVO couponManageVo) {
		couponService.saveCouponManage(couponManageVo);
		return R.success();
	}

	/**
	 * 优惠券作废
	 *
	 * @param exchangeCodeId
	 * @return
	 */
	@DeleteMapping("/exchange_code_del")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "优惠券作废", notes = "传入exchangeCodeId")
	public R deleteCouponManage(Long exchangeCodeId) {
		couponService.updateExchangeCode(exchangeCodeId);
		return R.success();
	}

	/**
	 * 延长优惠券有效期
	 *
	 * @param exchangeCodeId
	 * @param recordStatus
	 * @param days
	 * @return
	 */
	@PutMapping("/exchange_code_yc")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "延长优惠券有效期", notes = "传入exchangeCodeId、recordStatus、days")
	public R exchangeCodeYc(Long exchangeCodeId, Integer recordStatus, Integer days) {
		couponService.updateExchangeCode(exchangeCodeId, recordStatus, days);
		return R.success();
	}

	/**
	 * 批量兑换码分页列表
	 *
	 * @param exchangeBulkListReqVo
	 * @return
	 */
	@GetMapping("/exchange_bulk_list_ui")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "批量兑换码分页列表", notes = "传入exchangeBulkListReqVo")
	public R<IPage<SnCouponManageVO>> exchangeBulkPage(Query query, ExchangeBulkListReqVO exchangeBulkListReqVo) {
		IPage<SnCouponManageVO> couponManagePage = couponService.findExchangeBulkPage(Condition.getPage(query), exchangeBulkListReqVo);
		return R.data(couponManagePage);
	}

	/**
	 * 导出批量兑换码分页列表
	 *
	 * @param response
	 * @param query
	 * @param exchangeBulkListReqVo
	 * @return
	 */
	@GetMapping("/exchange_bulk_list_ui_export")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "导出优惠券列表", notes = "传入exchangeBulkListReqVo")
	public R exchangeBulkListExport(HttpServletResponse response, Query query, ExchangeBulkListReqVO exchangeBulkListReqVo) {
		// 创建一个webbook，对应一个Excel文件
		Workbook wb = new XSSFWorkbook();
		// 在webbook中添加一个sheet,对应Excel文件中的sheet
		Sheet sheet = wb.createSheet("批量兑换码详情");
		// 在sheet中添加表头第0行
		Row row = sheet.createRow(0);
		// 创建单元格，并设置值表头 设置表头居中
		CellStyle style = wb.createCellStyle();
		// 创建一个居中格式
		style.setAlignment(HorizontalAlignment.CENTER);

		String excelTitle = "";

		Cell cell = row.createCell(0);
		cell.setCellValue("批次");
		cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue("创建时间");
		cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue("创建人");
		cell.setCellStyle(style);

		cell = row.createCell(3);
		cell.setCellValue("创建数量");
		cell.setCellStyle(style);

		cell = row.createCell(4);
		cell.setCellValue("卡劵类型");
		cell.setCellStyle(style);

		cell = row.createCell(5);
		cell.setCellValue("卡劵名称");
		cell.setCellStyle(style);

		cell = row.createCell(6);
		cell.setCellValue("使用条件");
		cell.setCellStyle(style);

		cell = row.createCell(7);
		cell.setCellValue("资产门槛");
		cell.setCellStyle(style);

		cell = row.createCell(8);
		cell.setCellValue("兑换码");
		cell.setCellStyle(style);

		cell = row.createCell(9);
		cell.setCellValue("是否使用");
		cell.setCellStyle(style);

		cell = row.createCell(10);
		cell.setCellValue("过期时间");
		cell.setCellStyle(style);

		excelTitle = "兑换数据统计";

		List<SnCouponManageVO> list = null;
		if (exchangeBulkListReqVo.getStartDate() != null || exchangeBulkListReqVo.getEndDate() != null
			|| exchangeBulkListReqVo.getOprName() != null || exchangeBulkListReqVo.getUseStatus() != -1) {
			IPage<SnCouponManageVO> exchangeBulkPage = couponService.findExchangeBulkPage(Condition.getPage(query), exchangeBulkListReqVo);
			list = exchangeBulkPage.getRecords();
		} else {
			IPage<SnCouponManageVO> exchangeBulkPage = couponService.findExchangeBulkPage(Condition.getPage(query), null);
			list = exchangeBulkPage.getRecords();
		}


		for (int i = 0; i < list.size(); i++) {
			SnCouponManageVO exchangeCode = list.get(i);
			row = sheet.createRow(i + 1);

			//创建单元格，并设置值
			if (exchangeCode.getId() == null) {
				row.createCell(0).setCellValue("");
			} else {
				row.createCell(0).setCellValue(exchangeCode.getId());
			}

			row.createCell(1).setCellValue(DateUtil.format(exchangeCode.getCreateTime(), "yyyy年MM月dd日"));

			row.createCell(2).setCellValue(exchangeCode.getOprName());

			row.createCell(3).setCellValue(exchangeCode.getCreateNumber());

			String cardType = "";
			if (exchangeCode.getCardType() == 1) {
				cardType = "免佣券";
			}
			if (exchangeCode.getCardType() == 2) {
				cardType = "现金券";
			}
			if (exchangeCode.getCardType() == 4) {
				cardType = "赠股";
			}
			if (exchangeCode.getCardType() == 5) {
				cardType = "积分";
			}
			if (exchangeCode.getCardType() == 6) {
				cardType = "现金抵用券";
			}
			row.createCell(4).setCellValue(cardType);

			row.createCell(5).setCellValue(exchangeCode.getCardName());
			String useType = "";
			if (exchangeCode.getUseType() == 1) {
				useType = "首次开户";
			}
			if (exchangeCode.getUseType() == 2) {
				useType = "首次入金";
			}
			if (exchangeCode.getUseType() == 3) {
				useType = "首次转仓";
			}
			if (exchangeCode.getUseType() == 8) {
				useType = "入金金额";
			}
			if (exchangeCode.getUseType() == 9) {
				useType = "首次打新";
			}
			row.createCell(6).setCellValue(useType);

			row.createCell(7).setCellValue(exchangeCode.getAmount() == null ? "" : exchangeCode.getAmount().toString());

			row.createCell(8).setCellValue(exchangeCode.getCode());

			String useStatus = "";
			if (exchangeCode.getUseStatus() != null) {
				if (exchangeCode.getUseStatus() == 0) {
					useStatus = "未使用";
				}
				if (exchangeCode.getUseStatus() == 1) {
					useStatus = "已使用";
				}
				if (exchangeCode.getUseStatus() == 2) {
					useStatus = "已过期";
				}
				if (exchangeCode.getUseStatus() == 3) {
					useStatus = "已作废";
				}
			}

			row.createCell(9).setCellValue(useStatus);
			row.createCell(10).setCellValue(DateUtil.format(exchangeCode.getExpiredTime(), "yyyy年MM月dd日"));
		}
		try {
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流

			response.setContentType("application/msexcel");
			response.setHeader("Content-disposition", "attachment; filename=" + new String((excelTitle).getBytes("utf-8"), "iso8859-1") + ".xlsx");

			wb.write(os);
			os.close();
		} catch (IOException e) {
			log.error("导出兑换码分页列表失败->", e);
			e.printStackTrace();
			return fail("导出兑换码分页列表失败!");
		}
		return success("导出兑换码分页列表成功!");
	}

	/**
	 * 导出指定优惠券管理记录
	 *
	 * @param response
	 * @param manageId
	 * @return
	 */
	@GetMapping("/coupon_manage_detail_export")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "保存优惠券", notes = "传入couponManageVo")
	public R couponManageDetailExport(HttpServletResponse response, Long manageId) {
		SnCouponManageEntity snCouponManage = couponService.findCouponManage(manageId);
		if (snCouponManage.getChannelId().equalsIgnoreCase("-1")) {
			snCouponManage.setChannelId("不限");
		}
		// 创建一个webbook，对应一个Excel文件
		Workbook wb = new XSSFWorkbook();
		// 在webbook中添加一个sheet,对应Excel文件中的sheet
		Sheet sheet = wb.createSheet("兑换码详情");
		// 在sheet中添加表头第0行
		Row row = sheet.createRow(0);
		// 创建单元格，并设置值表头 设置表头居中
		CellStyle style = wb.createCellStyle();
		// 创建一个居中格式
		style.setAlignment(HorizontalAlignment.CENTER);

		String excelTitle = "";

		Cell cell = row.createCell(0);
		cell.setCellValue("序列号");
		cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue("激活码");
		cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue("卡券名称");
		cell.setCellStyle(style);

		cell = row.createCell(3);
		cell.setCellValue("卡券类型");
		cell.setCellStyle(style);

		cell = row.createCell(4);
		cell.setCellValue("批次ID");
		cell.setCellStyle(style);

		cell = row.createCell(5);
		cell.setCellValue("渠道ID");
		cell.setCellStyle(style);

		cell = row.createCell(6);
		cell.setCellValue("使用条件");
		cell.setCellStyle(style);

		cell = row.createCell(7);
		cell.setCellValue("资产门槛");
		cell.setCellStyle(style);

		cell = row.createCell(8);
		cell.setCellValue("UID");
		cell.setCellStyle(style);

		cell = row.createCell(9);
		cell.setCellValue("手机号");
		cell.setCellStyle(style);

		cell = row.createCell(10);
		cell.setCellValue("状态");
		cell.setCellStyle(style);

		cell = row.createCell(11);
		cell.setCellValue("是否使用");
		cell.setCellStyle(style);

		cell = row.createCell(12);
		cell.setCellValue("过期时间");
		cell.setCellStyle(style);

		cell = row.createCell(13);
		cell.setCellValue("兑换时间");
		cell.setCellStyle(style);

		cell = row.createCell(14);
		cell.setCellValue("使用时间");
		cell.setCellStyle(style);

		cell = row.createCell(15);
		cell.setCellValue("新增资产");
		cell.setCellStyle(style);

		excelTitle = "兑换数据统计";

		List<SnCouponExchangeCodeEntity> list = couponService.findCouponExchangeCodeList(manageId);

		for (int i = 0; i < list.size(); i++) {
			SnCouponExchangeCodeEntity exchangeCode = list.get(i);
			row = sheet.createRow(i + 1);
			//创建单元格，并设置值
			row.createCell(0).setCellValue(null == exchangeCode.getSerialNum() ? "" : exchangeCode.getSerialNum());
			row.createCell(1).setCellValue(exchangeCode.getCode());

			row.createCell(2).setCellValue(snCouponManage.getCardName());
			String cardType = "";
			if (snCouponManage.getCardType() == 1) {
				cardType = "免佣券";
			}
			if (snCouponManage.getCardType() == 2) {
				cardType = "现金券";
			}
			if (snCouponManage.getCardType() == 4) {
				cardType = "赠股";
			}
			if (snCouponManage.getCardType() == 5) {
				cardType = "积分";
			}
			row.createCell(3).setCellValue(cardType);
			row.createCell(4).setCellValue(manageId);
			row.createCell(5).setCellValue(snCouponManage.getChannelId());
			String useType = "";
			if (snCouponManage.getUseType() == 1) {
				useType = "首次开户";
			}
			if (snCouponManage.getUseType() == 2) {
				useType = "首次入金";
			}
			if (snCouponManage.getUseType() == 3) {
				useType = "首次转仓";
			}
			if (snCouponManage.getUseType() == 8) {
				useType = "入金金额";
			}
			if (snCouponManage.getUseType() == 9) {
				useType = "首次打新";
			}
			row.createCell(6).setCellValue(useType);
			row.createCell(7).setCellValue(snCouponManage.getAmount() == null ? "" : snCouponManage.getAmount().toString());
			row.createCell(8).setCellValue(exchangeCode.getUseUid() == null ? "" : exchangeCode.getUseUid().toString());
			row.createCell(9).setCellValue(exchangeCode.getPhoneNumber());
			row.createCell(10).setCellValue(exchangeCode.getRecordStatus() == 0 ? "未兑换" : "已兑换");
			String useStatus = "";
			if (exchangeCode.getUseStatus() == 0) {
				useStatus = "未使用";
			}
			if (exchangeCode.getUseStatus() == 1) {
				useStatus = "已使用";
			}
			if (exchangeCode.getUseStatus() == 2) {
				useStatus = "已过期";
			}
			if (exchangeCode.getUseStatus() == 3) {
				useStatus = "已作废";
			}
			row.createCell(11).setCellValue(useStatus);
			row.createCell(12).setCellValue(cn.hutool.core.date.DateUtil.format(exchangeCode.getExpiredTime(), "yyyy年MM月dd日"));
			row.createCell(13).setCellValue(cn.hutool.core.date.DateUtil.format(exchangeCode.getRecordDate(), "yyyy年MM月dd日"));
			row.createCell(14).setCellValue(DateUtil.format(exchangeCode.getUseDate(), "yyyy年MM月dd日"));
			row.createCell(15).setCellValue(exchangeCode.getAmount() == null ? "" : exchangeCode.getAmount().toString());
		}

		try {
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流

			response.setContentType("application/msexcel");
			response.setHeader("Content-disposition", "attachment; filename=" + new String((excelTitle).getBytes("utf-8"), "iso8859-1") + ".xlsx");

			wb.write(os);
			os.close();
		} catch (IOException e) {
			log.error("导出指定优惠券管理记录异常,异常详情->", e);
			R.fail("导出指定优惠券管理记录异常!");
		}
		return R.success();
	}

	/**
	 * 查询优惠券列表
	 *
	 * @param exchangeCodeReqVo
	 * @return
	 */
	@GetMapping("/exchange_code_list")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "查询优惠券列表", notes = "传入exchangeCodeReqVo")
	public R<IPage<ExchangeCodeRespVO>> findExchangeCodePage(Query query, ExchangeCodeReqVO exchangeCodeReqVo) {
		return R.data(couponService.findExchangeCodePage(Condition.getPage(query), exchangeCodeReqVo));
	}

	/**
	 * 导出优惠券列表
	 *
	 * @param response
	 * @param exchangeCodeReqVo
	 * @return
	 */
	@GetMapping("/exchange_code_list_export")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "导出优惠券列表", notes = "传入exchangeCodeReqVo")
	public R exchangeCode(HttpServletResponse response, ExchangeCodeReqVO exchangeCodeReqVo) {
		if (exchangeCodeReqVo.getEndDate() == null || exchangeCodeReqVo.getStartDate() == null) {
			return R.fail("导出数据开始日期、截止日期参数不能为空!");
		}
		// 创建一个webbook，对应一个Excel文件
		Workbook wb = new XSSFWorkbook();
		// 在webbook中添加一个sheet,对应Excel文件中的sheet
		Sheet sheet = wb.createSheet("兑换码详情");
		// 在sheet中添加表头第0行
		Row row = sheet.createRow(0);
		// 创建单元格，并设置值表头 设置表头居中
		CellStyle style = wb.createCellStyle();
		// 创建一个居中格式
		style.setAlignment(HorizontalAlignment.CENTER);

		String excelTitle = "";

		Cell cell = row.createCell(0);
		cell.setCellValue("序列号");
		cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue("激活码");
		cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue("卡券名称");
		cell.setCellStyle(style);

		cell = row.createCell(3);
		cell.setCellValue("卡券类型");
		cell.setCellStyle(style);

		cell = row.createCell(4);
		cell.setCellValue("批次ID");
		cell.setCellStyle(style);

		cell = row.createCell(5);
		cell.setCellValue("渠道ID");
		cell.setCellStyle(style);

		cell = row.createCell(6);
		cell.setCellValue("使用条件");
		cell.setCellStyle(style);

		cell = row.createCell(7);
		cell.setCellValue("资产条件");
		cell.setCellStyle(style);

		cell = row.createCell(8);
		cell.setCellValue("UID");
		cell.setCellStyle(style);

		cell = row.createCell(9);
		cell.setCellValue("手机号");
		cell.setCellStyle(style);

		cell = row.createCell(10);
		cell.setCellValue("状态");
		cell.setCellStyle(style);

		cell = row.createCell(11);
		cell.setCellValue("过期时间");
		cell.setCellStyle(style);

		cell = row.createCell(12);
		cell.setCellValue("注册时间");
		cell.setCellStyle(style);

		cell = row.createCell(13);
		cell.setCellValue("开户时间");
		cell.setCellStyle(style);

		cell = row.createCell(14);
		cell.setCellValue("入金时间");
		cell.setCellStyle(style);

		cell = row.createCell(15);
		cell.setCellValue("兑换时间");
		cell.setCellStyle(style);

		cell = row.createCell(16);
		cell.setCellValue("使用时间");
		cell.setCellStyle(style);

		cell = row.createCell(17);
		cell.setCellValue("新增资产");
		cell.setCellStyle(style);

		cell = row.createCell(18);
		cell.setCellValue("用户渠道ID");
		cell.setCellStyle(style);

		excelTitle = "兑换数据统计";

		List<ExchangeCodeRespVO> list = couponService.findExchangeCodeList(exchangeCodeReqVo);

		for (int i = 0; i < list.size(); i++) {
			ExchangeCodeRespVO exchangeCode = list.get(i);
			row = sheet.createRow(i + 1);
			//创建单元格，并设置值
			row.createCell(0).setCellValue(null == exchangeCode.getSerialNum() ? "" : exchangeCode.getSerialNum());
			row.createCell(1).setCellValue(exchangeCode.getExchangeCode());

			row.createCell(2).setCellValue(exchangeCode.getCardName());
			String cardType = "";
			if (exchangeCode.getCardType() != null) {
				if (exchangeCode.getCardType() == 1) {
					cardType = "免佣券";
				}
				if (exchangeCode.getCardType() == 2) {
					cardType = "现金券";
				}
				if (exchangeCode.getCardType() == 4) {
					cardType = "赠股";
				}
				if (exchangeCode.getCardType() == 5) {
					cardType = "积分";
				}
			}
			row.createCell(3).setCellValue(cardType);
			row.createCell(4).setCellValue(exchangeCode.getManageId());
			String channelId = exchangeCode.getChannelId();
			if (exchangeCode.getChannelId() != null) {
				if (exchangeCode.getChannelId().equalsIgnoreCase("-1")) {
					channelId = "不限";
				}
			}

			row.createCell(5).setCellValue(channelId);
			String useType = "";

			if (exchangeCode.getUseType() != null) {
				if (exchangeCode.getUseType() == 1) {
					useType = "首次开户";
				}
				if (exchangeCode.getUseType() == 2) {
					useType = "首次入金";
				}
				if (exchangeCode.getUseType() == 3) {
					useType = "首次转仓";
				}
				if (exchangeCode.getUseType() == 8) {
					useType = "入金金额";
				}
				if (exchangeCode.getUseType() == 9) {
					useType = "首次打新";
				}
			}

			row.createCell(6).setCellValue(useType);

			row.createCell(7).setCellValue(exchangeCode.getAmount() == null ? "" : exchangeCode.getAmount().toString());

			row.createCell(8).setCellValue(exchangeCode.getUseUid() == null ? "" : exchangeCode.getUseUid().toString());
			row.createCell(9).setCellValue(exchangeCode.getPhoneNumber());
			row.createCell(10).setCellValue(exchangeCode.getRecordStatus() == 0 ? "未兑换" : "已兑换");
			row.createCell(11).setCellValue(DateUtil.format(exchangeCode.getExpiredTime(), "yyyy/MM/dd"));
			row.createCell(12).setCellValue(DateUtil.format(exchangeCode.getRegisterDate(), "yyyy/MM/dd"));
			row.createCell(13).setCellValue(DateUtil.format(exchangeCode.getOpenDate(), "yyyy/MM/dd"));
			row.createCell(14).setCellValue(DateUtil.format(exchangeCode.getDepositDate(), "yyyy/MM/dd"));
			row.createCell(15).setCellValue(DateUtil.format(exchangeCode.getRecordDate(), "yyyy/MM/dd"));
			row.createCell(16).setCellValue(DateUtil.format(exchangeCode.getUseDate(), "yyyy/MM/dd"));
			row.createCell(17).setCellValue(exchangeCode.getAddAmount() == null ? "" : exchangeCode.getAddAmount().toString());
			String userChannelId = null;
			/* todo 以下逻辑需要依赖用户相关信息表
			if (null != exchangeCode.getUseUid()) {
				UserInfo userInfo = userInfoDao.findUserInfoByUserId(exchangeCode.getUseUid());
				if (null != userInfo && null != userInfo.getUserSourceChannelId()) {
					userChannelId = userInfo.getUserSourceChannelId();
				}
			}*/
			row.createCell(18).setCellValue(userChannelId == null ? "" : userChannelId);
		}

		try {
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流

			response.setContentType("application/msexcel");
			response.setHeader("Content-disposition", "attachment; filename=" + new String((excelTitle).getBytes("utf-8"), "iso8859-1") + ".xlsx");

			wb.write(os);
			os.close();
		} catch (IOException e) {
			log.error("导出优惠券兑换码列表失败,异常信息->", e);
			R.fail("导出优惠券兑换码列表失败!");
		}
		return R.success("导出优惠券兑换码列表成功!");
	}

	/**
	 * 查询优惠券兑换码列表
	 *
	 * @param exchangeCodeReqVo
	 * @return
	 */
	@GetMapping("/exchange_code_data")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "查询优惠券兑换码列表", notes = "传入exchangeCodeReqVo")
	public R<IPage<SnCouponExchangeCodeDataEntity>> findExchangeCodeDataPage(Query query, ExchangeCodeReqVO exchangeCodeReqVo) {
		return R.data(couponService.findCouponExchangeCodeDataPage(Condition.getPage(query), exchangeCodeReqVo));
	}

	/**
	 * 导出优惠券兑换码列表
	 *
	 * @param exchangeCodeReqVo
	 * @return
	 */
	@GetMapping("/exchange_code_data_export")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "导出优惠券兑换码列表", notes = "传入exchangeCodeReqVo")
	public R exchangeCodeData(HttpServletResponse response, ExchangeCodeReqVO exchangeCodeReqVo) {
		if (exchangeCodeReqVo.getStartDate() == null && exchangeCodeReqVo.getEndDate() == null) {
			return R.fail("导出数据开始日期、截止日期参数不能为空!");
		}
		List<SnCouponExchangeCodeDataEntity> list = couponService.findCouponExchangeCodeDataList(exchangeCodeReqVo);
		// 创建一个webbook，对应一个Excel文件
		Workbook wb = new XSSFWorkbook();
		// 在webbook中添加一个sheet,对应Excel文件中的sheet
		Sheet sheet = wb.createSheet("兑换码数据统计");
		// 在sheet中添加表头第0行
		Row row = sheet.createRow(0);
		// 创建单元格，并设置值表头 设置表头居中
		CellStyle style = wb.createCellStyle();
		// 设置水平居中
		style.setAlignment(HorizontalAlignment.CENTER);

		Cell cell = row.createCell(0);
		cell.setCellValue("渠道ID");
		cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue("批次ID");
		cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue("兑换人数");
		cell.setCellStyle(style);

		cell = row.createCell(3);
		cell.setCellValue("使用人数");
		cell.setCellStyle(style);

		cell = row.createCell(4);
		cell.setCellValue("卡券名称");
		cell.setCellStyle(style);

		cell = row.createCell(5);
		cell.setCellValue("卡券类型");
		cell.setCellStyle(style);

		cell = row.createCell(6);
		cell.setCellValue("首次开户数");
		cell.setCellStyle(style);

		cell = row.createCell(7);
		cell.setCellValue("首次入金数");
		cell.setCellStyle(style);

		cell = row.createCell(8);
		cell.setCellValue("首次入金金额");
		cell.setCellStyle(style);

		cell = row.createCell(9);
		cell.setCellValue("首次转仓数");
		cell.setCellStyle(style);

		cell = row.createCell(10);
		cell.setCellValue("首次转仓金额");

		String excelTitle = "兑换数据统计";

		for (int i = 0; i < list.size(); i++) {
			SnCouponExchangeCodeDataEntity exchangeCode = list.get(i);
			row = sheet.createRow(i + 1);
			//创建单元格，并设置值
			String channelId = exchangeCode.getChannelId();
			if (channelId.equals("-1")) {
				channelId = "不限";
			}
			row.createCell(0).setCellValue(channelId);
			row.createCell(1).setCellValue(exchangeCode.getManageId());
			row.createCell(2).setCellValue(exchangeCode.getExchangePersonNumber());
			row.createCell(3).setCellValue(exchangeCode.getUsePersonNumber());
			row.createCell(4).setCellValue(exchangeCode.getCardName());
			String cardType = "";
			if (exchangeCode.getCardType() == 1) {
				cardType = "免佣券";
			}
			if (exchangeCode.getCardType() == 2) {
				cardType = "现金券";
			}
			if (exchangeCode.getCardType() == 4) {
				cardType = "赠股";
			}
			if (exchangeCode.getCardType() == 5) {
				cardType = "积分";
			}
			row.createCell(5).setCellValue(cardType);
			row.createCell(6).setCellValue(exchangeCode.getFirstOpenAccountNumber());
			row.createCell(7).setCellValue(exchangeCode.getFirstDepositFundNumber());
			row.createCell(8).setCellValue(exchangeCode.getFirstDepositFundAmount().toString());
			row.createCell(9).setCellValue(exchangeCode.getFirstTransferNumber());
			row.createCell(10).setCellValue(exchangeCode.getFirstTransferAmount().toString());

		}

		try {
			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流
			response.setContentType("application/msexcel");
			response.setHeader("Content-disposition", "attachment; filename=" + new String((excelTitle).getBytes("utf-8"), "iso8859-1") + ".xlsx");
			wb.write(os);
			os.close();
		} catch (IOException e) {
			log.error("导出优惠券兑换码列表失败,异常详情->", e);
			R.fail("导出优惠券兑换码列表失败!");
		}
		return R.success("导出优惠券兑换码列表成功!");
	}

	/**
	 * 导入兑换码匹配列表
	 *
	 * @return
	 */
	@PostMapping("/import_match_excel")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "导入兑换码匹配列表", notes = "Excel文件流")
	public R importMatchExcel(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		try {
			List<SnCouponManageMatchEntity> snCouponManageMatcheList = couponService.findSnCouponManageMatchList();
			if (CollectionUtils.isNotEmpty(snCouponManageMatcheList)) {
				couponService.deleteSnCouponManageMatchList(snCouponManageMatcheList);
			}
			//读取excel
			InputStream inputStream = file.getInputStream();
			Workbook inputWb = WorkbookFactory.create(inputStream);
			Sheet readSheet = inputWb.getSheetAt(0);
			String type = readSheet.getRow(0).getCell(0).getStringCellValue();
			List<String> stringList = new LinkedList<String>();
			for (Row row : readSheet) {
				if ("卡号".equals(type)) {
					row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				}
				String stringCellValue = row.getCell(0).getStringCellValue();
				if (!("".equals(stringCellValue)) && null != stringCellValue) {
					stringList.add(stringCellValue);
				}
			}
			if (CollectionUtils.isNotEmpty(stringList)) {
				stringList.remove(0);
				String[] stringArray = stringList.toArray(new String[stringList.size()]);
				List<SnCouponManageMatchVO> snCouponManageMatchVoList = null;
				if ("卡号".equals(type)) {
					snCouponManageMatchVoList = couponService.findSnCouponManageMatchSerialNumData(stringArray);
				} else if ("卡密".equals(type)) {
					snCouponManageMatchVoList = couponService.findSnCouponManageMatchCodeData(stringArray);
				}
				//入库
				if (CollectionUtils.isNotEmpty(snCouponManageMatchVoList)) {
					List<SnCouponManageMatchEntity> snCouponManageMatchList = new ArrayList<SnCouponManageMatchEntity>();
					for (SnCouponManageMatchVO snCouponManageMatchVo : snCouponManageMatchVoList) {
						SnCouponManageMatchEntity snCouponManageMatch = new SnCouponManageMatchEntity();
						BeanUtil.copyProperties(snCouponManageMatchVo, snCouponManageMatch);
						if (null != snCouponManageMatchVo.getDepositMoney()) {
							snCouponManageMatch.setDepositMoney(BigDecimal.valueOf(snCouponManageMatchVo.getDepositMoney()));
						}
						snCouponManageMatchList.add(snCouponManageMatch);
					}
					if (CollectionUtils.isNotEmpty(snCouponManageMatchList)) {
						couponService.saveSnCouponManageMatchList(snCouponManageMatchList);
					}
				}
			}
		} catch (Exception e) {
			log.error("导入兑换码匹配列表异常,异常信息->", e);
			e.printStackTrace();
			return R.fail("导入兑换码匹配列表异常!");
		}
		return R.success("导入兑换码匹配列表成功!");
	}

	/**
	 * 导出兑换码匹配列表
	 *
	 * @return
	 */
	@GetMapping("/export_match_excel")
	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "导出优惠券兑换码列表", notes = "无需传参")
	public R exportMatchExcel(HttpServletResponse response) {
		try {
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet("兑换码匹配");
			Row row = sheet.createRow(0);
			CellStyle style = wb.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);

			String excelTitle = "";

			Cell cell = row.createCell(0);
			cell.setCellValue("序列号");
			cell.setCellStyle(style);

			cell = row.createCell(1);
			cell.setCellValue("激活码");
			cell.setCellStyle(style);

			cell = row.createCell(2);
			cell.setCellValue("有鱼号");
			cell.setCellStyle(style);

			cell = row.createCell(3);
			cell.setCellValue("用户渠道id");
			cell.setCellStyle(style);

			cell = row.createCell(4);
			cell.setCellValue("激活时间");
			cell.setCellStyle(style);

			cell = row.createCell(5);
			cell.setCellValue("使用时间");
			cell.setCellStyle(style);

			cell = row.createCell(6);
			cell.setCellValue("开户时间");
			cell.setCellStyle(style);

			cell = row.createCell(7);
			cell.setCellValue("入金时间");
			cell.setCellStyle(style);

			cell = row.createCell(8);
			cell.setCellValue("首日入金金额");
			cell.setCellStyle(style);

			excelTitle = "兑换码匹配";

			List<SnCouponManageMatchEntity> snCouponManageMatches = couponService.findSnCouponManageMatchList();
			if (CollectionUtils.isNotEmpty(snCouponManageMatches)) {
				for (int i = 0; i < snCouponManageMatches.size(); i++) {
					SnCouponManageMatchEntity snCouponManageMatch = snCouponManageMatches.get(i);
					row = sheet.createRow(i + 1);
					//创建单元格，并设置值
					row.createCell(0).setCellValue(snCouponManageMatch.getSerialNum());
					row.createCell(1).setCellValue(snCouponManageMatch.getCode());
					row.createCell(2).setCellValue(snCouponManageMatch.getUserId());
					row.createCell(3).setCellValue(snCouponManageMatch.getChannelId());
					row.createCell(4).setCellValue(snCouponManageMatch.getRecordDate() == null ? null : DateUtil.format(snCouponManageMatch.getRecordDate(), "yyyy/MM/dd h:mm:ss"));
					row.createCell(5).setCellValue(snCouponManageMatch.getUseDate() == null ? null : DateUtil.format(snCouponManageMatch.getUseDate(), "yyyy/MM/dd h:mm:ss"));
					row.createCell(6).setCellValue(snCouponManageMatch.getOpenDate() == null ? null : DateUtil.format(snCouponManageMatch.getOpenDate(), "yyyy/MM/dd h:mm:ss"));
					row.createCell(7).setCellValue(snCouponManageMatch.getDepositDate() == null ? null : DateUtil.format(snCouponManageMatch.getDepositDate(), "yyyy/MM/dd h:mm:ss"));
					if (null == snCouponManageMatch.getDepositMoney()) {
						row.createCell(8).setCellValue("");
					} else {
						row.createCell(8).setCellValue(snCouponManageMatch.getDepositMoney().doubleValue());
					}
				}
			}

			OutputStream os = response.getOutputStream();
			response.reset();// 清空输出流

			response.setContentType("application/msexcel");
			response.setHeader("Content-disposition", "attachment; filename=" + new String((excelTitle).getBytes("utf-8"), "iso8859-1") + ".xlsx");

			wb.write(os);
			os.close();

			List<SnCouponManageMatchEntity> snCouponManageMatcheList = couponService.findSnCouponManageMatchList();
			if (CollectionUtils.isNotEmpty(snCouponManageMatcheList)) {
				couponService.deleteSnCouponManageMatchList(snCouponManageMatcheList);
			}
		} catch (Exception e) {
			log.error("导出兑换码匹配列表失败,异常详情->", e);
			e.printStackTrace();
			return R.fail("导出兑换码匹配列表失败!");
		}
		return R.success("导出兑换码匹配列表成功!");
	}
}
