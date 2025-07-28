package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.dto.InvestMsgSearchReqDTO;
import com.minigod.zero.manage.dto.SendInvestMsgReqDTO;
import com.minigod.zero.manage.enums.ShowTypeEnum;
import com.minigod.zero.manage.service.IPlatformCommonTemplateService;
import com.minigod.zero.manage.service.IPlatformInvestMsgService;
import com.minigod.zero.manage.service.IPlatformTemplateTypeService;
import com.minigod.zero.manage.utils.FileReadUtil;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.api.ResultCode;
import com.minigod.zero.platform.entity.PlatformInvestMsgEntity;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * OMS消息中心
 *
 * @author Zhe.Xiao
 */
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/investMsg")
@RequiredArgsConstructor
@Slf4j
public class InvestMsgController {

    private final IPlatformInvestMsgService platformInvestMsgService;
    private final IPlatformTemplateTypeService platformTemplateTypeService;
    private final IPlatformCommonTemplateService platformCommonTemplateService;
    /**
     * 保存并发送消息通知
     *
     * @return
     */
    @ApiOperation("保存并发送消息通知")
    @PostMapping("/sendMsgSaveUpdate")
    public R<Object> confirmMsgCenterMsg(@RequestParam(value = "file", required = false) MultipartFile file,
                                         SendInvestMsgReqDTO sendInvestMsgReqDTO) {
        try {
            if (StringUtils.isBlank(sendInvestMsgReqDTO.getShowType()) || sendInvestMsgReqDTO.getDisplayGroup() == null
                    || StringUtils.isBlank(sendInvestMsgReqDTO.getTitle()) || StringUtils.isBlank(sendInvestMsgReqDTO.getContent())
                    || sendInvestMsgReqDTO.getSendWay() == null || sendInvestMsgReqDTO.getClientType() == null) {
                return R.fail(ResultCode.PARAMETER_ERROR);
            }
            boolean userIdsMissing = sendInvestMsgReqDTO.getShowType().equals(ShowTypeEnum.SPECIFIC.getCode())
                    && (sendInvestMsgReqDTO.getUserIds() == null || sendInvestMsgReqDTO.getUserIds().length == 0)
                    && (file == null || file.isEmpty());
            if (userIdsMissing) {
                return R.fail(ResultCode.PARAMETER_ERROR);
            }

            sendInvestMsgReqDTO.setSendTime(new Date());

            // 是否通过文件导入
            if (file != null && !file.isEmpty()) {
                List<Long> lstUserIds = FileReadUtil.getUserIdsFromFile(file);
                if (CollectionUtils.isEmpty(lstUserIds)) {
                    return R.fail("导入文件内容为空");
                } else {
                    Long[] arr = new Long[lstUserIds.size()];
                    sendInvestMsgReqDTO.setUserIds(lstUserIds.toArray(arr));
                }
            }
            platformInvestMsgService.saveUpdateAndSendMsg(sendInvestMsgReqDTO);
            return R.success();
        } catch (Exception e) {
            log.error(" 保存并发送消息通知失败：", e);
            return R.fail(ResultCode.INTERNAL_ERROR.getCode(), "操作失败");
        }
    }

    /**
     * 查询消息中心消息历史列表
     *
     * @return
     */
    @ApiOperation("查询消息中心消息历史列表")
    @GetMapping("/msgList")
    public R<Object> msgCenterMsgList(InvestMsgSearchReqDTO dto, Query query) {
        IPage<PlatformInvestMsgEntity> investMsgPage = platformInvestMsgService.getInvestMsgList(Condition.getPage(query), dto);
        return R.data(investMsgPage);
    }

}
