
ALTER TABLE zero_flow.wf_copy DROP COLUMN create_by;
ALTER TABLE zero_flow.wf_copy DROP COLUMN create_time;
ALTER TABLE zero_flow.wf_copy DROP COLUMN update_by;
ALTER TABLE zero_flow.wf_copy DROP COLUMN update_time;
ALTER TABLE zero_flow.wf_copy DROP COLUMN del_flag;


ALTER TABLE zero_flow.wf_category DROP COLUMN create_by;
ALTER TABLE zero_flow.wf_category DROP COLUMN create_time;
ALTER TABLE zero_flow.wf_category DROP COLUMN update_by;
ALTER TABLE zero_flow.wf_category DROP COLUMN update_time;
ALTER TABLE zero_flow.wf_category DROP COLUMN del_flag;

ALTER TABLE zero_flow.wf_form DROP COLUMN create_by;
ALTER TABLE zero_flow.wf_form DROP COLUMN create_time;
ALTER TABLE zero_flow.wf_form DROP COLUMN update_by;
ALTER TABLE zero_flow.wf_form DROP COLUMN update_time;
ALTER TABLE zero_flow.wf_form DROP COLUMN del_flag;


ALTER TABLE zero_flow.wf_category CHANGE  `category_id` `id` bigint NOT NULL AUTO_INCREMENT COMMENT '流程分类id';
ALTER TABLE zero_flow.wf_copy CHANGE `copy_id` `id` bigint NOT NULL AUTO_INCREMENT COMMENT '抄送id';
ALTER TABLE zero_flow.wf_deploy_form CHANGE `deploy_id` `id` bigint NOT NULL AUTO_INCREMENT COMMENT '流程实例id';
ALTER TABLE zero_flow.wf_form CHANGE `form_id` `id` bigint NOT NULL AUTO_INCREMENT COMMENT '表单id';


ALTER TABLE zero_flow.wf_copy ADD COLUMN `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间';
ALTER TABLE zero_flow.wf_copy ADD COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间';
ALTER TABLE zero_flow.wf_copy ADD COLUMN `create_user` bigint NULL DEFAULT NULL COMMENT '创建人';
ALTER TABLE zero_flow.wf_copy ADD COLUMN `update_user` bigint NULL DEFAULT NULL COMMENT '修改人';
ALTER TABLE zero_flow.wf_copy ADD COLUMN `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除';
ALTER TABLE zero_flow.wf_copy ADD COLUMN `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '租户 ID';
ALTER TABLE zero_flow.wf_copy ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室';
ALTER TABLE zero_flow.wf_copy ADD COLUMN `status` int NULL DEFAULT NULL COMMENT '状态';


ALTER TABLE zero_flow.wf_category ADD COLUMN`create_time` datetime NULL DEFAULT NULL COMMENT '创建时间';
ALTER TABLE zero_flow.wf_category ADD COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间';
ALTER TABLE zero_flow.wf_category ADD COLUMN `create_user` bigint NULL DEFAULT NULL COMMENT '创建人';
ALTER TABLE zero_flow.wf_category ADD COLUMN `update_user` bigint NULL DEFAULT NULL COMMENT '修改人';
ALTER TABLE zero_flow.wf_category ADD COLUMN `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除';
ALTER TABLE zero_flow.wf_category ADD COLUMN `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '租户 ID';
ALTER TABLE zero_flow.wf_category ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室';
ALTER TABLE zero_flow.wf_category ADD COLUMN `status` int NULL DEFAULT NULL COMMENT '状态';


ALTER TABLE zero_flow.wf_deploy_form ADD COLUMN`create_time` datetime NULL DEFAULT NULL COMMENT '创建时间';
ALTER TABLE zero_flow.wf_deploy_form ADD COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间';
ALTER TABLE zero_flow.wf_deploy_form ADD COLUMN `create_user` bigint NULL DEFAULT NULL COMMENT '创建人';
ALTER TABLE zero_flow.wf_deploy_form ADD COLUMN `update_user` bigint NULL DEFAULT NULL COMMENT '修改人';
ALTER TABLE zero_flow.wf_deploy_form ADD COLUMN `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除';
ALTER TABLE zero_flow.wf_deploy_form ADD COLUMN `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '租户 ID';
ALTER TABLE zero_flow.wf_deploy_form ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室';
ALTER TABLE zero_flow.wf_deploy_form ADD COLUMN `status` int NULL DEFAULT NULL COMMENT '状态';


ALTER TABLE zero_flow.wf_form ADD COLUMN`create_time` datetime NULL DEFAULT NULL COMMENT '创建时间';
ALTER TABLE zero_flow.wf_form ADD COLUMN `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间';
ALTER TABLE zero_flow.wf_form ADD COLUMN `create_user` bigint NULL DEFAULT NULL COMMENT '创建人';
ALTER TABLE zero_flow.wf_form ADD COLUMN `update_user` bigint NULL DEFAULT NULL COMMENT '修改人';
ALTER TABLE zero_flow.wf_form ADD COLUMN `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除';
ALTER TABLE zero_flow.wf_form ADD COLUMN `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '租户 ID';
ALTER TABLE zero_flow.wf_form ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室';
ALTER TABLE zero_flow.wf_form ADD COLUMN `status` int NULL DEFAULT NULL COMMENT '状态';
