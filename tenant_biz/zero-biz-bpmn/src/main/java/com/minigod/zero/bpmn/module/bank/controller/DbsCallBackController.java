package com.minigod.zero.bpmn.module.bank.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.minigod.zero.bpmn.module.withdraw.service.DbsBankCallbackApiService;
import com.minigod.zero.dbs.bo.DbsTransaction;
import com.minigod.zero.dbs.bo.icn.IcnInfo;
import com.minigod.zero.dbs.bo.icn.IdnInfo;
import com.minigod.zero.dbs.service.DbsCallBackApi;
import com.minigod.zero.core.boot.ctrl.ZeroController;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 入金银行流水记录表(deposit_bank_bill_flow)表控制层
 *
 * @author chenyu
 */
@Validated
@Slf4j
@RestController
@AllArgsConstructor
@Api(value = "DBS网关回调接口", tags = "DBS网关回调接口")
@RequestMapping(AppConstant.PROXY_API_PREFIX)
public class DbsCallBackController extends ZeroController {
    @Autowired
    DbsBankCallbackApiService dbsBankCallbackApiService;

    @PostMapping(value = DbsCallBackApi.PATH + DbsCallBackApi.ICN,produces = MediaType.APPLICATION_JSON_VALUE)
    public R icn(@RequestBody String body) {
        String tenantId = getRequest().getHeader(DbsCallBackApi.TENANT_ID);
        log.info("{} icn body:{}", tenantId, body);
        DbsTransaction<IcnInfo> transaction = JSONObject.parseObject(body, new TypeReference<DbsTransaction<IcnInfo>>() {
        });
        dbsBankCallbackApiService.icn(tenantId, transaction);
        return R.success();
    }

    @PostMapping(value = DbsCallBackApi.PATH + DbsCallBackApi.IDN ,produces = MediaType.APPLICATION_JSON_VALUE)
    public R idn(@RequestBody String body) {
        String tenantId = getRequest().getHeader(DbsCallBackApi.TENANT_ID);
        log.info("{} idn body:{}", tenantId, body);
        DbsTransaction<IdnInfo> transaction = JSONObject.parseObject(body, new TypeReference<DbsTransaction<IdnInfo>>() {
        });
        dbsBankCallbackApiService.idn(tenantId, transaction);
        return R.success();
    }

    @PostMapping(value = DbsCallBackApi.PATH + DbsCallBackApi.REMIT_ACK, produces = MediaType.APPLICATION_JSON_VALUE)
    public R remitAck(@RequestBody String body) {
        String tenantId = getRequest().getHeader(DbsCallBackApi.TENANT_ID);
        log.info("{} remitAck body:{}", tenantId, body);
        dbsBankCallbackApiService.remitAck(tenantId, body);
        return R.success();
    }


    @PostMapping(value = DbsCallBackApi.PATH + DbsCallBackApi.EDDA_ACK2, produces = MediaType.APPLICATION_JSON_VALUE)
    public R eddaAck2(@RequestBody String body) {
        String tenantId = getRequest().getHeader(DbsCallBackApi.TENANT_ID);
        log.info("{} remitAck body:{}", tenantId, body);
        dbsBankCallbackApiService.eddaAck2(tenantId, body);
        return R.success();
    }

}
