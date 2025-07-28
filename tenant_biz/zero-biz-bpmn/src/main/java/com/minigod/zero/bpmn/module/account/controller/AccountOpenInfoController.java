package com.minigod.zero.bpmn.module.account.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.bpmn.module.account.vo.BackCustomerDetailVO;
import com.minigod.zero.bpmn.module.account.vo.CustomerOpenAccountVO;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.biz.common.utils.FileUtil;
import com.minigod.zero.bpmn.module.account.service.IAccountAdditionalFileService;
import com.minigod.zero.bpmn.module.account.service.IAccountOpenInfoService;
import com.minigod.zero.bpmn.module.account.service.impl.CustomerAccOpenReportGenerate;
import com.minigod.zero.bpmn.utils.FileUtils;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.utils.Func;
import com.minigod.zero.bpmn.module.account.entity.AccountOpenInfoEntity;
import com.minigod.zero.bpmn.module.account.enums.AccountPdfEnum;
import com.minigod.zero.bpmn.module.account.vo.AccountOpenInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.io.File;
import java.util.List;

import static com.minigod.zero.bpmn.module.account.enums.AccountPdfEnum.ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT;
import static com.minigod.zero.bpmn.module.account.enums.AccountPdfEnum.ACCOUNT_OPEN_REPORT_USER_W8_REPORT;


/**
 * 开户信息处理控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/account/accountOpenInfo")
@Api(value = "", tags = "")
public class AccountOpenInfoController extends ZeroController {

    private final IAccountOpenInfoService accountOpenInfoService;
    private final CustomerAccOpenReportGenerate customerAccOpenReportGenerate;
    private final IAccountAdditionalFileService openAccountAdditionalFileService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入account_open_info")
    public R<AccountOpenInfoEntity> detail(AccountOpenInfoEntity account_open_info) {
        AccountOpenInfoEntity detail = accountOpenInfoService.getOne(Condition.getQueryWrapper(account_open_info));
        return R.data(detail);
    }

    /**
     * 分页
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入account_open_info")
    public R<IPage<AccountOpenInfoEntity>> list(AccountOpenInfoEntity account_open_info, Query query) {
        IPage<AccountOpenInfoEntity> pages = accountOpenInfoService.page(Condition.getPage(query), Condition.getQueryWrapper(account_open_info));
        return R.data(pages);
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入account_open_info")
    public R save(@Valid @RequestBody AccountOpenInfoEntity account_open_info) {
        return R.status(accountOpenInfoService.save(account_open_info));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入account_open_info")
    public R update(@Valid @RequestBody AccountOpenInfoEntity account_open_info) {
        return R.status(accountOpenInfoService.updateById(account_open_info));
    }

    /**
     * 新增或修改
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入account_open_info")
    public R submit(@Valid @RequestBody AccountOpenInfoEntity account_open_info) {
        return R.status(accountOpenInfoService.saveOrUpdate(account_open_info));
    }


    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(accountOpenInfoService.deleteLogic(Func.toLongList(ids)));
    }

    /**
     * CA认证
     *
     * @param applicationId
     * @return
     */
    @PostMapping("/caAuth/{applicationId}")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "CA认证", notes = "传预约号ApplicationId")
    public R caAuthTask(@ApiParam("预约号")
                        @NotNull(message = "预约号不能为空")
                        @PathVariable("applicationId") String applicationId) {
        try {
            accountOpenInfoService.gdCaAuth(applicationId);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.success();
    }

    @ApiOperation("获取客户开户详细资料详细信息")
    @GetMapping("/{applicationId}")
    public R<AccountOpenInfoVO> getInfo(@ApiParam("预约号")
                                        @NotNull(message = "预约号不能为空")
                                        @PathVariable("applicationId") String applicationId) {
        return R.data(accountOpenInfoService.queryByApplicationId(applicationId));
    }

    @ApiOperation("归档")
    @GetMapping("/placeInfo/{applicationId}/{type}")
    public R<Void> placeInfo(@ApiParam("流水号")
                             @NotEmpty(message = "流水号不能为空")
                             @PathVariable("applicationId") String applicationId,
                             @PathVariable("type") Integer type) {
        AccountPdfEnum accountPdfEnum = AccountPdfEnum.find(type);
        String path = customerAccOpenReportGenerate.generateReport(applicationId, accountPdfEnum);
        if (StringUtils.isNotBlank(path)) {
            openAccountAdditionalFileService.deleteByApplicationAndTypeAndFileType(applicationId, 3, accountPdfEnum.getValue());
            openAccountAdditionalFileService.uploadAdditionalFile(applicationId, 3, accountPdfEnum.getValue(), accountPdfEnum.getName(), FileUtil.getMultipartFile(FileUtils.file(path)));
        }
        return R.success();
    }

    @ApiOperation("修改客户号")
    @PostMapping("/setClientId/{applicationId}")
    public R<Void> setClientId(@ApiParam("流水号")
                               @NotBlank(message = "流水号不能为空")
                               @PathVariable("applicationId") String applicationId,
                               @NotBlank(message = "客户号不能为空")
                               @RequestParam("clientId") String clientId,
                               @RequestParam("idKind") Integer idKind) {
        accountOpenInfoService.updateClientId(applicationId, clientId, idKind);
        return R.success();
    }

    @ApiOperation("生成开户PDF文件")
    @PostMapping("/generateReport/{applicationId}")
    public R<Object> gen(@PathVariable("applicationId") String applicationId) {
        String path = customerAccOpenReportGenerate.generateReport(applicationId, ACCOUNT_OPEN_REPORT_IFUND_USER_FORM_REPORT);
        if (StringUtils.isNotBlank(path)) {
            File pdfFile = new File(path);
            if (pdfFile != null) {
                return R.data(String.format("生成PDF文件成功->路径:%s,名称:%s", pdfFile.getPath(), pdfFile.getName()));
            } else {
                return R.fail("生成失败!");
            }

        } else {
            return R.fail("生成失败!");
        }
    }

    @ApiOperation("生成开户各种文件文件")
    @PostMapping("/generateReport/{applicationId}/{type}")
    public R gen(@PathVariable("applicationId") String applicationId, @PathVariable("type") Integer type) {

		return  accountOpenInfoService.gen(applicationId, type);
    }
    @ApiOperation("补资料-生成w8和自我声明")
    @PostMapping("/generateReport/W8SelfDeclare")
    public R w8SelfDeclare() {
		//w8的applicationId

		return accountOpenInfoService.w8SelfDeclare();
    }

    @ApiOperation("下载开户PDF文件")
    @GetMapping("/downloadDoc/{applicationId}")
    public void downloadDoc(@ApiParam("流水号")
                            @NotBlank(message = "流水号不能为空")
                            @PathVariable("applicationId") String applicationId,
                            HttpServletResponse httpServletResponse) {
        accountOpenInfoService.downloadDoc(applicationId, httpServletResponse);
    }


	@GetMapping("/userList")
	public R<Page<CustomerOpenAccountVO>> openAccountUserList(String keyword, String startTime, String endTime,
															  Integer pageIndex, Integer pageSize){
		return R.data(accountOpenInfoService.openAccountUserList(keyword, startTime, endTime, new Page(Long.valueOf(pageIndex),Long.valueOf(pageSize))));
	}

	@GetMapping("/userDetail")
	public R<BackCustomerDetailVO> openAccountDetail(Long userId){
		return R.data(accountOpenInfoService.openAccountUserDetail(userId));
	}
}
