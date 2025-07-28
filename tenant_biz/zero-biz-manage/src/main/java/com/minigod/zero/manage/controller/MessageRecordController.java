package com.minigod.zero.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.manage.dto.InvestMsgSearchReqDTO;
import com.minigod.zero.manage.service.IPlatformEmailContentService;
import com.minigod.zero.manage.service.IPlatformInvestMsgService;
import com.minigod.zero.manage.service.IPlatformMobileContentService;
import com.minigod.zero.platform.entity.PlatformEmailContentEntity;
import com.minigod.zero.platform.entity.PlatformInvestMsgEntity;
import com.minigod.zero.platform.vo.PlatformEmailContentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/8/16 16:51
 * @description：
 */
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/message/record")
public class MessageRecordController {

	@Autowired
	private IPlatformEmailContentService platformEmailContentService;

	@Autowired
	private IPlatformInvestMsgService platformInvestMsgService;

	@Autowired
	private IPlatformMobileContentService platformMobileContentService;


	/**
	 * 邮件消息-发送记录
	 * @return
	 */
	@GetMapping("/email")
	public R<Object> msgInfo(PlatformEmailContentVO platformEmailContentVO, Query query) {
		R<Object> rt = R.success();
		IPage<PlatformEmailContentVO> page = platformEmailContentService.selectPlatformEmailContentPage(Condition.getPage(query), platformEmailContentVO);
		rt.setData(page);
		return rt;
	}

	/**
	 * 邮件消息-发送记录-详情
	 * @return
	 */
	@GetMapping("/email/{id}")
	public R<Object> msgDetail(@PathVariable("id") Long id) {
		PlatformEmailContentEntity detail = platformEmailContentService.getById(id);
		return R.data(detail);
	}


	/**
	 * 查询消息中心消息历史列表
	 *
	 * @return
	 */
	@GetMapping("/push")
	public R<Object> msgCenterMsgList(InvestMsgSearchReqDTO dto, Query query) {
		IPage<PlatformInvestMsgEntity> investMsgPage = platformInvestMsgService.getInvestMsgList(Condition.getPage(query), dto);
		return R.data(investMsgPage);
	}

	/**
	 * 短信记录
	 * @param query
	 * @param startTime
	 * @param endTime
	 * @param phone
	 * @return
	 */
	@GetMapping("/sms")
	public R smsMessageRecord(Query query,String startTime,String endTime,String phone,String sendStatus ){
		return platformMobileContentService.pageList(Condition.getPage(query),startTime,endTime,phone,sendStatus);
	}

}
