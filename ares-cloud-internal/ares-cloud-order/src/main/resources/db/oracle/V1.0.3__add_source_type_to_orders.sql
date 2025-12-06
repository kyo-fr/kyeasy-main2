-- 为订单表添加来源类型字段
ALTER TABLE orders ADD source_type NUMBER(10) DEFAULT 1 NOT NULL;

COMMENT ON COLUMN orders.source_type IS '订单来源类型（1：网上下单，2：商户下单）';