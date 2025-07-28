package com.minigod.zero.trade.afe.service;

import cn.hutool.json.JSONObject;
import cn.hutool.json.XML;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.afe.common.ApiEnum;
import com.minigod.zero.trade.afe.core.AccountCore;
import com.minigod.zero.trade.afe.enums.AfeCounterEnum;
import com.minigod.zero.trade.afe.req.CreateAccountReq;
import com.minigod.zero.trade.afe.req.UpdateAccountReq;
import com.minigod.zero.trade.afe.resp.back.BackCommonResponse;
import com.minigod.zero.trade.afe.resp.back.OpenAccountCommonResponse;
import com.minigod.zero.trade.afe.util.ClientCall;
import com.minigod.zero.trade.afe.webservice.createaccount.CreateAccount;
import com.minigod.zero.trade.afe.webservice.createaccount.CreateAccountPortType;
import com.minigod.zero.trade.service.IAccountService;
import com.minigod.zero.trade.utils.RSAUtils;
import com.minigod.zero.trade.vo.sjmb.CountryEnum;
import com.minigod.zero.trade.vo.sjmb.req.ModifyAccountReq;
import com.minigod.zero.trade.vo.sjmb.req.OpenAccountReq;
import com.minigod.zero.trade.vo.sjmb.resp.OpenAccountVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_AFE;

