ALTER TABLE `zero_bpmn`.open_account_identity_confirm ADD COLUMN  `is_beneficial_owner` tinyint(1) NULL DEFAULT NULL COMMENT '您是否此帐户的最终实益拥有人/最终受益于交易及承担风险人士? ' AFTER `lineal_relatives_job`;
ALTER TABLE `zero_bpmn`.open_account_identity_confirm ADD COLUMN  `is_account_ultimately_responsible` tinyint(1) NULL DEFAULT NULL COMMENT '您是否向户口最终负责发出指示的人士?' AFTER `is_beneficial_owner`;

UPDATE `zero_bpmn`.open_account_identity_confirm SET `is_beneficial_owner`=1, `is_account_ultimately_responsible` =1;
