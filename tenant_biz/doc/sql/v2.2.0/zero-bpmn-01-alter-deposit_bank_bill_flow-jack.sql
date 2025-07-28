
--添加流水表 删除字段 创建时间 修改时间
alter table deposit_bank_bill_flow
    add is_deleted int  NOT NULL DEFAULT 0 comment '是否已删除：1-已删除';

