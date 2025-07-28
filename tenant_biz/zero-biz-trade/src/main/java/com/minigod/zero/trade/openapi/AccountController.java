package com.minigod.zero.trade.openapi;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.minigod.zero.core.secure.utils.AuthUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.feign.ICounterOpenAccountClient;
import com.minigod.zero.trade.vo.sjmb.req.OpenAccountReq;
import com.minigod.zero.trade.vo.sjmb.resp.OpenAccountVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/trade/account")
@Api(value = "账号相关接口", tags = "账号相关接口")
public class AccountController {

	@Resource
	private ICounterOpenAccountClient counterOpenAccountClient;



    @PostMapping(value = "/openAccount")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "开户", notes = "开户")
    public R getServerType(@Valid @RequestBody String request) {

		OpenAccountReq openAccountReq =new OpenAccountReq();
		openAccountReq.setTradeAccount("88888888");
		openAccountReq.setFundAccount("88888888");
		openAccountReq.setFirstName("测试001");
		openAccountReq.setLastName("测");
		openAccountReq.setFirstNameEn("TEST");
		openAccountReq.setLastNameEn("test");
		openAccountReq.setNationality("HKG");
		openAccountReq.setBirthDate("2000-01-01");
		openAccountReq.setGender("MALE");
		openAccountReq.setAreaCode("+852");
		openAccountReq.setPhoneNumber("80000001");
		openAccountReq.setEmail("chenqi@zszhizhu.com");
		openAccountReq.setIdType(2);
		openAccountReq.setIdNo("81234567");
		openAccountReq.setContactCountry("CHN");
		openAccountReq.setContactState("广东省");
		openAccountReq.setContactCity("深圳市");
		openAccountReq.setContactDistrict("福田区");
		openAccountReq.setContactAddress("xxx街道xxx号");
		openAccountReq.setContactPostal("123456");
		openAccountReq.setAccountType(2);
		openAccountReq.setBrokerPractitioner(false);
		openAccountReq.setDecisionMaker(false);
		openAccountReq.setLanguage("zh_HK");
		openAccountReq.setUserRegion("CN");
		openAccountReq.setIsOpenCnMarket(0);
		openAccountReq.setIsOpenHkMarket(1);
		openAccountReq.setIsOpenUsMarket(1);
		openAccountReq.setCountryOfTax("CHN");
		openAccountReq.setTaxNumber("1234656");
		R<OpenAccountVO> result = R.fail();
		try {
			result = counterOpenAccountClient.openAccount(openAccountReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }






}
