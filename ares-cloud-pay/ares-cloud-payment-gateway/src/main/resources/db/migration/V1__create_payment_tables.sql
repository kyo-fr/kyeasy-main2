-- 创建支付订单表
CREATE TABLE payment_orders (
    order_no VARCHAR2(32) NOT NULL,
    merchant_id VARCHAR2(32) NOT NULL,
    channel VARCHAR2(20) NOT NULL,
    amount NUMBER(19,2) NOT NULL,
    subject VARCHAR2(256),
    body VARCHAR2(1024),
    status VARCHAR2(20) NOT NULL,
    platform VARCHAR2(20) NOT NULL,
    return_url VARCHAR2(512),
    notify_url VARCHAR2(512),
    channel_trade_no VARCHAR2(64),
    expire_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    pay_time TIMESTAMP,
    CONSTRAINT pk_payment_orders PRIMARY KEY (order_no)
);

-- 创建支付订单索引
CREATE INDEX idx_payment_orders_merchant ON payment_orders(merchant_id);
CREATE INDEX idx_payment_orders_channel_trade ON payment_orders(channel_trade_no);
CREATE INDEX idx_payment_orders_create_time ON payment_orders(create_time);

-- 添加注释
COMMENT ON TABLE payment_orders IS '支付订单表';
COMMENT ON COLUMN payment_orders.order_no IS '订单号';
COMMENT ON COLUMN payment_orders.merchant_id IS '商户ID';
COMMENT ON COLUMN payment_orders.channel IS '支付渠道';
COMMENT ON COLUMN payment_orders.amount IS '支付金额';
COMMENT ON COLUMN payment_orders.subject IS '订单标题';
COMMENT ON COLUMN payment_orders.body IS '订单内容';
COMMENT ON COLUMN payment_orders.status IS '订单状态';
COMMENT ON COLUMN payment_orders.platform IS '支付平台';
COMMENT ON COLUMN payment_orders.return_url IS '同步回调地址';
COMMENT ON COLUMN payment_orders.notify_url IS '异步通知地址';
COMMENT ON COLUMN payment_orders.channel_trade_no IS '渠道交易号';
COMMENT ON COLUMN payment_orders.expire_time IS '过期时间';
COMMENT ON COLUMN payment_orders.create_time IS '创建时间';
COMMENT ON COLUMN payment_orders.pay_time IS '支付时间';

-- 创建支付通知记录表
CREATE TABLE payment_notify_records (
    notify_id VARCHAR2(32) NOT NULL,
    order_no VARCHAR2(32) NOT NULL,
    merchant_id VARCHAR2(32) NOT NULL,
    notify_params CLOB,
    status VARCHAR2(20) NOT NULL,
    notify_result VARCHAR2(512),
    retry_count NUMBER(3) DEFAULT 0 NOT NULL,
    next_retry_time TIMESTAMP,
    create_time TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    update_time TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    CONSTRAINT pk_payment_notify_records PRIMARY KEY (notify_id)
);

-- 创建支付通知记录索引
CREATE INDEX idx_notify_records_order ON payment_notify_records(order_no);
CREATE INDEX idx_notify_records_merchant ON payment_notify_records(merchant_id);
CREATE INDEX idx_notify_records_retry ON payment_notify_records(retry_count, next_retry_time);

-- 添加注释
COMMENT ON TABLE payment_notify_records IS '支付通知记录表';
COMMENT ON COLUMN payment_notify_records.notify_id IS '通知ID';
COMMENT ON COLUMN payment_notify_records.order_no IS '订单号';
COMMENT ON COLUMN payment_notify_records.merchant_id IS '商户ID';
COMMENT ON COLUMN payment_notify_records.notify_params IS '通知参数';
COMMENT ON COLUMN payment_notify_records.status IS '通知状态';
COMMENT ON COLUMN payment_notify_records.notify_result IS '通知结果';
COMMENT ON COLUMN payment_notify_records.retry_count IS '重试次数';
COMMENT ON COLUMN payment_notify_records.next_retry_time IS '下次重试时间';
COMMENT ON COLUMN payment_notify_records.create_time IS '创建时间';
COMMENT ON COLUMN payment_notify_records.update_time IS '更新时间';

-- 创建更新时间触发器
CREATE OR REPLACE TRIGGER trg_notify_records_upd
    BEFORE UPDATE ON payment_notify_records
    FOR EACH ROW
BEGIN
    :NEW.update_time := SYSTIMESTAMP;
END;
/ 