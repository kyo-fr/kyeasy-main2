-- 订单规格表
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE order_item_specifications';
EXCEPTION WHEN OTHERS THEN
    IF SQLCODE != -942 THEN RAISE; END IF;
END;
/

CREATE TABLE order_item_specifications (
    id VARCHAR2(64) PRIMARY KEY,
    product_spec_id VARCHAR2(64) NOT NULL,
    order_item_id VARCHAR2(64) NOT NULL,
    name VARCHAR2(255) NOT NULL,
    value VARCHAR2(4000),
    price NUMBER(20) NOT NULL,
    currency VARCHAR2(3),
    currency_scale NUMBER(2) DEFAULT 2
);

COMMENT ON TABLE order_item_specifications IS '订单规格表';
COMMENT ON COLUMN order_item_specifications.id IS '规格ID';
COMMENT ON COLUMN order_item_specifications.product_spec_id IS '商品规格ID（来自商品服务）';
COMMENT ON COLUMN order_item_specifications.order_item_id IS '订单项ID';
COMMENT ON COLUMN order_item_specifications.name IS '规格名称';
COMMENT ON COLUMN order_item_specifications.value IS '规格值';
COMMENT ON COLUMN order_item_specifications.price IS '规格价格(分)';
COMMENT ON COLUMN order_item_specifications.currency IS '币种';
COMMENT ON COLUMN order_item_specifications.currency_scale IS '币种精度';

-- 订单支付记录表
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE order_payments';
EXCEPTION WHEN OTHERS THEN
    IF SQLCODE != -942 THEN RAISE; END IF;
END;
/

CREATE TABLE order_payments (
    id VARCHAR2(64) PRIMARY KEY,
    order_id VARCHAR2(64) NOT NULL,
    channel_id VARCHAR2(64),
    trade_no VARCHAR2(128),
    amount NUMBER(20) NOT NULL,
    currency VARCHAR2(3),
    currency_scale NUMBER(2),
    user_id VARCHAR2(64),
    status NUMBER(10) NOT NULL,
    fail_reason VARCHAR2(4000),
    pay_time NUMBER(13),
    creator VARCHAR2(64),
    create_time NUMBER(13),
    updater VARCHAR2(64),
    update_time NUMBER(13),
    version NUMBER(10) DEFAULT 0,
    deleted NUMBER(10) DEFAULT 0
);

COMMENT ON TABLE order_payments IS '订单支付记录表';
COMMENT ON COLUMN order_payments.id IS '主键ID';
COMMENT ON COLUMN order_payments.order_id IS '订单ID';
COMMENT ON COLUMN order_payments.channel_id IS '支付渠道ID';
COMMENT ON COLUMN order_payments.trade_no IS '支付流水号';
COMMENT ON COLUMN order_payments.amount IS '支付金额（分）';
COMMENT ON COLUMN order_payments.currency IS '币种';
COMMENT ON COLUMN order_payments.currency_scale IS '币种精度';
COMMENT ON COLUMN order_payments.user_id IS '支付用户ID';
COMMENT ON COLUMN order_payments.status IS '支付状态（1:成功,0:失败）';
COMMENT ON COLUMN order_payments.fail_reason IS '失败原因';
COMMENT ON COLUMN order_payments.pay_time IS '支付时间';
COMMENT ON COLUMN order_payments.creator IS '创建者';
COMMENT ON COLUMN order_payments.create_time IS '创建时间';
COMMENT ON COLUMN order_payments.updater IS '更新者';
COMMENT ON COLUMN order_payments.update_time IS '更新时间';
COMMENT ON COLUMN order_payments.version IS '版本号（乐观锁，每次更新+1）';
COMMENT ON COLUMN order_payments.deleted IS '删除标记（0：正常，1：已删除）';

-- 订单项表
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE order_items';
EXCEPTION WHEN OTHERS THEN
    IF SQLCODE != -942 THEN RAISE; END IF;
END;
/

CREATE TABLE order_items (
    id VARCHAR2(64) PRIMARY KEY,
    order_id VARCHAR2(64) NOT NULL,
    product_id VARCHAR2(64) NOT NULL,
    product_name VARCHAR2(255) NOT NULL,
    unit_price NUMBER(20) NOT NULL,
    discounted_price NUMBER(20),
    quantity NUMBER(10) NOT NULL,
    total_price NUMBER(20) NOT NULL,
    currency VARCHAR2(3),
    currency_scale NUMBER(2),
    payment_status NUMBER(10),
    create_time NUMBER(13),
    update_time NUMBER(13),
    version NUMBER(10) DEFAULT 0,
    deleted NUMBER(10) DEFAULT 0
);

