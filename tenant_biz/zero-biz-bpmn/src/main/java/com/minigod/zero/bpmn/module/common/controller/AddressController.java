package com.minigod.zero.bpmn.module.common.controller;

import com.minigod.zero.bpmn.module.common.service.AddressService;
import com.minigod.zero.bpmn.module.common.bo.QueryAddressCode;
import com.minigod.zero.bpmn.module.common.vo.address.Province;
import org.springframework.web.bind.annotation.*;

import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;

import com.minigod.zero.core.tool.api.R;

import java.util.List;

/**
 * 地址表(sys_address)表控制层
 *
 * @author chenyu
 */
@Validated
@RestController
@AllArgsConstructor
@Api(value = "地址表", tags = "地址表接口")
@RequestMapping(AppConstant.BACK_API_PREFIX + "/common/address")
public class AddressController extends ZeroController {
    private final AddressService addressService;

    @ApiOperation("查询地址列表")
    @GetMapping("/getAddressList")
    public R<List<Province>> getAddressList(QueryAddressCode queryAddressCode) {
        List<Province> list = addressService.getAddressList(queryAddressCode);
        return R.data(list);
    }


    @ApiOperation("清理地址缓存")
    @GetMapping("/resetCache")
    public R<Object> resetCache() {
        addressService.resetCache();
        return R.success();
    }
}
