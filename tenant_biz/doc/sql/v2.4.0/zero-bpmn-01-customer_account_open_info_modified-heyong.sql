
-- customer_account_open_info_modify 新增表

DROP TABLE IF EXISTS `customer_account_open_info_modify`;
CREATE TABLE `customer_account_open_info_modify`(
	`id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `apply_id` bigint NOT NULL COMMENT '修改申请记录ID',
	`application_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '预约流水号',
	`user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户ID',
	-- 个人信息 --
	`family_phone` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '住所电话',
	`marital_status` int NULL DEFAULT NULL COMMENT '婚姻：1.单身 2.已婚',
	`nationality` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '国籍',
	`id_card_valid_date_start` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身份证生效日期',
	`id_card_valid_date_end` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身份证失效日期',
	`education_level` int NULL DEFAULT 0 COMMENT '教育程度:1.小学或以下 2.中学 3.大专 4.大学或以上',
	`email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '电子邮箱',
	-- 地址信息 --
	`statement_receive_mode` int NULL DEFAULT 0 COMMENT '日结单及月结单发送方式[0、未知  1、电子邮箱  2、邮寄到住宅地址  3、邮寄到营业地址]',
	`contact_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '通讯地址',
	`contact_city_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系地址的城市',
	`contact_province_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系地址的省份',
	`contact_county_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系地址的区域',
	`contact_republic_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '通讯地址的国家',
	`contact_detail_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系地址的详细地址',
	`family_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '住宅地址',
	`family_city_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '住宅地址的城市',
	`family_county_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '住宅地址的区域',
	`family_province_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '住宅地址的省份',
	`family_republic_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '住宅地址的国家',
	`family_detail_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '住宅地址的详细地址',
	`permanent_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '永久居住地址',
	`permanent_city_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '永久城市',
	`permanent_county_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '永久区域',
	`permanent_province_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '永久省份',
	`permanent_republic_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '永久居住地址的国家',
	`permanent_detail_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '永久详情',
	-- 职业信息 --
	`profession_code` int NULL DEFAULT NULL COMMENT '就业情况类型[1-全职 2-在职 3-自雇 4-待业 5-其他]',
	`company_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '公司名称',
	`company_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '公司地址',
	`company_phone_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '公司电话',
	`company_city_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '公司地址的城市',
	`company_county_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '公司地址的区域',
	`company_province_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '公司地址的省份',
	`company_republic_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '公司地址的国家',
	`company_detail_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '公司地址的详细地址',
	`job_position` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '职位级别[1-普通员工 2-中层管理 3-高层管理]',
	`job_position_other` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '其他职位',
	`company_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '公司邮箱',
	`company_facsimile` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '公司传真',
	`company_business_nature` int NULL DEFAULT NULL COMMENT '公司业务性质',
	`company_business_nature_other` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '公司其它业务性质',
	-- 资产股资信息 --
	`annual_income` int NULL DEFAULT NULL COMMENT '年收入范围类型[1=<25万 2=25万-50万 3=50万-100万 4=>100万]',
	`annual_income_other` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '年收入具体金额',
	`net_asset` int NULL DEFAULT NULL COMMENT '净资产',
	`net_asset_other` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '净资产具体金额',
	`capital_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '财富来源[1-工资 2-业务利润 3-投资获取的资本 4-租金 5-退休金 6-家人成员提供 7-其他]',
	`capital_source_other` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '最初资金其它收入来源描述',
	`expected_capital_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预期财富来源[1-工资 2-业务利润 3-投资获取的资本 4-租金 5-退休金 0-其他]',
	`expected_capital_source_other` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预期资金其它收入来源',
	`trade_amount` int NULL DEFAULT NULL COMMENT '每月交易金额',
	`trade_amount_other` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '每月交易具体金额',
	`trade_frequency` int NULL DEFAULT NULL COMMENT '使用/交易频率[1-每日 2-每周 3-每月 4-每季 5-每半年 6-每年 0-其他]',
	`trade_frequency_other` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '其他使用/交易频率说明',

	-- 系统字段 --
	`create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
	`update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
	`create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
	`update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
	`is_deleted` int NULL DEFAULT 0 COMMENT '是否删除',
	`tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '租户 ID',
	`create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室',
	`status` int NULL DEFAULT NULL COMMENT '状态',
	 PRIMARY KEY (`id`) USING BTREE,
	 INDEX `idx_user_id`(`user_id` ASC) USING BTREE

)ENGINE = InnoDB AUTO_INCREMENT = 1016710125420213100 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '客户开户详细资料修改' ROW_FORMAT = DYNAMIC;



