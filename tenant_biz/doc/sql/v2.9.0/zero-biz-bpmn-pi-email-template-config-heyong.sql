
SELECT *FROM `zero_biz`.`platform_common_template` WHERE id='1669534547039200001';
DELETE FROM `zero_biz`.`platform_common_template` WHERE id='1669534547039200001';



-- 《关于被视为专业投资者的通知》邮件模版
INSERT INTO `zero_biz`.`platform_common_template` (`id`, `temp_code`, `temp_code_hans`, `temp_code_hant`, `temp_code_en`, `temp_param`, `title`, `title_hant`, `title_en`, `content`, `content_hant`, `content_en`, `apply_explain`, `msg_type`, `url`, `tenant_id`, `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (1669534547039200001, 3012, 3012, 3012, 3012, NULL, '狮瀚环球金融 - 关于被视为专业投资者的申请 - ({0})', '獅瀚環球金融 - 關於被視為專業投資者的申請 - ({0})', ' Lion Global - Regarding the Application to be Recognized as a Professional Investor – ({0})', '<!DOCTYPE html>
<html lang="zh-Hans" style="font-size: 1.6vw;">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>狮瀚环球金融-关于被视为专业投资者的申请</title>
</head>

<body style="margin: 0; font-family: SourceHanSansSC-Regular; background-color: #f5f5f5; font-size: 1.6vw;">
<table style="width: 100%; max-width: 1440px; margin: 0 auto; background-color: #fff; border-collapse: collapse;">
    <!-- Header -->
    <tr>
        <td
            style="background-image: url(''https://minio.ifund.live:9000/minigod-prd/upload/20241031/697b20c15852018882542151b8519d0d.png''); background-size: cover; height: 8vw;">
            <table style="width: 100%; height: 100%;">
                <tr>
                    <td style="padding-left: 4vw; padding-top: 2vw;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/be19cfa72395dfd24dcfc3133884ccad.png"
                            alt="DigiFIN Logo" style="width: 20vw;">
                    </td>
                </tr>
            </table>
        </td>
    </tr>

    <!-- Main Content -->
    <tr>
        <td style="padding: 2vw; text-align: center;">
            <table style="width: 100%; background-color: #e9f0f8; padding: 3vw 0; margin: 0 auto;">
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        尊敬的{0}，您好！
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        感谢您选择狮瀚环球。我们已通过阁下申请成为专业投资者的审批, 附件为关于被视为专业投资者的通知, 请细阅, 谢谢。
                    </td>
                </tr>
                 <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        根据《证券及期货（专业投资者）规则》（“专业投资者规则”）及证券及期货事务监察委员会所印制的“证券及期货事务监察委员会持牌人或注册人操守准则”（“操守准则”）第15段的定义，阁下已被归类为一名“专业投资者”。
                    </td>
                </tr>
                 <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        阁下应该知道被归类为“专业投资者”的风险及后果。不论就所有或任何部分产品或市场而言，阁下可享有撤回被归类为专业投资者的权利。在任何时候，若阁下不希望被归类为“专业投资者”，敬请阁下在到期日或之前，以书面方式通知本公司。
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        如阁下有任何查询，敬请致电本公司的客户服务热线 (852) 3896 3896。
                    </td>
                </tr>
                  <tr>
                    <td
                        style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block; float: left">
                        此致
                    </td>
                </tr>
                <tr>
                    <td
                        style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block; float: left">
                        <a style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block;"
                           href="https://www.digifingroup.com/#/home">狮瀚环球金融有限公司</a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

    <!-- Security Info -->
    <tr>
        <td style="padding: 2vw;">
            <table style="width: 100%;">
                <tr>
                    <td style="text-align: left;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/d95d1bf57ea0810ae74eab94fecbe9f6.png"
                            alt="" style="width: 1.6vw; vertical-align: middle;">
                        <span
                            style="font-size: 1.2rem; padding: 0 1vw; background: linear-gradient(to bottom, transparent 50%, #FFF6E5 50%); display: inline-block;">
                                网络安全提示：保护好您的登录/安全凭证
                            </span>
                    </td>
                </tr>
                <tr>
                    <td style="padding-left: 3.6vw; color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <p style="margin: 0;">
                            1.提防诈骗！狮瀚环球不会以电邮要求客户提供个人资料，用户名、密码、一次性密码或其他账户资料；</p>
                        <p style="margin: 0;">2.切勿通过任何电子邮件提供的超链接登录网上服务，或任何社交媒体平台、电邮、短信或电话等方式，透露您的登录账号、密码和一次性登录密码
                            (OTP) 给他人，请务必小心保管个人资料；</p>
                        <p style="margin: 0;">3.不要使用公共场所的设备登录交易系统；</p>
                        <p style="margin: 0;">4.定期修改密码，确保密码强度，能够有效保障您的账号安全；</p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

    <!-- Footer -->
    <tr>
        <td
            style="background-image: url(''https://minio.ifund.live:9000/minigod-prd/upload/20241031/1df6f6c328c06f0d07ad041a3b5c37a0.png''); background-size: 100% 100%; padding: 3vw 4vw;">
            <table style="width: 100%;">
                <tr>
                    <td style="font-size: 1.2rem; position: relative; margin-bottom: 2vw;">
                        关于 [狮瀚环球资产管理平台]

                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-bottom: 3vw;">
                        【狮瀚环球资产管理平台】作为一站式金融资产管理平台，在全球范围内为投资者提供优质财富管理服务，由数字金融集团（DigiFIN
                        Group）旗下狮瀚环球金融有限公司全资拥有。狮瀚环球金融有限公司创立于1998年，为香港证券监会持牌法人（获发牌照包括第一类：证券交易、第四类：就证券提供意见及第九类：提供资产管理），中央编号:
                        AEO169。
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 1.2rem; position: relative; margin-top: 5vw; margin-bottom: 1vw;">
                        联系我们

                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-bottom: 2vw;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/07ae5f7fc157497eb0f5746e3b7ae3b3.png"
                            alt=""
                            style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">地址：香港上环永乐街88号25楼
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/1cba1443d01a80d923c756ffff8ebb00.png"
                            alt=""
                            style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">客户服务热线：+852
                        3896 3896(香港)
                    </td>
                </tr>
                <tr>
                    <td
                        style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-left: 3vw;padding-bottom: 2vw;">
                        星期一至五 9:00时至下午6:00时(星期六、日及公众假期除外)
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/dff8c3f663dc4f9f9ff824791934ee07.png"
                            alt=""
                            style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">电子邮件(客户服务)：cs@lionfin.com
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-left: 3vw;">
                        E-mail (Customer Service)：cs@lionfin.com
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>

</html>
','<!DOCTYPE html>
<html lang="zh-Hant" style="font-size: 1.6vw;">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>獅瀚環球金融-關於被視為專業投資者的申請</title>
</head>

<body style="margin: 0; font-family: SourceHanSansSC-Regular; background-color: #f5f5f5; font-size: 1.6vw;">
<table style="width: 100%; max-width: 1440px; margin: 0 auto; background-color: #fff; border-collapse: collapse;">
    <!-- Header -->
    <tr>
        <td
            style="background-image: url(''https://minio.ifund.live:9000/minigod-prd/upload/20241031/697b20c15852018882542151b8519d0d.png''); background-size: cover; height: 8vw;">
            <table style="width: 100%; height: 100%;">
                <tr>
                    <td style="padding-left: 4vw; padding-top: 2vw;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/be19cfa72395dfd24dcfc3133884ccad.png"
                            alt="DigiFIN Logo" style="width: 20vw;">
                    </td>
                </tr>
            </table>
        </td>
    </tr>

    <!-- Main Content -->
    <tr>
        <td style="padding: 2vw; text-align: center;">
            <table style="width: 100%; background-color: #e9f0f8; padding: 3vw 0; margin: 0 auto;">
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        尊敬的{0}，您好！
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        感謝您選擇獅瀚環球。我們已通過閣下申請成為專業投資者的審批, 附件為關於被視為專業投資者的通知, 請細閱, 謝謝。
                    </td>
                </tr>
                  <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        根據《證券及期貨（專業投資者）規則》（「專業投資者規則」）及證券及期貨事務監察委員會所印製的「證券及期貨事務監察委員會持牌人或註冊人操守準則」（「操守準則」）第15段的定義，閣下已被歸類為一名「專業投資者」。
                    </td>
                </tr>
                  <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        閣下應該知道被歸類為「專業投資者」的風險及後果。不論就所有或任何部分產品或市場而言，閣下可享有撤回被歸類為專業投資者的權利。在任何時候，若閣下不希望被歸類為「專業投資者」，敬請閣下在到期日或之前，以書面方式通知本公司。
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        如閣下有任何查詢，敬請致電本公司的客戶服務熱線 (852) 3896 3896。
                    </td>
                </tr>
                <tr>
                    <td
                        style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block; float: left">
                        此致
                    </td>
                </tr>
                <tr>
                    <td
                        style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block; float: left">
                        <a style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block;"
                           href="https://www.digifingroup.com/#/home">獅瀚環球金融有限公司</a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

    <!-- Security Info -->
    <tr>
        <td style="padding: 2vw;">
            <table style="width: 100%;">
                <tr>
                    <td style="text-align: left;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/d95d1bf57ea0810ae74eab94fecbe9f6.png"
                            alt="" style="width: 1.6vw; vertical-align: middle;">
                        <span
                            style="font-size: 1.2rem; padding: 0 1vw; background: linear-gradient(to bottom, transparent 50%, #FFF6E5 50%); display: inline-block;">
                                網絡安全提示：保護好您的登錄/安全憑證
                            </span>
                    </td>
                </tr>
                <tr>
                    <td style="padding-left: 3.6vw; color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <p style="margin: 0;">
                            1.提防詐騙！獅瀚環球不會以電郵要求客戶提供個人資料，使用者名稱、密碼、一次性密碼或其他賬戶資料；</p>
                        <p style="margin: 0;">2.切勿通過任何電子郵件提供的超鏈接登錄網上服務，或任何社交媒體平台、電郵、短信或電話等方式，透露您的登錄賬號、密碼和一次性登錄密碼
                            (OTP) 給他人，請務必小心保管個人資料；</p>
                        <p style="margin: 0;">3.不要使用公共場所的設備登錄交易系統；</p>
                        <p style="margin: 0;">4.定期修改密碼，確保密碼強度，能夠有效保障您的賬號安全；</p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

    <!-- Footer -->
    <tr>
        <td
            style="background-image: url(''https://minio.ifund.live:9000/minigod-prd/upload/20241031/1df6f6c328c06f0d07ad041a3b5c37a0.png''); background-size: 100% 100%; padding: 3vw 4vw;">
            <table style="width: 100%;">
                <tr>
                    <td style="font-size: 1.2rem; position: relative; margin-bottom: 2vw;">
                        關於 [獅瀚環球資產管理平台]
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-bottom: 3vw;">
                        【獅瀚環球資產管理平台】作為一站式金融資產管理平台，在全球範圍內為投資者提供優質財富管理服務，由數字金融集團（DigiFIN
                        Group）旗下獅瀚環球金融有限公司全資擁有。獅瀚環球金融有限公司創立於1998年，為香港證券監會持牌法人（獲發牌照包括第一類：證券交易、第四類：就證券提供意見及第九類：提供資產管理），中央編號:
                        AEO169。
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 1.2rem; position: relative; margin-top: 5vw; margin-bottom: 1vw;">
                        聯繫我們
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-bottom: 2vw;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/07ae5f7fc157497eb0f5746e3b7ae3b3.png"
                            alt=""
                            style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">地址：香港上環永樂街88號25樓
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/1cba1443d01a80d923c756ffff8ebb00.png"
                            alt=""
                            style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">客戶服務熱線：+852
                        3896 3896(香港)
                    </td>
                </tr>
                <tr>
                    <td
                        style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-left: 3vw;padding-bottom: 2vw;">
                        星期一至五 9:00時至下午6:00時(星期六、日及公眾假期除外)
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/dff8c3f663dc4f9f9ff824791934ee07.png"
                            alt=""
                            style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">電子郵件(客戶服務)：cs@lionfin.com
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-left: 3vw;">
                        E-mail (Customer Service)：cs@lionfin.com
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>

</html>
','<!DOCTYPE html>
<html lang="en" style="font-size: 1.6vw;">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lion Global - Regarding the Application to be Recognized as a Professional Investor</title>
</head>

<body style="margin: 0; font-family: SourceHanSansSC-Regular; background-color: #f5f5f5; font-size: 1.6vw;">
<table style="width: 100%; max-width: 1440px; margin: 0 auto; background-color: #fff; border-collapse: collapse;">
    <!-- Header -->
    <tr>
        <td
            style="background-image: url(''https://minio.ifund.live:9000/minigod-prd/upload/20241031/697b20c15852018882542151b8519d0d.png''); background-size: cover; height: 8vw;">
            <table style="width: 100%; height: 100%;">
                <tr>
                    <td style="padding-left: 4vw; padding-top: 2vw;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/be19cfa72395dfd24dcfc3133884ccad.png"
                            alt="DigiFIN Logo" style="width: 20vw;">
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <!-- Main Content -->
    <tr>
        <td style="padding: 2vw; text-align: center;">
            <table style="width: 100%; background-color: #e9f0f8; padding: 3vw 0; margin: 0 auto;">
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        Dear {0},
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        Thank you for choosing Lion Global. We are pleased to inform you that your application to
                        become a professional investor has been approved. Attached is the Notice of Treatment as
                        Professional Investor. Please read it carefully. Thank you.
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        According to the “Securities and Futures (Professional Investor) Rules” (“Professional
                        Investor Rules”) and paragraph 15 of the “Code of Conduct for Persons Licensed by or
                        Registered with the Securities and Futures Commission” (“Code of Conduct”) issued by the
                        Securities and Futures Commission, you have been classified as a “professional
                        investor.”
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        You should be aware of the risks and consequences of being classified as a “professional
                        investor.” You have the right to withdraw your classification as a professional investor for
                        all or any part of the products or markets. At any time, if you do not wish to be classified
                        as a “professional investor,” please notify us in writing on or before the due date.
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        If you have any inquiries, please contact our customer service team at (852) 3896 3896.
                    </td>
                </tr>
                <tr>
                    <td
                        style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block; float: left">
                        Yours sincerely,
                    </td>
                </tr>
                <tr>
                    <td
                        style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block; float: left">
                        <a style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block;"
                           href="digifin.exchange:31077">Lion Global Financial Limited</a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <!-- Security Info -->
    <tr>
        <td style="padding: 2vw;">
            <table style="width: 100%;">
                <tr>
                    <td style="text-align: left;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/d95d1bf57ea0810ae74eab94fecbe9f6.png"
                            alt="" style="width: 1.6vw; vertical-align: middle;">
                        <span
                            style="font-size: 1.2rem; padding: 0 1vw; background: linear-gradient(to bottom, transparent 50%, #FFF6E5 50%); display: inline-block;">
                                Cybersecurity Reminder: Protect your login/security credentials
                            </span>
                    </td>
                </tr>
                <tr>
                    <td style="padding-left: 3.6vw; color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <p style="margin: 0;">1. Beware of scams! Lion Financial Group will not ask customers to provide
                            personal information, usernames, passwords, one-time passwords, or other account information
                            via email;</p>
                        <p style="margin: 0;">2. Do not log in to online services through hyperlinks provided in any
                            email, or disclose your login account, password, and one-time login password (OTP) to others
                            through any social media platform, email, SMS, or phone. Please be sure to keep your
                            personal information safe;</p>
                        <p style="margin: 0;">3. Do not use public devices to log in to the trading system;</p>
                        <p style="margin: 0;">4. Regularly change your password to ensure password strength and
                            effectively protect your account security;</p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

    <!-- Footer -->
    <tr>
        <td
            style="background-image: url(''https://minio.ifund.live:9000/minigod-prd/upload/20241031/1df6f6c328c06f0d07ad041a3b5c37a0.png''); background-size: 100% 100%; padding: 3vw 4vw;">
            <table style="width: 100%;">
                <tr>
                    <td style="font-size: 1.2rem; position: relative; margin-bottom: 2vw;">
                        About [Lion Financial Asset Management Platform]
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-bottom: 3vw;">
                        The [Lion Financial Asset Management Platform] is a one-stop financial asset management platform
                        that provides high-quality wealth management services to investors worldwide, fully owned by
                        Lion Financial Group Limited under DigiFIN Group. Lion Financial Group Limited was established
                        in 1998 and is a licensed entity of the Hong Kong Securities and Futures Commission (licenses
                        include Type 1: Securities Trading, Type 4: Advising on Securities, and Type 9: Asset
                        Management), Central Number: AEO169.
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 1.2rem; position: relative; margin-top: 5vw; margin-bottom: 1vw;">
                        Contact Us
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-bottom: 2vw;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/07ae5f7fc157497eb0f5746e3b7ae3b3.png"
                            alt=""
                            style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">Address: 25th
                        Floor, 88 Wing Lok Street, Sheung Wan, Hong Kong
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/1cba1443d01a80d923c756ffff8ebb00.png"
                            alt=""
                            style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">Customer Service
                        Hotline: +852 3896 3896 (Hong Kong)
                    </td>
                </tr>
                <tr>
                    <td
                        style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-left: 3vw;padding-bottom: 2vw;">
                        Monday to Friday 9:00 AM to 6:00 PM (excluding Saturdays, Sundays, and public holidays)
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/dff8c3f663dc4f9f9ff824791934ee07.png"
                            alt=""
                            style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">Email (Customer
                        Service): cs@lionfin.com
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
', NULL, 2, NULL, '000000', 1123598821738675201, NULL, NOW(), 1123598821738675201, NOW(), 1, 0);







SELECT *FROM `zero_biz`.`platform_common_template` WHERE id='1669534547039200002';
DELETE FROM `zero_biz`.`platform_common_template` WHERE id='1669534547039200002';

-- 《关于被视为专业投资者的通知》邮件模版
INSERT INTO `zero_biz`.`platform_common_template` (`id`, `temp_code`, `temp_code_hans`, `temp_code_hant`, `temp_code_en`, `temp_param`, `title`, `title_hant`, `title_en`, `content`, `content_hant`, `content_en`, `apply_explain`, `msg_type`, `url`, `tenant_id`, `create_user`, `create_dept`, `create_time`, `update_user`, `update_time`, `status`, `is_deleted`) VALUES (1669534547039200002, 3013, 3013, 3013, 3013, NULL, '狮瀚环球金融 - 关于被视为专业投资者的申请 - ({0})', '獅瀚環球金融 - 關於被視為專業投資者的申請 - ({0})', 'Lion Global - Regarding the Application to be Recognized as a Professional Investor – ({0})', '<!DOCTYPE html>
<html lang="zh-Hans" style="font-size: 1.6vw;">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>狮瀚环球金融-关于被视为专业投资者的申请</title>
</head>

<body style="margin: 0; font-family: SourceHanSansSC-Regular; background-color: #f5f5f5; font-size: 1.6vw;">
<table style="width: 100%; max-width: 1440px; margin: 0 auto; background-color: #fff; border-collapse: collapse;">
    <!-- Header -->
    <tr>
        <td
            style="background-image: url(''https://minio.ifund.live:9000/minigod-prd/upload/20241031/697b20c15852018882542151b8519d0d.png''); background-size: cover; height: 8vw;">
            <table style="width: 100%; height: 100%;">
                <tr>
                    <td style="padding-left: 4vw; padding-top: 2vw;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/be19cfa72395dfd24dcfc3133884ccad.png"
                            alt="DigiFIN Logo" style="width: 20vw;">
                    </td>
                </tr>
            </table>
        </td>
    </tr>

    <!-- Main Content -->
    <tr>
        <td style="padding: 2vw; text-align: center;">
            <table style="width: 100%; background-color: #e9f0f8; padding: 3vw 0; margin: 0 auto;">
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        尊敬的{0}，您好！
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        感谢您选择狮瀚环球。我们未能通过阁下申请成为专业投资者的审批，请登入DigiFIN APP查看原因并再次提交。谢谢。
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        如阁下有任何查询，敬请致电本公司的客户服务热线 (852) 3896 3896。
                    </td>
                </tr>
                  <tr>
                    <td
                        style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block; float: left">
                        此致
                    </td>
                </tr>
                <tr>
                    <td
                        style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block; float: left">
                        <a style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block;"
                           href="https://www.digifingroup.com/#/home">狮瀚环球金融有限公司</a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

    <!-- Security Info -->
    <tr>
        <td style="padding: 2vw;">
            <table style="width: 100%;">
                <tr>
                    <td style="text-align: left;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/d95d1bf57ea0810ae74eab94fecbe9f6.png"
                            alt="" style="width: 1.6vw; vertical-align: middle;">
                        <span
                            style="font-size: 1.2rem; padding: 0 1vw; background: linear-gradient(to bottom, transparent 50%, #FFF6E5 50%); display: inline-block;">
                                网络安全提示：保护好您的登录/安全凭证
                            </span>
                    </td>
                </tr>
                <tr>
                    <td style="padding-left: 3.6vw; color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <p style="margin: 0;">
                            1.提防诈骗！狮瀚环球不会以电邮要求客户提供个人资料，用户名、密码、一次性密码或其他账户资料；</p>
                        <p style="margin: 0;">2.切勿通过任何电子邮件提供的超链接登录网上服务，或任何社交媒体平台、电邮、短信或电话等方式，透露您的登录账号、密码和一次性登录密码
                            (OTP) 给他人，请务必小心保管个人资料；</p>
                        <p style="margin: 0;">3.不要使用公共场所的设备登录交易系统；</p>
                        <p style="margin: 0;">4.定期修改密码，确保密码强度，能够有效保障您的账号安全；</p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

    <!-- Footer -->
    <tr>
        <td
            style="background-image: url(''https://minio.ifund.live:9000/minigod-prd/upload/20241031/1df6f6c328c06f0d07ad041a3b5c37a0.png''); background-size: 100% 100%; padding: 3vw 4vw;">
            <table style="width: 100%;">
                <tr>
                    <td style="font-size: 1.2rem; position: relative; margin-bottom: 2vw;">
                        关于 [狮瀚环球资产管理平台]

                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-bottom: 3vw;">
                        【狮瀚环球资产管理平台】作为一站式金融资产管理平台，在全球范围内为投资者提供优质财富管理服务，由数字金融集团（DigiFIN
                        Group）旗下狮瀚环球金融有限公司全资拥有。狮瀚环球金融有限公司创立于1998年，为香港证券监会持牌法人（获发牌照包括第一类：证券交易、第四类：就证券提供意见及第九类：提供资产管理），中央编号:
                        AEO169。
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 1.2rem; position: relative; margin-top: 5vw; margin-bottom: 1vw;">
                        联系我们

                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-bottom: 2vw;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/07ae5f7fc157497eb0f5746e3b7ae3b3.png"
                            alt=""
                            style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">地址：香港上环永乐街88号25楼
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/1cba1443d01a80d923c756ffff8ebb00.png"
                            alt=""
                            style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">客户服务热线：+852
                        3896 3896(香港)
                    </td>
                </tr>
                <tr>
                    <td
                        style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-left: 3vw;padding-bottom: 2vw;">
                        星期一至五 9:00时至下午6:00时(星期六、日及公众假期除外)
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/dff8c3f663dc4f9f9ff824791934ee07.png"
                            alt=""
                            style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">电子邮件(客户服务)：cs@lionfin.com
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-left: 3vw;">
                        E-mail (Customer Service)：cs@lionfin.com
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>

</html>
','<!DOCTYPE html>
<html lang="zh-Hant" style="font-size: 1.6vw;">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>獅瀚環球金融-關於被視為專業投資者的申請</title>
</head>

<body style="margin: 0; font-family: SourceHanSansSC-Regular; background-color: #f5f5f5; font-size: 1.6vw;">
<table style="width: 100%; max-width: 1440px; margin: 0 auto; background-color: #fff; border-collapse: collapse;">
    <!-- Header -->
    <tr>
        <td
            style="background-image: url(''https://minio.ifund.live:9000/minigod-prd/upload/20241031/697b20c15852018882542151b8519d0d.png''); background-size: cover; height: 8vw;">
            <table style="width: 100%; height: 100%;">
                <tr>
                    <td style="padding-left: 4vw; padding-top: 2vw;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/be19cfa72395dfd24dcfc3133884ccad.png"
                            alt="DigiFIN Logo" style="width: 20vw;">
                    </td>
                </tr>
            </table>
        </td>
    </tr>

    <!-- Main Content -->
    <tr>
        <td style="padding: 2vw; text-align: center;">
            <table style="width: 100%; background-color: #e9f0f8; padding: 3vw 0; margin: 0 auto;">
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        尊敬的{0}，您好！
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        感謝您選擇獅瀚環球。我們未能通過閣下申請成為專業投資者的審批，請登入DigiFIN APP查看原因並再次提交。謝謝。
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        如閣下有任何查詢，敬請致電本公司的客戶服務熱線 (852) 3896 3896。
                    </td>
                </tr>
                <tr>
                    <td
                        style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block; float: left">
                        此致
                    </td>
                </tr>
                <tr>
                    <td
                        style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block; float: left">
                        <a style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block;"
                           href="https://www.digifingroup.com/#/home">獅瀚環球金融有限公司</a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

    <!-- Security Info -->
    <tr>
        <td style="padding: 2vw;">
            <table style="width: 100%;">
                <tr>
                    <td style="text-align: left;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/d95d1bf57ea0810ae74eab94fecbe9f6.png"
                            alt="" style="width: 1.6vw; vertical-align: middle;">
                        <span
                            style="font-size: 1.2rem; padding: 0 1vw; background: linear-gradient(to bottom, transparent 50%, #FFF6E5 50%); display: inline-block;">
                                網絡安全提示：保護好您的登錄/安全憑證
                            </span>
                    </td>
                </tr>
                <tr>
                    <td style="padding-left: 3.6vw; color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <p style="margin: 0;">
                            1.提防詐騙！獅瀚環球不會以電郵要求客戶提供個人資料，使用者名稱、密碼、一次性密碼或其他賬戶資料；</p>
                        <p style="margin: 0;">2.切勿通過任何電子郵件提供的超鏈接登錄網上服務，或任何社交媒體平台、電郵、短信或電話等方式，透露您的登錄賬號、密碼和一次性登錄密碼
                            (OTP) 給他人，請務必小心保管個人資料；</p>
                        <p style="margin: 0;">3.不要使用公共場所的設備登錄交易系統；</p>
                        <p style="margin: 0;">4.定期修改密碼，確保密碼強度，能夠有效保障您的賬號安全；</p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

    <!-- Footer -->
    <tr>
        <td
            style="background-image: url(''https://minio.ifund.live:9000/minigod-prd/upload/20241031/1df6f6c328c06f0d07ad041a3b5c37a0.png''); background-size: 100% 100%; padding: 3vw 4vw;">
            <table style="width: 100%;">
                <tr>
                    <td style="font-size: 1.2rem; position: relative; margin-bottom: 2vw;">
                        關於 [獅瀚環球資產管理平台]
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-bottom: 3vw;">
                        【獅瀚環球資產管理平台】作為一站式金融資產管理平台，在全球範圍內為投資者提供優質財富管理服務，由數字金融集團（DigiFIN
                        Group）旗下獅瀚環球金融有限公司全資擁有。獅瀚環球金融有限公司創立於1998年，為香港證券監會持牌法人（獲發牌照包括第一類：證券交易、第四類：就證券提供意見及第九類：提供資產管理），中央編號:
                        AEO169。
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 1.2rem; position: relative; margin-top: 5vw; margin-bottom: 1vw;">
                        聯繫我們
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-bottom: 2vw;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/07ae5f7fc157497eb0f5746e3b7ae3b3.png"
                            alt=""
                            style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">地址：香港上環永樂街88號25樓
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/1cba1443d01a80d923c756ffff8ebb00.png"
                            alt=""
                            style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">客戶服務熱線：+852
                        3896 3896(香港)
                    </td>
                </tr>
                <tr>
                    <td
                        style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-left: 3vw;padding-bottom: 2vw;">
                        星期一至五 9:00時至下午6:00時(星期六、日及公眾假期除外)
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <img
                            src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/dff8c3f663dc4f9f9ff824791934ee07.png"
                            alt=""
                            style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">電子郵件(客戶服務)：cs@lionfin.com
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-left: 3vw;">
                        E-mail (Customer Service)：cs@lionfin.com
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>

</html>
','<!DOCTYPE html>
<html lang="en" style="font-size: 1.6vw;">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lion Global - Regarding the Application to be Recognized as a Professional Investor</title>
</head>

<body style="margin: 0; font-family: SourceHanSansSC-Regular; background-color: #f5f5f5; font-size: 1.6vw;">
<table style="width: 100%; max-width: 1440px; margin: 0 auto; background-color: #fff; border-collapse: collapse;">
    <!-- Header -->
    <tr>
        <td
            style="background-image: url(''https://minio.ifund.live:9000/minigod-prd/upload/20241031/697b20c15852018882542151b8519d0d.png''); background-size: cover; height: 8vw;">
            <table style="width: 100%; height: 100%;">
                <tr>
                    <td style="padding-left: 4vw; padding-top: 2vw;">
                        <img src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/be19cfa72395dfd24dcfc3133884ccad.png"
                             alt="DigiFIN Logo" style="width: 20vw;">
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <!-- Main Content -->
    <tr>
        <td style="padding: 2vw; text-align: center;">
            <table style="width: 100%; background-color: #e9f0f8; padding: 3vw 0; margin: 0 auto;">
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        Dear {0},
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        Thank you for choosing Lion Global. We regret to inform you that your application to become a professional investor has not been approved. Please log in to the DigiFIN APP to check the reason and resubmit your application. Thank you.
                    </td>
                </tr>
                <tr>
                    <td style="font-weight: bold; font-size: 1rem; color: #182987; padding-bottom: 2vw; text-align: left;">
                        If you have any inquiries, please contact our customer service team at (852) 3896 3896.
                    </td>
                </tr>
                <tr>
                    <td
                        style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block; float: left">
                        Yours sincerely,
                    </td>
                </tr>
                <tr>
                    <td
                        style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block; float: left">
                        <a style="font-size: 1.125rem; color: #182987; text-decoration: underline; display: inline-block;"
                           href="https://www.digifingroup.com/#/home">Lion Global Financial Limited</a>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <!-- Security Info -->
    <tr>
        <td style="padding: 2vw;">
            <table style="width: 100%;">
                <tr>
                    <td style="text-align: left;">
                        <img src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/d95d1bf57ea0810ae74eab94fecbe9f6.png"
                             alt="" style="width: 1.6vw; vertical-align: middle;">
                        <span
                            style="font-size: 1.2rem; padding: 0 1vw; background: linear-gradient(to bottom, transparent 50%, #FFF6E5 50%); display: inline-block;">
                                Cybersecurity Reminder: Protect your login/security credentials
                            </span>
                    </td>
                </tr>
                <tr>
                    <td style="padding-left: 3.6vw; color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <p style="margin: 0;">1. Beware of scams! Lion Financial Group will not ask customers to provide personal information, usernames, passwords, one-time passwords, or other account information via email;</p>
                        <p style="margin: 0;">2. Do not log in to online services through hyperlinks provided in any email, or disclose your login account, password, and one-time login password (OTP) to others through any social media platform, email, SMS, or phone. Please be sure to keep your personal information safe;</p>
                        <p style="margin: 0;">3. Do not use public devices to log in to the trading system;</p>
                        <p style="margin: 0;">4. Regularly change your password to ensure password strength and effectively protect your account security;</p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>

    <!-- Footer -->
    <tr>
        <td
            style="background-image: url(''https://minio.ifund.live:9000/minigod-prd/upload/20241031/1df6f6c328c06f0d07ad041a3b5c37a0.png''); background-size: 100% 100%; padding: 3vw 4vw;">
            <table style="width: 100%;">
                <tr>
                    <td style="font-size: 1.2rem; position: relative; margin-bottom: 2vw;">
                        About [Lion Financial Asset Management Platform]
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-bottom: 3vw;">
                        The [Lion Financial Asset Management Platform] is a one-stop financial asset management platform that provides high-quality wealth management services to investors worldwide, fully owned by Lion Financial Group Limited under DigiFIN Group. Lion Financial Group Limited was established in 1998 and is a licensed entity of the Hong Kong Securities and Futures Commission (licenses include Type 1: Securities Trading, Type 4: Advising on Securities, and Type 9: Asset Management), Central Number: AEO169.
                    </td>
                </tr>
                <tr>
                    <td style="font-size: 1.2rem; position: relative; margin-top: 5vw; margin-bottom: 1vw;">
                        Contact Us
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-bottom: 2vw;">
                        <img src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/07ae5f7fc157497eb0f5746e3b7ae3b3.png"
                             alt=""
                             style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">Address: 25th Floor, 88 Wing Lok Street, Sheung Wan, Hong Kong
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <img src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/1cba1443d01a80d923c756ffff8ebb00.png"
                             alt=""
                             style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">Customer Service Hotline: +852 3896 3896 (Hong Kong)
                    </td>
                </tr>
                <tr>
                    <td
                        style="color: #666666; font-size: 1rem; line-height: 1.875rem; padding-left: 3vw;padding-bottom: 2vw;">
                        Monday to Friday 9:00 AM to 6:00 PM (excluding Saturdays, Sundays, and public holidays)
                    </td>
                </tr>
                <tr>
                    <td style="color: #666666; font-size: 1rem; line-height: 1.875rem;">
                        <img src="https://minio.ifund.live:9000/minigod-prd/upload/20241031/dff8c3f663dc4f9f9ff824791934ee07.png"
                             alt=""
                             style="width: 2vw; height: 2vw; vertical-align: middle; margin-right: 1vw;">Email (Customer Service): cs@lionfin.com
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>

</html>
', NULL, 2, NULL, '000000', 1123598821738675201, NULL, NOW(), 1123598821738675201, NOW(), 1, 0);
