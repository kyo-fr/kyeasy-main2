-- 创建商户表
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE merchants CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

CREATE TABLE merchants (
    id VARCHAR2(50) PRIMARY KEY,
    merchant_no VARCHAR2(50) NOT NULL,
    merchant_name VARCHAR2(100) NOT NULL,
    merchant_type VARCHAR2(20) NOT NULL,
    status VARCHAR2(20) NOT NULL,
    pay_secret VARCHAR2(100) NOT NULL,
    pay_password VARCHAR2(100),
    supported_regions CLOB,
    contact_person VARCHAR2(50),
    contact_phone VARCHAR2(20),
    contact_email VARCHAR2(100),
    address VARCHAR2(500),
    business_license VARCHAR2(100),
    legal_representative VARCHAR2(50),
    creator VARCHAR2(50),
    create_time NUMBER(20) NOT NULL,
    updater VARCHAR2(50),
    update_time NUMBER(20) NOT NULL,
    version NUMBER(10) DEFAULT 1 NOT NULL,
    deleted NUMBER(1) DEFAULT 0 NOT NULL,
    CONSTRAINT uk_merchant_no UNIQUE (merchant_no)
);

COMMENT ON TABLE merchants IS '商户表';
COMMENT ON COLUMN merchants.id IS '主键ID';
COMMENT ON COLUMN merchants.merchant_no IS '商户号';
COMMENT ON COLUMN merchants.merchant_name IS '商户名称';
COMMENT ON COLUMN merchants.merchant_type IS '商户类型';
COMMENT ON COLUMN merchants.status IS '商户状态';
COMMENT ON COLUMN merchants.pay_secret IS '支付密钥';
COMMENT ON COLUMN merchants.pay_password IS '付款密码';
COMMENT ON COLUMN merchants.supported_regions IS '支持的支付区域(JSON格式)';
COMMENT ON COLUMN merchants.contact_person IS '联系人';
COMMENT ON COLUMN merchants.contact_phone IS '联系电话';
COMMENT ON COLUMN merchants.contact_email IS '联系邮箱';
COMMENT ON COLUMN merchants.address IS '商户地址';
COMMENT ON COLUMN merchants.business_license IS '营业执照号';
COMMENT ON COLUMN merchants.legal_representative IS '法人代表';
COMMENT ON COLUMN merchants.creator IS '创建者';
COMMENT ON COLUMN merchants.create_time IS '创建时间';
COMMENT ON COLUMN merchants.updater IS '更新者';
COMMENT ON COLUMN merchants.update_time IS '更新时间';
COMMENT ON COLUMN merchants.version IS '版本号';
COMMENT ON COLUMN merchants.deleted IS '删除标记(0:未删除,1:已删除)';

-- 创建钱包表
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE wallets CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

CREATE TABLE wallets (
    id VARCHAR2(50) PRIMARY KEY,
    owner_id VARCHAR2(50) NOT NULL,
    owner_type VARCHAR2(20) NOT NULL,
    payment_region VARCHAR2(10) NOT NULL,
    balance NUMBER(20) DEFAULT 0 NOT NULL,
    frozen_amount NUMBER(20) DEFAULT 0 NOT NULL,
    status VARCHAR2(20) DEFAULT 'ACTIVE' NOT NULL,
    create_time NUMBER(20) NOT NULL,
    update_time NUMBER(20) NOT NULL,
    creator VARCHAR2(50),
    updater VARCHAR2(50),
    version NUMBER(10) DEFAULT 1 NOT NULL,
    deleted NUMBER(1) DEFAULT 0 NOT NULL,
    CONSTRAINT uk_wallet_owner_region UNIQUE (owner_id, payment_region)
);

COMMENT ON TABLE wallets IS '钱包表';
COMMENT ON COLUMN wallets.id IS '主键ID';
COMMENT ON COLUMN wallets.owner_id IS '账户ID或商户ID';
COMMENT ON COLUMN wallets.owner_type IS '所有者类型(ACCOUNT:账户,MERCHANT:商户)';
COMMENT ON COLUMN wallets.payment_region IS '支付区域(EUR/USD/CNY/CHF/GBP)';
COMMENT ON COLUMN wallets.balance IS '余额(以分为单位)';
COMMENT ON COLUMN wallets.frozen_amount IS '冻结金额(以分为单位)';
COMMENT ON COLUMN wallets.status IS '钱包状态(ACTIVE:激活,FROZEN:冻结,CLOSED:关闭)';
COMMENT ON COLUMN wallets.create_time IS '创建时间';
COMMENT ON COLUMN wallets.update_time IS '更新时间';
COMMENT ON COLUMN wallets.creator IS '创建者';
COMMENT ON COLUMN wallets.updater IS '更新者';
COMMENT ON COLUMN wallets.version IS '版本号';
COMMENT ON COLUMN wallets.deleted IS '删除标记(0:未删除,1:已删除)';

