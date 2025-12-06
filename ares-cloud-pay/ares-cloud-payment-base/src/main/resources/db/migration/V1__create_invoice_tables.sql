-- 删除交易方表（如果存在）
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE t_party CASCADE CONSTRAINTS';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

-- 创建交易方表
CREATE TABLE t_party (
    id VARCHAR2(38) NOT NULL,
    party_id VARCHAR2(38) NOT NULL,
    invoice_id VARCHAR2(38),
    name VARCHAR2(100) NOT NULL,
    party_type NUMBER(1) NOT NULL,
    user_status NUMBER(1) DEFAULT 0 NOT NULL,
    tax_id VARCHAR2(50),
    address VARCHAR2(200),
    postal_code VARCHAR2(20),
    phone VARCHAR2(20),
    email VARCHAR2(100),
    country_code VARCHAR2(10),
    create_time NUMBER(20),
    update_time NUMBER(20),
    is_deleted NUMBER(1) DEFAULT 0,
    CONSTRAINT pk_party PRIMARY KEY (id)
);

COMMENT ON TABLE t_party IS '交易方表';
COMMENT ON COLUMN t_party.id IS '主键ID';
COMMENT ON COLUMN t_party.party_id IS '交易方ID';
COMMENT ON COLUMN t_party.invoice_id IS '关联发票ID';
COMMENT ON COLUMN t_party.name IS '交易方名称';
COMMENT ON COLUMN t_party.party_type IS '交易方类型(1:商户,2:个人)';
COMMENT ON COLUMN t_party.user_status IS '用户状态(0:未注册,1:已注册)';
COMMENT ON COLUMN t_party.tax_id IS '税号';
COMMENT ON COLUMN t_party.address IS '地址';
COMMENT ON COLUMN t_party.postal_code IS '邮编';
COMMENT ON COLUMN t_party.phone IS '联系电话';
COMMENT ON COLUMN t_party.email IS '电子邮箱';
COMMENT ON COLUMN t_party.country_code IS '国家代码';
COMMENT ON COLUMN t_party.create_time IS '创建时间';
COMMENT ON COLUMN t_party.update_time IS '更新时间';
COMMENT ON COLUMN t_party.is_deleted IS '是否删除(0:未删除,1:已删除)';

-- 创建索引
CREATE INDEX idx_party_party_id ON t_party(party_id);
CREATE INDEX idx_party_invoice_id ON t_party(invoice_id);
CREATE INDEX idx_party_user_status ON t_party(user_status);
CREATE INDEX idx_party_country_code ON t_party(country_code);

-- 删除发票表（如果存在）
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE pay_invoice CASCADE CONSTRAINTS';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

-- 创建发票表
CREATE TABLE pay_invoice (
    id VARCHAR2(38) NOT NULL,
    transaction_party_type VARCHAR2(20) NOT NULL,
    payer_id VARCHAR2(38) NOT NULL,
    payee_id VARCHAR2(38) NOT NULL,
    contract_id VARCHAR2(38),
    order_id VARCHAR2(38),
    create_time NUMBER(20),
    delivery_time NUMBER(20),
    complete_time NUMBER(20),
    transaction_id VARCHAR2(38),
    digital_signature VARCHAR2(64),
    currency VARCHAR2(10) NOT NULL,
    scale NUMBER(2) NOT NULL,
    pre_tax_amount NUMBER(20,0),
    total_amount NUMBER(20,0),
    deduct_amount NUMBER(20,0),
    status VARCHAR2(20),
    creator VARCHAR2(38),
    updater VARCHAR2(38),
    update_time NUMBER(20),
    version NUMBER(10) DEFAULT 1,
    deleted NUMBER(1) DEFAULT 0,
    tenant_id VARCHAR2(38),
    CONSTRAINT pk_invoice PRIMARY KEY (id)
);

