package com.minigod.zero.platform.core;

import com.minigod.zero.platform.client.ICustomerInfoClient;
import com.minigod.zero.platform.entity.PlatformMsgReadRecordEntity;
import com.minigod.zero.platform.mapper.*;
import com.minigod.zero.platform.utils.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/11 9:26
 * @description：
 */
@Component
public class MessageMapperManager {

	@Autowired
	private SequenceService sequenceService;
	@Autowired
	private ICustomerInfoClient customerInfoClient;
	@Autowired
	private PlatformInvestMsgMapper investMsgMapper;
	@Autowired
	private PlatformMsgReadRecordMapper msgReadRecordMapper;
	@Autowired
	private PlatformEmailContentMapper emailContentMapper;
	@Autowired
	private PlatformMobileContentMapper mobileContentMapper;
	@Autowired
	private PlatformCommonTemplateMapper commonTemplateMapper;
	@Autowired
	private PlatformPushMsgHistoryMapper pushMsgHistoryMapper;
	@Autowired
	private PlatformMessagePropertiesMapper messagePropertiesMapper;

	public PlatformInvestMsgMapper investMsg(){
		return this.investMsgMapper;
	}

	public SequenceService sequenceService(){
		return this.sequenceService;
	}

	public PlatformMobileContentMapper mobileContent(){
		return this.mobileContentMapper;
	}

	public PlatformEmailContentMapper emailContent(){
		return this.emailContentMapper;
	}

	public PlatformPushMsgHistoryMapper pushMsg(){
		return this.pushMsgHistoryMapper;
	}

	public PlatformCommonTemplateMapper commonTemplate(){
		return this.commonTemplateMapper;
	}

	public PlatformMessagePropertiesMapper messageProperties(){
		return this.messagePropertiesMapper;
	}

	public ICustomerInfoClient customerInfoClient(){
		return this.customerInfoClient;
	}

	public PlatformMsgReadRecordMapper msgReadRecord(){
		return this.msgReadRecordMapper;
	}
}
