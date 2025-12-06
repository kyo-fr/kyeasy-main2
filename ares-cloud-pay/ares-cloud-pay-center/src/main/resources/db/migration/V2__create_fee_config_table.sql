-- 创建手续费配置表
CREATE TABLE fee_config (
    id VARCHAR2(50) PRIMARY KEY,
    transaction_type VARCHAR2(50) NOT NULL,
    payment_region VARCHAR2(10) NOT NULL,
    fee_rate NUMBER(10) NOT NULL,
    min_fee_amount NUMBER(20),
    max_fee_amount NUMBER(20),
    enabled NUMBER(1) DEFAULT 1 NOT NULL,
    create_time NUMBER(20) NOT NULL,
    update_time NUMBER(20) NOT NULL,
    creator VARCHAR2(50),
    updater VARCHAR2(50),
    version NUMBER(10) DEFAULT 1 NOT NULL,
    deleted NUMBER(1) DEFAULT 0 NOT NULL
);

-- 添加注释
COMMENT ON TABLE fee_config IS '手续费配置表';
COMMENT ON COLUMN fee_config.id IS '主键ID';
COMMENT ON COLUMN fee_config.transaction_type IS '交易类型';
COMMENT ON COLUMN fee_config.payment_region IS '支付区域';
COMMENT ON COLUMN fee_config.fee_rate IS '手续费百分比（以万分比为单位，如100表示1%）';
COMMENT ON COLUMN fee_config.min_fee_amount IS '最低手续费金额（以分为单位）';
COMMENT ON COLUMN fee_config.max_fee_amount IS '最高手续费金额（以分为单位）';
COMMENT ON COLUMN fee_config.enabled IS '是否启用（1:启用,0:禁用）';
COMMENT ON COLUMN fee_config.create_time IS '创建时间';
COMMENT ON COLUMN fee_config.update_time IS '更新时间';
COMMENT ON COLUMN fee_config.creator IS '创建者';
COMMENT ON COLUMN fee_config.updater IS '更新者';
COMMENT ON COLUMN fee_config.version IS '版本号';
COMMENT ON COLUMN fee_config.deleted IS '删除标记（0:未删除,1:已删除）';

-- 创建索引
CREATE INDEX idx_fee_config_type_region ON fee_config(transaction_type, payment_region);
CREATE INDEX idx_fee_config_enabled ON fee_config(enabled);

-- 插入默认手续费配置
INSERT INTO fee_config (id, transaction_type, payment_region, fee_rate, min_fee_amount, max_fee_amount, enabled, create_time, update_time, creator, updater, version, deleted) VALUES
('FEE_CONFIG_001', 'USER_TO_USER', 'EUR', 50, 100, 5000, 1, 1640995200000, 1640995200000, 'SYSTEM', 'SYSTEM', 1, 0),
('FEE_CONFIG_002', 'USER_TO_USER', 'USD', 50, 100, 5000, 1, 1640995200000, 1640995200000, 'SYSTEM', 'SYSTEM', 1, 0),
('FEE_CONFIG_003', 'USER_TO_USER', 'CNY', 50, 100, 5000, 1, 1640995200000, 1640995200000, 'SYSTEM', 'SYSTEM', 1, 0),
('FEE_CONFIG_004', 'USER_TO_MERCHANT', 'EUR', 100, 200, 10000, 1, 1640995200000, 1640995200000, 'SYSTEM', 'SYSTEM', 1, 0),
('FEE_CONFIG_005', 'USER_TO_MERCHANT', 'USD', 100, 200, 10000, 1, 1640995200000, 1640995200000, 'SYSTEM', 'SYSTEM', 1, 0),
('FEE_CONFIG_006', 'USER_TO_MERCHANT', 'CNY', 100, 200, 10000, 1, 1640995200000, 1640995200000, 'SYSTEM', 'SYSTEM', 1, 0),
('FEE_CONFIG_007', 'MERCHANT_TO_USER', 'EUR', 100, 200, 10000, 1, 1640995200000, 1640995200000, 'SYSTEM', 'SYSTEM', 1, 0),
('FEE_CONFIG_008', 'MERCHANT_TO_USER', 'USD', 100, 200, 10000, 1, 1640995200000, 1640995200000, 'SYSTEM', 'SYSTEM', 1, 0),
('FEE_CONFIG_009', 'MERCHANT_TO_USER', 'CNY', 100, 200, 10000, 1, 1640995200000, 1640995200000, 'SYSTEM', 'SYSTEM', 1, 0),
('FEE_CONFIG_010', 'PLATFORM_RECHARGE', 'EUR', 0, 0, 0, 1, 1640995200000, 1640995200000, 'SYSTEM', 'SYSTEM', 1, 0),
('FEE_CONFIG_011', 'PLATFORM_RECHARGE', 'USD', 0, 0, 0, 1, 1640995200000, 1640995200000, 'SYSTEM', 'SYSTEM', 1, 0),
('FEE_CONFIG_012', 'PLATFORM_RECHARGE', 'CNY', 0, 0, 0, 1, 1640995200000, 1640995200000, 'SYSTEM', 'SYSTEM', 1, 0),
('FEE_CONFIG_013', 'PLATFORM_DEDUCTION', 'EUR', 0, 0, 0, 1, 1640995200000, 1640995200000, 'SYSTEM', 'SYSTEM', 1, 0),
('FEE_CONFIG_014', 'PLATFORM_DEDUCTION', 'USD', 0, 0, 0, 1, 1640995200000, 1640995200000, 'SYSTEM', 'SYSTEM', 1, 0),
('FEE_CONFIG_015', 'PLATFORM_DEDUCTION', 'CNY', 0, 0, 0, 1, 1640995200000, 1640995200000, 'SYSTEM', 'SYSTEM', 1, 0); 