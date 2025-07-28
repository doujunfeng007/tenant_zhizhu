UPDATE zero_bpmn.client_bank_card_application t SET t.status = 2 WHERE t.id = 134;



UPDATE zero_bpmn.client_bank_card_info t SET t.status = 0 WHERE t.id = 121;


UPDATE zero_flow.act_hi_comment t SET t.FULL_MSG_ = '银行卡绑定成功222255', t.MESSAGE_ = '银行卡绑定成功222255'WHERE t.ID_ = 'bb64983f-ad2d-11ef-b559-e2e8c61e1372'