-- 删除并重新创建账户表（普通表）
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE accounts CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

CREATE TABLE accounts (
    id VARCHAR2(50) PRIMARY KEY,
    user_id VARCHAR2(50) NOT NULL,
    password VARCHAR2(100),
    country_code VARCHAR2(10),
    phone VARCHAR2(20),
    account VARCHAR2(50),
    status VARCHAR2(20) DEFAULT 'ACTIVE' NOT NULL,
    creator VARCHAR2(50),
    create_time NUMBER(20) NOT NULL,
    updater VARCHAR2(50),
    update_time NUMBER(20) NOT NULL,
    version NUMBER(10) DEFAULT 1 NOT NULL,
    deleted NUMBER(1) DEFAULT 0 NOT NULL,
    CONSTRAINT uk_account_user_id UNIQUE (user_id),
    CONSTRAINT uk_account_phone UNIQUE (phone),
    CONSTRAINT uk_account_account UNIQUE (account)
);

COMMENT ON TABLE accounts IS '账户表';
COMMENT ON COLUMN accounts.id IS '主键ID';
COMMENT ON COLUMN accounts.user_id IS '用户ID';
COMMENT ON COLUMN accounts.password IS '密码';
COMMENT ON COLUMN accounts.country_code IS '国家代码';
COMMENT ON COLUMN accounts.phone IS '手机号';
COMMENT ON COLUMN accounts.account IS '账号';
COMMENT ON COLUMN accounts.status IS '账户状态(ACTIVE:激活,FROZEN:冻结,CLOSED:关闭)';
COMMENT ON COLUMN accounts.creator IS '创建者';
COMMENT ON COLUMN accounts.create_time IS '创建时间';
COMMENT ON COLUMN accounts.updater IS '更新者';
COMMENT ON COLUMN accounts.update_time IS '更新时间';
COMMENT ON COLUMN accounts.version IS '版本号';
COMMENT ON COLUMN accounts.deleted IS '删除标记(0:未删除,1:已删除)';

-- 创建支付订单表（普通表）
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE payment_order CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

CREATE TABLE payment_order (
    id VARCHAR2(50) PRIMARY KEY,
    order_no VARCHAR2(50) NOT NULL,
    merchant_order_no VARCHAR2(64),
    merchant_id VARCHAR2(50),
    amount NUMBER(20) NOT NULL,
    currency VARCHAR2(10) NOT NULL,
    scale NUMBER(2) NOT NULL,
    payment_region VARCHAR2(10),
    payment_method NUMBER(1) DEFAULT 1,
    subject VARCHAR2(255),
    body VARCHAR2(4000),
    return_url VARCHAR2(500),
    notify_url VARCHAR2(500),
    status NUMBER(1) DEFAULT 0 NOT NULL,
    channel NUMBER(1) NOT NULL,
    expire_time NUMBER(20),
    pay_time NUMBER(20),
    custom_params CLOB,
    creator VARCHAR2(50),
    create_time NUMBER(20) NOT NULL,
    updater VARCHAR2(50),
    update_time NUMBER(20) NOT NULL,
    version NUMBER(10) DEFAULT 1 NOT NULL,
    deleted NUMBER(1) DEFAULT 0 NOT NULL,
    CONSTRAINT uk_payment_order_no UNIQUE (order_no)
);

