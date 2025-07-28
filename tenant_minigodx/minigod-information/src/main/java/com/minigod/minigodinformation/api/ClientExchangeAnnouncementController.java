package com.minigod.minigodinformation.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.minigodinformation.dto.ClientExchangeAnnListDTO;
import com.minigod.minigodinformation.service.ExchangeAnnouncementService;
import com.minigod.minigodinformation.vo.ClientExchangeAnnInfoVO;
import com.minigod.minigodinformation.vo.ClientExchangeAnnListVO;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 披露公告表;(disclosure_announcement)表控制层
 * @author : http://www.chiner.pro
 * @date : 2024-10-19
 */
@Api(tags = "交易所通知前台接口")
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/exchange_announcement")
public class ClientExchangeAnnouncementController {
    @Autowired
    private ExchangeAnnouncementService exchangeAnnouncementService;

    @ApiOperation("分页查询(交易所通知)")
    @GetMapping("/page")
    public R<IPage<ClientExchangeAnnListVO>> queryPageList(Query query, ClientExchangeAnnListDTO clientExchangeAnnListDTO){
        IPage<ClientExchangeAnnListVO> pages = exchangeAnnouncementService.queryClientPageList(Condition.getPage(query), clientExchangeAnnListDTO);
        return R.data(pages);
    }

    @ApiOperation("查询详情(交易所通知)")
    @GetMapping("/info")
    public R<ClientExchangeAnnInfoVO> getInfoById(Integer id){
        return exchangeAnnouncementService.getClientInfo(id);
    }



}
