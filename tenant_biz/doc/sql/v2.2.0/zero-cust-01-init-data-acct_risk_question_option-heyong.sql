-- 第1题答案（繁体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(100101,1001,1,'超過HK$8,000,000', 'zh-han', 8, 1, 0, NOW(), NOW()),
(100102,1001,2,'HK$4,000,000至HK$8,000,000','zh-han', 6, 2, 0, NOW(), NOW()),
(100103,1001,3,'HK$ 500,000至HK$3,999,999','zh-han', 4, 3, 0, NOW(), NOW()),
(100104,1001,4,'少於HK$500,000','zh-han', 2, 4, 0, NOW(), NOW());

-- 第1题答案（简体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(200101,2001,1,'超过HK$8,000,000', 'zh-hans', 8, 1, 0, NOW(), NOW()),
(200102,2001,2,'HK$4,000,000至HK$8,000,000','zh-hans', 6, 2, 0, NOW(), NOW()),
(200103,2001,3,'HK$ 500,000至HK$3,999,999','zh-hans', 4, 3, 0, NOW(), NOW()),
(200104,2001,4,'少于HK$500,000','zh-hans', 2, 4, 0, NOW(), NOW());

-- 第1题答案（英文）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(300101,3001,1,'Over HK$8,000,000', 'en', 8, 1, 0, NOW(), NOW()),
(300102,3001,2,'HK$4,000,000 to HK$8,000,000','en', 6, 2, 0, NOW(), NOW()),
(300103,3001,3,'HK$ 500,000 to HK$3,999,999','en', 4, 3, 0, NOW(), NOW()),
(300104,3001,4,'Less than HK$500,000','en', 2, 4, 0, NOW(), NOW());




-- 第2题答案（繁体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(100201,1002,1,'超過HK$50,000', 'zh-han', 12, 1, 0, NOW(), NOW()),
(100202,1002,2,'HK$20,000 至 HK$50,000','zh-han', 9, 2, 0, NOW(), NOW()),
(100203,1002,3,'HK$ 5,000 至 HK$19,999','zh-han', 6, 3, 0, NOW(), NOW()),
(100204,1002,4,'少於HK$5,000','zh-han', 3, 4, 0, NOW(), NOW());

-- 第2题答案（简体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(200201,2002,1,'超过HK$50,000', 'zh-hans', 12, 1, 0, NOW(), NOW()),
(200202,2002,2,'HK$20,000 至 HK$50,000','zh-hans', 9, 2, 0, NOW(), NOW()),
(200203,2002,3,'HK$ 5,000 至 HK$19,999','zh-hans', 6, 3, 0, NOW(), NOW()),
(200204,2002,4,'少於HK$5,000','zh-hans', 3, 4, 0, NOW(), NOW());

-- 第2题答案（英文）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(300201,3002,1,'Over HK$50,000', 'en', 12, 1, 0, NOW(), NOW()),
(300202,3002,2,'HK$20,000 to HK$50,000','en', 9, 2, 0, NOW(), NOW()),
(300203,3002,3,'HK$ 5,000 to HK$19,999','en', 6, 3, 0, NOW(), NOW()),
(300204,3002,4,'Less than HK$5,000','en', 3, 4, 0, NOW(), NOW());




-- 第3题答案（繁体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(100301,1003,1,'30歲或以下', 'zh-han', 9, 1, 0, NOW(), NOW()),
(100302,1003,2,'31至45歲','zh-han', 6, 2, 0, NOW(), NOW()),
(100303,1003,3,'46至65歲','zh-han', 3, 3, 0, NOW(), NOW()),
(100304,1003,4,'65歲以上','zh-han', 0, 4, 0, NOW(), NOW());

-- 第3题答案（简体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(200301,2003,1,'30岁或以下', 'zh-hans', 9, 1, 0, NOW(), NOW()),
(200302,2003,2,'31至45岁','zh-hans', 6, 2, 0, NOW(), NOW()),
(200303,2003,3,'46至65岁','zh-hans', 3, 3, 0, NOW(), NOW()),
(200304,2003,4,'65岁以上','zh-hans', 0, 4, 0, NOW(), NOW());

