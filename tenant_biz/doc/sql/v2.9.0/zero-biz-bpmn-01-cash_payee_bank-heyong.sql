
-- 屏蔽掉所有出金银行付款方式为【电汇-值=1】的配置，到时候上生产也要检查一下是否有配置电汇的数据。
SELECT *FROM cash_payee_bank_detail WHERE payee_bank_id IN(SELECT id FROM cash_payee_bank WHERE bank_type=2) AND support_type=1;-- 付款信息详情配置（入金和出金都使用这张表的银行卡信息配置）

UPDATE cash_payee_bank_detail SET is_deleted=1 WHERE payee_bank_id IN(SELECT id FROM cash_payee_bank WHERE bank_type=2) AND support_type=1;