COMMENT ON TABLE payment_order IS '支付订单表';
COMMENT ON COLUMN payment_order.id IS '主键ID';
COMMENT ON COLUMN payment_order.order_no IS '业务订单号（系统生成）';
COMMENT ON COLUMN payment_order.merchant_order_no IS '商户订单号（商户传入）';
COMMENT ON COLUMN payment_order.merchant_id IS '商户ID';
COMMENT ON COLUMN payment_order.amount IS '订单金额(以分为单位)';
COMMENT ON COLUMN payment_order.currency IS '币种';
COMMENT ON COLUMN payment_order.scale IS '币种精度';
COMMENT ON COLUMN payment_order.payment_region IS '支付区域(EUR/USD/CNY/CHF/GBP)';
COMMENT ON COLUMN payment_order.payment_method IS '支付方式(1:余额支付)';
COMMENT ON COLUMN payment_order.subject IS '订单标题';
COMMENT ON COLUMN payment_order.body IS '订单描述';
COMMENT ON COLUMN payment_order.return_url IS '支付完成后的跳转地址';
COMMENT ON COLUMN payment_order.notify_url IS '支付结果通知地址';
COMMENT ON COLUMN payment_order.status IS '订单状态(0:待支付 1:成功 2:失败 3:关闭)';
COMMENT ON COLUMN payment_order.channel IS '支付渠道(1:礼物点)';
COMMENT ON COLUMN payment_order.expire_time IS '订单过期时间';
COMMENT ON COLUMN payment_order.pay_time IS '支付时间';
COMMENT ON COLUMN payment_order.custom_params IS '自定义参数（JSON格式存储）';
COMMENT ON COLUMN payment_order.creator IS '创建者';
COMMENT ON COLUMN payment_order.create_time IS '创建时间';
COMMENT ON COLUMN payment_order.updater IS '更新者';
COMMENT ON COLUMN payment_order.update_time IS '更新时间';
COMMENT ON COLUMN payment_order.version IS '版本号';
COMMENT ON COLUMN payment_order.deleted IS '删除标记(0:未删除,1:已删除)';

-- 创建交易记录区块链表
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE payment_transactions CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

CREATE BLOCKCHAIN TABLE payment_transactions (
    id VARCHAR2(50) PRIMARY KEY,
    from_account_id VARCHAR2(50),
    to_account_id VARCHAR2(50),
    order_id VARCHAR2(50),
    amount NUMBER(20) NOT NULL,
    fee_rate NUMBER(10),
    actual_amount NUMBER(20),
    payment_region VARCHAR2(10) NOT NULL,
    fee_amount NUMBER(20) NOT NULL,
    type VARCHAR2(20) NOT NULL,
    status VARCHAR2(20) NOT NULL,
    description VARCHAR2(200),
    create_time NUMBER(20) NOT NULL,
    update_time NUMBER(20) NOT NULL,
    creator VARCHAR2(50),
    updater VARCHAR2(50),
    version NUMBER(10) NOT NULL,
    deleted NUMBER(1) NOT NULL
) NO DROP UNTIL 3650 DAYS IDLE
NO DELETE UNTIL 16 DAYS AFTER INSERT
HASHING USING "SHA2_512" VERSION "v1";

COMMENT ON TABLE payment_transactions IS '交易记录区块链表';
COMMENT ON COLUMN payment_transactions.id IS '主键ID';
COMMENT ON COLUMN payment_transactions.from_account_id IS '来源账户ID或商户ID';
COMMENT ON COLUMN payment_transactions.to_account_id IS '目标账户ID或商户ID';
COMMENT ON COLUMN payment_transactions.order_id IS '支付订单ID';
COMMENT ON COLUMN payment_transactions.amount IS '交易金额(以分为单位)';
COMMENT ON COLUMN payment_transactions.payment_region IS '支付区域(EUR/USD/CNY/CHF/GBP)';
COMMENT ON COLUMN payment_transactions.fee_amount IS '手续费金额(以分为单位)';
COMMENT ON COLUMN payment_transactions.type IS '交易类型';
COMMENT ON COLUMN payment_transactions.status IS '交易状态';
COMMENT ON COLUMN payment_transactions.description IS '交易描述';
COMMENT ON COLUMN payment_transactions.create_time IS '创建时间';
COMMENT ON COLUMN payment_transactions.update_time IS '更新时间';
COMMENT ON COLUMN payment_transactions.creator IS '创建者';
COMMENT ON COLUMN payment_transactions.updater IS '更新者';
COMMENT ON COLUMN payment_transactions.version IS '版本号';
COMMENT ON COLUMN payment_transactions.deleted IS '删除标记(0:未删除,1:已删除)';

-- 创建账户流水表（区块表）
BEGIN
EXECUTE IMMEDIATE 'DROP TABLE payment_account_flow CASCADE CONSTRAINTS';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
/

