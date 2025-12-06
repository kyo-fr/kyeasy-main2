-- 为交易方表添加国家和城市字段
-- 版本：V1.0.4
-- 执行时间：2025-01-XX
-- 说明：为t_party表添加country和city字段，用于存储交易方的国家和城市信息

-- 添加国家字段
ALTER TABLE t_party ADD country VARCHAR2(100);

-- 添加城市字段
ALTER TABLE t_party ADD city VARCHAR2(100);

-- 添加字段注释
COMMENT ON COLUMN t_party.country IS '国家';
COMMENT ON COLUMN t_party.city IS '城市';

-- 创建索引（可选，如果经常按国家或城市查询）
-- CREATE INDEX IDX_T_PARTY_COUNTRY ON t_party(country);
-- CREATE INDEX IDX_T_PARTY_CITY ON t_party(city);

-- 验证字段是否添加成功
-- SELECT COLUMN_NAME, DATA_TYPE, DATA_LENGTH, NULLABLE, COMMENTS 
-- FROM USER_TAB_COLUMNS 
-- WHERE TABLE_NAME = 'T_PARTY' 
-- AND COLUMN_NAME IN ('COUNTRY', 'CITY');
