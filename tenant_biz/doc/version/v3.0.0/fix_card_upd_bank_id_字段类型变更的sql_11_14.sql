ALTER TABLE zero_bpmn.client_bank_card_review_info
    MODIFY `bank_id` varchar(30) DEFAULT NULL COMMENT '银行代码';



ALTER TABLE `zero_bpmn`.`customer_account_open_info_modify` MODIFY COLUMN `user_id` bigint NOT NULL COMMENT '用户ID';

ALTER TABLE `zero_bpmn`.`customer_open_info_modify_apply` MODIFY COLUMN `user_id` bigint NOT NULL COMMENT '用户ID';


ALTER TABLE `zero_bpmn`.`open_account_taxation_info_modify` MODIFY COLUMN `user_id` bigint NOT NULL COMMENT '用户ID';

ALTER TABLE `zero_bpmn`.`open_account_w8ben_info_modify` MODIFY COLUMN `user_id` bigint NOT NULL COMMENT '用户ID';