/**
 * @author chen
 * @ClassName AfeAccountServiceImpl.java
 * @Description TODO
 * @createTime 2024年04月13日 18:36:00
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "trade.type", havingValue = MULTI_SERVER_TYPE_AFE)
public class AfeAccountServiceImpl implements IAccountService {
    @Autowired
    private AccountCore accountCore;

	@Autowired
	SendApiService sendApiService;

	@Value("${afe.companyId:LG}")
	private String companyId;
	@Value("${afe.userId:LG_USER}")
	private String userId;
	@Value("${afe.userPassword:testtest}")
	private String userPassword;

	@Value("${afe.publicKey}")
	private String publicKey;

    @Override
    public R<OpenAccountVO> openAccount(OpenAccountReq openAccountReq,boolean isRepeat) {
		Boolean isRepeatOpenAccount =isRepeat;
		log.info("开始执行柜台开户openAccountReq:{}",JSONUtil.toJson(openAccountReq));
        CreateAccountReq createAccountReq = buildCreateAccountReq(openAccountReq,isRepeatOpenAccount);
		log.info("开户转换后的参数：{}", JSON.toJSONString(createAccountReq));
		if(null ==createAccountReq){
			return R.fail("参数错误");
		}
        String xmlParam = null;
        try {
            String messageReq = JSON.toJSONString(createAccountReq);
            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode jsonNode = jsonMapper.readTree(messageReq);
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, false);
            xmlParam = xmlMapper.writer().withRootName("REQUEST").writeValueAsString(jsonNode);
            log.info("开户请求的xml文件为=={}", xmlParam);

			String encryptPassword =RSAUtils.encrypt(userPassword, publicKey);
			String encryptParam = ClientCall.encryptParam(companyId, userId, encryptPassword, xmlParam);
			if(StringUtils.isEmpty(encryptParam)){
				return R.fail("加密参数异常");
			}
			CreateAccount account = new CreateAccount();
			CreateAccountPortType portType = account.getCreateAccountHttpSoap12Endpoint();
			log.info("请求加密后的数据=="+encryptParam);
			String xmlResultStr = portType.run(encryptParam);
			log.info("开户柜台返回参数：{}", xmlResultStr);
			if (StringUtils.isNotEmpty(xmlResultStr)) {
				JSONObject respJson = XML.toJSONObject(xmlResultStr);
				String jsonStr =cn.hutool.json.JSONUtil.toJsonStr(respJson);
				OpenAccountCommonResponse response = JSONUtil.fromJson(jsonStr, OpenAccountCommonResponse.class);
				Map<String, Object> dynamicProperties = response.getResult().getReturncode().getDynamicProperties();
				String errorCode ="";
				if(null != dynamicProperties && dynamicProperties.size()>0){
					errorCode =new ArrayList<>(dynamicProperties.keySet()).get(0);
				}
				if ("ERROR_UNKNOWN".equals(response.getResult().getReturncode())
				|| "ERROR_UNKNOWN".equals(errorCode)) {
					if (! isRepeatOpenAccount) {
						// 这里是重试逻辑
						return this.openAccount(openAccountReq, true); // 递归调用
					} else {
						// 如果已经重试过，返回失败信息
						log.info("重试后任然失败==={}",openAccountReq.getTradeAccount());
					}
				}
				if(response.isSuccess()){
					return R.success();
				}else{
					return R.fail(response.getMessage());
				}
			} else {
				return R.fail("柜台返回数据为空");
			}

        } catch (Exception e) {
            log.error("柜台开户异常", e);
        }
        return R.success();
    }

    private CreateAccountReq buildCreateAccountReq(OpenAccountReq openAccountReq,boolean isRepeat) {

		CreateAccountReq createAccountReq = null;
		try {
			createAccountReq = new CreateAccountReq();
			createAccountReq.setClientID(openAccountReq.getTradeAccount()); // 客户号
			createAccountReq.setAccountID(openAccountReq.getFundAccount());// 资金账号

//			if (1 == openAccountReq.getFundAccountType()) {
//				// 账户类别 M融资 X现金
//				createAccountReq.setAccountTypeID("X");
//			} else {
//				createAccountReq.setAccountTypeID("M");
//			}
			createAccountReq.setAccountTypeID("M");
			createAccountReq.setMobileNumber(openAccountReq.getPhoneNumber());
			// 0 个人
			createAccountReq.setRegistrationType("0");

			// 英文名称
			createAccountReq.setName(openAccountReq.getClientNameSpell());
			// 中文名称
			if (AccountCore.isStandardAscii(openAccountReq.getClientName())) {
				createAccountReq.setNameEx(openAccountReq.getClientName());
			} else {
				createAccountReq.setNameEx(AccountCore.transUtf16(openAccountReq.getClientName()));
			}
			if ("MALE".equals(openAccountReq.getGender())) {
				// M男 F女
				createAccountReq.setGender(AfeCounterEnum.GENDER.MALE.getCode());
			} else {
				createAccountReq.setGender(AfeCounterEnum.GENDER.FEMALE.getCode());
			}
			createAccountReq.setDateOfBirth(openAccountReq.getBirthDate());

			createAccountReq.setNationalityID(CountryEnum.getCounterCountry(openAccountReq.getNationality()));
			createAccountReq.setPlaceOfBirth(CountryEnum.getCounterCountry(openAccountReq.getNationality()));
			// 婚姻状态
			if (1 == openAccountReq.getMaritalStatus()) {
				createAccountReq.setMaritalStatus(AfeCounterEnum.MARITAL_STATUS.UNMARRIED.getCode());
			} else {
				createAccountReq.setMaritalStatus(AfeCounterEnum.MARITAL_STATUS.MARRIED.getCode());
			}
			if (4 == openAccountReq.getIdType().intValue()) {
				createAccountReq.setIdType(AfeCounterEnum.ID_TYPE.PASSPORT.getCode());
			} else {
				createAccountReq.setIdType(AfeCounterEnum.ID_TYPE.ID.getCode());
			}
			createAccountReq.setIdNumber(openAccountReq.getIdNo());
			/**
			 * 暂时和国籍传值一样
			 */
			// TODO 证件签发地
			createAccountReq.setIdIssuePlace(CountryEnum.getCounterCountry(openAccountReq.getNationality()));
			/**
			 * 证件签发日期
			 */
			// TODO 证件签发日期
			createAccountReq.setIdIssueDate("1990-01-01");

			createAccountReq.setHomeNumber(openAccountReq.getPhoneNumber());

			createAccountReq.setContactName(openAccountReq.getClientNameSpell());
			createAccountReq.setMailAddress1(openAccountReq.getEmail());
			// TODO 接收结单方式 待转换
			createAccountReq.setMailContactReportFlag("Y");
			createAccountReq.setEmailAddress(openAccountReq.getEmail());
			createAccountReq.setEmailContactReportFlag("Y");

			// TODO 教育级别 待转换
			createAccountReq.setEducationLevel("4");

			// TODO 就业情况 待转换
			createAccountReq.setEmploymentStatus("M");
			// TODO 每年收入 待转换
			createAccountReq.setAnnualSalary("0");

			// TODO 总资产净值 待转换
			createAccountReq.setTotalAssetValue("0");
			// TODO 收入來源 待转换
			createAccountReq.setSourceOfIncome("A|S");
			// TODO 资金来源 待转换
			createAccountReq.setSourceOfFunds("A|S");

			// TODO 居住地 待转换
			createAccountReq.setOwnershipOfResidence("P");

			// TODO 投资目标 待转换
			createAccountReq.setInvestmentTarget("A");

			// TODO 每月交易頻率 待转换
			createAccountReq.setInvestmentFrequency("1");

			// TODO 税务居民数量
			createAccountReq.setNumberOfTaxResidency("1");

			createAccountReq.setTaxResidency1(CountryEnum.getCounterCountry(openAccountReq.getCountryOfTax()));
			createAccountReq.setTin1(openAccountReq.getTaxNumber());

			createAccountReq.setIpAddress("127.0.0.1");
			// 语言
			createAccountReq.setLanguageID("CHT"); // 繁体中文
			createAccountReq.setCreationMethod("O");
			// TODO 投资经验
			createAccountReq.setInvestmentType01("1");

			String signatureImage = buildSignatureImage(openAccountReq.getSignImage());
			if (AccountCore.isStandardAscii(signatureImage)) {
				createAccountReq.setSignatureImage("#Base64Encoded#" + signatureImage);
			} else {
				createAccountReq.setSignatureImage("#Base64Encoded#" + AccountCore.transUtf16(signatureImage));
			}
