package com.minigod.zero.bpmn.module.withdraw.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.bpmn.module.common.group.AddGroup;
import com.minigod.zero.bpmn.module.common.group.EditGroup;
import com.minigod.zero.bpmn.module.common.group.QueryGroup;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientBankInfoBo;
import com.minigod.zero.bpmn.module.withdraw.bo.ClientBankInfoExcBO;
import com.minigod.zero.bpmn.module.withdraw.entity.ClientBankInfo;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import com.minigod.zero.bpmn.module.withdraw.service.IClientBankInfoService;
import com.minigod.zero.core.excel.util.ExcelUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 银行信息记录Controller
 *
 * @author marty
 * @date 2023-04-07
 */
@Validated
@Api(value = "香港银行SwiftCode信息记录控制器", tags = {"香港银行SwiftCode信息管理"})
@RequiredArgsConstructor
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/withdraw/clientBankInfo")
public class ClientBankInfoController {

    private final IClientBankInfoService iClientBankInfoService;

    /**
     * 查询银行信息记录列表
     */
    @ApiOperation("查询银行信息记录列表")
    //@SaCheckPermission("withdraw:clientBankInfo:list")
    @GetMapping("/list")
    public R<IPage<ClientBankInfo>> list(@Validated(QueryGroup.class) ClientBankInfoBo bo, Query query) {
        return R.data(iClientBankInfoService.queryPageList(Condition.getPage(query),bo));
    }


    /**
     * 获取银行信息记录详细信息
     */
    @ApiOperation("获取银行信息记录通过ID")
    //@SaCheckPermission("withdraw:clientBankInfo:getInfo")
    @GetMapping("/{id}")
    public R<ClientBankInfo> getInfo(@ApiParam("流水号")
                                       @NotNull(message = "流水号不能为空")
                                       @PathVariable("id") Long id) {
        return R.data(iClientBankInfoService.queryById(id));
    }

    /**
     * 获取银行信息记录详细信息
     */
    @ApiOperation("获取银行信息记录详细信息")
    //@SaCheckPermission("withdraw:clientBankInfo:queryEntity")
    @PostMapping("/queryEntity")
    public R<ClientBankInfo> queryEntity(@Validated(QueryGroup.class) ClientBankInfoBo bo) {
        return R.data(iClientBankInfoService.queryEntity(bo));
    }

    /**
     * 修改银行信息记录
     */
    @ApiOperation("修改银行信息记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "流水号", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "swiftCode", value = "SWIFT代码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bankId", value = "银行编号", required = true, dataType = "String", paramType = "query"),
    })
    //@SaCheckPermission("withdraw:clientBankInfo:edit")
    ////@Log(title = "香港银行SwiftCode信息记录", businessType = BusinessType.UPDATE)
    //@RepeatSubmit()
    @PostMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) ClientBankInfoBo bo) {
        ClientBankInfoBo updateBo = new ClientBankInfoBo();
        updateBo.setId(bo.getId());
        updateBo.setBankCode(bo.getBankCode());
        updateBo.setBankName(bo.getBankName());
        updateBo.setSwiftCode(bo.getSwiftCode());
        updateBo.setBankId(bo.getBankId());
        return R.status(iClientBankInfoService.updateByBo(updateBo));
    }

    /**
     * 新增银行信息记录
     */
    @ApiOperation("新增银行信息记录")
    //@SaCheckPermission("withdraw:clientBankInfo:add")
    //@Log(title = "香港银行SwiftCode信息记录", businessType = BusinessType.INSERT)
    //@RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody ClientBankInfoBo bo) {
        return R.data(iClientBankInfoService.commitApply(bo));
    }

    /**
     * 删除银行信息记录
     */
    @ApiOperation("删除银行信息记录")
    //@SaCheckPermission("withdraw:clientBankInfo:remove")
    //@Log(title = "删除银行信息记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@ApiParam("主键串")
                          @NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return R.status(iClientBankInfoService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 香港银行导出
     */
    @ApiOperation("香港银行信息导出")
    //@SaCheckPermission("withdraw:clientBankInfo:export")
    ////@Log(title = "香港银行信息导出", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        List<ClientBankInfo> dataList = iClientBankInfoService.queryList(new ClientBankInfoBo());
		ArrayList<ClientBankInfoExcBO> excBOArrayList = new ArrayList<>();
        try {
			for (ClientBankInfo info : dataList) {
				ClientBankInfoExcBO bo = new ClientBankInfoExcBO();
				bo.setBankName(info.getBankName());
				bo.setBankCode(info.getBankCode());
				bo.setSwiftCode(info.getSwiftCode());
				bo.setBankId(info.getBankId());

				excBOArrayList.add(bo);
			}
        } catch (Exception e) {
			throw new ServiceException("导出失败");
        }

        if (excBOArrayList == null || excBOArrayList.size() == 0) {
            throw new ServiceException("未查到记录");
        }
        // 模板文件
        String templateFileName = SystemCommonEnum.TemplateFileTypeEnum.BANKINFO_ITEM_TEMPLATE.getName();
        response.setContentType("octets/stream");
        ExcelUtil.export(response,"香港银行信息","香港银行信息",excBOArrayList,ClientBankInfoExcBO.class);
    }

}
