
-- 风险测评记录表中增加问卷ID字段
ALTER TABLE `zero_bpmn`.acct_risk_evaluation ADD COLUMN questionnaire_id bigint NULL DEFAULT NULL COMMENT '问卷ID' AFTER cust_id;


-- 创建风险测评问卷表
DROP TABLE IF EXISTS `zero_bpmn`.`acct_risk_questionnaire`;
CREATE TABLE `zero_bpmn`.`acct_risk_questionnaire`  (
                                            `id` bigint NOT NULL COMMENT '主键',
                                            `questionnaire_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '问卷名称',
                                            `questionnaire_type` int NOT NULL COMMENT '问卷类型',
                                            `questionnaire_desc` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问卷描述',
                                            `lang` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '多语言标识',
                                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                            `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
                                            `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                            `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
                                            `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除',
                                            `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '租户 ID',
                                            `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室',
                                            `status` int NULL DEFAULT NULL COMMENT '状态',
                                            PRIMARY KEY (`id`) USING BTREE,
                                            UNIQUE INDEX `idx_questionnaire_type_lang`(`questionnaire_type` ASC,`lang` ASC) USING BTREE,
                                            INDEX `idx_questionnaire_type`(`questionnaire_type` ASC) USING BTREE,
                                            INDEX `idx_lang`(`lang` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '风险测评问卷' ROW_FORMAT = DYNAMIC;


-- 创建风险测评问卷及题目关联表
DROP TABLE IF EXISTS `zero_bpmn`.`acct_risk_questionnaire_question`;
CREATE TABLE `zero_bpmn`.`acct_risk_questionnaire_question`  (
                                                     `id` bigint NOT NULL COMMENT '主键',
                                                     `questionnaire_id` bigint NOT NULL COMMENT '问卷ID',
                                                     `question_id` bigint NOT NULL COMMENT '问题ID',
                                                     `sort` int NULL DEFAULT NULL COMMENT '问题顺序',
                                                     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                                     `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
                                                     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                                     `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
                                                     `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除',
                                                     `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '租户 ID',
                                                     `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室',
                                                     `status` int NULL DEFAULT NULL COMMENT '状态',
                                                     PRIMARY KEY (`id`) USING BTREE,
                                                     UNIQUE INDEX `idx_questionnaire_id_question_id`(`questionnaire_id` ASC, `question_id` ASC) USING BTREE,
                                                     INDEX `idx_questionnaire_id`(`questionnaire_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '风险测评问卷及题目关联' ROW_FORMAT = DYNAMIC;


-- 创建风险测评问卷及风险等级关联表
DROP TABLE IF EXISTS `zero_bpmn`.`acct_risk_questionnaire_rating`;
CREATE TABLE `zero_bpmn`.`acct_risk_questionnaire_rating`  (
                                                   `id` bigint NOT NULL COMMENT '主键',
                                                   `questionnaire_id` bigint NOT NULL COMMENT '问卷ID',
                                                   `rating` int NOT NULL COMMENT '风险等级',
                                                   `rating_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '等级名称',
                                                   `sort` int NULL DEFAULT NULL COMMENT '等级顺序',
                                                   `score_upper` float NOT NULL COMMENT '分数上限',
                                                   `score_lower` float NOT NULL COMMENT '分数下限',
                                                   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                                   `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
                                                   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                                   `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
                                                   `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除',
                                                   `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '租户 ID',
                                                   `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室',
                                                   `status` int NULL DEFAULT NULL COMMENT '状态',
                                                   PRIMARY KEY (`id`) USING BTREE,
                                                   UNIQUE INDEX `idx_questionnaire_id_rating_name`(`questionnaire_id` ASC, `rating_name` ASC) USING BTREE,
                                                   INDEX `idx_questionnaire_id`(`questionnaire_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '风险测评问卷及风险等级关联' ROW_FORMAT = DYNAMIC;
