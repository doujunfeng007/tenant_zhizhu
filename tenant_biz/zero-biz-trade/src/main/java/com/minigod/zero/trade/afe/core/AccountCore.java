package com.minigod.zero.trade.afe.core;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.minigod.zero.core.log.exception.ServiceException;
import com.minigod.zero.trade.afe.req.CreateAccountMustReq;
import com.minigod.zero.trade.afe.req.CreateAccountReq;
import com.minigod.zero.trade.afe.util.ClientCall;
import com.minigod.zero.trade.afe.util.XmlGeneratorUtil;
import com.minigod.zero.trade.afe.webservice.createaccount.CreateAccount;
import com.minigod.zero.trade.afe.webservice.createaccount.CreateAccountPortType;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author：jim(liaoguangjie) @Date：2024/4/16 9:44
 *
 * @description：账户相关工具类
 */
@Slf4j
@Component
public class AccountCore {

    @Value("${trade.afe.url:http://10.9.68.165:7777/bpm}")
    private String companyId;
    @Value("${trade.afe.userId:123}")
    private String userId;
    @Value("${trade.afe.userPassword:456}")
    private String userPassword;

    private static final String PARAM_PREFIX = "<REQUEST>";

    private static final String PARAM_SUFFIX = "</REQUEST>";

    /**
     * 开户
     */
    public Map<String, Object> openAccount(CreateAccountMustReq createAccountMustReq) {
        log.info("开户请求参数：{}", JSONObject.toJSONString(createAccountMustReq));
        String xmlParam = XmlGeneratorUtil.toXml(createAccountMustReq, PARAM_PREFIX, PARAM_SUFFIX, true);
        String encryptParam = null;
        try {
            encryptParam = ClientCall.encryptParam(companyId, userId, userPassword, xmlParam);
        } catch (Exception e) {
            throw new ServiceException("开户加密参数异常！");
        }
        CreateAccount account = new CreateAccount();
        CreateAccountPortType portType = account.getCreateAccountHttpSoap11Endpoint();
        String result = portType.run(encryptParam);
        log.info("开户柜台返回参数：{}", result);
        return XmlGeneratorUtil.parseXml(result);
    }

