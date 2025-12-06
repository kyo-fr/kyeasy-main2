-- 为 payment_account_flow 表添加 transaction_type 字段
-- 用于记录资金用途，配合 flow_type 字段使用
-- flow_type: 标记收入还是支出
-- transaction_type: 记录具体的资金用途
-- 适用于 Oracle 数据库

-- 添加 transaction_type 字段
ALTER TABLE payment_account_flow 
ADD (transaction_type VARCHAR2(50));

-- 添加字段注释
COMMENT ON COLUMN payment_account_flow.transaction_type IS '交易类型（记录资金用途）';

-- 为现有数据设置默认值（根据业务逻辑调整）
-- 这里提供一些示例，实际使用时需要根据具体业务场景调整
UPDATE payment_account_flow 
SET transaction_type = CASE 
    WHEN flow_type = 'IN' THEN 'USER_TO_USER'  -- 转入流水默认为用户间转账
    WHEN flow_type = 'OUT' THEN 'USER_TO_USER' -- 转出流水默认为用户间转账
    ELSE 'UNKNOWN' -- 其他类型标记为未知
END
WHERE transaction_type IS NULL;

-- 提交事务
COMMIT;

-- 添加索引以提高查询性能
CREATE INDEX idx_account_flow_transaction_type ON payment_account_flow(transaction_type);

-- 添加复合索引，提高按账户和交易类型查询的性能
CREATE INDEX idx_account_flow_account_transaction_type ON payment_account_flow(account_id, transaction_type);

-- 添加复合索引，提高按流水类型和交易类型查询的性能
CREATE INDEX idx_account_flow_flow_transaction_type ON payment_account_flow(flow_type, transaction_type);
