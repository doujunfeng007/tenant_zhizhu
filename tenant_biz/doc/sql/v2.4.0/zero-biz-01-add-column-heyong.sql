-- 添加租户ID
ALTER TABLE `zero_biz`.`platform_common_template` ADD COLUMN `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID' AFTER `url`;
ALTER TABLE `zero_biz`.`platform_email_content` ADD COLUMN `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID' AFTER `sendsmtp_status`;
ALTER TABLE `zero_biz`.`platform_invest_msg` ADD COLUMN `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID' AFTER `url`;
ALTER TABLE `zero_biz`.`platform_mobile_content` ADD COLUMN `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID' AFTER `lang`;
ALTER TABLE `zero_biz`.`platform_msg_read_record` ADD COLUMN `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID' AFTER `client_type`;
ALTER TABLE `zero_biz`.`platform_push_msg_history` ADD COLUMN `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID' AFTER `relation_id`;
ALTER TABLE `zero_biz`.`platform_sms_template` ADD COLUMN `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID' AFTER `channel`;
ALTER TABLE `zero_biz`.`platform_sms_template_ext` ADD COLUMN `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID' AFTER `remark`;
ALTER TABLE `zero_biz`.`platform_sys_msg` ADD COLUMN `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID' AFTER `pop_invalid_time`;
ALTER TABLE `zero_biz`.`platform_sys_sequence` ADD COLUMN `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID' AFTER `seq_version`;
ALTER TABLE `zero_biz`.`platform_template_type` ADD COLUMN `tenant_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID' AFTER `send_way`;


-- 机构开户支持多租户
ALTER TABLE zero_bpmn.organization_open_info ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室' AFTER `create_user`;
ALTER TABLE zero_bpmn.organization_open_info ADD COLUMN `status` int NULL DEFAULT NULL COMMENT '状态' AFTER `create_dept`;

ALTER TABLE zero_bpmn.organization_open_shareholder_info ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室' AFTER `create_user`;
ALTER TABLE zero_bpmn.organization_open_shareholder_info ADD COLUMN `status` int NULL DEFAULT NULL COMMENT '状态' AFTER `create_dept`;


-- PI认证支持多租户
ALTER TABLE zero_bpmn.professional_investor_pi_application ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室' AFTER `create_user`;
ALTER TABLE zero_bpmn.professional_investor_pi_info ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室' AFTER `create_user`;

ALTER TABLE zero_bpmn.professional_investor_pi_voucher_image ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室' AFTER `create_user`;
ALTER TABLE zero_bpmn.professional_investor_pi_voucher_image ADD COLUMN `status` int NULL DEFAULT NULL COMMENT '状态' AFTER `create_dept`;
ALTER TABLE zero_bpmn.professional_investor_pi_voucher_image ADD COLUMN `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除';



-- 风险等级测评及题库支持多租户
ALTER TABLE `zero_cust`.`acct_risk_question` MODIFY COLUMN `question_id` bigint NULL DEFAULT NULL COMMENT '问题ID';

ALTER TABLE `zero_cust`.`acct_risk_evaluation` ADD COLUMN `create_user` bigint NULL DEFAULT NULL COMMENT '创建人' AFTER `create_time`;
ALTER TABLE `zero_cust`.`acct_risk_evaluation` ADD COLUMN `update_user` bigint NULL DEFAULT NULL COMMENT '修改人' AFTER `update_time`;
ALTER TABLE `zero_cust`.`acct_risk_evaluation` ADD COLUMN `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除';
ALTER TABLE `zero_cust`.`acct_risk_evaluation` ADD COLUMN `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '租户 ID';
ALTER TABLE `zero_cust`.`acct_risk_evaluation` ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室';
ALTER TABLE `zero_cust`.`acct_risk_evaluation` ADD COLUMN `status` int NULL DEFAULT NULL COMMENT '状态';

ALTER TABLE `zero_cust`.`acct_risk_question` ADD COLUMN `create_user` bigint NULL DEFAULT NULL COMMENT '创建人' AFTER `create_time`;
ALTER TABLE `zero_cust`.`acct_risk_question` ADD COLUMN `update_user` bigint NULL DEFAULT NULL COMMENT '修改人' AFTER `update_time`;
ALTER TABLE `zero_cust`.`acct_risk_question` ADD COLUMN `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除';
ALTER TABLE `zero_cust`.`acct_risk_question` ADD COLUMN `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '租户 ID';
ALTER TABLE `zero_cust`.`acct_risk_question` ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室';
ALTER TABLE `zero_cust`.`acct_risk_question` ADD COLUMN `status` int NULL DEFAULT NULL COMMENT '状态';

ALTER TABLE `zero_cust`.`acct_risk_question_option` ADD COLUMN `create_user` bigint NULL DEFAULT NULL COMMENT '创建人' AFTER `create_time`;
ALTER TABLE `zero_cust`.`acct_risk_question_option` ADD COLUMN `update_user` bigint NULL DEFAULT NULL COMMENT '修改人' AFTER `update_time`;
ALTER TABLE `zero_cust`.`acct_risk_question_option` ADD COLUMN `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除';
ALTER TABLE `zero_cust`.`acct_risk_question_option` ADD COLUMN `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '租户 ID';
ALTER TABLE `zero_cust`.`acct_risk_question_option` ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室';
ALTER TABLE `zero_cust`.`acct_risk_question_option` ADD COLUMN `status` int NULL DEFAULT NULL COMMENT '状态';

