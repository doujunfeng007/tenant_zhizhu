package com.minigod.zero.dbs.web.controller;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.dbs.service.DbsCallBackService;
import com.minigod.zero.dbs.web.filter.MultiReadHttpServletRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Dbs银行回调接口 ICN IDN Remit/Ack  Edda/Ack2
 *
 * @author chenyu
 * @title DbsBankCallback
 * @date 2023-04-12 7:30
 * @description
 */
@Slf4j
@ApiModel
@ConditionalOnProperty(prefix = "bank.dbs", name = "enabled", havingValue = "true")
@RequiredArgsConstructor
@RestController
@RequestMapping("/callback/api/")
@Api(value = "Dbs银行回调接口", tags = {"Dbs银行回调接口"})
public class DbsBankCallbackController {

    @Autowired
    private DbsCallBackService dbsCallBackService;

    /**
     * DBS icn
     *
     * @param request
     * @return
     */
    @ApiOperation("ICN接口")
    @PostMapping("/dbs/icn")
    public R icn(HttpServletRequest request) {
        try {
            MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest(request);
            String bodyStr = this.getJsonParams(multiReadRequest);
            log.info("callback dbs bank icn data：" + bodyStr);
            if(StringUtils.isEmpty(bodyStr)){
                return R.fail();
            }
            // 去掉BGP加密的注释
			bodyStr = bodyStr.replaceFirst("Version: BCPG v\\d{1}.\\d{1,2}", "");
			bodyStr = bodyStr.replaceFirst(
				"-----END PGP MESSAGE-----", "");
			bodyStr = bodyStr.replaceFirst(
				"-----BEGIN PGP MESSAGE-----", "");
            return dbsCallBackService.icn(bodyStr);
        } catch (Exception e) {
            log.error("处理通知异常", e);
            return R.fail(e.getMessage());
        }
    }

    /**
     *  DBS edda/ack2
     *
     * @param request
     * @return
     */
    @ApiOperation("Edda ACK通知")
    @PostMapping("/dbs/edda/ack2")
    public R eddaAck2(HttpServletRequest request) {
        try {

            MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest(request);
            String bodyStr = this.getJsonParams(multiReadRequest);
            log.info("callback dbs bank edda-ack2 data：" + bodyStr);
            if(StringUtils.isEmpty(bodyStr)){
                return R.fail();
            }
            // 去掉BGP加密的注释
            String body = bodyStr.replaceFirst("Version: BCPG v\\d{1}.\\d{1,2}", "");
			bodyStr = bodyStr.replaceFirst("Version: BCPG v\\d{1}.\\d{1,2}", "");
			bodyStr = bodyStr.replaceFirst(
				"-----END PGP MESSAGE-----", "");
			bodyStr = bodyStr.replaceFirst(
				"-----BEGIN PGP MESSAGE-----", "");

            return dbsCallBackService.eddaAck2(bodyStr);
        } catch (Exception e) {
            log.error("处理通知异常", e);
            return R.fail();
        }
    }

    /**
     * DBS remit/ack
     *
     * @param request
     * @return
     */
    @ApiOperation("RemitACK通知")
    @PostMapping("/dbs/remit/ack")
    public R remitAck(HttpServletRequest request) {
        try {

            MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest(request);
            String bodyStr = this.getJsonParams(multiReadRequest);
            log.info("callback dbs bank remit-ack data：" + bodyStr);
            if(StringUtils.isEmpty(bodyStr)){
                return R.fail();
            }
            // 去掉BGP加密的注释
            String body = bodyStr.replaceFirst("Version: BCPG v\\d{1}.\\d{1,2}", "");
			bodyStr = bodyStr.replaceFirst("Version: BCPG v\\d{1}.\\d{1,2}", "");
			bodyStr = bodyStr.replaceFirst(
				"-----END PGP MESSAGE-----", "");
			bodyStr = bodyStr.replaceFirst(
				"-----BEGIN PGP MESSAGE-----", "");
            return dbsCallBackService.remitAck(bodyStr);
        } catch (Exception e) {
            log.error("处理通知异常", e);
            return R.fail();
        }
    }

    /**
     * DBS idn
     *
     * @param request
     * @return
     */
    @ApiOperation("IDN通知")
    @PostMapping("/dbs/idn")
    public R idn(HttpServletRequest request) {
        try {

            MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest(request);
            String bodyStr = this.getJsonParams(multiReadRequest);
            log.info("callback dbs idn bank data：" + bodyStr);
            if(StringUtils.isEmpty(bodyStr)){
                return R.fail();
            }
            // 去掉BGP加密的注释
            String body = bodyStr.replaceFirst("Version: BCPG v\\d{1}.\\d{1,2}", "");
			bodyStr = bodyStr.replaceFirst("Version: BCPG v\\d{1}.\\d{1,2}", "");
			bodyStr = bodyStr.replaceFirst(
				"-----END PGP MESSAGE-----", "");
			bodyStr = bodyStr.replaceFirst(
				"-----BEGIN PGP MESSAGE-----", "");
			return dbsCallBackService.idn(bodyStr);
        } catch (Exception e) {
            log.error("处理通知异常", e);
            return R.fail();
        }
    }

    /**
     * 获取json参数
     *
     * @param multiReadRequest
     * @return
     */
    private String getJsonParams(MultiReadHttpServletRequest multiReadRequest) {
        String json = null;
        try {
            json = IOUtils.toString(multiReadRequest.getInputStream());
        } catch (IOException e) {
            log.error("Callback get http inputstream error!", e);
        }
        return json;
    }
}
