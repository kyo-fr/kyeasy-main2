-- 修复 PRODUCT_INVENTORY_RESERVATION 表 ID 字段长度问题
-- 问题：ID 字段长度为 VARCHAR2(32)，但实际值为 orderId_productId，长度达到 39
-- 解决：将 ID 字段长度扩展到 VARCHAR2(64)
-- 
-- 错误信息：
-- ORA-12899: 列 "ADMIN"."PRODUCT_INVENTORY_RESERVATION"."ID" 的值太大 (实际值: 39, 最大值: 32)
-- 
-- 版本：V1.0.1
-- 执行时间：2025-10-16
-- 影响：商品库存预留功能

-- 1. 修改 ID 字段长度
ALTER TABLE PRODUCT_INVENTORY_RESERVATION MODIFY ID VARCHAR2(64);

-- 2. 验证修改结果
SELECT COLUMN_NAME, DATA_TYPE, DATA_LENGTH, NULLABLE, DATA_DEFAULT
FROM USER_TAB_COLUMNS 
WHERE TABLE_NAME = 'PRODUCT_INVENTORY_RESERVATION' 
  AND COLUMN_NAME = 'ID';

-- 预期结果：
-- COLUMN_NAME  DATA_TYPE   DATA_LENGTH  NULLABLE  DATA_DEFAULT
-- -----------  ----------  -----------  --------  ------------
-- ID           VARCHAR2    64           N         NULL

-- 说明：
-- ID 字段由 orderId 和 productId 通过下划线拼接而成
-- orderId: 19位（如 1978834071595544578）
-- productId: 19位（如 1963601007629848578）
-- 拼接格式: orderId_productId（如 1978834071595544578_1963601007629848578）
-- 实际长度: 19 + 1 + 19 = 39 字符
-- 
-- 为保证兼容性和未来扩展，将字段长度设置为 64 字符