-- 第3题答案（英文）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(300301,3003,1,'30 or under', 'en', 9, 1, 0, NOW(), NOW()),
(300302,3003,2,'31 to 45','en', 6, 2, 0, NOW(), NOW()),
(300303,3003,3,'46 to 65','en', 3, 3, 0, NOW(), NOW()),
(300304,3003,4,'65 or above','en', 0, 4, 0, NOW(), NOW());




-- 第4题答案（繁体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(100401,1004,1,'10年後才會動用', 'zh-han', 12, 1, 0, NOW(), NOW()),
(100402,1004,2,'5-10年間會動用','zh-han', 9, 2, 0, NOW(), NOW()),
(100403,1004,3,'未來2-5年內會動用','zh-han', 7, 3, 0, NOW(), NOW()),
(100404,1004,4,'未來兩年內會動用','zh-han', 1, 4, 0, NOW(), NOW());

-- 第4题答案（简体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(200401,2004,1,'10年后才会动用', 'zh-hans', 12, 1, 0, NOW(), NOW()),
(200402,2004,2,'5-10年间会动用','zh-hans', 9, 2, 0, NOW(), NOW()),
(200403,2004,3,'未来2-5年內会动用','zh-hans', 7, 3, 0, NOW(), NOW()),
(200404,2004,4,'未来两年内会动用','zh-hans', 1, 4, 0, NOW(), NOW());

-- 第4题答案（英文）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(300401,3004,1,'After 10 years or more', 'en', 12, 1, 0, NOW(), NOW()),
(300402,3004,2,'Between 5 and 10 years','en', 9, 2, 0, NOW(), NOW()),
(300403,3004,3,'Within 2 and 5 years','en', 7, 3, 0, NOW(), NOW()),
(300404,3004,4,'Within the next 2 years','en', 1, 4, 0, NOW(), NOW());




-- 第5题答案（繁体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(100501,1005,1,'少於25%', 'zh-han', 8, 1, 0, NOW(), NOW()),
(100502,1005,2,'介乎25-50%之間','zh-han', 6, 2, 0, NOW(), NOW()),
(100503,1005,3,'介乎50-75%之間','zh-han', 4, 3, 0, NOW(), NOW()),
(100504,1005,4,'超過75%','zh-han', 2, 4, 0, NOW(), NOW());

-- 第5题答案（简体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(200501,2005,1,'少于25%', 'zh-hans', 8, 1, 0, NOW(), NOW()),
(200502,2005,2,'介乎25-50%之间','zh-hans', 6, 2, 0, NOW(), NOW()),
(200503,2005,3,'介乎50-75%之间','zh-hans', 4, 3, 0, NOW(), NOW()),
(200504,2005,4,'超过75%','zh-hans', 2, 4, 0, NOW(), NOW());

-- 第5题答案（英文）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(300501,3005,1,'Less than 25%', 'en', 8, 1, 0, NOW(), NOW()),
(300502,3005,2,'Between 25-50%','en', 6, 2, 0, NOW(), NOW()),
(300503,3005,3,'Between 50-75%','en', 4, 3, 0, NOW(), NOW()),
(300504,3005,4,'Greater than 75%','en', 2, 4, 0, NOW(), NOW());




-- 第6题答案（繁体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(100601,1006,1,'收入增幅多且快，遠遠超越通脹', 'zh-han', 12, 1, 0, NOW(), NOW()),
(100602,1006,2,'收入增幅將跑贏通脹','zh-han', 9, 2, 0, NOW(), NOW()),
(100603,1006,3,'收入增幅將與通脹同步','zh-han', 6, 3, 0, NOW(), NOW()),
(100604,1006,4,'收入增幅低過通脹','zh-han', 4, 4, 0, NOW(), NOW());

-- 第6题答案（简体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(200601,2006,1,'收入增幅多且快，远远超越通胀', 'zh-hans', 12, 1, 0, NOW(), NOW()),
(200602,2006,2,'收入增幅将跑赢通胀','zh-hans', 9, 2, 0, NOW(), NOW()),
(200603,2006,3,'收入增幅将与通胀同步','zh-hans', 6, 3, 0, NOW(), NOW()),
(200604,2006,4,'收入增幅低过通胀','zh-hans', 4, 4, 0, NOW(), NOW());