ALTER TABLE `zero_cust`.`bpm_fund_acct_info` ADD COLUMN `create_user` bigint NULL DEFAULT NULL COMMENT '创建人' AFTER `create_time`;
ALTER TABLE `zero_cust`.`bpm_fund_acct_info` ADD COLUMN `update_user` bigint NULL DEFAULT NULL COMMENT '修改人' AFTER `update_time`;
ALTER TABLE `zero_cust`.`bpm_fund_acct_info` ADD COLUMN `is_deleted` int NULL DEFAULT 0 COMMENT '是否删除';
ALTER TABLE `zero_cust`.`bpm_fund_acct_info` ADD COLUMN `tenant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '租户 ID';
ALTER TABLE `zero_cust`.`bpm_fund_acct_info` ADD COLUMN `create_dept` bigint NULL DEFAULT NULL COMMENT '创建科室';
ALTER TABLE `zero_cust`.`bpm_fund_acct_info` ADD COLUMN `status` int NULL DEFAULT NULL COMMENT '状态';


-- OPS系统邮箱收件地址
INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_sealed`, `is_deleted`)
VALUES (1823228745599201282,'000000', 0, 'system_email_address', '-1', 'OPS系统邮箱收件地址', 0, '接收新开户、银行卡绑定、eDDA授权、入金、出金、PI认证，电邮通知运营及fundservice', 0, 0);

INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_sealed`, `is_deleted`)
VALUES (1823229138634846209,'000000', 1823228745599201282, 'system_email_address', 'ops@lionfin.com', 'system_email_ops', 1, 'OPS收件箱', 0, 0);

INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_sealed`, `is_deleted`)
VALUES (1823229370705686530,'000000', 1823228745599201282, 'system_email_address', 'fundservice@lionfin.com', 'system_email_fundservice', 2, 'FundService收件箱', 0, 0);

INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_sealed`, `is_deleted`)
VALUES (1823229664285995010,'000000', 1823228745599201282, 'system_email_address', 'finance@lionfin.com', 'system_email_finance', 3, 'finance收件箱', 0, 0);

INSERT INTO `zero_cloud`.`zero_dict_biz` (`id`, `tenant_id`, `parent_id`, `code`, `dict_key`, `dict_value`, `sort`, `remark`, `is_sealed`, `is_deleted`)
VALUES (1823229874328350721,'000000', 1823228745599201282, 'system_email_address', 'cs@lionfin.com', 'system_email_cs', 4, 'cs收件箱地址', 0, 0);


/*
这张表不插入已经不用它了,但以前是有关联关系的。
INSERT INTO `zero_biz`.`platform_template_type` (`id`, `bus_type`, `temp_name`, `parent_id`, `send_way`, `tenant_id`, `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (1816030891542332201, 1, '开户提交审批提醒', 31, 0, '000000', 1798979554172383233, NULL, NOW(), 1798979554172383233, NOW(), 1, 0);*/