COMMENT ON TABLE pay_invoice IS '发票表';
COMMENT ON COLUMN pay_invoice.id IS '主键ID';
COMMENT ON COLUMN pay_invoice.transaction_party_type IS '交易方类型';
COMMENT ON COLUMN pay_invoice.payer_id IS '付款方ID';
COMMENT ON COLUMN pay_invoice.payee_id IS '收款方ID';
COMMENT ON COLUMN pay_invoice.contract_id IS '合同ID';
COMMENT ON COLUMN pay_invoice.order_id IS '订单ID';
COMMENT ON COLUMN pay_invoice.create_time IS '创建时间';
COMMENT ON COLUMN pay_invoice.delivery_time IS '配送时间';
COMMENT ON COLUMN pay_invoice.complete_time IS '完成时间';
COMMENT ON COLUMN pay_invoice.transaction_id IS '交易ID';
COMMENT ON COLUMN pay_invoice.digital_signature IS '数字签名hash';
COMMENT ON COLUMN pay_invoice.currency IS '币种';
COMMENT ON COLUMN pay_invoice.scale IS '币种精度';
COMMENT ON COLUMN pay_invoice.pre_tax_amount IS '税前总价';
COMMENT ON COLUMN pay_invoice.total_amount IS '总价（含税）';
COMMENT ON COLUMN pay_invoice.deduct_amount IS '减免金额';
COMMENT ON COLUMN pay_invoice.status IS '发票状态';
COMMENT ON COLUMN pay_invoice.creator IS '创建者';
COMMENT ON COLUMN pay_invoice.updater IS '更新者';
COMMENT ON COLUMN pay_invoice.update_time IS '更新时间';
COMMENT ON COLUMN pay_invoice.version IS '版本号';
COMMENT ON COLUMN pay_invoice.deleted IS '删除标记(0:未删除,1:已删除)';
COMMENT ON COLUMN pay_invoice.tenant_id IS '租户ID';

-- 删除发票明细表（如果存在）
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE pay_invoice_item CASCADE CONSTRAINTS';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

-- 创建发票明细表
CREATE TABLE pay_invoice_item (
    id VARCHAR2(38) NOT NULL,
    invoice_id VARCHAR2(38) NOT NULL,
    order_item_id VARCHAR2(38),
    product_id VARCHAR2(38),
    product_name VARCHAR2(100) NOT NULL,
    quantity NUMBER(10) NOT NULL,
    original_price NUMBER(20,0),
    unit_price NUMBER(20,0) NOT NULL,
    tax_rate NUMBER(20,4),
    tax_amount NUMBER(20,0) NOT NULL,
    total_amount NUMBER(20,0) NOT NULL,
    remark VARCHAR2(200),
    CONSTRAINT pk_invoice_item PRIMARY KEY (id)
);

COMMENT ON TABLE pay_invoice_item IS '发票明细表';
COMMENT ON COLUMN pay_invoice_item.id IS '主键ID';
COMMENT ON COLUMN pay_invoice_item.order_item_id IS '订单项ID';
COMMENT ON COLUMN pay_invoice_item.invoice_id IS '发票ID';
COMMENT ON COLUMN pay_invoice_item.product_id IS '商品ID';
COMMENT ON COLUMN pay_invoice_item.product_name IS '商品名称';
COMMENT ON COLUMN pay_invoice_item.quantity IS '数量';
COMMENT ON COLUMN pay_invoice_item.original_price IS '原价';
COMMENT ON COLUMN pay_invoice_item.unit_price IS '单价';
COMMENT ON COLUMN pay_invoice_item.tax_rate IS '税率';
COMMENT ON COLUMN pay_invoice_item.tax_amount IS '税额';
COMMENT ON COLUMN pay_invoice_item.total_amount IS '总价（含税）';
COMMENT ON COLUMN pay_invoice_item.remark IS '备注';

-- 删除支付项表（如果存在）
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE pay_item CASCADE CONSTRAINTS';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

-- 创建支付项表
CREATE TABLE pay_item (
    id VARCHAR2(38) NOT NULL,
    channel_id VARCHAR2(38) NOT NULL,
    trade_no VARCHAR2(64),
    amount NUMBER(20,0) NOT NULL,
    pay_time NUMBER(20),
    status NUMBER(1) DEFAULT 1,
    invoice_id VARCHAR2(38) NOT NULL,
    pay_type NUMBER(1) DEFAULT 1,
    remark VARCHAR2(200),
    CONSTRAINT pk_pay_item PRIMARY KEY (id)
);

COMMENT ON TABLE pay_item IS '支付项表';
COMMENT ON COLUMN pay_item.id IS '主键ID';
COMMENT ON COLUMN pay_item.channel_id IS '支付渠道ID';
COMMENT ON COLUMN pay_item.trade_no IS '交易流水号';
COMMENT ON COLUMN pay_item.amount IS '支付金额';
COMMENT ON COLUMN pay_item.pay_time IS '支付时间';
COMMENT ON COLUMN pay_item.status IS '支付状态(0:未支付,1:已支付,2:支付失败)';
COMMENT ON COLUMN pay_item.invoice_id IS '关联发票ID';
COMMENT ON COLUMN pay_item.pay_type IS '支付类型(1:线上支付,2:线下支付)';
COMMENT ON COLUMN pay_item.remark IS '备注';