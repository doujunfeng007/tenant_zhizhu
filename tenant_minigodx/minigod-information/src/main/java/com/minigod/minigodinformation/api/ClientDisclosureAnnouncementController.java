package com.minigod.minigodinformation.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.minigodinformation.dto.ClientAnnListDTO;
import com.minigod.minigodinformation.dto.ClientInfAnnListDTO;
import com.minigod.minigodinformation.service.ExchangeDisclosureAnnouncementService;
import com.minigod.minigodinformation.vo.ClientAnnInfoVO;
import com.minigod.minigodinformation.vo.ClientAnnListVO;
import com.minigod.minigodinformation.vo.ClientAnnouncementInfoVO;
import com.minigod.minigodinformation.vo.ClientInfAnnListVO;
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
* 披露公告表;(disclosure_announcement)表控制层  前台json接口
* @author : http://www.chiner.pro
* @date : 2024-10-19
*/
@Api(tags = "披露公告前台接口")
@RestController
@AllArgsConstructor
@RequestMapping(AppConstant.OPEN_API_PREFIX + "/disclosure_announcement")
public class ClientDisclosureAnnouncementController {
   @Autowired
   private ExchangeDisclosureAnnouncementService disclosureAnnouncementService;


    @ApiOperation("分页查询(披露公告)")
    @GetMapping("/announcement/page")
    public R<IPage<ClientAnnListVO>> queryAnnouncementPageList(Query query, ClientAnnListDTO clientAnnListDTO){
        IPage<ClientAnnListVO> pages = disclosureAnnouncementService.getClientAnnouncementPageList(Condition.getPage(query), clientAnnListDTO);
        return R.data(pages);
    }


    @ApiOperation("查询详情(披露公告)")
    @GetMapping("/annInfo")
    public R<ClientAnnouncementInfoVO> annInfo(Integer id){
        return R.data(disclosureAnnouncementService.queryById(id));
    }





}
