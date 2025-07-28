package com.minigod.zero.trade.feign;

import com.minigod.zero.trade.vo.req.IFundAccountBo;
import com.minigod.zero.trade.vo.resp.IFundAccountVO;
import com.minigod.zero.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName: IFundClient
 * @Description: IFund基金平台
 * @Author chenyu
 * @Date 2024/3/2
 * @Version 1.0
 */
@FeignClient(value = "IFund", url = "${ifund.url}"
)
public interface IFundClient {

    @PostMapping("/v3/yfund/account")
    R<IFundAccountVO> iFundAccount(@RequestBody IFundAccountBo iFundAccount);
}