CREATE BLOCKCHAIN TABLE payment_account_flow (
    id VARCHAR2(50) PRIMARY KEY,
    account_id VARCHAR2(50) NOT NULL,
    transaction_id VARCHAR2(50) NOT NULL,
    flow_type VARCHAR2(20) NOT NULL,
    amount NUMBER(20) NOT NULL,
    fee_amount NUMBER(20),
    fee_rate NUMBER(10),
    actual_amount NUMBER(20),
    currency VARCHAR2(10) NOT NULL,
    scale NUMBER(2) NOT NULL,
    balance_before NUMBER(20) NOT NULL,
    balance_after NUMBER(20) NOT NULL,
    create_time NUMBER(20) NOT NULL,
    creator VARCHAR2(50),
    updater VARCHAR2(50),
    update_time NUMBER(20) NOT NULL,
    version NUMBER(10) NOT NULL,
    deleted NUMBER(1) NOT NULL
) NO DROP UNTIL 3650 DAYS IDLE
NO DELETE UNTIL 16 DAYS AFTER INSERT
HASHING USING "SHA2_512" VERSION "v1";

COMMENT ON TABLE payment_account_flow IS '账户流水表';
COMMENT ON COLUMN payment_account_flow.id IS '主键ID';
COMMENT ON COLUMN payment_account_flow.account_id IS '账户ID';
COMMENT ON COLUMN payment_account_flow.transaction_id IS '交易ID';
COMMENT ON COLUMN payment_account_flow.flow_type IS '流水类型';
COMMENT ON COLUMN payment_account_flow.amount IS '变动金额(以分为单位)';
COMMENT ON COLUMN payment_account_flow.currency IS '币种';
COMMENT ON COLUMN payment_account_flow.scale IS '币种精度';
COMMENT ON COLUMN payment_account_flow.balance_before IS '变动前余额(以分为单位)';
COMMENT ON COLUMN payment_account_flow.balance_after IS '变动后余额(以分为单位)';
COMMENT ON COLUMN payment_account_flow.create_time IS '创建时间';
COMMENT ON COLUMN payment_account_flow.creator IS '创建者';
COMMENT ON COLUMN payment_account_flow.updater IS '更新者';
COMMENT ON COLUMN payment_account_flow.update_time IS '更新时间';
COMMENT ON COLUMN payment_account_flow.version IS '版本号';
COMMENT ON COLUMN payment_account_flow.deleted IS '删除标记(0:未删除,1:已删除)';


-- 创建索引
-- 删除已存在的索引
BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_merchants_merchant_no';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_wallets_owner';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_wallets_owner_type';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_wallets_region';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_accounts_user_id';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_accounts_phone';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_accounts_account';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_payment_order_merchant_order_no';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_payment_order_merchant_id';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_payment_order_payment_region';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_payment_order_pay_time';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_transactions_from_account';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_transactions_to_account';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_transactions_order';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_transactions_create_time';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_transactions_region';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_payment_account_flow_account';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_payment_account_flow_transaction';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_payment_account_flow_create_time';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_payment_account_flow_currency';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -1418 THEN
            RAISE;
        END IF;
END;
/

-- 创建索引
CREATE INDEX idx_merchants_merchant_no ON merchants(merchant_no);
CREATE INDEX idx_wallets_owner ON wallets(owner_id);
CREATE INDEX idx_wallets_owner_type ON wallets(owner_type);
CREATE INDEX idx_wallets_region ON wallets(payment_region);
CREATE INDEX idx_accounts_user_id ON accounts(user_id);
CREATE INDEX idx_accounts_phone ON accounts(phone);
CREATE INDEX idx_accounts_account ON accounts(account);
CREATE INDEX idx_payment_order_merchant_order_no ON payment_order(merchant_order_no);
CREATE INDEX idx_payment_order_merchant_id ON payment_order(merchant_id);
CREATE INDEX idx_payment_order_payment_region ON payment_order(payment_region);
CREATE INDEX idx_payment_order_pay_time ON payment_order(pay_time);
CREATE INDEX idx_transactions_from_account ON payment_transactions(from_account_id);
CREATE INDEX idx_transactions_to_account ON payment_transactions(to_account_id);
CREATE INDEX idx_transactions_order ON payment_transactions(order_id);
CREATE INDEX idx_transactions_create_time ON payment_transactions(create_time);
CREATE INDEX idx_transactions_region ON payment_transactions(payment_region);
CREATE INDEX idx_payment_account_flow_account ON payment_account_flow(account_id);
CREATE INDEX idx_payment_account_flow_transaction ON payment_account_flow(transaction_id);
CREATE INDEX idx_payment_account_flow_create_time ON payment_account_flow(create_time);
CREATE INDEX idx_payment_account_flow_currency ON payment_account_flow(currency); 