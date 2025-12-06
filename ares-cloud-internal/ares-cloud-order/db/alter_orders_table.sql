-- 为orders表添加phoneNumber和orderCode字段
ALTER TABLE orders ADD phone_number VARCHAR2(20);
ALTER TABLE orders ADD order_code VARCHAR2(50);

-- 添加注释
COMMENT ON COLUMN orders.phone_number IS '用户手机号';
COMMENT ON COLUMN orders.order_code IS '订单编号';


ALTER TABLE orders ADD country_code VARCHAR2(20);

-- 添加注释
COMMENT ON COLUMN orders.country_code IS '国家编码';



ALTER TABLE orders ADD rider_id VARCHAR2(50);

-- 添加注释
COMMENT ON COLUMN orders.rider_id IS '骑手id';