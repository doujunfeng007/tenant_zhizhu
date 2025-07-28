package com.minigod.zero.dbs.web.mock;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.dbs.bo.FundTransferEntity;
import com.minigod.zero.dbs.service.DbsMockApiBusinessService;
import com.minigod.zero.dbs.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.minigod.zero.dbs.service.DbsApiBusinessService.*;

/**
 * @Description: 业务接口
 * @ClassName: DbsBankController
 * @Author chenyu
 * @Version 1.0
 */
@Slf4j
@Validated
@ConditionalOnProperty(prefix = "bank.dbs", name = "enabled", havingValue = "true")
@RestController
@AllArgsConstructor
@RequestMapping("/proxy/mock/api")
@Api(value = "DBS业务模拟接口", tags = {"DBS业务模拟接口"})
public class BankMockApiProxyController {

    private final DbsMockApiBusinessService dbsApiBusinessService;

    /**
     * DBS BLE
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询银行账户余额")
    @PostMapping(BLE)
    public R ble(@RequestBody AccountBalanceRequestVO reqVo, HttpServletRequest request) {
        return dbsApiBusinessService.sendBleQuery(reqVo);
    }

    /**
     * DBS EDDA Initiation
     *
     * @param request
     * @return
     */
    @ApiOperation("EDDA 申请")
    @PostMapping(EDDA_SETUP)
    public R eddaSetup(@RequestBody EddaInfoRequestVO reqVo, HttpServletRequest request) {

        return dbsApiBusinessService.sendEDDAInitiation(reqVo);
    }

    /**
     * 子账户VAC
     *
     * @param request
     * @return
     */
    @ApiOperation("子账号操作")
    @PostMapping(VAC)
    public R vac(@RequestBody SubAccountRequestVO reqVo, HttpServletRequest request) {

        return dbsApiBusinessService.sendProvisionQuery(reqVo);

    }

    /**
     * Fps Gpc付款
     *
     * @param request
     * @return
     */
    @ApiOperation("Fps Gpc付款")
    @PostMapping(FPS_GPC)
    public R fpsGpc(@RequestBody FpsTransactionRequestVO reqVo, HttpServletRequest request) {

        return dbsApiBusinessService.sendFpsGpcFund(reqVo);

    }

    @ApiOperation("Fps Gps付款")
    @PostMapping(FPS_GPP)
    public R fpsGpp(@RequestBody FundTransferEntity reqVo, HttpServletRequest request) {

        return dbsApiBusinessService.sendFpsGppFund(reqVo);

    }

    @ApiOperation("Fps Ppp付款")
    @PostMapping(FPS_PPP)
    public R fpsPpp(@RequestBody FundTransferEntity reqVo, HttpServletRequest request) {

        return dbsApiBusinessService.sendFpsPPPFund(reqVo);

    }

    @ApiOperation("银行转账(Dbs Act 同行转账)")
    @PostMapping(ACT)
    public R act(@RequestBody FundTransferEntity reqVo, HttpServletRequest request) {

        return dbsApiBusinessService.sendActFund(reqVo);

    }

    @ApiOperation("银行转账(Dbs RFD 退款)")
    @PostMapping(RFD)
    public R rfd(@RequestBody FundTransferEntity reqVo, HttpServletRequest request) {

        return dbsApiBusinessService.sendRFDFund(reqVo);

    }

    @ApiOperation("银行转账(Dbs CHATS/RTGS 跨行转账)")
    @PostMapping(CHATS)
    public R chats(@RequestBody FundTransferEntity reqVo, HttpServletRequest request) {

        return dbsApiBusinessService.sendChatsFund(reqVo);

    }


    @ApiOperation("银行转账(Dbs TT 海外转账)")
    @PostMapping(TT)
    public R tt(@RequestBody FundTransferEntity reqVo, HttpServletRequest request) {

        return dbsApiBusinessService.sendTTFund(reqVo);

    }


    /**
     * DBS EDDA Enquiry
     *
     * @param request
     * @return
     */
    @ApiOperation("EDDA 查询")
    @PostMapping(EDDA_ENQ)
    public R eddaEnq(@RequestBody EddaTransactionEnquiryRequestVO reqVo, HttpServletRequest request) {

        return dbsApiBusinessService.sendEDDAEnquiry(reqVo);

    }

    @ApiOperation("Tse 查询汇款交易状态")
    @PostMapping(TSE)
    public R tse(@RequestBody TseTransactionEnquiryRequestVO reqVo, HttpServletRequest request) {

        return dbsApiBusinessService.sendTseEnquiry(reqVo);

    }

    /**
     * DBS CAMT53
     *
     * @param request
     * @return
     */
    @ApiOperation("DBS CAMT53 日报")
    @PostMapping(CAMT53)
    public R camt53(@RequestBody Camt053ReportRequestVO reqVo, HttpServletRequest request) {

        return dbsApiBusinessService.sendCamt053(reqVo);

    }

    /**
     * DBS CAMT52
     *
     * @param request
     * @return
     */
    @ApiOperation("DBS CAMT52 小时表")
    @PostMapping(CAMT52)
    public R camt52(@RequestBody Camt052ReportRequestVO reqVo, HttpServletRequest request) {
        return dbsApiBusinessService.sendCamt052(reqVo);

    }

    /**
     * FPS ID 查询
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Fps Id查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "applicationId", value = "应用流水号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "bankNo", value = "FpsId", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "hsBankAccount", value = "银行账号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "moneyType", value = "币种代码[0-人民币 1-美元 2-港币]", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "bankAccountType", value = "银行账号类型[1-银行卡 2-fps id 3-fps-手机号码 4-fps-邮箱]", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "clientNameSpell", value = "银行账号账户名称", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(FPS_ID_ENQUIRY)
    public R fpsIdEnquiry(FundTransferEntity fundWithdrawApplyEntity, HttpServletRequest request) {

        return dbsApiBusinessService.sendFpsIdEnquiryFund(fundWithdrawApplyEntity);
    }


}
