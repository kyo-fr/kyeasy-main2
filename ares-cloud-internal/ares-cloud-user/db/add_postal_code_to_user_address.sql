-- 为用户地址表添加邮编字段
-- 版本：V1.0.2
-- 执行时间：2025-01-XX
-- 说明：为user_address表添加postal_code字段，用于存储地址的邮编信息

-- 添加邮编字段
ALTER TABLE user_address ADD postal_code VARCHAR2(20);

-- 添加字段注释
COMMENT ON COLUMN user_address.postal_code IS '邮编';

-- 创建索引（可选，如果经常按邮编查询）
-- CREATE INDEX IDX_USER_ADDRESS_POSTAL_CODE ON user_address(postal_code);

-- 验证字段是否添加成功
-- SELECT COLUMN_NAME, DATA_TYPE, DATA_LENGTH, NULLABLE, COMMENTS 
-- FROM USER_TAB_COLUMNS 
-- WHERE TABLE_NAME = 'USER_ADDRESS' 
-- AND COLUMN_NAME = 'POSTAL_CODE';
