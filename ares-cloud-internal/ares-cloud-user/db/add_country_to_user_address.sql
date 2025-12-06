-- 为用户地址表添加国家字段
-- 版本：V1.0.1
-- 执行时间：2025-01-XX
-- 说明：为user_address表添加country字段，用于存储地址的国家信息

-- 添加国家字段
ALTER TABLE user_address ADD country VARCHAR2(100);

-- 添加字段注释
COMMENT ON COLUMN user_address.country IS '国家';

-- 创建索引（可选，如果经常按国家查询）
-- CREATE INDEX IDX_USER_ADDRESS_COUNTRY ON user_address(country);

-- 验证字段是否添加成功
-- SELECT COLUMN_NAME, DATA_TYPE, DATA_LENGTH, NULLABLE, COMMENTS 
-- FROM USER_TAB_COLUMNS 
-- WHERE TABLE_NAME = 'USER_ADDRESS' 
-- AND COLUMN_NAME = 'COUNTRY';
