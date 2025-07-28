package com.minigod.zero.bpmn.module.common.controller;

import com.minigod.zero.bpm.dto.BpmAccountRespDto;
import com.minigod.zero.bpm.dto.BpmTradeAcctRespDto;
import com.minigod.zero.bpm.feign.IBpmAccountClient;
import com.minigod.zero.bpmn.module.account.vo.ClientInfoVo;
import com.minigod.zero.bpmn.module.feign.ICustomerInfoClient;
import com.minigod.zero.bpmn.module.feign.vo.CustomerAccountDetailVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.feign.ICustAccountClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ClientController
 * @Description:
 * @Author chenyu
 * @Date 2024/3/30
 * @Version 1.0
 */
@Validated
@RestController
@AllArgsConstructor
@Api(value = "客户信息", tags = "客户信息接口")
@RequestMapping(AppConstant.BACK_API_PREFIX + "/withdraw/client/info")
public class ClientController {
    private final ICustAccountClient custAccountClient;
	private final ICustomerInfoClient customerInfoClient;
    /**
     * 获取客户信息记录详细信息
     */
    @ApiOperation("获取客户信息记录详细信息")
    //@SaCheckPermission("client:info:getInfo")
    @PostMapping("/getInfo")
    public R<ClientInfoVo> getInfo(@ApiParam("理财账号")
                                   @NotBlank(message = "理财账号不能为空")
                                   @RequestParam("fundAccount") String fundAccount
    ) {
		R<CustomerAccountDetailVO> result = customerInfoClient.selectCustomerDetailByAccountId(fundAccount);
		if (!result.isSuccess()) {
			return R.fail(result.getMsg());
		}
		if (result.getData()==null){
			return R.fail("查找客户资料失败");
		}
		CustomerAccountDetailVO data = result.getData();

		ClientInfoVo clientInfoVo = ClientInfoVo.builder()
			.clientName(data.getClientName())
			.clientNameSpell(data.getGivenNameSpell())
			.email(data.getEmail())
			.clientId(fundAccount)
			.mobile(data.getPhoneNumber())
			.custId(data.getCustId())
			.fundAccount(fundAccount).build();
		return R.data(clientInfoVo);


		/*BpmTradeAcctRespDto dto = new BpmTradeAcctRespDto();
        dto.setCapitalAccount(fundAccount);
        R<BpmAccountRespDto> bpmAccountRespDto = bpmAccountClient.bpmAccountInfoByAccount(dto);
        if (bpmAccountRespDto.isSuccess()) {
            ClientInfoVo clientInfoVo = ClientInfoVo.builder()
                    .clientName(bpmAccountRespDto.getData().getCust().getCustName())
                    .clientNameSpell(bpmAccountRespDto.getData().getCust().getCustNameSpell())
                    .email(bpmAccountRespDto.getData().getCust().getEmail())
                    .clientId(bpmAccountRespDto.getData().getAcct().getTradeAccount())
                    .mobile(bpmAccountRespDto.getData().getCust().getPhoneNumber())
                    .fundAccount(bpmAccountRespDto.getData().getAcct().getCapitalAccount()).build();
            return R.data(clientInfoVo);
        } else {
            return R.fail(bpmAccountRespDto.getMsg());
        }*/
    }

    @ApiOperation("获取资金信息")
    //@SaCheckPermission("client:info:getInfo")
    @PostMapping("/fundInfoTotal")
    public R<Map<String,Object>> getFundInfo(@ApiParam("账户号码")
                                   @NotBlank(message = "账户号码不能为空")
                                   @RequestParam("fundAccount") String fundAccount,
                                   @RequestParam("moneyType") String moneyType
    ) {
        Map<String,Object> map = new HashMap<>();
        map.put("fetchBalance",BigDecimal.ZERO);
        map.put("frozenBalance",BigDecimal.ZERO);
       R<String> r =  custAccountClient.getExtractableMoney(fundAccount,moneyType);
	   if (r==null){
		   map.put("fetchBalance",new BigDecimal(0));
		   return R.data(map);
	   }
       if (r.isSuccess()) {
           map.put("fetchBalance",new BigDecimal(r.getData()));
       }
       return R.data(map);
    }
}
