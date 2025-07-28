package com.minigod.zero.trade.afe.core;

import java.io.Reader;
import java.io.StringReader;

import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.trade.afe.req.CreateAccountReq;
import com.minigod.zero.trade.afe.webservice.createaccount.CreateAccount;
import com.minigod.zero.trade.afe.webservice.createaccount.CreateAccountPortType;

/**
 * @author chen
 * @ClassName Test001.java
 * @Description TODO
 * @createTime 2024年08月06日 18:01:00
 */
public class Test001 {

	public static void main22(String[] args) {
		CreateAccount account = new CreateAccount();
		CreateAccountPortType portType = account.getCreateAccountHttpSoap11Endpoint();
		String responseFromQueryParameters = portType.queryParameters("LG","LG_USER","testtest");
		System.out.println(responseFromQueryParameters);
	}

	public static void main11(String[] args) {
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

			System.out.println("请求的xml===" + xmlParam);

			CreateAccount account = new CreateAccount();
			CreateAccountPortType portType = account.getCreateAccountHttpSoap11Endpoint();
			String responseFromQueryParameters = portType.queryParameters("LG","LG_USER","testtest");

			if (StringUtil.isBlank(responseFromQueryParameters)){

			}
//			String encryptMap = getEncryptMessage(queryResponse);
//
			Reader reader = new StringReader(responseFromQueryParameters);

			InputSource inputSource = new InputSource(reader);
			inputSource.setEncoding("UTF-8");




		} catch (Exception e) {
			throw new RuntimeException(e);
		}








	}


	private static CreateAccountReq createAccountReq() {
		CreateAccountReq createAccountReq = new CreateAccountReq();
		/**
		 * 必填字段
		 */
		createAccountReq.setClientID("123456"); // 客户号
		createAccountReq.setAccountID("123456");// 资金账号
		createAccountReq.setAccountTypeID("M");// 账户类别 M融资 X现金
		createAccountReq.setRegistrationType("1"); // 0 个人
		createAccountReq.setName("ZHANGSAN"); // 英文名称
		createAccountReq.setNameEx("张三"); // 中文名称
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

		return createAccountReq;
	}
}
