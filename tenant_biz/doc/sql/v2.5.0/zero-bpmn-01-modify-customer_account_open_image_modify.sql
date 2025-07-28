-- 修改开户图片资料修改的字段为空
ALTER TABLE customer_account_open_image_modify
    MODIFY apply_id BIGINT NULL;

ALTER TABLE customer_account_open_image_modify
    MODIFY file_storage_name VARCHAR(255) NULL;

ALTER TABLE customer_account_open_image_modify
    MODIFY ext_name VARCHAR(32) NULL;