-- OPS邮件模版
INSERT INTO `zero_biz`.`platform_common_template` (`id`, `temp_code`, `temp_code_hans`, `temp_code_hant`, `temp_code_en`, `temp_param`, `title`, `title_hant`, `title_en`, `content`, `content_hant`, `content_en`, `apply_explain`, `msg_type`, `url`, `tenant_id`, `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (1669534547039200001, 3003, 3003, 3003, 3003, NULL, '【新开户通知】{0} {1}开户申请审批提醒', '【新開戶通知】{0} {1}開戶申請審批提醒', '【New Open Account Notification】{0} {1} Account opening submission approval reminder', '<html>
<body>
<table align="center" cellpadding="0" cellspacing="0" width="900" style="margin:0 auto;border:1px solid #e7e2ef;
       box-shadow:0 4px 8px 0 #aaaaaa; font-size: 14px; font-family: Microsoft YaHei; ">
    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr height="">
        <td width="45" height="67" style="background-color:#e7e2ef;">&nbsp;</td>
        <td height="67" colspan="8" style="background-color:#e7e2ef;">
            <div style="background-color:#e7e2ef; width:100%">
                <img alt="logo" title="" src="https://www.digifingroup.com/assets/logo_and_text.d001de9e.png"
                     height="50" width="400"/></div>
        </td>
        <td width="45" height="67" style="background-color:#e7e2ef;"> &nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <!-- 头部结束 -->
    <tr style="border-left: 1px solid #787878; border-right: 1px solid #787878;">
        <td width="45" height="50">&nbsp;</td>
        <td height="" colspan="8">
            <div style="padding-top:10px; font-size:14px; color:#333; line-height:26px;">
                <div style="padding:0px; margin:0px; line-height:26px;"><b>Hi,</b></div>
                <div style="padding:0px; margin:0px; line-height:26px; margin-top:15px;"> 请OPS知悉有新开户申请，请及时审核，详细信息如下：<br>
                    <table width="100%" border="1px" cellspacing="0" cellpadding="0">
                        <tbody>
                        <tr>
                            <td width="200"
                                style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">申请时间：</td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {0}</td>
                        </tr>
                        <tr>
                            <td width="200" style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">客户名称：</td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {1}</td>
                        </tr>
                        <tr>
                            <td width="200"
                                style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">开户方式：</td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {2}</td>
                        </tr>
                        <tr>
                            <td width="200" style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">手机号码：</td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {3}</td>
                        </tr>
                        </tbody>
                    </table>
                    <div style="padding:0px; margin:0px;margin-top:15px; line-height:26px;"> 此致<br> <b><a
                        style="text-decoration:none; color:#blue;"
                        href="https://www.digifingroup.com/#/home">狮瀚环球金融有限公司</a></b></div>
                </div>
            </div>
            <br/><br/>
            <div style="line-height:26px;margin-top:10px; padding:0px">
                <strong>网路安全提示：保护好您的登录/安全凭证</strong></div>
            <div style="line-height:26px;padding:0px"> 提防骗案！狮瀚环球不会以电邮要求客户提供个人资料，用户名称、密码丶一次性密码或其他帐户资料；<br/>
                切勿透过任何电子邮件提供的超连结登入网上服务，或任何社交媒体平臺、电邮、简讯或电话等方式，透露您的登录帐号、密码和一次性登录密码
                (OTP) 给他人，务请小心保管个人资料；<br/> 不要使用公共场所的装置登录交易系统；<br/>
                定期修改密码、增强密码强度，能更有效保障您的帐户安全；<br/>
                如您怀疑自己遇上骗案，请立即致电客户服务热线向我们举报，以提防损失。<br/>
            </div>
        </td>
        <td width="45" height="50" style="border-right: 1px solid #e7e2ef;">&nbsp;</td>
    </tr>    <!-- 中间结束 -->
    <tr height="15">
        <td colspan="10"></td>
    </tr>

    <tr height="15">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <!-- 中间部分结束 -->
    <tr style="background-color:#e7e2ef;">
        <td width="45" height="50">&nbsp;</td>
        <td colspan="8"> <span style="width: 250px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size:
            18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">关于[狮瀚环球资产管理平台]</span>
        </td>
        <td width="45" height="50">&nbsp;</td>
    </tr>
    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <tr style="background-color:#e7e2ef;">
        <td width="45" height="100">&nbsp;</td>
        <td colspan="7" valign="top"> <span style="font-size: 16px; font-family: Microsoft YaHei;">
            【狮瀚环球资产管理平台】作为一站式金融资产管理平台，在全球范围内為投资者提供优质财富管理服务，由数字金融集团（DigiFIN Group）旗下狮瀚环球金融有限公司全资拥有。狮瀚环球金融有限公司创立于 1998 年，为香港证监会持牌法团
            (获发牌照包括第一类：证券交易、第四类：就证券提供意见及第九类：提供资产管理)，中央编号: AEO169。</span> <br> <span
            style="font-size: 16px; font-family: Microsoft YaHei;"></span>
        </td>
        <td width="45" height="100">&nbsp;</td>
        <td width="45" height="100">&nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="45" height="50">&nbsp;</td>
        <td colspan="8"> <span style="width: 120px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size:
            18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">&nbsp;&nbsp;联络我们&nbsp;&nbsp;</span>
        </td>
        <td width="45" height="50">&nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top"><span
            style="font-size: 16px; font-family: Microsoft YaHei;">地址：香港上环永乐街88号25楼</span>
        </td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top">
            <span style="font-size: 16px; font-family: Microsoft YaHei;">客户服务热线：+852 3896 3896(香港)</span> <br>
            <span style="font-size: 16px; font-family: Microsoft YaHei;">星期一至五早上9时至下午6时(星期六、日及公众假期除外)</span>
        </td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top">
            <span style="font-size: 16px; font-family: Microsoft YaHei;">电邮(客戶服务)：cs@digifingroup.com</span><br>
            <span style="font-size: 16px; font-family: Microsoft YaHei;">E-mail(Customer Service)：cs@digifingroup.com</span>
        </td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
</table>
</body>
</html>
','<html>
<body>
<table align="center" cellpadding="0" cellspacing="0" width="900" style="margin:0 auto;border:1px solid #e7e2ef;
       box-shadow:0 4px 8px 0 #aaaaaa; font-size: 14px; font-family: Microsoft YaHei; ">
    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr height="">
        <td width="45" height="67" style="background-color:#e7e2ef;">&nbsp;</td>
        <td height="67" colspan="8" style="background-color:#e7e2ef;">
            <div style="background-color:#e7e2ef; width:100%">
                <img alt="logo" title="" src="https://www.digifingroup.com/assets/logo_and_text.d001de9e.png"
                     height="50" width="400"/></div>
        </td>
        <td width="45" height="67" style="background-color:#e7e2ef;"> &nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <!-- 頭部結束 -->
    <tr style="border-left: 1px solid #787878; border-right: 1px solid #787878;">
        <td width="45" height="50">&nbsp;</td>
        <td height="" colspan="8">
            <div style="padding-top:10px; font-size:14px; color:#333; line-height:26px;">
                <div style="padding:0px; margin:0px; line-height:26px;"><b>Hi,</b></div>
                <div style="padding:0px; margin:0px; line-height:26px; margin-top:15px;"> 請OPS知悉有新開戶申請，請及時審核，詳細信息如下：<br>
                    <table width="100%" border="1px" cellspacing="0" cellpadding="0">
                        <tbody>
                        <tr>
                            <td width="200"
                                style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">申請時間：
                            </td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {0}
                            </td>
                        </tr>
                        <tr>
                            <td width="200" style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">
                                客戶名稱：
                            </td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {1}
                            </td>
                        </tr>
                        <tr>
                            <td width="200"
                                style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">開戶方式：
                            </td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {2}
                            </td>
                        </tr>
                        <tr>
                            <td width="200" style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">
                                手機號碼：
                            </td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {3}
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div style="padding:0px; margin:0px;margin-top:15px; line-height:26px;"> 此致<br>
                        <b><a style="text-decoration:none; color:#blue;" href="https://www.digifingroup.com/#/home">狮瀚环球金融有限公司</a></b>
                    </div>
                </div>
            </div>
            <br/><br/>
            <div style="line-height:26px;margin-top:10px; padding:0px">
                <strong>網路安全提示：保護好您的登錄/安全憑證</strong></div>
            <div style="line-height:26px;padding:0px"> 提防騙案！獅瀚環球不會以電郵要求客護提供個人資料，用護名稱、密碼丶壹次性密碼或其他帳護資料；<br/>
                切勿透過任何電子郵件提供的超連結登入網上服務，或任何社交媒體平臺、電郵、間訊或電話等方式，透露您的登錄帳號、密碼和壹次性登錄密碼
                (OTP) 給他人，務請小心保管個人資料；<br/> 不要使用公共場所的裝置登錄交易系統；<br/>
                定期修改密碼、增強密碼強度，能更有效保障您的帳護安全；<br/>
                如您懷疑自己遇上騙案，請立即致電客護服務熱線向我們舉報，以提防損失。<br/>
            </div>
        </td>
        <td width="45" height="50" style="border-right: 1px solid #e7e2ef;">&nbsp;</td>
    </tr>    <!-- 中間結束 -->
    <tr height="15">
        <td colspan="10"></td>
    </tr>

    <tr height="15">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <!-- 中間部分結束 -->
    <tr style="background-color:#e7e2ef;">
        <td width="45" height="50">&nbsp;</td>
        <td colspan="8"> <span style="width: 250px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size:
            18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">關於
            [獅瀚環球資產管理平臺]</span>
        </td>
        <td width="45" height="50">&nbsp;</td>
    </tr>
    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <tr style="background-color:#e7e2ef;">
        <td width="45" height="100">&nbsp;</td>
        <td colspan="7" valign="top"> <span style="font-size: 16px; font-family: Microsoft
            YaHei;">【獅瀚環球資產管理平臺】作為壹站式金融資產管理平臺，在全球範圍內為投資者提供優質材富管理服務，由數字金融集團（DigiFIN
            Group）旗下獅瀚環球金融有限公司全資擁有。獅瀚環球金融有限公司創立於 1998 年，為香港證監會持牌法團
            (獲發牌照包括第壹類：證券交易、第四類：就證券提供意見及第九類：提供資產管理)，中央編號: AEO169。</span> <br> <span
            style="font-size: 16px; font-family: Microsoft YaHei;"></span>
        </td>
        <td width="45" height="100">&nbsp;</td>
        <td width="45" height="100">&nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="45" height="50">&nbsp;</td>
        <td colspan="8"> <span style="width: 120px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size:
            18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">&nbsp;&nbsp;聯酪我們&nbsp;&nbsp;</span>
        </td>
        <td width="45" height="50">&nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top"><span
            style="font-size: 16px; font-family: Microsoft YaHei;">地址：香港上環永樂街88號25樓</span>
        </td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top"> <span style="font-size: 16px; font-family: Microsoft YaHei;">客護服務熱線：+852
        3896 3896(香港)</span> <br> <span style="font-size: 16px; font-family: Microsoft YaHei;">星期壹至五早上9時至下午6時(星期六、日及公眾假期除外)</span>
        </td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top">
            <span style="font-size: 16px; font-family: Microsoft YaHei;">電郵(客護服務)：cs@digifingroup.com</span><br>
            <span
                style="font-size: 16px; font-family: Microsoft YaHei;">E-mail(Customer Service)：cs@digifingroup.com</span>
        </td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
</table>
</body>
</html>
','<html>
<body>
<table align="center" cellpadding="0" cellspacing="0" width="900" style="margin:0 auto;border:1px solid #e7e2ef;
       box-shadow:0 4px 8px 0 #aaaaaa; font-size: 14px; font-family: Microsoft YaHei; ">
    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr height="">
        <td width="45" height="67" style="background-color:#e7e2ef;">&nbsp;</td>
        <td height="67" colspan="8" style="background-color:#e7e2ef;">
            <div style="background-color:#e7e2ef; width:100%">
                <img alt="logo" title="" src="https://www.digifingroup.com/assets/logo_and_text.d001de9e.png"
                     height="50" width="400"/></div>
        </td>
        <td width="45" height="67" style="background-color:#e7e2ef;"> &nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <!-- 頭部結束 -->
    <tr style="border-left: 1px solid #787878; border-right: 1px solid #787878;">
        <td width="45" height="50">&nbsp;</td>
        <td height="" colspan="8">
            <div style="padding-top:10px; font-size:14px; color:#333; line-height:26px;">
                <div style="padding:0px; margin:0px; line-height:26px;"><b>Hi,</b></div>
                <div style="padding:0px; margin:0px; line-height:26px; margin-top:15px;"> Please be informed of any new account opening applications and review them promptly. The detailed information is as follows:<br>
                    <table width="100%" border="1px" cellspacing="0" cellpadding="0">
                        <tbody>
                        <tr>
                            <td width="200"
                                style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">Application time：
                            </td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {0}
                            </td>
                        </tr>
                        <tr>
                            <td width="200" style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">
                                Customer Name：
                            </td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {1}
                            </td>
                        </tr>
                        <tr>
                            <td width="200"
                                style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">Account opening method：
                            </td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {2}
                            </td>
                        </tr>
                        <tr>
                            <td width="200" style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">
                                Phone number：
                            </td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {3}
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div style="padding:0px; margin:0px;margin-top:15px; line-height:26px;">Best wishes<br> <b>
                        <a style="text-decoration:none; color:#blue;" href="https://www.digifingroup.com/#/home">Shihan Global Financial Co., Ltd</a></b>
                    </div>
                </div>
            </div>
            <br/><br/>
            <div style="line-height:26px;margin-top:10px; padding:0px">
                <strong>Network Security Tip: Protect your login/security credentials</strong></div>
            <div style="line-height:26px; padding: 0px">Beware of scams! Shihan Global will not request customer
                protection to provide personal information through email, using protection name, password, one-time
                password or other account protection information <br/>
                Do not disclose your login account, password, and one-time login password through any hyperlinks
                provided by email to access online services, or through any social media platforms, emails, messages, or
                phone calls
                (OTP) To others, please be careful to keep personal information safe Do not use devices in public
                places to log in to the trading system <br/>
                Regularly changing passwords and enhancing password strength can more effectively protect your account
                security <br/>
                If you suspect that you have encountered a fraud case, please immediately call our customer service
                hotline to report it to us to prevent losses <br/>
            </div>
        </td>
        <td width="45" height="50" style="border-right: 1px solid #e7e2ef;">&nbsp;</td>
    </tr>    <!-- 中間結束 -->
    <tr height="15">
        <td colspan="10"></td>
    </tr>

    <tr height="15">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <!-- 中間部分結束 -->
    <tr style="background-color:#e7e2ef;">
        <td width="45" height="50">&nbsp;</td>
        <td colspan="8">
            <span style="width: 450px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size:18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">About Shihan Global Asset Management Platform</span>
        </td>
        <td width="45" height="50">&nbsp;</td>
    </tr>
    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <tr style="background-color:#e7e2ef;">
        <td width="45" height="100">&nbsp;</td>
        <td colspan="7" valign="top">
            <span style="font-size: 15px;  font-family: Microsoft YaHei;"> As a one-stop financial asset management platform, Shihan Global Asset Management Platform provides high-quality wealth management services for investors worldwide. It is managed by DigiFIN, a digital finance group
Shihan Global Financial Co., Ltd., a subsidiary of the Group, is wholly owned. Lion Rock Global Financial Limited was founded in 1998 and is a licensed corporation of the Hong Kong Securities and Futures Commission
(License issued includes Class 1: Securities Trading, Class 4: Providing Opinions on Securities, and Class 9: Providing Asset Management), Central Number: AEO169</span> <br>
        </td>
        <td width="45" height="100">&nbsp;</td>
        <td width="45" height="100">&nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="45" height="50">&nbsp;</td>
        <td colspan="8"> <span style="width: 120px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size:
            18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">&nbsp;&nbsp;Contact Us&nbsp;&nbsp;</span>
        </td>
        <td width="45" height="50">&nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top">
            <span style="font-size: 15px; font-family: Microsoft YaHei;">Address: 25th Floor, 88 Wing Lok Street, Sheung Wan, Hong Kong</span>
        </td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top">
            <span style="font-size: 15px; font-family: Microsoft YaHei;">Customer Service Hotline：+852 3896 3896(HongKong)</span> <br>
            <span style="font-size: 15px; font-family: Microsoft YaHei;">Monday to Friday from 9am to 6pm (excluding Saturdays, Sundays, and public holidays)</span>
        </td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top">
            <span style="font-size: 15px; font-family: Microsoft YaHei;">E-mail (Customer Service)：cs@digifingroup.com</span>
        </td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
</table>
</body>
</html>
', NULL, 2, NULL, '000000', 1123598821738675201, NULL, NOW(), 1123598821738675201, NOW(), 1, 0);



/*
这张表不插入已经不用它了,但以前是有关联关系的。
INSERT INTO `zero_biz`.`platform_template_type` (`id`, `bus_type`, `temp_name`, `parent_id`, `send_way`, `tenant_id`, `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (1816030891542332202, 1, 'PI认证审批提醒', 31, 0, '000000', 1798979554172383233, NULL, NOW(), 1798979554172383233, NOW(), 1, 0);*/

INSERT INTO `zero_biz`.`platform_common_template` (`id`, `temp_code`, `temp_code_hans`, `temp_code_hant`, `temp_code_en`, `temp_param`, `title`, `title_hant`, `title_en`, `content`, `content_hant`, `content_en`, `apply_explain`, `msg_type`, `url`, `tenant_id`, `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (1669534547039200002, 3004, 3004, 3004, 3004, null, '【PI认证申请通知】{0} {1}PI认证审批提醒', '【PI認證申請通知】{0} {1}PI認證審批提醒', '【Notice of PI Certification Application】{0} {1} PI certification approval reminder', '<html>
<body>
<table align="center" cellpadding="0" cellspacing="0" width="900" style="margin:0 auto;border:1px solid #e7e2ef;
       box-shadow:0 4px 8px 0 #aaaaaa; font-size: 14px; font-family: Microsoft YaHei; ">
    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr height="">
        <td width="45" height="67" style="background-color:#e7e2ef;">&nbsp;</td>
        <td height="67" colspan="8" style="background-color:#e7e2ef;">
            <div style="background-color:#e7e2ef; width:100%">
                <img alt="logo" title="" src="https://www.digifingroup.com/assets/logo_and_text.d001de9e.png"
                     height="50" width="400"/></div>
        </td>
        <td width="45" height="67" style="background-color:#e7e2ef;"> &nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <!-- 头部结束 -->
    <tr style="border-left: 1px solid #787878; border-right: 1px solid #787878;">
        <td width="45" height="50">&nbsp;</td>
        <td height="" colspan="8">
            <div style="padding-top:10px; font-size:14px; color:#333; line-height:26px;">
                <div style="padding:0px; margin:0px; line-height:26px;"><b>Hi,</b></div>
                <div style="padding:0px; margin:0px; line-height:26px; margin-top:15px;"> 请OPS知悉有PI认证申请，请及时审核，详细信息如下：<br>
                    <table width="100%" border="1px" cellspacing="0" cellpadding="0">
                        <tbody>
                        <tr>
                            <td width="200"
                                style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">申请时间：</td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {0}</td>
                        </tr>
                        <tr>
                            <td width="200" style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">客户名称：</td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {1}</td>
                        </tr>
                        </tbody>
                    </table>
                    <div style="padding:0px; margin:0px;margin-top:15px; line-height:26px;"> 此致<br> <b><a
                        style="text-decoration:none; color:#blue;"
                        href="https://www.digifingroup.com/#/home">狮瀚环球金融有限公司</a></b></div>
                </div>
            </div>
            <br/><br/>
            <div style="line-height:26px;margin-top:10px; padding:0px">
                <strong>网路安全提示：保护好您的登录/安全凭证</strong></div>
            <div style="line-height:26px;padding:0px"> 提防骗案！狮瀚环球不会以电邮要求客户提供个人资料，用户名称、密码丶一次性密码或其他帐户资料；<br/>
                切勿透过任何电子邮件提供的超连结登入网上服务，或任何社交媒体平臺、电邮、简讯或电话等方式，透露您的登录帐号、密码和一次性登录密码
                (OTP) 给他人，务请小心保管个人资料；<br/> 不要使用公共场所的装置登录交易系统；<br/>
                定期修改密码、增强密码强度，能更有效保障您的帐户安全；<br/>
                如您怀疑自己遇上骗案，请立即致电客户服务热线向我们举报，以提防损失。<br/>
            </div>
        </td>
        <td width="45" height="50" style="border-right: 1px solid #e7e2ef;">&nbsp;</td>
    </tr>    <!-- 中间结束 -->
    <tr height="15">
        <td colspan="10"></td>
    </tr>

    <tr height="15">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <!-- 中间部分结束 -->
    <tr style="background-color:#e7e2ef;">
        <td width="45" height="50">&nbsp;</td>
        <td colspan="8"> <span style="width: 250px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size:
            18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">关于[狮瀚环球资产管理平台]</span>
        </td>
        <td width="45" height="50">&nbsp;</td>
    </tr>
    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <tr style="background-color:#e7e2ef;">
        <td width="45" height="100">&nbsp;</td>
        <td colspan="7" valign="top"> <span style="font-size: 16px; font-family: Microsoft YaHei;">
            【狮瀚环球资产管理平台】作为一站式金融资产管理平台，在全球范围内為投资者提供优质财富管理服务，由数字金融集团（DigiFIN Group）旗下狮瀚环球金融有限公司全资拥有。狮瀚环球金融有限公司创立于 1998 年，为香港证监会持牌法团
            (获发牌照包括第一类：证券交易、第四类：就证券提供意见及第九类：提供资产管理)，中央编号: AEO169。</span> <br> <span
            style="font-size: 16px; font-family: Microsoft YaHei;"></span>
        </td>
        <td width="45" height="100">&nbsp;</td>
        <td width="45" height="100">&nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="45" height="50">&nbsp;</td>
        <td colspan="8"> <span style="width: 120px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size:
            18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">&nbsp;&nbsp;联络我们&nbsp;&nbsp;</span>
        </td>
        <td width="45" height="50">&nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top"><span
            style="font-size: 16px; font-family: Microsoft YaHei;">地址：香港上环永乐街88号25楼</span>
        </td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top">
            <span style="font-size: 16px; font-family: Microsoft YaHei;">客户服务热线：+852 3896 3896(香港)</span> <br>
            <span style="font-size: 16px; font-family: Microsoft YaHei;">星期一至五早上9时至下午6时(星期六、日及公众假期除外)</span>
        </td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top">
            <span style="font-size: 16px; font-family: Microsoft YaHei;">电邮(客戶服务)：cs@digifingroup.com</span><br>
            <span style="font-size: 16px; font-family: Microsoft YaHei;">E-mail(Customer Service)：cs@digifingroup.com</span>
        </td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
</table>
</body>
</html>
','<html>
<body>
<table align="center" cellpadding="0" cellspacing="0" width="900" style="margin:0 auto;border:1px solid #e7e2ef;
       box-shadow:0 4px 8px 0 #aaaaaa; font-size: 14px; font-family: Microsoft YaHei; ">
    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr height="">
        <td width="45" height="67" style="background-color:#e7e2ef;">&nbsp;</td>
        <td height="67" colspan="8" style="background-color:#e7e2ef;">
            <div style="background-color:#e7e2ef; width:100%">
                <img alt="logo" title="" src="https://www.digifingroup.com/assets/logo_and_text.d001de9e.png"
                     height="50" width="400"/></div>
        </td>
        <td width="45" height="67" style="background-color:#e7e2ef;"> &nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <!-- 頭部結束 -->
    <tr style="border-left: 1px solid #787878; border-right: 1px solid #787878;">
        <td width="45" height="50">&nbsp;</td>
        <td height="" colspan="8">
            <div style="padding-top:10px; font-size:14px; color:#333; line-height:26px;">
                <div style="padding:0px; margin:0px; line-height:26px;"><b>Hi,</b></div>
                <div style="padding:0px; margin:0px; line-height:26px; margin-top:15px;"> 請OPS知悉有PI認證申請，請及時審核，詳細信息如下：<br>
                    <table width="100%" border="1px" cellspacing="0" cellpadding="0">
                        <tbody>
                        <tr>
                            <td width="200"
                                style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">申請時間：
                            </td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {0}
                            </td>
                        </tr>
                        <tr>
                            <td width="200" style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">
                                客戶名稱：
                            </td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {1}
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div style="padding:0px; margin:0px;margin-top:15px; line-height:26px;"> 此致<br> <b>
                        <a style="text-decoration:none; color:#blue;" href="https://www.digifingroup.com/#/home">獅瀚環球金融有限公司</a></b>
                    </div>
                </div>
            </div>
            <br/><br/>
            <div style="line-height:26px;margin-top:10px; padding:0px">
                <strong>網路安全提示：保護好您的登錄/安全憑證</strong></div>
            <div style="line-height:26px;padding:0px"> 提防騙案！獅瀚環球不會以電郵要求客護提供個人資料，用護名稱、密碼丶壹次性密碼或其他帳護資料；<br/>
                切勿透過任何電子郵件提供的超連結登入網上服務，或任何社交媒體平臺、電郵、間訊或電話等方式，透露您的登錄帳號、密碼和壹次性登錄密碼
                (OTP) 給他人，務請小心保管個人資料；<br/> 不要使用公共場所的裝置登錄交易系統；<br/>
                定期修改密碼、增強密碼強度，能更有效保障您的帳護安全；<br/>
                如您懷疑自己遇上騙案，請立即致電客護服務熱線向我們舉報，以提防損失。<br/>
            </div>
        </td>
        <td width="45" height="50" style="border-right: 1px solid #e7e2ef;">&nbsp;</td>
    </tr>    <!-- 中間結束 -->
    <tr height="15">
        <td colspan="10"></td>
    </tr>

    <tr height="15">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <!-- 中間部分結束 -->
    <tr style="background-color:#e7e2ef;">
        <td width="45" height="50">&nbsp;</td>
        <td colspan="8"> <span style="width: 250px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size:
            18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">關於
            [獅瀚環球資產管理平臺]</span>
        </td>
        <td width="45" height="50">&nbsp;</td>
    </tr>
    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <tr style="background-color:#e7e2ef;">
        <td width="45" height="100">&nbsp;</td>
        <td colspan="7" valign="top"> <span style="font-size: 16px; font-family: Microsoft YaHei;">【獅瀚環球資產管理平臺】作為壹站式金融資產管理平臺，在全球範圍內為投資者提供優質材富管理服務，由數字金融集團（DigiFIN
            Group）旗下獅瀚環球金融有限公司全資擁有。獅瀚環球金融有限公司創立於 1998 年，為香港證監會持牌法團
            (獲發牌照包括第壹類：證券交易、第四類：就證券提供意見及第九類：提供資產管理)，中央編號: AEO169。</span> <br> <span
            style="font-size: 16px; font-family: Microsoft YaHei;"></span>
        </td>
        <td width="45" height="100">&nbsp;</td>
        <td width="45" height="100">&nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="45" height="50">&nbsp;</td>
        <td colspan="8"> <span style="width: 120px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size:
            18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">&nbsp;&nbsp;聯酪我們&nbsp;&nbsp;</span>
        </td>
        <td width="45" height="50">&nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top"><span
            style="font-size: 16px; font-family: Microsoft YaHei;">地址：香港上環永樂街88號25樓</span>
        </td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top"> <span style="font-size: 16px; font-family: Microsoft YaHei;">客護服務熱線：+852
        3896 3896(香港)</span> <br> <span style="font-size: 16px; font-family: Microsoft YaHei;">星期壹至五早上9時至下午6時(星期六、日及公眾假期除外)</span>
        </td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top">
            <span style="font-size: 16px; font-family: Microsoft YaHei;">電郵(客護服務)：cs@digifingroup.com</span><br>
            <span style="font-size: 16px; font-family: Microsoft YaHei;">E-mail(Customer Service)：cs@digifingroup.com</span>
        </td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
</table>
</body>
</html>
','<html>
<body>
<table align="center" cellpadding="0" cellspacing="0" width="900" style="margin:0 auto;border:1px solid #e7e2ef;
       box-shadow:0 4px 8px 0 #aaaaaa; font-size: 14px; font-family: Microsoft YaHei; ">
    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr height="">
        <td width="45" height="67" style="background-color:#e7e2ef;">&nbsp;</td>
        <td height="67" colspan="8" style="background-color:#e7e2ef;">
            <div style="background-color:#e7e2ef; width:100%">
                <img alt="logo" title="" src="https://www.digifingroup.com/assets/logo_and_text.d001de9e.png"
                     height="50" width="400"/></div>
        </td>
        <td width="45" height="67" style="background-color:#e7e2ef;"> &nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <!-- 頭部結束 -->
    <tr style="border-left: 1px solid #787878; border-right: 1px solid #787878;">
        <td width="45" height="50">&nbsp;</td>
        <td height="" colspan="8">
            <div style="padding-top:10px; font-size:14px; color:#333; line-height:26px;">
                <div style="padding:0px; margin:0px; line-height:26px;"><b>Hi,</b></div>
                <div style="padding:0px; margin:0px; line-height:26px; margin-top:15px;"> Please be aware of the PI
                    certification application and review it promptly. The detailed information is as follows:<br>
                    <table width="100%" border="1px" cellspacing="0" cellpadding="0">
                        <tbody>
                        <tr>
                            <td width="200"
                                style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">Application time：
                            </td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {0}
                            </td>
                        </tr>
                        <tr>
                            <td width="200" style="padding-bottom:6px; font-size:14px; background-color:#e7e2ef">
                                Customer Name：
                            </td>
                            <td valign="top" style="padding-bottom:6px; line-height:28px; font-size:14px;">
                                {1}
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div style="padding:0px; margin:0px;margin-top:15px; line-height:26px;">Best wishes<br> <b>
                        <a style="text-decoration:none; color:#blue;" href="https://www.digifingroup.com/#/home">Shihan Global Financial Co., Ltd</a></b>
                    </div>
                </div>
            </div>
            <br/><br/>
            <div style="line-height:26px;margin-top:10px; padding:0px">
                <strong>Network Security Tip: Protect your login/security credentials</strong></div>
            <div style="line-height:26px; padding: 0px">Beware of scams! Shihan Global will not request customer
                protection to provide personal information through email, using protection name, password, one-time
                password or other account protection information <br/>
                Do not disclose your login account, password, and one-time login password through any hyperlinks
                provided by email to access online services, or through any social media platforms, emails, messages, or
                phone calls
                (OTP) To others, please be careful to keep personal information safe Do not use devices in public
                places to log in to the trading system <br/>
                Regularly changing passwords and enhancing password strength can more effectively protect your account
                security <br/>
                If you suspect that you have encountered a fraud case, please immediately call our customer service
                hotline to report it to us to prevent losses <br/>
            </div>
        </td>
        <td width="45" height="50" style="border-right: 1px solid #e7e2ef;">&nbsp;</td>
    </tr>    <!-- 中間結束 -->
    <tr height="15">
        <td colspan="10"></td>
    </tr>

    <tr height="15">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <!-- 中間部分結束 -->
    <tr style="background-color:#e7e2ef;">
        <td width="45" height="50">&nbsp;</td>
        <td colspan="8">
            <span style="width: 450px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size:18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">About Shihan Global Asset Management Platform</span>
        </td>
        <td width="45" height="50">&nbsp;</td>
    </tr>
    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
    <tr style="background-color:#e7e2ef;">
        <td width="45" height="100">&nbsp;</td>
        <td colspan="7" valign="top">
            <span style="font-size: 15px;  font-family: Microsoft YaHei;"> As a one-stop financial asset management platform, Shihan Global Asset Management Platform provides high-quality wealth management services for investors worldwide. It is managed by DigiFIN, a digital finance group
Shihan Global Financial Co., Ltd., a subsidiary of the Group, is wholly owned. Lion Rock Global Financial Limited was founded in 1998 and is a licensed corporation of the Hong Kong Securities and Futures Commission
(License issued includes Class 1: Securities Trading, Class 4: Providing Opinions on Securities, and Class 9: Providing Asset Management), Central Number: AEO169</span> <br>
        </td>
        <td width="45" height="100">&nbsp;</td>
        <td width="45" height="100">&nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="45" height="50">&nbsp;</td>
        <td colspan="8"> <span style="width: 120px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size:
            18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">&nbsp;&nbsp;Contact Us&nbsp;&nbsp;</span>
        </td>
        <td width="45" height="50">&nbsp;</td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top">
            <span style="font-size: 15px; font-family: Microsoft YaHei;">Address: 25th Floor, 88 Wing Lok Street, Sheung Wan, Hong Kong</span>
        </td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top">
            <span style="font-size: 15px; font-family: Microsoft YaHei;">Customer Service Hotline：+852 3896 3896(HongKong)</span> <br>
            <span style="font-size: 15px; font-family: Microsoft YaHei;">Monday to Friday from 9am to 6pm (excluding Saturdays, Sundays, and public holidays)</span>
        </td>
    </tr>

    <tr style="background-color:#e7e2ef;">
        <td width="5" height="50">&nbsp;</td>
        <td width="5" height="50">&nbsp;</td>
        <td colspan="8" valign="top">
            <span style="font-size: 15px; font-family: Microsoft YaHei;">E-mail (Customer Service)：cs@digifingroup.com</span>
        </td>
    </tr>

    <tr height="10">
        <td colspan="10" style="background-color:#e7e2ef;"></td>
    </tr>
</table>
</body>
</html>
', NULL, 2, NULL, '000000', 1123598821738675201, NULL, NOW(), 1123598821738675201, NOW(), 1, 0);
