package com.minigod.zero.trade.feign;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.service.IAccountService;
import com.minigod.zero.trade.vo.sjmb.req.ModifyAccountReq;
import com.minigod.zero.trade.vo.sjmb.req.OpenAccountReq;
import com.minigod.zero.trade.vo.sjmb.resp.OpenAccountVO;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author:yanghu.luo
 * @create: 2023-04-26 10:19
 * @Description: 开户相关
 */
@Slf4j
@ApiIgnore()
@RestController
@AllArgsConstructor
public class CountOpenAccountClient implements ICounterOpenAccountClient {

	@Autowired
	private IAccountService accountService;

	@Override
	@SneakyThrows
	public R<OpenAccountVO> openAccount(OpenAccountReq openAccountReq) {
	    return accountService.openAccount(openAccountReq,false);
	}

	@Override
	public R updateAccount(ModifyAccountReq modifyAccountReq) {
		return accountService.updateAccount(modifyAccountReq);
	}
}