COMMENT ON TABLE order_items IS '订单项表';
COMMENT ON COLUMN order_items.id IS '订单项ID';
COMMENT ON COLUMN order_items.order_id IS '订单ID';
COMMENT ON COLUMN order_items.product_id IS '商品ID';
COMMENT ON COLUMN order_items.product_name IS '商品名称';
COMMENT ON COLUMN order_items.unit_price IS '商品单价（分）';
COMMENT ON COLUMN order_items.discounted_price IS '优惠价格（分）';
COMMENT ON COLUMN order_items.quantity IS '数量';
COMMENT ON COLUMN order_items.total_price IS '订单项总价（分）';
COMMENT ON COLUMN order_items.currency IS '币种';
COMMENT ON COLUMN order_items.currency_scale IS '币种精度';
COMMENT ON COLUMN order_items.payment_status IS '支付状态（0：未支付，1：已支付，2：部分支付）';
COMMENT ON COLUMN order_items.create_time IS '创建时间';
COMMENT ON COLUMN order_items.update_time IS '更新时间';
COMMENT ON COLUMN order_items.version IS '版本号（乐观锁，每次更新+1）';
COMMENT ON COLUMN order_items.deleted IS '删除标记（0：正常，1：已删除）';

-- 订单配送信息表
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE order_delivery_info';
EXCEPTION WHEN OTHERS THEN
    IF SQLCODE != -942 THEN RAISE; END IF;
END;
/

CREATE TABLE order_delivery_info (
     id VARCHAR2(64) PRIMARY KEY,
     order_id VARCHAR2(64) NOT NULL,
     delivery_type NUMBER(10) NOT NULL,
     rider_id VARCHAR2(64),
     delivery_company VARCHAR2(255),
     tracking_no VARCHAR2(128),
     delivery_phone VARCHAR2(20),
     delivery_name VARCHAR2(255),
     delivery_start_time NUMBER(13),
     receiver_name VARCHAR2(255),
     receiver_phone VARCHAR2(20),
     delivery_address VARCHAR2(1000),
     delivery_latitude NUMBER(17,14),
     delivery_longitude NUMBER(17,14),
     delivery_distance NUMBER(10,2),
     delivery_fee NUMBER(20),
     currency VARCHAR2(3),
     currency_scale NUMBER(2),
     create_time NUMBER(13),
     update_time NUMBER(13),
     version NUMBER(10) DEFAULT 0,
     deleted NUMBER(10) DEFAULT 0
);

COMMENT ON TABLE order_delivery_info IS '订单配送信息表';
COMMENT ON COLUMN order_delivery_info.id IS '主键ID';
COMMENT ON COLUMN order_delivery_info.order_id IS '订单ID';
COMMENT ON COLUMN order_delivery_info.delivery_type IS '配送方式（1: 自提, 2: 快递, 3: 骑手配送）';
COMMENT ON COLUMN order_delivery_info.rider_id IS '骑手ID';
COMMENT ON COLUMN order_delivery_info.delivery_company IS '物流公司';
COMMENT ON COLUMN order_delivery_info.tracking_no IS '物流单号';
COMMENT ON COLUMN order_delivery_info.delivery_phone IS '配送联系电话';
COMMENT ON COLUMN order_delivery_info.delivery_start_time IS '配送开始时间';
COMMENT ON COLUMN order_delivery_info.receiver_name IS '收货人姓名';
COMMENT ON COLUMN order_delivery_info.receiver_phone IS '收货人电话';
COMMENT ON COLUMN order_delivery_info.delivery_address IS '配送地址';
COMMENT ON COLUMN order_delivery_info.delivery_latitude IS '配送地址纬度';
COMMENT ON COLUMN order_delivery_info.delivery_longitude IS '配送地址经度';
COMMENT ON COLUMN order_delivery_info.delivery_distance IS '配送距离(公里)';
COMMENT ON COLUMN order_delivery_info.delivery_fee IS '配送费用(分)';
COMMENT ON COLUMN order_delivery_info.currency IS '币种';
COMMENT ON COLUMN order_delivery_info.currency_scale IS '币种精度';
COMMENT ON COLUMN order_delivery_info.create_time IS '创建时间';
COMMENT ON COLUMN order_delivery_info.update_time IS '更新时间';
COMMENT ON COLUMN order_delivery_info.version IS '版本号（乐观锁）';
COMMENT ON COLUMN order_delivery_info.deleted IS '删除标记（0:正常,1:删除）';

-- 订单预订信息表
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE order_reservation_info';
EXCEPTION WHEN OTHERS THEN
    IF SQLCODE != -942 THEN RAISE; END IF;
END;
/

