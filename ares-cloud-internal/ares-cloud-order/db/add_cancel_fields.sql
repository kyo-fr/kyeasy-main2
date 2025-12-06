-- 添加字段
ALTER TABLE orders ADD (cancel_time NUMBER(19));
ALTER TABLE orders ADD (cancel_reason VARCHAR2(500));

-- 添加注释
COMMENT ON COLUMN orders.cancel_time IS '取消时间';
COMMENT ON COLUMN orders.cancel_reason IS '取消原因';

-- 添加索引
CREATE INDEX idx_orders_cancel_time ON orders(cancel_time);
CREATE INDEX idx_orders_cancel_reason ON orders(cancel_reason);