//			createAccountReq.setSignatureImage("#Base64Encoded#" + AccountCore.transUtf16(signatureImage));


			String addressImage = buildSignatureImage(openAccountReq.getAddressImageUrl());
			if (AccountCore.isStandardAscii(addressImage)) {
				createAccountReq.setAddressProveImage("#Base64Encoded#" + addressImage);
			} else {
				createAccountReq.setAddressProveImage("#Base64Encoded#" + AccountCore.transUtf16(addressImage));
			}

//			createAccountReq.setAddressProveImage("#Base64Encoded#" + AccountCore.transUtf16(addressImage));
			createAccountReq.setAddressProveIsPdfFile("N");

			String idCardImage = buildSignatureImage(openAccountReq.getIdCardIdFrontUrl());

			if (AccountCore.isStandardAscii(idCardImage)) {
				createAccountReq.setHkIdCardImage("#Base64Encoded#" + idCardImage);
			} else {
				createAccountReq.setHkIdCardImage("#Base64Encoded#" + AccountCore.transUtf16(idCardImage));
			}
//			createAccountReq.setHkIdCardImage("#Base64Encoded#" + AccountCore.transUtf16(idCardImage));
			createAccountReq.setHkIdIsPdfFile("N");

			// TODO 开户申请书，需要传生成的pdf
//			createAccountReq.setAccountOpeningForm("#Base64Encoded#" + AccountCore.transUtf16(idCardImage));
//			createAccountReq.setAccountOpeningFormIsPdfFile("N");

			createAccountReq.setAutoApprovalFlag("Y");
			createAccountReq.setChannelId("WS");
			createAccountReq.setLoginId(openAccountReq.getFundAccount());
			createAccountReq.setMarketIdList("HKEX|NYSE"); //港股和美股

			/**
			 * 图片编码
			 */
			if(isRepeat){
				createAccountReq.setSignatureImage("#Base64Encoded#" + AccountCore.transUtf16(signatureImage));
				createAccountReq.setAddressProveImage("#Base64Encoded#" + AccountCore.transUtf16(addressImage));
				createAccountReq.setHkIdCardImage("#Base64Encoded#" + AccountCore.transUtf16(idCardImage));
			}

		} catch (Exception e) {
			log.error("buildCreateAccountReq error", e);
			return null;
		}

		return createAccountReq;
    }

    private String buildSignatureImage(String imageUrl) {
		// TODO  柜台需要这些信息，所以在线下开户的时候需要上传这些文件
		if (StringUtils.isEmpty(imageUrl)) {
			imageUrl ="http://cloud-api.zsdp.zszhizhu.com:9000/minigodx-sit/upload/20241105/b2681c991b9b02909765b63d3cef8220.jpg";
		}
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = null;
        String result = "";
        try {
            URL url = new URL(imageUrl);
            URLConnection connection = url.openConnection();
            inputStream = connection.getInputStream();
            int numberOfBytesRead;
            byte[] buffer = new byte[16384];
            while ((numberOfBytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, numberOfBytesRead);
            }
            inputStream.close();
            outputStream.flush();
            result = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return result;
    }

	/**
	 * 账户资料设置
	 *
	 * @param modifyAccountReq
	 * @return
	 */
	@Override
	public R updateAccount(ModifyAccountReq modifyAccountReq) {
		UpdateAccountReq updateAccountReq = new UpdateAccountReq();
		updateAccountReq.setAccountId(modifyAccountReq.getFundAccount());
		updateAccountReq.setEmailAddress(modifyAccountReq.getEmail());
		updateAccountReq.setAddress1(modifyAccountReq.getFamilyAddress());
		updateAccountReq.setAddress2(modifyAccountReq.getContactAddress());
		updateAccountReq.setApiName(ApiEnum.UPDATE_ACCOUNT_INFO.getUrl());
		BackCommonResponse backCommonResponse = sendApiService.sendMessage(updateAccountReq, updateAccountReq.getApiName(), true);

		if (null != backCommonResponse) {
			if (backCommonResponse.isSuccess()) {
				return R.success();
			} else {
				return R.fail(backCommonResponse.getMessage());
			}
		}
		return R.fail();

	}

}
