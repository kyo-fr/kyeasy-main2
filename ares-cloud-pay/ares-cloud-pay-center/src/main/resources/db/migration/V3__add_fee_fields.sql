-- 为交易表添加手续费相关字段
ALTER TABLE blockchain_transactions ADD (
    fee_rate NUMBER(10),
    actual_amount NUMBER(20)
);

-- 为账户流水表添加手续费相关字段
ALTER TABLE account_flow ADD (
    fee_amount NUMBER(20),
    fee_rate NUMBER(10),
    actual_amount NUMBER(20)
);

-- 添加注释
COMMENT ON COLUMN blockchain_transactions.fee_rate IS '手续费百分比（以万分比为单位，如100表示1%）';
COMMENT ON COLUMN blockchain_transactions.actual_amount IS '实际到账金额（扣除手续费后的金额，以分为单位）';
COMMENT ON COLUMN account_flow.fee_amount IS '手续费金额';
COMMENT ON COLUMN account_flow.fee_rate IS '手续费百分比（以万分比为单位，如100表示1%）';
COMMENT ON COLUMN account_flow.actual_amount IS '实际到账金额（扣除手续费后的金额）'; 