-- 第6题答案（英文）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(300601,3006,1,'Grow quickly and far outpace inflation', 'en', 12, 1, 0, NOW(), NOW()),
(300602,3006,2,'Grow ahead of inflation','en', 9, 2, 0, NOW(), NOW()),
(300603,3006,3,'Grow in line with inflation','en', 6, 3, 0, NOW(), NOW()),
(300604,3006,4,'Go down','en', 4, 4, 0, NOW(), NOW());






-- 第7题答案（繁体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(100701,1007,1,'有，金額多過六個月收入', 'zh-han', 8, 1, 0, NOW(), NOW()),
(100702,1007,2,'有，金額介乎三個月至六個收入','zh-han', 6, 2, 0, NOW(), NOW()),
(100703,1007,3,'有，金額低過三個月收入','zh-han', 4, 3, 0, NOW(), NOW()),
(100704,1007,4,'沒有','zh-han', 2, 4, 0, NOW(), NOW());

-- 第7题答案（简体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(200701,2007,1,'有，金额多过六个月收入', 'zh-hans', 8, 1, 0, NOW(), NOW()),
(200702,2007,2,'有，金额介乎三个月至六个收入','zh-hans', 6, 2, 0, NOW(), NOW()),
(200703,2007,3,'有，金额低过三个月收入','zh-hans', 4, 3, 0, NOW(), NOW()),
(200704,2007,4,'没有','zh-hans', 2, 4, 0, NOW(), NOW());

-- 第7题答案（英文）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(300701,3007,1,'Yes, more than 6 months'' income', 'en', 8, 1, 0, NOW(), NOW()),
(300702,3007,2,'Yes, more than 3 months'' but less than 6 months'' income','en', 6, 2, 0, NOW(), NOW()),
(300703,3007,3,'Yes, less than 3 months'' income','en', 4, 3, 0, NOW(), NOW()),
(300704,3007,4,'No','en', 2, 4, 0, NOW(), NOW());




-- 第8题答案（繁体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(100801,1008,1,'是，我對自己的決定有信心', 'zh-han', 12, 1, 0, NOW(), NOW()),
(100802,1008,2,'間中，但若我須作決定，我靠自己','zh-han', 9, 2, 0, NOW(), NOW()),
(100803,1008,3,'間中，我依賴我經紀/顧問的建','zh-han', 6, 3, 0, NOW(), NOW()),
(100804,1008,4,'從不，我把所有決定交付我顧問','zh-han', 3, 4, 0, NOW(), NOW());

-- 第8题答案（简体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(200801,2008,1,'是，我对自己的决定有信心', 'zh-hans', 12, 1, 0, NOW(), NOW()),
(200802,2008,2,'间中，但若我须作决定，我靠自己','zh-hans', 9, 2, 0, NOW(), NOW()),
(200803,2008,3,'间中，我依赖我经纪/顾问的建','zh-hans', 6, 3, 0, NOW(), NOW()),
(200804,2008,4,'从不，我把所有决定交付我顾问','zh-hans', 3, 4, 0, NOW(), NOW());

-- 第8题答案（英文）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(300801,3008,1,'Yes, I am confident with my decisions', 'en', 12, 1, 0, NOW(), NOW()),
(300802,3008,2,'Sometimes. But whenever I make the decision, I do it myself','en', 9, 2, 0, NOW(), NOW()),
(300803,3008,3,'Sometimes. I rely on my broker''s/advisor''s suggestion','en', 6, 3, 0, NOW(), NOW()),
(300804,3008,4,'Never, I leave the decisions to my advisor','en', 3, 4, 0, NOW(), NOW());





-- 第9题答案（繁体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(100901,1009,1,'長線增長', 'zh-han', 16, 1, 0, NOW(), NOW()),
(100902,1009,2,'增長及收入','zh-han', 12, 2, 0, NOW(), NOW()),
(100903,1009,3,'固定收入','zh-han', 8, 3, 0, NOW(), NOW()),
(100904,1009,4,'資本保值，能承受小幅的價格波動','zh-han', 4, 4, 0, NOW(), NOW());