    public static void main1(String[] args) {
        String xmlParam = null;
        CreateAccountReq createAccountReq = createAccountReq();
        try {
            String messageReq = JSON.toJSONString(createAccountReq);
            // String xmlParam = XmlGeneratorUtil.toXml(createAccountReq, PARAM_PREFIX, PARAM_SUFFIX, false);
            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode jsonNode = jsonMapper.readTree(messageReq);
            XmlMapper xmlMapper = new XmlMapper();
			xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, false);
            xmlParam = xmlMapper.writer().withRootName("REQUEST").writeValueAsString(jsonNode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("请求的xml===" + xmlParam);
        String encryptParam = null;
        try {
            encryptParam = ClientCall.encryptParam("LG", "LG_USER", "nszoQ603bScrusblfTthK0Ie3XydjvLdrWYaSG7r/s0PhG/yd8qAjolZprwwllm9ljcEeD02gfH9j4SCVxCf5FCa7vC5umqHQ7zdsNQvs7J0oWj8AcvrZ6TYv+mQxwieLwr8V3YHszzUcqjfk3YhASTWowgTqXDrrAnpKdfnTsw=", xmlParam);
        } catch (Exception e) {
            throw new ServiceException("开户加密参数异常！"+e);
        }
        CreateAccount account = new CreateAccount();
        CreateAccountPortType portType = account.getCreateAccountHttpSoap12Endpoint();
		System.out.println("请求加密后的数据=="+encryptParam);
        String result = portType.run(encryptParam);
        System.out.println(result);
    }

    private static CreateAccountReq createAccountReq()  {
        CreateAccountReq createAccountReq = new CreateAccountReq();
        /**
         * 必填字段
         */
        createAccountReq.setClientID("123456"); // 客户号
        createAccountReq.setAccountID("123456");// 资金账号
        createAccountReq.setAccountTypeID("M");// 账户类别 M融资 X现金
        createAccountReq.setRegistrationType("1"); // 0 个人
        createAccountReq.setName("ZHANGSAN"); // 英文名称
        createAccountReq.setNameEx("~5F204E09"); // 中文名称
        createAccountReq.setGender("M"); // M男 F女
        createAccountReq.setDateOfBirth("1990-01-01");
        createAccountReq.setNationalityID("CN");
        createAccountReq.setPlaceOfBirth("CN");
        createAccountReq.setMaritalStatus("U");
        createAccountReq.setIdType("I");
        createAccountReq.setIdNumber("420703199210012212");
        createAccountReq.setIdIssuePlace("CN");
        createAccountReq.setIdIssueDate("1990-01-01");
        createAccountReq.setHomeNumber("13037164972");
        createAccountReq.setMobileNumber("13037164972");
        createAccountReq.setContactName("ZHANGSAN");
        createAccountReq.setMailAddress1("chenkai@zszhizhu.com");
        createAccountReq.setMailContactReportFlag("Y");
        createAccountReq.setEmailAddress("chenkai@zszhizhu.com");
        createAccountReq.setEmailContactReportFlag("Y");
        createAccountReq.setEducationLevel("4");
        createAccountReq.setEmploymentStatus("M");
        createAccountReq.setAnnualSalary("0");
        createAccountReq.setTotalAssetValue("0");
        createAccountReq.setSourceOfIncome("A|S");
        createAccountReq.setSourceOfFunds("A|S");
        createAccountReq.setOwnershipOfResidence("P");
        createAccountReq.setInvestmentTarget("A");
        createAccountReq.setInvestmentFrequency("1");
        createAccountReq.setNumberOfTaxResidency("1");
        createAccountReq.setTaxResidency1("CN");
        createAccountReq.setTin1("420703199210012232");
        createAccountReq.setIpAddress("127.0.0.1");
        createAccountReq.setLanguageID("CHT"); // 繁体中文
        createAccountReq.setCreationMethod("O");

        createAccountReq.setInvestmentType01("1");

		// 图像
		try {
			InputStream inputStream = new FileInputStream("D:\\data\\3333.png");
			int numberOfBytesRead;
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[16384];
			while ((numberOfBytesRead = inputStream.read(buffer, 0,
				buffer.length)) != -1)
			{
				outputStream.write(buffer, 0, numberOfBytesRead);
			}
			inputStream.close();
			outputStream.flush();

			/**
			 * "#Base64Encoded#"+
			 */
			createAccountReq.setSignatureImage( Base64.getEncoder().encodeToString(outputStream.toByteArray()));
//			createAccountReq.setSignatureImage("#Base64Encoded#"+ baseStr);
			createAccountReq.setAddressProveImage( Base64.getEncoder().encodeToString(outputStream.toByteArray()));
//			createAccountReq.setAddressProveImage("#Base64Encoded#"+ baseStr);
			createAccountReq.setAddressProveIsPdfFile("N");
			createAccountReq.setHkIdCardImage( Base64.getEncoder().encodeToString(outputStream.toByteArray()));
//			createAccountReq.setHkIdCardImage("#Base64Encoded#"+ baseStr);
			createAccountReq.setHkIdIsPdfFile("N");
			createAccountReq.setHkIdCardImageBackSide(Base64.getEncoder().encodeToString(outputStream.toByteArray()));
			createAccountReq.setAccountOpeningForm( Base64.getEncoder().encodeToString(outputStream.toByteArray()));
			createAccountReq.setAccountOpeningFormIsPdfFile("N");

			if(!isStandardAscii(createAccountReq.getSignatureImage())){
				createAccountReq.setSignatureImage("#Base64Encoded#"+transUtf16(createAccountReq.getSignatureImage()));
			}
			if(!isStandardAscii(createAccountReq.getAddressProveImage())){
				createAccountReq.setAddressProveImage("#Base64Encoded#"+transUtf16(createAccountReq.getAddressProveImage()));
			}
			if(!isStandardAscii(createAccountReq.getHkIdCardImage())){
				createAccountReq.setHkIdCardImage("#Base64Encoded#"+transUtf16(createAccountReq.getHkIdCardImage()));
			}
			if(!isStandardAscii(createAccountReq.getHkIdCardImageBackSide())){
				createAccountReq.setHkIdCardImageBackSide("#Base64Encoded#"+transUtf16(createAccountReq.getHkIdCardImageBackSide()));
			}
			if(!isStandardAscii(createAccountReq.getAccountOpeningForm())){
				createAccountReq.setAccountOpeningForm("#Base64Encoded#"+transUtf16(createAccountReq.getAccountOpeningForm()));
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}



        return createAccountReq;
    }

	public static String transUtf16(String parameter) {

		StringBuilder hexString = new StringBuilder();
		for (char c : parameter.toCharArray()) {
			hexString.append(String.format("%02x", (int) c));
		}
		return "~"+hexString.toString().toUpperCase();
	}

	/**
	 * 判断是否是标准ASCII字符集
	 * 如果内容包含的字符编码小于0或大于127，或者包含特殊字符（例如“<”，“>”，“|”，“~”）
	 * @param parameter
	 * @return
	 */
	public static boolean isStandardAscii(String parameter) {
		for (char c : parameter.toCharArray()) {
			int codePoint =  c;
			if (codePoint < 32 || codePoint > 127) {
				log.info("参数包含非ASCII字符：{}原始字符为：{}", codePoint,parameter);
				return false;
			}else if(parameter.contains("<") ||
				parameter.contains(">")||
				parameter.contains("|")||
				parameter.contains("~")){
				log.info("特殊字符参数包含非ASCII字符：{}", codePoint);
				return false;
			}else {
				return true;
			}
		}
		return true;
	}
}
