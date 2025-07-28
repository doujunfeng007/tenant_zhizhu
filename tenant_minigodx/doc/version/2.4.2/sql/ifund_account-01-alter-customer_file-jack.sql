#增加结单文件状态字段
alter table ifund_account.customer_file
    add status int default 0 null comment '状态 0未发送 1已发送 2发送失败';
alter table ifund_account.customer_file
    add file_name varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名';

#ifund_account库
CREATE TABLE `customer_statement_file_history` (
                                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                                   `statement_file_id` bigint DEFAULT NULL COMMENT '结单文件id',
                                                   `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
                                                   `send_time` datetime DEFAULT NULL,
                                                   `send_num` int DEFAULT NULL COMMENT '发送次数',
                                                   `create_time` datetime DEFAULT NULL,
                                                   `update_time` datetime DEFAULT NULL,
                                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='结单文件发送记录表';

#修改结单消息模板
UPDATE zero_biz.platform_common_template t SET t.content = '您的账户({0})日结单已生成，请点击我的>我的结单查看详情', t.content_hant = '您的賬戶({0})日結單已生成，請點擊我的>我的結單查看詳情', t.content_en = 'Your account ({0}) daily statement has been generated, please click My > My Statement to see the details'
                                           WHERE t.temp_code = 2023;
UPDATE zero_biz.platform_common_template t SET t.content = '您的账户({0})月结单已生成，请点击我的>我的结单查看详情', t.content_hant = '您的賬戶({0})月結單已生成，請點擊我的>我的結單查看詳情', t.content_en = 'Your account ({0}) monthly statement has been generated, please click My > My Statement to see details'
                                           WHERE t.temp_code = 2024;

INSERT INTO zero_biz.platform_common_template (id, temp_code, temp_code_hans, temp_code_hant, temp_code_en, temp_param, title, title_hant, title_en, content, content_hant, content_en, apply_explain, msg_type, url, status, is_deleted, tenant_id, create_user, create_dept, create_time, update_user, update_time, channel, create_user_name) VALUES (1897686987244888154, 3009, '3009', '3009', '3009', null, '【DigiFIN】日结单-{0}', '【DigiFIN】日結單-{0}', '【DigiFIN】Daily statement notice - {}', '<html>

<body>
<table align="center" cellpadding="0" cellspacing="0"width="900" style="margin:0 auto;border:1px solid #e7e2ef; box-shadow:0 4px 8px 0 #aaaaaa; font-size: 14px; font-family: Microsoft YaHei; ">
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<tr height="">
		<td width="45" height="67" style="background-color:#e7e2ef;">&nbsp; </td>
		<td height="67" colspan="8" style="background-color:#e7e2ef;">
			<div style="background-color:#e7e2ef; width:100%"><img alt="logo" title=""
																   src="https://www.digifingroup.com/assets/logo_and_text.d001de9e.png" height="50" width="400" /></div>
		</td>
		<td width="45" height="67" style="background-color:#e7e2ef;"> &nbsp;</td>
	</tr>
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<!-- 头部结束 -->
	<tr style="border-left: 1px solid #787878; border-right: 1px solid #787878;">
		<td width="45" height="50">&nbsp; </td>
		<td height="" colspan="8">

			<div style="padding-top:10px; font-size:14px; color:#333; line-height:26px;">
				<div style="padding:0px; margin:0px; line-height:26px;"><b>尊敬的DigiFIN 财富管理用户，您好！</b>
				</div>
				<div style="padding:0px; margin:0px; line-height:26px; margin-top:15px;">
					附件为您的{0}日结单，请查阅。<br>


					<div style="padding:0px; margin:0px;margin-top:15px; line-height:26px;">
						此致<br>
						<b><a style="text-decoration:none; color:#blue;" href="https://www.digifingroup.com/#/home">獅瀚環球金融有限公司</a></b>
					</div>
				</div>
			</div>

            <br/><br/>
            <div style="line-height:26px;margin-top:10px; padding:0px"><strong>網路安全提示：保護好您的登錄/安全憑證</strong></div>
            <div style="line-height:26px;padding:0px">
                提防騙案！獅瀚環球不會以電郵要求客戶提供個人資料，用戶名稱、密碼丶一次性密碼或其他帳戶資料；<br/>
                切勿透過任何電子郵件提供的超連結登入網上服務，或任何社交媒體平臺、電郵、簡訊或電話等方式，透露您的登錄帳號、密碼和一次性登錄密碼 (OTP) 給他人，務請小心保管個人資料；<br/>
                不要使用公共場所的裝置登錄交易系統；<br/>
                定期修改密碼、增強密碼強度，能更有效保障您的帳戶安全；<br/>
                如您懷疑自己遇上騙案，請立即致電客戶服務熱線向我們舉報，以提防損失。<br/>
            </div>

		</td>
		<td width="45" height="50" style="border-right: 1px  solid #e7e2ef;">&nbsp; </td>
	</tr>
	<!-- 中间结束 -->
	<tr height="15">
		<td colspan="10"></td>
	</tr>
	<tr height="15">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<!-- 中间部分结束 -->
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="50">&nbsp; </td>
		<td colspan="8">
                <span
						style="width: 250px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size: 18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">關於 [獅瀚環球資產管理平臺]</span>
		</td>
		<td width="45" height="50">&nbsp; </td>
	</tr>
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="100">&nbsp; </td>
		<td colspan="7" valign="top">
                <span
						style="font-size: 16px; font-family: Microsoft YaHei;">【獅瀚環球資產管理平臺】作為一站式金融資產管理平臺，在全球範圍內為投資者提供優質財富管理服務，由數字金融集團（DigiFIN Group）旗下獅瀚環球金融有限公司全資擁有。獅瀚環球金融有限公司創立於 1998 年，為香港證監會持牌法團 (獲發牌照包括第一類：證券交易、第四類：就證券提供意見及第九類：提供資產管理)，中央編號: AEO169。</span>
			<br>
			<span style="font-size: 16px; font-family: Microsoft YaHei;"></span>
		</td>
		<td width="45" height="100">&nbsp; </td>
	</tr>
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="50">&nbsp; </td>
		<td colspan="8">
                <span
						style="width: 120px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size: 18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">&nbsp;&nbsp;聯絡我們&nbsp;&nbsp;</span>
		</td>
		<td width="45" height="50">&nbsp; </td>
	</tr>
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="80">&nbsp; </td>
		<td colspan="8" valign="top">
                <span
						style="font-size: 16px; font-family: Microsoft YaHei;">地址：香港上環永樂街88號25樓</span>
		</td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="110">&nbsp; </td>
		<td colspan="8" valign="top">
                <span style="font-size: 16px; font-family: Microsoft YaHei;">客戶服務熱線：+852 3896 3896(香港)</span>
			<br>
			<span style="font-size: 16px; font-family: Microsoft YaHei;">星期一至五早上9時至下午6時(星期六、日及公眾假期除外)</span>
		</td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="50">&nbsp; </td>
		<td colspan="8" valign="top">
			<span style="font-size: 16px; font-family: Microsoft YaHei;">電郵 (客戶服務)：cs@digifingroup.com</span><br>
			<span style="font-size: 16px; font-family: Microsoft YaHei;">E-mail (Customer
                    Service)：cs@digifingroup.com</span>
		</td>
	</tr>

	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
</table>
</body>

</html>', '<html>

<body>
<table align="center" cellpadding="0" cellspacing="0"width="900" style="margin:0 auto;border:1px solid #e7e2ef; box-shadow:0 4px 8px 0 #aaaaaa; font-size: 14px; font-family: Microsoft YaHei; ">
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<tr height="">
		<td width="45" height="67" style="background-color:#e7e2ef;">&nbsp; </td>
		<td height="67" colspan="8" style="background-color:#e7e2ef;">
			<div style="background-color:#e7e2ef; width:100%"><img alt="logo" title=""
																   src="https://www.digifingroup.com/assets/logo_and_text.d001de9e.png" height="50" width="400" /></div>
		</td>
		<td width="45" height="67" style="background-color:#e7e2ef;"> &nbsp;</td>
	</tr>
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<!-- 头部结束 -->
	<tr style="border-left: 1px solid #787878; border-right: 1px solid #787878;">
		<td width="45" height="50">&nbsp; </td>
		<td height="" colspan="8">

			<div style="padding-top:10px; font-size:14px; color:#333; line-height:26px;">
				<div style="padding:0px; margin:0px; line-height:26px;"><b>尊敬的DigiFIN 财富管理用户，您好！</b>
				</div>
				<div style="padding:0px; margin:0px; line-height:26px; margin-top:15px;">
					附件為您的{0}日結單，請查閱<br>


					<div style="padding:0px; margin:0px;margin-top:15px; line-height:26px;">
						此致<br>
						<b><a style="text-decoration:none; color:#blue;" href="https://www.digifingroup.com/#/home">獅瀚環球金融有限公司</a></b>
					</div>
				</div>
			</div>

            <br/><br/>
            <div style="line-height:26px;margin-top:10px; padding:0px"><strong>網路安全提示：保護好您的登錄/安全憑證</strong></div>
            <div style="line-height:26px;padding:0px">
                提防騙案！獅瀚環球不會以電郵要求客戶提供個人資料，用戶名稱、密碼丶一次性密碼或其他帳戶資料；<br/>
                切勿透過任何電子郵件提供的超連結登入網上服務，或任何社交媒體平臺、電郵、簡訊或電話等方式，透露您的登錄帳號、密碼和一次性登錄密碼 (OTP) 給他人，務請小心保管個人資料；<br/>
                不要使用公共場所的裝置登錄交易系統；<br/>
                定期修改密碼、增強密碼強度，能更有效保障您的帳戶安全；<br/>
                如您懷疑自己遇上騙案，請立即致電客戶服務熱線向我們舉報，以提防損失。<br/>
            </div>

		</td>
		<td width="45" height="50" style="border-right: 1px  solid #e7e2ef;">&nbsp; </td>
	</tr>
	<!-- 中间结束 -->
	<tr height="15">
		<td colspan="10"></td>
	</tr>
	<tr height="15">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<!-- 中间部分结束 -->
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="50">&nbsp; </td>
		<td colspan="8">
                <span
						style="width: 250px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size: 18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">關於 [獅瀚環球資產管理平臺]</span>
		</td>
		<td width="45" height="50">&nbsp; </td>
	</tr>
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="100">&nbsp; </td>
		<td colspan="7" valign="top">
                <span
						style="font-size: 16px; font-family: Microsoft YaHei;">【獅瀚環球資產管理平臺】作為一站式金融資產管理平臺，在全球範圍內為投資者提供優質財富管理服務，由數字金融集團（DigiFIN Group）旗下獅瀚環球金融有限公司全資擁有。獅瀚環球金融有限公司創立於 1998 年，為香港證監會持牌法團 (獲發牌照包括第一類：證券交易、第四類：就證券提供意見及第九類：提供資產管理)，中央編號: AEO169。</span>
			<br>
			<span style="font-size: 16px; font-family: Microsoft YaHei;"></span>
		</td>
		<td width="45" height="100">&nbsp; </td>
	</tr>
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="50">&nbsp; </td>
		<td colspan="8">
                <span
						style="width: 120px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size: 18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">&nbsp;&nbsp;聯絡我們&nbsp;&nbsp;</span>
		</td>
		<td width="45" height="50">&nbsp; </td>
	</tr>
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="80">&nbsp; </td>
		<td colspan="8" valign="top">
                <span
						style="font-size: 16px; font-family: Microsoft YaHei;">地址：香港上環永樂街88號25樓</span>
		</td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="110">&nbsp; </td>
		<td colspan="8" valign="top">
                <span style="font-size: 16px; font-family: Microsoft YaHei;">客戶服務熱線：+852 3896 3896(香港)</span>
			<br>
			<span style="font-size: 16px; font-family: Microsoft YaHei;">星期一至五早上9時至下午6時(星期六、日及公眾假期除外)</span>
		</td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="50">&nbsp; </td>
		<td colspan="8" valign="top">
			<span style="font-size: 16px; font-family: Microsoft YaHei;">電郵 (客戶服務)：cs@digifingroup.com</span><br>
			<span style="font-size: 16px; font-family: Microsoft YaHei;">E-mail (Customer
                    Service)：cs@digifingroup.com</span>
		</td>
	</tr>

	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
</table>
</body>

</html>', '<html>

<body>
<table align="center" cellpadding="0" cellspacing="0"width="900" style="margin:0 auto;border:1px solid #e7e2ef; box-shadow:0 4px 8px 0 #aaaaaa; font-size: 14px; font-family: Microsoft YaHei; ">
  <tr height="10">
    <td colspan="10" style="background-color:#e7e2ef;"></td>
  </tr>
  <tr height="">
    <td width="45" height="67" style="background-color:#e7e2ef;">&nbsp; </td>
    <td height="67" colspan="8" style="background-color:#e7e2ef;">
      <div style="background-color:#e7e2ef; width:100%"><img alt="logo" title=""
                                   src="https://www.digifingroup.com/assets/logo_and_text.d001de9e.png" height="50" width="400" /></div>
    </td>
    <td width="45" height="67" style="background-color:#e7e2ef;"> &nbsp;</td>
  </tr>
  <tr height="10">
    <td colspan="10" style="background-color:#e7e2ef;"></td>
  </tr>
  <!-- 头部结束 -->
  <tr style="border-left: 1px solid #787878; border-right: 1px solid #787878;">
    <td width="45" height="50">&nbsp; </td>
    <td height="" colspan="8">

      <div style="padding-top:10px; font-size:14px; color:#333; line-height:26px;">
        <div style="padding:0px; margin:0px; line-height:26px;"><b>尊敬的DigiFIN 财富管理用户，您好！</b>
        </div>
        <div style="padding:0px; margin:0px; line-height:26px; margin-top:15px;">
          Attached is your {0} daily statement, please check
<br>


          <div style="padding:0px; margin:0px;margin-top:15px; line-height:26px;">
            此致<br>
            <b><a style="text-decoration:none; color:#blue;" href="https://www.digifingroup.com/#/home">獅瀚環球金融有限公司</a></b>
          </div>
        </div>
      </div>

            <br/><br/>
            <div style="line-height:26px;margin-top:10px; padding:0px"><strong>網路安全提示：保護好您的登錄/安全憑證</strong></div>
            <div style="line-height:26px;padding:0px">
                提防騙案！獅瀚環球不會以電郵要求客戶提供個人資料，用戶名稱、密碼丶一次性密碼或其他帳戶資料；<br/>
                切勿透過任何電子郵件提供的超連結登入網上服務，或任何社交媒體平臺、電郵、簡訊或電話等方式，透露您的登錄帳號、密碼和一次性登錄密碼 (OTP) 給他人，務請小心保管個人資料；<br/>
                不要使用公共場所的裝置登錄交易系統；<br/>
                定期修改密碼、增強密碼強度，能更有效保障您的帳戶安全；<br/>
                如您懷疑自己遇上騙案，請立即致電客戶服務熱線向我們舉報，以提防損失。<br/>
            </div>

    </td>
    <td width="45" height="50" style="border-right: 1px  solid #e7e2ef;">&nbsp; </td>
  </tr>
  <!-- 中间结束 -->
  <tr height="15">
    <td colspan="10"></td>
  </tr>
  <tr height="15">
    <td colspan="10" style="background-color:#e7e2ef;"></td>
  </tr>
  <!-- 中间部分结束 -->
  <tr style="background-color:#e7e2ef;">
    <td width="45" height="50">&nbsp; </td>
    <td colspan="8">
                <span
            style="width: 250px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size: 18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">關於 [獅瀚環球資產管理平臺]</span>
    </td>
    <td width="45" height="50">&nbsp; </td>
  </tr>
  <tr height="10">
    <td colspan="10" style="background-color:#e7e2ef;"></td>
  </tr>
  <tr style="background-color:#e7e2ef;">
    <td width="45" height="100">&nbsp; </td>
    <td colspan="7" valign="top">
                <span
            style="font-size: 16px; font-family: Microsoft YaHei;">【獅瀚環球資產管理平臺】作為一站式金融資產管理平臺，在全球範圍內為投資者提供優質財富管理服務，由數字金融集團（DigiFIN Group）旗下獅瀚環球金融有限公司全資擁有。獅瀚環球金融有限公司創立於 1998 年，為香港證監會持牌法團 (獲發牌照包括第一類：證券交易、第四類：就證券提供意見及第九類：提供資產管理)，中央編號: AEO169。</span>
      <br>
      <span style="font-size: 16px; font-family: Microsoft YaHei;"></span>
    </td>
    <td width="45" height="100">&nbsp; </td>
  </tr>
  <tr height="10">
    <td colspan="10" style="background-color:#e7e2ef;"></td>
  </tr>
  <tr style="background-color:#e7e2ef;">
    <td width="45" height="50">&nbsp; </td>
    <td colspan="8">
                <span
            style="width: 120px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size: 18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">&nbsp;&nbsp;聯絡我們&nbsp;&nbsp;</span>
    </td>
    <td width="45" height="50">&nbsp; </td>
  </tr>
  <tr height="10">
    <td colspan="10" style="background-color:#e7e2ef;"></td>
  </tr>
  <tr style="background-color:#e7e2ef;">
    <td width="45" height="80">&nbsp; </td>
    <td colspan="8" valign="top">
                <span
            style="font-size: 16px; font-family: Microsoft YaHei;">地址：香港上環永樂街88號25樓</span>
    </td>
  </tr>
  <tr style="background-color:#e7e2ef;">
    <td width="45" height="110">&nbsp; </td>
    <td colspan="8" valign="top">
                <span style="font-size: 16px; font-family: Microsoft YaHei;">客戶服務熱線：+852 3896 3896(香港)</span>
      <br>
      <span style="font-size: 16px; font-family: Microsoft YaHei;">星期一至五早上9時至下午6時(星期六、日及公眾假期除外)</span>
    </td>
  </tr>
  <tr style="background-color:#e7e2ef;">
    <td width="45" height="50">&nbsp; </td>
    <td colspan="8" valign="top">
      <span style="font-size: 16px; font-family: Microsoft YaHei;">電郵 (客戶服務)：cs@digifingroup.com</span><br>
      <span style="font-size: 16px; font-family: Microsoft YaHei;">E-mail (Customer
                    Service)：cs@digifingroup.com</span>
    </td>
  </tr>

  <tr height="10">
    <td colspan="10" style="background-color:#e7e2ef;"></td>
  </tr>
</table>
</body>

</html>', null, 3, null, 1, 0, '000000', null, null, '2024-08-28 19:11:37', null, '2024-08-28 13:44:28', 1, null);
INSERT INTO zero_biz.platform_common_template (id, temp_code, temp_code_hans, temp_code_hant, temp_code_en, temp_param, title, title_hant, title_en, content, content_hant, content_en, apply_explain, msg_type, url, status, is_deleted, tenant_id, create_user, create_dept, create_time, update_user, update_time, channel, create_user_name) VALUES (1897686987244888155, 3010, '3010', '3010', '3010', null, '【DigiFIN】月结单-{0}', '【DigiFIN】月結單-{0}', '【DigiFIN】Monthly statement notice - {}', '<html>

<body>
<table align="center" cellpadding="0" cellspacing="0"width="900" style="margin:0 auto;border:1px solid #e7e2ef; box-shadow:0 4px 8px 0 #aaaaaa; font-size: 14px; font-family: Microsoft YaHei; ">
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<tr height="">
		<td width="45" height="67" style="background-color:#e7e2ef;">&nbsp; </td>
		<td height="67" colspan="8" style="background-color:#e7e2ef;">
			<div style="background-color:#e7e2ef; width:100%"><img alt="logo" title=""
																   src="https://www.digifingroup.com/assets/logo_and_text.d001de9e.png" height="50" width="400" /></div>
		</td>
		<td width="45" height="67" style="background-color:#e7e2ef;"> &nbsp;</td>
	</tr>
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<!-- 头部结束 -->
	<tr style="border-left: 1px solid #787878; border-right: 1px solid #787878;">
		<td width="45" height="50">&nbsp; </td>
		<td height="" colspan="8">

			<div style="padding-top:10px; font-size:14px; color:#333; line-height:26px;">
				<div style="padding:0px; margin:0px; line-height:26px;"><b>尊敬的DigiFIN 财富管理用户，您好！</b>
				</div>
				<div style="padding:0px; margin:0px; line-height:26px; margin-top:15px;">
					附件为您的{0}月结单，请查阅。<br>


					<div style="padding:0px; margin:0px;margin-top:15px; line-height:26px;">
						此致<br>
						<b><a style="text-decoration:none; color:#blue;" href="https://www.digifingroup.com/#/home">獅瀚環球金融有限公司</a></b>
					</div>
				</div>
			</div>

            <br/><br/>
            <div style="line-height:26px;margin-top:10px; padding:0px"><strong>網路安全提示：保護好您的登錄/安全憑證</strong></div>
            <div style="line-height:26px;padding:0px">
                提防騙案！獅瀚環球不會以電郵要求客戶提供個人資料，用戶名稱、密碼丶一次性密碼或其他帳戶資料；<br/>
                切勿透過任何電子郵件提供的超連結登入網上服務，或任何社交媒體平臺、電郵、簡訊或電話等方式，透露您的登錄帳號、密碼和一次性登錄密碼 (OTP) 給他人，務請小心保管個人資料；<br/>
                不要使用公共場所的裝置登錄交易系統；<br/>
                定期修改密碼、增強密碼強度，能更有效保障您的帳戶安全；<br/>
                如您懷疑自己遇上騙案，請立即致電客戶服務熱線向我們舉報，以提防損失。<br/>
            </div>

		</td>
		<td width="45" height="50" style="border-right: 1px  solid #e7e2ef;">&nbsp; </td>
	</tr>
	<!-- 中间结束 -->
	<tr height="15">
		<td colspan="10"></td>
	</tr>
	<tr height="15">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<!-- 中间部分结束 -->
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="50">&nbsp; </td>
		<td colspan="8">
                <span
						style="width: 250px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size: 18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">關於 [獅瀚環球資產管理平臺]</span>
		</td>
		<td width="45" height="50">&nbsp; </td>
	</tr>
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="100">&nbsp; </td>
		<td colspan="7" valign="top">
                <span
						style="font-size: 16px; font-family: Microsoft YaHei;">【獅瀚環球資產管理平臺】作為一站式金融資產管理平臺，在全球範圍內為投資者提供優質財富管理服務，由數字金融集團（DigiFIN Group）旗下獅瀚環球金融有限公司全資擁有。獅瀚環球金融有限公司創立於 1998 年，為香港證監會持牌法團 (獲發牌照包括第一類：證券交易、第四類：就證券提供意見及第九類：提供資產管理)，中央編號: AEO169。</span>
			<br>
			<span style="font-size: 16px; font-family: Microsoft YaHei;"></span>
		</td>
		<td width="45" height="100">&nbsp; </td>
	</tr>
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="50">&nbsp; </td>
		<td colspan="8">
                <span
						style="width: 120px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size: 18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">&nbsp;&nbsp;聯絡我們&nbsp;&nbsp;</span>
		</td>
		<td width="45" height="50">&nbsp; </td>
	</tr>
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="80">&nbsp; </td>
		<td colspan="8" valign="top">
                <span
						style="font-size: 16px; font-family: Microsoft YaHei;">地址：香港上環永樂街88號25樓</span>
		</td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="110">&nbsp; </td>
		<td colspan="8" valign="top">
                <span style="font-size: 16px; font-family: Microsoft YaHei;">客戶服務熱線：+852 3896 3896(香港)</span>
			<br>
			<span style="font-size: 16px; font-family: Microsoft YaHei;">星期一至五早上9時至下午6時(星期六、日及公眾假期除外)</span>
		</td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="50">&nbsp; </td>
		<td colspan="8" valign="top">
			<span style="font-size: 16px; font-family: Microsoft YaHei;">電郵 (客戶服務)：cs@digifingroup.com</span><br>
			<span style="font-size: 16px; font-family: Microsoft YaHei;">E-mail (Customer
                    Service)：cs@digifingroup.com</span>
		</td>
	</tr>

	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
</table>
</body>

</html>', '<html>

<body>
<table align="center" cellpadding="0" cellspacing="0"width="900" style="margin:0 auto;border:1px solid #e7e2ef; box-shadow:0 4px 8px 0 #aaaaaa; font-size: 14px; font-family: Microsoft YaHei; ">
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<tr height="">
		<td width="45" height="67" style="background-color:#e7e2ef;">&nbsp; </td>
		<td height="67" colspan="8" style="background-color:#e7e2ef;">
			<div style="background-color:#e7e2ef; width:100%"><img alt="logo" title=""
																   src="https://www.digifingroup.com/assets/logo_and_text.d001de9e.png" height="50" width="400" /></div>
		</td>
		<td width="45" height="67" style="background-color:#e7e2ef;"> &nbsp;</td>
	</tr>
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<!-- 头部结束 -->
	<tr style="border-left: 1px solid #787878; border-right: 1px solid #787878;">
		<td width="45" height="50">&nbsp; </td>
		<td height="" colspan="8">

			<div style="padding-top:10px; font-size:14px; color:#333; line-height:26px;">
				<div style="padding:0px; margin:0px; line-height:26px;"><b>尊敬的DigiFIN 财富管理用户，您好！</b>
				</div>
				<div style="padding:0px; margin:0px; line-height:26px; margin-top:15px;">
					附件為您的{0}月結單，請查閱<br>


					<div style="padding:0px; margin:0px;margin-top:15px; line-height:26px;">
						此致<br>
						<b><a style="text-decoration:none; color:#blue;" href="https://www.digifingroup.com/#/home">獅瀚環球金融有限公司</a></b>
					</div>
				</div>
			</div>

            <br/><br/>
            <div style="line-height:26px;margin-top:10px; padding:0px"><strong>網路安全提示：保護好您的登錄/安全憑證</strong></div>
            <div style="line-height:26px;padding:0px">
                提防騙案！獅瀚環球不會以電郵要求客戶提供個人資料，用戶名稱、密碼丶一次性密碼或其他帳戶資料；<br/>
                切勿透過任何電子郵件提供的超連結登入網上服務，或任何社交媒體平臺、電郵、簡訊或電話等方式，透露您的登錄帳號、密碼和一次性登錄密碼 (OTP) 給他人，務請小心保管個人資料；<br/>
                不要使用公共場所的裝置登錄交易系統；<br/>
                定期修改密碼、增強密碼強度，能更有效保障您的帳戶安全；<br/>
                如您懷疑自己遇上騙案，請立即致電客戶服務熱線向我們舉報，以提防損失。<br/>
            </div>

		</td>
		<td width="45" height="50" style="border-right: 1px  solid #e7e2ef;">&nbsp; </td>
	</tr>
	<!-- 中间结束 -->
	<tr height="15">
		<td colspan="10"></td>
	</tr>
	<tr height="15">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<!-- 中间部分结束 -->
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="50">&nbsp; </td>
		<td colspan="8">
                <span
						style="width: 250px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size: 18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">關於 [獅瀚環球資產管理平臺]</span>
		</td>
		<td width="45" height="50">&nbsp; </td>
	</tr>
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="100">&nbsp; </td>
		<td colspan="7" valign="top">
                <span
						style="font-size: 16px; font-family: Microsoft YaHei;">【獅瀚環球資產管理平臺】作為一站式金融資產管理平臺，在全球範圍內為投資者提供優質財富管理服務，由數字金融集團（DigiFIN Group）旗下獅瀚環球金融有限公司全資擁有。獅瀚環球金融有限公司創立於 1998 年，為香港證監會持牌法團 (獲發牌照包括第一類：證券交易、第四類：就證券提供意見及第九類：提供資產管理)，中央編號: AEO169。</span>
			<br>
			<span style="font-size: 16px; font-family: Microsoft YaHei;"></span>
		</td>
		<td width="45" height="100">&nbsp; </td>
	</tr>
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="50">&nbsp; </td>
		<td colspan="8">
                <span
						style="width: 120px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size: 18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">&nbsp;&nbsp;聯絡我們&nbsp;&nbsp;</span>
		</td>
		<td width="45" height="50">&nbsp; </td>
	</tr>
	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="80">&nbsp; </td>
		<td colspan="8" valign="top">
                <span
						style="font-size: 16px; font-family: Microsoft YaHei;">地址：香港上環永樂街88號25樓</span>
		</td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="110">&nbsp; </td>
		<td colspan="8" valign="top">
                <span style="font-size: 16px; font-family: Microsoft YaHei;">客戶服務熱線：+852 3896 3896(香港)</span>
			<br>
			<span style="font-size: 16px; font-family: Microsoft YaHei;">星期一至五早上9時至下午6時(星期六、日及公眾假期除外)</span>
		</td>
	</tr>
	<tr style="background-color:#e7e2ef;">
		<td width="45" height="50">&nbsp; </td>
		<td colspan="8" valign="top">
			<span style="font-size: 16px; font-family: Microsoft YaHei;">電郵 (客戶服務)：cs@digifingroup.com</span><br>
			<span style="font-size: 16px; font-family: Microsoft YaHei;">E-mail (Customer
                    Service)：cs@digifingroup.com</span>
		</td>
	</tr>

	<tr height="10">
		<td colspan="10" style="background-color:#e7e2ef;"></td>
	</tr>
</table>
</body>

</html>', '<html>

<body>
<table align="center" cellpadding="0" cellspacing="0"width="900" style="margin:0 auto;border:1px solid #e7e2ef; box-shadow:0 4px 8px 0 #aaaaaa; font-size: 14px; font-family: Microsoft YaHei; ">
  <tr height="10">
    <td colspan="10" style="background-color:#e7e2ef;"></td>
  </tr>
  <tr height="">
    <td width="45" height="67" style="background-color:#e7e2ef;">&nbsp; </td>
    <td height="67" colspan="8" style="background-color:#e7e2ef;">
      <div style="background-color:#e7e2ef; width:100%"><img alt="logo" title=""
                                   src="https://www.digifingroup.com/assets/logo_and_text.d001de9e.png" height="50" width="400" /></div>
    </td>
    <td width="45" height="67" style="background-color:#e7e2ef;"> &nbsp;</td>
  </tr>
  <tr height="10">
    <td colspan="10" style="background-color:#e7e2ef;"></td>
  </tr>
  <!-- 头部结束 -->
  <tr style="border-left: 1px solid #787878; border-right: 1px solid #787878;">
    <td width="45" height="50">&nbsp; </td>
    <td height="" colspan="8">

      <div style="padding-top:10px; font-size:14px; color:#333; line-height:26px;">
        <div style="padding:0px; margin:0px; line-height:26px;"><b>尊敬的DigiFIN 财富管理用户，您好！</b>
        </div>
        <div style="padding:0px; margin:0px; line-height:26px; margin-top:15px;">
          Attached is your {0} Monthly statement, please check
<br>


          <div style="padding:0px; margin:0px;margin-top:15px; line-height:26px;">
            此致<br>
            <b><a style="text-decoration:none; color:#blue;" href="https://www.digifingroup.com/#/home">獅瀚環球金融有限公司</a></b>
          </div>
        </div>
      </div>

            <br/><br/>
            <div style="line-height:26px;margin-top:10px; padding:0px"><strong>網路安全提示：保護好您的登錄/安全憑證</strong></div>
            <div style="line-height:26px;padding:0px">
                提防騙案！獅瀚環球不會以電郵要求客戶提供個人資料，用戶名稱、密碼丶一次性密碼或其他帳戶資料；<br/>
                切勿透過任何電子郵件提供的超連結登入網上服務，或任何社交媒體平臺、電郵、簡訊或電話等方式，透露您的登錄帳號、密碼和一次性登錄密碼 (OTP) 給他人，務請小心保管個人資料；<br/>
                不要使用公共場所的裝置登錄交易系統；<br/>
                定期修改密碼、增強密碼強度，能更有效保障您的帳戶安全；<br/>
                如您懷疑自己遇上騙案，請立即致電客戶服務熱線向我們舉報，以提防損失。<br/>
            </div>

    </td>
    <td width="45" height="50" style="border-right: 1px  solid #e7e2ef;">&nbsp; </td>
  </tr>
  <!-- 中间结束 -->
  <tr height="15">
    <td colspan="10"></td>
  </tr>
  <tr height="15">
    <td colspan="10" style="background-color:#e7e2ef;"></td>
  </tr>
  <!-- 中间部分结束 -->
  <tr style="background-color:#e7e2ef;">
    <td width="45" height="50">&nbsp; </td>
    <td colspan="8">
                <span
            style="width: 250px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size: 18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">關於 [獅瀚環球資產管理平臺]</span>
    </td>
    <td width="45" height="50">&nbsp; </td>
  </tr>
  <tr height="10">
    <td colspan="10" style="background-color:#e7e2ef;"></td>
  </tr>
  <tr style="background-color:#e7e2ef;">
    <td width="45" height="100">&nbsp; </td>
    <td colspan="7" valign="top">
                <span
            style="font-size: 16px; font-family: Microsoft YaHei;">【獅瀚環球資產管理平臺】作為一站式金融資產管理平臺，在全球範圍內為投資者提供優質財富管理服務，由數字金融集團（DigiFIN Group）旗下獅瀚環球金融有限公司全資擁有。獅瀚環球金融有限公司創立於 1998 年，為香港證監會持牌法團 (獲發牌照包括第一類：證券交易、第四類：就證券提供意見及第九類：提供資產管理)，中央編號: AEO169。</span>
      <br>
      <span style="font-size: 16px; font-family: Microsoft YaHei;"></span>
    </td>
    <td width="45" height="100">&nbsp; </td>
  </tr>
  <tr height="10">
    <td colspan="10" style="background-color:#e7e2ef;"></td>
  </tr>
  <tr style="background-color:#e7e2ef;">
    <td width="45" height="50">&nbsp; </td>
    <td colspan="8">
                <span
            style="width: 120px;height: 35px;background: #e7e2ef;border-radius: 20px;font-size: 18px;text-align: center;display: inline-block;line-height: 35px;border: 1px solid #787878;">&nbsp;&nbsp;聯絡我們&nbsp;&nbsp;</span>
    </td>
    <td width="45" height="50">&nbsp; </td>
  </tr>
  <tr height="10">
    <td colspan="10" style="background-color:#e7e2ef;"></td>
  </tr>
  <tr style="background-color:#e7e2ef;">
    <td width="45" height="80">&nbsp; </td>
    <td colspan="8" valign="top">
                <span
            style="font-size: 16px; font-family: Microsoft YaHei;">地址：香港上環永樂街88號25樓</span>
    </td>
  </tr>
  <tr style="background-color:#e7e2ef;">
    <td width="45" height="110">&nbsp; </td>
    <td colspan="8" valign="top">
                <span style="font-size: 16px; font-family: Microsoft YaHei;">客戶服務熱線：+852 3896 3896(香港)</span>
      <br>
      <span style="font-size: 16px; font-family: Microsoft YaHei;">星期一至五早上9時至下午6時(星期六、日及公眾假期除外)</span>
    </td>
  </tr>
  <tr style="background-color:#e7e2ef;">
    <td width="45" height="50">&nbsp; </td>
    <td colspan="8" valign="top">
      <span style="font-size: 16px; font-family: Microsoft YaHei;">電郵 (客戶服務)：cs@digifingroup.com</span><br>
      <span style="font-size: 16px; font-family: Microsoft YaHei;">E-mail (Customer
                    Service)：cs@digifingroup.com</span>
    </td>
  </tr>

  <tr height="10">
    <td colspan="10" style="background-color:#e7e2ef;"></td>
  </tr>
</table>
</body>

</html>', null, 3, null, 1, 0, '000000', null, null, '2024-08-28 19:11:37', null, '2024-08-28 13:44:28', 1, null);