CREATE TABLE order_reservation_info (
    id VARCHAR2(64) PRIMARY KEY,
    order_id VARCHAR2(64) NOT NULL,
    reservation_time NUMBER(13) NOT NULL,
    reserver_name VARCHAR2(255) NOT NULL,
    reserver_phone VARCHAR2(20) NOT NULL,
    dining_number NUMBER(10),
    room_preference VARCHAR2(4000),
    dietary_requirements VARCHAR2(4000),
    remarks VARCHAR2(4000),
    create_time NUMBER(13),
    update_time NUMBER(13),
    version NUMBER(10) DEFAULT 0,
    deleted NUMBER(10) DEFAULT 0
);

COMMENT ON TABLE order_reservation_info IS '订单预订信息表';
COMMENT ON COLUMN order_reservation_info.id IS '主键ID';
COMMENT ON COLUMN order_reservation_info.order_id IS '订单ID';
COMMENT ON COLUMN order_reservation_info.reservation_time IS '预订时间';
COMMENT ON COLUMN order_reservation_info.reserver_name IS '预订人姓名';
COMMENT ON COLUMN order_reservation_info.reserver_phone IS '预订人电话';
COMMENT ON COLUMN order_reservation_info.dining_number IS '就餐人数';
COMMENT ON COLUMN order_reservation_info.room_preference IS '包间要求';
COMMENT ON COLUMN order_reservation_info.dietary_requirements IS '特殊餐饮要求';
COMMENT ON COLUMN order_reservation_info.remarks IS '预订备注';
COMMENT ON COLUMN order_reservation_info.create_time IS '创建时间';
COMMENT ON COLUMN order_reservation_info.update_time IS '更新时间';
COMMENT ON COLUMN order_reservation_info.version IS '版本号（乐观锁，每次更新+1）';
COMMENT ON COLUMN order_reservation_info.deleted IS '删除标记（0：正常，1：已删除）';

-- 订单状态日志表
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE order_status_logs';
EXCEPTION WHEN OTHERS THEN
    IF SQLCODE != -942 THEN RAISE; END IF;
END;
/

CREATE TABLE order_status_logs (
    id VARCHAR2(64) PRIMARY KEY,
    order_id VARCHAR2(64) NOT NULL,
    status_type NUMBER(10) NOT NULL,
    old_status NUMBER(10),
    new_status NUMBER(10) NOT NULL,
    operator_id VARCHAR2(64),
    remark VARCHAR2(4000),
    operate_time NUMBER(13),
    version NUMBER(10) DEFAULT 0,
    deleted NUMBER(10) DEFAULT 0
);

COMMENT ON TABLE order_status_logs IS '订单状态变更日志表';
COMMENT ON COLUMN order_status_logs.id IS '主键ID';
COMMENT ON COLUMN order_status_logs.order_id IS '订单ID';
COMMENT ON COLUMN order_status_logs.status_type IS '状态类型（1:订单状态 2:支付状态 3:配送状态）';
COMMENT ON COLUMN order_status_logs.old_status IS '原状态值';
COMMENT ON COLUMN order_status_logs.new_status IS '新状态值';
COMMENT ON COLUMN order_status_logs.operator_id IS '操作人ID';
COMMENT ON COLUMN order_status_logs.remark IS '操作备注';
COMMENT ON COLUMN order_status_logs.operate_time IS '变更时间';
COMMENT ON COLUMN order_status_logs.version IS '版本号（乐观锁，每次更新+1）';
COMMENT ON COLUMN order_status_logs.deleted IS '删除标记（0：正常，1：已删除）';

-- 订单主表
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE orders';
EXCEPTION WHEN OTHERS THEN
    IF SQLCODE != -942 THEN RAISE; END IF;
END;
/

CREATE TABLE orders (
    id VARCHAR2(64) PRIMARY KEY,
    merchant_id VARCHAR2(64) NOT NULL,
    order_number VARCHAR2(64) NOT NULL,
    total_amount NUMBER(20) NOT NULL,
    currency VARCHAR2(3),
    service_fee NUMBER(20),
    status NUMBER(10) NOT NULL,
    order_type NUMBER(10) NOT NULL,
    payment_mode NUMBER(10),
    payment_channel_id VARCHAR2(64),
    payment_trade_no VARCHAR2(128),
    payment_time NUMBER(13),
    payment_deadline NUMBER(13),
    payment_status NUMBER(10),
    paid_amount NUMBER(20),
    timezone VARCHAR2(64),
    reservation_time NUMBER(13),
    table_no VARCHAR2(64),
    dining_number NUMBER(10),
    pickup_time NUMBER(13),
    delivery_time NUMBER(13),
    finish_time NUMBER(13),
    currency_scale NUMBER(2),
    delivery_type NUMBER(10),
    delivery_status NUMBER(10),
    tenant_id VARCHAR2(64) NOT NULL,
    creator VARCHAR2(64),
    create_time NUMBER(13),
    updater VARCHAR2(64),
    update_time NUMBER(13),
    version NUMBER(10) DEFAULT 0,
    deleted NUMBER(10) DEFAULT 0
);

