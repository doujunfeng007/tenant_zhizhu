package com.minigod.zero.dbs.web.controller;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.dbs.bo.FundTransferEntity;
import com.minigod.zero.dbs.service.DbsApiBusinessService;
import com.minigod.zero.dbs.util.DbsCommManageUtils;
import com.minigod.zero.dbs.vo.*;
import com.minigod.zero.dbs.web.filter.MultiReadHttpServletRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
@RequestMapping("/proxy/api")
@Api(value = "DBS业务接口", tags = {"DBS业务接口"})
public class BankApiProxyController {

    private final DbsApiBusinessService dbsApiBusinessService;

    /**
     * DBS BLE
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询银行账户余额")
    @PostMapping(BLE)
    public R ble(@RequestBody AccountBalanceRequestVO reqVo, HttpServletRequest request) {
		log.info("DBS BLE 请求参数：{}", reqVo);
        R resResult = null;
        try {
            resResult = dbsApiBusinessService.sendBleQuery(reqVo);
        } catch (Exception e) {
            resResult = R.fail(e.getMessage());
        }
        return resResult;
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
        R resResult = null;
        try {
            resResult = dbsApiBusinessService.sendEDDAInitiation(reqVo);
        } catch (Exception e) {
            resResult = R.fail(e.getMessage());
        }
        return resResult;
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
        R resResult = null;
        try {
            resResult = dbsApiBusinessService.sendProvisionQuery(reqVo);
        } catch (Exception e) {
            resResult = R.fail(e.getMessage());
        }
        return resResult;
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
        R resResult = null;
        try {
            resResult = dbsApiBusinessService.sendFpsGpcFund(reqVo);
        } catch (Exception e) {
            resResult = R.fail(e.getMessage());
        }
        return resResult;
    }

    @ApiOperation("Fps Gps付款")
    @PostMapping(FPS_GPP)
    public R fpsGpp(@RequestBody FundTransferEntity reqVo, HttpServletRequest request) {
        R resResult = null;
        try {
            resResult = dbsApiBusinessService.sendFpsGppFund(reqVo);
        } catch (Exception e) {
            resResult = R.fail(e.getMessage());
        }
        return resResult;
    }

    @ApiOperation("Fps Ppp付款")
    @PostMapping(FPS_PPP)
    public R fpsPpp(@RequestBody FundTransferEntity reqVo, HttpServletRequest request) {
        R resResult = null;
        try {
            resResult = dbsApiBusinessService.sendFpsPPPFund(reqVo);
        } catch (Exception e) {
            resResult = R.fail(e.getMessage());
        }
        return resResult;
    }

    @ApiOperation("银行转账(Dbs Act 同行转账)")
    @PostMapping(ACT)
    public R act(@RequestBody FundTransferEntity reqVo, HttpServletRequest request) {
        R resResult = null;
        try {
            resResult = dbsApiBusinessService.sendActFund(reqVo);
        } catch (Exception e) {
            resResult = R.fail(e.getMessage());
        }
        return resResult;
    }

    @ApiOperation("银行转账(Dbs RFD 退款)")
    @PostMapping(RFD)
    public R rfd(@RequestBody FundTransferEntity reqVo, HttpServletRequest request) {
        R resResult = null;
        try {
            resResult = dbsApiBusinessService.sendRFDFund(reqVo);
        } catch (Exception e) {
            resResult = R.fail(e.getMessage());
        }
        return resResult;
    }

    @ApiOperation("银行转账(Dbs CHATS/RTGS 跨行转账)")
    @PostMapping(CHATS)
    public R chats(@RequestBody FundTransferEntity reqVo, HttpServletRequest request) {
        R resResult = null;
        try {
            resResult = dbsApiBusinessService.sendChatsFund(reqVo);
        } catch (Exception e) {
            resResult = R.fail(e.getMessage());
        }
        return resResult;
    }


    @ApiOperation("银行转账(Dbs TT 海外转账)")
    @PostMapping(TT)
    public R tt(@RequestBody FundTransferEntity reqVo, HttpServletRequest request) {
        R resResult = null;
        try {
            resResult = dbsApiBusinessService.sendTTFund(reqVo);
        } catch (Exception e) {
            resResult = R.fail(e.getMessage());
        }
        return resResult;
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
        R resResult = null;
        try {
            resResult = dbsApiBusinessService.sendEDDAEnquiry(reqVo);
        } catch (Exception e) {
            resResult = R.fail(e.getMessage());
        }
        return resResult;
    }

    @ApiOperation("Tse 查询汇款交易状态")
    @PostMapping(TSE)
    public R tse(@RequestBody TseTransactionEnquiryRequestVO reqVo, HttpServletRequest request) {
        R resResult = null;
        try {
            resResult = dbsApiBusinessService.sendTseEnquiry(reqVo);
        } catch (Exception e) {
            resResult = R.fail(e.getMessage());
        }
        return resResult;
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
        R resResult = null;
        try {
            resResult = dbsApiBusinessService.sendCamt053(reqVo);

            // 获取数据
//            String xmlData = (String)resResult.getResult();

//            // xml数据转化为实体
//            CamtReportResponseVO camt53ReportResponseVO = DbsBankCamtXmlUtils.parseCamtXmlToEntity(xmlData, CamtReportTypeEnum.CAMT053.getCode());
//            resResult.setResult(camt53ReportResponseVO);

        } catch (Exception e) {
            resResult = R.fail(e.getMessage());
        }
        return resResult;
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
        R resResult = null;
        try {
            resResult = dbsApiBusinessService.sendCamt052(reqVo);

//            // 获取数据
//            String xmlData = (String)resResult.getResult();
//            // xml数据转化为实体
//            CamtReportResponseVO camt53ReportResponseVO = DbsBankCamtXmlUtils.parseCamtXmlToEntity(xmlData, CamtReportTypeEnum.CAMT052.getCode());
//            resResult.setResult(camt53ReportResponseVO);

        } catch (Exception e) {
            resResult = R.fail(e.getMessage());
        }
        return resResult;
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
    public R fpsIdEnquiry(@RequestBody FundTransferEntity fundWithdrawApplyEntity, HttpServletRequest request) {
        R resResult = null;
        try {
            resResult = dbsApiBusinessService.sendFpsIdEnquiryFund(fundWithdrawApplyEntity);
        } catch (Exception e) {
			e.printStackTrace();
            resResult = R.fail(e.getMessage());
        }
        return resResult;
    }

    /**
     * 解密
     *
     * @param request
     * @return
     */
    @ApiOperation("解密")
    @PostMapping(DECRYPT)
    public R decrypt(HttpServletRequest request) {
        try {
            MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest(request);
            String bodyStr = this.getJsonParams(multiReadRequest);
            log.info("decrypt bank data：" + bodyStr);
            if (StringUtils.isEmpty(bodyStr)) {
                return R.fail();
            }
            // 去掉BGP加密的注释
            String responseMes = bodyStr.replaceFirst("Version: BCPG v\\d{1}.\\d{1,2}", "");
			bodyStr = bodyStr.replaceFirst("Version: BCPG v\\d{1}.\\d{1,2}", "");
			bodyStr = bodyStr.replaceFirst(
				"-----END PGP MESSAGE-----", "");
			bodyStr = bodyStr.replaceFirst(
				"-----BEGIN PGP MESSAGE-----", "");


			String decResponse = DbsCommManageUtils.decrypt(bodyStr, "", "");
            return R.success(decResponse);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }
    /**
     * icc 解密
     *
     * @param request
     * @return
     */
    @ApiOperation("解密")
    @PostMapping(DECRYPT+"/icc")
    public R iccDecrypt(HttpServletRequest request) {
        try {
            MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest(request);
            String bodyStr = this.getJsonParams(multiReadRequest);
            log.info("decrypt bank data：" + bodyStr);
            if (StringUtils.isEmpty(bodyStr)) {
                return R.fail();
            }
            // 去掉BGP加密的注释
            String responseMes = bodyStr.replaceFirst("Version: BCPG v\\d{1}.\\d{1,2}", "");
            String decResponse = DbsCommManageUtils.iccDecrypt(responseMes, "", "");
            return R.success(decResponse);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }


	/**
	 * icc 加密
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation("加密")
	@PostMapping(ENCRYPT+"/icc")
	public R iccEncrypt(HttpServletRequest request) {
		try {
			MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest(request);
			String bodyStr = this.getJsonParams(multiReadRequest);
			log.info("decrypt bank data：" + bodyStr);
			if (StringUtils.isEmpty(bodyStr)) {
				return R.fail();
			}
			// 去掉BGP加密的注释
			String responseMes = bodyStr.replaceFirst("Version: BCPG v\\d{1}.\\d{1,2}", "");
			String decResponse = DbsCommManageUtils.iccDecrypt(responseMes, "", "");
			return R.success(decResponse);
		} catch (Exception e) {
			return R.fail(e.getMessage());
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
