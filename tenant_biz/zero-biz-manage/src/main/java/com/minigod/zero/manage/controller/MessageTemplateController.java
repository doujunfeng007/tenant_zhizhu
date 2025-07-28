package com.minigod.zero.manage.controller;

import com.minigod.zero.manage.dto.EmailDTO;
import com.minigod.zero.manage.dto.PushDTO;
import com.minigod.zero.manage.dto.SmsDTO;
import com.minigod.zero.manage.service.IPlatformCommonTemplateService;
import com.minigod.zero.manage.service.MobileMsgService;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.platform.entity.PlatformCommonTemplateEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstant.BACK_API_PREFIX + "/message")
public class MessageTemplateController {

    private final MobileMsgService mobileMsgService;
    private final IPlatformCommonTemplateService platformCommonTemplateService;

    /**
     * 短信模板列表
     * @return
     */
    @GetMapping("/template")
    public R mobileCenterTempList(Query query,String keyword,Integer msgType) {
        return platformCommonTemplateService.getTemplateList(Condition.getPage(query),keyword, msgType);
    }

	@GetMapping("/template/{id}")
	public R<PlatformCommonTemplateEntity> getTemplateDetail(@PathVariable("id") Long id) {
		return platformCommonTemplateService.getTemplateDetail(id);
	}

	@PostMapping("/template")
	public R addTemplate(@RequestBody PlatformCommonTemplateEntity template){
		return platformCommonTemplateService.addTemplate(template);
	}

	@PutMapping("/template")
	public R updateTemplate(@RequestBody PlatformCommonTemplateEntity template){
		return platformCommonTemplateService.updateTemplate(template);
	}

	@DeleteMapping("/template")
	public R deleteTemplate(Long id){
		return platformCommonTemplateService.deleteTemplate(id);
	}


	@GetMapping("/template/type/{msgType}")
	public R getSmsTemplate(@PathVariable("msgType") Integer msgType){
		return platformCommonTemplateService.selectTemplateByMsgType(msgType);
	}

	@PostMapping("/sms/send")
	public R sendMessage(@RequestBody SmsDTO sendSmsVO){
		return mobileMsgService.sendMessage(sendSmsVO);

	}

	@PostMapping("/push/send")
	public R sendMessage(@RequestBody EmailDTO emailDTO){
		return mobileMsgService.sendMessage(emailDTO);
	}

	@PostMapping("/email/send")
	public R sendMessage(@RequestBody PushDTO pushDTO){
		return mobileMsgService.sendMessage(pushDTO);
	}
}