COMMENT ON TABLE orders IS '订单主表';
COMMENT ON COLUMN orders.id IS '主键ID';
COMMENT ON COLUMN orders.merchant_id IS '商户ID';
COMMENT ON COLUMN orders.order_number IS '订单号';
COMMENT ON COLUMN orders.total_amount IS '订单总金额（分）';
COMMENT ON COLUMN orders.currency IS '币种';
COMMENT ON COLUMN orders.service_fee IS '服务费（分）';
COMMENT ON COLUMN orders.status IS '订单状态';
COMMENT ON COLUMN orders.order_type IS '订单类型';
COMMENT ON COLUMN orders.payment_mode IS '支付方式';
COMMENT ON COLUMN orders.payment_channel_id IS '支付渠道ID';
COMMENT ON COLUMN orders.payment_trade_no IS '支付流水号';
COMMENT ON COLUMN orders.payment_time IS '支付时间';
COMMENT ON COLUMN orders.payment_deadline IS '支付截止时间';
COMMENT ON COLUMN orders.payment_status IS '支付状态';
COMMENT ON COLUMN orders.paid_amount IS '已支付金额（分）';
COMMENT ON COLUMN orders.timezone IS '商户时区';
COMMENT ON COLUMN orders.reservation_time IS '预订时间';
COMMENT ON COLUMN orders.table_no IS '桌号';
COMMENT ON COLUMN orders.dining_number IS '就餐人数';
COMMENT ON COLUMN orders.pickup_time IS '取餐时间';
COMMENT ON COLUMN orders.delivery_time IS '配送时间';
COMMENT ON COLUMN orders.finish_time IS '完成时间';
COMMENT ON COLUMN orders.currency_scale IS '币种精度';
COMMENT ON COLUMN orders.delivery_type IS '配送方式';
COMMENT ON COLUMN orders.delivery_status IS '配送状态';
COMMENT ON COLUMN orders.tenant_id IS '租户ID';
COMMENT ON COLUMN orders.creator IS '创建者';
COMMENT ON COLUMN orders.create_time IS '创建时间';
COMMENT ON COLUMN orders.updater IS '更新者';
COMMENT ON COLUMN orders.update_time IS '更新时间';
COMMENT ON COLUMN orders.version IS '版本号（乐观锁，每次更新+1）';
COMMENT ON COLUMN orders.deleted IS '删除标记（0：正常，1：已删除）';

-- 订单操作日志表
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE order_operation_logs';
EXCEPTION WHEN OTHERS THEN
    IF SQLCODE != -942 THEN RAISE; END IF;
END;
/

CREATE TABLE order_operation_logs (
    id VARCHAR2(64) PRIMARY KEY,
    order_id VARCHAR2(64) NOT NULL,
    action NUMBER(10) NOT NULL,
    operator_id VARCHAR2(64),
    content VARCHAR2(4000),
    remark VARCHAR2(4000),
    operate_time NUMBER(13),
    amount NUMBER(20),
    currency VARCHAR2(3),
    currency_scale NUMBER(2),
    item_count NUMBER(10),
    order_count NUMBER(10),
    version NUMBER(10) DEFAULT 0,
    tenant_id VARCHAR2(64) NOT NULL,
    deleted NUMBER(10) DEFAULT 0
);

COMMENT ON TABLE order_operation_logs IS '订单操作日志表';
COMMENT ON COLUMN order_operation_logs.id IS '主键ID';
COMMENT ON COLUMN order_operation_logs.order_id IS '订单ID';
COMMENT ON COLUMN order_operation_logs.action IS '操作类型';
COMMENT ON COLUMN order_operation_logs.operator_id IS '操作人ID';
COMMENT ON COLUMN order_operation_logs.content IS '操作内容';
COMMENT ON COLUMN order_operation_logs.remark IS '操作备注';
COMMENT ON COLUMN order_operation_logs.operate_time IS '操作时间';
COMMENT ON COLUMN order_operation_logs.amount IS '操作涉及的金额（分）';
COMMENT ON COLUMN order_operation_logs.currency IS '币种';
COMMENT ON COLUMN order_operation_logs.currency_scale IS '币种精度';
COMMENT ON COLUMN order_operation_logs.item_count IS '操作涉及的商品数量';
COMMENT ON COLUMN order_operation_logs.order_count IS '操作涉及的订单数量';
COMMENT ON COLUMN order_operation_logs.version IS '版本号（乐观锁，每次更新+1）';
COMMENT ON COLUMN order_operation_logs.tenant_id IS '租户ID';
COMMENT ON COLUMN order_operation_logs.deleted IS '删除标记（0：正常，1：已删除）';