-- 第9题答案（简体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(200901,2009,1,'长线增长', 'zh-hans', 16, 1, 0, NOW(), NOW()),
(200902,2009,2,'增长及收入','zh-hans', 12, 2, 0, NOW(), NOW()),
(200903,2009,3,'固定收入','zh-hans', 8, 3, 0, NOW(), NOW()),
(200904,2009,4,'资本保值，能承受小幅的价格波动','zh-hans', 4, 4, 0, NOW(), NOW());

-- 第9题答案（英文）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(300901,3009,1,'Long term Capital Growth', 'en', 16, 1, 0, NOW(), NOW()),
(300902,3009,2,'Growth and Income','en', 12, 2, 0, NOW(), NOW()),
(300903,3009,3,'Income','en', 8, 3, 0, NOW(), NOW()),
(300904,3009,4,'Capital Preservation, can bear little price fluctuation','en', 4, 4, 0, NOW(), NOW());




-- 第10题答案（繁体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(101001,1010,1,'曾經投資，而我亦可承受有關風險', 'zh-han', 8, 1, 0, NOW(), NOW()),
(101002,1010,2,'曾經投資，但我對有關風險感到不安','zh-han', 6, 2, 0, NOW(), NOW()),
(101003,1010,3,'不曾投資，但我相信我可承受有關投資','zh-han', 4, 3, 0, NOW(), NOW()),
(101004,1010,4,'不曾投資，而我相信我將對有關風險感到不安','zh-han', 2, 4, 0, NOW(), NOW());

-- 第10题答案（简体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(201001,2010,1,'曾经投资，而我亦可承受有关风险', 'zh-hans', 8, 1, 0, NOW(), NOW()),
(201002,2010,2,'曾经投资，但我对有关风险感到不安','zh-hans', 6, 2, 0, NOW(), NOW()),
(201003,2010,3,'不曾投资，但我相信我可承受有关投资','zh-hans', 4, 3, 0, NOW(), NOW()),
(201004,2010,4,'不曾投资，而我相信我将对有关风险感到不安','zh-hans', 2, 4, 0, NOW(), NOW());

-- 第10题答案（英文）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(301001,3010,1,'Yes, and I felt comfortable with the risk', 'en', 8, 1, 0, NOW(), NOW()),
(301002,3010,2,'Yes, but I was uncomfortable with the risk','en', 6, 2, 0, NOW(), NOW()),
(301003,3010,3,'No, but I would be comfortable with the risk if I did','en', 4, 3, 0, NOW(), NOW()),
(301004,3010,4,'No, and I would be uncomfortable with the risk if I did','en', 2, 4, 0, NOW(), NOW());





-- 第11题答案（繁体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(101101,1011,1,'不接受任何虧損', 'zh-han', 0, 1, 0, NOW(), NOW()),
(101102,1011,2,'10%或以下','zh-han', 5, 2, 0, NOW(), NOW()),
(101103,1011,3,'多過10% 至35%','zh-han', 10, 3, 0, NOW(), NOW()),
(101104,1011,4,'多過35%','zh-han', 15, 4, 0, NOW(), NOW());

-- 第11题答案（简体）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(201101,2011,1,'不接受任何亏损', 'zh-hans', 0, 1, 0, NOW(), NOW()),
(201102,2011,2,'10%或以下','zh-hans', 5, 2, 0, NOW(), NOW()),
(201103,2011,3,'多过10%','zh-hans', 10, 3, 0, NOW(), NOW()),
(201104,2011,4,'多过35%','zh-hans', 15, 4, 0, NOW(), NOW());

-- 第11题答案（英文）
INSERT INTO `zero_cust`.`acct_risk_question_option` (`id`, `question_id`, `sort`, `option_value`, `lang`, `option_score`, `option_id`, `flag`, `create_time`, `update_time`)
VALUES
(301101,3011,1,'No investment loss at all', 'en', 0, 1, 0, NOW(), NOW()),
(301102,3011,2,'10% or below','en', 5, 2, 0, NOW(), NOW()),
(301103,3011,3,'Over 10% up to 35%','en', 10, 3, 0, NOW(), NOW()),
(301104,3011,4,'More than 35%','en', 15, 4, 0, NOW(), NOW());


